package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valida se o número de grupos semafóricos é igual ao definido pelo modelo do controlador
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConformidadeDeNumeroDeDetectoresDePedestreValidator.class})
@Documented
public @interface ConformidadeDeNumeroDeDetectoresDePedestre {

    String message() default "Numero total de detectores de pedestre informado individualmente nos aneis excede o limite do controlador";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

