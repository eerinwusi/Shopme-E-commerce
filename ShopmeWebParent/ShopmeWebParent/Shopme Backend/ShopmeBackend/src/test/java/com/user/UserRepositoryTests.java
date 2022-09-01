package com.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.ITERABLE;

import com.admin.ShopmeBackendApplication;
import com.admin.user.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = ShopmeBackendApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

//    This assigns a role to a particular user
    @Test
    public void testCreateUserWithOneRole() {
        Role roleAdmin = testEntityManager.find(Role.class, 1);
        User user = new User("g.erinwusi@erinwusi.com", "heythere", "Emmanuel", "Erinwusi");
        user.addRoles(roleAdmin);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

//    This is assigns two roles to a particular user
    @Test
    public void testCreateUserWithTwoRoles() {
        User user = new User("test@gmail.com", "test2020", "test", "tester");
//        This assigns the user to an editor role using the ID of 3 since the editor role is of ID 3 in the db.
        Role roleEditor = new Role(3);
//        This assigns the user to an assistant role using the ID of 5 since the assistant role is of ID 5 in the db.
        Role roleAssistant = new Role(5);

        user.addRoles(roleEditor);
        user.addRoles(roleAssistant);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User userNam = userRepository.findById(1).get();
        System.out.println(userNam);
        assertThat(userNam).isNotNull();
        System.out.println("Hello");
    }

    @Test
    public void testUpdateUserDetails() {
        User user = userRepository.findById(1).get();
        user.setEnabled(true);
        user.setEmail("testjavaprogrammer@gmail.com");

        userRepository.save(user);
    }

    @Test
    public void testUpdateUserRoles() {
        User user = userRepository.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        user.getRoles().remove(roleEditor);
        user.addRoles(roleSalesperson);

        userRepository.save(user);
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 2;
        userRepository.deleteById(userId);
    }
}
