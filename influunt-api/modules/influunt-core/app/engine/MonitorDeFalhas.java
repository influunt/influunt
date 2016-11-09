package engine;

import org.joda.time.DateTime;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 11/9/16.
 */
public class MonitorDeFalhas {


    private final MotorEventoHandler motorEventoHandler;

    //TODO: Avaliar se necessário separar por tipo de grupo semaforico
    private final Pattern sequenciaDeCores = Pattern.compile("(^(0)+$)|(^(3)+$)|(^(1)+$)|(^(5)+$)|(^(3)+(5)+$)|(^(3)+(0)+$)|(^(3)+(1)+$)|(^(5)+(3)+$)|(^(0)+(3)+$)|(^(0)+(5)+(3)+$)|(^((5)+(3)+|(0)+(5)+(3)+)(1)+$)|((((2)+|(1)+(2)+)(3)+|((2)+|(1)+(2)+)(6)+(3)+)($|(5)+|(0)+))|((((4)+|(1)+(4)+)(3)+|((4)+|(1)+(4)+)(6)+(3)+)($|(5)+|(0)+))");

    public MonitorDeFalhas(MotorEventoHandler motorEventoHandler) {
        this.motorEventoHandler = motorEventoHandler;
    }

    public void onEstagioChange(int anel, Long numeroCiclos, Long tempoDecorrido, DateTime timestamp, IntervaloGrupoSemaforico intervalos) {
        if(!sequenciaCoresValida(intervalos)){
            motorEventoHandler.handle(new EventoMotor(timestamp,TipoEvento.FALHA_SEQUENCIA_DE_CORES,anel));
        }
    }


    private boolean sequenciaCoresValida(IntervaloGrupoSemaforico intervalos) {
        return intervalos.getEstados().entrySet().stream().map(entry -> {
            return entry.getValue().asMapOfRanges().values()
                                                   .stream()
                                                   .map(estado -> String.valueOf(estado.ordinal()))
                                                   .collect(Collectors.joining());
        }).allMatch(s -> {
            if (!sequenciaDeCores.matcher(s).matches()) {
                System.out.println(s);
            }
            return sequenciaDeCores.matcher(s).matches();
        });

    }
}
