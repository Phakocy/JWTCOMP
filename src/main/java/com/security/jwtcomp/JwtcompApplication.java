package com.security.jwtcomp;

import com.security.jwtcomp.model.User;
import com.security.jwtcomp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootApplication
public class JwtcompApplication {

    @Autowired
   private UserRepository userRepository;

    @PostConstruct
    public void initUsers(){
        List<User> users = Stream.of(
                new User(101, "warith", "omojola001", "omojolawarith@gmail.com"),
                new User(102, "ikechukwu", "obiwanne001", "obiwanneikechuckwu@gmail.com"),
                new User(103, "usman", "kulaha001", "kulahausman@gmail.com"),
                new User(104, "uchenna", "ogbodo001", "ogbodouchenna@gmail.com")
        ).collect(Collectors.toList());
        userRepository.saveAll(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(JwtcompApplication.class, args);
    }

}
