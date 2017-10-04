package reports;

public class FalhaPorFabricanteVO {
    private String falha;

    private Integer total;

    public FalhaPorFabricanteVO() {
        super();
    }

    public FalhaPorFabricanteVO(String falha, int total) {
        this.falha = falha;
        this.total = total;
    }

    public String getFalha() {
        return falha;
    }

    public void setFalha(String falha) {
        this.falha = falha;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
