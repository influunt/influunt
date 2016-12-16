package os72c.client.utils;

/**
 * Created by leonardo on 9/14/16.
 */
public class AtoresDevice {
    public final static String mqttActorPath(String idControlador) {
        return "akka://InfluuntSystem/user/".concat(idControlador).concat("/ControladorMQTT");
    }

    public final static String motor(String idControlador) {
        return "akka://InfluuntSystem/user/".concat(idControlador).concat("/motor");
    }

    public final static String deadLetterPath(String idControlador) {
        return "akka://InfluuntSystem/user/".concat(idControlador).concat("/DeadLettersActor");
    }
}
