package com.laboration.service;

import com.laboration.DTO.UserWrapper;
import com.laboration.controller.basicAuth.BCryptEncoderTest;
import com.laboration.models.PersonalMessage;
import com.laboration.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends Services {

    private ModelMapper mapper = new ModelMapper();
    private BCryptEncoderTest encrypt = new BCryptEncoderTest();

    public List<String> getAllUsers() {
        List<User> listofUsers = userRepo.findAll();
        List<String> listofNames = new ArrayList<String>();
        for (int i = 0; i < listofUsers.size(); i++) {
            listofNames.add(listofUsers.get(i).getUsername());
        }
        return listofNames;
    }

    public UserWrapper findByUsername(String username) {
        return mapper.map(userRepo.findByUsername(username), UserWrapper.class);
    }

    // TODO: Boolean för confirmation message?
    @Transactional
    public UserWrapper signUpRequest(UserWrapper user) {
        if(user != null){
            user.setPassword(encrypt.encryptPassword(user.getPassword()));
            userRepo.save(mapper.map(user, User.class));
        }
        return user;
    }

    public List<String> getSenders(String receivername){
        List<PersonalMessage> messages = pmRepo.findDistinctSenderByReceiver(userRepo.findByUsername(receivername));
        List<String> listOfSenders = new ArrayList<String>();
        for(PersonalMessage entry : messages){
            if(!listOfSenders.contains(entry.getSender().getUsername())){
                listOfSenders.add(entry.getSender().getUsername());
            }
        }
        return listOfSenders;
    }
}
