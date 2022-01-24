
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Main {

    WebDriver driver;
    FplUtils fplUtils;

    @Before
    public void beforeTest() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://fantasy.premierleague.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.className("js-accept-all-close")));

        driver.findElement(By.className("js-accept-all-close")).click();
        driver.findElement(By.id("loginUsername")).sendKeys("lukpawlowski1996@gmail.com");
        driver.findElement(By.id("loginPassword")).sendKeys("/d%,pP2MBEeK?dc");
        driver.findElement(By.className("jYWOpQ")).click();

        fplUtils = new FplUtils(driver);
    }

    @Test
    public void removeTest() {
        fplUtils.clickTab("Transfers");
        fplUtils.removeOrRestorePlayer("Lacazette");
        fplUtils.removeOrRestorePlayer("Lacazette");
    }

    @Test
    public void makeCapitainTest(){
        fplUtils.clickTab("Pick Team");
        fplUtils.makeCaptain("Lacazette");
    }
    @Test
    public void filtrTest(){
        fplUtils.clickTab("Transfers");
        fplUtils.sortPlayerBy("Price");
        fplUtils.vievPlayersBy("Arsenal");

    }
    @Test
    public void tabTest() {
        fplUtils.clickTab("Transfers");
        Assert.assertTrue(driver.getCurrentUrl().contains("transfers"));
        fplUtils.vievPlayersBy("Arsenal");
        fplUtils.searchPlayer("De Bruyne");
        fplUtils.maxCostOfPlayer(12.2);
    }
    @Test
    public void addPlayerToTheSquad() {
        fplUtils.clickTab("Transfers");
        Select playerCharacteristic = new Select(driver.findElement(By.id("filter")));
        playerCharacteristic.selectByIndex(0);
        Select sortPlayerBy = new Select(driver.findElement(By.id("sort")));
        sortPlayerBy.selectByIndex(0);
        driver.findElement(By.id("search")).sendKeys("Alexander-Arnold");
        Select maxCostOfPlayer = new Select(driver.findElement(By.id("maxCost")));
        maxCostOfPlayer.selectByIndex(0);
        List<WebElement> filteredPlayers = driver.findElements(By.xpath("//table/tbody/tr"));
        Assert.assertEquals(1, filteredPlayers.size());
        filteredPlayers.get(0).findElement(By.xpath("//td[2]/button")).click();
        WebElement errorMaxDefender = driver.findElement(By.xpath("//div[@type='error']"));
        Assert.assertEquals("You already have the maximum number of Defenders", errorMaxDefender.getText());
        WebElement addPlayerButton = driver.findElement(By.xpath("//div[@role='document']/div[3]/ul/li[1]/button"));
        Assert.assertFalse(addPlayerButton.isEnabled());
    }

    @Test
    public void tooExpensiveSquadAlert() {
        fplUtils.clickTab("Transfers");
        //driver.findElement(By.xpath("//nav[@role='navigation']/ul/li[3]/a")).click();
        double moneyRemaining = Double.parseDouble(driver.findElements(By.xpath("//div[@role='status']/div")).get(1).getText());
        double playerPrice = Double.parseDouble(driver.findElement(By.xpath("//div[@data-testid='pitch']/div[3]/div[4]/div/div/button/div/div[2]")).getText());
        driver.findElement(By.xpath("//div[@data-testid='pitch']/div[3]/div[4]/div/div/div/div/button")).click();
        driver.findElement(By.id("search")).sendKeys("De Bruyne");
        List<WebElement> filteredPlayers = driver.findElements(By.xpath("//table/tbody/tr"));
        filteredPlayers.get(0).findElement(By.xpath("//td[2]/button")).click();
        driver.findElement(By.xpath("//div[@role='document']/div[2]/ul/li[1]/button")).click();


        WebElement alertSelectPlayer = driver.findElement(By.xpath("//div[@role='status']/div[2]/div"));
        Assert.assertTrue(alertSelectPlayer.isDisplayed());

        WebElement addedPlayerSucces = driver.findElement(By.xpath("//div[@role='status']/div/div"));
        Assert.assertEquals("De Bruyne has been added to your squad.", addedPlayerSucces.getText());
    }

    @Test
    public void goodValueOfSquad() {
        fplUtils.clickTab("Transfers");
        double oldMoneyRemaining = Double.parseDouble(driver.findElements(By.xpath("//div[@role='status']/div")).get(1).getText());
        double oldPlayerPrice = Double.parseDouble(driver.findElement(By.xpath("//div[@data-testid='pitch']/div[3]/div[4]/div/div/button/div/div[2]")).getText());
        driver.findElement(By.xpath("//div[@data-testid='pitch']/div[3]/div[4]/div/div/div/div/button")).click();
        driver.findElement(By.id("search")).sendKeys("Dendoncker");
        List<WebElement> filteredPlayers = driver.findElements(By.xpath("//table/tbody/tr"));
        filteredPlayers.get(0).findElement(By.xpath("//td[2]/button")).click();
        driver.findElement(By.xpath("//div[@role='document']/div[2]/ul/li[1]/button")).click();

        WebElement addedPlayerSucces = driver.findElement(By.xpath("//div[@role='status']/div/div"));
        Assert.assertEquals("Dendoncker has been added to your squad.", addedPlayerSucces.getText());
        WebElement makeTransferButton = driver.findElement(By.className("SaveBar-sc-1juyyag-0")).findElement(By.tagName("button"));
        Assert.assertTrue(makeTransferButton.isEnabled());
        WebElement newPlayer = driver.findElement(By.xpath("//div[@data-testid='pitch']/div[3]/div[4]/div/div/button/div"));
        String newPlayerName = newPlayer.findElement(By.xpath("//div[1]")).getText();

        Assert.assertEquals("Dendoncker", newPlayerName);
        double newPlayerPrice = Double.parseDouble(newPlayer.findElement(By.xpath("//div[2]")).getText());
        double newMoneyRemaining = Double.parseDouble(driver.findElements(By.xpath("//div[@role='status']/div")).get(1).getText());
        Assert.assertEquals(newMoneyRemaining, oldPlayerPrice - newPlayerPrice + oldMoneyRemaining, 0.5);

    }

    @Test
    public void minusPointsTransfer() {

    }

    @After
    public void afterTest() {
        // driver.quit();
    }
}