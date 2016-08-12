package utils;

/**
 * Created by lesiopinheiro on 7/14/16.
 */
public class InfluuntUtils {

    private static final InfluuntUtils instance = new InfluuntUtils();

    protected InfluuntUtils() {
    }

    public static InfluuntUtils getInstance() {
        return instance;
    }

    public boolean multiplo(Integer x, Integer y) {
        //TODO: Gerar excecção caso o x ou y seja null ou x e y igual a 0
        if (x.compareTo(y) == 1) {
            return x % y == 0;
        }
        return y % x == 0;
    }
}