package lab2mod;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import bsh.Console;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class testLogin {
	
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
//数据成员变量
  private String id = null;
  private String pwd = null;
  private String gitAddress = null;
  
//使用数据的构造函数 
  public testLogin(String id, String gitAddress){
	  this.id = id;
	  this.gitAddress = gitAddress;
  }
//数据供给方法（静态，用@Parameter注释，返回类型为Collection
  @Parameters
  public static Collection<Object[]> getData() throws IOException{
      Object[][] data = new Object[117][];
      CsvReader r = new CsvReader("F:/大三下/软件测试/测试项目/lab2/inputgit.csv",',',Charset.forName("GBK"));
      r.readHeaders();
      int count = 0;
      while(r.readRecord()){
          data[count] = new Object[]{r.get(0), r.get(2)};
          count++;
      }
      return Arrays.asList(data);
  }  
  
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://121.193.130.195:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testLogin() throws Exception {
	driver.get(baseUrl + "/");
	driver.findElement(By.id("name")).clear();
	driver.findElement(By.id("name")).sendKeys(this.id);
	driver.findElement(By.id("pwd")).clear();
	driver.findElement(By.id("pwd")).sendKeys(this.id.substring(4));
	new Select(driver.findElement(By.id("gender"))).selectByVisibleText("女");
	driver.findElement(By.id("submit")).click();
	assertEquals(this.gitAddress, driver.findElement(By.xpath("//tbody[@id='table-main']/tr[3]/td[2]")).getText());
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
