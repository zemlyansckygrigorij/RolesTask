package org.example.rolestask.web.controller;

import org.example.rolestask.entity.Role;
import org.example.rolestask.services.DataTestValues;
import org.example.rolestask.services.RoleComponent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.io.UnsupportedEncodingException;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser("user")
class RoleControllerTest {
    @Autowired
    private RoleComponent component;
    @Autowired
    private DataTestValues testValues;
    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int port;

    @Test
    void findByIdOrDie() {
        long id = getIdCommitedRole();
        try{
            this.mockMvc.perform(get("http://localhost:" + port + "/api/roles/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testValues.testName)))
                .andExpect(jsonPath("$.description", is(testValues.testDescription)));
        }catch(Exception e){
            e.printStackTrace();
        }
        component.deleteRoleById(id);
    }

    @Test
    void commit() {
        long id = getIdCommitedRole();
        Role roleFromTable = component.findByIdOrDie(id);

        assertNotNull(roleFromTable);
        assertEquals(testValues.testName, roleFromTable.getName());
        assertEquals(testValues.testDescription, roleFromTable.getDescription());
        component.deleteRoleById(id);
    }

    @Test
    void findAll() {
        try{
            mockMvc.perform(get("http://localhost:" + port + "/api/roles"))
                    .andExpect(jsonPath("$", hasSize(2)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void deleteRoleById() {
        long id = getIdCommitedRole();
        try{
            mockMvc.perform(get("http://localhost:" + port + "/api/roles"))
                .andExpect(jsonPath("$", hasSize(3)));
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/roles/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            mockMvc.perform(get("http://localhost:" + port + "/api/roles"))
                .andExpect(jsonPath("$", hasSize(2)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void updateRoleById() {
        long id = getIdCommitedRole();
        try{
            mockMvc.perform(MockMvcRequestBuilders
                    .put("/api/roles/"+id)
                    .content(testValues.testRoleUpdate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        }catch(Exception e){
            e.printStackTrace();
        }

        Role roleFromTable = component.findByIdOrDie(id);
        assertNotNull(roleFromTable);
        assertEquals(testValues.testUpdateName, roleFromTable.getName());
        assertEquals(testValues.testUpdateDescription, roleFromTable.getDescription());
        component.deleteRoleById(id);
    }

    private long getIdCommitedRole(){
        MvcResult result = null;
        try{
            result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/roles")
                .content(DataTestValues.testRole)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            return Long.valueOf(result.getResponse().getContentAsString().split(":")[1].split(",")[0]);
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return 0;
    }
}