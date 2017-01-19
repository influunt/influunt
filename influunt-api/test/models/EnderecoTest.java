package models;

import checks.Erro;
import checks.InfluuntValidator;
import config.WithInfluuntApplicationNoAuthentication;
import org.junit.Test;

import javax.validation.groups.Default;

import java.util.List;

import static org.junit.Assert.*;

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

        endereco.setAlturaNumerica(null);
        endereco.setLocalizacao2("Av Bela Cintra");
        endereco.save();
        assertTrue(endereco.isAlturaNumericaNegativa());

        endereco.setLocalizacao2("");
        endereco.setAlturaNumerica(2000);
        endereco.save();
        assertTrue(endereco.isLocalizacao2());

    }

    @Test
    public void validateAlturaNumericaPositiva() {
        Endereco endereco = getEndereco();

        endereco.setAlturaNumerica(-1231);
        endereco.save();
        assertFalse(endereco.isAlturaNumericaNegativa());

        endereco.setAlturaNumerica(1231);
        endereco.save();
        assertTrue(endereco.isAlturaNumericaNegativa());
    }

    @Test
    public void validateEndereco() {
        Endereco endereco = getEndereco();
        endereco.setLocalizacao2(null);
        endereco.setAlturaNumerica(1231);

        List<Erro> erros = new InfluuntValidator<Endereco>().validate(endereco, Default.class);

        assertEquals(0, erros.size());

        endereco.setLocalizacao2("Av Bela Cintra");
        endereco.setAlturaNumerica(null);

        erros = new InfluuntValidator<Endereco>().validate(endereco, Default.class);

        assertEquals(0, erros.size());

        endereco.setLocalizacao2(null);
        endereco.setAlturaNumerica(null);

        erros = new InfluuntValidator<Endereco>().validate(endereco, Default.class);

        assertEquals(1, erros.size());


        endereco.setLocalizacao2(null);
        endereco.setAlturaNumerica(-1231);

        erros = new InfluuntValidator<Endereco>().validate(endereco, Default.class);

        assertEquals(1, erros.size());
    }
}
