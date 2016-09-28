package os72c.client.v2;

import models.EstadoGrupoSemaforico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rodrigosol on 9/16/16.
 */
public class ResultadoSimulacao {
    private final VelocidadeSimulacao velocidadeSimulacao;

    private final long steps;

    private long pointer = 0;

    private List<HashMap<Integer, HashMap<EstadoGrupoSemaforico, Long>>> tempoGrupos = new ArrayList<>();

    private long tempoSimulacao;

    public ResultadoSimulacao(VelocidadeSimulacao velocidadeSimulacao, long tempoSimulacao) {
        this.velocidadeSimulacao = velocidadeSimulacao;
        this.tempoSimulacao = tempoSimulacao;
        if (velocidadeSimulacao.equals(VelocidadeSimulacao.RESULTADO_FINAL)) {
            this.steps = 1l;
        } else {
            this.steps = tempoSimulacao / velocidadeSimulacao.getVelocidade();
        }
        for (int i = 0; i < steps; i++) {
            tempoGrupos.add(new HashMap<>());
        }

    }

    public void registraGrupos(List<EstadoGrupoSemaforico> grupos) {
        int localStep = (int) (pointer % steps);

        for (int i = 1; i <= grupos.size(); i++) {
            if (tempoGrupos.get(localStep).get(i) == null || tempoGrupos.get(localStep).get(i).get(grupos.get(i - 1)) == null) {
                HashMap<EstadoGrupoSemaforico, Long> local;

                if (tempoGrupos.get(localStep).get(i) == null) {
                    local = new HashMap<>();
                } else {
                    local = tempoGrupos.get(localStep).get(i);
                }

                local.put(grupos.get(i - 1), 1l);
                tempoGrupos.get(localStep).put(i, local);
            } else {
                Long atual = tempoGrupos.get(localStep).get(i).get(grupos.get(i - 1));
                tempoGrupos.get(localStep).get(i).put(grupos.get(i - 1), atual + 1);
            }
        }
        pointer++;
    }

    public long getTempoSimulacao() {
        return tempoSimulacao;
    }

    public void setTempoSimulacao(long tempoSimulacao) {
        this.tempoSimulacao = tempoSimulacao;
    }

    public HashMap<EstadoGrupoSemaforico, Long> getTemposGrupo(int step, int grupo) {
        return tempoGrupos.get(step).get(grupo);
    }

    public List<HashMap<Integer, HashMap<EstadoGrupoSemaforico, Long>>> tempoGrupos() {
        return tempoGrupos;
    }

    public void play(long momento) {

    }

    public void foward(long momento) {

    }

    public void backward(long momento) {

    }
}
