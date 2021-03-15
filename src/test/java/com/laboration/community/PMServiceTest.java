package com.laboration.community;

import com.laboration.DTO.PMWrapper;
import com.laboration.models.PersonalMessage;
import com.laboration.models.User;
import com.laboration.repository.PersonalMessageRepository;
import com.laboration.repository.UserRepository;
import com.laboration.service.PMService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PMServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    public PMWrapper pm;

    @Autowired
    private PMService pmSer;

    @Autowired
    private PersonalMessageRepository pmRepo;
    @Autowired
    private UserRepository userRepo;

    @Before
    public void setUp(){
        User user = new User();
        user.setEmail("Ricml");
        user.setPassword("Accompany");
        user.setUsername("Ricml");
        User user2 = new User();
        user2.setEmail("Abe");
        user2.setPassword("Onn");
        user2.setUsername("Abe");
        userRepo.save(user);
        userRepo.save(user2);
        pm = new PMWrapper();
        pm.setContent("First Message");
        pm.setReceiver("Ricml");
        pm.setSender("Abe");
    }

    @Test
    public void testSavePM(){
        pmSer.createPM(pm);
        PersonalMessage expResult = pmRepo.findBySender(userRepo.findByUsername(pm.getSender()));
        System.out.println(expResult.getDateSent().toString());
        assertEquals(pm.getReceiver(), expResult.getReceiver().getUsername());
        assertNotNull(expResult);
        System.out.println(expResult.getContent());
    }
    @Test
    public void testGetAllFromSender(){
        pmSer.createPM(pm);
        PMWrapper pm2 = new PMWrapper();
        pm2.setContent("Second Message");
        pm2.setReceiver("Ricml");
        pm2.setSender("Abe");
        pmSer.createPM(pm2);
        List<PMWrapper> result = pmSer.getPMsFromSender("Abe", "Ricml");
        assertFalse(result.isEmpty());
        for(int i = 0; i < result.size(); i++){
            System.out.println(result.get(i).getContent());
        }
    }
    @Test
    public void testGetFromMsgId(){
        pmSer.createPM(pm);
        PMWrapper pm2 = new PMWrapper();
        pm2.setContent("Second Message");
        pm2.setReceiver("Ricml");
        pm2.setSender("Abe");
        pmSer.createPM(pm2);
        List<PMWrapper> result = pmSer.getPMsFromSender("Abe", "Ricml");
        PMWrapper msg = pmSer.getSpecificPM(result.get(0).getMessageId().intValue());
        assertNotNull(msg);
        assertEquals(msg.getContent(), pm.getContent());
    }
}
