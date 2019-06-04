import java.util.*;

;
import dao.Sql2oEngineerDao;
import dao.Sql2oSiteDao;
import models.Engineer;
import models.Site;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");

        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oSiteDao siteDao = new Sql2oSiteDao(sql2o);
        Sql2oEngineerDao engineerDao = new Sql2oEngineerDao(sql2o);

        //configure for Deployment
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port (port);

        //get: show all sites in all engineers and show all engineers
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Locale.Engineer> allEngineers = engineerDao.getAll();
            model.put("engineers", allEngineers);
            List<Site> sites = siteDao.getAll();
            model.put("sites", sites);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //show new engineer form
        get("/engineers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> engineers = engineerDao.getAll(); //refresh list of links for navbar
            model.put("engineers", engineers);
            return new ModelAndView(model, "engineer-form.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //post: process new engineer form
        post("/engineers", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Engineer newEngineer = new Engineer(name);
            engineerDao.add(newEngineer);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all engineers and all sites
        get("/engineers/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            engineerDao.clearAllEngineers();
            siteDao.clearAllSites();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all sites
        get("/sites/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            siteDao.clearAllSites();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual engineer and sites he/she maintains
        get("/engineers/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfEngineerToFind = Integer.parseInt(req.params("id")); //new
            Engineer foundEngineer = engineerDao.findById(idOfEngineerToFind);
            model.put("engineer", foundEngineer);
            List<Site> allSitesByEngineer = engineerDao.getAllSitesByEngineer(idOfEngineerToFind);
            model.put("sites", allSitesByEngineer);
            model.put("engineers", engineerDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "engineer-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //get: show a form to update an Engineer
        get("/engineers/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editEngineer", true);
            Engineer engineer = engineerDao.findById(Integer.parseInt(req.params("id")));
            model.put("engineer", engineer);
            model.put("engineers", engineerDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "engineer-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update an engineer
        post("/engineers/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfEngineerToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newEngineerName");
            exception().update(idOfEngineerToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: delete an individual Site
        get("/engineers/:engineer_id/sites/:site_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSiteToDelete = Integer.parseInt(req.params("site_id"));
            siteDao.deleteById(idOfSiteToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new site form
        get("/sites/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> engineers = engineerDao.getAll();
            model.put("engineers", engineers);
            return new ModelAndView(model, "site-form.hbs");
        }, new HandlebarsTemplateEngine());

        //site: process new site form
        post("/sites", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> allEngineers = engineerDao.getAll();
            model.put("engineers", allEngineers);
            String description = req.queryParams("description");
            int engineerId = Integer.parseInt(req.queryParams("engineerId"));
            Site newSite = new Site(description, engineerId ); //ignore the hardcoded engineerId
            siteDao.add(newSite);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual site that is assigned to an engineer
        get("/engineers/:engineer_id/sites/:site_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSiteToFind = Integer.parseInt(req.params("site_id"));
            Site foundSite = siteDao.findById(idOfSiteToFind);
            int idOfEngineerToFind = Integer.parseInt(req.params("engineer_id"));
            Engineer foundEngineer = engineerDao.findById(idOfEngineerToFind);
            model.put("site", foundSite);
            model.put("engineer", foundEngineer);
            model.put("engineers", engineerDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "site-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a site
        get("/sites/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Engineer> allEngineer = engineerDao.getAll();
            model.put("engineers", allEngineers);
            Site site = siteDao.findById(Integer.parseInt(req.params("id")));
            model.put("site", site);
            model.put("editSite", true);
            return new ModelAndView(model, "site-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a site
        post("/sites/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int siteToEditId = Integer.parseInt(req.params("id"));
            String newContent = req.queryParams("description");
            int newEngineerId = Integer.parseInt(req.queryParams("engineerId"));
            siteDao.update(siteToEditId, newContent, newEngineerId);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}






//Blog Post
//        //get: show new post form
//        get("/posts/new", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            return new ModelAndView(model, "post-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: show all posts
//        get("/",(request, response) -> {
//            Map<String,Object> model = new HashMap<>();
//            ArrayList<Post> posts = Post.getAll();
//            model.put("posts", posts);
//            return new ModelAndView(model, "index.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //post: process new post form
//        post("/posts/new",(request, response) -> { //URL to make new post on POST route
//            Map<String,Object> model = new HashMap<String,Object>();
//            String content = request.queryParams("content");
//            Post newPost = new Post(content);
//            return new ModelAndView(model,"success.hbs");
//        },  new HandlebarsTemplateEngine());
//
//
//        //get: show an individual post
//        get("/posts/:id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfPostToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
//            Post foundPost = Post.findById(idOfPostToFind); //use it to find post
//            model.put("post", foundPost); //add it to model for template to display
//            return new ModelAndView(model, "post-detail.hbs"); //individual post page.
//        }, new HandlebarsTemplateEngine());
//
//
//        //get: show a form to update a post
//        get("/posts/:id/update", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfPostToEdit = Integer.parseInt(req.params("id"));
//            Post editPost = Post.findById(idOfPostToEdit);
//            model.put("editPost", editPost);
//            return new ModelAndView(model, "post-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //post: process a form to update a post
//        post("/posts/:id/update", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            String newContent = req.queryParams("content");
//            int idOfPostToEdit = Integer.parseInt(req.params("id"));
//            Post editPost = Post.findById(idOfPostToEdit);
//            editPost.update(newContent); //don’t forget me
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: delete an individual post
//        get("/posts/:id/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfPostToDelete = Integer.parseInt(req.params("id")); //pull id - must match route segment
//            Post deletePost = Post.findById(idOfPostToDelete); //use it to find post
//            deletePost.deletePost();
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: delete all posts
//        get("/posts/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            Post.clearAllPosts();
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());


//first form
//        get("/", (request, response) -> {
//           Map<String, Object> model = new HashMap<String, Object>();//new model is made to store information
//            return new ModelAndView(model, "hello.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/favorite_photos",(request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            return new ModelAndView(new HashMap(), "favorite_photos.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/form", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            return new ModelAndView(model, "form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/greeting_card", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            String recipient = request.queryParams("recipient");
//            String sender = request.queryParams("sender");
//            model.put("recipient", recipient);
//            model.put("sender", sender);
//            return new ModelAndView(model, "greeting_card.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/form2", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            return new ModelAndView(model, "form2.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/engineer.hbs", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            String recipient = request.queryParams("engineer");
//            String sender = request.queryParams("sites");
//            String ek = request.queryParams("ek");
//            model.put("engineer", recipient);
//            model.put("ek", ek);
//            model.put("sites", sender);
//            return new ModelAndView(model, "engineer.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/",(request, response) -> {
//            Map<String, Object> model = new HashMap<String,Object>();
//            model.put("username", request.session().attribute("username"));
//            return new ModelAndView(model, "welcome.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        post("/welcome",(request, response) -> {
//            Map<String,Object> model = new HashMap<String, Object>();
//
//            String inputtedUsername = request.queryParams("username");
//            request.session().attribute("username", inputtedUsername);
//            model.put("username", inputtedUsername);
//
//            return new ModelAndView(model, "welcome.hbs");
//        }, new HandlebarsTemplateEngine());



    }
}