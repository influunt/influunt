package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by rodrigosol on 6/16/16.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NumeroDeAneisValidator.class})
@Documented
public @interface NumeroDeAneis {

    String message() default "{foo}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
