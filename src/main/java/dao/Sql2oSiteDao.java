package dao;

import models.Site;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oSiteDao  implements SiteDao{
    private final Sql2o sql2o;

    public Sql2oSiteDao(Sql2o sql2o){
        this.sql2o = sql2o; //making the sql2o object available everywhere so we can call methods in it
    }

    @Override
    public void add(Site site) {
        String sql = "INSERT INTO sites (description, engineerId) VALUES (:description, :engineerId)"; //raw sql
        try(Connection con = sql2o.open()){ //try to open a connection
            int id = (int) con.createQuery(sql, true) //make a new variable
                    .bind(site)
                    .executeUpdate() //run it all
                    .getKey(); //int id is now the row number (row “key”) of db
            site.setId(id); //update object to set id now from database
        } catch (Sql2oException ex) {
            System.out.println(ex); //oops we have an error!
        }
    }

    @Override
    public List<Site> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM sites") //raw sql
                    .executeAndFetch(Site.class); //fetch a list
        }
    }


    @Override
    public Site findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM sites WHERE id = :id")
                    .addParameter("id", id) //key/value pair, key must match above
                    .executeAndFetchFirst(Site.class); //fetch an individual item
        }
    }

    @Override
    public void update(int id, String newDescription, int newEngineerId){
        String sql = "UPDATE sites SET (description, engineerId) = (:description, :engineerId) WHERE id=:id"; //raw sql
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("description", newDescription)
                    .addParameter("engineerId", newEngineerId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from sites WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllSites() {
        String sql = "DELETE from sites";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
