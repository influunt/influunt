package os72c.client.v2;

import models.EstadoGrupoSemaforico;
import models.Estagio;
import models.Plano;

import java.util.List;

/**
 * Created by rodrigosol on 9/16/16.
 */
public class Simulador {

    private final List<Plano> planos;

    private final ParametrosSimulacao params;

    private Programacao programacao;

    public Simulador(List<Plano> planos, ParametrosSimulacao params) {
        this.planos = planos;
        this.params = params;
    }

    public ResultadoSimulacao simular(){
        long momento = params.getInicioSimulacao() - params.getDataInicio();
        long fimSimulacao = params.getFimSimulacao() - params.getDataInicio();

        this.programacao = new Programacao(planos);
//        this.descolamento = params.getDataInicio();

        ResultadoSimulacao resultado = new ResultadoSimulacao(params.getVelocidadeSimulacao(),(fimSimulacao - momento) + 1);

        long cicloMaximo = this.programacao.getCicloMaximo();


        int instante = (int) (momento - ((cicloMaximo * (momento / cicloMaximo)))) + 1;
        List<EstadoGrupoSemaforico> grupos = null;
        //List<Estagio> estagios = null;
        while(momento <= fimSimulacao){

            grupos = programacao.getProgram(instante);
            //estagios = programacao.getEstagiosAtuais(instante);
            resultado.registraGrupos(grupos);

            momento++;
            if(instante + 1 > cicloMaximo){
                instante = 1;
            }else{
                instante++;
            }

        }


        return resultado;
    }

}
