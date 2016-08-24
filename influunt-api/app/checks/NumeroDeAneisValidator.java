package checks;

import models.Controlador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class NumeroDeAneisValidator implements ConstraintValidator<NumeroDeAneisIgualAoModelo, Controlador> {
    @Override
    public void initialize(NumeroDeAneisIgualAoModelo constraintAnnotation) {

    }

    @Override
    public boolean isValid(Controlador controlador, ConstraintValidatorContext context) {
        if (controlador.getModelo() == null) {
            return true;
        } else {
            return (controlador.getAneis() != null && controlador.getAneis().size() == controlador.getModelo().getLimiteAnel());
        }

    }
}
