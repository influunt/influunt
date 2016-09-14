package integracao;

import config.WithInfluuntApplicationNoAuthentication;
import models.*;
import org.joda.time.LocalTime;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by lesiopinheiro on 8/10/16.
 */
public class ControladorHelper extends WithInfluuntApplicationNoAuthentication {
    private Cidade cidade;

    private Area area;

    private Subarea subarea;

    private Fabricante fabricante;

    private ModeloControlador modeloControlador;

    private Controlador controlador;

    public Controlador getControlador() {
        controlador = new Controlador();

        criaRelacaoDadosBasicos();

        setDadosBasicos();
        setDadosAneis();
        setDadosGruposSemaforicos();
        setDadosVerdesConflitantes();
        setDadosAssociacaoEstagioGrupoSemaforico();
        setDadosTransicoesProibidas();
        setDadosAtrasoDeGrupo();
        setDadosTabelaEntreVerdes();
        setDadosAssociacaoDetectores();

        controlador.setStatusControlador(StatusControlador.CONFIGURADO);

        controlador.save();

        return controlador;
    }

    private void criaRelacaoDadosBasicos() {
        cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.save();

        area = new Area();
        area.setCidade(cidade);
        area.setDescricao(1);
        area.save();

        subarea = new Subarea();
        subarea.setArea(area);
        subarea.setNome("Paulista");
        subarea.setNumero(456);
        subarea.save();

        fabricante = new Fabricante();
        fabricante.setNome("Tesc");
        fabricante.save();

        modeloControlador = new ModeloControlador();
        modeloControlador.setFabricante(fabricante);
        modeloControlador.setDescricao("Modelo 1");
        modeloControlador.setLimiteAnel(4);
        modeloControlador.setLimiteGrupoSemaforico(16);
        modeloControlador.setLimiteDetectorPedestre(4);
        modeloControlador.setLimiteDetectorVeicular(8);
        modeloControlador.setLimiteEstagio(16);
        modeloControlador.setLimitePlanos(16);
        modeloControlador.setLimiteTabelasEntreVerdes(2);
        modeloControlador.save();
    }

    public void setDadosBasicos() {
        controlador.setArea(area);
        controlador.setSubarea(subarea);
        controlador.setModelo(modeloControlador);
        controlador.setNumeroSMEE("1234");
        controlador.setNumeroSMEEConjugado1("1235");
        controlador.setFirmware("1.0rc");
        controlador.setNomeEndereco("Av Paulista com Bela Cintra");

        Endereco enderecoPaulista = new Endereco();
        enderecoPaulista.setLocalizacao("Av Paulista");
        enderecoPaulista.setLocalizacao2("Rua Bela Cintra");
        enderecoPaulista.setLatitude(1.0);
        enderecoPaulista.setLongitude(2.0);
        enderecoPaulista.setControlador(controlador);

        controlador.setEndereco(enderecoPaulista);
        ControladorFisico controladorFisico = new ControladorFisico();
        VersaoControlador versaoControlador = new VersaoControlador(controlador, controladorFisico, getUsuario());
        controladorFisico.addVersaoControlador(versaoControlador);
        controladorFisico.setArea(controlador.getArea());
        controlador.setVersaoControlador(versaoControlador);
        controlador.save();
        controladorFisico.save();

    }

    private Anel getAnel(Integer posicao) {
        return controlador.getAneis().stream().filter(anel -> anel.getPosicao().equals(posicao)).findAny().get();
    }

    public void setDadosAneis() {
        Anel anel = getAnel(1);
        anel.setDescricao("Av. Paulista com Haddock Lobo");
        anel.setPosicao(1);
        anel.setAtivo(true);
        List<Estagio> estagios = Arrays.asList(new Estagio(1), new Estagio(2), new Estagio(3));
        anel.setEstagios(estagios);

        Endereco haddock = new Endereco(1.0, 1.0, "Av. Paulista com Haddock Lobo");
        haddock.setAnel(anel);
        haddock.setAlturaNumerica(145);
        anel.setEndereco(haddock);

        anel = getAnel(2);
        anel.setDescricao("Av. Paulista com Haddock Lobo");
        anel.setPosicao(2);
        anel.setAtivo(true);
        List<Estagio> estagios2 = Arrays.asList(new Estagio(1), new Estagio(2), new Estagio(3));
        anel.setEstagios(estagios2);

        Endereco augusta = new Endereco(1.0, 1.0, "Av. Paulista com Rua Augusta");
        augusta.setAnel(anel);
        augusta.setAlturaNumerica(145);
        anel.setEndereco(augusta);

        controlador.save();
    }

    private GrupoSemaforico criaGrupoSemaforico(Anel anel, TipoGrupoSemaforico tipo, Integer posicao) {
        GrupoSemaforico grupoSemaforico = new GrupoSemaforico();
        grupoSemaforico.setAnel(anel);
        grupoSemaforico.setControlador(controlador);
        grupoSemaforico.setTipo(tipo);
        grupoSemaforico.setDescricao("G" + posicao);
        grupoSemaforico.setPosicao(posicao);
        if (tipo.equals(TipoGrupoSemaforico.PEDESTRE)) {
            grupoSemaforico.setTempoVerdeSeguranca(4);
            grupoSemaforico.setFaseVermelhaApagadaAmareloIntermitente(false);
        } else {
            grupoSemaforico.setTempoVerdeSeguranca(10);
            grupoSemaforico.setFaseVermelhaApagadaAmareloIntermitente(true);
        }
        anel.addGruposSemaforicos(grupoSemaforico);
        controlador.addGruposSemaforicos(grupoSemaforico);
        return grupoSemaforico;
    }

    public void setDadosGruposSemaforicos() {
        Anel anel = getAnel(1);

        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 1);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 2);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 3);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 4);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 5);

        anel = getAnel(2);

        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 6);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 7);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 8);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 9);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 10);

        controlador.save();
    }

    private void criarVerdeConflitante(Anel anel, Integer origem, Integer destino) {
        GrupoSemaforico grupoSemaforicoOrigem = anel.findGrupoSemaforicoByPosicao(origem);
        GrupoSemaforico grupoSemaforicoDestino = anel.findGrupoSemaforicoByPosicao(destino);

        grupoSemaforicoOrigem.addVerdeConflitante(grupoSemaforicoDestino);
        grupoSemaforicoDestino.addVerdeConflitante(grupoSemaforicoOrigem);
    }

    public void setDadosVerdesConflitantes() {
        Anel anel = getAnel(1);

        criarVerdeConflitante(anel, 1, 2);
        criarVerdeConflitante(anel, 1, 3);
        criarVerdeConflitante(anel, 1, 4);
        criarVerdeConflitante(anel, 2, 3);
        criarVerdeConflitante(anel, 2, 5);

        anel = getAnel(2);

        criarVerdeConflitante(anel, 6, 7);
        criarVerdeConflitante(anel, 6, 8);
        criarVerdeConflitante(anel, 6, 9);
        criarVerdeConflitante(anel, 7, 8);
        criarVerdeConflitante(anel, 7, 10);

        controlador.save();
    }

    private EstagioGrupoSemaforico criarAssociacaoEstagioGrupoSemaforico(Anel anel, Integer posicaoEstagio, Integer posicaoGrupoSemaforico) {
        Estagio estagio = anel.findEstagioByPosicao(posicaoEstagio);
        GrupoSemaforico grupoSemaforico = anel.findGrupoSemaforicoByPosicao(posicaoGrupoSemaforico);

        EstagioGrupoSemaforico estagioGrupoSemaforico = new EstagioGrupoSemaforico(estagio, grupoSemaforico);
        estagio.setDemandaPrioritaria(false);
        estagio.setTempoMaximoPermanencia(60);

        estagio.addEstagioGrupoSemaforico(estagioGrupoSemaforico);
        grupoSemaforico.addEstagioGrupoSemaforico(estagioGrupoSemaforico);

        return estagioGrupoSemaforico;
    }

    public void setDadosAssociacaoEstagioGrupoSemaforico() {
        Anel anel = getAnel(1);
        EstagioGrupoSemaforico estagioGrupoSemaforico;

        criarAssociacaoEstagioGrupoSemaforico(anel, 1, 1);
        criarAssociacaoEstagioGrupoSemaforico(anel, 1, 5);

        criarAssociacaoEstagioGrupoSemaforico(anel, 2, 3);
        criarAssociacaoEstagioGrupoSemaforico(anel, 2, 4);
        criarAssociacaoEstagioGrupoSemaforico(anel, 2, 5);

        criarAssociacaoEstagioGrupoSemaforico(anel, 3, 2);
        criarAssociacaoEstagioGrupoSemaforico(anel, 3, 4);

        anel = getAnel(2);

        estagioGrupoSemaforico = criarAssociacaoEstagioGrupoSemaforico(anel, 1, 6);
        estagioGrupoSemaforico.getEstagio().setTempoMaximoPermanenciaAtivado(false);
        criarAssociacaoEstagioGrupoSemaforico(anel, 1, 10);

        criarAssociacaoEstagioGrupoSemaforico(anel, 2, 7);
        criarAssociacaoEstagioGrupoSemaforico(anel, 2, 9);

        estagioGrupoSemaforico = criarAssociacaoEstagioGrupoSemaforico(anel, 3, 8);
        estagioGrupoSemaforico.getEstagio().setDemandaPrioritaria(true);
        criarAssociacaoEstagioGrupoSemaforico(anel, 3, 9);
        criarAssociacaoEstagioGrupoSemaforico(anel, 3, 10);

        controlador.save();
    }

    private void criarTransicaoProibida(Anel anel, Integer posicaoOrigem, Integer posicaoDestino, Integer posicaoAlternativo) {
        Estagio origem = anel.findEstagioByPosicao(posicaoOrigem);
        Estagio destino = anel.findEstagioByPosicao(posicaoDestino);
        Estagio alternativo = anel.findEstagioByPosicao(posicaoAlternativo);

        TransicaoProibida transicaoProibida = new TransicaoProibida();
        transicaoProibida.setOrigem(origem);
        transicaoProibida.setDestino(destino);
        transicaoProibida.setAlternativo(alternativo);

        origem.addTransicaoProibidaOrigem(transicaoProibida);
        destino.addTransicaoProibidaDestino(transicaoProibida);
        alternativo.addTransicaoProibidaAlternativa(transicaoProibida);
    }

    public void setDadosTransicoesProibidas() {
        Anel anel = getAnel(1);
        criarTransicaoProibida(anel, 1, 3, 1);
        criarTransicaoProibida(anel, 2, 1, 3);
        criarTransicaoProibida(anel, 3, 2, 1);

        controlador.save();
    }

    private void criarAtrasoDeGrupo() {
        for (Anel anel : controlador.getAneis()) {
            for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
                for (Transicao transicao : grupoSemaforico.getTransicoes()) {
                    AtrasoDeGrupo atrasoDeGrupo = new AtrasoDeGrupo(0);
                    atrasoDeGrupo.setIdJson(UUID.randomUUID().toString());
                    atrasoDeGrupo.setTransicao(transicao);
                    transicao.setAtrasoDeGrupo(atrasoDeGrupo);
                }
            }
        }
    }

    private void setAtrasoDeGrupo(Anel anel, Integer posicaoGrupo, Integer posicaoOrigem, Integer posicaoDestino, Integer tempoAtrasoGrupo) {
        GrupoSemaforico grupoSemaforico = anel.findGrupoSemaforicoByPosicao(posicaoGrupo);
        Estagio origem = anel.findEstagioByPosicao(posicaoOrigem);
        Estagio destino = anel.findEstagioByPosicao(posicaoDestino);

        Transicao transicao = grupoSemaforico.getTransicoes().stream().filter(transicao1 -> transicao1.getOrigem().equals(origem) && transicao1.getDestino().equals(destino)).findAny().get();
        AtrasoDeGrupo atrasoDeGrupo = transicao.getAtrasoDeGrupo();
        atrasoDeGrupo.setAtrasoDeGrupo(tempoAtrasoGrupo);
    }

    public void setDadosAtrasoDeGrupo() {
        criarAtrasoDeGrupo();
        controlador.save();

        Anel anel = getAnel(1);
        setAtrasoDeGrupo(anel, 2, 3, 1, 2);
        setAtrasoDeGrupo(anel, 1, 1, 2, 2);

        controlador.save();
    }

    private void setEntreVerde(Anel anel, Integer posicaoGrupo, Integer posicaoOrigem, Integer posicaoDestino, Integer tempoAmarelo, Integer tempoVermelho) {
        GrupoSemaforico grupoSemaforico = anel.findGrupoSemaforicoByPosicao(posicaoGrupo);
        Estagio origem = anel.findEstagioByPosicao(posicaoOrigem);
        Estagio destino = anel.findEstagioByPosicao(posicaoDestino);
        Transicao transicao = grupoSemaforico.getTransicoes().stream().filter(transicao1 -> transicao1.getOrigem().equals(origem) && transicao1.getDestino().equals(destino)).findAny().get();
        TabelaEntreVerdesTransicao tabelaEntreVerdesTransicao = transicao.getTabelaEntreVerdesTransicoes().get(0);
        if (grupoSemaforico.isVeicular()) {
            tabelaEntreVerdesTransicao.setTempoAmarelo(tempoAmarelo);
        } else {
            tabelaEntreVerdesTransicao.setTempoVermelhoIntermitente(tempoAmarelo);
        }
        tabelaEntreVerdesTransicao.setTempoVermelhoLimpeza(tempoVermelho);
    }

    public void setDadosTabelaEntreVerdes() {
        Anel anel = getAnel(1);
        setEntreVerde(anel, 1, 1, 2, 3, 3);
        setEntreVerde(anel, 5, 1, 2, 3, 3);
        setEntreVerde(anel, 3, 2, 3, 5, 3);
        setEntreVerde(anel, 4, 2, 3, 7, 3);
        setEntreVerde(anel, 5, 2, 3, 5, 3);
        setEntreVerde(anel, 2, 3, 1, 3, 3);
        setEntreVerde(anel, 4, 3, 1, 5, 3);

        anel = getAnel(2);
        setEntreVerde(anel, 6, 1, 2, 3, 5);
        setEntreVerde(anel, 6, 1, 3, 3, 5);
        setEntreVerde(anel, 10, 1, 2, 3, 5);
        setEntreVerde(anel, 10, 1, 3, 3, 5);
        setEntreVerde(anel, 7, 2, 1, 3, 5);
        setEntreVerde(anel, 7, 2, 3, 3, 5);
        setEntreVerde(anel, 9, 2, 1, 3, 5);
        setEntreVerde(anel, 9, 2, 3, 3, 5);
        setEntreVerde(anel, 8, 3, 1, 3, 5);
        setEntreVerde(anel, 8, 3, 2, 3, 5);
        setEntreVerde(anel, 9, 3, 1, 3, 5);
        setEntreVerde(anel, 9, 3, 2, 3, 5);
        setEntreVerde(anel, 10, 3, 1, 3, 5);
        setEntreVerde(anel, 10, 3, 2, 3, 5);

        controlador.save();
    }

    private Detector associaDetectorEstagio(Anel anel, Integer posicaoEstagio, TipoDetector tipo, Integer posicao) {
        Detector detector = criarDetector(anel, tipo, posicao, false);
        Estagio estagio = anel.findEstagioByPosicao(posicaoEstagio);
        detector.setEstagio(estagio);
        estagio.setDetector(detector);
        return detector;
    }

    public void setDadosAssociacaoDetectores() {
        Anel anel = getAnel(1);
        associaDetectorEstagio(anel, 2, TipoDetector.PEDESTRE, 1);

        anel = getAnel(2);
        Detector detector = associaDetectorEstagio(anel, 3, TipoDetector.PEDESTRE, 1);
        detector.setTempoDeteccaoPermanente(2);
        detector.setTempoAusenciaDeteccao(4);

        controlador.save();
    }

    protected Controlador setPlanos(Controlador controlador){
        this.controlador = controlador;
        setDadosPlanos();
        setDadosTabelaHoraria();

        return this.controlador;
    }

    private void criaVersaoPlanos(Anel anel){
        VersaoPlano versaoPlano = new VersaoPlano(anel, getUsuario());
        versaoPlano.setStatusVersao(StatusVersao.ATIVO);
        anel.addVersaoPlano(versaoPlano);
        versaoPlano.save();
    }

    private Plano criarPlano(Anel anel, Integer posicao, ModoOperacaoPlano modoOperacaoPlano, Integer tempoCiclo){
        Plano plano = new Plano();
        plano.setPosicao(posicao);
        plano.setDescricao("PLANO " + posicao);
        plano.setModoOperacao(modoOperacaoPlano);
        plano.setPosicaoTabelaEntreVerde(1);
        plano.setTempoCiclo(tempoCiclo);
        anel.getVersaoPlano().addPlano(plano);
        plano.setVersaoPlano(anel.getVersaoPlano());
        criarGrupoSemaforicoPlano(anel, plano);
        return plano;
    }

    public void setDadosPlanos() {
        Anel anel = getAnel(1);
        criaVersaoPlanos(anel);
        Plano plano = criarPlano(anel, 1, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 52);
        criarEstagioPlano(anel, plano, new int[]{1, 2, 3}, new int[]{10, 10, 10});

        plano = criarPlano(anel, 5, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 47);
        criarEstagioPlano(anel, plano, new int[]{1, 2, 3}, new int[]{10, 5, 10});

        anel = getAnel(2);
        criaVersaoPlanos(anel);
        plano = criarPlano(anel, 1, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 56);
        criarEstagioPlano(anel, plano, new int[]{1, 3, 2}, new int[]{10, 12, 10});

        controlador.save();
    }

    private void criarEvento(TabelaHorario tabelaHoraria, int posicao, TipoEvento tipoEvento, DiaDaSemana diaDaSemana, LocalTime horario, int posicaoPlano) {
        Evento evento = new Evento();
        evento.setPosicao(posicao);
        evento.setTipo(tipoEvento);
        evento.setDiaDaSemana(diaDaSemana);
        evento.setHorario(horario);
        evento.setPosicaoPlano(posicaoPlano);
        if(tipoEvento != TipoEvento.NORMAL){
            evento.setData(new Date());
        }
        evento.setTabelaHorario(tabelaHoraria);
        tabelaHoraria.addEventos(evento);
    }

    public void setDadosTabelaHoraria() {
        TabelaHorario tabelaHoraria = new TabelaHorario();
        VersaoTabelaHoraria versaoTabelaHoraria = new VersaoTabelaHoraria(controlador, null, tabelaHoraria, getUsuario());
        versaoTabelaHoraria.setStatusVersao(StatusVersao.ATIVO);
        tabelaHoraria.setVersaoTabelaHoraria(versaoTabelaHoraria);
        controlador.addVersaoTabelaHoraria(versaoTabelaHoraria);

        criarEvento(tabelaHoraria, 1, TipoEvento.NORMAL, DiaDaSemana.TODOS_OS_DIAS, LocalTime.parse("00:00:00"), 1);
        criarEvento(tabelaHoraria, 2, TipoEvento.NORMAL, DiaDaSemana.SEGUNDA_A_SABADO, LocalTime.parse("08:00:00"), 1);
        criarEvento(tabelaHoraria, 1, TipoEvento.NORMAL, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("18:00:00"), 1);
        criarEvento(tabelaHoraria, 3, TipoEvento.ESPECIAL_RECORRENTE, DiaDaSemana.SEGUNDA_A_SABADO, LocalTime.parse("18:00:00"), 1);
        criarEvento(tabelaHoraria, 4, TipoEvento.ESPECIAL_NAO_RECORRENTE, DiaDaSemana.DOMINGO, LocalTime.parse("08:00:00"), 1);

        controlador.save();
    }

    // METODOS AUXILIARES
    private void criarGrupoSemaforicoPlano(Anel anel, Plano plano) {
        plano.setGruposSemaforicosPlanos(null);
        for (GrupoSemaforico grupoSemaforico : anel.getGruposSemaforicos()) {
            GrupoSemaforicoPlano grupoPlano = new GrupoSemaforicoPlano();
            grupoPlano.setAtivado(true);
            grupoPlano.setPlano(plano);
            grupoPlano.setGrupoSemaforico(grupoSemaforico);
            plano.addGruposSemaforicos(grupoPlano);
        }
    }

    private void criarEstagioPlano(Anel anel, Plano plano, int posicoes[], int tempos[]) {
        int i = 0;
        plano.setEstagiosPlanos(null);
        for (Estagio estagio : anel.ordenarEstagiosPorPosicao()) {
            EstagioPlano estagioPlano = new EstagioPlano();
            estagioPlano.setPosicao(posicoes[i]);
            estagioPlano.setPlano(plano);
            estagioPlano.setEstagio(estagio);
            estagioPlano.setTempoVerde(tempos[i]);
            plano.addEstagios(estagioPlano);
            i++;
        }
    }


    protected Detector criarDetector(Anel anel, TipoDetector tipo, Integer posicao, Boolean monitorado) {
        Detector detector = new Detector();
        detector.setAnel(anel);
        detector.setControlador(anel.getControlador());
        detector.setTipo(tipo);
        detector.setPosicao(posicao);
        detector.setMonitorado(monitorado);
        anel.addDetectores(detector);
        return detector;
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
