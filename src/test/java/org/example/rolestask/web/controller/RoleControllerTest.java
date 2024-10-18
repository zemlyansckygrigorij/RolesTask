package org.example.rolestask.web.controller;

import org.example.rolestask.entity.Role;
import org.example.rolestask.services.DataTestValues;
import org.example.rolestask.services.RoleComponent;
import org.example.rolestask.web.model.request.RoleRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.util.OptionalLong;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RoleControllerTest {
    @Autowired
    private RoleComponent component;
    @Autowired
    private DataTestValues testValues;
    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int port;
    private static long id;
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

    @AfterEach
    void deleteTestRoleById(){
        try {
            OptionalLong optLong = component.findAll().stream().mapToLong(u -> u.getId()).max();
            if (optLong.isPresent()) {
                long idMax = optLong.getAsLong();
                component.deleteRoleById(idMax);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void findByIdOrDie() {
        try {
            OptionalLong optLong = component.findAll().stream().mapToLong(u -> u.getId()).max();
            if (optLong.isPresent()) {
                long idMax = optLong.getAsLong();

                this.mockMvc.perform(get("http://localhost:" + port + "/roles/" + idMax))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is(testValues.testName)))
                    .andExpect(jsonPath("$.description", is(testValues.testDescription)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void commit() {
        try{
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/roles")
                            .content(DataTestValues.testUser)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
        }catch(Exception e){
            e.printStackTrace();
        }

        OptionalLong optLong = component.findAll().stream().mapToLong(u -> u.getId()).max();
        if (optLong.isPresent()) {
            long idMax = optLong.getAsLong();
            Role roleFromTable = component.findByIdOrDie(idMax);
            LocalDateTime time = LocalDateTime.now();
            assertNotNull(roleFromTable);
            assertEquals(testValues.testName, roleFromTable.getName());
            assertEquals(testValues.testDescription, roleFromTable.getDescription());
        }
    }

    @Test
    void findAll() {
        try{
            mockMvc.perform(get("http://localhost:" + port + "/roles"))
                    .andExpect(jsonPath("$", hasSize(1)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void deleteRoleById() {
        try{
            mockMvc.perform(get("http://localhost:" + port + "/roles"))
                    .andExpect(jsonPath("$", hasSize(1)));
            this.mockMvc.perform(MockMvcRequestBuilders
                            .delete("/roles/"+id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
            mockMvc.perform(get("http://localhost:" + port + "/roles"))
                    .andExpect(jsonPath("$", hasSize(0)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void updateRoleById() {
        try{
            mockMvc.perform(MockMvcRequestBuilders
                    .put("/roles/"+id)
                    .content(testValues.testUserUpdate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        }catch(Exception e){
            e.printStackTrace();
        }
        Role roleFromTable = component.findByIdOrDie(id);
        LocalDateTime time = LocalDateTime.now();
        assertNotNull(roleFromTable);
        assertEquals(testValues.testUpdateName, roleFromTable.getName());
        assertEquals(testValues.testUpdateDescription, roleFromTable.getDescription());
    }
}