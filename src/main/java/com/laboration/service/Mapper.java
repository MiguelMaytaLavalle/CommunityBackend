package com.laboration.service;

import com.laboration.DTO.PMWrapper;
import com.laboration.DTO.PostWrapper;
import com.laboration.models.PersonalMessage;
import com.laboration.models.Post;
import com.laboration.models.User;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component //För att skapa en böna i spring boot tror jag.
public class Mapper extends Services{

    ModelMapper mapper;

    Mapper() {
        mapper = new ModelMapper();
        //---------------------------------------------------------------------------------
        //User conversion
        Converter<String, User> convertStringToReceiver = new Converter<String, User>(){
            public User convert(MappingContext<String, User> context){
                return userRepo.findByUsername(context.getSource());
            }
        };

        Converter<String, User> convertStringToSender = new Converter<String, User>(){
            public User convert(MappingContext<String, User> context){
                return userRepo.findByUsername(context.getSource());
            }

        };

        Converter<User, String> convertReceiverToString = new Converter<User, String>(){
            public String convert(MappingContext<User, String> context){
                return context.getSource().getUsername();
            }
        };

        Converter<User, String> convertSenderToString = new Converter<User, String>(){
            public String convert(MappingContext<User, String> context){
                return context.getSource().getUsername();
            }

        };

        PropertyMap<PMWrapper, PersonalMessage> pmMap = new PropertyMap<PMWrapper, PersonalMessage>() {
            @Override
            protected void configure() {
                using(convertStringToReceiver).map(source.getReceiver()).setReceiver(null);
                using(convertStringToSender).map(source.getSender()).setSender(null);
            }
        };

        PropertyMap<PersonalMessage, PMWrapper> pmWrapMap = new PropertyMap<PersonalMessage, PMWrapper>() {
            @Override
            protected void configure() {
                using(convertReceiverToString).map(source.getReceiver()).setReceiver(null);
                using(convertSenderToString).map(source.getSender()).setSender(null);
            }
        };
        //---------------------------------------------------------------------------------
        //Post Conversion
        Converter<String, User> convertStringToUser = new Converter<String, User>(){
            public User convert(MappingContext<String, User> context){
                return userRepo.findByUsername(context.getSource());
            }

        };
        PropertyMap<PostWrapper, Post> postMap = new PropertyMap<PostWrapper, Post>() {
            @Override
            protected void configure() {
                using(convertStringToUser).map(source.getUsername()).setUser(null);
            }
        };
        Converter<User, String> convertUserToString = new Converter<User, String>(){
            public String convert(MappingContext<User, String> context){
                return context.getSource().getUsername();
            }

        };
        PropertyMap<Post, PostWrapper> postWrapMap = new PropertyMap<Post, PostWrapper>() {
            @Override
            protected void configure() {
                using(convertUserToString).map(source.getUser()).setUsername(null);
            }
        };
        mapper.addMappings(pmMap);
        mapper.addMappings(pmWrapMap);
        mapper.addMappings(postMap);
        mapper.addMappings(postWrapMap);
    }
}
