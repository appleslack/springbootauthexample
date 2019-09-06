package com.redtailsoft.springbootauthupdated.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 */
@RestController
@RequestMapping( "/users" )
public class UserController {
    private ApplicationUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping( "/list") 
    public String listUsers( ) {
        StringBuilder usersStr = new StringBuilder();

        for (ApplicationUser user : userRepository.findAll()) {
            usersStr.append(user.getUsername() + "\n");
        }

        return usersStr.toString();
    }

    @PostMapping( "/sign-up" )
    public void signUp ( @RequestBody ApplicationUser user ) {
        System.out.println("SIGNING UP!");
        
        user.setPassword( bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    
}