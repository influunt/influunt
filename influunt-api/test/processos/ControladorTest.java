package processos;


import checks.InfluuntValidator;
import fixtures.ControladorFixture;
import models.*;
import play.Application;
import play.Logger;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

import java.util.*;

import static play.test.Helpers.inMemoryDatabase;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ControladorTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        Map<String, String> options = new HashMap<String, String>();
        options.put("DATABASE_TO_UPPER", "FALSE");
        return getApplication(inMemoryDatabase("default", options));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Application getApplication(Map configuration) {
        return new GuiceApplicationBuilder().configure(configuration).in(Mode.TEST).build();
    }


    protected Controlador getControladorComDadosBasicos(){
        return ControladorFixture.getControladorComDadosBasicos();
    }

    protected Controlador getControladorComAneis() {
        return ControladorFixture.getControladorComAneis();
    }

    protected Controlador getControladorComAssociacao() {
        return ControladorFixture.getControladorComAssociacao();
    }

    protected Controlador getControladorComVerdesConflitantes() {
        return ControladorFixture.getControladorComVerdesConflitantes();
    }

    protected void imprimeErros(List<InfluuntValidator.Erro> erros){
        int i = 0;
        for(InfluuntValidator.Erro erro :erros){
            Logger.debug(i + ":" + erro.path);
            Logger.debug(i + ":" + erro.message);
            i++;
        }
    }
}
