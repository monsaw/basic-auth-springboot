package com.example.demo.config;

import com.example.demo.repository.UsersRepository;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LibraryUserDetailsService implements UserDetailsService {

    @Autowired
   public UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username).
                map(LibraryUserDetails::new).
                orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
}
