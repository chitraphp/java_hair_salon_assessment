import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.junit.Assert.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppIntegrationTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Stylists-Clients");
  }

    @Test
    public void stylistIsDisplayedTest() {
    goTo("http://localhost:4567/stylists/new");
    fill("#new_stylist").with("chitra");
    submit(".btn");
    assertThat(pageSource()).contains("chitra");
  }

  @Test
  public void stylistLinkIsDisplayedTest() {
  Stylist new_stylist = new Stylist("chitra");
  new_stylist.save();
  String stylistPath = String.format("http://localhost:4567");
  goTo(stylistPath);
  assertThat(pageSource()).contains("chitra");
  }

  @Test
  public void allStylistClientsAreDisplayedTest() {
  Stylist myStylist = new Stylist("chitra");
  myStylist.save();
  Client firstClient = new Client("Great Clips", myStylist.getId());
  firstClient.save();
  Client secondClient = new Client("Fun Styles", myStylist.getId());
  secondClient.save();
  String stylistPath = String.format("http://localhost:4567/stylists/%d/clients", myStylist.getId());
  goTo(stylistPath);
  assertThat(pageSource()).contains("chitra");
  assertThat(pageSource()).contains("Great Clips");
  }

  @Test
  public void editClientLinkTest() {
    Stylist myStylist = new Stylist("chitra");
    myStylist.save();
    Client firstClient = new Client("Great Clips", myStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Fun Styles", myStylist.getId());
    secondClient.save();

    goTo("http://localhost:4567/");
    click("a", withText("All Clients"));
    assertThat(pageSource()).contains("Great Clips");
  }

  @Test
  public void editClientLinkFormTest() {
    Stylist myStylist = new Stylist("chitra");
    myStylist.save();
    Client firstClient = new Client("Great Clips", myStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Fun Styles", myStylist.getId());
    secondClient.save();

    String clientPath = String.format("http://localhost:4567/clients/%d/edit", firstClient.getId());
    goTo(clientPath);
    fill("#new_name").with("Great Clips!!!!");
    submit(".btn");
    assertThat(pageSource()).contains("Great Clips!!!!");
  }

    @Test
    public void deleteClientTest() {
    Stylist new_stylist = new Stylist("chitra");
    new_stylist.save();
    Client firstClient = new Client("Great Clips", new_stylist.getId());
    firstClient.save();
    firstClient.delete();
    String stylistPath = String.format("http://localhost:4567/clients");
    goTo(stylistPath);
    assertEquals(pageSource().contains("chitra"), false);
    }


}
