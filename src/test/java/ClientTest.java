import org.junit.*;
import static org.junit.Assert.*;
// import anything else needed

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Client.all().size());
  }

  @Test
  public void equals_returnTrueIfNamesMatch() {
    Client firstClient = new Client("Great Clips",1);
    Client secondClient = new Client("Great Clips", 1);
    assertEquals(true, firstClient.equals(secondClient));
  }

  @Test
  public void getClientName_returnsCorrectName(){
    Client newClient = new Client("Great Clips", 1);
    assertEquals("Great Clips", newClient.getName());
  }

  @Test
  public void getClientId_returnsCorrectId(){
    Client newClient = new Client("Great Clips", 1);
    assertEquals(1, newClient.getStylistId());
  }

  @Test
  public void getId_returnsCorrectValue() {
    Client newClient = new Client("Great Clips", 1);
    newClient.save();
    assertEquals(Client.all().get(0).getId(), newClient.getId());
  }

  @Test
  public void save_addsClientToDatabase() {
    Client newClient = new Client("Great Clips", 1);
    newClient.save();
    assertEquals(true, Client.all().get(0).equals(newClient));
  }

  @Test
  public void find_findsClientById() {
    Client newClient = new Client("Great Clips", 1);
    newClient.save();
    Client savedClient = Client.find(newClient.getId());
    assertEquals(savedClient, newClient);
  }

  @Test
  public void updateName_updatesToNewName() {
    Client newClient = new Client("Great Clips", 1);
    newClient.save();
    newClient.updateName("Great Hair salon");
    assertEquals("Great Hair salon",Client.all().get(0).getName());
  }
  @Test
  public void delete_client() {
    Client newClient = new Client("Great Clips", 1);
    newClient.save();
    newClient.delete();
    assertEquals(0,Client.all().size());
  }

}
