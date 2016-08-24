package checks;

import models.Anel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ConformidadeNumeroEstagiosValidator implements ConstraintValidator<ConformidadeNumeroEstagios, Anel> {
    @Override
    public void initialize(ConformidadeNumeroEstagios constraintAnnotation) {

    }

    @Override
    public boolean isValid(Anel anel, ConstraintValidatorContext context) {
        if (anel.getEstagios() != null && anel.isAtivo()) {
            return anel.getEstagios().size() >= 2 && anel.getEstagios().size() <= anel.getControlador().getModelo().getLimiteEstagio();
        } else {
            return true;
        }
    }
}
