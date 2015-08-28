import org.sql2o.*;

public class DB {
  public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/best_restaurants","chitra","1234");
  // Make sure to customize the url to have the actual database name.
}
