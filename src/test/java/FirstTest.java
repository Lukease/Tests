import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.github.javafaker.Faker;
import com.github.javafaker.PhoneNumber;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.beans.Visibility;
import java.time.Duration;


public class FirstTest {

    WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/index.php");

    }

    @Test
    public void userRegistrationWithValidData() throws InterruptedException {

        driver.findElement(By.cssSelector(("[title=\"Log in to your customer account\"]"))).click();
        Thread.sleep(500);

        driver.findElement(By.id("email_create")).sendKeys("testowyemail1@test.com");
        driver.findElement(By.id("SubmitCreate")).click();
        Thread.sleep(500);

        driver.findElement(By.id("id_gender1")).click();

        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String company = faker.company().buzzword();
        String city = faker.address().city();
        String phone = faker.phoneNumber().cellPhone();
        String streetAddress = faker.address().streetAddress();

        driver.findElement(By.id("customer_firstname")).sendKeys(firstName);
        driver.findElement(By.id("customer_lastname")).sendKeys(lastName);

        driver.findElement(By.id("submitAccount")).click();
        Thread.sleep(500);

        boolean isAlertDisplayed = driver.findElement(By.className("alert")).isDisplayed();
        Assert.assertTrue(isAlertDisplayed);

        driver.findElement(By.id("email")).clear();
        Thread.sleep(500);
        driver.findElement(By.id("email")).sendKeys("testowyemail1@test.com");
        driver.findElement(By.id("passwd")).sendKeys("123456");

        Select birthDay = new Select(driver.findElement(By.id("days")));
        birthDay.selectByValue("6");
        Select monthOfBirthday = new Select(driver.findElement(By.id("months")));
        monthOfBirthday.selectByValue("3");
        Select yearOfBirthday = new Select(driver.findElement(By.id("years")));
        yearOfBirthday.selectByValue("1996");

        driver.findElement(By.id("optin")).click();
        driver.findElement(By.id("newsletter")).click();

        boolean isFirstNameDispalyed = driver.findElement(By.id("firstname")).isDisplayed();
        Assert.assertTrue(isFirstNameDispalyed);
        boolean isLastNameDispalyed = driver.findElement(By.id("lastname")).isDisplayed();
        Assert.assertTrue(isLastNameDispalyed);

        driver.findElement(By.id("company")).sendKeys(company);
        driver.findElement(By.id("address1")).sendKeys(streetAddress);
        driver.findElement(By.id("city")).sendKeys(city);

        Select state = new Select(driver.findElement(By.id("id_state")));
        state.selectByValue("7");
        driver.findElement(By.id("postcode")).sendKeys("12344");
        Select country = new Select(driver.findElement(By.id("id_country")));
        country.selectByValue("21");
        driver.findElement(By.id("phone")).sendKeys(phone);

        driver.findElement(By.id("submitAccount")).click();

    }

    /*@After
    public void tearDown() {
        driver.quit();
    }

     */
}
