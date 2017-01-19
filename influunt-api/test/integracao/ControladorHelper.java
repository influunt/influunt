package integracao;

import config.WithInfluuntApplicationNoAuthentication;
import models.*;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import utils.RangeUtils;

import java.util.Arrays;
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

    public ControladorHelper(Controlador controlador) {
        this.controlador = controlador;
    }

    public ControladorHelper() {

    }

    public Controlador getControlador() {
        return getControlador(false);
    }

    public Controlador getControlador(boolean comVerdeConflitante) {
        controlador = new Controlador();
        controlador.setRangeUtils(RangeUtils.getInstance(null));

        criaRelacaoDadosBasicos();

        setDadosBasicos();
        setDadosAneis();
        setDadosGruposSemaforicos();
        setDadosVerdesConflitantes();
        setDadosAssociacaoEstagioGrupoSemaforico();
        setDadosTransicoesProibidas();

        setDadosAtrasoDeGrupo(comVerdeConflitante);

        setDadosTabelaEntreVerdes();
        setDadosAssociacaoDetectores();

        controlador.setStatusVersao(StatusVersao.CONFIGURADO);

        VersaoControlador versaoControlador = controlador.getVersaoControlador();

        ControladorFisico controladorFisico = versaoControlador.getControladorFisico();
        controladorFisico.criarChaves();

        controladorFisico.addVersaoControlador(versaoControlador);
        controladorFisico.setArea(controlador.getArea());

        controlador.save();
        controladorFisico.save();

        return controlador;
    }

    public Controlador getControladorSemTransicaoProibida() {
        controlador = new Controlador();

        criaRelacaoDadosBasicos();

        setDadosBasicos();
        setDadosAneis();
        setDadosGruposSemaforicos();
        setDadosVerdesConflitantes();
        setDadosAssociacaoEstagioGrupoSemaforico();
        setDadosAtrasoDeGrupo();
        setDadosTabelaEntreVerdesCompleta();
        setDadosAssociacaoDetectores();

        controlador.setStatusVersao(StatusVersao.CONFIGURADO);

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

    private void setDadosBasicos() {
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
        controladorFisico.setArea(controlador.getArea());
        controlador.save();
        controladorFisico.save();
        VersaoControlador versaoControlador = new VersaoControlador(controlador, controladorFisico, getUsuario());
        controladorFisico.addVersaoControlador(versaoControlador);
        controlador.setVersaoControlador(versaoControlador);
        versaoControlador.save();
    }

    private Anel getAnel(Integer posicao) {
        return controlador.getAneis().stream().filter(anel -> anel.getPosicao().equals(posicao)).findAny().get();
    }

    private void setDadosAneis() {
        Anel anel = getAnel(1);
        anel.setDescricao("Av. Paulista com Haddock Lobo");
        anel.setPosicao(1);
        anel.setAtivo(true);
        List<Estagio> estagios = Arrays.asList(new Estagio(1, anel), new Estagio(2, anel), new Estagio(3, anel));
        estagios.forEach(estagio -> {
            Imagem imagem = criarImagem();
            estagio.setImagem(imagem);
        });
        anel.setEstagios(estagios);

        Endereco haddock = new Endereco(1.0, 1.0, "Av. Paulista com Haddock Lobo");
        haddock.setAnel(anel);
        haddock.setAlturaNumerica(145);
        anel.setEndereco(haddock);

        anel = getAnel(2);
        anel.setDescricao("Av. Paulista com Rua Augusta");
        anel.setPosicao(2);
        anel.setAtivo(true);
        anel.setAceitaModoManual(false);
        List<Estagio> estagios2 = Arrays.asList(new Estagio(1, anel), new Estagio(2, anel), new Estagio(3, anel));
        estagios2.forEach(estagio -> {
            Imagem imagem = criarImagem();
            estagio.setImagem(imagem);
        });
        anel.setEstagios(estagios2);

        Endereco augusta = new Endereco(1.0, 1.0, "Av. Paulista com Rua Augusta");
        augusta.setAnel(anel);
        augusta.setAlturaNumerica(145);
        anel.setEndereco(augusta);

        anel = getAnel(3);
        anel.setDescricao("Av. Paulista com Rua da Consolação");
        anel.setPosicao(3);
        anel.setAtivo(true);
        List<Estagio> estagios3 = Arrays.asList(new Estagio(1, anel), new Estagio(2, anel), new Estagio(3, anel),
            new Estagio(4, anel), new Estagio(5, anel));
        estagios3.forEach(estagio -> {
            Imagem imagem = criarImagem();
            estagio.setImagem(imagem);
        });
        anel.setEstagios(estagios3);

        augusta = new Endereco(1.0, 1.0, "Av. Paulista com Rua da Consolação");
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

    private void setDadosGruposSemaforicos() {
        Anel anel = getAnel(1);

        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 1);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 2);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 3);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 4);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 5);

        anel = getAnel(2);

        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 10);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 9);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 8);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 7);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 6);

        anel = getAnel(3);

        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 11);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 12);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 13);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 14);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.PEDESTRE, 15);
        criaGrupoSemaforico(anel, TipoGrupoSemaforico.VEICULAR, 16);

        controlador.save();
    }

    private void criarVerdeConflitante(Anel anel, Integer origem, Integer destino) {
        GrupoSemaforico grupoSemaforicoOrigem = anel.findGrupoSemaforicoByPosicao(origem);
        GrupoSemaforico grupoSemaforicoDestino = anel.findGrupoSemaforicoByPosicao(destino);

        grupoSemaforicoOrigem.addVerdeConflitante(grupoSemaforicoDestino);
        grupoSemaforicoDestino.addVerdeConflitante(grupoSemaforicoOrigem);
    }

    private void setDadosVerdesConflitantes() {
        Anel anel = getAnel(1);

        criarVerdeConflitante(anel, 1, 3);
        criarVerdeConflitante(anel, 1, 2);
        criarVerdeConflitante(anel, 1, 4);
        criarVerdeConflitante(anel, 2, 3);
        criarVerdeConflitante(anel, 2, 5);

        anel = getAnel(2);

        criarVerdeConflitante(anel, 6, 7);
        criarVerdeConflitante(anel, 6, 8);
        criarVerdeConflitante(anel, 6, 9);
        criarVerdeConflitante(anel, 7, 8);
        criarVerdeConflitante(anel, 7, 10);

        anel = getAnel(3);

        criarVerdeConflitante(anel, 11, 12);
        criarVerdeConflitante(anel, 11, 13);
        criarVerdeConflitante(anel, 11, 14);
        criarVerdeConflitante(anel, 11, 15);
        criarVerdeConflitante(anel, 11, 16);

        criarVerdeConflitante(anel, 12, 11);
        criarVerdeConflitante(anel, 12, 13);
        criarVerdeConflitante(anel, 12, 14);
        criarVerdeConflitante(anel, 12, 15);
        criarVerdeConflitante(anel, 12, 16);

        criarVerdeConflitante(anel, 13, 11);
        criarVerdeConflitante(anel, 13, 12);
        criarVerdeConflitante(anel, 13, 14);
        criarVerdeConflitante(anel, 13, 15);
        criarVerdeConflitante(anel, 13, 16);

        criarVerdeConflitante(anel, 14, 11);
        criarVerdeConflitante(anel, 14, 12);
        criarVerdeConflitante(anel, 14, 13);
        criarVerdeConflitante(anel, 14, 16);

        criarVerdeConflitante(anel, 15, 11);
        criarVerdeConflitante(anel, 15, 12);
        criarVerdeConflitante(anel, 15, 13);
        criarVerdeConflitante(anel, 15, 16);

        criarVerdeConflitante(anel, 16, 11);
        criarVerdeConflitante(anel, 16, 12);
        criarVerdeConflitante(anel, 16, 13);
        criarVerdeConflitante(anel, 16, 14);
        criarVerdeConflitante(anel, 16, 15);

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

    private void setDadosAssociacaoEstagioGrupoSemaforico() {
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

        criarAssociacaoEstagioGrupoSemaforico(anel, 1, 10);
        estagioGrupoSemaforico = criarAssociacaoEstagioGrupoSemaforico(anel, 1, 6);
        estagioGrupoSemaforico.getEstagio().setTempoMaximoPermanenciaAtivado(false);

        criarAssociacaoEstagioGrupoSemaforico(anel, 2, 7);
        criarAssociacaoEstagioGrupoSemaforico(anel, 2, 9);

        criarAssociacaoEstagioGrupoSemaforico(anel, 3, 8);

        anel = getAnel(3);

        criarAssociacaoEstagioGrupoSemaforico(anel, 1, 11);
        criarAssociacaoEstagioGrupoSemaforico(anel, 2, 12);
        criarAssociacaoEstagioGrupoSemaforico(anel, 3, 13);

        criarAssociacaoEstagioGrupoSemaforico(anel, 4, 14);
        criarAssociacaoEstagioGrupoSemaforico(anel, 4, 15);

        estagioGrupoSemaforico = criarAssociacaoEstagioGrupoSemaforico(anel, 5, 16);
        estagioGrupoSemaforico.getEstagio().setDemandaPrioritaria(true);
        estagioGrupoSemaforico.getEstagio().setTempoVerdeDemandaPrioritaria(30);

        controlador.save();
    }

    public void criarTransicaoProibida(Anel anel, Integer posicaoOrigem, Integer posicaoDestino, Integer posicaoAlternativo) {
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

    private void setDadosTransicoesProibidas() {
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

    public void setAtrasoDeGrupo(Anel anel, Integer posicaoGrupo, Integer posicaoOrigem, Integer posicaoDestino, Integer tempoAtrasoGrupo) {
        GrupoSemaforico grupoSemaforico = anel.findGrupoSemaforicoByPosicao(posicaoGrupo);
        Estagio origem = anel.findEstagioByPosicao(posicaoOrigem);
        Estagio destino = anel.findEstagioByPosicao(posicaoDestino);

        Transicao transicao = grupoSemaforico.getTransicoes().stream().filter(transicao1 -> transicao1.getOrigem().equals(origem) && transicao1.getDestino().equals(destino)).findAny().get();
        AtrasoDeGrupo atrasoDeGrupo = transicao.getAtrasoDeGrupo();
        atrasoDeGrupo.setAtrasoDeGrupo(tempoAtrasoGrupo);
    }

    private void setDadosAtrasoDeGrupo() {
        setDadosAtrasoDeGrupo(false);
    }

    private void setDadosAtrasoDeGrupo(boolean gerarVerdeConflitante) {
        criarAtrasoDeGrupo();
        controlador.save();

        Anel anel = getAnel(1);
        setAtrasoDeGrupo(anel, 2, 3, 1, 2);

        if (gerarVerdeConflitante) {
            setAtrasoDeGrupo(anel, 1, 3, 1, 2);
        }

        controlador.save();
    }

    private void setEntreVerde(Anel anel, Integer posicaoGrupo, Integer posicaoOrigem, Integer posicaoDestino,
                               Integer tempoAmarelo, Integer tempoVermelho, boolean modoIntermitenteOuApagado) {
        Transicao transicao = setEntreVerde(anel, posicaoGrupo, posicaoOrigem, posicaoDestino, tempoAmarelo, tempoVermelho);
        transicao.setModoIntermitenteOuApagado(modoIntermitenteOuApagado);
    }

    private Transicao setEntreVerde(Anel anel, Integer posicaoGrupo, Integer posicaoOrigem, Integer posicaoDestino, Integer tempoAmarelo, Integer tempoVermelho) {
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
        return transicao;
    }

    private void setDadosTabelaEntreVerdes() {
        Anel anel = getAnel(1);
        setEntreVerde(anel, 1, 1, 2, 3, 3, true);

        setEntreVerde(anel, 3, 2, 3, 5, 3, true);
        setEntreVerde(anel, 5, 2, 3, 5, 3, true);

        setEntreVerde(anel, 2, 3, 1, 3, 3, true);
        setEntreVerde(anel, 4, 3, 1, 5, 3, true);

        anel = getAnel(2);
        setEntreVerde(anel, 6, 1, 2, 3, 4, true);
        setEntreVerde(anel, 6, 1, 3, 4, 5);
        setEntreVerde(anel, 10, 1, 2, 3, 4, true);
        setEntreVerde(anel, 10, 1, 3, 4, 5);

        setEntreVerde(anel, 7, 2, 1, 3, 5, true);
        setEntreVerde(anel, 7, 2, 3, 4, 5);
        setEntreVerde(anel, 9, 2, 1, 3, 5, true);
        setEntreVerde(anel, 9, 2, 3, 4, 5);

        setEntreVerde(anel, 8, 3, 1, 5, 5, true);
        setEntreVerde(anel, 8, 3, 2, 5, 5);

        anel = getAnel(3);
        setEntreVerde(anel, 11, 1, 2, 3, 4, true);
        setEntreVerde(anel, 11, 1, 3, 3, 4);
        setEntreVerde(anel, 11, 1, 4, 3, 4);
        setEntreVerde(anel, 11, 1, 5, 3, 4);

        setEntreVerde(anel, 12, 2, 1, 3, 5, true);
        setEntreVerde(anel, 12, 2, 3, 3, 5);
        setEntreVerde(anel, 12, 2, 4, 3, 5);
        setEntreVerde(anel, 12, 2, 5, 3, 5);

        setEntreVerde(anel, 13, 3, 1, 3, 6, true);
        setEntreVerde(anel, 13, 3, 2, 3, 6);
        setEntreVerde(anel, 13, 3, 4, 3, 6);
        setEntreVerde(anel, 13, 3, 5, 3, 6);

        setEntreVerde(anel, 14, 4, 1, 6, 5, true);
        setEntreVerde(anel, 14, 4, 2, 6, 5);
        setEntreVerde(anel, 14, 4, 3, 6, 5);
        setEntreVerde(anel, 14, 4, 5, 6, 5);

        setEntreVerde(anel, 15, 4, 1, 6, 5, true);
        setEntreVerde(anel, 15, 4, 2, 6, 5);
        setEntreVerde(anel, 15, 4, 3, 6, 5);
        setEntreVerde(anel, 15, 4, 5, 6, 5);

        setEntreVerde(anel, 16, 5, 1, 4, 4, true);
        setEntreVerde(anel, 16, 5, 2, 4, 4);
        setEntreVerde(anel, 16, 5, 3, 4, 4);
        setEntreVerde(anel, 16, 5, 4, 4, 4);

        controlador.save();
    }

    private void setDadosTabelaEntreVerdesCompleta() {
        Anel anel = getAnel(1);
        setEntreVerde(anel, 1, 1, 2, 3, 3, true);
        setEntreVerde(anel, 1, 1, 3, 3, 3);
        setEntreVerde(anel, 5, 1, 3, 3, 3, true);

        setEntreVerde(anel, 3, 2, 3, 5, 3, true);
        setEntreVerde(anel, 5, 2, 3, 5, 3);
        setEntreVerde(anel, 3, 2, 1, 5, 3);
        setEntreVerde(anel, 4, 2, 1, 5, 3);

        setEntreVerde(anel, 2, 3, 1, 3, 3, true);
        setEntreVerde(anel, 4, 3, 1, 5, 3, true);
        setEntreVerde(anel, 2, 3, 2, 3, 3);

        anel = getAnel(2);
        setEntreVerde(anel, 6, 1, 2, 3, 4, true);
        setEntreVerde(anel, 6, 1, 3, 4, 5);
        setEntreVerde(anel, 10, 1, 2, 3, 4, true);
        setEntreVerde(anel, 10, 1, 3, 4, 5);

        setEntreVerde(anel, 7, 2, 1, 3, 5, true);
        setEntreVerde(anel, 7, 2, 3, 4, 5);
        setEntreVerde(anel, 9, 2, 1, 3, 5, true);
        setEntreVerde(anel, 9, 2, 3, 4, 5);

        setEntreVerde(anel, 8, 3, 1, 5, 5, true);
        setEntreVerde(anel, 8, 3, 2, 5, 5);

        anel = getAnel(3);
        setEntreVerde(anel, 11, 1, 2, 3, 4, true);
        setEntreVerde(anel, 11, 1, 3, 3, 4);
        setEntreVerde(anel, 11, 1, 4, 3, 4);
        setEntreVerde(anel, 11, 1, 5, 3, 4);

        setEntreVerde(anel, 12, 2, 1, 3, 5, true);
        setEntreVerde(anel, 12, 2, 3, 3, 5);
        setEntreVerde(anel, 12, 2, 4, 3, 5);
        setEntreVerde(anel, 12, 2, 5, 3, 5);

        setEntreVerde(anel, 13, 3, 1, 3, 6, true);
        setEntreVerde(anel, 13, 3, 2, 3, 6);
        setEntreVerde(anel, 13, 3, 4, 3, 6);
        setEntreVerde(anel, 13, 3, 5, 3, 6);

        setEntreVerde(anel, 14, 4, 1, 6, 5, true);
        setEntreVerde(anel, 14, 4, 2, 6, 5);
        setEntreVerde(anel, 14, 4, 3, 6, 5);
        setEntreVerde(anel, 14, 4, 5, 6, 5);

        setEntreVerde(anel, 15, 4, 1, 6, 5, true);
        setEntreVerde(anel, 15, 4, 2, 6, 5);
        setEntreVerde(anel, 15, 4, 3, 6, 5);
        setEntreVerde(anel, 15, 4, 5, 6, 5);

        setEntreVerde(anel, 16, 5, 1, 4, 4, true);
        setEntreVerde(anel, 16, 5, 2, 4, 4);
        setEntreVerde(anel, 16, 5, 3, 4, 4);
        setEntreVerde(anel, 16, 5, 4, 4, 4);

        controlador.save();
    }

    private Detector associaDetectorEstagio(Anel anel, Integer posicaoEstagio, TipoDetector tipo, Integer posicao) {
        Detector detector = criarDetector(anel, tipo, posicao, false);
        Estagio estagio = anel.findEstagioByPosicao(posicaoEstagio);
        detector.setEstagio(estagio);
        estagio.setDetector(detector);
        return detector;
    }

    private void setDadosAssociacaoDetectores() {
        Anel anel = getAnel(1);
        associaDetectorEstagio(anel, 2, TipoDetector.PEDESTRE, 1);

        anel = getAnel(2);
        Detector detector = associaDetectorEstagio(anel, 3, TipoDetector.PEDESTRE, 1);
        detector.setTempoDeteccaoPermanente(2);
        detector.setTempoAusenciaDeteccao(4);

        associaDetectorEstagio(anel, 1, TipoDetector.PEDESTRE, 2);


        anel = getAnel(3);
        associaDetectorEstagio(anel, 1, TipoDetector.VEICULAR, 1);
        associaDetectorEstagio(anel, 2, TipoDetector.VEICULAR, 2);
        associaDetectorEstagio(anel, 3, TipoDetector.VEICULAR, 3);
        associaDetectorEstagio(anel, 4, TipoDetector.PEDESTRE, 1);
        associaDetectorEstagio(anel, 5, TipoDetector.VEICULAR, 4);

        controlador.save();
    }

    public Controlador setPlanos(Controlador controlador) {
        this.controlador = controlador;
        setDadosPlanos();
        setDadosTabelaHoraria();

        return this.controlador;
    }

    public Controlador setPlanosComTabelaHorariaMicro(Controlador controlador) {
        this.controlador = controlador;
        setDadosPlanos();
        setDadosTabelaHorariaMicro();

        return this.controlador;
    }

    public Controlador setPlanosComTabelaHorariaMicro2(Controlador controlador) {
        this.controlador = controlador;
        setDadosPlanos();
        setDadosTabelaHorariaMicro2();

        return this.controlador;
    }


    private void criaVersaoPlanos(Anel anel) {
        VersaoPlano versaoPlano = new VersaoPlano(anel, getUsuario());
        versaoPlano.setStatusVersao(StatusVersao.SINCRONIZADO);
        anel.addVersaoPlano(versaoPlano);
        versaoPlano.save();
    }

    public Plano criarPlano(Anel anel, Integer posicao, ModoOperacaoPlano modoOperacaoPlano, Integer tempoCiclo) {
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

    private void setDadosPlanos() {
        Anel anel = getAnel(1);
        criaVersaoPlanos(anel);
        Plano plano = criarPlano(anel, 1, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 52);
        criarEstagiosPlanos(anel, plano, new int[]{1, 2, 3}, new int[]{10, 10, 10});

        plano = criarPlano(anel, 2, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 47);
        criarEstagiosPlanos(anel, plano, new int[]{1, 2, 3}, new int[]{10, 5, 10});

        plano = criarPlano(anel, 3, ModoOperacaoPlano.TEMPO_FIXO_COORDENADO, 58);
        criarEstagiosPlanos(anel, plano, new int[]{2, 3, 1}, new int[]{10, 12, 14});

        plano = criarPlano(anel, 4, ModoOperacaoPlano.TEMPO_FIXO_COORDENADO, 58);
        criarEstagiosPlanos(anel, plano, new int[]{2, 3, 1}, new int[]{10, 12, 14});

        plano = criarPlano(anel, 5, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 47);
        criarEstagiosPlanos(anel, plano, new int[]{1, 2, 3}, new int[]{10, 5, 10});

        plano = criarPlano(anel, 6, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 47);
        criarEstagiosPlanos(anel, plano, new int[]{1, 2, 3}, new int[]{10, 5, 10});

        plano = criarPlano(anel, 7, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 58);
        criarEstagiosPlanos(anel, plano, new int[]{1, 2, 3}, new int[]{10, 12, 14});

        plano = criarPlano(anel, 10, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 58);
        criarEstagiosPlanos(anel, plano, new int[]{2, 3, 1}, new int[]{10, 12, 14});

        plano = criarPlano(anel, 11, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 65);
        criarEstagioPlano(anel, plano, 1, 1, 15, false);
        criarEstagioPlano(anel, plano, 2, 2, 4, false);
        criarEstagioPlano(anel, plano, 2, 3, 4, true);
        criarEstagioPlano(anel, plano, 3, 4, 20, false);

        plano = criarPlano(anel, 12, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 58);
        criarEstagiosPlanos(anel, plano, new int[]{2, 3, 1}, new int[]{10, 12, 14});
        criarPlano(anel, 13, ModoOperacaoPlano.INTERMITENTE, null);
        criarPlano(anel, 16, ModoOperacaoPlano.INTERMITENTE, null);

        anel = getAnel(2);
        criaVersaoPlanos(anel);
        plano = criarPlano(anel, 1, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 59);
        criarEstagiosPlanos(anel, plano, new int[]{1, 3, 2}, new int[]{10, 12, 10});

        plano = criarPlano(anel, 2, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 59);
        criarEstagiosPlanos(anel, plano, new int[]{1, 3, 2}, new int[]{10, 12, 10});

        plano = criarPlano(anel, 3, ModoOperacaoPlano.TEMPO_FIXO_COORDENADO, 58);
        plano.setDefasagem(10);
        criarEstagiosPlanos(anel, plano, new int[]{1, 3, 2}, new int[]{10, 11, 10});

        plano = criarPlano(anel, 4, ModoOperacaoPlano.TEMPO_FIXO_COORDENADO, 58);
        plano.setDefasagem(10);
        criarEstagiosPlanos(anel, plano, new int[]{1, 3, 2}, new int[]{10, 11, 10});
        EstagioPlano estagioPlano = plano.getEstagiosPlanos().stream().filter(e -> e.getEstagio().getPosicao().equals(3)).findFirst().get();
        estagioPlano.setDispensavel(true);
        estagioPlano.setEstagioQueRecebeEstagioDispensavel(plano.getEstagiosPlanos().stream().filter(e -> e.getEstagio().getPosicao().equals(1)).findFirst().get());

        plano = criarPlano(anel, 5, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 59);
        criarEstagiosPlanos(anel, plano, new int[]{1, 3, 2}, new int[]{10, 12, 10});

        plano = criarPlano(anel, 6, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 59);
        criarEstagiosPlanos(anel, plano, new int[]{1, 3, 2}, new int[]{10, 12, 10});

        plano = criarPlano(anel, 7, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 59);
        criarEstagiosPlanos(anel, plano, new int[]{1, 3, 2}, new int[]{10, 12, 10});

        //Plano com estágio 3 dispensavel no fim
        plano = criarPlano(anel, 10, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 63);
        criarEstagiosPlanos(anel, plano, new int[]{1, 2, 3}, new int[]{10, 15, 12});
        estagioPlano = plano.getEstagiosPlanos().stream().filter(e -> e.getEstagio().getPosicao().equals(3)).findFirst().get();
        estagioPlano.setDispensavel(true);

        //Plano com estágio 3 dispensavel no meio
        plano = criarPlano(anel, 11, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 54);
        criarEstagiosPlanos(anel, plano, new int[]{1, 3, 2}, new int[]{10, 5, 12});
        estagioPlano = plano.getEstagiosPlanos().stream().filter(e -> e.getEstagio().getPosicao().equals(3)).findFirst().get();
        estagioPlano.setDispensavel(true);

        //Plano com estágio 3 dispensavel no inicio
        plano = criarPlano(anel, 12, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 62);
        criarEstagiosPlanos(anel, plano, new int[]{1, 3, 2}, new int[]{12, 5, 18});
        estagioPlano = plano.getEstagiosPlanos().stream().filter(e -> e.getEstagio().getPosicao().equals(3)).findFirst().get();
        estagioPlano.setDispensavel(true);

        plano = criarPlano(anel, 13, ModoOperacaoPlano.TEMPO_FIXO_ISOLADO, 40);
        criarEstagioPlano(anel, plano, 1, 1, 15, false);
        criarEstagioPlano(anel, plano, 2, 2, 10, false);
        GrupoSemaforicoPlano grupoSemaforicoPlano = plano.getGruposSemaforicosPlanos()
            .stream().filter(e -> e.getGrupoSemaforico().getPosicao().equals(8)).findFirst().get();
        grupoSemaforicoPlano.setAtivado(false);

        criarPlano(anel, 16, ModoOperacaoPlano.INTERMITENTE, null);

        anel = getAnel(3);
        criaVersaoPlanos(anel);
        plano = criarPlano(anel, 1, ModoOperacaoPlano.ATUADO, null);
        criarEstagioPlano(anel, plano, 1, 1, new int[]{10, 15, 20, 11}, false);
        criarEstagioPlano(anel, plano, 2, 2, new int[]{10, 15, 20, 11}, false);
        criarEstagioPlano(anel, plano, 3, 3, new int[]{10, 15, 20, 11}, false);
        criarEstagioPlano(anel, plano, 4, 4, new int[]{10, 12, 14, 11}, false);

        plano = criarPlano(anel, 2, ModoOperacaoPlano.ATUADO, null);
        criarEstagioPlano(anel, plano, 1, 1, new int[]{10, 15, 20, 11}, false);
        criarEstagioPlano(anel, plano, 2, 2, new int[]{10, 15, 20, 11}, false);
        criarEstagioPlano(anel, plano, 3, 3, new int[]{10, 15, 20, 11}, false);
        criarEstagioPlano(anel, plano, 4, 4, new int[]{10, 10, 11, 11}, true);

        plano = criarPlano(anel, 3, ModoOperacaoPlano.ATUADO, null);
        criarEstagioPlano(anel, plano, 1, 1, new int[]{10, 15, 20, 11}, false);
        criarEstagioPlano(anel, plano, 2, 2, new int[]{10, 15, 20, 11}, false);
        criarEstagioPlano(anel, plano, 3, 3, new int[]{10, 15, 20, 11}, false);
        criarEstagioPlano(anel, plano, 4, 4, new int[]{10, 10, 11, 11}, true);

        criarPlano(anel, 4, ModoOperacaoPlano.INTERMITENTE, null);
        criarPlano(anel, 5, ModoOperacaoPlano.INTERMITENTE, null);
        criarPlano(anel, 6, ModoOperacaoPlano.APAGADO, null);
        criarPlano(anel, 7, ModoOperacaoPlano.APAGADO, null);
        criarPlano(anel, 10, ModoOperacaoPlano.INTERMITENTE, null);
        criarPlano(anel, 11, ModoOperacaoPlano.INTERMITENTE, null);
        criarPlano(anel, 12, ModoOperacaoPlano.INTERMITENTE, null);
        criarPlano(anel, 13, ModoOperacaoPlano.INTERMITENTE, null);
        criarPlano(anel, 16, ModoOperacaoPlano.INTERMITENTE, null);

        controlador.save();
    }

    public void criarEvento(TabelaHorario tabelaHoraria, int posicao, DiaDaSemana diaDaSemana, LocalTime horario, int posicaoPlano) {
        Evento evento = new Evento();
        evento.setPosicao(posicao);
        evento.setTipo(TipoEvento.NORMAL);
        evento.setDiaDaSemana(diaDaSemana);
        evento.setHorario(horario);
        evento.setPosicaoPlano(posicaoPlano);
        evento.setTabelaHorario(tabelaHoraria);
        tabelaHoraria.addEvento(evento);
    }

    private void criarEventoEspecial(TabelaHorario tabelaHoraria, int posicao, TipoEvento tipo, DateTime data, LocalTime horario, String nome, int posicaoPlano) {
        Evento evento = new Evento();
        evento.setNome(nome);
        evento.setPosicao(posicao);
        evento.setTipo(tipo);
        evento.setData(data.toDate());
        evento.setHorario(horario);
        evento.setPosicaoPlano(posicaoPlano);
        evento.setTabelaHorario(tabelaHoraria);
        tabelaHoraria.addEvento(evento);
    }

    private void setDadosTabelaHoraria() {
        TabelaHorario tabelaHoraria = new TabelaHorario();
        VersaoTabelaHoraria versaoTabelaHoraria = new VersaoTabelaHoraria(controlador, null, tabelaHoraria, getUsuario());
        versaoTabelaHoraria.setStatusVersao(StatusVersao.SINCRONIZADO);
        tabelaHoraria.setVersaoTabelaHoraria(versaoTabelaHoraria);
        controlador.addVersaoTabelaHoraria(versaoTabelaHoraria);

        criarEvento(tabelaHoraria, 1, DiaDaSemana.TODOS_OS_DIAS, LocalTime.parse("00:00:00"), 1);

        criarEvento(tabelaHoraria, 1, DiaDaSemana.SEXTA, LocalTime.parse("01:00:00"), 11);
        criarEvento(tabelaHoraria, 1, DiaDaSemana.SEXTA, LocalTime.parse("01:00:30"), 10);

        criarEvento(tabelaHoraria, 1, DiaDaSemana.TODOS_OS_DIAS, LocalTime.parse("02:00:00"), 7);

        criarEvento(tabelaHoraria, 1, DiaDaSemana.TODOS_OS_DIAS, LocalTime.parse("03:00:00"), 1);

        criarEvento(tabelaHoraria, 2, DiaDaSemana.SEGUNDA_A_SABADO, LocalTime.parse("08:00:00"), 11);

        criarEvento(tabelaHoraria, 2, DiaDaSemana.SEGUNDA_A_SABADO, LocalTime.parse("13:00:00"), 1);
        criarEvento(tabelaHoraria, 2, DiaDaSemana.SEGUNDA_A_SABADO, LocalTime.parse("14:00:00"), 1);

        criarEvento(tabelaHoraria, 3, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("18:00:00"), 1);
        criarEvento(tabelaHoraria, 4, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("18:01:00"), 10);
        criarEvento(tabelaHoraria, 5, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("18:02:00"), 1);

        criarEvento(tabelaHoraria, 6, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("19:00:00"), 1);
        criarEvento(tabelaHoraria, 7, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("19:01:00"), 6);
        criarEvento(tabelaHoraria, 8, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("19:02:00"), 1);

        criarEvento(tabelaHoraria, 9, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("20:00:00"), 10);
        criarEvento(tabelaHoraria, 10, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("20:00:58"), 16);

        criarEvento(tabelaHoraria, 11, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("21:00:00"), 10);
        criarEvento(tabelaHoraria, 12, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("21:00:58"), 16);
        criarEvento(tabelaHoraria, 13, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("21:01:00"), 10);

        criarEvento(tabelaHoraria, 14, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("22:00:00"), 1);
        criarEvento(tabelaHoraria, 15, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("22:02:00"), 6);

        criarEvento(tabelaHoraria, 16, DiaDaSemana.DOMINGO, LocalTime.parse("18:00:00"), 6);

        criarEvento(tabelaHoraria, 17, DiaDaSemana.SEGUNDA, LocalTime.parse("23:00:00"), 2);

        criarEvento(tabelaHoraria, 18, DiaDaSemana.TERCA, LocalTime.parse("23:00:00"), 3);

        criarEvento(tabelaHoraria, 19, DiaDaSemana.QUARTA, LocalTime.parse("23:00:00"), 4);

        criarEvento(tabelaHoraria, 20, DiaDaSemana.QUINTA, LocalTime.parse("23:00:00"), 16);

        criarEventoEspecial(tabelaHoraria, 1, TipoEvento.ESPECIAL_RECORRENTE, new DateTime(2016, 12, 25, 0, 0, 0), LocalTime.parse("08:00:00"), "Natal", 11);
        criarEventoEspecial(tabelaHoraria, 1, TipoEvento.ESPECIAL_NAO_RECORRENTE, new DateTime(2017, 3, 15, 0, 0, 0), LocalTime.parse("08:00:00"), "Dia das Mães", 12);

        controlador.save();
    }

    private void setDadosTabelaHorariaMicro() {
        TabelaHorario tabelaHoraria = new TabelaHorario();
        VersaoTabelaHoraria versaoTabelaHoraria = new VersaoTabelaHoraria(controlador, null, tabelaHoraria, getUsuario());
        versaoTabelaHoraria.setStatusVersao(StatusVersao.SINCRONIZADO);
        tabelaHoraria.setVersaoTabelaHoraria(versaoTabelaHoraria);
        controlador.addVersaoTabelaHoraria(versaoTabelaHoraria);

        criarEvento(tabelaHoraria, 1, DiaDaSemana.TODOS_OS_DIAS, LocalTime.parse("00:00:00"), 1);

        criarEvento(tabelaHoraria, 2, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("8:00:00"), 5);
        criarEvento(tabelaHoraria, 3, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("18:00:00"), 6);

        criarEvento(tabelaHoraria, 4, DiaDaSemana.DOMINGO, LocalTime.parse("17:00:00"), 7);

        criarEventoEspecial(tabelaHoraria, 1, TipoEvento.ESPECIAL_RECORRENTE, new DateTime(2016, 12, 25, 0, 0, 0), LocalTime.parse("08:00:00"), "Natal", 11);
        criarEventoEspecial(tabelaHoraria, 1, TipoEvento.ESPECIAL_NAO_RECORRENTE, new DateTime(2017, 03, 15, 0, 0, 0), LocalTime.parse("08:00:00"), "Dia das Mães", 12);

        controlador.save();
    }

    private void setDadosTabelaHorariaMicro2() {
        TabelaHorario tabelaHoraria = new TabelaHorario();
        VersaoTabelaHoraria versaoTabelaHoraria = new VersaoTabelaHoraria(controlador, null, tabelaHoraria, getUsuario());
        versaoTabelaHoraria.setStatusVersao(StatusVersao.SINCRONIZADO);
        tabelaHoraria.setVersaoTabelaHoraria(versaoTabelaHoraria);
        controlador.addVersaoTabelaHoraria(versaoTabelaHoraria);

        criarEvento(tabelaHoraria, 1, DiaDaSemana.TODOS_OS_DIAS, LocalTime.parse("00:00:00"), 6);

        criarEvento(tabelaHoraria, 2, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("8:00:00"), 7);
        criarEvento(tabelaHoraria, 3, DiaDaSemana.SEGUNDA_A_SEXTA, LocalTime.parse("18:00:00"), 1);

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
            plano.addGruposSemaforicoPlano(grupoPlano);
        }
    }

    public void criarEstagiosPlanos(Anel anel, Plano plano, int posicoes[], int tempos[]) {
        int i = 0;
        plano.setEstagiosPlanos(null);
        for (Estagio estagio : anel.ordenarEstagiosPorPosicao()) {
            EstagioPlano estagioPlano = new EstagioPlano();
            estagioPlano.setPosicao(posicoes[i]);
            estagioPlano.setPlano(plano);
            estagioPlano.setEstagio(estagio);
            estagio.addEstagioPlano(estagioPlano);
            estagioPlano.setTempoVerde(tempos[estagioPlano.getPosicao() - 1]);
            plano.addEstagios(estagioPlano);
            i++;
        }
    }

    public void criarEstagioPlano(Anel anel, Plano plano, int posicaoEstagio, int posicao, int[] tempos, boolean dispensavel) {
        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(posicao);
        estagioPlano.setPlano(plano);
        Estagio estagio = anel.findEstagioByPosicao(posicaoEstagio);
        estagioPlano.setEstagio(estagio);
        estagio.addEstagioPlano(estagioPlano);
        estagioPlano.setTempoVerdeMinimo(tempos[0]);
        estagioPlano.setTempoVerdeIntermediario(tempos[1]);
        estagioPlano.setTempoVerdeMaximo(tempos[2]);
        estagioPlano.setTempoExtensaoVerde(tempos[3] / 10.0);

        estagioPlano.setDispensavel(dispensavel);
        plano.addEstagios(estagioPlano);
    }

    public void criarEstagioPlano(Anel anel, Plano plano, int posicaoEstagio, int posicao, int tempo, boolean dispensavel) {
        EstagioPlano estagioPlano = new EstagioPlano();
        estagioPlano.setPosicao(posicao);
        estagioPlano.setPlano(plano);
        Estagio estagio = anel.findEstagioByPosicao(posicaoEstagio);
        estagioPlano.setEstagio(estagio);
        estagio.addEstagioPlano(estagioPlano);
        estagioPlano.setTempoVerde(tempo);
        estagioPlano.setDispensavel(dispensavel);
        plano.addEstagios(estagioPlano);
    }


    private Detector criarDetector(Anel anel, TipoDetector tipo, Integer posicao, Boolean monitorado) {
        Detector detector = new Detector();
        detector.setAnel(anel);
        detector.setControlador(anel.getControlador());
        detector.setTipo(tipo);
        detector.setPosicao(posicao);
        detector.setMonitorado(monitorado);
        anel.addDetectores(detector);
        return detector;
    }


    private Usuario getUsuario() {
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

    private Imagem criarImagem() {
        Imagem imagem = new Imagem();
        imagem.setContentType("image/png");
        imagem.save();
        return imagem;
    }
}
