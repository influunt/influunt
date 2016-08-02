package checks;

import models.Controlador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ConformidadeDeNumeroDeDetectoresDePedestreValidator implements ConstraintValidator<ConformidadeDeNumeroDeDetectoresDePedestre, Controlador> {
    @Override
    public void initialize(ConformidadeDeNumeroDeDetectoresDePedestre constraintAnnotation) {

    }

    @Override
    public boolean isValid(Controlador controlador, ConstraintValidatorContext context) {

        if (controlador.getModelo() == null || controlador.getAneis() == null) {
            return false;
        }

        Long total = controlador.getAneis()
                .stream()
                .filter(anel -> anel.isAtivo())
                .mapToLong(anel -> anel.getDetectores().stream().filter(detector -> detector.isPedestre()).count())
                .sum();

        return total <= controlador.getLimiteDetectorPedestre();
    }
}
