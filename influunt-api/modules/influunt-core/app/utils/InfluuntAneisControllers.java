package utils;

import models.ControladorFisico;
import play.libs.Json;
import status.AneisControlador;

import java.util.HashMap;
import java.util.List;

public class InfluuntAneisControllers {

    public InfluuntAneisControllers(){

        AneisControlador aneisControlador = new AneisControlador();
        HashMap<String, Integer> aneisPorControlador = new HashMap<>();
        List<ControladorFisico> todosControladoresFisicos = ControladorFisico.find.all();

        todosControladoresFisicos.stream().forEach(controladorFisico -> {
            aneisPorControlador.clear();
            aneisPorControlador.put(
                controladorFisico.getId().toString(),
                controladorFisico.getVersaoAtualControlador().getAneisAtivos().size()
            );
            aneisControlador.update(Json.toJson(aneisPorControlador).toString(), Json.toJson(aneisPorControlador).toString());
        });
    }
}
