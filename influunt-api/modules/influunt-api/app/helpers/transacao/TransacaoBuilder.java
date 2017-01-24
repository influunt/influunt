package helpers.transacao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import json.ControladorCustomSerializer;
import models.Anel;
import models.Cidade;
import models.Controlador;
import models.ModoOperacaoPlano;
import play.libs.Json;
import protocol.*;
import status.PacoteTransacao;
import status.Transacao;
import utils.RangeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by pedropires on 1/24/17.
 */
public class TransacaoBuilder {

    public static PacoteTransacao pacotePlanos(List<Controlador> controladores, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            JsonNode pacotePlanosJson = new ControladorCustomSerializer().getPacotePlanosJson(controlador);
            transacoes.add(new Transacao(controladorId, pacotePlanosJson.toString(), TipoTransacao.PACOTE_PLANO));
        });
        return new PacoteTransacao(TipoTransacao.PACOTE_PLANO, timeout, transacoes);
    }

    public static PacoteTransacao configuracaoCompleta(List<Controlador> controladores, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        List<Cidade> cidades = Cidade.find.all();
        RangeUtils rangeUtils = RangeUtils.getInstance(null);

        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            JsonNode configuracaoJson = new ControladorCustomSerializer().getPacoteConfiguracaoCompletaJson(controlador, cidades, rangeUtils);
            transacoes.add(new Transacao(controladorId, configuracaoJson.toString(), TipoTransacao.CONFIGURACAO_COMPLETA));
        });
        return new PacoteTransacao(TipoTransacao.CONFIGURACAO_COMPLETA, timeout, transacoes);
    }

    public static PacoteTransacao tabelaHoraria(List<Controlador> controladores, boolean imediato, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            JsonNode pacoteTabelaHoraria = new ControladorCustomSerializer().getPacoteTabelaHorariaJson(controlador);
            ((ObjectNode) pacoteTabelaHoraria).put("imediato", imediato);
            transacoes.add(new Transacao(controlador.getControladorFisicoId(), pacoteTabelaHoraria.toString(), TipoTransacao.PACOTE_TABELA_HORARIA));
        });
        return new PacoteTransacao(TipoTransacao.PACOTE_TABELA_HORARIA, timeout, transacoes);
    }

    public static PacoteTransacao modoOperacao(List<Anel> aneis, ModoOperacaoPlano modoOperacao, Long horarioEntrada, int duracao, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        List<Controlador> controladores = aneis.stream().map(Anel::getControlador).distinct().collect(Collectors.toList());
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            List<Integer> numerosAneis = aneis.stream().filter(anel -> Objects.equals(controlador.getId(), anel.getControlador().getId())).map(Anel::getPosicao).collect(Collectors.toList());
            String payload = Json.toJson(new MensagemImposicaoModoOperacao(modoOperacao.toString(), numerosAneis, horarioEntrada, duracao)).toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_MODO_OPERACAO));
        });
        return new PacoteTransacao(TipoTransacao.IMPOSICAO_MODO_OPERACAO, timeout, transacoes);
    }

    public static PacoteTransacao plano(List<Anel> aneis, int posicaoPlano, Long horarioEntrada, int duracao, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        List<Controlador> controladores = aneis.stream().map(Anel::getControlador).distinct().collect(Collectors.toList());
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            List<Integer> numerosAneis = aneis.stream().filter(anel -> java.util.Objects.equals(controlador.getId(), anel.getControlador().getId())).map(Anel::getPosicao).collect(Collectors.toList());
            String payload = Json.toJson(new MensagemImposicaoPlano(posicaoPlano, numerosAneis, horarioEntrada, duracao)).toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO));
        });
        return new PacoteTransacao(TipoTransacao.IMPOSICAO_PLANO, timeout, transacoes);
    }

    public static PacoteTransacao planoTemporario(List<Anel> aneis, int posicaoPlano, Long horarioEntrada, int duracao, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        List<Controlador> controladores = aneis.stream().map(Anel::getControlador).distinct().collect(Collectors.toList());
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            List<Integer> numerosAneis = aneis.stream().filter(anel -> Objects.equals(controlador.getId(), anel.getControlador().getId())).map(Anel::getPosicao).collect(Collectors.toList());
            String payload = new MensagemImposicaoPlanoTemporario(controladorId, posicaoPlano, numerosAneis, horarioEntrada, duracao).toJson().toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.IMPOSICAO_PLANO_TEMPORARIO));
        });
        return new PacoteTransacao(TipoTransacao.IMPOSICAO_PLANO_TEMPORARIO, timeout, transacoes);
    }

    public static PacoteTransacao liberarImposicao(List<Anel> aneis, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        List<Controlador> controladores = aneis.stream().map(Anel::getControlador).distinct().collect(Collectors.toList());
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            List<Integer> numerosAneis = aneis.stream().filter(anel -> java.util.Objects.equals(controlador.getId(), anel.getControlador().getId())).map(Anel::getPosicao).collect(Collectors.toList());
            String payload = Json.toJson(new MensagemLiberarImposicao(numerosAneis)).toString();
            transacoes.add(new Transacao(controladorId, payload, TipoTransacao.LIBERAR_IMPOSICAO));
        });
        return new PacoteTransacao(TipoTransacao.LIBERAR_IMPOSICAO, timeout, transacoes);
    }

    public static PacoteTransacao colocarManutencao(List<Controlador> controladores, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            transacoes.add(new Transacao(controladorId, null, TipoTransacao.COLOCAR_CONTROLADOR_MANUTENCAO));
        });
        return new PacoteTransacao(TipoTransacao.COLOCAR_CONTROLADOR_MANUTENCAO, timeout, transacoes);
    }

    public static PacoteTransacao inativar(List<Controlador> controladores, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            transacoes.add(new Transacao(controladorId, null, TipoTransacao.INATIVAR_CONTROLADOR));
        });
        return new PacoteTransacao(TipoTransacao.INATIVAR_CONTROLADOR, timeout, transacoes);
    }

    public static PacoteTransacao ativar(List<Controlador> controladores, long timeout) {
        List<Transacao> transacoes = new ArrayList<>();
        controladores.stream().forEach(controlador -> {
            String controladorId = controlador.getControladorFisicoId();
            transacoes.add(new Transacao(controladorId, null, TipoTransacao.ATIVAR_CONTROLADOR));
        });
        return new PacoteTransacao(TipoTransacao.INATIVAR_CONTROLADOR, timeout, transacoes);
    }

}
