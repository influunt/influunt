package checks;

import models.Controlador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ConformidadeDeNumeroDeGruposSemaforicosValidator implements ConstraintValidator<ConformidadeDeNumeroDeGruposSemaforicos, Controlador> {
    @Override
    public void initialize(ConformidadeDeNumeroDeGruposSemaforicos constraintAnnotation) {

    }

    @Override
    public boolean isValid(Controlador controlador, ConstraintValidatorContext context) {

        if (controlador.getModelo() == null || controlador.getAneis() == null) {
            return false;
        }
        Integer total = controlador.getAneis()
                .stream()
                .filter(anel -> anel.isAtivo())
                .mapToInt(anel -> anel.getGruposSemaforicos().size())
                .sum();

        return total <= controlador.getModelo().getConfiguracao().getLimiteGrupoSemaforico();
    }
}
