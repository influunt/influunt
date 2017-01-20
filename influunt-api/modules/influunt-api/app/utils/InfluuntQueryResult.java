package utils;

import models.Controlador;

import java.util.List;

/**
 * Created by pedropires on 9/26/16.
 */
public class InfluuntQueryResult {

    private List result;

    private Integer total;

    private Class klass;

    public InfluuntQueryResult(List result, Integer total, Class klass) {
        this.result = result;
        this.total = total;
        this.klass = klass;
        setRangeUtils();
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Class getKlass() {
        return klass;
    }

    public void setKlass(Class klass) {
        this.klass = klass;
    }

    private void setRangeUtils() {
        if (Controlador.class.equals(klass)) {
            RangeUtils rangeUtils = RangeUtils.getInstance(null);
            ((List<Controlador>) result).forEach(controlador -> controlador.setRangeUtils(rangeUtils));
        }
    }
}
