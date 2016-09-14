package helpers;

import models.Area;
import models.Cidade;
import models.Fabricante;
import models.Usuario;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by rodrigosol on 6/15/16.
 */
public class ControladorHelper {

    private List<Cidade> cidades;

    private List<Fabricante> fabricantes;

    public ControladorHelper(Usuario usuario) {
        if (usuario.isRoot()) {
            cidades = Cidade.find.findList();
        } else if (usuario.getArea() != null) {
            cidades = Cidade.find.where().eq("id", usuario.getArea().getCidade().getId()).findList();
            for (Cidade cidade : cidades) {
                ListIterator<Area> it = cidade.getAreas().listIterator();
                while (it.hasNext()) {
                    Area area = it.next();
                    if (!area.getId().equals(usuario.getArea().getId())) {
                        it.remove();
                    }
                }
            }
        }
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
