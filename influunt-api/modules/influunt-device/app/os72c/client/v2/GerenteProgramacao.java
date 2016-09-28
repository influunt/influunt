package os72c.client.v2;

import models.Evento;
import models.TabelaHorario;
import models.TipoEvento;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by rodrigosol on 9/19/16.
 */
public class GerenteProgramacao {
    private final TabelaHorario tabelaAtual;

    private List<Evento> eventos;

    public GerenteProgramacao(TabelaHorario tabelaAtual) {
        this.tabelaAtual = tabelaAtual;
        ordenaEventos();
    }

    public long atualizarTabela() {
        return 0;
    }

    private void ordenaEventos() {
        this.eventos = tabelaAtual.getEventos();
        eventos.sort(new Comparator<Evento>() {
            @Override
            public int compare(Evento o1, Evento o2) {
                return o2.compareTo(o1);
            }
        });
    }

    public List<Evento> getEventos() {
        return this.eventos;
    }

    public int getPlanoAtual(Long momento) {
        int planoEscolhido = -1;
        for (Evento evento : this.eventos) {
            System.out.println("Analisando:" + evento);
            if (planoEscolhido == -1) {
                planoEscolhido = evento.getPosicaoPlano();
            } else {
                if (momentoFuturo(evento, momento)) {
                    planoEscolhido = evento.getPosicaoPlano();
                }
            }
            System.out.println("Plano Escolhido:" + planoEscolhido);
        }

        return planoEscolhido;
    }

    private boolean momentoFuturo(Evento evento, Long momento) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(momento));
        Integer diaDaSemana = cal.get(Calendar.DAY_OF_WEEK);
        if (evento.getTipo().equals(TipoEvento.NORMAL)) {
            int diaDaSemanaEvento = evento.getDiaDaSemana().getDia();
            return diaDaSemana.compareTo(diaDaSemanaEvento) > 0;
        }

        return false;
    }
}
