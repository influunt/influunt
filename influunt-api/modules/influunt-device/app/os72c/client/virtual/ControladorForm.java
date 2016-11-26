package os72c.client.virtual;

import engine.EventoMotor;
import engine.IntervaloGrupoSemaforico;
import engine.TipoEvento;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import os72c.client.device.DeviceBridge;
import os72c.client.device.DeviceBridgeCallback;

import protocol.MensagemEstagio;
import protocol.TipoDeMensagemBaixoNivel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigosol on 11/26/16.
 */
public class ControladorForm implements Sender, DeviceBridge {
    private ActionListener anelActionListener;

    private ActionListener detectorActionListerner;

    public JPanel form;

    private JButton btnDP1;

    private JButton btnDV4;

    private JButton btnDV8;

    private JButton btnDV6;

    private JButton btnDV5;

    private JButton btnDP2;

    private JButton btnDV3;

    private JButton btnDV2;

    private JButton btnDV1;

    private JButton btnDP4;

    private JButton btnDP3;

    private JButton btnDV7;

    private JButton btnInserirPlug;

    private JButton btnRemoverPlug;

    private JButton btnTrocarEstagio;

    private JButton btnFaseVermelhaApagada;

    private JButton btnVerdesConflitantes;

    private JButton btnSequenciaDeCores;

    private JButton btnTempoMaxPermEstágio;

    private JButton btnDetectorPedestreDireto;

    private JButton btnCPU;

    private JButton btnMemoria;

    private JButton btnPortaPrincipal;

    private JButton btnPortalDePainel;

    private JButton btnIntermitente;

    private JButton btnSemaforoApagado;

    private JButton btnAcertoRelogio;

    private JButton btnFocoApagado;

    private JTextField txtLog;

    private JLabel g1;

    private JLabel g2;

    private JLabel g3;

    private JLabel g4;

    private JLabel g5;

    private JLabel g6;

    private JLabel g7;

    private JLabel g8;

    private JLabel g9;

    private JLabel g10;

    private JLabel g11;

    private JLabel g12;

    private JLabel g13;

    private JLabel g14;

    private JLabel g15;

    private JLabel g16;

    private JLabel tempoDecorrido;

    private JLabel relogio;

    private JButton btnDetectorPedestreFalta;

    private JButton btnDetectorVeicularDireto;

    private JButton btnDetectorVeicularFalta;

    private IntervaloGrupoSemaforico intervaloGrupoSemaforico;

    private Map<Integer, JLabel> labelsEstados = new HashMap<>();

    private Long time;

    int colors[] = new int[16];

    int times[][] = new int[16][5];

    private final int DESLIGADO = 0;

    private final int VERDE = 1;

    private final int VERMELHO = 2;

    private final int AMARELO = 3;

    private final int VERMELHO_INTERMITENTE = 4;

    private final int AMARELO_INTERMITENTE = 5;

    private final int VERMELHO_LIMPEZA = 6;

    int tempo = 0;

    int clock = 0;


    private ScheduledFuture<?> executor;

    private DeviceBridgeCallback callback;

    public ControladorForm() {

        start();

    }


    @Override
    public void send(EventoMotor eventoMotor) {
        System.out.println(eventoMotor);
    }

    @Override
    public void sendEstagio(IntervaloGrupoSemaforico intervaloGrupoSemaforico) {
        this.intervaloGrupoSemaforico = intervaloGrupoSemaforico;
        MensagemEstagio msg = new MensagemEstagio(TipoDeMensagemBaixoNivel.ESTAGIO, 0, intervaloGrupoSemaforico.quantidadeGruposSemaforicos());
        msg.addIntervalos(intervaloGrupoSemaforico);
        //msg.print();
        onReceiveEstage(msg.toByteArray());
    }

    @Override
    public void start(DeviceBridgeCallback callback) {
        this.callback = callback;
        detectorActionListerner = new DetectorActionListerner(callback);
        start();
    }

    @Override
    public void modoManualAtivo() {

    }

    @Override
    public void modoManualDesativado() {

    }

    private void start() {
        labelsEstados.put(1, g1);
        g1.setOpaque(true);

        labelsEstados.put(2, g2);
        g2.setOpaque(true);

        labelsEstados.put(3, g3);
        g3.setOpaque(true);

        labelsEstados.put(4, g4);
        g4.setOpaque(true);

        labelsEstados.put(5, g5);
        g5.setOpaque(true);

        labelsEstados.put(6, g6);
        g6.setOpaque(true);

        labelsEstados.put(7, g7);
        g7.setOpaque(true);

        labelsEstados.put(8, g8);
        g8.setOpaque(true);

        labelsEstados.put(9, g9);
        g9.setOpaque(true);

        labelsEstados.put(10, g10);
        g10.setOpaque(true);

        labelsEstados.put(11, g11);
        g11.setOpaque(true);

        labelsEstados.put(12, g12);
        g12.setOpaque(true);

        labelsEstados.put(13, g13);
        g13.setOpaque(true);

        labelsEstados.put(14, g14);
        g14.setOpaque(true);

        labelsEstados.put(15, g15);
        g15.setOpaque(true);

        labelsEstados.put(16, g16);
        g16.setOpaque(true);

        btnDP1.addActionListener(detectorActionListerner);
        btnDP2.addActionListener(detectorActionListerner);
        btnDP3.addActionListener(detectorActionListerner);
        btnDP4.addActionListener(detectorActionListerner);

        btnDV1.addActionListener(detectorActionListerner);
        btnDV2.addActionListener(detectorActionListerner);
        btnDV3.addActionListener(detectorActionListerner);
        btnDV4.addActionListener(detectorActionListerner);
        btnDV5.addActionListener(detectorActionListerner);
        btnDV6.addActionListener(detectorActionListerner);
        btnDV7.addActionListener(detectorActionListerner);
        btnDV8.addActionListener(detectorActionListerner);

        btnFaseVermelhaApagada.addActionListener(new GrupoSemaforicoActionListener(this, TipoEvento.FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA,TipoEvento.REMOCAO_FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO));
        btnVerdesConflitantes.addActionListener(new AnelActionListener(this, TipoEvento.FALHA_VERDES_CONFLITANTES,TipoEvento.REMOCAO_FALHA_VERDES_CONFLITANTES));
        btnSequenciaDeCores.addActionListener(new AnelActionListener(this, TipoEvento.FALHA_SEQUENCIA_DE_CORES,null));
        btnTempoMaxPermEstágio.addActionListener(new AnelActionListener(this, TipoEvento.FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO,null));


        btnDetectorPedestreDireto.addActionListener(new DetectorPedestreActionListener(this, TipoEvento.FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO,TipoEvento.REMOCAO_FALHA_DETECTOR_PEDESTRE));
        btnDetectorPedestreFalta.addActionListener(new DetectorPedestreActionListener(this, TipoEvento.FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO,TipoEvento.REMOCAO_FALHA_DETECTOR_PEDESTRE));

        btnDetectorVeicularDireto.addActionListener(new DetectorVeicularActionListener(this, TipoEvento.FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO,TipoEvento.REMOCAO_FALHA_DETECTOR_VEICULAR));
        btnDetectorVeicularFalta.addActionListener(new DetectorVeicularActionListener(this, TipoEvento.FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO,TipoEvento.REMOCAO_FALHA_DETECTOR_VEICULAR));

        btnCPU.addActionListener(new ControladorActionListener(this, TipoEvento.FALHA_WATCH_DOG,null));
        btnMemoria.addActionListener(new ControladorActionListener(this, TipoEvento.FALHA_MEMORIA,null));

        btnPortaPrincipal.addActionListener(new ControladorActionListener(this,TipoEvento.ALARME_ABERTURA_DA_PORTA_PRINCIPAL_DO_CONTROLADOR,TipoEvento.ALARME_FECHAMENTO_DA_PORTA_PRINCIPAL_DO_CONTROLADOR));
        btnPortalDePainel.addActionListener(new ControladorActionListener(this,TipoEvento.ALARME_ABERTURA_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR,TipoEvento.ALARME_FECHAMENTO_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR));
        btnIntermitente.addActionListener(new AnelActionListener(this, TipoEvento.ALARME_AMARELO_INTERMITENTE,null));
        btnSemaforoApagado.addActionListener(new AnelActionListener(this, TipoEvento.ALARME_SEMAFORO_APAGADO,null));
        btnAcertoRelogio.addActionListener(new AnelActionListener(this, TipoEvento.ALARME_ACERTO_RELOGIO_GPS,null));
        btnFocoApagado.addActionListener(new AnelActionListener(this, TipoEvento.ALARME_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA,TipoEvento.ALARME_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_REMOCAO));



        JFrame frame = new JFrame("ControladorForm");
        frame.setContentPane(this.form);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800,800));
        frame.pack();
        frame.setVisible(true);

        executor = Executors.newScheduledThreadPool(1)
            .scheduleAtFixedRate(() -> {
                try {
                    lights();
                    tempoDecorrido.setText(String.valueOf(tempo/1000) + "s");
                    relogio.setText(DateTime.now().toString("dd/MM/yyyy HH:mm:ss"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 0, 100, TimeUnit.MILLISECONDS);

    }


    private int whereIsTime(int line) {
        if (times[line][1] >= 100) {
            return 1;
        } else if (times[line][2] >= 100) {
            return 2;
        } else if (times[line][3] >= 100) {
            return 3;
        }

        return 4;
    }

    private void dropTime(int line) {
        times[line][whereIsTime(line)] -= 100;
    }

    int getColorAndDecrement(int line) {
        int wit = whereIsTime(line);
        int ret = DESLIGADO;
        switch (wit) {
            case 1: //Atraso de grupo ou vermelho
                ret = times[line][2] > 0 ? VERDE : VERMELHO;
                break;
            case 2:
                ret = ((times[line][0] & 0x80)>>7) != 0 ? VERMELHO_INTERMITENTE : AMARELO;
                break;
            case 3:
                ret = VERMELHO_LIMPEZA;
                break;
            case 4:
                switch ((times[line][0] & 0x60) >> 5) {
                    case 0:
                        ret = DESLIGADO;
                        break;
                    case 1:
                        ret = VERDE;
                        break;
                    case 2:
                        ret = VERMELHO;
                        break;
                    case 3:
                        ret = AMARELO_INTERMITENTE;
                        break;
                }
                break;
        }
        dropTime(line);


        return ret;
    }

    void run() {
        for (int i = 0; i < 16; i++) {
            if (times[i][0] > 0) {
                colors[i] = getColorAndDecrement(i);
            } else {
                colors[i] = DESLIGADO;
            }
        }
    }

    private void lights() {
        run();

        Color newColor = Color.BLACK;
        Color newBackgroundColor = Color.WHITE;
        String label = "Desligado";

        for (int i = 0; i < 16; i++) {
            int color = colors[i];
            switch (color) {
                case VERDE:
                    newBackgroundColor = Color.GREEN;
                    newColor = Color.BLACK;
                    label = "Verde";
                    break;
                case VERMELHO:
                    newBackgroundColor = Color.RED;
                    newColor = Color.BLACK;
                    label = "Vermelho";
                    break;

                case VERMELHO_LIMPEZA:
                    newBackgroundColor = Color.RED;
                    newColor = Color.BLACK;
                    label = "Vermelho Lim";
                    break;
                case AMARELO:
                    newBackgroundColor = Color.YELLOW;
                    newColor = Color.BLACK;

                    label = "Amarelo";
                    break;
                case AMARELO_INTERMITENTE:
                    if(labelsEstados.get(i+1).getBackground().equals(Color.YELLOW)){
                        newBackgroundColor = Color.BLACK;
                        newColor = Color.YELLOW;
                    }else{
                        newBackgroundColor = Color.YELLOW;
                        newColor = Color.BLACK;
                    }

                    label = "Amarelo Int";
                    break;
                case VERMELHO_INTERMITENTE:
                    if(labelsEstados.get(i+1).getBackground().equals(Color.RED)){
                        newBackgroundColor = Color.BLACK;
                        newColor = Color.RED;
                    }else{
                        newBackgroundColor = Color.RED;
                        newColor = Color.BLACK;
                    }

                    label = "Vermelho Int";
                    break;
            }

            labelsEstados.get(i+1).setForeground(newColor);
            labelsEstados.get(i+1).setBackground(newBackgroundColor);
            labelsEstados.get(i+1).setText(label);
        }
        tempo += 100;
    }



    void onReceiveEstage(byte[] msg) {
        int groupsSize = (msg[4] & 0xff) & 0x1F;
        int index = 5;

        for (int i = 0; i < groupsSize; i++) {
            int group = (msg[index] & 0xff) & 0x1F;
            times[group - 1][0] = msg[index] & 0xff;
            times[group - 1][1] = ((msg[index + 1] & 0xff) << 8) | (msg[index + 2]  & 0xff);
            times[group - 1][2] = ((msg[index + 3] & 0xff) << 8) | (msg[index + 4]  & 0xff);
            times[group - 1][3] = ((msg[index + 5] & 0xff) << 8) | (msg[index + 6]  & 0xff);
            times[group - 1][4] = ((msg[index + 7] & 0xff) << 8) | (msg[index + 8]  & 0xff);
            index += 9;
        }

    }


    public DeviceBridgeCallback getCallback() {
        return callback;
    }
}
