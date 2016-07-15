package utils;

/**
 * Created by lesiopinheiro on 7/14/16.
 */
public class RangeUtils {

    private static final RangeUtils instance = new RangeUtils();

    protected RangeUtils() {
    }

    public static RangeUtils getInstance() {
        return instance;
    }


    public static InfluuntRange TEMPO_VERDE_MINIMO = new InfluuntRange(10, 255);
    public static InfluuntRange TEMPO_VERDE_MAXIMO = new InfluuntRange(10, 255);
    public static InfluuntRange TEMPO_VERDE_INTERMEDIARIO = new InfluuntRange(10, 255);
    public static InfluuntRange TEMPO_EXTENSAO_VERDE = new InfluuntRange(1d, 10d);
    public static InfluuntRange TEMPO_VERDE = new InfluuntRange(1, 255);

}