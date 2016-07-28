package checks;

import models.Anel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by lesiopinheiro on 7/11/16.
 */
public class ConformidadeNumeroDetectoresValidator implements ConstraintValidator<ConformidadeNumeroDetectores, Anel> {

    @Override
    public void initialize(ConformidadeNumeroDetectores constraintAnnotation) {

    }

    @Override
    public boolean isValid(Anel anel, ConstraintValidatorContext context) {
        if (anel.isAtivo() && !anel.getDetectores().isEmpty()) {
            return anel.getDetectores().size() <= (anel.getDetectores().size());
        }
        return true;
    }

}
