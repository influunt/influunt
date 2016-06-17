package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Verifica se o número de aneis é igual ao número definido na configuração do modelo do controlador
 * Created by rodrigosol on 6/16/16.
 *
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NumeroDeAneisValidator.class})
@Documented
public @interface NumeroDeAneisIgualAoModelo {

    String message() default "{foo}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
