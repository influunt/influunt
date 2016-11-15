package checks;

import models.Controlador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class AoMenosUmAnelAtivoValidator implements ConstraintValidator<AoMenosUmAnelAtivo, Controlador> {
    @Override
    public void initialize(AoMenosUmAnelAtivo constraintAnnotation) {

    }

    @Override
    public boolean isValid(Controlador controlador, ConstraintValidatorContext context) {

        if (controlador.getAneis() == null) {
            return false;
        } else {
            return controlador.getAneis()
                .stream()
                .filter(anel -> anel.isAtivo()).count() >= 1;
        }
    }
}
