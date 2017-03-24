package lab2;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import bsh.Console;

import com.csvreader.CsvReader;

import java.nio.charset.Charset;

public class TestLogin {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String id = null;
  private String pwd = null;
  private String gitAddress = null;
  
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://121.193.130.195:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testLogin() throws Exception {
	CsvReader cr =  new CsvReader("F:/大三下/软件测试/测试项目/lab2/inputgit.csv", ',',Charset.forName("GBK"));
	cr.readHeaders();
	while (cr.readRecord()) {
		id = cr.get(0);
		gitAddress = cr.get(2);
		pwd = id.substring(4);
		driver.get(baseUrl + "/");
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys(id);
	    driver.findElement(By.id("pwd")).clear();
	    driver.findElement(By.id("pwd")).sendKeys(pwd);
	    new Select(driver.findElement(By.id("gender"))).selectByVisibleText("女");
	    driver.findElement(By.id("submit")).click();
	    assertEquals(gitAddress, driver.findElement(By.xpath("//tbody[@id='table-main']/tr[3]/td[2]")).getText());

	}
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
