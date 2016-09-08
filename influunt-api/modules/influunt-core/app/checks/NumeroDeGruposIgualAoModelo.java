package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valida se o número de grupos semafóricos é igual ao definido pelo modelo do controlador
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NumeroDeGruposValidator.class})
@Documented
public @interface NumeroDeGruposIgualAoModelo {

    String message() default "O numero de grupos semáforicos deve ser igual ao numero espeficado no modelo do controlador";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

