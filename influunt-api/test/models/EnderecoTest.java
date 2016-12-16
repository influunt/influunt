package models;

import config.WithInfluuntApplicationNoAuthentication;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by juarezlustosa on 07/12/16.
 */
public class EnderecoTest extends WithInfluuntApplicationNoAuthentication {
    public Endereco getEndereco() {
        Endereco endereco = new Endereco();
        endereco.setLocalizacao("Av Paulista");
        endereco.setLocalizacao2("Paulista");
        endereco.setLatitude(1.0);
        endereco.setLongitude(2.0);
        return endereco;
    }

    @Test
    public void validatePresenceOFLocalizacao2() {
        Endereco endereco = getEndereco();

        endereco.setLocalizacao2("");
        endereco.setAlturaNumerica(null);
        endereco.save();
        assertFalse(endereco.isLocalizacao2());

        endereco.setLocalizacao2(null);
        endereco.setAlturaNumerica(null);
        endereco.save();
        assertFalse(endereco.isLocalizacao2());

        endereco.setLocalizacao2("Av Bela Cintra");
        endereco.setAlturaNumerica(null);
        endereco.save();
        assertTrue(endereco.isLocalizacao2());

        endereco.setLocalizacao2("");
        endereco.setAlturaNumerica(2000);
        endereco.save();
        assertTrue(endereco.isLocalizacao2());

    }
}
