import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.Post;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");

        get("/",(request, response) -> {
            Map<String,Object> model = new HashMap<>();
            ArrayList<Post> posts = Post.getAll();
            model.put("posts", posts);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/posts/new",(request, response) -> {
            Map<String,Object> model = new HashMap<String,Object>();
            String content = request.queryParams("content");
            Post newPost = new Post(content);
            return new ModelAndView(model,"success.hbs");
        },  new HandlebarsTemplateEngine());



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