package utils;

import com.avaje.ebean.*;
import models.Controlador;
import models.StatusVersao;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.jongo.MongoCursor;
import security.Auditoria;

import java.util.*;
import java.util.function.BiConsumer;

import static java.lang.Integer.parseInt;
import static utils.InfluuntUtils.parseDate;
import static utils.InfluuntUtils.underscore;

/**
 * Created by lesiopinheiro on 9/6/16.
 */
public class InfluuntQueryBuilder {

    public static final int PER_PAGE_DEFAULT = 30;

    private Class klass;

    private int page;

    private int perPage;

    private Map<String, Object> searchFields = new HashMap<String, Object>();

    private String sortField;

    private String sortType;

    private List<String> fetches;

    private boolean reportMode;

    public InfluuntQueryBuilder(Class klass, Map<String, String[]> params) {
        this.klass = klass;
        this.perPage = PER_PAGE_DEFAULT;
        this.fetches = new ArrayList<>();
        if (params != null && !params.isEmpty()) {
            this.sortField = (params.get("sort") != null) ? params.get("sort")[0] : null;
            this.sortType = (params.get("sort_type") != null) ? params.get("sort_type")[0] : null;
            this.page = (params.get("page") != null) ? parseInt(params.get("page")[0]) : 0;
            this.perPage = (params.get("per_page") != null) ? parseInt(params.get("per_page")[0]) : PER_PAGE_DEFAULT;
            params.entrySet().forEach(q -> {
                if (!"sort".equals(q.getKey()) && !"sort_type".equals(q.getKey()) && !"page".equals(q.getKey()) && !"per_page".equals(q.getKey())) {
                    searchFields.put(q.getKey(), q.getValue()[0]);
                }
            });
        }
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

    public InfluuntQueryBuilder fetch(List<String> fetches) {
        this.fetches = fetches;
        return this;
    }

    public InfluuntQueryResult query() {
        PagedList pagedList;
        Query query = Ebean.find(klass);

        fetches.forEach(fetchAux -> {
            query.fetch(fetchAux);
        });

        if (klass.equals(Controlador.class)) {
            query.fetch("endereco");
        }

        if (!searchFields.isEmpty()) {
            ExpressionList predicates = query.where();

            ArrayList<SearchFieldDefinition> searchFieldDefinitions = new ArrayList<SearchFieldDefinition>();
            searchFields.forEach(buildSearchStatement(searchFieldDefinitions));

            searchFieldDefinitions.forEach(searchField -> {
                DateTime date = parseDate(searchField.getValue().toString(), null);
                if (date != null) {
                    predicates.add(getFieldOperator(searchField.getFieldOperator(), searchField.getFieldName(), date));
                } else {
                    if (searchField.getFieldOperator() != null) {
                        predicates.add(getFieldOperator(searchField.getFieldOperator(), searchField.getFieldName(), searchField.getValue()));
                    } else {
                        predicates.add(Expr.icontains(searchField.getFieldName(), searchField.getValue().toString()));
                    }
                }
            });

            if (klass.equals(Controlador.class)) {
                predicates.add(Expr.in("versaoControlador.statusVersao", Arrays.asList(StatusVersao.CONFIGURADO, StatusVersao.ATIVO, StatusVersao.EDITANDO)));
            }

            // Verifica se existem campos com between
            List<BetweenFieldDefinition> betweenFields = BetweenFieldDefinition.getBetweenFileds(searchFields);
            betweenFields.forEach(field -> {
                if (field.hasOnlyStartValue()) {
                    predicates.add(Expr.ge(field.getFieldName(), field.getStartValue()));
                } else if (field.hasOnlyEndValue()) {
                    predicates.add(Expr.le(field.getFieldName(), field.getEndValue()));
                } else {
                    predicates.add(Expr.between(field.getFieldName(), field.getStartValue(), field.getEndValue()));
                }
            });

            if (getSortField() != null) {
                if (reportMode) {
                    pagedList = predicates.orderBy(getSortField().concat(" ").concat(getSortType())).findPagedList();
                } else {
                    pagedList = predicates.orderBy(getSortField().concat(" ").concat(getSortType())).findPagedList(getPage(), getPerPage());
                }
            } else {
                if (reportMode) {
                    pagedList = predicates.findPagedList();
                } else {
                    pagedList = predicates.findPagedList(getPage(), getPerPage());
                }
            }
        } else {
            if (klass.equals(Controlador.class)) {
                query.where().add((Expr.in("versaoControlador.statusVersao", Arrays.asList(StatusVersao.CONFIGURADO, StatusVersao.ATIVO, StatusVersao.EDITANDO))));
            }
            if (getSortField() != null) {
                if (reportMode) {
                    pagedList = query.orderBy(getSortField().concat(" ").concat(getSortType())).findPagedList();
                } else {
                    pagedList = query.orderBy(getSortField().concat(" ").concat(getSortType())).findPagedList(getPage(), getPerPage());
                }
            } else {
                pagedList = query.findPagedList(getPage(), getPerPage());
            }
        }

        return new InfluuntQueryResult(pagedList.getList(), pagedList.getTotalRowCount(), klass);
    }

    public InfluuntQueryResult auditoriaQuery() {
        List<String> predicates = new ArrayList<>();
        MongoCursor<Auditoria> auditorias;
        int total = 0;
        if (!searchFields.isEmpty()) {

            ArrayList<SearchFieldDefinition> searchFieldDefinitions = new ArrayList<SearchFieldDefinition>();
            searchFields.forEach(buildSearchStatement(searchFieldDefinitions));

            searchFieldDefinitions.forEach(searchField -> {
                DateTime date = parseDate(searchField.getValue().toString(), null);
                if (date != null) {
                    predicates.add(getMongoFieldOperator(searchField.getFieldOperator(), searchField.getFieldName(), date));
                } else {
                    if (searchField.getFieldOperator() != null) {
                        predicates.add(getMongoFieldOperator(searchField.getFieldOperator(), searchField.getFieldName(), searchField.getValue()));
                    } else {
                        predicates.add(String.format("'%s': {$regex: '%s', $options: 'i'}", searchField.getFieldName(), searchField.getValue().toString()));
                    }
                }
            });

            // Verifica se existem campos com between
            List<BetweenFieldDefinition> betweenFields = BetweenFieldDefinition.getBetweenFileds(searchFields);
            betweenFields.forEach(field -> {
                if (field.hasOnlyStartValue()) {
                    predicates.add(String.format("timestamp: { $gte: %s}", field.getStartValueTimestamp()));
                } else if (field.hasOnlyEndValue()) {
                    predicates.add(String.format("timestamp: { $lte: %s}", field.getEndValueTimestamp()));
                } else {
                    predicates.add(String.format("timestamp: { $gte: %s, $lte: %s}", field.getStartValueTimestamp(), field.getEndValueTimestamp()));
                }
            });

            String query = "{".concat(String.join(",", predicates)).concat("}");
            if (getSortField() != null) {
                int sortTypeAux = getSortType().equalsIgnoreCase("asc") ? 1 : -1;
                if (reportMode) {
                    auditorias = Auditoria.auditorias().find(query).sort("{".concat(getSortType()).concat(String.format(": %s", sortTypeAux)).concat("}")).as(Auditoria.class);
                } else {
                    auditorias = Auditoria.auditorias().find(query).skip(getSkip()).sort("{".concat(getSortType()).concat(String.format(": %s", sortTypeAux)).concat("}")).limit(getPerPage()).as(Auditoria.class);
                }
            } else {
                if (reportMode) {
                    auditorias = Auditoria.auditorias().find(query).as(Auditoria.class);
                } else {
                    auditorias = Auditoria.auditorias().find(query).skip(getSkip()).limit(getPerPage()).as(Auditoria.class);
                }
            }
            total = Auditoria.auditorias().find(query).as(Auditoria.class).count();
        } else {
            if (getSortField() != null) {
                int sortTypeAux = getSortType().equalsIgnoreCase("asc") ? 1 : -1;
                if (reportMode) {
                    auditorias = Auditoria.auditorias().find().sort("{".concat(getSortField()).concat(String.format(": %s", sortTypeAux)).concat("}")).as(Auditoria.class);
                } else {
                    auditorias = Auditoria.auditorias().find().skip(getSkip()).sort("{".concat(getSortField()).concat(String.format(": %s", sortTypeAux)).concat("}")).limit(getPerPage()).as(Auditoria.class);
                }
            } else {
                if (reportMode) {
                    auditorias = Auditoria.auditorias().find().as(Auditoria.class);
                } else {
                    auditorias = Auditoria.auditorias().find().skip(getSkip()).limit(getPerPage()).as(Auditoria.class);
                }
            }
            total = Auditoria.auditorias().find().as(Auditoria.class).count();
        }

        return new InfluuntQueryResult(Auditoria.toList(auditorias), total, klass);
    }

    public InfluuntQueryBuilder reportMode() {
        this.reportMode = true;
        return this;
    }

    @NotNull
    private BiConsumer<String, Object> buildSearchStatement(ArrayList<SearchFieldDefinition> searchFieldDefinitions) {
        return (key, value) -> {
            String[] keyExpression = key.split("_");
            if (!key.contains(SearchFieldDefinition.START) && !key.contains(SearchFieldDefinition.END)) {
                if (keyExpression.length > 1) {
                    searchFieldDefinitions.add(new SearchFieldDefinition(underscore(keyExpression[0]), keyExpression[1], value));
                } else {
                    searchFieldDefinitions.add(new SearchFieldDefinition(underscore(key), null, value));
                }
            }
        };
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
            case SearchFieldDefinition.IN:
                String valueStr = (String) value;
                String[] values = valueStr.substring(1, valueStr.length() - 1).split(",");
                expr = Expr.in(key, values);
                break;
            case SearchFieldDefinition.EQ:
                expr = Expr.eq(key, value);
                break;
            default:
                expr = Expr.ieq(key, value.toString());
                break;
        }
        return expr;
    }


    private String getMongoFieldOperator(String operator, String key, Object value) {
        String expr;
        switch (operator) {
            case SearchFieldDefinition.LT:
                expr = String.format("%s: {$lt: %s}", key, value);
                break;
            case SearchFieldDefinition.LTE:
                expr = String.format("%s: {$lte: %s}", key, value);
                break;
            case SearchFieldDefinition.GT:
                expr = String.format("%s: {$gt: %s}", key, value);
                break;
            case SearchFieldDefinition.GTE:
                expr = String.format("%s: {$gte: %s}", key, value);
                break;
            default:
                expr = String.format("%s:  %s", key, value);
                break;
        }
        return expr;
    }

    private int getSkip() {
        return getPage() * getPerPage();
    }
}
