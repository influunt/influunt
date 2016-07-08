package os72c.controladores;

import akka.actor.ActorRef;
import jssc.SerialPort;
import jssc.SerialPortException;
import os72c.Constants;
import os72c.exceptions.HardwareFailureException;
import os72c.models.EstadoGrupo;
import scala.Int;

/**
 * Created by rodrigosol on 6/28/16.
 */
public class ControladorSerial extends Controlador {
    private SerialPort serialPort;
    private String porta;
    private Integer baudrate;
    private Integer databits;
    private Integer stopbits;
    private Integer parity;
    private Integer startDelay;


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
            serialPort.setParams(baudrate, databits, stopbits, parity);
            Thread.sleep(2000);

        } catch (SerialPortException spe) {
            spe.printStackTrace();
            throw new HardwareFailureException(spe.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }

        pronto("Raro Labs", "Arduino", "1.0");
    }

    @Override
    protected void entrarEmModoAmarelhoIntermitente() {

    }

    @Override
    protected void onChange(EstadoGrupo[] estadoDosGrupos, int tempoRestante) throws Exception {
        String command = "";
        for (EstadoGrupo estado : estadoDosGrupos) {
            switch (estado) {
                case VERDE:
                    command += "1,";
                    break;
                case AMARELHO:
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
            }

        }
        command += (String.valueOf(tempoRestante / 1000));
        System.out.println("*************:" + command);
        System.out.println(System.currentTimeMillis());
        if (serialPort.isOpened()) {
            serialPort.writeBytes(command.getBytes());
            byte[] retorno = serialPort.readBytes(1,1500);
            if(retorno[0] != 1){
                throw new SerialPortException(porta, "onChange", "Não foi possível comunicar pela porta serial");
            }
        } else {
            throw new SerialPortException(porta, "onChange", "Não foi possível comunicar pela porta serial");
        }
    }

}
