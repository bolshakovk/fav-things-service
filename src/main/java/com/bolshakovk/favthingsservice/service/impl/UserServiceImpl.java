package com.bolshakovk.favthingsservice.service.impl;

import com.bolshakovk.favthingsservice.dto.UserDto;
import com.bolshakovk.favthingsservice.entity.User;
import com.bolshakovk.favthingsservice.repository.UserRepository;
import com.bolshakovk.favthingsservice.utils.Role;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserServiceImpl  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    public UserDto convertToDto(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
    public User registerNewUserAccount(UserDto userDto)  {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setSecondName(userDto.getSecondName());
        user.setThirdName(userDto.getThirdName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        //user.setBirth(userDto.getDate());
        System.out.println("in method " +  user);
        return userRepository.save(user);
    }

    public  User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
