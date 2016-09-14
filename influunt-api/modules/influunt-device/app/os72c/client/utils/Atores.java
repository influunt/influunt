package os72c.client.utils;

/**
 * Created by leonardo on 9/14/16.
 */
public class Atores {
    public final static String mqttActorPath(String idControlador) {
        return "akka://InfluuntSystem/user/" + idControlador + "/ControladorMQTT";
    }
}
