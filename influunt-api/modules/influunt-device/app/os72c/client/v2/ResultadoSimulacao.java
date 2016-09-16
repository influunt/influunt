package os72c.client.v2;

import models.EstadoGrupoSemaforico;
import org.apache.commons.collections.map.MultiKeyMap;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rodrigosol on 9/16/16.
 */
public class ResultadoSimulacao {
    private HashMap<Integer,HashMap<EstadoGrupoSemaforico,Long>> tempoGrupos = new HashMap<>();

    private long tempoSimulacao;

    public void registraGrupos(List<EstadoGrupoSemaforico> grupos) {
        for(int i = 1; i <= grupos.size(); i++){
            if(tempoGrupos.get(i) == null || tempoGrupos.get(i).get(grupos.get(i-1)) == null){
                HashMap<EstadoGrupoSemaforico, Long> local;

                if(tempoGrupos.get(i) == null) {
                    local = new HashMap<>();
                }else{
                    local = tempoGrupos.get(i);
                }

                local.put(grupos.get(i-1),1l);
                tempoGrupos.put(i,local);
            }else{
                Long atual = tempoGrupos.get(i).get(grupos.get(i - 1));
                tempoGrupos.get(i).put(grupos.get(i-1),atual+1);
            }
        }
    }

    public void setTempoSimulacao(long tempoSimulacao) {
        this.tempoSimulacao = tempoSimulacao;
    }

    public long getTempoSimulacao() {
        return tempoSimulacao;
    }


    public HashMap<EstadoGrupoSemaforico,Long> getTemposGrupo(int grupo) {
        return tempoGrupos.get(grupo);
    }

    public HashMap<Integer,HashMap<EstadoGrupoSemaforico,Long>> tempoGrupos() {
        return tempoGrupos;
    }
}
