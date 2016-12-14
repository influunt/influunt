package os72c.client.device;

import com.typesafe.config.Config;
import engine.EventoMotor;
import engine.IntervaloGrupoSemaforico;
import engine.TipoEvento;
import engine.TipoEventoControlador;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import logger.InfluuntLogger;
import logger.NivelLog;
import logger.TipoLog;
import models.TipoDetector;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import os72c.client.Client;
import os72c.client.exceptions.HardwareFailureException;
import play.Logger;
import protocol.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by rodrigosol on 11/4/16.
 */
public class SerialDeviceNativo implements DeviceBridge, SerialPortEventListener {


    private final Config settings;

    private final int startDelay;

    private final String porta;

    private final Integer baudrate;

    private final Integer databits;

    private final Integer stopbits;

    private final Integer parity;

    private DeviceBridgeCallback callback;

    private SerialPort serialPort;

    private ScheduledExecutorService executor;

    private Mensagem lastReturn = null;

    private long ultima = 0l;

    private int sequencia = 0;

    public SerialDeviceNativo() {
        settings = Client.getConfig().getConfig("serial");
        porta = settings.getString("porta");
        baudrate = settings.getInt("baudrate");
        databits = settings.getInt("databits");
        stopbits = settings.getInt("stopbits");
        parity = settings.getInt("parity");
        startDelay = settings.getInt("startdelay");

        InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,String.format("Iniciando a municacao serial"));
        InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,String.format("PORTA    :%s", porta));
        InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,String.format("BAUDRATE :%d", baudrate));
        InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,String.format("DATABITS :%d", databits));
        InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,String.format("STOPBITS :%d", stopbits));
        InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,String.format("PARITY   :%d", parity));
        InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,String.format("START DELAY :%s", startDelay));
    }

    public void start(DeviceBridgeCallback deviceBridgeCallback) {
        this.callback = deviceBridgeCallback;
        this.executor = Executors.newScheduledThreadPool(1);

        serialPort = new SerialPort(porta);

        try {
            InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,"Abrindo a porta de comunicação");
            serialPort.openPort();//Open serial port

            serialPort.setParams(baudrate, databits, stopbits, parity);
            InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,"Cumprindo delay");
            Thread.sleep(startDelay);
            InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,"Limpando buffer");
            serialPort.readBytes();
            serialPort.purgePort(SerialPort.PURGE_RXCLEAR);
            serialPort.purgePort(SerialPort.PURGE_TXCLEAR);
            serialPort.readBytes();
            serialPort.addEventListener(this);
            InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,"Comunicação serial pronta para iniciar");
        } catch (SerialPortException spe) {
            InfluuntLogger.log(NivelLog.NORMAL,TipoLog.ERRO,"Não foi possível iniciar a comunicação serial");
            spe.printStackTrace();
            throw new HardwareFailureException(spe.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modoManualDesativado() {
        send(new MensagemModoManualAtivado(TipoDeMensagemBaixoNivel.MODO_MANUAL_DESATIVADO, sequencia));
    }

    @Override
    public void modoManualAtivo() {
        send(new MensagemModoManualAtivado(TipoDeMensagemBaixoNivel.MODO_MANUAL_ATIVADO, sequencia));
    }

    private void send(Mensagem mensagem) {
        try {
            byte[] bytes = mensagem.toByteArray();
            InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,String.format("Enviando %d bytes pela serial",bytes.length));
            serialPort.writeBytes(bytes);
        } catch (SerialPortException e) {
            InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.ERRO,e.getMessage());
            e.printStackTrace();
        }

    }


    @Override
    public void sendEstagio(IntervaloGrupoSemaforico intervaloGrupoSemaforico) {
        MensagemEstagio mensagem = new MensagemEstagio(TipoDeMensagemBaixoNivel.ESTAGIO, getSequencia(),
            intervaloGrupoSemaforico.quantidadeGruposSemaforicos());
        mensagem.addIntervalos(intervaloGrupoSemaforico);
        InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,mensagem.print());
        send(mensagem);
    }


    private Integer getSequencia() {
        if (sequencia + 1 > 65535) {
            sequencia = 0;
        }
        return ++sequencia;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0) {//If data is available

            int bytesCount = serialPortEvent.getEventValue();
            try {

                byte[] size = serialPort.readBytes(1);
                byte[] complement = serialPort.readBytes(size[0] - 1);
                byte[] msg = ArrayUtils.addAll(size, complement);
                Mensagem mensagem = Mensagem.toMensagem(msg);
                mensagemRecebida(mensagem);
                InfluuntLogger.log(NivelLog.DETALHADO,TipoLog.EXECUCAO,mensagem.getTipoMensagem().toString());
            } catch (Exception e) {
                InfluuntLogger.log(NivelLog.NORMAL,TipoLog.ERRO,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void mensagemRecebida(Mensagem mensagem) {
        TipoEvento te = null;
        TipoDetector tipoDetector = null;
        Pair<Integer, TipoDetector> pair = null;

        switch (mensagem.getTipoMensagem()) {
            case RETORNO:
                lastReturn = mensagem;
                break;
            case DETECTOR:
                final MensagemDetector mensagemDetector = (MensagemDetector) mensagem;
                te = mensagemDetector.isPedestre() ? TipoEvento.ACIONAMENTO_DETECTOR_PEDESTRE :
                    TipoEvento.ACIONAMENTO_DETECTOR_VEICULAR;
                final int codigo = ((MensagemDetector) mensagem).getPosicao();
                tipoDetector = ((MensagemDetector) mensagem).isPedestre() ? TipoDetector.PEDESTRE :
                    TipoDetector.VEICULAR;
                pair = new Pair<Integer, TipoDetector>(codigo, tipoDetector);
                callback.onEvento(new EventoMotor(DateTime.now(), te, pair));
                break;
            case FALHA_ANEL:
                final MensagemFalhaAnel mensagemFalhaAnel = (MensagemFalhaAnel) mensagem;
                callback.onEvento(new EventoMotor(DateTime.now(),
                    TipoEvento.getByTipoECodigo(TipoEventoControlador.FALHA, mensagemFalhaAnel.getFalha()),
                    mensagemFalhaAnel.getAnel()));
                break;
            case FALHA_DETECTOR:
                te = TipoEvento.getByTipoECodigo(TipoEventoControlador.FALHA, ((MensagemFalhaDetector) mensagem).getFalha());
                final int posicao = ((MensagemFalhaDetector) mensagem).getPosicao();
                tipoDetector = ((MensagemFalhaDetector) mensagem).isPedestre() ? TipoDetector.PEDESTRE :
                    TipoDetector.VEICULAR;
                pair = new Pair<Integer, TipoDetector>(posicao, tipoDetector);
                callback.onEvento(new EventoMotor(DateTime.now(), te, pair));
                break;
            case FALHA_GRUPO_SEMAFORICO:
                te = TipoEvento.getByTipoECodigo(TipoEventoControlador.FALHA, ((MensagemFalhaGrupoSemaforico) mensagem).getFalha());
                callback.onEvento(new EventoMotor(DateTime.now(), te, ((MensagemFalhaGrupoSemaforico) mensagem).getPosicao()));
                break;
            case FALHA_GENERICA:
                te = TipoEvento.getByTipoECodigo(TipoEventoControlador.FALHA, ((MensagemFalhaGenerica) mensagem).getFalha());
                callback.onEvento(new EventoMotor(DateTime.now(), te));
                break;
            case REMOCAO_GENERICA:
                te = TipoEvento.getByTipoECodigo(TipoEventoControlador.REMOCAO_FALHA, ((MensagemRemocaoFalha) mensagem).getFalha());
                callback.onEvento(new EventoMotor(DateTime.now(), te, ((MensagemRemocaoFalha) mensagem).getAnel()));
                break;
            case ALARME:
                te = TipoEvento.getByTipoECodigo(TipoEventoControlador.ALARME, ((MensagemAlarme) mensagem).getAlarme());
                callback.onEvento(new EventoMotor(DateTime.now(), te));
                break;
            case INSERCAO_PLUG:
                callback.onEvento(new EventoMotor(DateTime.now(), TipoEvento.INSERCAO_DE_PLUG_DE_CONTROLE_MANUAL));
                break;
            case REMOCAO_PLUG:
                callback.onEvento(new EventoMotor(DateTime.now(), TipoEvento.RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL));
                break;
            case TROCA_ESTAGIO_MANUAL:
                callback.onEvento(new EventoMotor(DateTime.now(), TipoEvento.TROCA_ESTAGIO_MANUAL));
                break;
        }

    }

    public void sendMensagem(TipoDeMensagemBaixoNivel inicio) {
        send(new MensagemInicio(TipoDeMensagemBaixoNivel.INICIO, getSequencia()));
    }
}
