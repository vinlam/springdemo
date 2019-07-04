package validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@Validated
public class UserTask {

    public void registerUser(@NotNull @Valid User user){
        System.out.println("registering user: "+ user);
    }
}