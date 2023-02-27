package com.bolshakovk.favthingsservice.service.impl;

import antlr.StringUtils;
import com.bolshakovk.favthingsservice.dto.UserDto;
import com.bolshakovk.favthingsservice.entity.User;
import com.bolshakovk.favthingsservice.repository.UserRepository;
import com.bolshakovk.favthingsservice.service.MailSender;
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
import java.util.UUID;

@Service
public class UserServiceImpl  implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    @Autowired
    private MailSender mailSender;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDto convertToDto(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
    public boolean registerNewUserAccount(UserDto userDto)  {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setThirdName(userDto.getThirdName());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setBirth(userDto.getBirth());

        if (userDto.getRole().equalsIgnoreCase("user")){
            user.setRoles(Collections.singleton(Role.USER));
        }else  if (userDto.getRole().equalsIgnoreCase("admin")){
            user.setRoles(Collections.singleton(Role.ADMIN));
        }
        if (emailExists(user.getEmail())){
            return false;
        }
        user.setActivationCode(UUID.randomUUID().toString());
        user.setActive(true);
        String message = ("Привет,"+  user.getUsername() +" \n Перейди по ссылке для подтверждения регистрации:" +
                " http://localhost:8080/activate/" + user.getActivationCode()
        +"\n ваш логин: " + user.getUsername());
        mailSender.send(user.getEmail(), "Activation code", message);
        userRepository.save(user);
        return true;
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        System.out.println(user);
        if(user == null){
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }
}
