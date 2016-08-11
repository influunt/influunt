package helpers;

import models.Controlador;
import models.Plano;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigosol on 6/15/16.
 */
public class TabelaHorarioHelper {

    private List<Plano> planos;

    public TabelaHorarioHelper(String id) {
        planos = Controlador.find.byId(UUID.fromString(id)).getAneis().stream().map(anel -> anel.getPlanos()).findAny().get();
    }

    public List<Plano> getPlanos() {
        return planos;
    }
}
