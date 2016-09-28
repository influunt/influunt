package engine;

import models.*;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by rodrigosol on 9/15/16.
 */
public class GerenciadorDeIntervalos {

    private final HashMap<Integer, Integer> temposDeCiclo;

    private final HashMap<Integer, Integer> temposDeCicloPorAnel;

    private final HashMap<Integer, Integer> temposDeVerdeSeguranca;

    private final HashMap<Integer, List<EstadoGrupoSemaforico>> grupos;

    private final HashMap<Integer, List<Estagio>> estagios;

    private final long cicloMaximo;

    private final List<Plano> planos;

    private final int numeroGrupoSemaforico;

    public <E> GerenciadorDeIntervalos(List<Plano> planos) {
        this.planos = planos;
        this.cicloMaximo = calculaMMC();
        this.numeroGrupoSemaforico = quantidadeDeGruposSemaforicos();
        grupos = new HashMap<>(numeroGrupoSemaforico);
        temposDeCiclo = new HashMap<>(numeroGrupoSemaforico);
        temposDeCicloPorAnel = new HashMap<>(planos.size());
        temposDeVerdeSeguranca = new HashMap<>(numeroGrupoSemaforico);
        estagios = new HashMap<>(planos.size());

        int index = 1;
        for (Plano plano : planos) {
            int indexEstagio = 0;
            ArrayList<Estagio> lista = new ArrayList<>();
            for (GrupoSemaforicoPlano grupoSemaforicoPlano : plano.getGruposSemaforicosPlanos()) {
                grupos.put(index, new ArrayList<>(plano.getTempoCiclo()));
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

    public int quantidadeDeGruposSemaforicos() {
        return planos.stream().mapToInt(i -> i.getGruposSemaforicosPlanos().size()).sum();
    }

    public long calculaMMC() {
        return planos.stream().mapToInt(i -> i.getTempoCiclo()).reduce((p1, p2) -> ArithmeticUtils.lcm(p1, p2)).getAsInt();
    }

    public int getIndex(int grupo, int instante) {
        return (instante - 1) % temposDeCiclo.get(grupo);
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

    public long getCicloMaximo() {
        return cicloMaximo;
    }

    public List<EstadoGrupoSemaforico> getProgram(int instante) {
        List<EstadoGrupoSemaforico> estadoGrupoSemaforicos = new ArrayList<>(numeroGrupoSemaforico);
        for (int grupo = 1; grupo <= numeroGrupoSemaforico; grupo++) {
            int indexGrupo = getIndex(grupo, instante);
            EstadoGrupoSemaforico estado = grupos.get(grupo).get(indexGrupo);
            estadoGrupoSemaforicos.add(estado);
        }
        return estadoGrupoSemaforicos;
    }

    public List<EstadoGrupoSemaforico> novaConfiguracaoSeHouverMudanca(int momentoAnterior, int momentoAtual) {
        List<EstadoGrupoSemaforico> confAnterior = getProgram(momentoAnterior);
        List<EstadoGrupoSemaforico> confAtual = getProgram(momentoAtual);
        return confAnterior.equals(confAtual) ? null : confAtual;
    }

    public int proximaJanelaParaTrocaDePlano(int instante) {
        int tempo = 0;
        for (int grupo = 1; grupo <= numeroGrupoSemaforico; grupo++) {
            int indexGrupo = getIndex(grupo, instante);
            EstadoGrupoSemaforico estado = grupos.get(grupo).get(indexGrupo);
            if (estado.equals(EstadoGrupoSemaforico.VERDE)) {
                int quantosSegundosEstavaNoVerde = rebobinaVerde(grupo, indexGrupo);
                if (quantosSegundosEstavaNoVerde < temposDeVerdeSeguranca.get(grupo)) {
                    if (tempo < (temposDeVerdeSeguranca.get(grupo) - quantosSegundosEstavaNoVerde)) {
                        tempo = (temposDeVerdeSeguranca.get(grupo) - quantosSegundosEstavaNoVerde);
                    }
                }
            }
        }
        return tempo;
    }

    private int rebobinaVerde(int grupo, int indexGrupo) {
        int intervalos = 0;
        while (grupos.get(grupo).get(indexGrupo).equals(EstadoGrupoSemaforico.VERDE)) {
            intervalos++;
            if ((indexGrupo - 1) < 0) {
                indexGrupo = temposDeCiclo.get(grupo) - 1;
            } else {
                indexGrupo--;
            }
        }
        return intervalos;
    }

    public List<EstadoGrupoSemaforico> getEstadosGrupo(DateTime instante) {
        int i = (int) (instante.getMillis() % cicloMaximo);
        return getProgram(i + 1);
    }
}
