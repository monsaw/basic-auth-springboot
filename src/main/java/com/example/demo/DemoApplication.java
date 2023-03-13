package com.example.demo;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication  {


//    @Bean
//    public CommandLineRunner run (UsersRepository usersRepository){
//        return args -> {
////            Users users = new Users(1l, "lawal", "12345","lawalmonsaw@gmail.com");
//            Users users = new Users()
//           usersRepository.save(users);
//
//
//        };
//    }
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);


    }


}
