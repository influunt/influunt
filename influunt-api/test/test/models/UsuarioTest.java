package test.models;

import checks.Erro;
import checks.InfluuntValidator;
import models.Area;
import models.Cidade;
import models.Perfil;
import models.Usuario;
import org.junit.Test;
import test.config.WithInfluuntApplicationNoAuthentication;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by pedropires on 12/21/16.
 */
public class UsuarioTest extends WithInfluuntApplicationNoAuthentication {

    @Test
    public void testValidation() {
        Usuario usuario = new Usuario();
        List<Erro> erros = new InfluuntValidator<Usuario>().validate(usuario);
        assertEquals(4, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro("Usuario", "não pode ficar em branco", "login"),
            new Erro("Usuario", "não pode ficar em branco", "nome"),
            new Erro("Usuario", "não pode ficar em branco", "email"),
            new Erro("Usuario", "não pode ficar em branco", "perfilObrigatorioSeNaoForRoot")
        ));

        usuario.setNome("Teste");
        usuario.setEmail("teste@example.com");
        usuario.setLogin("teste");
        usuario.setRoot(true);

        erros = new InfluuntValidator<Usuario>().validate(usuario);
        assertEquals(0, erros.size());

        Perfil perfil = new Perfil();
        perfil.setNome("Perfil de Teste");

        usuario.setRoot(false);
        usuario.setPerfil(perfil);

        erros = new InfluuntValidator<Usuario>().validate(usuario);
        assertEquals(1, erros.size());
        assertThat(erros, org.hamcrest.Matchers.hasItems(
            new Erro("Usuario", "não pode ficar em branco", "areaObrigatorioSeNaoForRoot")
        ));

        Cidade sp = new Cidade();
        sp.setNome("São Paulo");
        sp.save();

        Area areaSP = new Area();
        areaSP.setDescricao(1);
        areaSP.setCidade(sp);
        areaSP.save();

        usuario.setArea(areaSP);

        erros = new InfluuntValidator<Usuario>().validate(usuario);
        assertEquals(0, erros.size());
    }

}
