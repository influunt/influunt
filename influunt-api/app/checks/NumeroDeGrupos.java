package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NumeroDeGruposValidator.class})
@Documented
public @interface NumeroDeGrupos {

    String message() default "{bar}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

