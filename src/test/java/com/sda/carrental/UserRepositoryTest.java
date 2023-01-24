package com.sda.carrental;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.sda.carrental.model.users.User;


class UserRepositoryTest extends BaseTest {
    @Test
    void shouldCreateUser() {
        // given
        User user = new User("jkowalki@gmail.com", "Kowalski", User.Roles.ROLE_CUSTOMER, "Józef", "Brzeziński");

        // when
        User saved = userRepository.save(user);

        // then
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Józef", saved.getName());
        assertEquals(User.Roles.ROLE_CUSTOMER, saved.getRole());
    }

    @Test
    void shouldFindAllUsersByLastName() {
        // given
        // when
        List<User> users = userRepository.findAllByLastName("Kowalski");

        // then
        assertNotNull(users);
        assertEquals(0, users.size());
    }

}
