package org.example.rolestask.services;

import org.example.rolestask.entity.Role;
import org.example.rolestask.web.model.request.RoleRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import java.util.OptionalLong;


@SpringBootTest
@ContextConfiguration
@Transactional
class RoleComponentImplTest {
    @Autowired
    private RoleComponent component;
    @Autowired
    private DataTestValues testValues;
    static long id;

    @BeforeEach
    void setUp(){
        RoleRequest request = new RoleRequest();
        request.setName(testValues.testName);
        request.setDescription(testValues.testDescription);

        try {
            Role role = component.commit(request);
            id = role.getId();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void notNull(){
        assertNotNull(testValues);
    }
    @Test
    void findByIdOrDie() {
        Role roleFromTable = component.findByIdOrDie(id);
        assertNotNull(roleFromTable);
        assertEquals(testValues.testName, roleFromTable.getName());
        assertEquals(testValues.testDescription, roleFromTable.getDescription());
    }

    @Test
    void findByIdOrDieThrowException() {
        OptionalLong optLong = component.findAll().stream().mapToLong(u->u.getId()).max();
        if(optLong.isPresent()) {
            long id = optLong.getAsLong();
            Exception exception = assertThrows(Exception.class, () ->
                    component.findByIdOrDie(id + 1));

            assertEquals("role with this id not found !", exception.getMessage());
        }
    }

    @Test
    void findAll() {
        assertEquals(1, component.findAll().size());
    }

    @Test
    void deleteRoleById() {
        assertEquals(1, component.findAll().size());
        try{
            component.deleteRoleById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals(0, component.findAll().size());
    }
}