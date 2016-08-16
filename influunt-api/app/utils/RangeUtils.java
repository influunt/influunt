package utils;

/**
 * Created by lesiopinheiro on 7/14/16.
 */
public class RangeUtils {

    public static InfluuntRange TEMPO_DEFASAGEM = new InfluuntRange(0, 255);
    public static InfluuntRange TEMPO_AMARELO = new InfluuntRange(3, 5);
    public static InfluuntRange TEMPO_VERMELHO_INTERMITENTE = new InfluuntRange(3, 32);
    public static InfluuntRange TEMPO_VERMELHO_LIMPEZA_VEICULAR = new InfluuntRange(0, 7);
    public static InfluuntRange TEMPO_VERMELHO_LIMPEZA_PEDESTRE = new InfluuntRange(0, 5);
    public static InfluuntRange TEMPO_ATRASO_GRUPO = new InfluuntRange(0, 20);
    public static InfluuntRange TEMPO_VERDE_SEGURANCA_VEICULAR = new InfluuntRange(10, 30);
    public static InfluuntRange TEMPO_VERDE_SEGURANCA_PEDESTRE = new InfluuntRange(4, 10);
    public static InfluuntRange TEMPO_MAXIMO_PERMANECIA_ESTAGIO = new InfluuntRange(60, 255);
    public static InfluuntRange TEMPO_CICLO = new InfluuntRange(30, 255);
    public static InfluuntRange TEMPO_VERDE_MINIMO = new InfluuntRange(10, 255);
    public static InfluuntRange TEMPO_VERDE_MAXIMO = new InfluuntRange(10, 255);
    public static InfluuntRange TEMPO_VERDE_INTERMEDIARIO = new InfluuntRange(10, 255);
    public static InfluuntRange TEMPO_EXTENSAO_VERDE = new InfluuntRange(1d, 10d);
    public static InfluuntRange TEMPO_VERDE = new InfluuntRange(1, 255);
    private static final RangeUtils instance = new RangeUtils();

    protected RangeUtils() {
    }

    public static RangeUtils getInstance() {
        return instance;
    }

}
