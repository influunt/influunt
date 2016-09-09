package utils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.InfluuntUtils.*;

/**
 * Created by lesiopinheiro on 9/8/16.
 */
public class BetweenFieldDefinition {

    private String fieldName;

    private Object startValue;

    private Object endValue;

    public BetweenFieldDefinition(String fieldName, Object startValue, Object endValue) {
        this.fieldName = fieldName;
        this.startValue = startValue;
        this.endValue = endValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getStartValue() {
        return startValue;
    }

    public void setStartValue(Object startValue) {
        this.startValue = startValue;
    }

    public Object getEndValue() {
        return endValue;
    }

    public void setEndValue(Object endValue) {
        this.endValue = endValue;
    }

    public static List<BetweenFieldDefinition> getBetweenFileds(Map<String, Object> searchFields) {
        List<BetweenFieldDefinition> list = new ArrayList<>();
        List<String> guestList = new ArrayList<>();
        searchFields.forEach((key, value) -> {
            String[] keyExpression = key.split("_");
            Object valueAux = value;
            if (keyExpression.length > 1 && (SearchFieldDefinition.START.equals(keyExpression[1]) || SearchFieldDefinition.END.equals(keyExpression[1])) && !guestList.contains(key)) {
                DateTime date = parseDate(value.toString(), null);
                if(date != null) {
                    valueAux = date;
                } else {
                    valueAux = value;
                }
                guestList.add(key);
                if (SearchFieldDefinition.START.equals(keyExpression[1])) {
                    Object endValue = searchFields.get(keyExpression[0].concat("_").concat(SearchFieldDefinition.END));
                    list.add(new BetweenFieldDefinition(underscore(keyExpression[0]), valueAux, endValue));
                    guestList.add(keyExpression[0].concat("_").concat(SearchFieldDefinition.END));
                } else {
                    Object startValue = searchFields.get(keyExpression[0].concat("_").concat(SearchFieldDefinition.START));
                    list.add(new BetweenFieldDefinition(underscore(keyExpression[0]), startValue, valueAux));
                    guestList.add(keyExpression[0].concat("_").concat(SearchFieldDefinition.START));
                }
            }
        });

        return list;
    }

    public boolean hasOnlyStartValue() {
        return this.getStartValue() != null && this.getEndValue() == null;
    }

    public boolean hasOnlyEndValue() {
        return this.getStartValue() != null && this.getEndValue() == null;
    }
}
