package checks;

import models.Anel;
import models.Controlador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class ConformidadeNumeroMovimentosValidator implements ConstraintValidator<ConformidadeNumeroMovimentos, Anel> {
    @Override
    public void initialize(ConformidadeNumeroMovimentos constraintAnnotation) {

    }

    @Override
    public boolean isValid(Anel anel, ConstraintValidatorContext context) {
        if(anel.getMovimentos()!= null && anel.isAtivo()){
            return anel.getMovimentos().size() >= 2 && anel.getMovimentos().size() < anel.getControlador().getModelo().getConfiguracao().getLimiteEstagio();
        }else{
            return true;
        }
    }
}
