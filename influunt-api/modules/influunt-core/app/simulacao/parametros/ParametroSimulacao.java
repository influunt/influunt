//package simulacao.parametros;
//
//import models.Controlador;
//import org.joda.time.DateTime;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
///**
// * Created by rodrigosol on 10/4/16.
// */
//public class ParametroSimulacao {
//    private UUID id;
//    private Controlador controlador;
//    private int velocidade;
//    private DateTime inicioControlador;
//    private DateTime inicioSimulacao;
//    private DateTime fimSimulacao;
//
//
//    private List<ParametroSimulacaoDetector> detectores = new ArrayList<>();
//    private List<ParametroSimulacaoImposicaoPlano> imposicoes = new ArrayList<>();
//    private List<ParametroFalha> falhas = new ArrayList<>();
//
//    public ParametroSimulacao(){
//        this.id = UUID.randomUUID();
//    }
//    public Controlador getControlador() {
//        return controlador;
//    }
//
//    public void setControlador(Controlador controlador) {
//        this.controlador = controlador;
//    }
//
//    public int getVelocidade() {
//        return velocidade;
//    }
//
//    public void setVelocidade(int velocidade) {
//        this.velocidade = velocidade;
//    }
//
//    public DateTime getInicioControlador() {
//        return inicioControlador;
//    }
//
//    public void setInicioControlador(DateTime inicioControlador) {
//        this.inicioControlador = inicioControlador;
//    }
//
//    public DateTime getInicioSimulacao() {
//        return inicioSimulacao;
//    }
//
//    public void setInicioSimulacao(DateTime inicioSimulacao) {
//        this.inicioSimulacao = inicioSimulacao;
//    }
//
//    public DateTime getFimSimulacao() {
//        return fimSimulacao;
//    }
//
//    public void setFimSimulacao(DateTime fimSimulacao) {
//        this.fimSimulacao = fimSimulacao;
//    }
//
//    public List<ParametroSimulacaoDetector> getDetectores() {
//        return detectores;
//    }
//
//    public void setDetectores(List<ParametroSimulacaoDetector> detectores) {
//        this.detectores = detectores;
//    }
//
//    public List<ParametroSimulacaoImposicaoPlano> getImposicoes() {
//        return imposicoes;
//    }
//
//    public void setImposicoes(List<ParametroSimulacaoImposicaoPlano> imposicoes) {
//        this.imposicoes = imposicoes;
//    }
//
//    public List<ParametroFalha> getFalhas() {
//        return falhas;
//    }
//
//    public void setFalhas(List<ParametroFalha> falhas) {
//        this.falhas = falhas;
//    }
//
//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//}
