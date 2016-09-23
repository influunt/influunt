package os72c.client.v2;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.Evento;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Range.closed;
import static com.google.common.collect.Range.closedOpen;
import static utils.ConstantesDeTempo.*;


/**
 * Created by rodrigosol on 9/20/16.
 */
public class GerenciadorDeEventos {

    RangeMap<Integer, Evento> rangeMap = TreeRangeMap.create();

    Map<Integer, Boolean> petrios = new HashMap<>();

    private List<Evento> eventos;


    public void addEventos(List<Evento> eventos) {

        this.eventos = eventos.stream().sorted().collect(Collectors.toList());
        for (Evento evento : this.eventos) {
            processaEvento(evento);
        }
    }

    private void processaEvento(Evento evento) {
        for (Integer inicio : evento.getDiaDaSemana().momentosDeAtivacao(getMSNoDia(evento))) {
            adiconarEvento(evento, inicio);
        }
    }

    private void adiconarEvento(Evento evento, int inicio) {
        if (rangeMap.getEntry(0) == null) {
            rangeMap.put(closed(inicio, ULTIMO_MS_DA_SEMANA), evento);
            rangeMap.put(closedOpen(PRIMEIRO_MS_DA_SEMANA, inicio), evento);
            petrios.put(inicio, true);
            petrios.put(PRIMEIRO_MS_DA_SEMANA, inicio == 0 ? true : false);
        } else {

            Map.Entry<Range<Integer>, Evento> eventoAtualMap = rangeMap.getEntry(inicio);
            Range<Integer> eventoAtualRange = eventoAtualMap.getKey();
            Evento eventoAtual = eventoAtualMap.getValue();

//            if(petrios.get(eventoAtualRange.lowerEndpoint()) != null){
//                if(evento.compareTo(eventoAtual) > 0){
//                    rangeMap.remove(eventoAtualRange);
//                }
//            }

            Range<Integer> range;
            if (eventoAtualRange.upperEndpoint().equals(ULTIMO_MS_DA_SEMANA)) {
                range = Range.closed(inicio, eventoAtualRange.upperEndpoint());
            } else {
                range = Range.closedOpen(inicio, eventoAtualRange.upperEndpoint());
            }

            rangeMap.put(range, evento);
            petrios.put(range.lowerEndpoint(), true);
            atualizaRanges(evento, range);
        }
    }

    private void atualizaRanges(Evento evento, Range<Integer> range) {
        List<Map.Entry<Range<Integer>, Evento>> novosRanges = new ArrayList<>();


        List<Map.Entry<Range<Integer>, Evento>> praFrente = rangeMap.asMapOfRanges().entrySet().stream().filter(rangeEventoEntry -> {
            return rangeEventoEntry.getKey().lowerEndpoint() >= range.upperEndpoint();
        }).collect(Collectors.toList());

        boolean foiAteOFinal = praFrente.size() == 0;

        int adicionar = 0;
        for (Map.Entry<Range<Integer>, Evento> entry : praFrente) {
            if (evento.tenhoPrioridade(entry.getValue(), false, petrios.get(entry.getKey().lowerEndpoint()))) {
                adicionar = entry.getKey().upperEndpoint();
                rangeMap.remove(entry.getKey());
                petrios.remove(entry.getKey().lowerEndpoint());
                if (entry.getKey().upperEndpoint().equals(ULTIMO_MS_DA_SEMANA)) {
                    foiAteOFinal = true;
                }
            } else {
                break;
            }
        }

        if (adicionar > 0) {
            rangeMap.remove(range);
            Range<Integer> newRange = Range.closedOpen(range.lowerEndpoint(), adicionar);
            rangeMap.put(newRange, evento);
        }


        if (foiAteOFinal) {
            List<Map.Entry<Range<Integer>, Evento>> praTras = rangeMap.asMapOfRanges().entrySet().stream().filter(rangeEventoEntry -> {
                return rangeEventoEntry.getKey().upperEndpoint() < range.lowerEndpoint();
            }).collect(Collectors.toList());

            adicionar = 0;
            for (Map.Entry<Range<Integer>, Evento> entry : praTras) {
                Boolean petrio = petrios.get(entry.getKey().lowerEndpoint());
                if (evento.tenhoPrioridade(entry.getValue(), false,  petrio == null ? false : petrio)) {
                    adicionar = entry.getKey().upperEndpoint();
                    rangeMap.remove(entry.getKey());
                    petrios.remove(entry.getKey().lowerEndpoint());
                } else {
                    break;
                }
            }
            if (adicionar > 0) {
                Range<Integer> newRange = Range.closedOpen(PRIMEIRO_MS_DA_SEMANA, adicionar);
                rangeMap.put(newRange, evento);
            }
        }

    }


    private Integer getMSNoDia(Evento evento) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(evento.getData());
        return getMSNoDia(calendar);
    }

    private Integer getMSNoDia(Calendar calendar) {
        return (calendar.get(Calendar.HOUR_OF_DAY) * MS_POR_HORA) + (calendar.get(Calendar.MINUTE) * MS_POR_MINUTO) + ((calendar.get(Calendar.SECOND) * MS_POR_SEGUNDO)) + calendar.get(Calendar.MILLISECOND);
    }

    public Integer getMSNaSemana(Calendar calendar) {
        return ((calendar.get(Calendar.DAY_OF_WEEK) - 1) * MS_POR_DIA) + getMSNoDia(calendar);
    }


    public Evento eventoAtual(Calendar data) {
        return rangeMap.get(getMSNaSemana(data));
    }

    public int getQuantidadeIntervalos() {
        return rangeMap.asMapOfRanges().entrySet().size();
    }

    public void imprimeTabelaHoraria() {
        DateTimeFormatter sdf =  DateTimeFormat.forPattern("dd/MM/YYYY - EEE - HH:mm:ss");
        System.out.println("\n\n");
        rangeMap.asMapOfRanges().entrySet().stream().forEach(rangeEventoEntry -> {
            DateTime inicioDt = new DateTime(2016,9,18,0,0,0);
            inicioDt = inicioDt.plus(rangeEventoEntry.getKey().lowerEndpoint());
            DateTime fimDt = new DateTime(2016,9,18,0,0,0);
            fimDt = fimDt.plus(rangeEventoEntry.getKey().upperEndpoint());
            System.out.println(rangeEventoEntry.getKey().toString() + "---" + sdf.print(inicioDt) + " a " + sdf.print(fimDt) + ": " + rangeEventoEntry.getValue().getDiaDaSemana() + " : " + rangeEventoEntry.getValue().getPosicaoPlano());
        });

    }

    public RangeMap<Integer, Evento> getIntervalos() {
        return rangeMap;
    }
}
