package utils;

public class AtoresCentral {
    public static final String mqttActorPath() {
        return "akka://application/user/servidor/CentralMQTT";
    }

    public static final String transacaoActorPath(String id) {
        return "akka://application/user/servidor/*/*/transacao-" + id;
    }

    public static final String messageBroker() {
        return "akka://application/user/servidor/centralMessageBroker";
    }
}
