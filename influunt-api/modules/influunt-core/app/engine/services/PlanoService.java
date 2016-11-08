package engine.services;

import models.*;
import play.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.UUID;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by leonardo on 11/7/16.
 */
public class PlanoService {
    public static Plano gerarPlanoManual(Plano plano) {
        Plano novoPlano = gerarPlano(plano);

        plano.getEstagiosPlanos().forEach(estagioPlano -> {
            EstagioPlano estagioPlanoAux = copyPrimitiveFields(estagioPlano);
            estagioPlanoAux.setEstagio(estagioPlano.getEstagio());
            estagioPlanoAux.setPlano(novoPlano);
            novoPlano.addEstagios(estagioPlanoAux);
        });

        novoPlano.setModoOperacao(ModoOperacaoPlano.MANUAL);
        return novoPlano;
    }

    public static Plano gerarPlanoIntermitentePorFalha(Plano plano) {
        Plano novoPlano = gerarPlano(plano);
        novoPlano.setModoOperacao(ModoOperacaoPlano.INTERMITENTE);
        novoPlano.setImpostoPorFalha(true);
        return novoPlano;
    }

    private static Plano gerarPlano(Plano plano) {
        Plano novoPlano = copyPrimitiveFields(plano);

        novoPlano.setVersaoPlano(plano.getVersaoPlano());


        plano.getGruposSemaforicosPlanos().forEach(grupoSemaforicoPlano -> {
            GrupoSemaforicoPlano gspAux = copyPrimitiveFields(grupoSemaforicoPlano);
            gspAux.setGrupoSemaforico(grupoSemaforicoPlano.getGrupoSemaforico());
            gspAux.setPlano(novoPlano);
            novoPlano.addGruposSemaforicoPlano(gspAux);
        });

        return novoPlano;
    }


    private static <T> T copyPrimitiveFields(T obj) {
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
}
