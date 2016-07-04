package checks;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by rodrigosol on 6/16/16.
 */
public class InfluuntValidator<T> {

    public static Validator validator;



    public InfluuntValidator(){
      if(validator == null){
          validator = Validation.buildDefaultValidatorFactory().getValidator();
      }
    };

    public List<Erro> validate(T model){
        return parse(validator.validate(model));
    }


    public List<Erro> validate(T model, Class<?>... groups ) {
        return parse(validator.validate(model,groups));
    }

    private List<Erro> parse(Set<ConstraintViolation<T>> violations) {
        return violations.stream().map( v ->
            new Erro(v.getRootBeanClass().getSimpleName(),v.getMessage(),v.getPropertyPath().toString())
        ).collect(Collectors.toList());
    }



}
