package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valida o número de controladores em uma lista de controladores
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NumeroDeControladoresValidator.class})
@Documented
public @interface NumeroDeControladores {

    int min() default 1;

    String message() default "deve ter pelo menos 1 controlador.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

