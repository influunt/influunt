package os72c.client.virtual;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.google.inject.Guice;
import com.google.inject.util.Modules;
import com.typesafe.config.ConfigFactory;
import os72c.client.conn.ClientActor;
import os72c.client.device.DeviceBridge;
import play.api.Play;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by rodrigosol on 11/26/16.
 */
public class ControladorSettingsForm {
    private JTextField idControlador;

    private JTextField chavePrivada;

    private JTextField chavePublica;

    private JTextField host;

    private JButton iniciarControladorButton;

    private JTextField port;

    public JPanel form;


    public ControladorSettingsForm(){
        iniciarControladorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
    }

    private void start() {

        ActorSystem system = ActorSystem.create("InfluuntControlador", ConfigFactory.load());
        system.actorOf(Props.create(ClientActor.class), "ClientActor");
        system.awaitTermination();
    }


    public static void main(String args[]){
        JFrame frame = new JFrame("ControladorSettingsForm");
        frame.setContentPane(new ControladorSettingsForm().form);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
