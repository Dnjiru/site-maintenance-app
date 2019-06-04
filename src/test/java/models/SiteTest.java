package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class SiteTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void NewSiteObjectGetsCorrectlyCreated_true() throws Exception {
        Site task = setupNewSite();
        assertEquals(true, task instanceof Site);
    }

    @Test
    public void SiteInstantiatesWithDescription_true() throws Exception {
        Site site = setupNewSite();
        assertEquals("Nairobi Gospel", site.getDescription());
    }

    @Test
    public void isCompletedPropertyIsFalseAfterInstantiation() throws Exception {
        Site site = setupNewSite();
        assertEquals(false, site.getCompleted()); //should never start as completed
    }

    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
        Site site = setupNewSite();
        assertEquals(LocalDateTime.now().getDayOfWeek(), site.getCreatedAt().getDayOfWeek());
    }

    //helper methods
    public Site setupNewSite(){
        return new Site("Nairobi Gospel", 1);
    }
}