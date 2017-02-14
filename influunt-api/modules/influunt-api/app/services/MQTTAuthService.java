package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.ControladorFisico;
import play.Configuration;
import play.Logger;
import play.mvc.Http;
import play.mvc.StatusHeader;
import security.Authenticator;
import security.MqttCredentials;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static play.mvc.Results.ok;
import static play.mvc.Results.unauthorized;

/**
 * Created by rodrigosol on 1/26/17.
 */

@Singleton
public class MQTTAuthService {

    private final String centralPassword;

    private final List<String> aclDefinitionPubDevice;

    private final List<String> aclDefinitionSubDevice;

    private final List<String> aclDefinitionPubSimuladorWeb;

    private final List<String> aclDefinitionSubSimuladorWeb;

    private final List<String> aclDefinitionPubSimuladorApi;

    private final List<String> aclDefinitionSubSimuladorApi;

    private final List<String> aclDefinitionPubApp;

    private final List<String> aclDefinitionSubApp;

    @Inject
    Authenticator authenticator;

    private static final String CENTRAL = "central";

    private static final String DEVICE = "device_";

    private static final String APP = "influunt-app-";

    private static final String SIMULADOR_WEB = "simulador_web_";

    private static final String SIMULADOR_API = "sim_";

    private Map<String, Map<String, Function<MqttCredentials, StatusHeader>>> functions = new HashMap<>();

    @Inject
    public MQTTAuthService(Configuration configuration) {
        Configuration mqttConf = configuration.getConfig("central").getConfig("mqtt");
        Configuration aclConf = mqttConf.getConfig("acl");

        this.centralPassword = mqttConf.getString("senha");
        this.aclDefinitionPubDevice = aclConf.getConfig("device").getStringList("publish");
        this.aclDefinitionSubDevice = aclConf.getConfig("device").getStringList("subscribe");
        this.aclDefinitionPubApp = aclConf.getConfig("app").getStringList("publish");
        this.aclDefinitionSubApp = aclConf.getConfig("app").getStringList("subscribe");
        this.aclDefinitionPubSimuladorWeb = aclConf.getConfig("simulador_web").getStringList("publish");
        this.aclDefinitionSubSimuladorWeb = aclConf.getConfig("simulador_web").getStringList("subscribe");
        this.aclDefinitionPubSimuladorApi = aclConf.getConfig("simulador_api").getStringList("publish");
        this.aclDefinitionSubSimuladorApi = aclConf.getConfig("simulador_api").getStringList("subscribe");

        Map<String, Function<MqttCredentials, StatusHeader>> aclFunctions = new HashMap<>();
        aclFunctions.put(CENTRAL, this::aclCentral);
        aclFunctions.put(SIMULADOR_API, this::aclSimuladorApi);
        aclFunctions.put(SIMULADOR_WEB, this::aclSimuladorWeb);
        aclFunctions.put(DEVICE, this::aclDevice);
        aclFunctions.put(APP, this::aclApp);
        functions.put("acl", aclFunctions);

        Map<String, Function<MqttCredentials, StatusHeader>> authFunctions = new HashMap<>();
        authFunctions.put(CENTRAL, this::authCentral);
        authFunctions.put(SIMULADOR_API, this::authSimuladorApi);
        authFunctions.put(SIMULADOR_WEB, this::authSimuladorWeb);
        authFunctions.put(DEVICE, this::authDevice);
        authFunctions.put(APP, this::authApp);
        functions.put("auth", authFunctions);
    }

    public StatusHeader auth(MqttCredentials credentials) {
        return check("auth", credentials);
    }

    public StatusHeader acl(MqttCredentials credentials) {
        return check("acl", credentials);
    }



    private StatusHeader authSimuladorWeb(MqttCredentials credentials) {
        return ok();
    }

    private StatusHeader authSimuladorApi(MqttCredentials credentials) {
        return ok();
    }

    private StatusHeader authApp(MqttCredentials credentials) {
        return authenticator.getSubjectByToken(credentials.getPassword()) != null ? ok() : unauthorized();
    }

    private StatusHeader authDevice(MqttCredentials credentials) {
        ControladorFisico cf = ControladorFisico.find.byId(UUID.fromString(credentials.getUsername()));
        if (cf != null && cf.getPassword().equals(credentials.getPassword())) {
            return ok();
        } else {
            return unauthorized();
        }
    }

    private StatusHeader authCentral(MqttCredentials credentials) {
        return centralPassword.equals(credentials.getPassword()) ? ok() : unauthorized();
    }



    private StatusHeader aclCentral(MqttCredentials credentials) {
        return ok();
    }

    private StatusHeader aclSimuladorWeb(MqttCredentials credentials) {
        credentials.setUsername(credentials.getClientId().split("_")[2]);
        List<String> aclCopy = credentials.isSub() ? new ArrayList<>(aclDefinitionSubSimuladorWeb) : new ArrayList<>(aclDefinitionPubSimuladorWeb);
        return checkAcl(aclCopy, credentials) ? ok() : unauthorized();
    }

    private StatusHeader aclSimuladorApi(MqttCredentials credentials) {
        credentials.setUsername(credentials.getClientId().split("_")[1]);
        List<String> aclCopy = credentials.isSub() ? new ArrayList<>(aclDefinitionSubSimuladorApi) : new ArrayList<>(aclDefinitionPubSimuladorApi);
        return checkAcl(aclCopy, credentials) ? ok() : unauthorized();
    }

    private StatusHeader aclDevice(MqttCredentials credentials) {
        List<String> aclCopy = credentials.isSub() ? new ArrayList<>(aclDefinitionSubDevice) : new ArrayList<>(aclDefinitionPubDevice);
        return checkAcl(aclCopy, credentials) ? ok() : unauthorized();
    }

    private StatusHeader aclApp(MqttCredentials credentials) {
        List<String> aclCopy = credentials.isSub() ? new ArrayList<>(aclDefinitionSubApp) : new ArrayList<>(aclDefinitionPubApp);
        return checkAcl(aclCopy, credentials) ? ok() : unauthorized();
    }


    public StatusHeader check(String type, MqttCredentials credentials) {
        StatusHeader retorno;
        String cli = functions.get(type).keySet().stream()
            .filter(cliente -> credentials.getClientId().startsWith(cliente))
            .findFirst().orElse(null);
        if (cli == null) {
            retorno = unauthorized();
        } else {
            retorno  = functions.get(type).get(cli).apply(credentials);
        }
        return retorno;
    }

    private boolean checkAcl(List<String> acl, MqttCredentials credentials) {
        List<String[]> lista = acl.stream().map(topicString -> {
            return topicString.replace("$USERNAME", credentials.getUsername())
                .replace("$CLIENTID", credentials.getClientId())
                .replace("$PASSWORD", credentials.getPassword())
                .split("/");
        }).collect(Collectors.toList());

        return lista.stream().anyMatch(item -> {
            String[] topicSplitted = credentials.getTopic().split("/");
            if (item.length == topicSplitted.length) {
                for (int i = 0; i < item.length; i++) {
                    if (!item[i].equals("+")) {
                        if (!item[i].equals(topicSplitted[i])) {
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        });
    }
}
