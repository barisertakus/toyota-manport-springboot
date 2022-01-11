package com.barisertakus.toyotamanport;

import com.barisertakus.toyotamanport.dto.SignupRequest;
import com.barisertakus.toyotamanport.entity.Role;
import com.barisertakus.toyotamanport.entity.User;
import com.barisertakus.toyotamanport.repository.RoleRepository;
import com.barisertakus.toyotamanport.repository.UserRepository;
import com.barisertakus.toyotamanport.service.AuthService;
import com.barisertakus.toyotamanport.service.RoleService;
import com.barisertakus.toyotamanport.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class ToyotaManportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToyotaManportApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner (UserService userService, RoleService roleService, PasswordEncoder passwordEncoder){
        return args -> {

            long roleCount = roleService.count();
            Role roleAdmin = null;
            Role roleUser = null;

            if (roleCount == 0){
                roleAdmin = new Role("ROLE_ADMIN");
                roleUser = new Role("ROLE_USER");
            }

            long userCount = userService.count();
            User admin = null;
            User user = null;

            if(userCount == 0){
                admin = new User("admin", "admin", "admin@admin.com", passwordEncoder.encode("admin"));
                user = new User("user", "user", "user@user.com", passwordEncoder.encode("user"));
            }


            if(roleCount == 0 && userCount == 0){
                roleService.save(roleAdmin);
                roleService.save(roleUser);
                admin.setRoles(Collections.singletonList(roleAdmin));
                user.setRoles(Collections.singletonList(roleUser));
                userService.saveAll(Arrays.asList(admin ,user));
            }
        };
    };

}
