package helpers;

import com.avaje.ebean.Ebean;
import com.google.inject.Provider;
import models.*;
import net.coobird.thumbnailator.Thumbnails;
import play.Application;
import play.Logger;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 8/3/16.
 */
@Singleton
@SuppressWarnings({"ConstantConditions", "MismatchedQueryAndUpdateOfCollection"})
public class ControladorUtil {

    private Provider<Application> provider;

    public Controlador deepClone(Controlador controlador, Usuario usuario) {

        if (!controlador.podeClonar()) {
            throw new IllegalStateException();
        }

        long startTime = System.nanoTime();

        Controlador controladorClone = copyPrimitiveFields(controlador);

        /*
         * DADOS BASICOS
         */
        controlador.setStatusVersao(StatusVersao.EDITANDO);
        controladorClone.setArea(controlador.getArea());
        controladorClone.setModelo(controlador.getModelo());
        controladorClone.setSubarea(controlador.getSubarea());

        if (controlador.getEndereco() != null && controlador.getEndereco().getIdJson() != null) {
            Endereco enderecoAux = copyPrimitiveFields(controlador.getEndereco());
            enderecoAux.setControlador(controladorClone);
            controladorClone.setEndereco(enderecoAux);
        }

        /*
         * ANEIS
         */
        ArrayList<Anel> aneisClonados = new ArrayList<Anel>();
        controlador.getAneis().forEach(anel -> {
            Anel anelClonado = copyPrimitiveFields(anel);
            anelClonado.setControlador(controladorClone);

            anelClonado.setCroqui(getImagem(anel.getCroqui()));

            if (anel.getEndereco() != null && anel.getEndereco().getIdJson() != null) {
                Endereco enderecoAux = copyPrimitiveFields(anel.getEndereco());
                enderecoAux.setAnel(anelClonado);
                anelClonado.setEndereco(enderecoAux);
            }

            HashMap<UUID, Estagio> estagios = new HashMap<UUID, Estagio>();
            anel.getEstagios().forEach(estagio -> {
                Estagio estagioAux = copyPrimitiveFields(estagio);
                estagioAux.setAnel(anelClonado);
                estagioAux.setImagem(getImagem(estagio.getImagem()));
                anelClonado.addEstagio(estagioAux);
                estagios.put(estagio.getId(), estagioAux);
            });
            aneisClonados.add(anelClonado);

            /*
            * GRUPOS SEMAFORICOS
            */
            HashMap<UUID, GrupoSemaforico> gruposSemaforicos = new HashMap<UUID, GrupoSemaforico>();
            anel.getGruposSemaforicos().forEach(grupoSemaforico -> {
                GrupoSemaforico grupoAux = copyPrimitiveFields(grupoSemaforico);
                grupoAux.setAnel(anelClonado);
                anelClonado.addGruposSemaforicos(grupoAux);
                gruposSemaforicos.put(grupoSemaforico.getId(), grupoAux);
            });

            // Rodando novamente para montar a tabela de verdes conflitantes
            anel.getGruposSemaforicos().forEach(grupoSemaforico -> {
                ArrayList<VerdesConflitantes> verdesOrigem = new ArrayList<VerdesConflitantes>();

                grupoSemaforico.getVerdesConflitantesOrigem().forEach(verdesConflitantes -> {
                    VerdesConflitantes verdesAux = copyPrimitiveFields(verdesConflitantes);
                    if (verdesConflitantes.getOrigem() != null) {
                        verdesAux.setOrigem(gruposSemaforicos.get(verdesConflitantes.getOrigem().getId()));
                    }
                    if (verdesConflitantes.getDestino() != null) {
                        verdesAux.setDestino(gruposSemaforicos.get(verdesConflitantes.getDestino().getId()));
                    }
                    verdesOrigem.add(verdesAux);
                });

                GrupoSemaforico gsAux = gruposSemaforicos.get(grupoSemaforico.getId());
                gsAux.setVerdesConflitantesOrigem(verdesOrigem);
            });

        });
        controladorClone.setAneis(aneisClonados);

        // salvar o controlador para salvar os estagios e associa-los ao grupo semaforico
        Ebean.save(controladorClone);

        // ASSOCIACAO ESTAGIO x GRUPO SEMAFORICO
        HashMap<String, Anel> aneisClone = new HashMap<String, Anel>();
        HashMap<String, Estagio> estagiosClone = new HashMap<String, Estagio>();
        HashMap<String, GrupoSemaforico> gruposClone = new HashMap<String, GrupoSemaforico>();

        controladorClone.getAneis().forEach(anel -> {
            aneisClone.put(anel.getIdJson(), anel);
            anel.getEstagios().forEach(estagio -> {
                estagiosClone.put(estagio.getIdJson(), estagio);
            });
            anel.getGruposSemaforicos().forEach(grupoSemaforico -> {
                gruposClone.put(grupoSemaforico.getIdJson(), grupoSemaforico);
            });
        });

        controlador.getAneis().forEach(anel -> {

            // Rodando novamente para montar a estagio grupo semaforico
            anel.getEstagios().forEach(estagio -> {
                estagio.getEstagiosGruposSemaforicos().forEach(estagioGrupoSemaforico -> {
                    EstagioGrupoSemaforico egsAux = copyPrimitiveFields(estagioGrupoSemaforico);
                    Estagio estagioAux = estagiosClone.get(estagioGrupoSemaforico.getEstagio().getIdJson());
                    GrupoSemaforico gsAux = gruposClone.get(estagioGrupoSemaforico.getGrupoSemaforico().getIdJson());
                    egsAux.setEstagio(estagioAux);
                    egsAux.setGrupoSemaforico(gsAux);

                    estagioAux.addEstagioGrupoSemaforico(egsAux);
                    gsAux.addEstagioGrupoSemaforico(egsAux);
                });

                estagio.getOrigemDeTransicoesProibidas().forEach(transicaoProibida -> {
                    TransicaoProibida transicaoProibidaAux = copyPrimitiveFields(transicaoProibida);
                    Estagio estagioOrigemClone = estagiosClone.get(transicaoProibida.getOrigem().getIdJson());
                    transicaoProibidaAux.setOrigem(estagioOrigemClone);
                    estagioOrigemClone.addTransicaoProibidaOrigem(transicaoProibidaAux);
                    Estagio estagioDestinoClone = estagiosClone.get(transicaoProibida.getDestino().getIdJson());
                    transicaoProibidaAux.setDestino(estagioDestinoClone);
                    estagioDestinoClone.addTransicaoProibidaDestino(transicaoProibidaAux);
                    Estagio estagioAlternativoClone = estagiosClone.get(transicaoProibida.getAlternativo().getIdJson());
                    transicaoProibidaAux.setAlternativo(estagioAlternativoClone);
                    estagioAlternativoClone.addTransicaoProibidaAlternativa(transicaoProibidaAux);
                });
            });

            anel.getGruposSemaforicos().forEach(grupoSemaforico -> {

                HashMap<UUID, TabelaEntreVerdes> teAux = new HashMap<UUID, TabelaEntreVerdes>();
                grupoSemaforico.getTabelasEntreVerdes().forEach(tabelaEntreVerdes -> {
                    TabelaEntreVerdes tabelaEntreVerdesAux = copyPrimitiveFields(tabelaEntreVerdes);
                    GrupoSemaforico grupoSemaforicoClone = gruposClone.get(tabelaEntreVerdes.getGrupoSemaforico().getIdJson());
                    tabelaEntreVerdesAux.setGrupoSemaforico(grupoSemaforicoClone);
                    grupoSemaforicoClone.addTabelaEntreVerdes(tabelaEntreVerdesAux);
                    teAux.put(tabelaEntreVerdes.getId(), tabelaEntreVerdesAux);

                });
                grupoSemaforico.getTransicoes().forEach(transicao -> {
                    GrupoSemaforico grupoSemaforicoAux = gruposClone.get(transicao.getGrupoSemaforico().getIdJson());
                    Transicao transicaoAux = copyPrimitiveFields(transicao);
                    transicaoAux.setOrigem(estagiosClone.get(transicao.getOrigem().getIdJson()));
                    transicaoAux.setDestino(estagiosClone.get(transicao.getDestino().getIdJson()));
                    transicaoAux.setGrupoSemaforico(grupoSemaforicoAux);

                    // TODO - O IDJSON do atraso de grupo nao esta sendo carregado via JSON Serializable/Deserializable
                    transicao.getAtrasoDeGrupo().getIdJson();
                    AtrasoDeGrupo atrasoDeGrupoAux = copyPrimitiveFields(transicao.getAtrasoDeGrupo());
                    atrasoDeGrupoAux.setTransicao(transicaoAux);
                    transicaoAux.setAtrasoDeGrupo(atrasoDeGrupoAux);

                    transicao.getTabelaEntreVerdesTransicoes().forEach(tabelaEntreVerdesTransicao -> {
                        TabelaEntreVerdesTransicao tvt = copyPrimitiveFields(tabelaEntreVerdesTransicao);
                        TabelaEntreVerdes te = teAux.get(tabelaEntreVerdesTransicao.getTabelaEntreVerdes().getId());
                        tvt.setTransicao(transicaoAux);
                        tvt.setTabelaEntreVerdes(te);

                        transicaoAux.addTabelaEntreVerdesTransicao(tvt);
                        te.addTabelaEntreVerdesTransicao(tvt);
                    });
                    grupoSemaforicoAux.addTransicao(transicaoAux);
                });
            });

            anel.getDetectores().forEach(detector -> {
                Detector detectorAux = copyPrimitiveFields(detector);
                Anel anelAux = aneisClone.get(detector.getAnel().getIdJson());
                Estagio estagioAux = estagiosClone.get(detector.getEstagio().getIdJson());
                detectorAux.setAnel(anelAux);
                detectorAux.setControlador(controladorClone);
                detectorAux.setEstagio(estagioAux);
                estagioAux.setDetector(detectorAux);
                anelAux.addDetectores(detectorAux);

            });

            // Clone de planos
            VersaoPlano versaoAntiga = anel.getVersaoPlanoAtivoOuConfigurado();
            if (versaoAntiga != null) {
                versaoAntiga.setStatusVersao(StatusVersao.ARQUIVADO);
                versaoAntiga.update();

                Anel anelClonado = aneisClone.get(anel.getIdJson());
                VersaoPlano novaVersao = new VersaoPlano(anelClonado, usuario);
                novaVersao.setAnel(anelClonado);
                novaVersao.setVersaoAnterior(versaoAntiga);

                anel.getPlanos().forEach(plano -> {
                    Plano planoClonado = copyPrimitiveFields(plano);

                    plano.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> {
                        GrupoSemaforicoPlano gspClonado = copyPrimitiveFields(grupoSemaforicoPlano);
                        GrupoSemaforico gsClonado = gruposClone.get(grupoSemaforicoPlano.getGrupoSemaforico().getIdJson());
                        gspClonado.setGrupoSemaforico(gsClonado);
                        gspClonado.setPlano(planoClonado);
                        planoClonado.addGruposSemaforicoPlano(gspClonado);
                    });

                    plano.getEstagiosPlanos().forEach(estagioPlano -> {
                        EstagioPlano estagioPlanoClonado = copyPrimitiveFields(estagioPlano);
                        Estagio estagioClonado = estagiosClone.get(estagioPlano.getEstagio().getIdJson());
                        estagioPlanoClonado.setEstagio(estagioClonado);
                        estagioPlanoClonado.setPlano(planoClonado);
                        planoClonado.addEstagios(estagioPlanoClonado);
                    });

                    planoClonado.setVersaoPlano(novaVersao);
                    novaVersao.addPlano(planoClonado);
                });
                versaoAntiga.update();
                anelClonado.addVersaoPlano(novaVersao);
                Ebean.update(anelClonado);
            }
        });

        // Refazendo associacao estagio dispensavel planos
        controlador.getAneis().forEach(anel -> {
            Anel anelClonado = controladorClone.getAneis().stream().filter(anelClone -> anelClone.getIdJson().equals(anel.getIdJson())).findFirst().orElse(null);
            anel.getPlanos().forEach(plano -> {
                Plano planoClonado = anelClonado.getPlanos().stream().filter(planoClone -> planoClone.getIdJson().equals(plano.getIdJson())).findFirst().orElse(null);
                plano.getEstagiosPlanos().forEach(estagioPlano -> {
                    if (estagioPlano.getEstagioQueRecebeEstagioDispensavel() != null) {
                        setarEstagioQueRecebeDispensavel(planoClonado, estagioPlano);
                    }
                });
            });
        });

        // Clone de Tabela Horária
        VersaoTabelaHoraria versaoAntiga = controlador.getVersaoTabelaHorariaAtivaOuConfigurada();
        if (versaoAntiga != null && versaoAntiga.getTabelaHoraria() != null) {
            TabelaHorario tabelaClonada = copyPrimitiveFields(versaoAntiga.getTabelaHoraria());
            tabelaClonada.setIdJson(UUID.randomUUID().toString());
            VersaoTabelaHoraria novaVersao = new VersaoTabelaHoraria(controladorClone, versaoAntiga.getTabelaHoraria(), tabelaClonada, usuario);
            tabelaClonada.setVersaoTabelaHoraria(novaVersao);

            cloneEventos(versaoAntiga, tabelaClonada);

            versaoAntiga.setStatusVersao(StatusVersao.ARQUIVADO);
            versaoAntiga.update();
            controladorClone.addVersaoTabelaHoraria(novaVersao);
        }

        //Atualizando IDJson de Planos
        atualizarIdJsonPlanos(controladorClone);

        //Atualizando IDJson
        controladorClone.getAneis().forEach(anel -> {
            anel.getDetectores().forEach(detector -> detector.setIdJson(UUID.randomUUID().toString()));
            anel.getGruposSemaforicos().forEach(grupoSemaforico -> {
                grupoSemaforico.getVerdesConflitantesOrigem().forEach(verdesConflitantes -> verdesConflitantes.setIdJson(UUID.randomUUID().toString()));
                grupoSemaforico.getEstagiosGruposSemaforicos().forEach(estagioGrupoSemaforico -> estagioGrupoSemaforico.setIdJson(UUID.randomUUID().toString()));
                grupoSemaforico.getTabelasEntreVerdes().forEach(tabelaEntreVerdes -> tabelaEntreVerdes.setIdJson(UUID.randomUUID().toString()));
                grupoSemaforico.getTransicoes().forEach(transicao -> {
                    transicao.getTabelaEntreVerdesTransicoes().forEach(tabelaEntreVerdesTransicao -> tabelaEntreVerdesTransicao.setIdJson(UUID.randomUUID().toString()));
                    transicao.getAtrasoDeGrupo().setIdJson(UUID.randomUUID().toString());
                    transicao.setIdJson(UUID.randomUUID().toString());
                });
                grupoSemaforico.setIdJson(UUID.randomUUID().toString());
            });
            anel.getEstagios().forEach(estagio -> {
                estagio.getOrigemDeTransicoesProibidas().forEach(transicaoProibida -> transicaoProibida.setIdJson(UUID.randomUUID().toString()));
                estagio.getDestinoDeTransicoesProibidas().forEach(transicaoProibida -> transicaoProibida.setIdJson(UUID.randomUUID().toString()));
                estagio.getAlternativaDeTransicoesProibidas().forEach(transicaoProibida -> transicaoProibida.setIdJson(UUID.randomUUID().toString()));
                estagio.setIdJson(UUID.randomUUID().toString());
            });
        });

        // FIM CLONE CONTROLADOR
        Ebean.update(controladorClone);

        long elapsed = System.nanoTime() - startTime;
        Logger.info(String.format("[CONTROLADOR] - DeepClone: Elapsed time: %d ns (%f seconds)%n", elapsed, elapsed / Math.pow(10, 9)));

        return controladorClone;
    }

    public void deepClonePlanos(Controlador controlador, Usuario usuario) {
        long startTime = System.nanoTime();

        Map<String, GrupoSemaforico> grupos = new HashMap<>();
        Map<String, Estagio> estagios = new HashMap<>();

        controlador.getAneis().forEach(anel -> {
            anel.getGruposSemaforicos().forEach(grupoSemaforico -> {
                grupos.put(grupoSemaforico.getIdJson(), grupoSemaforico);
            });
            anel.getEstagios().forEach(estagio -> {
                estagios.put(estagio.getIdJson(), estagio);
            });
        });

        controlador.getAneis().forEach(anel -> {
            VersaoPlano versaoAntiga = anel.getVersaoPlanoAtivoOuConfigurado();
            if (versaoAntiga != null) {
                versaoAntiga.setStatusVersao(StatusVersao.ARQUIVADO);

                VersaoPlano novaVersao = new VersaoPlano(anel, usuario);
                novaVersao.setVersaoAnterior(versaoAntiga);

                anel.getPlanos().forEach(plano -> {
                    Plano planoAux = copyPrimitiveFields(plano);
                    plano.setVersaoPlano(novaVersao);

                    plano.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> {
                        GrupoSemaforicoPlano gspAux = copyPrimitiveFields(grupoSemaforicoPlano);
                        gspAux.setGrupoSemaforico(grupos.get(grupoSemaforicoPlano.getGrupoSemaforico().getIdJson()));
                        gspAux.setPlano(planoAux);
                        planoAux.addGruposSemaforicoPlano(gspAux);
                    });

                    plano.getEstagiosPlanos().forEach(estagioPlano -> {
                        EstagioPlano estagioPlanoAux = copyPrimitiveFields(estagioPlano);
                        estagioPlanoAux.setEstagio(estagios.get(estagioPlano.getEstagio().getIdJson()));
                        estagioPlanoAux.setPlano(planoAux);
                        planoAux.addEstagios(estagioPlanoAux);
                    });

                    novaVersao.addPlano(planoAux);
                });
                versaoAntiga.update();

                anel.addVersaoPlano(novaVersao);

                // FIM CLONE PLANO
                Ebean.update(anel);
            }
        });

        controlador.getAneis().forEach(anel -> {
            anel.getPlanos().forEach(plano -> {
                plano.getEstagiosPlanos().forEach(estagioPlano -> {
                    if (estagioPlano.getEstagioQueRecebeEstagioDispensavel() != null) {
                        setarEstagioQueRecebeDispensavel(plano, estagioPlano);
                    }
                });
            });
        });

        // Clone de tabela horária
        VersaoTabelaHoraria versaoAntiga = controlador.getVersaoTabelaHorariaAtivaOuConfigurada();
        if (versaoAntiga != null && versaoAntiga.getTabelaHoraria() != null) {
            TabelaHorario tabelaClonada = copyPrimitiveFields(versaoAntiga.getTabelaHoraria());
            tabelaClonada.setIdJson(UUID.randomUUID().toString());
            VersaoTabelaHoraria novaVersao = new VersaoTabelaHoraria(controlador, versaoAntiga.getTabelaHoraria(), tabelaClonada, usuario);
            tabelaClonada.setVersaoTabelaHoraria(novaVersao);

            cloneEventos(versaoAntiga, tabelaClonada);

            versaoAntiga.setStatusVersao(StatusVersao.ARQUIVADO);
            controlador.addVersaoTabelaHoraria(novaVersao);
        }

        //Atualizando IDJson de Planos
        atualizarIdJsonPlanos(controlador);

        controlador.setBloqueado(true);
        Ebean.update(controlador);

        VersaoControlador versaoControlador = controlador.getVersaoControlador();
        versaoControlador.setUsuario(usuario);
        Ebean.update(versaoControlador);

        long elapsed = System.nanoTime() - startTime;
        Logger.info(String.format("[PLANO] - DeepClone: Elapsed time: %d ns (%f seconds)%n", elapsed, elapsed / Math.pow(10, 9)));
    }

    public void deepCloneTabelaHoraria(Controlador controlador, Usuario usuario) {
        long startTime = System.nanoTime();

        VersaoTabelaHoraria versaoAntiga = controlador.getVersaoTabelaHorariaAtivaOuConfigurada();
        if (versaoAntiga != null && versaoAntiga.getTabelaHoraria() != null) {
            TabelaHorario tabelaClonada = copyPrimitiveFields(versaoAntiga.getTabelaHoraria());
            tabelaClonada.setIdJson(UUID.randomUUID().toString());
            VersaoTabelaHoraria novaVersao = new VersaoTabelaHoraria(controlador, versaoAntiga.getTabelaHoraria(), tabelaClonada, usuario);
            tabelaClonada.setVersaoTabelaHoraria(novaVersao);

            cloneEventos(versaoAntiga, tabelaClonada);

            versaoAntiga.setStatusVersao(StatusVersao.ARQUIVADO);
            controlador.addVersaoTabelaHoraria(novaVersao);

            // FIM CLONE TABELA HORARIA
            controlador.setBloqueado(true);
            controlador.setPlanosBloqueado(true);
            Ebean.update(controlador);

            VersaoControlador versaoControlador = controlador.getVersaoControlador();
            versaoControlador.setUsuario(usuario);
            Ebean.update(versaoControlador);
        }

        long elapsed = System.nanoTime() - startTime;
        Logger.info(String.format("[TABELA HORARIO] - DeepClone: Elapsed time: %d ns (%f seconds)%n", elapsed, elapsed / Math.pow(10, 9)));
    }

    private Imagem getImagem(Imagem imagem) {
        if (imagem == null) {
            return null;
        }
        imagem.getFilename();
        Imagem imagemClone = copyPrimitiveFields(imagem);
        imagemClone.save();

        try {
            File appRootPath = provider.get().path();
            File imagemPath = imagem.getPath(appRootPath);
            if (imagemPath.exists()) {
                Files.copy(imagemPath.toPath(), imagemClone.getPath(appRootPath).toPath());

                //Create thumbnail
                Thumbnails.of(imagemClone.getPath(appRootPath))
                    .forceSize(150, 150)
                    .outputFormat("jpg")
                    .toFile(imagemClone.getPath(appRootPath, "thumb"));
            }
        } catch (IOException e) {
            imagemClone.delete();
            Logger.error(e.getMessage(), e);
        }
        return imagemClone;
    }


    private <T> T copyPrimitiveFields(T obj) {
        try {
            T clone = (T) obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(obj) == null || Modifier.isFinal(field.getModifiers()) || field.getClass().equals(UUID.class) || field.getType().equals(DiaDaSemana.class)) {
                    continue;
                }
                if (field.getType().isEnum()) {
                    field.set(clone, Enum.valueOf((Class<Enum>) field.getType(), field.get(obj).toString()));
                }
                if (field.getType().isPrimitive() || field.getType().equals(String.class)
                    || (field.getType().getSuperclass() != null && field.getType().getSuperclass().equals(Number.class))
                    || field.getType().equals(Boolean.class)) {
                    field.set(clone, field.get(obj));
                }
            }

            return (T) clone;
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            return null;
        }
    }

    public ControladorUtil provider(Provider<Application> provider) {
        this.provider = provider;
        return this;
    }

    private void cloneEventos(VersaoTabelaHoraria versaoAntiga, TabelaHorario tabelaClonada) {
        versaoAntiga.getTabelaHoraria().getEventos().forEach(evento -> {
            Evento eventoClonado = copyPrimitiveFields(evento);
            eventoClonado.setIdJson(UUID.randomUUID().toString());
            eventoClonado.setTabelaHorario(tabelaClonada);
            eventoClonado.setDiaDaSemana(evento.getDiaDaSemana());
            eventoClonado.setTipo(evento.getTipo());
            eventoClonado.setHorario(evento.getHorario());
            eventoClonado.setPosicaoPlano(evento.getPosicaoPlano());
            eventoClonado.setData(evento.getData());
            eventoClonado.setAgrupamento(evento.getAgrupamento());
            tabelaClonada.addEvento(eventoClonado);
        });
    }

    private void atualizarIdJsonPlanos(Controlador controlador) {
        controlador.getAneis().forEach(anel -> {
            anel.getPlanos().forEach(plano -> {
                plano.getEstagiosPlanos().forEach(estagioPlano -> estagioPlano.setIdJson(UUID.randomUUID().toString()));
                plano.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> grupoSemaforicoPlano.setIdJson(UUID.randomUUID().toString()));
                plano.setIdJson(UUID.randomUUID().toString());
            });
        });
    }

    private void setarEstagioQueRecebeDispensavel(Plano planoClonado, EstagioPlano estagioPlano) {
        EstagioPlano estagioPlanoClone = planoClonado.getEstagiosPlanos().stream().filter(ep -> ep.getIdJson().equals(estagioPlano.getIdJson())).findFirst().orElse(null);
        EstagioPlano epClonadoQueRecebe = planoClonado.getEstagiosPlanos().stream().filter(ep -> ep.getIdJson().equals(estagioPlano.getEstagioQueRecebeEstagioDispensavel().getIdJson())).findFirst().orElse(null);
        estagioPlanoClone.setEstagioQueRecebeEstagioDispensavel(epClonadoQueRecebe);
        Ebean.update(estagioPlanoClone);
    }
}
