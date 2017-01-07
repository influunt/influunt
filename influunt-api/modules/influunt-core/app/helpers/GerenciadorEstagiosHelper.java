package helpers;

import models.Estagio;
import models.EstagioPlano;

import java.util.List;

/**
 * Created by leonardo on 11/14/16.
 */
public class GerenciadorEstagiosHelper {

    public static final long TEMPO_SEQUENCIA_DE_PARTIDA = 8000L;

    public static final long TEMPO_VERMELHO_INTEGRAL = 3000L;

    public static EstagioPlano getEstagioPlanoAlternativoDaTransicaoProibida(Estagio origem, Estagio destino, List<EstagioPlano> listaEstagioPlanos) {
        final Estagio estagioAtual = origem.getTransicaoProibidaPara(destino).getAlternativo();
        return listaEstagioPlanos.stream().filter(ep -> ep.getEstagio().equals(estagioAtual)).findFirst().orElse(null);
    }
}
