package reports;

/**
 * Classe utilizada para exibir os dados no relatório
 * <p>
 * Created by lesiopinheiro on 10/10/16.
 */
public class ControladorStatusVO {

    private String idFabricante;

    private String nomeFabricante;

    private String status;

    private Integer total;

    public ControladorStatusVO() {
        super();
    }

    public ControladorStatusVO(String nomeFabricante, String status, int total) {
        this.nomeFabricante = nomeFabricante;
        this.status = status;
        this.total = total;
    }

    public ControladorStatusVO(String status, int total) {
        this.status = status;
        this.total = total;
    }

    public String getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(String idFabricante) {
        this.idFabricante = idFabricante;
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

    @Override
    public String toString() {
        return "ControladorStatusVO{" +
            "idFabricante='" + idFabricante + '\'' +
            ", nomeFabricante='" + nomeFabricante + '\'' +
            ", status='" + status + '\'' +
            ", total=" + total +
            '}';
    }
}
