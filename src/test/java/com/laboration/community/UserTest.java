package com.laboration.community;

import com.laboration.DTO.PMWrapper;
import com.laboration.DTO.UserWrapper;
import com.laboration.models.User;
import com.laboration.repository.UserRepository;
import com.laboration.service.PMService;
import com.laboration.service.UserService;
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
public class UserTest {

    @Autowired
    private TestEntityManager entityManager;

    public PMWrapper pm;
    public PMWrapper pm2;
    public UserWrapper testSave;
    public User findByUsername;
    public User testUserId;

    @Autowired
    private UserService userSer;
    @Autowired
    private PMService pmSer;

    @Autowired
    private UserRepository userRep;
    @Before
    public void setUp(){
        testSave = new UserWrapper();
        testSave.setEmail("Ricml");
        testSave.setPassword("Accompany");
        testSave.setUsername("Ricml");

        findByUsername = new User();
        findByUsername.setEmail("Abe");
        findByUsername.setPassword("Onnn");
        findByUsername.setUsername("Abe");

        testUserId = new User();
        testUserId.setEmail("Jdadddj");
        testUserId.setPassword("Owdi");
        testUserId.setUsername("Pahpa");

        userRep.save(testUserId);
        userRep.save(findByUsername);

        pm = new PMWrapper();
        pm2 = new PMWrapper();

        pm.setContent("First Message");
        pm.setReceiver("Ricml");
        pm.setSender("Abe");

        pm2.setContent("Second Message");
        pm2.setReceiver("Ricml");
        pm2.setSender("Abe");

        pmSer.createPM(pm);
        pmSer.createPM(pm2);
    }

    @Test
    public void testUserId(){
        Long id = entityManager.persistAndGetId(testUserId, Long.class);
        User expResult = userRep.findByUsername("Pahpa");
        assertNotNull(expResult);
        assertEquals(expResult.getId(), id);
    }

    @Test
    public void findByUserName(){
        Long id = entityManager.persistAndGetId(findByUsername, Long.class);
        UserWrapper testResult = userSer.findByUsername("Abe");
        User expResult = userRep.findByUsername(testResult.getUsername());
        assertEquals(expResult.getId(), id);
    }

    @Test
    public void testSaveUser(){
        userSer.signUpRequest(testSave);
        User expResult = userRep.findByUsername("Ricml");
        System.out.println("UserId Expected" + " " + expResult.getId().toString());
        assertEquals(expResult.getUsername(), testSave.getUsername());
    }

    @Test
    public void testGetAllSenders(){
        List<String> list = userSer.getSenders("Ricml");
        assertFalse(list.isEmpty());
        list = userSer.getSenders("Abe");
        assertTrue(list.isEmpty());
    }


}
