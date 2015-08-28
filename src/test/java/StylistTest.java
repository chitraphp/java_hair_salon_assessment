import org.junit.*;
import static org.junit.Assert.*;
// import anything else needed

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Stylist.all().size());
  }

  @Test
  public void equals_returnTrueIfNamesMatch() {
    Stylist firstStylist = new Stylist("chitra");
    Stylist secondStylist = new Stylist("chitra");
    assertEquals(true, firstStylist.equals(secondStylist));
  }

  @Test
  public void getStylistName_returnsCorrectName(){
    Stylist newStylist = new Stylist("chitra");
    assertEquals("chitra", newStylist.getName());
  }

  @Test
  public void getId_returnsCorrectValue() {
    Stylist newStylist = new Stylist("chitra");
    newStylist.save();
    assertEquals(Stylist.all().get(0).getId(), newStylist.getId());
  }

  @Test
  public void save_addsStylistToDatabase() {
    Stylist newStylist = new Stylist("chitra");
    newStylist.save();
    assertEquals(true, Stylist.all().get(0).equals(newStylist));
  }

  @Test
  public void find_findsStylistById() {
    Stylist newStylist = new Stylist("chitra");
    newStylist.save();
    Stylist savedStylist = Stylist.find(newStylist.getId());
    assertEquals(savedStylist, newStylist);
  }

  @Test
  public void update_stylist() {
    Stylist newStylist = new Stylist("chitra");
    newStylist.save();
    newStylist.update("suchitra");
    assertEquals("suchitra",Stylist.all().get(0).getName());
  }

  @Test
  public void delete_stylist() {
    Stylist newStylist = new Stylist("chitra");
    newStylist.save();
    newStylist.delete();
    assertEquals(0,Stylist.all().size());
  }






}
