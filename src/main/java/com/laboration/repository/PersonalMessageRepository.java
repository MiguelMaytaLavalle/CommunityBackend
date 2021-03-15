package com.laboration.repository;

import com.laboration.models.PersonalMessage;
import com.laboration.models.Post;
import com.laboration.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalMessageRepository extends JpaRepository<PersonalMessage, Long> {
    PersonalMessage findBySender(User sender);
    List<PersonalMessage> findAllBySenderAndReceiver(User sender, User receiver);
    //Få alla meddelande till en person där sändarna är unika?????
    List<PersonalMessage> findDistinctSenderByReceiver(User receiver);
}