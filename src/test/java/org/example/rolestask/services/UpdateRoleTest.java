package org.example.rolestask.services;
import org.example.rolestask.entity.Role;
import org.example.rolestask.web.model.request.RoleRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration
public class UpdateRoleTest {
    @Autowired
    private DataTestValues testValues;

    @Autowired
    private RoleComponent component;
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
    void updateUserById(){

        RoleRequest requestUpdate = new RoleRequest();
        requestUpdate.setName(testValues.testUpdateName);
        requestUpdate.setDescription(testValues.testUpdateDescription);

        component.updateRoleById(id,requestUpdate);
        Role roleFromTable = component.findByIdOrDie(id);

        assertNotNull(roleFromTable);
        assertEquals(testValues.testUpdateName, roleFromTable.getName());
        assertEquals(testValues.testUpdateDescription, roleFromTable.getDescription());
    }

    @AfterEach
    void deleteUserById(){
        try {
            component.deleteRoleById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

