package be.btorm.tf_java_2023_demojwt.controllers.security;

import be.btorm.tf_java_2023_demojwt.models.dtos.UserTokenDTO;
import be.btorm.tf_java_2023_demojwt.models.entities.security.User;
import be.btorm.tf_java_2023_demojwt.models.form.UserLoginForm;
import be.btorm.tf_java_2023_demojwt.models.form.UserRegisterForm;
import be.btorm.tf_java_2023_demojwt.services.UserService;
import be.btorm.tf_java_2023_demojwt.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;

@RestController
@RequestMapping()
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<UserTokenDTO> register(
            @RequestBody UserRegisterForm form
            ){
        User user = userService.register(form.toEntity());
        String token = jwtUtils.generateToken(user);
        UserTokenDTO dto = UserTokenDTO.fromEntity(user);
        dto.setToken(token);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDTO> login(
            @RequestBody UserLoginForm form
    ){
        User user = userService.login(form.toEntity());
        String token = jwtUtils.generateToken(user);
        UserTokenDTO dto = UserTokenDTO.fromEntity(user);
        dto.setToken(token);
        return ResponseEntity.ok(dto);
    }
}
