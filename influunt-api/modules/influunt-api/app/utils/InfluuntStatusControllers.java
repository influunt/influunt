package utils;

import models.Controlador;
import models.StatusVersao;
import play.libs.Json;
import status.StatusAtualControlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brunaseewald on 15/06/18.
 */
public class InfluuntStatusControllers {

    public InfluuntStatusControllers() {

        Map<String, Object> documentMapStatus = new HashMap<>();
        Map<String, Object> documentMapStatusQuantidadeEmConfig = new HashMap<>();
        Map<String, Object> documentMapStatusQuantidadeConfig = new HashMap<>();
        Map<String, Object> documentMapStatusQuantidadeRevisao = new HashMap<>();
        Map<String, Object> documentMapStatusQuantidadeSinc = new HashMap<>();
        Map<String, Object> documentMap = new HashMap<>();

        List<Controlador> todosControladores = Controlador.find.all();

        final int[] totalEmConfig = {0};
        final int[] totalAneisEmConfig = {0};

        final int[] totalConfig = {0};
        final int[] totalAneisConfig = {0};

        final int[] totalSinc = {0};
        final int[] totalAneisSinc = {0};

        final int[] totalEdit = {0};
        final int[] totalAneisEdit = {0};

        todosControladores.stream().forEach(controlador -> {
            if (controlador.getStatusVersao().equals(StatusVersao.EM_CONFIGURACAO)) {
                totalEmConfig[0]++;
                totalAneisEmConfig[0] += controlador.getAneisAtivos().size();
            }

            if (controlador.getStatusVersao().equals(StatusVersao.CONFIGURADO)) {
                totalConfig[0]++;
                totalAneisConfig[0] += controlador.getAneisAtivos().size();
            }

            if (controlador.getStatusVersao().equals(StatusVersao.SINCRONIZADO)) {
                totalSinc[0]++;
                totalAneisSinc[0] += controlador.getAneisAtivos().size();
            }

            if (controlador.getStatusVersao().equals(StatusVersao.EDITANDO)) {
                totalEdit[0]++;
                totalAneisEdit[0] += controlador.getAneisAtivos().size();
            }
        });

        documentMapStatusQuantidadeEmConfig.put("nControladores", totalEmConfig[0]);
        documentMapStatusQuantidadeEmConfig.put("nAneis", totalAneisEmConfig[0]);
        documentMapStatus.put("EM_CONFIGURACAO", documentMapStatusQuantidadeEmConfig);

        documentMapStatusQuantidadeConfig.put("nControladores", totalConfig[0]);
        documentMapStatusQuantidadeConfig.put("nAneis", totalAneisConfig[0]);
        documentMapStatus.put("CONFIGURADO", documentMapStatusQuantidadeConfig);

        documentMapStatusQuantidadeSinc.put("nControladores", totalSinc[0]);
        documentMapStatusQuantidadeSinc.put("nAneis", totalAneisSinc[0]);
        documentMapStatus.put("SINCRONIZADO", documentMapStatusQuantidadeSinc);

        documentMapStatusQuantidadeRevisao.put("nControladores", totalEdit[0]);
        documentMapStatusQuantidadeRevisao.put("nAneis", totalAneisEdit[0]);
        documentMapStatus.put("EDITANDO", documentMapStatusQuantidadeRevisao);

        documentMap.put("status", documentMapStatus);

        StatusAtualControlador statusAtualControlador = new StatusAtualControlador();
        statusAtualControlador.update("{}", Json.toJson(documentMap).toString());
    }
}
