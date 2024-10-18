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


    public static final String testUser = """
           {
               "name":"Test",
               "description":"description"
           }
    """;
    public static final String testUserUpdate = """
           {
               "name":"Testupdate",
               "description":"testupdatedescription"
           }
    """;
}
