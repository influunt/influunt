package helpers;

import models.Cidade;
import models.Fabricante;

import java.util.List;

/**
 * Created by rodrigosol on 6/15/16.
 */
public class ControladorHelper {

    private List<Cidade> cidades;
    private List<Fabricante> fabricantes;

    public ControladorHelper() {
        cidades = Cidade.find.findList();
        fabricantes = Fabricante.find.findList();
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public List<Fabricante> getFabricantes() {
        return fabricantes;
    }

    public void setFabricantes(List<Fabricante> fabricantes) {
        this.fabricantes = fabricantes;
    }
}
