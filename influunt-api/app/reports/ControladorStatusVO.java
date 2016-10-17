package reports;

/**
 *
 * Classe utilizada para exibir os dados no relatório
 *
 * Created by lesiopinheiro on 10/10/16.
 */
public class ControladorStatusVO {

    private String status;
    private Integer total;

    public ControladorStatusVO(String status, int total) {
        this.status = status;
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}