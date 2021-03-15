package com.laboration.service;

import com.laboration.repository.PersonalMessageRepository;
import com.laboration.repository.PostRepository;
import com.laboration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

abstract class Services {
    @Autowired
    protected PostRepository postRepo;
    @Autowired
    protected UserRepository userRepo;
    @Autowired
    protected PersonalMessageRepository pmRepo;
    @Autowired
    protected Mapper object;
}
