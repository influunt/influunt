package checks;

import models.Anel;

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
            return (anel.getGruposSemaforicos().size()) > 1;
        }else {
            return true;
        }
    }
}
