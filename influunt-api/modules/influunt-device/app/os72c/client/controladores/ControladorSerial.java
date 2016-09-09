package os72c.client.controladores;

import akka.actor.ActorRef;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import os72c.client.exceptions.HardwareFailureException;
import os72c.client.models.EstadoGrupo;
import os72c.client.utils.Constants;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * Created by rodrigosol on 6/28/16.
 */
public class ControladorSerial extends Controlador implements SerialPortEventListener {
    private SerialPort serialPort;
    private String porta;
    private Integer baudrate;
    private Integer databits;
    private Integer stopbits;
    private Integer parity;
    private Integer startDelay;
    private boolean ack = false;

    private StringBuilder message = new StringBuilder();


    @Override
    protected void supervisorPronto(ActorRef supervisor, String[] argumentos) {

        porta = argumentos[Constants.SERIAL_PORTA];
        baudrate = Integer.valueOf(argumentos[Constants.SERIAL_BAUDRATE]);
        databits = Integer.valueOf(argumentos[Constants.SERIAL_DATABITS]);
        stopbits = Integer.valueOf(argumentos[Constants.SERIAL_STOPBITS]);
        parity = Integer.valueOf(argumentos[Constants.SERIAL_PARITY]);
        startDelay = Integer.valueOf(argumentos[Constants.SERIAL_START_DELAY]);

        serialPort = new SerialPort(porta);

        try {

            serialPort.openPort();//Open serial port
            serialPort.addEventListener(this, SerialPort.MASK_RXCHAR);
            serialPort.setParams(baudrate, databits, stopbits, parity);
            Thread.sleep(3000);
            serialPort.writeBytes("START;".getBytes());
        } catch (SerialPortException spe) {
            spe.printStackTrace();
            throw new HardwareFailureException(spe.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void entrarEmModoAmarelhoIntermitente() throws SerialPortException {

        if (serialPort.isOpened()) {
            serialPort.writeBytes("AI;".getBytes());
        } else {
            throw new SerialPortException(porta, "onChange", "Não foi possível comunicar pela porta serial");
        }

    }

    @Override
    protected void onChange(EstadoGrupo[] estadoDosGrupos, int tempoRestante) throws Exception {
        String command = "G" + estadoDosGrupos.length + ",";
        for (EstadoGrupo estado : estadoDosGrupos) {
            switch (estado) {
                case VERDE:
                    command += "1,";
                    break;
                case AMARELO:
                    command += "2,";
                    break;
                case VERMELHO:
                    command += "3,";
                    break;
                case DESLIGADO:
                    command += "0,";
                    break;
                case VERMELHO_INTERMITENTE:
                    command += "4,";
                    break;
                case AMARELHO_INTERMITENTE:
                    command += "5,";

            }

        }
        command += (String.valueOf(tempoRestante / 1000) + ";");
        System.out.println(command);
        if (serialPort.isOpened()) {
            serialPort.writeBytes(command.getBytes());
            ack = false;
        } else {
            System.out.println("ACK:" + ack);
            throw new SerialPortException(porta, "onChange", "Não foi possível comunicar pela porta serial");
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                byte buffer[] = serialPort.readBytes();
                for (byte b : buffer) {
                    if ((b == '\r' || b == '\n') && message.length() > 0) {
                        String toProcess = message.toString();
                        CompletableFuture.supplyAsync(() -> {
                            return processMessage(toProcess);
                        });
                        message.setLength(0);
                    } else {
                        message.append((char) b);
                    }
                }
            } catch (SerialPortException ex) {
                System.out.println(ex);
                System.out.println("serialEvent");
            }
        }

    }


    private boolean processMessage(String msg) {
        msg = msg.trim();
        System.out.println(msg);
        if (msg.endsWith("ACK;")) {
            //Permite o envio da proxima mudancao de grupo
            ack = true;
        } else if (msg.startsWith("OK")) {
            String fields[] = msg.split(",");
            Arrays.stream(fields).map(s -> s.trim());
            pronto(fields[1], fields[2], fields[3]);
            ack = true;

        } else {
            System.out.println("Interrupcao Recebida");
            ack = true;
            interruption(msg);

        }
        return true;
    }

//        if(serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0) {
//            try {
//                String receivedData = "";
//                String receivedChar = "";
//                while(!(receivedChar = new String(serialPort.readBytes(1),"UTF-8")).equals(";")){
//                    receivedData+=receivedChar;
//                }
//                System.out.println("Received response: " + receivedData);
//                if(receivedData.startsWith("ACK")){
//                    //Permite o envio da proxima mudancao de grupo
//                    ack = true;
//                }else if(receivedData.matches("([PVENM]\\d+)")){
//                    System.out.println("Interrupcao Recebida");
//                    serialPort.writeBytes("ACK;".getBytes());
//                }
//            }
//            catch (SerialPortException ex) {
//                System.out.println("Error in receiving string from COM-port: " + ex);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }

}
