package dao;

import java.util.List;
import models.Engineer;
import models.Site;

public interface EngineerDao {
    //LIST
    List<Engineer> getAll();

    //CREATE
    void add (Engineer engineer);

    //READ
    Engineer findById(int id);
    List<Site> getAllSitesByEngineer(int engineerId);

    //UPDATE
    void update(int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllEngineers();


}
