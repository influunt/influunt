package utils;

import com.avaje.ebean.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import play.Logger;
import play.libs.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static utils.InfluuntUtils.parseDate;
import static utils.InfluuntUtils.underscore;

/**
 * Created by lesiopinheiro on 9/6/16.
 */
public class InfluuntQueryBuilder {

    public final static int PER_PAGE_DEFAULT = 30;

    private Class klass;

    private int page = 0;

    private int perPage;

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
        this.perPage = PER_PAGE_DEFAULT;
        if (!params.isEmpty()) {
            this.sortField = (params.get("sort") != null) ? params.get("sort")[0] : null;
            this.sortType = (params.get("sort_type") != null) ? params.get("sort_type")[0] : null;
            this.page = (params.get("page") != null) ? parseInt(params.get("page")[0]) : 0;
            this.perPage = (params.get("per_page") != null) ? parseInt(params.get("per_page")[0]) : PER_PAGE_DEFAULT;
            params.entrySet().forEach(q -> {
                if(!"sort".equals(q.getKey()) && !"sort_type".equals(q.getKey()) && !"page".equals(q.getKey()) && !"per_page".equals(q.getKey())) {
                    searchFields.put(q.getKey().toString(), q.getValue()[0].toString());
                }
            });
        }
    }


    public JsonNode query() {
        PagedList pagedList;

        if (!searchFields.isEmpty()) {
            ExpressionList predicates = Ebean.find(klass).where();

            ArrayList<SearchFieldDefinition> searchFieldDefinitions = new ArrayList<SearchFieldDefinition>();
            searchFields.forEach((key, value) -> {
                String[] keyExpression = key.split("_");

                if(!key.contains(SearchFieldDefinition.START) && !key.contains(SearchFieldDefinition.END)) {
                    if (keyExpression.length > 1) {
                        searchFieldDefinitions.add(new SearchFieldDefinition(underscore(keyExpression[0]), keyExpression[1], value));
                    } else {
                        searchFieldDefinitions.add(new SearchFieldDefinition(underscore(key), null, value));
                    }
                }

            });

            searchFieldDefinitions.forEach(searchField -> {
                DateTime date = parseDate(searchField.getValue().toString(), null);
                if (date != null) {
                    predicates.add(getFieldOperator(searchField.getFieldOperator(), searchField.getFieldName(), date));
                } else {
                    if(searchField.getFieldOperator() != null) {
                        predicates.add(getFieldOperator(searchField.getFieldOperator(), searchField.getFieldName(), searchField.getValue()));
                    } else {
                        predicates.add(Expr.icontains(searchField.getFieldName(), searchField.getValue().toString()));
                    }
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

            if (getSortField() != null) {
                pagedList = predicates.orderBy(getSortField().concat(" ").concat(getSortType())).findPagedList(getPage(), getPerPage());
            } else {
                pagedList = predicates.findPagedList(getPage(), getPerPage());
            }
        } else {
            if (getSortField() != null) {
                pagedList = Ebean.find(getKlass()).orderBy(getSortField().concat(" ").concat(getSortType())).findPagedList(getPage(), getPerPage());
            } else {
                pagedList = Ebean.find(getKlass()).findPagedList(getPage(), getPerPage());
            }
        }

        ObjectNode retorno = JsonNodeFactory.instance.objectNode();
        List result = pagedList.getList();
        JsonNode dataJson = Json.toJson(result);
        retorno.set("data", dataJson);
        retorno.put("total", pagedList.getTotalRowCount());

        return retorno;
    }

    public Class getKlass() {
        return klass;
    }

    public void setKlass(Class klass) {
        this.klass = klass;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public Map<String, Object> getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(Map<String, Object> searchFields) {
        this.searchFields = searchFields;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
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
