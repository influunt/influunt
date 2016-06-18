package checks;

import models.Anel;
import models.Controlador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class AoMenosUmGrupoSemaforicoValidator implements ConstraintValidator<AoMenosUmGrupoSemaforico, Anel> {
    @Override
    public void initialize(AoMenosUmGrupoSemaforico constraintAnnotation) {

    }

    @Override
    public boolean isValid(Anel anel, ConstraintValidatorContext context) {
        if(anel.isAtivo()) {
            return (anel.getQuantidadeGrupoVeicular() + anel.getQuantidadeGrupoPedestre()) > 1;
        }else {
            return true;
        }
    }
}
