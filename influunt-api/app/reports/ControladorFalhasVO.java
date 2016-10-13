package reports;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lesiopinheiro on 13/10/16.
 */
public class ControladorFalhasVO {

    private String idFabricante;
    private List<FalhaPorFabricanteVO> falhas = new ArrayList<FalhaPorFabricanteVO>();
    private String nomeFabricante;

    public ControladorFalhasVO() {
        super();
    }

    public String getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(String idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getNomeFabricante() {
        return nomeFabricante;
    }

    public void setNomeFabricante(String nomeFabricante) {
        this.nomeFabricante = nomeFabricante;
    }

    public List<FalhaPorFabricanteVO> getFalhas() {
        return falhas;
    }

    public void setFalhas(List<FalhaPorFabricanteVO> falhas) {
        this.falhas = falhas;
    }

    public void addFalha(FalhaPorFabricanteVO falha) {
        getFalhas().add(falha);
    }




}


