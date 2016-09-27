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
public class ControladorUtil {

    private Provider<Application> provider;

    public Controlador deepClone(Controlador controlador) {

        if (controlador.getStatusControlador() != StatusControlador.ATIVO) {
            throw new IllegalStateException();
        }

        long startTime = System.nanoTime();

        Controlador controladorClone = copyPrimitveFields(controlador);

        /*
         * DADOS BASICOS
         */
        controladorClone.setStatusControlador(StatusControlador.EM_EDICAO);
        controladorClone.setArea(controlador.getArea());
        controladorClone.setModelo(controlador.getModelo());
        controladorClone.setSubarea(controlador.getSubarea());

        if (controlador.getEndereco() != null && controlador.getEndereco().getIdJson() != null) {
            Endereco enderecoAux = copyPrimitveFields(controlador.getEndereco());
            enderecoAux.setControlador(controladorClone);
            controladorClone.setEndereco(enderecoAux);
        }

        /*
         * ANEIS
         */
        ArrayList<Anel> aneisClonados = new ArrayList<Anel>();
        controlador.getAneis().forEach(anel -> {
            Anel anelAux = copyPrimitveFields(anel);
            anelAux.setControlador(controladorClone);

            anelAux.setCroqui(getImagem(anel.getCroqui()));

            if (anel.getEndereco() != null && anel.getEndereco().getIdJson() != null) {
                Endereco enderecoAux = copyPrimitveFields(anel.getEndereco());
                enderecoAux.setAnel(anelAux);
                anelAux.setEndereco(enderecoAux);
            }

            HashMap<UUID, Estagio> estagios = new HashMap<UUID, Estagio>();
            anel.getEstagios().forEach(estagio -> {
                Estagio estagioAux = copyPrimitveFields(estagio);
                estagioAux.setAnel(anelAux);
                estagioAux.setImagem(getImagem(estagio.getImagem()));
                anelAux.addEstagio(estagioAux);
                estagios.put(estagio.getId(), estagioAux);
            });
            aneisClonados.add(anelAux);

            /*
            * GRUPOS SEMAFORICOS
            */
            HashMap<UUID, GrupoSemaforico> gruposSemaforicos = new HashMap<UUID, GrupoSemaforico>();
            anel.getGruposSemaforicos().forEach(grupoSemaforico -> {
                GrupoSemaforico grupoAux = copyPrimitveFields(grupoSemaforico);
                grupoAux.setAnel(anelAux);
                anelAux.addGruposSemaforicos(grupoAux);
                gruposSemaforicos.put(grupoSemaforico.getId(), grupoAux);
            });

            // Rodando novamente para montar a tabela de verdesconfilatnates
            anel.getGruposSemaforicos().forEach(grupoSemaforico -> {
                ArrayList<VerdesConflitantes> verdesOrigem = new ArrayList<VerdesConflitantes>();

                grupoSemaforico.getVerdesConflitantesOrigem().forEach(verdesConflitantes -> {
                    VerdesConflitantes verdesAux = copyPrimitveFields(verdesConflitantes);
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
                    EstagioGrupoSemaforico egsAux = copyPrimitveFields(estagioGrupoSemaforico);
                    Estagio estagioAux = estagiosClone.get(estagioGrupoSemaforico.getEstagio().getIdJson());
                    GrupoSemaforico gsAux = gruposClone.get(estagioGrupoSemaforico.getGrupoSemaforico().getIdJson());
                    egsAux.setEstagio(estagioAux);
                    egsAux.setGrupoSemaforico(gsAux);

                    estagioAux.addEstagioGrupoSemaforico(egsAux);
                    gsAux.addEstagioGrupoSemaforico(egsAux);
                });

                estagio.getOrigemDeTransicoesProibidas().forEach(transicaoProibida -> {
                    TransicaoProibida transicaoProibidaAux = copyPrimitveFields(transicaoProibida);
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
                    TabelaEntreVerdes tabelaEntreVerdesAux = copyPrimitveFields(tabelaEntreVerdes);
                    GrupoSemaforico grupoSemaforicoClone = gruposClone.get(tabelaEntreVerdes.getGrupoSemaforico().getIdJson());
                    tabelaEntreVerdesAux.setGrupoSemaforico(grupoSemaforicoClone);
                    grupoSemaforicoClone.addTabelaEntreVerdes(tabelaEntreVerdesAux);
                    teAux.put(tabelaEntreVerdes.getId(), tabelaEntreVerdesAux);

                });
                grupoSemaforico.getTransicoes().forEach(transicao -> {
                    GrupoSemaforico grupoSemaforicoAux = gruposClone.get(transicao.getGrupoSemaforico().getIdJson());
                    Transicao transicaoAux = copyPrimitveFields(transicao);
                    transicaoAux.setOrigem(estagiosClone.get(transicao.getOrigem().getIdJson()));
                    transicaoAux.setDestino(estagiosClone.get(transicao.getDestino().getIdJson()));
                    transicaoAux.setGrupoSemaforico(grupoSemaforicoAux);

                    // TODO - O IDJSON do atraso de grupo nao esta sendo carregado via JSON Serializable/Deserializable
                    transicao.getAtrasoDeGrupo().getIdJson();
                    AtrasoDeGrupo atrasoDeGrupoAux = copyPrimitveFields(transicao.getAtrasoDeGrupo());
                    atrasoDeGrupoAux.setTransicao(transicaoAux);
                    transicaoAux.setAtrasoDeGrupo(atrasoDeGrupoAux);

                    transicao.getTabelaEntreVerdesTransicoes().forEach(tabelaEntreVerdesTransicao -> {
                        TabelaEntreVerdesTransicao tvt = copyPrimitveFields(tabelaEntreVerdesTransicao);
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
                Detector detectorAux = copyPrimitveFields(detector);
                Anel anelAux = aneisClone.get(detector.getAnel().getIdJson());
                Estagio estagioAux = estagiosClone.get(detector.getEstagio().getIdJson());
                detectorAux.setAnel(anelAux);
                detectorAux.setControlador(controladorClone);
                detectorAux.setEstagio(estagioAux);
                estagioAux.setDetector(detectorAux);
                anelAux.addDetectores(detectorAux);

            });
        });

        //Atualizando IDJson de Planos
        controlador.getAneis().forEach(anel -> {
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

        Map<String, GrupoSemaforico> grupos = new HashMap<String, GrupoSemaforico>();
        Map<String, Estagio> estagios = new HashMap<String, Estagio>();

        controlador.getAneis().stream().forEach(anel -> {
            anel.getGruposSemaforicos().stream().forEach(grupoSemaforico -> {
                grupos.put(grupoSemaforico.getIdJson(), grupoSemaforico);
            });
            anel.getEstagios().stream().forEach(estagio -> {
                estagios.put(estagio.getIdJson(), estagio);
            });
        });

        controlador.getAneis().forEach(anel -> {
            VersaoPlano versaoPlanoOrigem = anel.getVersaoPlanoAtivo();
            if (versaoPlanoOrigem != null) {
                versaoPlanoOrigem.setStatusVersao(StatusVersao.ARQUIVADO);

                VersaoPlano versaoPlano = new VersaoPlano(anel, usuario);
                versaoPlano.setVersaoAnterior(versaoPlanoOrigem);

                anel.getPlanos().forEach(plano -> {
                    Plano planoAux = copyPrimitveFields(plano);
                    plano.setVersaoPlano(versaoPlano);

                    plano.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> {
                        GrupoSemaforicoPlano gspAux = copyPrimitveFields(grupoSemaforicoPlano);
                        gspAux.setGrupoSemaforico(grupos.get(grupoSemaforicoPlano.getGrupoSemaforico().getIdJson()));
                        gspAux.setPlano(planoAux);
                        planoAux.addGruposSemaforicos(gspAux);
                    });

                    plano.getEstagiosPlanos().forEach(estagioPlano -> {
                        EstagioPlano estagioPlanoAux = copyPrimitveFields(estagioPlano);
                        estagioPlanoAux.setEstagio(estagios.get(estagioPlano.getEstagio().getIdJson()));
                        estagioPlanoAux.setPlano(planoAux);
                        planoAux.addEstagios(estagioPlanoAux);
                    });

                    versaoPlano.addPlano(planoAux);
                });
                versaoPlanoOrigem.update();

                anel.addVersaoPlano(versaoPlano);

                // FIM CLONE PLANO
                Ebean.update(anel);
            }
        });

        controlador.getAneis().forEach(anel -> {
            anel.getPlanos().forEach(plano -> {
                plano.getEstagiosPlanos().forEach(estagioPlano -> {
                    if (estagioPlano.getEstagioQueRecebeEstagioDispensavel() != null) {
                        EstagioPlano estagioPlanoAux = plano.getEstagiosPlanos().stream().filter(aux -> aux.getIdJson().equals(estagioPlano.getIdJson())).findFirst().orElse(null);
                        estagioPlanoAux.setEstagioQueRecebeEstagioDispensavel(plano.getEstagiosPlanos().stream().filter(aux -> aux.getIdJson().equals(estagioPlano.getEstagioQueRecebeEstagioDispensavel().getIdJson())).findFirst().orElse(null));
                        Ebean.update(estagioPlanoAux);
                    }
                });
            });
        });

        //Atualizando IDJson de Planos
        controlador.getAneis().forEach(anel -> {
            anel.getPlanos().forEach(plano -> {
                plano.getEstagiosPlanos().forEach(estagioPlano -> estagioPlano.setIdJson(UUID.randomUUID().toString()));
                plano.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> grupoSemaforicoPlano.setIdJson(UUID.randomUUID().toString()));
                plano.setIdJson(UUID.randomUUID().toString());
            });
        });
        Ebean.update(controlador);

        long elapsed = System.nanoTime() - startTime;
        Logger.info(String.format("[PLANO] - DeepClone: Elapsed time: %d ns (%f seconds)%n", elapsed, elapsed / Math.pow(10, 9)));
    }


    public void deepCloneTabelaHoraria(Controlador controlador, Usuario usuario) {
        long startTime = System.nanoTime();

        VersaoTabelaHoraria versaoTabelaHorariaOrigem = controlador.getVersaoTabelaHoraria();
        if (versaoTabelaHorariaOrigem.getTabelaHoraria() != null) {
            TabelaHorario tabelaHorarioAux = copyPrimitveFields(versaoTabelaHorariaOrigem.getTabelaHoraria());
            tabelaHorarioAux.setIdJson(UUID.randomUUID().toString());
            VersaoTabelaHoraria versaoTabelaHoraria = new VersaoTabelaHoraria(controlador, versaoTabelaHorariaOrigem.getTabelaHoraria(), tabelaHorarioAux, usuario);
            tabelaHorarioAux.setVersaoTabelaHoraria(versaoTabelaHoraria);

            versaoTabelaHorariaOrigem.getTabelaHoraria().getEventos().forEach(evento -> {
                Evento eventoAux = copyPrimitveFields(evento);
                eventoAux.setIdJson(UUID.randomUUID().toString());
                eventoAux.setTabelaHorario(tabelaHorarioAux);
                eventoAux.setDiaDaSemana(evento.getDiaDaSemana());
                eventoAux.setTipo(evento.getTipo());
                eventoAux.setHorario(evento.getHorario());
                eventoAux.setPosicaoPlano(evento.getPosicaoPlano());
                eventoAux.setData(evento.getData());
                tabelaHorarioAux.addEventos(eventoAux);
            });

            versaoTabelaHorariaOrigem.setStatusVersao(StatusVersao.ARQUIVADO);
            controlador.addVersaoTabelaHoraria(versaoTabelaHoraria);

            // FIM CLONE TABELA HORARIA
            Ebean.update(controlador);
        }

        long elapsed = System.nanoTime() - startTime;
        Logger.info(String.format("[TABELA HORARIO] - DeepClone: Elapsed time: %d ns (%f seconds)%n", elapsed, elapsed / Math.pow(10, 9)));
    }

    private Imagem getImagem(Imagem imagem) {
        if (imagem == null) {
            return null;
        }
        imagem.getFilename();
        Imagem imagemAux = copyPrimitveFields(imagem);
        imagemAux.save();

        try {
            File appRootPath = provider.get().path();
            Files.copy(imagem.getPath(appRootPath).toPath(), imagemAux.getPath(appRootPath).toPath());

            //Create thumbnail
            Thumbnails.of(imagemAux.getPath(appRootPath))
                    .forceSize(150, 150)
                    .outputFormat("jpg")
                    .toFile(imagemAux.getPath(appRootPath, "thumb"));


        } catch (IOException e) {
            imagemAux.delete();
            Logger.error(e.getMessage(), e);
        }
        return imagemAux;
    }


    private <T> T copyPrimitveFields(T obj) {

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


}

