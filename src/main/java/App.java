import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.Map;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists",Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/stylists-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist new_stylist = new Stylist(request.queryParams("new_stylist"));
      new_stylist.save();
      List<Stylist> stylists = Stylist.all();
      model.put("stylists", stylists);
      model.put("template", "templates/index.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id/clients", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      List<Client> clients = stylist.getClients();
      model.put("stylist", stylist);
      model.put("clients", clients);
      model.put("template", "templates/clients.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      //Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      List<Client> clients = Client.all();
      model.put("clients", clients);
      model.put("template", "templates/all_clients.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int stylist_id = Integer.parseInt(request.queryParams("stylist_id"));
      Stylist stylist = Stylist.find(stylist_id);
      Client new_client = new Client(request.queryParams("new_client"), stylist_id);
      new_client.save();
      List<Client> clients = stylist.getClients();
      model.put("stylist", stylist);
      model.put("clients", clients);
      model.put("template", "templates/clients.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":id")));

      model.put("client", client);
      model.put("template", "templates/clients_edit_form.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      String new_name = request.queryParams("new_name");
      client.updateName(new_name);
      model.put("clients",Client.all());
      model.put("client", client);
      model.put("template", "templates/all_clients.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      client.delete();
      //model.put("client", client);
      model.put("clients",Client.all());
      model.put("template", "templates/all_clients.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());



  }
}
