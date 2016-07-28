package checks;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Valida se o número de {@link models.Detector} é igual ao somatório das quantidades de detectores definido pelo {@link models.ModeloControlador}
 * Created by lesiopinheiro on 7/11/16.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConformidadeNumeroDetectoresValidator.class})
@Documented
public @interface ConformidadeNumeroDetectores {

    String message() default "Um anel ativo não deve ultrapassar o somatório das quantidades de detectores definidas no modelo do controlador";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
