package helpers;

import models.Anel;
import models.Controlador;
import models.ModoOperacaoPlano;

import java.util.List;

/**
 * Created by pedropires on 1/23/17.
 */
public class TransacaoHelper {
    public String enviarPacotePlanos(List<Controlador> controladores, long timeout) {
        return "";
    }

    public String enviarConfiguracaoCompleta(List<Controlador> controladores, long timeout) {
        return "";
    }

    public String enviarTabelaHoraria(List<Controlador> controladores, boolean imediato, long timeout) {
        return "";
    }

    public String imporModoOperacao(List<Anel> aneis, ModoOperacaoPlano modoOperacao, Long horarioEntrada, int duracao, long timeout) {
        return "";
    }

    public String imporPlano(List<Anel> aneis, int posicaoPlano, Long horarioEntrada, int duracao, long timeout) {
        return "";
    }

    private String imporPlanoTemporario(List<Anel> aneis, int posicaoPlano, Long horarioEntrada, int duracao, long timeout) {
        return "";
    }

    public String liberarImposicao(List<Anel> aneis, long timeout) {
        return "";
    }

    public String lerDados(Controlador controlador) {
        return "";
    }

    public String colocarControladorManutencao(List<Controlador> controladores, long timeout) {
        return "";
    }


    public String inativarControlador(List<Controlador> controladores, long timeout) {
        return "";
    }

    public String ativarControlador(List<Controlador> controladores, long timeout) {
        return "";
    }
}
