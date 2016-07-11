package checks;

import models.Controlador;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class NumeroDeControladoresValidator implements ConstraintValidator<NumeroDeControladores, List<Controlador>> {
    @Override
    public void initialize(NumeroDeControladores constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Controlador> controladores, ConstraintValidatorContext context) {
        Integer min = (Integer) ((ConstraintValidatorContextImpl) context).getConstraintDescriptor().getAttributes().get("min");
        return controladores != null && controladores.size() >= min;
    }
}
