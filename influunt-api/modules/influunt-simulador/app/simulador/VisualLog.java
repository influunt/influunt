package simulador;

import models.EstadoGrupoSemaforico;
import models.Intervalo;
import org.apache.commons.math3.util.Pair;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.joda.time.DateTime;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;

public class VisualLog extends JFrame{
    private static final Color COR_VERMELHO_LIMPEZA = new Color(212, 106, 106);

    private static final Color COR_VERMELHO_INTERMITENTE = new Color(88, 15, 79);

    private static final float ESCALA = 10.0f;

    private static final float MARGIN_LATERAL = 5.0f * ESCALA;

    private static final float TICK = 1.0f * ESCALA;

    private static final float MARGIN_SUPERIOR = 2.0f * ESCALA;

    private static final float ALTURA_INTERVALO = 1.0f * ESCALA;

    private static final int ALTURA_PAGINA = 400;

    private  SVGGraphics2D g;


    private Simulador simulador;

    public VisualLog(Simulador simulador) throws IOException {

        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

//        this.simulador = simulador;
//        simulador.getTempoSimulacao();
//        simulador.getLogSimulacao().print(TipoEventoLog.ALTERACAO_EVENTO);
//
//        int largura = (int) (simulador.getTempoSimulacao() * ESCALA);
//        this.g = new SVGGraphics2D(largura, ALTURA_PAGINA);
//        processaIntervalos(simulador.getIntervalos());
//        processaMudancaDeEventos(simulador.getMudancaEventos());
////        g.dispose();
////        desenhaLinhaDeMudancaDeEvento(1,16,27,1,2);
////        desenhaLinhaDeMudancaPlano(1,5,30);
////        desenhaLinhaDeMudancaPlano(6,9,32);
////        desenhaLinhaDeMudancaPlano(10,16,35);
//
//        Files.write(Paths.get("/Users/rodrigosol/intervalos.svg"), g.getSVGDocument().getBytes());


    }

    private void processaMudancaDeEventos(java.util.List<EventoLog> mudancaEventos) {
        for (EventoLog eventoLog : mudancaEventos) {
            AlteracaoEventoLog alteracaoEventoLog = (AlteracaoEventoLog) eventoLog;
            int x = getX(alteracaoEventoLog.timeStamp);

            int a = -1;
            if (alteracaoEventoLog.getAnterior() != null) {
                a = alteracaoEventoLog.getAnterior().getPosicaoPlano();
            }
            int p = alteracaoEventoLog.getAtual().getPosicaoPlano();

            desenhaLinhaDeMudancaDeEvento(1, simulador.getQuantidadeGruposSemaforicos(), x, a, p);
        }
    }

    private int getX(DateTime timeStamp) {
        return (int) (timeStamp.getMillis() / 1000 - simulador.getInicio().getMillis() / 1000);
    }

    private void processaIntervalos(Map<Integer, java.util.List<Pair<DateTime, Intervalo>>> intervalos) {
        for (Integer key : intervalos.keySet()) {
            for (Pair<DateTime, Intervalo> pair : intervalos.get(key)) {
                desenhaIntervalo(key, getX(pair.getKey()), pair.getSecond().getTamanho(), getColor(pair.getSecond().getEstadoGrupoSemaforico()));
            }
        }
    }

    private Color getColor(EstadoGrupoSemaforico estadoGrupoSemaforico) {
        switch (estadoGrupoSemaforico) {
            case VERDE:
                return Color.GREEN;
            case AMARELO:
                return Color.YELLOW;
            case VERMELHO:
                return Color.RED;
            case VERMELHO_INTERMITENTE:
                return COR_VERMELHO_INTERMITENTE;
            case VERMELHO_LIMPEZA:
                return COR_VERMELHO_LIMPEZA;
            case DESLIGADO:
                return Color.GRAY;
            case AMARELO_INTERMITENTE:
                return Color.ORANGE;
            default:
                return Color.BLACK;
        }
    }

    private void desenhaIntervalo(int grupo, int instante, int duracao, Color color) {
        System.out.println("GRUPO:" + grupo);
        System.out.println("Instante:" + instante);
        System.out.println("Duracao:" + duracao);
        System.out.println("Cor:" + color);

        g.setColor(color);
        g.fill(new Rectangle.Float(instante * ESCALA + MARGIN_LATERAL, grupo * ALTURA_INTERVALO + MARGIN_SUPERIOR, duracao * ESCALA, ALTURA_INTERVALO));
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(0.5f));
        g.draw(new Rectangle.Float(instante * ESCALA + MARGIN_LATERAL, grupo * ALTURA_INTERVALO + MARGIN_SUPERIOR, duracao * ESCALA, ALTURA_INTERVALO));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 8);
        g.setFont(font);
        g.drawString(duracao + "s", MARGIN_LATERAL + 2 + instante * ESCALA, grupo * ALTURA_INTERVALO + (MARGIN_SUPERIOR + ALTURA_INTERVALO) - 2);


//        Stroke dashed = new BasicStroke(0.1f);
//        g.setStroke(dashed);
//        g.setColor(Color.WHITE);
//        float inicio = (instante * ESCALA + MARGIN_LATERAL) + 1;
//        while(duracao > 1){
//            g.draw(new Line2D.Float(inicio, grupo * ALTURA_INTERVALO + MARGIN_SUPERIOR + ALTURA_INTERVALO, inicio,  ALTURA_INTERVALO));
//            inicio += TICK;
//            duracao--;
//        }


    }


    private void desenhaLinhaDeMudancaDeEvento(int grupoInicial, int grupoFinal, int instante, int de, int para) {
        Stroke dashed = new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{0.4f}, 0);
        g.setColor(Color.BLUE);
        g.setStroke(dashed);
        g.draw(new Line2D.Float(instante + MARGIN_LATERAL, (grupoInicial * ALTURA_INTERVALO + MARGIN_SUPERIOR) - ALTURA_INTERVALO, instante * ESCALA + MARGIN_LATERAL, (grupoFinal * ALTURA_INTERVALO) + MARGIN_SUPERIOR + (2 * ALTURA_INTERVALO)));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 10);
        g.setFont(font);
        g.drawString("1/2", (instante * ESCALA + MARGIN_LATERAL) - 0.5f, MARGIN_SUPERIOR - 0.5f);

    }

    private void desenhaLinhaDeMudancaPlano(int grupoInicial, int grupoFinal, int instante) {
        Stroke dashed = new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{0.4f}, 0);
        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(dashed);
        g.draw(new Line2D.Float(instante / ESCALA + MARGIN_LATERAL, (grupoInicial * ALTURA_INTERVALO + MARGIN_SUPERIOR), instante / ESCALA + MARGIN_LATERAL, (grupoFinal * ALTURA_INTERVALO) + MARGIN_SUPERIOR + ALTURA_INTERVALO));
//        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//        Font font = new Font("Serif", Font.PLAIN, 1);
//        g.setFont(font);
//        g.drawString("1/2",(instante/ESCALA + MARGIN_LATERAL) - 0.5f, MARGIN_SUPERIOR - 0.5f);

    }


}