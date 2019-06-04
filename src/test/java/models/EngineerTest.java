package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EngineerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void NewEngineerObjectGetsCorrectlyCreated_true() throws Exception {
        Engineer engineer = setupNewEngineer();
        assertEquals(true, engineer instanceof Engineer);
    }

    @Test
    public void EngineerInstantiatesWithName_school() throws Exception {
        Engineer category = setupNewEngineer();
        assertEquals("school", category.getName());
    }

    //helper methods
    public Engineer setupNewEngineer(){
        return new Engineer("school");
    }
}