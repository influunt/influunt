package checks;

import models.Anel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Created by lesiopinheiro on 7/12/16.
 */
public class ConformidadeNumeroDetectoresEstagiosValidator implements ConstraintValidator<ConformidadeNumeroDetectoresEstagios, Anel> {

    @Override
    public void initialize(ConformidadeNumeroDetectoresEstagios constraintAnnotation) {

    }

    @Override
    public boolean isValid(Anel anel, ConstraintValidatorContext context) {
        if (Objects.nonNull(anel.getDetectores()) && Objects.nonNull(anel.getEstagios()) && anel.isAtivo()) {
            return anel.getDetectores().size() <= anel.getEstagios().size();
        }
        return true;
    }
}
