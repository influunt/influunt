package utils;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionList;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import play.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static utils.InfluuntUtils.*;

/**
 * Created by lesiopinheiro on 9/6/16.
 */
public class InfluuntQueryBuilder {

    private Class klass;

    private int page = 1;

    private int perPage = 20;

    private Map<String, Object> searchFields = new HashMap<String, Object>();

    private String sortField;

    private String sortType;

    public static final String FG_BLUE = (char) 27 + "[0;34m";
    public static final String FG_GREEN = (char) 27 + "[0;32m";
    public static final String FG_BROWN = (char) 27 + "[0;33m";
    public static final String FG_YELLOW = (char) 27 + "[1;33m";
    public static final String FG_DEFAULT = (char) 27 + "[0;0m";
    public static final String FG_WHITE = (char) 27 + "[1;37m";

    public InfluuntQueryBuilder(Class klass, Map<String, String[]> params) {
        this.klass = klass;
        if (!params.isEmpty()) {
            this.sortField = (params.get("sort") != null) ? params.get("sort")[0] : null;
            this.sortType = (params.get("sort_type") != null) ? params.get("sort_type")[0] : null;
            this.page = (params.get("page") != null) ? parseInt(params.get("page")[0]) : 1;
            this.perPage = (params.get("perPage") != null) ? parseInt(params.get("perPage")[0]) : 20;
            params.entrySet().forEach(q -> {
                if(!"sort".equals(q.getKey()) && !"sort_type".equals(q.getKey()) && !"page".equals(q.getKey()) && !"perPage".equals(q.getKey())) {
                    searchFields.put(q.getKey().toString(), q.getValue()[0].toString());
                }
            });
        }
    }

    public List<? extends Class> query() {
        Logger.debug(FG_GREEN + "SQL: " + klass.getName() + FG_DEFAULT);

        if (!searchFields.isEmpty()) {
            ExpressionList predicates = Ebean.find(klass).where();

            ArrayList<SearchFieldDefinition> searchFieldDefinitions = new ArrayList<SearchFieldDefinition>();
            searchFields.forEach((key, value) -> {
                String[] keyExpression = key.split("_");

                if(!key.contains(SearchFieldDefinition.START) && !key.contains(SearchFieldDefinition.END)) {
                    if (keyExpression.length > 1) {
                        searchFieldDefinitions.add(new SearchFieldDefinition(underscore(keyExpression[0]), keyExpression[1], value));
                    } else {
                        searchFieldDefinitions.add(new SearchFieldDefinition(underscore(key), SearchFieldDefinition.EQ, value));
                    }
                }

            });

            searchFieldDefinitions.forEach(searchField -> {
                DateTime date = parseDate(searchField.getValue().toString(), null);
                if (date != null) {
                    predicates.add(getFieldOperator(searchField.getFieldOperator(), searchField.getFieldName(), date));
                } else if (NumberUtils.isNumber(searchField.getValue().toString())) {
                    predicates.add(getFieldOperator(searchField.getFieldOperator(), searchField.getFieldName(), searchField.getValue()));
                } else {
                    predicates.add(Expr.icontains(searchField.getFieldName(), searchField.getValue().toString()));
                }
            });


            // Verifica se existem campos com between
            List<BetweenFieldDefinition> betweenFiels = BetweenFieldDefinition.getBetweenFileds(searchFields);
            betweenFiels.forEach(field ->{
                if(field.hasOnlyStartValue()) {
                    predicates.add(Expr.ge(field.getFieldName(), field.getStartValue()));
                } else if (field.hasOnlyEndValue()) {
                    predicates.add(Expr.le(field.getFieldName(), field.getEndValue()));
                } else {
                    predicates.add(Expr.between(field.getFieldName(), field.getStartValue(), field.getEndValue()));
                }
            });

            if (sortField != null) {
                return predicates.orderBy(sortField.concat(" ").concat(sortType)).findList();
            } else {
                return predicates.findList();
            }
        } else {
            return Ebean.find(klass).findList();
        }
    }

    private Expression getFieldOperator(String operator, String key, Object value) {
        Expression expr;
        switch (operator) {
            case SearchFieldDefinition.LT:
                expr = Expr.lt(key, value);
                break;
            case SearchFieldDefinition.LTE:
                expr = Expr.le(key, value);
                break;
            case SearchFieldDefinition.GT:
                expr = Expr.gt(key, value);
                break;
            case SearchFieldDefinition.GTE:
                expr = Expr.ge(key, value);
                break;
            default:
                expr = Expr.ieq(key, value.toString());
                break;
        }

        return expr;
    }


}
