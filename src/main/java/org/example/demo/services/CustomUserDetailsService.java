package org.example.demo.services;
import org.example.demo.entities.Student;
import org.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public  class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private StudentRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //fetch user from database
        Student user= userRepository.findByUsername(username).
                orElseThrow( ()->new UsernameNotFoundException("user Not Found!"));
        return new User(user.getUsername(),user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));

    }
}