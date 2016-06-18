package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valida se o número de grupos semafóricos é igual ao definido pelo modelo do controlador
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AoMenosUmAnelAtivoValidator.class})
@Documented
public @interface AoMenosUmAnelAtivo {

    String message() default "Ao menos um anel deve estar ativo";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

