package com.project.WorldFirst.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserTest {
	
	private User user;

    @Before
    public void setupScenario() {
    	user = new User();
    	user.setUserId(1);
    	user.setFirstName("Javier");
    	user.setSurname("de la Osa Escalada");
    	user.setEmail("javier.delaosa@gmail.com");
    }
    
    @Test
    public void shouldGetState() {
    	assertNotNull(user);
        assertEquals(user.getUserId(), 1);
        assertEquals(user.getFirstName(), "Javier");
        assertEquals(user.getSurname(), "de la Osa Escalada");
        assertEquals(user.getEmail(), "javier.delaosa@gmail.com");
    }

}
