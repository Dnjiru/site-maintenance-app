//package dao;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.sql2o.Connection;
//import org.sql2o.Sql2o;
//
//import static junit.framework.TestCase.assertEquals;
//import static junit.framework.TestCase.assertFalse;
//import static org.junit.Assert.*;
//
//public class Sql2oEngineerDaoTest {
//    private Sql2oEngineerDao engineerDao;
//    private Sql2oSiteDao siteDao;
//    private Connection conn;
//
//    @Before
//    public void setUp() throws Exception {
//        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        Sql2o sql2o = new Sql2o(connectionString, "", "");
//        engineerDao = new Sql2oEngineerDao(sql2o);
//        siteDao = new Sql2oSiteDao(sql2o);
//        conn = sql2o.open();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        conn.close();
//    }
//
//    @Test
//    public void addingEngineerSetsId() throws Exception {
//        Engineer engineer = setupNewEngineer();
//        int originalEngineerId = engineer.getId();
//        engineerDao.add(engineer);
//        assertNotEquals(originalEngineerId, engineer.getId());
//    }
//
//    @Test
//    public void existingEngineersCanBeFoundById() throws Exception {
//        Engineer engineer = setupNewEngineer();
//        engineerDao.add(engineer);
//        Engineer foundEngineer = engineerDao.findById(engineer.getId());
//        assertEquals(engineer, foundEngineer);
//    }
//
//    @Test
//    public void addedEngineersAreReturnedFromGetAll() throws Exception {
//        Engineer engineer = setupNewEngineer();
//        engineerDao.add(engineer);
//        assertEquals(1, engineerDao.getAll().size());
//    }
//
//    @Test
//    public void noEngineersReturnsEmptyList() throws Exception {
//        assertEquals(0, engineerDao.getAll().size());
//    }
//
//    @Test
//    public void updateChangesEngineerContent() throws Exception {
//        String initialDescription = "Yardwork";
//        Engineer category = new Engineer (initialDescription);
//        engineerDao.add(engineer);
//        engineerDao.update(engineer.getId(),"Cleaning");
//        Engineer updatedEngineer = engineerDao.findById(engineer.getId());
//        assertNotEquals(initialDescription, updatedEngineer.getName());
//    }
//
//    @Test
//    public void deleteByIdDeletesCorrectEngineer() throws Exception {
//        Engineer engineer = setupNewEngineer();
//        engineerDao.add(engineer);
//        engineerDao.deleteById(engineer.getId());
//        assertEquals(0, categoryDao.getAll().size());
//    }
//
//    @Test
//    public void clearAllClearsAllEngineers() throws Exception {
//        Engineer engineer = setupNewEngineer();
//        Engineer otherEngineer = new Engineer("Cleaning");
//        engineerDao.add(engineer);
//        engineerDao.add(otherEngineer);
//        int daoSize = engineerDao.getAll().size();
//        engineerDao.clearAllEngineers();
//        assertTrue(daoSize > 0 && daoSize > engineerDao.getAll().size());
//    }
//
//    @Test
//    public void getAllSitesByEngineerReturnsSitesCorrectly() throws Exception {
//        Engineer engineer = setupNewEngineer();
//        engineerDao.add(engineer);
//        int engineerId = engineer.getId();
//        Site newSite = new Site("Nairobi Gospel", engineerId);
//        Site otherSite = new Site("QoA Kasarani", engineerId);
//        Site thirdSite = new Site("Thika Kiboko", engineerId);
//        siteDao.add(newSite);
//        siteDao.add(otherSite); //we are not adding 3 sites so we can test things precisely.
//        assertEquals(2, engineerDao.getAllSitesByEngineer(engineerId).size());
//        assertTrue(engineerDao.getAllSitesByEngineer(engineerId).contains(newSite));
//        assertTrue(engineerDao.getAllSitesByEngineer(engineerId).contains(otherSite));
//        assertFalse(engineerDao.getAllSitesByEngineer(engineerId).contains(thirdSite)); //things are accurate!
//    }
//
//    // helper method
//    public Engineer setupNewEngineer(){
//        return new Engineer("Mwaniki");
//    }
//}