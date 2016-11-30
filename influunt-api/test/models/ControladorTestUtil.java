package models;

import org.joda.time.LocalTime;
import utils.RangeUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 8/10/16.
 */
public class ControladorTestUtil {

    private Area area;

    private Subarea subarea;

    private Fabricante fabricante;

    private ModeloControlador modeloControlador;

    private Usuario usuario;

    public ControladorTestUtil(Area area, Subarea subarea, Fabricante fabricante, ModeloControlador modeloControlador) {
        this.area = area;
        this.subarea = subarea;
        this.fabricante = fabricante;
        this.modeloControlador = modeloControlador;
    }

    public ControladorTestUtil(Area area, Fabricante fabricante, ModeloControlador modeloControlador) {
        this.area = area;
        this.subarea = null;
        this.fabricante = fabricante;
        this.modeloControlador = modeloControlador;
    }

    public Controlador getControlador() {
        Controlador controlador = new Controlador();
        controlador.setRangeUtils(RangeUtils.getInstance(null));
        return controlador;
    }

    public Controlador getControladorDadosBasicos() {

        Controlador controlador = getControlador();
        controlador.setArea(this.area);
        controlador.setSubarea(this.subarea);
        controlador.setModelo(this.modeloControlador);
        controlador.setNumeroSMEE("1234");
        controlador.setNumeroSMEEConjugado1("C1");
        controlador.setNumeroSMEEConjugado2("C2");
        controlador.setNumeroSMEEConjugado3("C3");
        controlador.setFirmware("1.0rc");
        controlador.getModelo().setLimiteAnel(4);
        controlador.getModelo().setLimiteGrupoSemaforico(16);
        controlador.getModelo().setLimiteDetectorPedestre(4);
        controlador.getModelo().setLimiteDetectorVeicular(8);
        controlador.getModelo().setLimiteEstagio(16);

        Endereco enderecoPaulista = new Endereco();
        enderecoPaulista.setLocalizacao("Av Paulista");
        enderecoPaulista.setLocalizacao2("Rua Bela Cintra");
        enderecoPaulista.setLatitude(1.0);
        enderecoPaulista.setLongitude(2.0);
        enderecoPaulista.setControlador(controlador);
        controlador.setEndereco(enderecoPaulista);
        controlador.setNomeEndereco("Av Paulista com Bela Cintra");

        ControladorFisico controladorFisico = new ControladorFisico();
        controladorFisico.setArea(controlador.getArea());
        controlador.save();
        controladorFisico.save();

        VersaoControlador versaoControlador = new VersaoControlador(controlador, controladorFisico, getUsuario());
        versaoControlador.setStatusVersao(StatusVersao.EM_CONFIGURACAO);
        versaoControlador.save();

        controlador.setVersaoControlador(versaoControlador);
        controladorFisico.addVersaoControlador(versaoControlador);

        return controlador;
    }

    public Controlador getControladorAneis() {
        Controlador controlador = getControladorDadosBasicos();
        controlador.save();

        Anel anel1 = controlador.getAneis().get(0);
        anel1.setDescricao("Anel 0");
        anel1.setAtivo(true);
        List<Estagio> estagios = Arrays.asList(new Estagio(1), new Estagio(2), new Estagio(3), new Estagio(4));
        estagios.forEach(estagio -> estagio.setTempoMaximoPermanencia(100));
        anel1.setEstagios(estagios);

        Endereco paulista = new Endereco(1.0, 1.0, "Av. Paulista");
        paulista.setAnel(anel1);
        paulista.setAlturaNumerica(145);
        anel1.setEndereco(paulista);

        return controlador;
    }

    public Controlador getControladorGrupoSemaforicos() {
        Controlador controlador = getControladorAneis();
        controlador.save();

        Anel anelAtivo = controlador.getAneis().stream().filter(Anel::isAtivo).findFirst().get();

        GrupoSemaforico grupoSemaforicoPedestre = new GrupoSemaforico();
        grupoSemaforicoPedestre.setAnel(anelAtivo);
        grupoSemaforicoPedestre.setControlador(controlador);
        grupoSemaforicoPedestre.setTipo(TipoGrupoSemaforico.PEDESTRE);
        grupoSemaforicoPedestre.setDescricao("G1");
        grupoSemaforicoPedestre.setPosicao(1);
        grupoSemaforicoPedestre.setTempoVerdeSeguranca(10);
        grupoSemaforicoPedestre.setFaseVermelhaApagadaAmareloIntermitente(false);
        anelAtivo.addGruposSemaforicos(grupoSemaforicoPedestre);
        grupoSemaforicoPedestre.setAnel(anelAtivo);
        controlador.addGruposSemaforicos(grupoSemaforicoPedestre);

        GrupoSemaforico grupoSemaforicoVeicular = new GrupoSemaforico();
        grupoSemaforicoVeicular.setAnel(anelAtivo);
        grupoSemaforicoVeicular.setControlador(controlador);
        grupoSemaforicoVeicular.setTipo(TipoGrupoSemaforico.VEICULAR);
        grupoSemaforicoVeicular.setTempoVerdeSeguranca(10);
        grupoSemaforicoVeicular.setDescricao("G2");
        grupoSemaforicoVeicular.setPosicao(2);
        grupoSemaforicoVeicular.setFaseVermelhaApagadaAmareloIntermitente(true);
        anelAtivo.addGruposSemaforicos(grupoSemaforicoVeicular);
        grupoSemaforicoVeicular.setAnel(anelAtivo);
        controlador.addGruposSemaforicos(grupoSemaforicoVeicular);

        return controlador;
    }

    public Controlador getControladorAssociacao() {
        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Estagio estagio1 = anelCom4Estagios.getEstagios().get(0);
        Estagio estagio2 = anelCom4Estagios.getEstagios().get(1);
        Estagio estagio3 = anelCom4Estagios.getEstagios().get(2);
        Estagio estagio4 = anelCom4Estagios.getEstagios().get(3);

        GrupoSemaforico grupoSemaforico1 = anelCom4Estagios.getGruposSemaforicos().get(0);
        GrupoSemaforico grupoSemaforico2 = anelCom4Estagios.getGruposSemaforicos().get(1);

        EstagioGrupoSemaforico estagioGrupoSemaforico1 = new EstagioGrupoSemaforico(estagio1, grupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico2 = new EstagioGrupoSemaforico(estagio2, grupoSemaforico2);
        EstagioGrupoSemaforico estagioGrupoSemaforico3 = new EstagioGrupoSemaforico(estagio3, grupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico4 = new EstagioGrupoSemaforico(estagio4, grupoSemaforico2);


        estagio1.setDemandaPrioritaria(false);
        estagio1.setTempoMaximoPermanencia(100);
        estagio1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        estagio1.setDescricao("estagio1");
        estagio2.setDemandaPrioritaria(false);
        estagio2.setTempoMaximoPermanencia(200);
        estagio2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);
        estagio2.setDescricao("estagio2");

        estagio3.addEstagioGrupoSemaforico(estagioGrupoSemaforico3);
        estagio3.setTempoMaximoPermanenciaAtivado(false);
        estagio3.setDescricao("estagio3");
        estagio4.addEstagioGrupoSemaforico(estagioGrupoSemaforico4);
        estagio4.setTempoMaximoPermanenciaAtivado(false);
        estagio4.setDescricao("estagio4");

        grupoSemaforico1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        grupoSemaforico2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);
        grupoSemaforico1.addEstagioGrupoSemaforico(estagioGrupoSemaforico3);
        grupoSemaforico2.addEstagioGrupoSemaforico(estagioGrupoSemaforico4);

        Estagio estagioNovo = anelCom2Estagios.getEstagios().get(0);
        Estagio estagioNovo2 = anelCom2Estagios.getEstagios().get(1);
        estagioNovo.setDemandaPrioritaria(true);
        estagioNovo.setTempoVerdeDemandaPrioritaria(100);
        estagioNovo.setTempoMaximoPermanenciaAtivado(false);
        estagioNovo2.setTempoMaximoPermanenciaAtivado(false);

        GrupoSemaforico grupoSemaforicoNovo = anelCom2Estagios.getGruposSemaforicos().get(0);
        GrupoSemaforico grupoSemaforicoNovo2 = anelCom2Estagios.getGruposSemaforicos().get(1);

        EstagioGrupoSemaforico estagioGrupoSemaforicoNovo = new EstagioGrupoSemaforico(estagioNovo, grupoSemaforicoNovo);
        EstagioGrupoSemaforico estagioGrupoSemaforicoNovo2 = new EstagioGrupoSemaforico(estagioNovo2, grupoSemaforicoNovo2);
        estagioNovo.addEstagioGrupoSemaforico(estagioGrupoSemaforicoNovo);
        estagioNovo2.addEstagioGrupoSemaforico(estagioGrupoSemaforicoNovo2);

        grupoSemaforicoNovo.addEstagioGrupoSemaforico(estagioGrupoSemaforicoNovo);
        grupoSemaforicoNovo2.addEstagioGrupoSemaforico(estagioGrupoSemaforicoNovo2);

        return controlador;
    }

    public Controlador getControladorVerdesConflitantes() {
        Controlador controlador = getControladorGrupoSemaforicos();

        Anel anelAtivo = controlador.getAneis().stream().filter(anel -> !anel.isAtivo()).findFirst().get();
        anelAtivo.setDescricao("Anel 1");
        anelAtivo.setAtivo(Boolean.TRUE);
        Endereco paulista = new Endereco(1.0, 1.0, "Av. Paulista");
        paulista.setAnel(anelAtivo);
        paulista.setLocalizacao2("R. Bela Cintra");
        anelAtivo.setEndereco(paulista);

        anelAtivo.setEstagios(Arrays.asList(new Estagio(1), new Estagio(2)));
        anelAtivo.getEstagios().forEach(estagio -> estagio.setTempoMaximoPermanencia(100));

        criarGrupoSemaforico(anelAtivo, TipoGrupoSemaforico.VEICULAR, 3);
        criarGrupoSemaforico(anelAtivo, TipoGrupoSemaforico.VEICULAR, 4);

        controlador.save();

        GrupoSemaforico grupoSemaforico3 = anelAtivo.findGrupoSemaforicoByPosicao(3);
        GrupoSemaforico grupoSemaforico4 = anelAtivo.findGrupoSemaforicoByPosicao(4);

        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();
        GrupoSemaforico grupoSemaforico1 = anelCom4Estagios.findGrupoSemaforicoByPosicao(1);
        GrupoSemaforico grupoSemaforico2 = anelCom4Estagios.findGrupoSemaforicoByPosicao(2);

        grupoSemaforico1.addVerdeConflitante(grupoSemaforico2);
        grupoSemaforico2.addVerdeConflitante(grupoSemaforico1);
        grupoSemaforico3.addVerdeConflitante(grupoSemaforico4);
        grupoSemaforico4.addVerdeConflitante(grupoSemaforico3);

        grupoSemaforico1.setDescricao("G1");
        grupoSemaforico2.setDescricao("G2");
        grupoSemaforico3.setDescricao("G3");
        grupoSemaforico4.setDescricao("G4");

        return controlador;
    }

    public Controlador getControladorTransicoesProibidas() {
        Controlador controlador = getControladorAssociacao();
        controlador.save();

        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Estagio estagio1AnelCom4Estagios = anelCom4Estagios.getEstagios().stream().filter(estagio -> estagio.getPosicao().equals(1)).findAny().get();
        estagio1AnelCom4Estagios.setDescricao("estagio1AnelCom4Estagios");
        Estagio estagio2AnelCom4Estagios = anelCom4Estagios.getEstagios().stream().filter(estagio -> estagio.getPosicao().equals(2)).findAny().get();
        estagio2AnelCom4Estagios.setDescricao("estagio2AnelCom4Estagios");
        Estagio estagio3AnelCom4Estagios = anelCom4Estagios.getEstagios().stream().filter(estagio -> estagio.getPosicao().equals(3)).findAny().get();
        estagio3AnelCom4Estagios.setDescricao("estagio3AnelCom4Estagios");
        Estagio estagio4AnelCom4Estagios = anelCom4Estagios.getEstagios().stream().filter(estagio -> estagio.getPosicao().equals(4)).findAny().get();
        estagio4AnelCom4Estagios.setDescricao("estagio4AnelCom4Estagios");

        TransicaoProibida transicaoProibida = new TransicaoProibida();
        transicaoProibida.setOrigem(estagio1AnelCom4Estagios);
        transicaoProibida.setDestino(estagio2AnelCom4Estagios);
        transicaoProibida.setAlternativo(estagio4AnelCom4Estagios);

        TransicaoProibida transicaoProibidaEstagio1ComEstagio3 = new TransicaoProibida();
        transicaoProibidaEstagio1ComEstagio3.setOrigem(estagio1AnelCom4Estagios);
        transicaoProibidaEstagio1ComEstagio3.setDestino(estagio3AnelCom4Estagios);
        transicaoProibidaEstagio1ComEstagio3.setAlternativo(estagio4AnelCom4Estagios);


        estagio1AnelCom4Estagios.setOrigemDeTransicoesProibidas(Arrays.asList(transicaoProibida, transicaoProibidaEstagio1ComEstagio3));
        estagio2AnelCom4Estagios.setDestinoDeTransicoesProibidas(Arrays.asList(transicaoProibida));
        estagio3AnelCom4Estagios.setDestinoDeTransicoesProibidas(Arrays.asList(transicaoProibidaEstagio1ComEstagio3));
        estagio4AnelCom4Estagios.setAlternativaDeTransicoesProibidas(Arrays.asList(transicaoProibida, transicaoProibidaEstagio1ComEstagio3));


        return controlador;
    }

    public Controlador getControladorAtrasoDeGrupo() {
        Controlador controlador = getControladorTabelaDeEntreVerdes();
        controlador.save();

        for (Anel anel : controlador.getAneis()) {
            for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
                for (Transicao transicao : grupoSemaforico.getTransicoes()) {
                    AtrasoDeGrupo atrasoDeGrupo = new AtrasoDeGrupo(2);
                    atrasoDeGrupo.setIdJson(UUID.randomUUID().toString());
                    atrasoDeGrupo.setTransicao(transicao);
                    transicao.setAtrasoDeGrupo(atrasoDeGrupo);
                }
            }
        }
        return controlador;
    }

    public Controlador getControladorTabelaDeEntreVerdes() {
        Controlador controlador = getControladorTransicoesProibidas();
        controlador.save();

        for (Anel anel : controlador.getAneis()) {
            for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
                for (Transicao transicao : grupoSemaforico.getTransicoesComPerdaDePassagem()) {
                    for (TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao : transicao.getTabelaEntreVerdesTransicoes()) {
                        tabelaEntreVerdesTransicao.setTempoVermelhoLimpeza(5);
                        if (grupoSemaforico.isVeicular()) {
                            tabelaEntreVerdesTransicao.setTempoAmarelo(4);
                        } else {
                            tabelaEntreVerdesTransicao.setTempoVermelhoIntermitente(30);
                        }
                    }
                }

                grupoSemaforico.getEstagiosGruposSemaforicos().forEach(estagioGrupoSemaforico -> {
                    Estagio origem = estagioGrupoSemaforico.getEstagio();
                    Transicao t = grupoSemaforico.getTransicoes().stream().filter(transicao -> origem.equals(transicao.getOrigem())).findFirst().get();
                    t.setModoIntermitenteOuApagado(true);
                });
            }
        }
        return controlador;
    }

    public Controlador getControladorAssociacaoDetectores() {
        Controlador controlador = getControladorAtrasoDeGrupo();
        controlador.save();

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Estagio estagio = anelCom2Estagios.getEstagios().get(0);

        criarDetector(anelCom2Estagios, TipoDetector.VEICULAR, 1, false);

        Detector detector = anelCom2Estagios.getDetectores().get(0);
        detector.setEstagio(estagio);
        estagio.setDetector(detector);

        Estagio estagio1 = anelCom4Estagios.getEstagios().get(0);
        estagio1.setDescricao("E1");
        estagio1.setTempoMaximoPermanencia(60);
        Estagio estagio2 = anelCom4Estagios.getEstagios().get(1);
        estagio2.setDescricao("E2");
        estagio2.setTempoMaximoPermanencia(60);
        Estagio estagio3 = anelCom4Estagios.getEstagios().get(2);
        estagio3.setDescricao("E3");
        estagio3.setTempoMaximoPermanencia(60);
        Estagio estagio4 = anelCom4Estagios.getEstagios().get(3);
        estagio4.setDescricao("E4");
        estagio4.setTempoMaximoPermanencia(60);

        criarDetector(anelCom4Estagios, TipoDetector.PEDESTRE, 1, false);
        criarDetector(anelCom4Estagios, TipoDetector.PEDESTRE, 2, false);
        criarDetector(anelCom4Estagios, TipoDetector.VEICULAR, 3, false);

        Detector detector1 = anelCom4Estagios.getDetectores().get(0);
        detector1.setDescricao("D1");
        Detector detector2 = anelCom4Estagios.getDetectores().get(1);
        detector2.setDescricao("D2");
        Detector detector3 = anelCom4Estagios.getDetectores().get(2);
        detector3.setDescricao("D3");

        detector1.setEstagio(estagio1);
        estagio1.setDetector(detector1);

        detector2.setEstagio(estagio3);
        estagio3.setDetector(detector2);

        detector3.setEstagio(estagio2);
        estagio2.setDetector(detector3);

        return controlador;
    }

    public Controlador getControladorPlanos() {
        Controlador controlador = getControladorAssociacaoDetectores();
        controlador.save();

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        VersaoPlano versaoPlanoAnel2Estagios = new VersaoPlano(anelCom2Estagios, getUsuario());
        versaoPlanoAnel2Estagios.setStatusVersao(StatusVersao.ATIVO);
        anelCom2Estagios.addVersaoPlano(versaoPlanoAnel2Estagios);
        versaoPlanoAnel2Estagios.save();

        VersaoPlano versaoPlanoAnel4Estagios = new VersaoPlano(anelCom4Estagios, getUsuario());
        versaoPlanoAnel4Estagios.setStatusVersao(StatusVersao.ATIVO);
        anelCom4Estagios.addVersaoPlano(versaoPlanoAnel4Estagios);
        versaoPlanoAnel4Estagios.save();

        Plano plano1Anel2 = new Plano();
        versaoPlanoAnel2Estagios.addPlano(plano1Anel2);
        plano1Anel2.setVersaoPlano(versaoPlanoAnel2Estagios);

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);
        plano1Anel2.setPosicao(1);
        plano1Anel2.setDescricao("Principal");
        plano1Anel2.setPosicaoTabelaEntreVerde(1);

        Plano plano1Anel4 = new Plano();
        versaoPlanoAnel4Estagios.addPlano(plano1Anel4);
        plano1Anel4.setVersaoPlano(versaoPlanoAnel4Estagios);

        Estagio estagio = anelCom4Estagios.getEstagios().stream().filter(estagio1 -> estagio1.getPosicao().equals(4)).findFirst().get();
        criarDetector(anelCom4Estagios, TipoDetector.VEICULAR, 4, false);
        Detector detector = anelCom4Estagios.getDetectores().get(3);
        detector.setDescricao("D4");
        detector.setEstagio(estagio);
        estagio.setDetector(detector);

        plano1Anel4.setModoOperacao(ModoOperacaoPlano.ATUADO);
        plano1Anel4.setPosicao(1);
        plano1Anel4.setDescricao("Principal");
        plano1Anel4.setPosicaoTabelaEntreVerde(1);

        criarGrupoSemaforicoPlano(anelCom2Estagios, plano1Anel2);
        criarGrupoSemaforicoPlano(anelCom4Estagios, plano1Anel4);

        criarEstagioPlano(anelCom2Estagios, plano1Anel2, new int[]{2, 1});
        criarEstagioPlano(anelCom4Estagios, plano1Anel4, new int[]{1, 4, 3, 2});

        EstagioPlano estagioPlano1Anel2 = plano1Anel2.getEstagiosPlanos().get(0);
        EstagioPlano estagioPlano2Anel2 = plano1Anel2.getEstagiosPlanos().get(1);

        EstagioPlano estagioPlano1Anel4 = plano1Anel4.getEstagiosPlanos().get(0);
        EstagioPlano estagioPlano2Anel4 = plano1Anel4.getEstagiosPlanos().get(1);
        EstagioPlano estagioPlano3Anel4 = plano1Anel4.getEstagiosPlanos().get(2);
        EstagioPlano estagioPlano4Anel4 = plano1Anel4.getEstagiosPlanos().get(3);

        estagioPlano1Anel2.setTempoVerde(21);
        plano1Anel2.setTempoCiclo(60);
        estagioPlano2Anel2.setTempoVerde(21);

        estagioPlano1Anel4.setTempoVerdeMinimo(15);
        estagioPlano1Anel4.setTempoVerdeMaximo(20);
        estagioPlano1Anel4.setTempoVerdeIntermediario(17);
        estagioPlano1Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(15);
        estagioPlano2Anel4.setTempoVerdeMaximo(20);
        estagioPlano2Anel4.setTempoVerdeIntermediario(17);
        estagioPlano2Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(15);
        estagioPlano3Anel4.setTempoVerdeMaximo(20);
        estagioPlano3Anel4.setTempoVerdeIntermediario(17);
        estagioPlano3Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(15);
        estagioPlano4Anel4.setTempoVerdeMaximo(20);
        estagioPlano4Anel4.setTempoVerdeIntermediario(17);
        estagioPlano4Anel4.setTempoExtensaoVerde(10.0);

        return controlador;
    }

    public Controlador getControladorTabelaHorario() {
        Controlador controlador = getControladorPlanos();
        controlador.save();

        TabelaHorario tabelaHoraria = new TabelaHorario();
        VersaoTabelaHoraria versaoTabelaHoraria = new VersaoTabelaHoraria(controlador, null, tabelaHoraria, getUsuario());
        versaoTabelaHoraria.setStatusVersao(StatusVersao.ATIVO);
        tabelaHoraria.setVersaoTabelaHoraria(versaoTabelaHoraria);
        controlador.addVersaoTabelaHoraria(versaoTabelaHoraria);

        Evento evento = new Evento();
        evento.setTabelaHorario(tabelaHoraria);
        tabelaHoraria.addEvento(evento);
        evento.setTipo(TipoEvento.NORMAL);
        evento.setPosicao(1);
        evento.setDiaDaSemana(DiaDaSemana.DOMINGO);
        evento.setHorario(LocalTime.parse("10:00:00"));
        evento.setPosicaoPlano(1);

        Evento evento2 = new Evento();
        evento2.setTabelaHorario(tabelaHoraria);
        tabelaHoraria.addEvento(evento2);
        evento2.setTipo(TipoEvento.ESPECIAL_RECORRENTE);
        evento2.setPosicao(2);
        evento2.setData(new Date());
        evento2.setDiaDaSemana(DiaDaSemana.SEGUNDA_A_SABADO);
        evento2.setHorario(LocalTime.parse("18:00:00"));
        evento2.setPosicaoPlano(1);

        Evento evento3 = new Evento();
        evento3.setTabelaHorario(tabelaHoraria);
        tabelaHoraria.addEvento(evento3);
        evento3.setTipo(TipoEvento.ESPECIAL_NAO_RECORRENTE);
        evento3.setPosicao(3);
        evento3.setData(new Date());
        evento3.setDiaDaSemana(DiaDaSemana.DOMINGO);
        evento3.setHorario(LocalTime.parse("08:00:00"));
        evento3.setPosicaoPlano(1);

        controlador.setStatusVersao(StatusVersao.CONFIGURADO);

        return controlador;
    }

    public Controlador getControladorAgrupamentos() {
        Controlador controlador = getControladorTabelaHorario();
        controlador.save();

        Agrupamento agrupamento = new Agrupamento();
        agrupamento.setTipo(TipoAgrupamento.ROTA);
        agrupamento.setNome("Rota 1");
        agrupamento.setNumero("1");
        agrupamento.setDiaDaSemana(DiaDaSemana.DOMINGO);
        agrupamento.setHorario(LocalTime.MIDNIGHT);
        agrupamento.setPosicaoPlano(1);
        controlador.getAneis()
            .stream()
            .filter(Anel::isAtivo)
            .forEach(agrupamento::addAnel);
        agrupamento.save();

        return controlador;
    }

    // METODOS AUXILIARES

    protected void criarGrupoSemaforicoPlano(Anel anel, Plano plano) {
        plano.setGruposSemaforicosPlanos(null);
        for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
            GrupoSemaforicoPlano grupoPlano = new GrupoSemaforicoPlano();
            grupoPlano.setAtivado(true);
            grupoPlano.setPlano(plano);
            grupoPlano.setGrupoSemaforico(grupoSemaforico);
            plano.addGruposSemaforicoPlano(grupoPlano);
        }
    }

    protected void criarEstagioPlano(Anel anel, Plano plano, int posicoes[]) {
        int i = 0;
        plano.setEstagiosPlanos(null);
        for (Estagio estagio : anel.ordenarEstagiosPorPosicao()) {
            EstagioPlano estagioPlano = new EstagioPlano();
            estagioPlano.setPosicao(posicoes[i]);
            estagioPlano.setPlano(plano);
            estagioPlano.setEstagio(estagio);
            plano.addEstagios(estagioPlano);
            estagio.addEstagioPlano(estagioPlano);
            i++;
        }
    }

    protected void criarGrupoSemaforico(Anel anel, TipoGrupoSemaforico tipo, Integer posicao) {
        GrupoSemaforico grupoSemaforico = new GrupoSemaforico();
        grupoSemaforico.setAnel(anel);
        grupoSemaforico.setControlador(anel.getControlador());
        grupoSemaforico.setTipo(tipo);
        grupoSemaforico.setPosicao(posicao);
        grupoSemaforico.setTempoVerdeSeguranca(10);
        anel.addGruposSemaforicos(grupoSemaforico);
        anel.getControlador().addGruposSemaforicos(grupoSemaforico);
    }

    protected void criarDetector(Anel anel, TipoDetector tipo, Integer posicao, Boolean monitorado) {
        Detector detector = new Detector();
        detector.setAnel(anel);
        detector.setControlador(anel.getControlador());
        detector.setTipo(tipo);
        detector.setPosicao(posicao);
        detector.setMonitorado(monitorado);
        anel.addDetectores(detector);
    }


    public Usuario getUsuario() {
        Usuario usuario = Usuario.find.where().eq("login", "admin").findUnique();
        if (usuario == null) {
            usuario = new Usuario();
            usuario.setNome("Admin");
            usuario.setLogin("admin");
            usuario.setSenha("1234");
            usuario.setRoot(false);
            usuario.setEmail("root@influunt.com.br");

            usuario.save();
        }

        return usuario;
    }
}
