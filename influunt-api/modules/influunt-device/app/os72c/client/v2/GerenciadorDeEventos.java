package os72c.client.v2;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import models.Evento;
import utils.CustomCalendar;


import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Range.closed;
import static com.sun.javafx.tools.resource.DeployResource.Type.data;
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
        this.eventos.forEach(evento -> {
            processaEvento(evento);
        });
        //compacta(rangeMap);

        System.out.println(rangeMap);
    }

    private void processaEvento(Evento evento) {
        Arrays.stream(evento.getDiaDaSemana().momentosDeAtivacao(getMSNoDia(evento))).forEach(inicio -> {
            adiconarEvento(evento, inicio);
        });
    }

    private void adiconarEvento(Evento evento, int inicio) {
        if (rangeMap.getEntry(0) == null) {
            rangeMap.put(closed(inicio, ULTIMO_MS_DA_SEMANA), evento);
            rangeMap.put(closed(PRIMEIRO_MS_DA_SEMANA, inicio - 1), evento);
            petrios.put(inicio, true);
            petrios.put(PRIMEIRO_MS_DA_SEMANA, false);

        } else {
            Map.Entry<Range<Integer>, Evento> eventoAtualMap = rangeMap.getEntry(inicio);
            Range<Integer> eventoAtualRange = eventoAtualMap.getKey();
            Evento eventoAtual = eventoAtualMap.getValue();
            Range<Integer> range = Range.closed(inicio, eventoAtualRange.upperEndpoint());
            rangeMap.put(range, evento);
            petrios.put(range.lowerEndpoint(), true);
            atualizaRanges(evento, range);
        }
    }

    private void compacta(RangeMap<Integer, Evento> rangeMap) {
        List<Map.Entry<Range<Integer>, Evento>> novosRanges = new ArrayList<>();
        Map.Entry<Range<Integer>, Evento> merge = null;
        boolean inicializa = true;
        for (Map.Entry<Range<Integer>, Evento> entry : rangeMap.asMapOfRanges().entrySet()) {
            if (inicializa) {
                merge = entry;
                inicializa = false;
            }
            if (merge.getKey().isConnected(entry.getKey()) && merge.getValue().equals(entry.getValue())) {
                merge = Maps.immutableEntry(Range.closed(merge.getKey().lowerEndpoint(), entry.getKey().upperEndpoint()), merge.getValue());
            } else {
                novosRanges.add(merge);
                inicializa = true;
            }
        }

        novosRanges.stream().forEach(rangeEventoEntry -> {
            rangeMap.put(rangeEventoEntry.getKey(), rangeEventoEntry.getValue());
        });

    }


    private void atualizaRanges(Evento evento, Range<Integer> range) {
        List<Map.Entry<Range<Integer>, Evento>> novosRanges = new ArrayList<>();


        List<Map.Entry<Range<Integer>, Evento>> praFrente = rangeMap.asMapOfRanges().entrySet().stream().filter(rangeEventoEntry -> {
            return rangeEventoEntry.getKey().lowerEndpoint() >= range.upperEndpoint();
        }).collect(Collectors.toList());

        boolean foiAteOFinal = praFrente.size() == 0;

        for (Map.Entry<Range<Integer>, Evento> entry : praFrente) {


            if (evento.tenhoPrioridade(entry.getValue(), false, petrios.get(entry.getKey().lowerEndpoint()))) {
                novosRanges.add(Maps.immutableEntry(entry.getKey(), evento));
                if (entry.getKey().upperEndpoint().equals(ULTIMO_MS_DA_SEMANA)) {
                    foiAteOFinal = true;
                }
            } else {
                break;
            }
        }

        novosRanges.stream().forEach(rangeEventoEntry -> {
            petrios.put(rangeEventoEntry.getKey().upperEndpoint(), false);
            rangeMap.put(rangeEventoEntry.getKey(), rangeEventoEntry.getValue());
        });


        if (foiAteOFinal) {
            List<Map.Entry<Range<Integer>, Evento>> praTras = rangeMap.asMapOfRanges().entrySet().stream().filter(rangeEventoEntry -> {
                return rangeEventoEntry.getKey().upperEndpoint() < range.lowerEndpoint();
            }).collect(Collectors.toList());

            for (Map.Entry<Range<Integer>, Evento> entry : praTras) {
                if (evento.tenhoPrioridade(entry.getValue(), false, petrios.get(entry.getKey().lowerEndpoint()))) {
                    novosRanges.add(Maps.immutableEntry(entry.getKey(), evento));
                } else {
                    break;
                }
            }
            novosRanges.stream().forEach(rangeEventoEntry -> {
                petrios.put(rangeEventoEntry.getKey().upperEndpoint(), false);
                rangeMap.put(rangeEventoEntry.getKey(), rangeEventoEntry.getValue());
            });
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

    private Integer getMSNaSemana(Calendar calendar) {
        return ((calendar.get(Calendar.DAY_OF_WEEK) - 1) * MS_POR_DIA) + getMSNoDia(calendar);
    }


    public Evento eventoAtual(Calendar data) {
        return rangeMap.get(getMSNaSemana(data));
    }

    public int getQuantidadeIntervalos() {
        return rangeMap.asMapOfRanges().entrySet().size();
    }

    public void imprimeTabelaHoraria() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY - EEE - HH:mm:ss");
        System.out.println("\n\n");
        rangeMap.asMapOfRanges().entrySet().stream().forEach(rangeEventoEntry -> {
            Calendar inicio = CustomCalendar.getCalendar();
            Calendar fim = CustomCalendar.getCalendar();

            inicio.add(Calendar.MILLISECOND, rangeEventoEntry.getKey().lowerEndpoint());
            fim.add(Calendar.MILLISECOND, rangeEventoEntry.getKey().upperEndpoint());
            System.out.println(rangeEventoEntry.getKey().toString() + "---" + sdf.format(inicio.getTime()) + " a " + sdf.format(fim.getTime()) + ": " + rangeEventoEntry.getValue().getDiaDaSemana() + " : " + rangeEventoEntry.getValue().getPosicaoPlano());
        });

    }

}
