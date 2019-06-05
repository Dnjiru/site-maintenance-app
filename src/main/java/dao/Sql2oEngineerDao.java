package dao;

import models.Engineer;
import models.Site;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oEngineerDao implements EngineerDao {
    private final Sql2o sql2o;

    public Sql2oEngineerDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Engineer engineer) {
        String sql = "INSERT INTO engineers (name) VALUES (:name)";
        try(Connection con = DB.sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(engineer)
                    .executeUpdate()
                    .getKey();
            engineer.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Engineer> getAll() {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM engineers")
                    .executeAndFetch(Engineer.class);
        }
    }

    @Override
    public Engineer findById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM engineers WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Engineer.class);
        }
    }


    @Override
    public void update(int id, String newName){
        String sql = "UPDATE engineers SET name = :name WHERE id=:id";
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from engineers WHERE id=:id"; //raw sql
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllEngineers() {
        String sql = "DELETE from engineers"; //raw sql
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Site> getAllSitesByEngineer(int engineerId) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM tasks WHERE engineerId = :engineerId")
                    .addParameter("engineerId", engineerId)
                    .executeAndFetch(Site.class);
        }
    }
}