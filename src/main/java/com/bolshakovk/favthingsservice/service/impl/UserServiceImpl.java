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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl  implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setActive(true);
        if (userDto.getRole().equalsIgnoreCase("user")){
            user.setRoles(Collections.singleton(Role.USER));
        }else  if (userDto.getRole().equalsIgnoreCase("admin")){
            user.setRoles(Collections.singleton(Role.ADMIN));
        }
        System.out.println("authorities: " + user.getAuthorities());

        //user.setBirth(userDto.getDate());
        //User u = userRepository.save(user);
        //System.out.println(u);
        System.out.println("in method " +  user);
        return userRepository.save(user);
    }
    public List<User> findAll(){
        return userRepository.findAll();
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
