package models;

import com.google.inject.Singleton;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;
import security.AllowAllAuthenticator;
import security.Authenticator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static play.inject.Bindings.bind;
import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 6/22/16.
 */
public abstract class ControladorTest extends WithApplication {

    private static Cidade cidade;
    private static Area area;
    private static Fabricante fabricante;
    private static ConfiguracaoControlador configuracaoControlador;
    private static ModeloControlador modeloControlador;

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration)
                .overrides(bind(Authenticator.class).to(AllowAllAuthenticator.class).in(Singleton.class))
                .in(Mode.TEST).build();
    }

    protected Controlador getControlador() {
        return new Controlador();
    }

    protected Controlador getControladorDadosBasicos() {

        cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);
        area.save();

        fabricante = new Fabricante();
        fabricante.setNome("Tesc");
        fabricante.save();

        configuracaoControlador = new ConfiguracaoControlador();
        configuracaoControlador.setLimiteAnel(4);
        configuracaoControlador.setLimiteGrupoSemaforico(16);
        configuracaoControlador.setLimiteDetectorPedestre(4);
        configuracaoControlador.setLimiteDetectorVeicular(8);
        configuracaoControlador.setLimiteEstagio(16);
        configuracaoControlador.save();

        modeloControlador = new ModeloControlador();
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setConfiguracao(configuracaoControlador);
        modeloControlador.setDescricao("Modelo 1");
        modeloControlador.save();

        Controlador controlador = getControlador();
        controlador.setLocalizacao("Av Paulista com Bela Cintra");
        controlador.setLatitude(1.0);
        controlador.setLongitude(2.0);
        controlador.setArea(this.area);
        controlador.setModelo(this.modeloControlador);
        controlador.setNumeroSMEE("1234");
        controlador.setNumeroSMEEConjugado1("C1");
        controlador.setNumeroSMEEConjugado2("C2");
        controlador.setNumeroSMEEConjugado3("C3");
        controlador.setFirmware("1.0rc");

        return controlador;
    }

    protected Controlador getControladorAneis() {
        Controlador controlador = getControladorDadosBasicos();
        controlador.save();

        Anel anel1 = controlador.getAneis().get(0);
        anel1.setDescricao("Anel 0");
        anel1.setAtivo(true);
        anel1.setEstagios(Arrays.asList(new Estagio(), new Estagio(), new Estagio(), new Estagio()));

        anel1.setLatitude(1.0);
        anel1.setLongitude(1.0);

        return controlador;
    }

    protected Controlador getControladorAssociacao() {
        Controlador controlador = getControladorAneis();
        controlador.save();

        Anel anelAtivo = controlador.getAneis().stream().filter(anel -> anel.isAtivo()).findFirst().get();

        Estagio estagio1 = anelAtivo.getEstagios().get(0);
        Estagio estagio2 = anelAtivo.getEstagios().get(1);
        Estagio estagio3 = anelAtivo.getEstagios().get(2);
        Estagio estagio4 = anelAtivo.getEstagios().get(3);

        GrupoSemaforico grupoSemaforico1 = anelAtivo.getGruposSemaforicos().get(0);
        grupoSemaforico1.setTipo(TipoGrupoSemaforico.PEDESTRE);
        GrupoSemaforico grupoSemaforico2 = anelAtivo.getGruposSemaforicos().get(1);
        grupoSemaforico2.setTipo(TipoGrupoSemaforico.VEICULAR);


        EstagioGrupoSemaforico estagioGrupoSemaforico1 = new EstagioGrupoSemaforico(estagio1, grupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico2 = new EstagioGrupoSemaforico(estagio2, grupoSemaforico2);
        EstagioGrupoSemaforico estagioGrupoSemaforico3 = new EstagioGrupoSemaforico(estagio3, grupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico4 = new EstagioGrupoSemaforico(estagio4, grupoSemaforico2);

        estagio1.setDemandaPrioritaria(true);
        estagio1.setTempoMaximoPermanencia(100);
        estagio1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        estagio2.setDemandaPrioritaria(false);
        estagio2.setTempoMaximoPermanencia(200);
        estagio2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);

        estagio3.addEstagioGrupoSemaforico(estagioGrupoSemaforico3);
        estagio3.setTempoMaximoPermanenciaAtivado(false);
        estagio4.addEstagioGrupoSemaforico(estagioGrupoSemaforico4);
        estagio4.setTempoMaximoPermanenciaAtivado(false);

        grupoSemaforico1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        grupoSemaforico2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);
        grupoSemaforico1.addEstagioGrupoSemaforico(estagioGrupoSemaforico3);
        grupoSemaforico2.addEstagioGrupoSemaforico(estagioGrupoSemaforico4);

        return controlador;
    }

    protected Controlador getControladorVerdesConflitantes() {
        Controlador controlador = getControladorAssociacao();

        Anel anelAtivo = controlador.getAneis().stream().filter(anel -> !anel.isAtivo()).findFirst().get();
        anelAtivo.setDescricao("Anel 1");
        anelAtivo.setAtivo(Boolean.TRUE);
        anelAtivo.setLatitude(1.0);
        anelAtivo.setLongitude(1.0);

        anelAtivo.setEstagios(Arrays.asList(new Estagio(), new Estagio()));

        Estagio estagio1 = anelAtivo.getEstagios().get(0);
        estagio1.setTempoMaximoPermanencia(60);
        Estagio estagio2 = anelAtivo.getEstagios().get(1);
        estagio2.setTempoMaximoPermanencia(60);

        controlador.save();

        GrupoSemaforico grupoSemaforico3 = anelAtivo.findGrupoSemaforicoByPosicao(3);
        grupoSemaforico3.setTipo(TipoGrupoSemaforico.VEICULAR);
        GrupoSemaforico grupoSemaforico4 = anelAtivo.findGrupoSemaforicoByPosicao(4);
        grupoSemaforico4.setTipo(TipoGrupoSemaforico.VEICULAR);


        EstagioGrupoSemaforico estagioGrupoSemaforico1 = new EstagioGrupoSemaforico(estagio1, grupoSemaforico3);
        estagio1.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        EstagioGrupoSemaforico estagioGrupoSemaforico2 = new EstagioGrupoSemaforico(estagio2, grupoSemaforico4);
        estagio2.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);

        grupoSemaforico3.addEstagioGrupoSemaforico(estagioGrupoSemaforico1);
        grupoSemaforico4.addEstagioGrupoSemaforico(estagioGrupoSemaforico2);

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

    protected Controlador getControladorTransicoesProibidas() {
        Controlador controlador = getControladorVerdesConflitantes();
        controlador.save();

        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Estagio estagio1AnelCom4Estagios = anelCom4Estagios.getEstagios().get(0);
        estagio1AnelCom4Estagios.setDescricao("estagio1AnelCom4Estagios");
        Estagio estagio2AnelCom4Estagios = anelCom4Estagios.getEstagios().get(1);
        estagio2AnelCom4Estagios.setDescricao("estagio2AnelCom4Estagios");
        Estagio estagio3AnelCom4Estagios = anelCom4Estagios.getEstagios().get(2);
        estagio3AnelCom4Estagios.setDescricao("estagio3AnelCom4Estagios");
        Estagio estagio4AnelCom4Estagios = anelCom4Estagios.getEstagios().get(3);
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

    protected Controlador getControladorTabelaDeEntreVerdes() {
        Controlador controlador = getControladorTransicoesProibidas();
        controlador.save();

        for (Anel anel : controlador.getAneis()) {
            for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
                for (Transicao transicao : grupoSemaforico.getTransicoes()) {
                    for (TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao : transicao.getTabelaEntreVerdesTransicoes()) {
                        tabelaEntreVerdesTransicao.setTempoAtrasoGrupo(0);
                        tabelaEntreVerdesTransicao.setTempoVermelhoLimpeza(5);
                        if (grupoSemaforico.isVeicular()) {
                            tabelaEntreVerdesTransicao.setTempoAmarelo(4);
                        } else {
                            tabelaEntreVerdesTransicao.setTempoVermelhoIntermitente(30);
                        }
                    }
                }
            }
        }
        return controlador;
    }

    protected Controlador getControladorAssociacaoDetectores() {
        Controlador controlador = getControladorTabelaDeEntreVerdes();
        controlador.save();

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Estagio estagio = anelCom2Estagios.getEstagios().get(0);
        Detector detector = anelCom2Estagios.getDetectores().get(0);
        detector.setEstagio(estagio);

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

        Detector detector1 = anelCom4Estagios.getDetectores().get(0);
        detector1.setDescricao("D1");
        Detector detector2 = anelCom4Estagios.getDetectores().get(1);
        detector2.setDescricao("D2");
        Detector detector3 = anelCom4Estagios.getDetectores().get(2);
        detector3.setDescricao("D3");
        Detector detector4 = anelCom4Estagios.getDetectores().get(3);
        detector4.setDescricao("D4");

        detector1.setEstagio(estagio1);
        detector2.setEstagio(estagio2);
        detector3.setEstagio(estagio3);
        detector4.setEstagio(estagio4);

        estagio1.setDetector(detector1);
        estagio2.setDetector(detector2);
        estagio3.setDetector(detector3);
        estagio4.setDetector(detector4);

        return controlador;
    }

    protected Controlador getControladorPlanos() {
        Controlador controlador = getControladorAssociacaoDetectores();
        controlador.save();

        Anel anelCom2Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 2).findFirst().get();
        Anel anelCom4Estagios = controlador.getAneis().stream().filter(anel -> anel.isAtivo() && anel.getEstagios().size() == 4).findFirst().get();

        Plano plano1Anel2 = new Plano();
        plano1Anel2.setAnel(anelCom2Estagios);
        anelCom2Estagios.setPlanos(Arrays.asList(plano1Anel2));

        plano1Anel2.setModoOperacao(ModoOperacaoPlano.TEMPO_FIXO_ISOLADO);
        plano1Anel2.setPosicao(1);
        plano1Anel2.setPosicaoTabelaEntreVerde(1);

        Plano plano1Anel4 = new Plano();
        plano1Anel4.setAnel(anelCom4Estagios);
        anelCom4Estagios.setPlanos(Arrays.asList(plano1Anel4));

        plano1Anel4.setModoOperacao(ModoOperacaoPlano.ATUADO);
        plano1Anel4.setPosicao(1);
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

        estagioPlano1Anel4.setTempoVerdeMinimo(20);
        estagioPlano1Anel4.setTempoVerdeMaximo(20);
        estagioPlano1Anel4.setTempoVerdeIntermediario(20);
        estagioPlano1Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano2Anel4.setTempoVerdeMinimo(20);
        estagioPlano2Anel4.setTempoVerdeMaximo(20);
        estagioPlano2Anel4.setTempoVerdeIntermediario(20);
        estagioPlano2Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano3Anel4.setTempoVerdeMinimo(20);
        estagioPlano3Anel4.setTempoVerdeMaximo(20);
        estagioPlano3Anel4.setTempoVerdeIntermediario(20);
        estagioPlano3Anel4.setTempoExtensaoVerde(10.0);

        estagioPlano4Anel4.setTempoVerdeMinimo(20);
        estagioPlano4Anel4.setTempoVerdeMaximo(20);
        estagioPlano4Anel4.setTempoVerdeIntermediario(20);
        estagioPlano4Anel4.setTempoExtensaoVerde(10.0);

        return  controlador;
    }


    // METODOS AUXILIARES

    protected void criarGrupoSemaforicoPlano(Anel anel, Plano plano) {
        plano.setGruposSemaforicosPlanos(null);
        for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
            GrupoSemaforicoPlano grupoPlano = new GrupoSemaforicoPlano();
            grupoPlano.setAtivado(true);
            grupoPlano.setPlano(plano);
            grupoPlano.setGrupoSemaforico(grupoSemaforico);
            plano.addGruposSemaforicos(grupoPlano);
        }
    }

    protected void criarEstagioPlano(Anel anel, Plano plano, int posicoes[]) {
        int i = 0;
        plano.setEstagiosPlanos(null);
        for (Estagio estagio : anel.getEstagios()) {
            EstagioPlano estagioPlano = new EstagioPlano();
            estagioPlano.setPosicao(posicoes[i]);
            estagioPlano.setPlano(plano);
            estagioPlano.setEstagio(estagio);
            plano.addEstagios(estagioPlano);
            i++;
        }
    }

    public abstract void testVazio();

    public abstract void testNoValidationErro();

    public abstract void testORM();

    public abstract void testJSON();

    public abstract void testControllerValidacao();

    public abstract void testController();


}
