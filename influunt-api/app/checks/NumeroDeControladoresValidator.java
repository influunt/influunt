package checks;

import models.Controlador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class NumeroDeControladoresValidator implements ConstraintValidator<NumeroDeControladores, List<Controlador>> {

    private int minControladores;

    @Override
    public void initialize(NumeroDeControladores constraintAnnotation) {
        minControladores = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(List<Controlador> controladores, ConstraintValidatorContext context) {
        return controladores != null && controladores.size() >= minControladores;
    }
}
