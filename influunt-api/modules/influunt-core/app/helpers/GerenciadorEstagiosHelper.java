package helpers;

import models.DiaDaSemana;
import models.Estagio;
import models.EstagioPlano;
import play.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.UUID;

/**
 * Created by leonardo on 11/14/16.
 */
public class GerenciadorEstagiosHelper {

    public static EstagioPlano getEstagioPlanoAlternativoDaTransicaoProibida(Estagio origem, Estagio destino, List<EstagioPlano> listaEstagioPlanos) {
        final Estagio estagioAtual = origem.getTransicaoProibidaPara(destino).getAlternativo();
        return listaEstagioPlanos.stream().filter(ep -> ep.getEstagio().equals(estagioAtual)).findFirst().orElse(null);
    }
}
