package test.models;

import checks.Erro;
import checks.InfluuntValidator;
import models.Perfil;
import models.Usuario;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by pedropires on 12/21/16.
 */
public class UsuarioTest {

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
        assertEquals(0, erros.size());
    }

}
