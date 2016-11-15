package os72c.client.device;

import engine.IntervaloGrupoSemaforico;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.apache.commons.lang.ArrayUtils;
import os72c.client.exceptions.HardwareFailureException;
import protocol.*;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by rodrigosol on 11/4/16.
 */
public class SerialDevice implements DeviceBridge, SerialPortEventListener {


    private SerialPort serialPort;

    private String porta = "/dev/tty.usbmodemFD131";

    private Integer baudrate  = 115200;

    private Integer databits = 8;

    private Integer stopbits = 1;

    private Integer parity = 0;

    private ScheduledExecutorService executor;

    private Mensagem lastReturn = null;

    public SerialDevice(){
        this.executor = Executors.newScheduledThreadPool(1);

        serialPort = new SerialPort(porta);

        try {
            serialPort.openPort();//Open serial port
            serialPort.addEventListener(this);
            serialPort.setParams(baudrate, databits, stopbits, parity);

            serialPort.readBytes();
        } catch (SerialPortException spe) {
            spe.printStackTrace();
            throw new HardwareFailureException(spe.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private  void send(Mensagem mensagem)  {
        System.out.println("Enviado mensagem:" + mensagem.getSequencia());
        try {
            serialPort.writeBytes(mensagem.toByteArray());
//            Callable<Mensagem> run = new Callable<Mensagem>()
//            {
//                @Override
//                public Mensagem call() throws Exception
//                {
//
//                    while(true){
//                        if(lastReturn != null){
//                            return lastReturn;
//                        }
//                        Thread.sleep(10);
//                    }
//                }
//            };
//            RunnableFuture<Mensagem> future = new FutureTask(run);
//            ExecutorService service = Executors.newSingleThreadExecutor();
//            service.execute(future);
//            Mensagem result = null;
//            try
//            {
//                result = future.get(10000, TimeUnit.MILLISECONDS);    // wait 100 milis
//                if(result.getSequencia() != mensagem.getSequencia()){
//                    System.out.println("Retorno invalido");
//                }
//            }
//            catch (TimeoutException ex)
//            {
//                System.out.println("Deu timeout!");
//                //Todo:Tratar timeout
//                future.cancel(true);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//
//            service.shutdown();

        } catch (SerialPortException e) {
            e.printStackTrace();
        }

    }

    private void noResponse(int sequencia) {

    }

    private int sequencia = 0;

    @Override
    public void sendEstagio(IntervaloGrupoSemaforico intervaloGrupoSemaforico) {
        MensagemEstagio mensagem = new MensagemEstagio(TipoDeMensagemBaixoNivel.ESTAGIO,getSequencia(),
           intervaloGrupoSemaforico.quantidadeGruposSemaforicos());
        mensagem.addIntervalos(intervaloGrupoSemaforico);
        mensagem.print();
        send(mensagem);
    }


    private Integer getSequencia() {
        if(sequencia + 1 > 65535){
            sequencia = 0;
        }
        return ++sequencia;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        System.out.println("RX recebido");
        if (serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0) {//If data is available

            int bytesCount = serialPortEvent.getEventValue();
            try {

                byte[] size = serialPort.readBytes(1);
                byte[] complement = serialPort.readBytes(size[0] - 1);
                byte[] msg = ArrayUtils.addAll(size, complement);
                Mensagem mensagem = Mensagem.toMensagem(msg);
                mensagemRecebida(mensagem);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    private void mensagemRecebida(Mensagem mensagem) {
        if(mensagem instanceof MensagemRetorno){
            lastReturn = mensagem;
        }else{
            //Trata eventos
        }
    }

    public void sendMensagem(TipoDeMensagemBaixoNivel inicio) {
        send(new MensagemInicio(TipoDeMensagemBaixoNivel.INICIO,getSequencia()));
    }
}
