package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valida se o número de grupos semafóricos é igual ao definido pelo modelo do controlador
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AoMenosUmGrupoSemaforicoValidator.class})
@Documented
public @interface AoMenosUmGrupoSemaforico {

    String message() default "Esse anel deve ter no mínimo 2 grupos semáforicos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

