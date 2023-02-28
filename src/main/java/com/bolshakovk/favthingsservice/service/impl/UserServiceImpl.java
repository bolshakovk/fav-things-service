package com.bolshakovk.favthingsservice.service.impl;

import com.bolshakovk.favthingsservice.dto.UserDto;
import com.bolshakovk.favthingsservice.dto.UserForModel;
import com.bolshakovk.favthingsservice.entity.User;
import com.bolshakovk.favthingsservice.repository.UserRepository;
import com.bolshakovk.favthingsservice.service.MailSender;
import com.bolshakovk.favthingsservice.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl  implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private MailSender mailSender;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        String message = ("Привет,"+  user.getUsername() +" \n Перейди по ссылке для подтверждения регистрации:" +
                " http://localhost:8090/activate/" + user.getActivationCode()
        +"\n ваш логин: " + user.getUsername());
        mailSender.send(user.getEmail(), "Activation code", message);
        userRepository.save(user);
        return true;
    }
    public void initModel(Map<String, Object> model){
        List<User> list = findAll();
        List<UserForModel> userForModels = new ArrayList<>();
        for (int i = 0; i < list.size(); i ++){
            UserForModel user = new UserForModel(
                    list.get(i).getFirstName(),
                    list.get(i).getSecondName(),
                    list.get(i).getThirdName(),
                    list.get(i).getBirth(),
                    list.get(i).getEmail(),
                    list.get(i).getRoles().toString());
            userForModels.add(user);
        }
        model.put("listForModel", userForModels);
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
        user.setActive(true);
        userRepository.save(user);
        return true;
    }
}
