import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dao.Sql2oTaskDao;
import models.Post;
import models.;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");

        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,  "", "");
        Sql2oTaskDao taskDao = new Sql2oTaskDao(sql2o);

        //get: delete all tasks
        get("/tasks/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            taskDao.clearAllTasks(); //change
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual task
        get("/tasks/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("id"));
             deleteTask = taskDao.findById(idOfTaskToDelete); //change
            deleteTask.deleteTask();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show all tasks
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<> tasks = .getAll(); //change
            model.put("tasks", tasks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new task form
        get("/tasks/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new task form
        post("/tasks", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            String description = req.queryParams("description");
             newTask = new (description); //change
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual task
        get("/tasks/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToFind = Integer.parseInt(req.params("id"));
             foundTask = .findById(idOfTaskToFind); //change
            model.put("task", foundTask);
            return new ModelAndView(model, "task-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a task
        get("/tasks/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToEdit = Integer.parseInt(req.params("id"));
             editTask = .findById(idOfTaskToEdit); //change
            model.put("editTask", editTask);
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a task
        post("/tasks/:id", (req, res) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            String newContent = req.queryParams("description");
            int idOfTaskToEdit = Integer.parseInt(req.params("id"));
             editTask = .findById(idOfTaskToEdit); //change
            editTask.update(newContent); //change
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