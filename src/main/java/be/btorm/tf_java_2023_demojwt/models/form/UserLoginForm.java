package be.btorm.tf_java_2023_demojwt.models.form;

import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"email","password"})
@ToString(of = {"email","password"})
public class UserLoginForm {

    private String email;

    private String password;

    public User toEntity(){
        User user = new User();
        user.setEmail(this.email);
        user.setPassword((this.password));
        return user;
    }
}
