package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valida se o número de grupos semafóricos é igual ao definido pelo modelo do controlador
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConformidadeNumeroMovimentosValidator.class})
@Documented
public @interface ConformidadeNumeroMovimentos {

    String message() default "Um anel ativo deve ter ao menos dois movimento e no máximo o limite do modelo do controlador";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

