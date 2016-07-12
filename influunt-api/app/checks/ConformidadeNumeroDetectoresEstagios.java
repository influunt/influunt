package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valida se o número de {@link models.Detector} é menor ou igual ao número de {@link models.Estagio}
 * Created by lesiopinheiro on 7/12/16.
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConformidadeNumeroDetectoresEstagiosValidator.class})
@Documented
public @interface ConformidadeNumeroDetectoresEstagios {

    String message() default "A quantidade de detectores não deve ultrapassar a quantidade de estágios definidas no modelo do controlador.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
