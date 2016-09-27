package checks;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class NumeroDeElementosNaListaValidator implements ConstraintValidator<NumeroDeElementosNaLista, List> {

    private int minElementos;

    @Override
    public void initialize(NumeroDeElementosNaLista constraintAnnotation) {
        minElementos = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(List lista, ConstraintValidatorContext context) {
        return lista != null && lista.size() >= minElementos;
    }
}
