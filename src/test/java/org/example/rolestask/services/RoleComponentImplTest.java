package org.example.rolestask.services;

import org.example.rolestask.entity.Role;
import org.example.rolestask.web.model.request.RoleRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.io.UnsupportedEncodingException;
import java.util.OptionalLong;


@SpringBootTest
@ContextConfiguration
class RoleComponentImplTest {
    @Autowired
    private RoleComponent component;
    @Autowired
    private DataTestValues testValues;

    @Test
    void notNull(){
        assertNotNull(testValues);
    }
    @Test
    void findByIdOrDie() throws UnsupportedEncodingException {
        Role roleFromTable = component.findByIdOrDie(testValues.idFirst);
        assertNotNull(roleFromTable);
        assertEquals(testValues.nameFirst, roleFromTable.getName());

        try {
        assertEquals(testValues.descriptionFirst, new String(roleFromTable.getDescription().getBytes("Cp1252"), "Cp1251"));
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
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
        assertEquals(2, component.findAll().size());
    }

    @Test
    void deleteRoleById() {
        long id =  getIdCommitedRole();
        assertEquals(3, component.findAll().size());
        try{
            component.deleteRoleById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals(2, component.findAll().size());
    }

    @Test
    void updateUserById(){
        long id =  getIdCommitedRole();

        component.updateRoleById(id,new RoleRequest(testValues.testUpdateName,testValues.testUpdateDescription));
        Role roleFromTable = component.findByIdOrDie(id);

        assertNotNull(roleFromTable);
        assertEquals(testValues.testUpdateName, roleFromTable.getName());
        assertEquals(testValues.testUpdateDescription, roleFromTable.getDescription());
        component.deleteRoleById(id);
    }
    private long getIdCommitedRole(){
        try{
            return component.commit(new RoleRequest(testValues.testName,testValues.testDescription )).getId();
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}