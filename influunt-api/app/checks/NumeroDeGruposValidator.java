package checks;

import models.Controlador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class NumeroDeGruposValidator implements ConstraintValidator<NumeroDeGruposIgualAoModelo, Controlador> {
    @Override
    public void initialize(NumeroDeGruposIgualAoModelo constraintAnnotation) {

    }

    @Override
    public boolean isValid(Controlador controlador, ConstraintValidatorContext context) {
        if (controlador.getModelo() == null) {
            return true;
        } else {
            return (controlador.getGruposSemaforicos() != null && controlador.getGruposSemaforicos().size() == controlador.getModelo().getLimiteGrupoSemaforico());
        }

    }
}
