package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valida se o número de grupos semafóricos é igual ao definido pelo modelo do controlador
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConformidadeDeNumeroDeDetectoresVeicularValidator.class})
@Documented
public @interface ConformidadeDeNumeroDeDetectoresVeicular {

    String message() default "Numero total de detectores veiculares informado individualmente nos aneis excede o limite do controlador";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

