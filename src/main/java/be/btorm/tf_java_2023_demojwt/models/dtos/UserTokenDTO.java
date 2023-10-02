package be.btorm.tf_java_2023_demojwt.models.dtos;

import be.btorm.tf_java_2023_demojwt.models.entities.security.RoleType;
import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id","email","role"})
@ToString(of = {"id","email","role"})
public class UserTokenDTO {

    private Long id;

    private String email;

    private RoleType role;

    private String token;

    public static UserTokenDTO fromEntity(User user){
        UserTokenDTO dto = new UserTokenDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}
