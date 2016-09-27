package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valida o número de elementos em uma lista
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NumeroDeElementosNaListaValidator.class})
@Documented
public @interface NumeroDeElementosNaLista {

    int min() default 1;

    String message() default "deve ter pelo menos 1 elemento.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

