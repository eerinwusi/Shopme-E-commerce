package com.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.admin.user.RoleRepository;
import com.shopme.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.util.List;

//@SpringBootTest(classes = Role.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole() {
        Role roleAdmin = new Role("Admin", "Manage everything");
        Role savedRole = roleRepository.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles() {
        Role salesPerson = new Role("Salesperson", "Manage product price, customers, shipping, " +
                "orders and sales report.");

        Role editor = new Role("Editor", "Manage categories, brands, products, articles " +
                "and menus");

        Role shipper = new Role("Shipper", "View products, view orders and update order status");

        Role assistant = new Role("Assistant", "Manage questions and reviews");

        roleRepository.saveAll(List.of(salesPerson, editor, shipper, assistant));
    }
}
