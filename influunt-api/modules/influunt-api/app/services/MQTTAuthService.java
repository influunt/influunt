package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.ControladorFisico;
import play.Configuration;
import play.Logger;
import play.mvc.StatusHeader;
import security.Authenticator;
import security.MqttCredentials;

import java.util.UUID;


import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static play.mvc.Results.ok;
import static play.mvc.Results.unauthorized;

/**
 * Created by rodrigosol on 1/26/17.
 */

@Singleton
public class MQTTAuthService {

    private final String centralPassword;

    @Inject
    Authenticator authenticator;

    private static final String CENTRAL = "central";

    private static final String DEVICE = "device_";

    private static final String APP = "influunt-app-";

    private static final String SIMULADOR = "simulador_web_";

    @Inject
    public MQTTAuthService(Configuration configuration){
        this.centralPassword = configuration.getConfig("central").getConfig("mqtt").getString("senha");
    }

    public StatusHeader auth(MqttCredentials credentials) {
        Logger.error("Chamada ao authenticador");
        Logger.error(String.format("USERNAME:%s",credentials.getUsername()));
        Logger.error(String.format("CLIENTID:%s",credentials.getClientId()));
        Logger.error(String.format("PASSWORD:%s",credentials.getPassword()));

        if(credentials.getClientId().equals(CENTRAL)){
            return authCentral(credentials);
        }else if(credentials.getClientId().startsWith(DEVICE)){
            return authDevice(credentials);
        }else if(credentials.getClientId().startsWith(APP)){
            return authApp(credentials);
        }else if(credentials.getClientId().startsWith(SIMULADOR)){
            return ok();
        }else{
            return unauthorized();
        }

    }

    private StatusHeader authApp(MqttCredentials credentials) {
        Logger.error("Achou token:" + authenticator.getSubjectByToken(credentials.getPassword()));
        return authenticator.getSubjectByToken(credentials.getPassword()) != null ? ok() : unauthorized();
    }

    private StatusHeader authRoot(MqttCredentials credentials) {
        return null;
    }

    private StatusHeader authDevice(MqttCredentials credentials) {
        Logger.error("Autenticando device");

        ControladorFisico cf = ControladorFisico.find.byId(UUID.fromString(credentials.getUsername()));
        Logger.error("Controlador fisico:", cf);

        if(cf != null && cf.getPassword().equals(credentials.getPassword())){
            return ok();
        }else{
            return unauthorized();
        }
    }

    private StatusHeader authCentral(MqttCredentials credentials) {
        return centralPassword.equals(credentials.getPassword()) ? ok() : unauthorized();
    }

    public StatusHeader acl(MqttCredentials credentials) {

        if(credentials.getClientId().equals(CENTRAL)){
            return ok();
        }else if(credentials.getClientId().startsWith(DEVICE)){
            return aclDevice(credentials);
        }else if(credentials.getClientId().startsWith(APP)){
            return aclApp(credentials);
        }else if(credentials.getClientId().startsWith(SIMULADOR)){
            return aclSimulador();
        }else{
            return unauthorized();
        }
    }

    private StatusHeader aclSimulador(MqttCredentials credentials) {

        String simulacaoId = credentials.getClientId().split("_")[2];
        credentials.getTopic().equals("simulador/" + simulacaoId +"/estado");

        return null;
    }

    private StatusHeader aclApp(MqttCredentials credentials) {
        return null;
    }

    private StatusHeader aclDevice(MqttCredentials credentials) {
        return null;
    }
}
