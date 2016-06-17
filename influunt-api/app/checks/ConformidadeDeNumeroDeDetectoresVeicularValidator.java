package checks;

import models.Controlador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ConformidadeDeNumeroDeDetectoresVeicularValidator implements ConstraintValidator<ConformidadeDeNumeroDeDetectoresVeicular, Controlador> {
    @Override
    public void initialize(ConformidadeDeNumeroDeDetectoresVeicular constraintAnnotation) {

    }

    @Override
    public boolean isValid(Controlador controlador, ConstraintValidatorContext context) {

        if (controlador.getModelo() == null || controlador.getAneis() == null) {
            return false;
        }
        Integer total = controlador.getAneis()
                .stream()
                .filter(anel -> anel.isAtivo())
                .mapToInt(anel -> anel.getQuantidadeDetectorVeicular())
                .sum();

        return total <= controlador.getModelo().getConfiguracao().getLimiteDetectorVeicular();
    }
}
