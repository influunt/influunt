package com.example;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.Props;
import os.cet.driver.supervisores.Supervisor;
import os.cet.driver.controladores.ControladorFalso;

public class ApplicationMain {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("InfluuntSystem");
        ActorRef supervidor = system.actorOf(Supervisor.props(), "supervisor");
        system.awaitTermination();
    }

}