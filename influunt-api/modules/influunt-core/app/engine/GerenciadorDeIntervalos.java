package engine;

import models.*;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import scala.Int;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by rodrigosol on 9/15/16.
 */
public class GerenciadorDeIntervalos {

    private HashMap<Integer, Integer> temposDeCiclo;

    private HashMap<Integer, Integer> temposDeCicloPorAnel;

    private HashMap<Integer, Integer> temposDeVerdeSeguranca;

    private HashMap<Integer, List<EstadoGrupoSemaforico>> grupos;

    private HashMap<Integer, List<Estagio>> estagios;

    private final GerenciadorDeIntervalosCallBack callback;

    private HashMap<DateTime, HashMap<Integer,Pair<Integer,Plano>>> trocaDePlanos = new HashMap<>();
    private HashMap<Integer,List<Integer>> gruposPorAnel;
    private HashMap<Integer,Integer> delayGrupo = new HashMap<>();
    private final List<Plano> planos;

    private int numeroGrupoSemaforico;



    public <E> GerenciadorDeIntervalos(List<Plano> planos, GerenciadorDeIntervalosCallBack callback) {
        this.planos = planos;
        this.callback = callback;


        processaPlanos();

    }

    public int quantidadeDeGruposSemaforicos() {
        return planos.stream().mapToInt(i -> i.getGruposSemaforicosPlanos().size()).sum();
    }

    public long calculaMMC() {
        return planos.stream().mapToInt(i -> i.getTempoCiclo()).reduce((p1, p2) -> ArithmeticUtils.lcm(p1, p2)).getAsInt();
    }

    public long getIndex(int grupo, DateTime instante) {
        return (instante.getMillis()/1000) % temposDeCiclo.get(grupo);
    }

    public int getIndexAnel(int anel, int instante) {
        return (instante - 1) % temposDeCicloPorAnel.get(anel);
    }

    public List<Estagio> getEstagiosAtuais(int instante) {
        return estagios.keySet().stream().map(key -> estagios.get(key).get(getIndexAnel(key, instante))).collect(Collectors.toList());
    }

    public List<Estagio> novaConfiguracaoEstagioSeHouverMudanca(int momentoAnterior, int momentoAtual) {
        List<Estagio> confAnterior = getEstagiosAtuais(momentoAnterior);
        List<Estagio> confAtual = getEstagiosAtuais(momentoAtual);
        return confAnterior.equals(confAtual) ? null : confAtual;
    }


    public List<EstadoGrupoSemaforico> getProgram(DateTime instante) {

        if(trocaDePlanos.containsKey(instante)){

            for(Map.Entry<Integer, Pair<Integer, Plano>> anel : trocaDePlanos.get(instante).entrySet()){
                final int posicaoAnel = anel.getKey();
                planos.set(posicaoAnel, anel.getValue().getSecond());
//                gruposPorAnel.get(posicaoAnel).stream().forEach(i -> {
//                    delayGrupo.put(i,anel.getValue().getFirst());
//                });
                processaPlanos();
            }
        }

        List<EstadoGrupoSemaforico> estadoGrupoSemaforicos = new ArrayList<>(numeroGrupoSemaforico);
        for (int grupo = 1; grupo <= numeroGrupoSemaforico; grupo++) {
            EstadoGrupoSemaforico estado;
            if(grupos.get(grupo).size() != 0) {
                long indexGrupo = getIndex(grupo, instante);
                estado = grupos.get(grupo).get((int) indexGrupo);
            }else{
                estado = EstadoGrupoSemaforico.DESLIGADO;
            }
            estadoGrupoSemaforicos.add(estado);
        }
        return estadoGrupoSemaforicos;
    }

    private void processaPlanos(){

        this.numeroGrupoSemaforico = quantidadeDeGruposSemaforicos();
        grupos = new HashMap<>(numeroGrupoSemaforico);
        temposDeCiclo = new HashMap<>(numeroGrupoSemaforico);
        temposDeCicloPorAnel = new HashMap<>(planos.size());
        temposDeVerdeSeguranca = new HashMap<>(numeroGrupoSemaforico);
        estagios = new HashMap<>(planos.size());
        gruposPorAnel = new HashMap<>();
        int index = 1;
        for (Plano plano : planos) {
            int indexEstagio = 0;
            ArrayList<Estagio> lista = new ArrayList<>();
            if(!gruposPorAnel.containsKey(plano.getAnel().getPosicao())) {
                gruposPorAnel.put(plano.getAnel().getPosicao(), new ArrayList<>());
            }

            List<GrupoSemaforicoPlano> grupoSemaforicoPlanos = plano.getGruposSemaforicosPlanos()
                    .stream()
                    .sorted((o1, o2) -> o1.getGrupoSemaforico().getPosicao().compareTo(o2.getGrupoSemaforico().getPosicao()))
                    .collect(Collectors.toList());

            for (GrupoSemaforicoPlano grupoSemaforicoPlano : grupoSemaforicoPlanos) {
                grupos.put(index, new ArrayList<>(plano.getTempoCiclo()));
                gruposPorAnel.get(plano.getAnel().getPosicao()).add(index);
                List<Intervalo> intervalos = grupoSemaforicoPlano.getIntervalos().stream().sorted((o1, o2) -> o1.getOrdem().compareTo(o2.getOrdem())).collect(Collectors.toList());
                int i = 0;
                for (Intervalo intervalo : intervalos) {
                    for (int j = 0; j < intervalo.getTamanho(); j++, i++) {
                        grupos.get(index).add(i, intervalo.getEstadoGrupoSemaforico());
                    }
                }
                temposDeVerdeSeguranca.put(index, grupoSemaforicoPlano.getGrupoSemaforico().getTempoVerdeSeguranca());
                temposDeCiclo.put(index, plano.getTempoCiclo());
                index++;
                if(index > 16){
                    System.out.println("Erro");
                }
            }

            for (EstagioPlano estagioPlano : plano.ordenarEstagiosPorPosicao()) {
                for (int i = 0; i < estagioPlano.getDuracaoEstagio(); i++) {
                    lista.add(indexEstagio, estagioPlano.getEstagio());
                    indexEstagio++;
                }
                estagios.put(plano.getAnel().getPosicao(), lista);

            }

            temposDeCicloPorAnel.put(plano.getAnel().getPosicao(), plano.getTempoCiclo());
        }
    }


//    public int proximaJanelaParaTrocaDePlano(int instante) {
//        int tempo = 0;
//        for (int grupo = 1; grupo <= numeroGrupoSemaforico; grupo++) {
//            int indexGrupo = getIndex(grupo, instante);
//            EstadoGrupoSemaforico estado = grupos.get(grupo).get(indexGrupo);
//            if (estado.equals(EstadoGrupoSemaforico.VERDE)) {
//                int quantosSegundosEstavaNoVerde = rebobinaVerde(grupo, indexGrupo);
//                if (quantosSegundosEstavaNoVerde < temposDeVerdeSeguranca.get(grupo)) {
//                    if (tempo < (temposDeVerdeSeguranca.get(grupo) - quantosSegundosEstavaNoVerde)) {
//                        tempo = (temposDeVerdeSeguranca.get(grupo) - quantosSegundosEstavaNoVerde);
//                    }
//                }
//            }
//        }
//        return tempo;
//    }

//    private int rebobinaVerde(int grupo, int indexGrupo) {
//        int intervalos = 0;
//        while (grupos.get(grupo).get(indexGrupo).equals(EstadoGrupoSemaforico.VERDE)) {
//            intervalos++;
//            if ((indexGrupo - 1) < 0) {
//                indexGrupo = temposDeCiclo.get(grupo) - 1;
//            } else {
//                indexGrupo--;
//            }
//        }
//        return intervalos;
//    }

    public List<EstadoGrupoSemaforico> getEstadosGrupo(DateTime instante) {
        return getProgram(instante);
    }

    public void trocarPlanos(DateTime instante, List<Plano> planos) {



        for(int anel = 1; anel <= planos.size(); anel++){
            int delay = (int) (temposDeCicloPorAnel.get(anel) - (instante.getMillis()/1000 % temposDeCicloPorAnel.get(anel)));
            DateTime momento = instante.plusSeconds(delay);
            if(!trocaDePlanos.containsKey(momento)){
                trocaDePlanos.put(momento,new HashMap<>());
            }
            trocaDePlanos.get(momento).put(anel - 1,new Pair<Integer, Plano>(delay,planos.get(anel -1)));
            callback.onAgendamentoDeTrocaDePlanos(instante,momento,anel,planos.get(anel-1).getPosicao(),this.planos.get(anel-1).getPosicao());
        }
    }

}
