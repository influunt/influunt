package os72c.client.v2;

import models.EstadoGrupoSemaforico;
import models.GrupoSemaforicoPlano;
import models.Intervalo;
import models.Plano;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.math3.util.ArithmeticUtils.lcm;


/**
 * Created by rodrigosol on 9/15/16.
 */
public class Programacao {

    private final HashMap<Integer,Integer> temposDeCiclo;
    private final HashMap<Integer,Integer> temposDeVerdeSeguranca;

    private final HashMap<Integer,List<EstadoGrupoSemaforico>> grupos;
    private final long cicloMaximo;

    private final List<Plano> planos;

    private final int numeroGrupoSemaforico;

    public Programacao(List<Plano> planos){
        this.planos = planos;
        this.cicloMaximo = calculaMMC();
        this.numeroGrupoSemaforico = quantidadeDeGruposSemaforicos();
        grupos = new HashMap<>(numeroGrupoSemaforico);
        temposDeCiclo = new HashMap<>(numeroGrupoSemaforico);
        temposDeVerdeSeguranca = new HashMap<>(numeroGrupoSemaforico);

        int index = 1;
        for(Plano plano : planos){
            for (GrupoSemaforicoPlano grupoSemaforicoPlano : plano.getGruposSemaforicosPlanos()){
                grupos.put(index,new ArrayList<>(plano.getTempoCiclo()));
                List<Intervalo> intervalos = grupoSemaforicoPlano.getIntervalos().stream().sorted((o1, o2) -> o1.getOrdem().compareTo(o2.getOrdem())).collect(Collectors.toList());;
                int i = 0;
                for(Intervalo intervalo : intervalos){
                    for(int j = 0; j < intervalo.getTamanho(); j++,i++){
                        grupos.get(index).add(i,intervalo.getEstadoGrupoSemaforico());
                    }
                }
                temposDeVerdeSeguranca.put(index,grupoSemaforicoPlano.getGrupoSemaforico().getTempoVerdeSeguranca());
                temposDeCiclo.put(index,plano.getTempoCiclo());
                index++;
            }
        }

    }

    public int quantidadeDeGruposSemaforicos(){
        return planos.stream().mapToInt(i -> i.getGruposSemaforicosPlanos().size()).sum();
    }

    public long  calculaMMC() {
        return planos.stream().mapToInt(i -> i.getTempoCiclo()).reduce((p1, p2) -> lcm(p1,p2)).getAsInt();
    }

    public int getIndex(int grupo,int instante){
        return (instante - 1) % temposDeCiclo.get(grupo);
    }

    public long getCicloMaximo() {
        return cicloMaximo;
    }

    public List<EstadoGrupoSemaforico> getProgram(int instante){
        List<EstadoGrupoSemaforico> estadoGrupoSemaforicos = new ArrayList<>(numeroGrupoSemaforico);
        for(int grupo = 1; grupo <= numeroGrupoSemaforico; grupo++){
            int indexGrupo = getIndex(grupo,instante);
            EstadoGrupoSemaforico estado = grupos.get(grupo).get(indexGrupo);
            estadoGrupoSemaforicos.add(estado);
        }
        return estadoGrupoSemaforicos;
    }

    public List<EstadoGrupoSemaforico> novaConfiguracaoSeHouverMudanca(int momentoAnterior, int momentoAtual){
        List<EstadoGrupoSemaforico> confAnterior = getProgram(momentoAnterior);
        List<EstadoGrupoSemaforico> confAtual = getProgram(momentoAtual);
        return confAnterior.equals(confAtual) ? null : confAtual;
    }

    public int proximaJanelaParaTrocaDePlano(int instante){
        int tempo = 0;
        for(int grupo = 1; grupo <= numeroGrupoSemaforico; grupo++){
            int indexGrupo = getIndex(grupo,instante);
            EstadoGrupoSemaforico estado = grupos.get(grupo).get(indexGrupo);
            if(estado.equals(EstadoGrupoSemaforico.VERDE)){
                int quantosSegundosEstavaNoVerde = rebobinaVerde(grupo,indexGrupo);
                if(quantosSegundosEstavaNoVerde < temposDeVerdeSeguranca.get(grupo)){
                    if(tempo < (temposDeVerdeSeguranca.get(grupo) - quantosSegundosEstavaNoVerde)){
                        tempo = (temposDeVerdeSeguranca.get(grupo) - quantosSegundosEstavaNoVerde);
                    }
                }
            }
        }
        return tempo;
    }

    private int rebobinaVerde(int grupo, int indexGrupo) {
        int intervalos = 0;
        while(grupos.get(grupo).get(indexGrupo).equals(EstadoGrupoSemaforico.VERDE)){
            intervalos++;
            if((indexGrupo - 1) < 0 ){
                indexGrupo = temposDeCiclo.get(grupo) - 1;
            }else{
                indexGrupo--;
            }
        }
        return intervalos;
    }

}
