package reports;

import models.*;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import utils.InfluuntUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lesiopinheiro on 10/10/16.
 */

public class TabelaHorariaReportService extends ReportService<Controlador> {

    private Map<Integer, List<DiaDaSemana>> diasDaSemana = new HashMap<>();

    public TabelaHorariaReportService() {
        List<DiaDaSemana> segunda = new ArrayList<>();
        segunda.add(DiaDaSemana.SEGUNDA);
        segunda.add(DiaDaSemana.SEGUNDA_A_SEXTA);
        segunda.add(DiaDaSemana.SEGUNDA_A_SABADO);
        segunda.add(DiaDaSemana.TODOS_OS_DIAS);
        diasDaSemana.put(1, segunda);

        List<DiaDaSemana> terca = new ArrayList<>();
        terca.add(DiaDaSemana.TERCA);
        terca.add(DiaDaSemana.SEGUNDA_A_SEXTA);
        terca.add(DiaDaSemana.SEGUNDA_A_SABADO);
        terca.add(DiaDaSemana.TODOS_OS_DIAS);
        diasDaSemana.put(2, terca);

        List<DiaDaSemana> quarta = new ArrayList<>();
        quarta.add(DiaDaSemana.QUARTA);
        quarta.add(DiaDaSemana.SEGUNDA_A_SEXTA);
        quarta.add(DiaDaSemana.SEGUNDA_A_SABADO);
        quarta.add(DiaDaSemana.TODOS_OS_DIAS);
        diasDaSemana.put(3, quarta);

        List<DiaDaSemana> quinta = new ArrayList<>();
        quinta.add(DiaDaSemana.QUINTA);
        quinta.add(DiaDaSemana.SEGUNDA_A_SEXTA);
        quinta.add(DiaDaSemana.SEGUNDA_A_SABADO);
        quinta.add(DiaDaSemana.TODOS_OS_DIAS);
        diasDaSemana.put(4, quinta);

        List<DiaDaSemana> sexta = new ArrayList<>();
        sexta.add(DiaDaSemana.SEXTA);
        sexta.add(DiaDaSemana.SEGUNDA_A_SEXTA);
        sexta.add(DiaDaSemana.SEGUNDA_A_SABADO);
        sexta.add(DiaDaSemana.TODOS_OS_DIAS);
        diasDaSemana.put(5, sexta);

        List<DiaDaSemana> sabado = new ArrayList<>();
        sabado.add(DiaDaSemana.SABADO);
        sabado.add(DiaDaSemana.SEGUNDA_A_SABADO);
        sabado.add(DiaDaSemana.SABADO_A_DOMINGO);
        sabado.add(DiaDaSemana.TODOS_OS_DIAS);
        diasDaSemana.put(6, sabado);

        List<DiaDaSemana> domingo = new ArrayList<>();
        domingo.add(DiaDaSemana.DOMINGO);
        domingo.add(DiaDaSemana.SABADO_A_DOMINGO);
        domingo.add(DiaDaSemana.TODOS_OS_DIAS);
        diasDaSemana.put(7, domingo);
    }

    @Override
    public InputStream generateReport(Map<String, String[]> params, List<Controlador> lista, ReportType reportType) {
        throw new NotImplementedException("Método nao implementado. Favor utilizar outro método gerador.");
    }

    public List<Map<String, String>> reportData(String controladorId, String dataStr) {
        Controlador controlador = Controlador.find
            .fetch("versoesTabelasHorarias")
            .fetch("versoesTabelasHorarias.tabelaHoraria")
            .fetch("versoesTabelasHorarias.tabelaHoraria.eventos")
            .where().eq("id", controladorId).findUnique();

        if (controlador == null) {
            return Collections.emptyList();
        }

        VersaoTabelaHoraria versaoTabelaHoraria = controlador.getVersaoTabelaHoraria();
        if (versaoTabelaHoraria == null) {
            return Collections.emptyList();
        }

        TabelaHorario tabela = versaoTabelaHoraria.getTabelaHoraria();
        DateTime data = DateTime.parse(dataStr, ISODateTimeFormat.dateTimeParser());
        List<Evento> eventos = tabela.getEventos().stream()
            .filter(evento -> isEventoNoDia(evento, data))
            .sorted((e1, e2) -> e1.getHorario().compareTo(e2.getHorario()))
            .collect(Collectors.toList());

        removeEventosNormaisSeTemEventoEspecial(eventos);

        List<Map<String, String>> reportData = new ArrayList<>();
        eventos.forEach(evento -> {
            Map<String, String> datum = new HashMap<>();
            Plano plano = controlador.getAneis().stream()
                .filter(anel -> anel.isAtivo() && anel.getPosicao() == 1)
                .map(Anel::getPlanos)
                .flatMap(Collection::stream)
                .filter(p -> Objects.equals(p.getPosicao(), evento.getPosicaoPlano()))
                .findFirst().orElse(null);
            if (plano != null) {
                datum.put("plano", plano.getDescricao());
                datum.put("modoOperacaoPlano", plano.getModoOperacao().toString());
            } else {
                datum.put("plano", "");
                datum.put("modoOperacaoPlano", "");
            }
            if (evento.getAgrupamento() != null) {
                datum.put("agrupamento", evento.getAgrupamento().getTipo() + ": " + evento.getAgrupamento().getNome());
            } else {
                datum.put("agrupamento", "");
            }
            if (controlador.getSubarea() != null) {
                datum.put("subarea", controlador.getSubarea().getNome());
            } else {
                datum.put("subarea", "");
            }

            datum.put("numeroPlano", evento.getPosicaoPlano().toString());
            datum.put("horario", evento.getHorario().toString());
            datum.put("tipoEvento", evento.getTipo().toString());
            reportData.add(datum);
        });

        return reportData;
    }

    private boolean isEventoNoDia(Evento evento, DateTime data) {
        boolean isMesmoDiaSemana = diasDaSemana.get(data.getDayOfWeek()).contains(evento.getDiaDaSemana());
        boolean isMesmaData;
        if (TipoEvento.ESPECIAL_RECORRENTE.equals(evento.getTipo())) {
            DateTime eventoDate = new DateTime(evento.getData());
            isMesmaData = eventoDate.getYear() == data.getYear() && eventoDate.getDayOfMonth() == data.getDayOfMonth();
        } else {
            isMesmaData = data.toDate().equals(evento.getData());
        }
        return isMesmoDiaSemana || isMesmaData;
    }

    private void removeEventosNormaisSeTemEventoEspecial(List<Evento> eventos) {
        Iterator<Evento> it = eventos.iterator();
        boolean deveRemoverEventoNormal = false;
        while (it.hasNext()) {
            Evento evento = it.next();
            if (TipoEvento.NORMAL.equals(evento.getTipo())) {
                if (deveRemoverEventoNormal) {
                    it.remove();
                }
            } else {
                deveRemoverEventoNormal = true;
            }
        }
    }

    /**
     * Retorna um CSV com dados da {@link TabelaHorario}
     * <p>
     * FORMATO: HORÁRIO | PLANO | NUM. PLANO | MODO OP. PLANO | SUBÁREA
     *
     * @return {@link InputStream} do csv
     */
    public InputStream generateTabelaHorariaCSVReport(String controladorId, String dataStr) {
        StringBuilder buffer = new StringBuilder();

        DateTime data = DateTime.parse(dataStr, ISODateTimeFormat.dateTimeParser());
        buffer.append("Relatório da Tabela Horária do dia ").append(InfluuntUtils.formatDateToString(data, FORMAT_DATE_COMPLETE)).append(NEW_LINE_SEPARATOR)
            .append("Gerado em: ").append(COMMA_DELIMITER).append(InfluuntUtils.formatDateToString(new DateTime(), FORMAT_DATE_HOUR_COMPLETE))
            .append(NEW_LINE_SEPARATOR).append(NEW_LINE_SEPARATOR)

            // Write the CSV file header
            .append("HORÁRIO").append(COMMA_DELIMITER).append("PLANO").append(COMMA_DELIMITER)
            .append("NÚMERO PLANO").append(COMMA_DELIMITER).append("MODO OPERAÇÃO PLANO").append(COMMA_DELIMITER)
            .append("SUBÁREA").append(NEW_LINE_SEPARATOR);

        List<Map<String, String>> params = reportData(controladorId, dataStr);
        params.forEach(param -> buffer.append(StringUtils.defaultIfBlank(param.get("horario"), StringUtils.EMPTY)).append(COMMA_DELIMITER)
            .append(StringUtils.defaultIfBlank(param.get("plano"), StringUtils.EMPTY)).append(COMMA_DELIMITER)
            .append(StringUtils.defaultIfBlank(param.get("numeroPlano"), StringUtils.EMPTY)).append(COMMA_DELIMITER)
            .append(StringUtils.defaultIfBlank(param.get("modoOperacaoPlano"), StringUtils.EMPTY)).append(COMMA_DELIMITER)
            .append(StringUtils.defaultIfBlank(param.get("subarea"), StringUtils.EMPTY)).append(NEW_LINE_SEPARATOR));

        return new ByteArrayInputStream(buffer.toString().getBytes(Charset.forName("UTF-8")));
    }

}
