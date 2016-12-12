package os72c.client.virtual;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import engine.EventoMotor;
import engine.IntervaloGrupoSemaforico;
import engine.TipoEvento;
import org.joda.time.DateTime;
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
    private final int DESLIGADO = 0;

    private final int VERDE = 1;

    private final int VERMELHO = 2;

    private final int AMARELO = 3;

    private final int VERMELHO_INTERMITENTE = 4;

    private final int AMARELO_INTERMITENTE = 5;

    private final int VERMELHO_LIMPEZA = 6;

    public JPanel form;

    int colors[] = new int[16];

    int times[][] = new int[16][5];

    int tempo = 0;

    int clock = 0;

    private ActionListener anelActionListener;

    private ActionListener detectorActionListerner;

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

    private ScheduledFuture<?> executor;

    private DeviceBridgeCallback callback;

    public ControladorForm() {
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
        btnTrocarEstagio.setEnabled(true);
        btnTrocarEstagio.setVisible(true);

    }

    @Override
    public void modoManualDesativado() {
        btnTrocarEstagio.setEnabled(false);
        btnTrocarEstagio.setVisible(false);
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

        btnFaseVermelhaApagada.addActionListener(new GrupoSemaforicoActionListener(this, TipoEvento.FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO_APAGADA, TipoEvento.REMOCAO_FALHA_FASE_VERMELHA_DE_GRUPO_SEMAFORICO));
        btnVerdesConflitantes.addActionListener(new AnelActionListener(this, TipoEvento.FALHA_VERDES_CONFLITANTES, TipoEvento.REMOCAO_FALHA_VERDES_CONFLITANTES));
        btnSequenciaDeCores.addActionListener(new AnelActionListener(this, TipoEvento.FALHA_SEQUENCIA_DE_CORES, null));
        btnTempoMaxPermEstágio.addActionListener(new AnelActionListener(this, TipoEvento.FALHA_DESRESPEITO_AO_TEMPO_MAXIMO_DE_PERMANENCIA_NO_ESTAGIO, null));

        btnDetectorPedestreDireto.addActionListener(new DetectorPedestreActionListener(this, TipoEvento.FALHA_DETECTOR_PEDESTRE_ACIONAMENTO_DIRETO, TipoEvento.REMOCAO_FALHA_DETECTOR_PEDESTRE));
        btnDetectorPedestreFalta.addActionListener(new DetectorPedestreActionListener(this, TipoEvento.FALHA_DETECTOR_PEDESTRE_FALTA_ACIONAMENTO, TipoEvento.REMOCAO_FALHA_DETECTOR_PEDESTRE));

        btnDetectorVeicularDireto.addActionListener(new DetectorVeicularActionListener(this, TipoEvento.FALHA_DETECTOR_VEICULAR_ACIONAMENTO_DIRETO, TipoEvento.REMOCAO_FALHA_DETECTOR_VEICULAR));
        btnDetectorVeicularFalta.addActionListener(new DetectorVeicularActionListener(this, TipoEvento.FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO, TipoEvento.REMOCAO_FALHA_DETECTOR_VEICULAR));

        btnCPU.addActionListener(new ControladorActionListener(this, TipoEvento.FALHA_WATCH_DOG, null));
        btnMemoria.addActionListener(new ControladorActionListener(this, TipoEvento.FALHA_MEMORIA, null));

        btnFocoApagado.addActionListener(new AnelActionListener(this, TipoEvento.FALHA_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO_APAGADA, TipoEvento.REMOCAO_FOCO_VERMELHO_DE_GRUPO_SEMAFORICO));

        btnPortaPrincipal.addActionListener(new ControladorActionListener(this, TipoEvento.ALARME_ABERTURA_DA_PORTA_PRINCIPAL_DO_CONTROLADOR, TipoEvento.ALARME_FECHAMENTO_DA_PORTA_PRINCIPAL_DO_CONTROLADOR));
        btnPortalDePainel.addActionListener(new ControladorActionListener(this, TipoEvento.ALARME_ABERTURA_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR, TipoEvento.ALARME_FECHAMENTO_DA_PORTA_DO_PAINEL_DE_FACILIDADES_DO_CONTROLADOR));
        btnIntermitente.addActionListener(new AnelActionListener(this, TipoEvento.ALARME_AMARELO_INTERMITENTE, null));
        btnSemaforoApagado.addActionListener(new AnelActionListener(this, TipoEvento.ALARME_SEMAFORO_APAGADO, null));
        btnAcertoRelogio.addActionListener(new AnelActionListener(this, TipoEvento.ALARME_ACERTO_RELOGIO_GPS, null));


        btnInserirPlug.addActionListener(new ControladorActionListener(this, TipoEvento.INSERCAO_DE_PLUG_DE_CONTROLE_MANUAL, TipoEvento.RETIRADA_DE_PLUG_DE_CONTROLE_MANUAL));
        btnTrocarEstagio.addActionListener(new ControladorActionListener(this, TipoEvento.TROCA_ESTAGIO_MANUAL, null));

        btnTrocarEstagio.setEnabled(false);
        btnTrocarEstagio.setVisible(false);


        JFrame frame = new JFrame("ControladorForm");
        frame.setContentPane(this.form);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.pack();
        frame.setVisible(true);

        executor = Executors.newScheduledThreadPool(1)
            .scheduleAtFixedRate(() -> {
                try {
                    lights();
                    tempoDecorrido.setText(String.valueOf(tempo / 1000) + "s");
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
                ret = ((times[line][0] & 0x80) >> 7) != 0 ? VERMELHO_INTERMITENTE : AMARELO;
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
                    if (labelsEstados.get(i + 1).getBackground().equals(Color.YELLOW)) {
                        newBackgroundColor = Color.BLACK;
                        newColor = Color.YELLOW;
                    } else {
                        newBackgroundColor = Color.YELLOW;
                        newColor = Color.BLACK;
                    }

                    label = "Amarelo Int";
                    break;
                case VERMELHO_INTERMITENTE:
                    if (labelsEstados.get(i + 1).getBackground().equals(Color.RED)) {
                        newBackgroundColor = Color.BLACK;
                        newColor = Color.RED;
                    } else {
                        newBackgroundColor = Color.RED;
                        newColor = Color.BLACK;
                    }

                    label = "Vermelho Int";
                    break;
                default:
                    newBackgroundColor = Color.BLACK;
                    newColor = Color.WHITE;
                    label = "Desligado";


            }

            labelsEstados.get(i + 1).setForeground(newColor);
            labelsEstados.get(i + 1).setBackground(newBackgroundColor);
            labelsEstados.get(i + 1).setText(label);
        }
        tempo += 100;
    }

    void onReceiveEstage(byte[] msg) {
        int groupsSize = (msg[4] & 0xff) & 0x1F;
        int index = 5;

        for (int i = 0; i < groupsSize; i++) {
            int group = (msg[index] & 0xff) & 0x1F;
            times[group - 1][0] = msg[index] & 0xff;
            times[group - 1][1] = ((msg[index + 1] & 0xff) << 8) | (msg[index + 2] & 0xff);
            times[group - 1][2] = ((msg[index + 3] & 0xff) << 8) | (msg[index + 4] & 0xff);
            times[group - 1][3] = ((msg[index + 5] & 0xff) << 8) | (msg[index + 6] & 0xff);
            times[group - 1][4] = ((msg[index + 7] & 0xff) << 8) | (msg[index + 8] & 0xff);
            index += 9;
        }

    }

    public DeviceBridgeCallback getCallback() {
        return callback;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        form = new JPanel();
        form.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        form.add(panel1, BorderLayout.SOUTH);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        form.add(panel2, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(18, 2, new Insets(0, 10, 0, 10), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Execução"));
        final JLabel label1 = new JLabel();
        label1.setText("G1");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g1 = new JLabel();
        g1.setText("");
        panel3.add(g1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("G2");
        panel3.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("G3");
        panel3.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("G4");
        panel3.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("G5");
        panel3.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("G6");
        panel3.add(label6, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("G7");
        panel3.add(label7, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("G8");
        panel3.add(label8, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("G9");
        panel3.add(label9, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("G10");
        panel3.add(label10, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("G11");
        panel3.add(label11, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("G12");
        panel3.add(label12, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("G13");
        panel3.add(label13, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("G14");
        panel3.add(label14, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("G15");
        panel3.add(label15, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("G16");
        panel3.add(label16, new GridConstraints(15, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g2 = new JLabel();
        g2.setText("");
        panel3.add(g2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g3 = new JLabel();
        g3.setText("");
        panel3.add(g3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g4 = new JLabel();
        g4.setText("");
        panel3.add(g4, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g5 = new JLabel();
        g5.setText("");
        panel3.add(g5, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g6 = new JLabel();
        g6.setText("");
        panel3.add(g6, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g7 = new JLabel();
        g7.setText("");
        panel3.add(g7, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g8 = new JLabel();
        g8.setText("");
        panel3.add(g8, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g9 = new JLabel();
        g9.setText("");
        panel3.add(g9, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g10 = new JLabel();
        g10.setText("");
        panel3.add(g10, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g11 = new JLabel();
        g11.setText("");
        panel3.add(g11, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g12 = new JLabel();
        g12.setText("");
        panel3.add(g12, new GridConstraints(11, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g13 = new JLabel();
        g13.setText("");
        panel3.add(g13, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g14 = new JLabel();
        g14.setText("");
        panel3.add(g14, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g15 = new JLabel();
        g15.setText("");
        panel3.add(g15, new GridConstraints(14, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        g16 = new JLabel();
        g16.setText("");
        panel3.add(g16, new GridConstraints(15, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Tempo Decorrido");
        panel3.add(label17, new GridConstraints(16, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tempoDecorrido = new JLabel();
        tempoDecorrido.setText("1");
        panel3.add(tempoDecorrido, new GridConstraints(16, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("Relógio");
        panel3.add(label18, new GridConstraints(17, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        relogio = new JLabel();
        relogio.setText("22:00:00");
        panel3.add(relogio, new GridConstraints(17, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(13, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Detectores"));
        btnDP1 = new JButton();
        btnDP1.setText("DP 1");
        panel4.add(btnDP1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDV8 = new JButton();
        btnDV8.setText("DV 8");
        panel4.add(btnDV8, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDV5 = new JButton();
        btnDV5.setText("DV 5");
        panel4.add(btnDV5, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDV4 = new JButton();
        btnDV4.setText("DV 4");
        panel4.add(btnDV4, new GridConstraints(7, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDV6 = new JButton();
        btnDV6.setText("DV 6");
        panel4.add(btnDV6, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDP2 = new JButton();
        btnDP2.setText("DP 2");
        panel4.add(btnDP2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDV3 = new JButton();
        btnDV3.setText("DV 3");
        panel4.add(btnDV3, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDV2 = new JButton();
        btnDV2.setText("DV 2");
        panel4.add(btnDV2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDV1 = new JButton();
        btnDV1.setText("DV 1");
        panel4.add(btnDV1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDP4 = new JButton();
        btnDP4.setText("DP 4");
        panel4.add(btnDP4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDP3 = new JButton();
        btnDP3.setText("DP 3");
        panel4.add(btnDP3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDV7 = new JButton();
        btnDV7.setText("DV 7");
        panel4.add(btnDV7, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Modo Manual"));
        btnInserirPlug = new JButton();
        btnInserirPlug.setText("Plug");
        panel5.add(btnInserirPlug, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnTrocarEstagio = new JButton();
        btnTrocarEstagio.setText("Trocar Estágio");
        panel5.add(btnTrocarEstagio, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(12, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Falhas"));
        btnFaseVermelhaApagada = new JButton();
        btnFaseVermelhaApagada.setText("Fase Vermelha Apagada");
        panel6.add(btnFaseVermelhaApagada, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnVerdesConflitantes = new JButton();
        btnVerdesConflitantes.setText("Verdes Conflitantes");
        panel6.add(btnVerdesConflitantes, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnSequenciaDeCores = new JButton();
        btnSequenciaDeCores.setText("Sequencia de Cores");
        panel6.add(btnSequenciaDeCores, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnTempoMaxPermEstágio = new JButton();
        btnTempoMaxPermEstágio.setText("Tempo Max. Perm. Estágio");
        panel6.add(btnTempoMaxPermEstágio, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDetectorPedestreDireto = new JButton();
        btnDetectorPedestreDireto.setText("Detector Pedestre Direto");
        panel6.add(btnDetectorPedestreDireto, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCPU = new JButton();
        btnCPU.setText("CPU");
        panel6.add(btnCPU, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnMemoria = new JButton();
        btnMemoria.setText("Mémoria");
        panel6.add(btnMemoria, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel7, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Alarmes"));
        btnPortaPrincipal = new JButton();
        btnPortaPrincipal.setText("Porta Principal");
        panel7.add(btnPortaPrincipal, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnPortalDePainel = new JButton();
        btnPortalDePainel.setText("Portal de Painel");
        panel7.add(btnPortalDePainel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnIntermitente = new JButton();
        btnIntermitente.setText("Amarelo Intermitente");
        panel7.add(btnIntermitente, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnSemaforoApagado = new JButton();
        btnSemaforoApagado.setText("Semáforo Apagado");
        panel7.add(btnSemaforoApagado, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAcertoRelogio = new JButton();
        btnAcertoRelogio.setText("Acerto Relógio");
        panel7.add(btnAcertoRelogio, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDetectorPedestreFalta = new JButton();
        btnDetectorPedestreFalta.setText("Detector Pedestre Falta");
        panel6.add(btnDetectorPedestreFalta, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDetectorVeicularDireto = new JButton();
        btnDetectorVeicularDireto.setText("Detector Veícular Direto");
        panel6.add(btnDetectorVeicularDireto, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDetectorVeicularFalta = new JButton();
        btnDetectorVeicularFalta.setText("Detector Veícular Falta");
        panel6.add(btnDetectorVeicularFalta, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnFocoApagado = new JButton();
        btnFocoApagado.setText("Foco Apagado");
        panel6.add(btnFocoApagado, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return form;
    }
}
