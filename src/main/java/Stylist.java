import org.sql2o.*;
import java.util.List;

public class Stylist {

   private int id;
   private String name;

  public Stylist (String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Client> getClients() {
    String sql = "SELECT * FROM clients WHERE stylist_id = :id";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
              .addParameter("id", id)
              .executeAndFetch(Client.class);
    }
  }

  @Override
  public boolean equals(Object otherStylist) {
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getName().equals(newStylist.getName()) &&
              this.getId() == newStylist.getId();
    }
  }

  public static List<Stylist> all() {
    String sql = "SELECT * FROM stylists";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO stylists (name) VALUES (:name);";
    try(Connection con = DB.sql2o.open()) {
      this.id = (int)con.createQuery(sql, true)
                  .addParameter("name", name)
                  .executeUpdate()
                  .getKey();
    }
  }

  public static Stylist find(int id) {
    String sql = "SELECT * FROM stylists WHERE id = :id;";
    try (Connection con = DB.sql2o.open()) {
      Stylist cuisine = con.createQuery(sql)
                            .addParameter("id", id)
                            .executeAndFetchFirst(Stylist.class);
      return cuisine;
    }
  }

  public void update( String new_name) {
    this.name = new_name;
    String sql = "UPDATE stylists SET name = :name WHERE id = :id;";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    String sql = "DELETE FROM stylists WHERE id = :id;";
    try (Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }

  }

}
