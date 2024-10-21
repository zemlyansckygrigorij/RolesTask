package org.example.rolestask.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@Component
@ContextConfiguration
@PropertySource(
        "classpath:testValues.properties")
public class DataTestValues {
    @Value("${test.name}")
    public String testName;
    @Value("${test.description}")
    public String testDescription;

    @Value("${test.update.name}")
    public String testUpdateName;
    @Value("${test.update.description}")
    public String testUpdateDescription;
    @Value("${test.id.first}")
    public long idFirst;
    @Value("${test.name.first}")
    public String nameFirst;
    @Value("${test.description.first}")
    public String descriptionFirst;


    public static final String testRole = """
           {
               "name":"Test",
               "description":"description"
           }
    """;
    public static final String testRoleUpdate = """
           {
               "name":"Testupdate",
               "description":"testupdatedescription"
           }
    """;
}
