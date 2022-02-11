
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Main {

    static WebDriver driver;
    static FplUtils fplUtils;

    @BeforeAll
    static void beforeTests() {
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
        String removedPlayer = driver.findElement(By.className("kpjbSp")).getText();
        assertThat(removedPlayer).isEqualTo("Lacazette has been removed from your squad.");
        fplUtils.removeOrRestorePlayer("Lacazette");
        assertThat(removedPlayer).isEqualTo("Lacazette has been added to your squad.");
    }

    @Test
    public void addPlayerTest() {
        fplUtils.clickTab("Transfers");
        fplUtils.removeOrRestorePlayer("Ronaldo");
        fplUtils.removeOrRestorePlayer("Ronaldo");
        fplUtils.removeOrRestorePlayer("Ronaldo");
        String succesRemove = driver.findElement(By.className("kpjbSp")).getText();
        assertThat(succesRemove).isEqualTo("Ronaldo has been removed from your squad.");
        fplUtils.addPlayerToTeam("Dennis");
        String succesAdd = driver.findElement(By.className("kpjbSp")).getText();
        assertThat(succesAdd).isEqualTo("Dennis has been added to your squad.");
    }

    @Test
    public void makeCapitainTest() {
        fplUtils.clickTab("Pick Team");
        fplUtils.makeCaptain("Lacazette");
        Player capitain = fplUtils.getPlayerByName("Lacazette", true);
        WebElement capitainPosition = driver.findElement(By.xpath("//div[@data-testid='pitch']/div[" + (fplUtils.getFormationsOrder().indexOf(capitain.getPosition()) + 1) + "]/div[" + capitain.getPlayerIndex() + "]/div/div"));
        assertThat(capitainPosition.findElement(By.className("kemjNJ"))).isNotNull();
    }

    @Test
    public void makeViceCapitainTest() {
        fplUtils.clickTab("Pick Team");
        fplUtils.makeViceCaptain("Gordon");
        Player viceCapitain = fplUtils.getPlayerByName("Gordon", true);
        WebElement capitainPosition = driver.findElement(By.xpath("//div[@data-testid='pitch']/div[" + (fplUtils.getFormationsOrder().indexOf(viceCapitain.getPosition()) + 1) + "]/div[" + viceCapitain.getPlayerIndex() + "]/div/div"));
        assertThat(capitainPosition.findElement(By.className("beWuyf"))).isNotNull();
    }

    @Test
    public void filtrerTest() {
        fplUtils.clickTab("Transfers");
        fplUtils.sortPlayerBy("Price");
        String sortBy = driver.findElement(By.id("sort")).getText();
        assertThat(sortBy).isEqualTo("Price");
        fplUtils.vievPlayersBy("Arsenal");
        String viewBy = driver.findElement(By.id("filter")).getText();
        assertThat(viewBy).isEqualTo("Arsenal");
    }

    @Test
    public void tooExpensiveSquadAlert() {
        fplUtils.clickTab("Transfers");
        fplUtils.removeOrRestorePlayer("Martinelli");
        fplUtils.addPlayerToTeam("Jota");
        WebElement table = driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div/div/div/div[2]/div[3]"));
        float moneyRemaining = Float.parseFloat(table.findElement(By.xpath("//div[6]/div[1]/div[1]")).getText());
        assertThat(moneyRemaining).isLessThan(0);
        WebElement makeTransfers = driver.findElement(By.className("iMCPqE"));
        assertThat(makeTransfers.isEnabled()).isFalse();
    }

    @Test
    public void goodValueOfSquad() {
        fplUtils.clickTab("Transfers");
        double oldMoneyRemaining = Double.parseDouble(driver.findElements(By.xpath("//div[@role='status']/div")).get(2).getText());
        double oldPlayerPrice = Double.parseDouble(driver.findElement(By.xpath("//div[@data-testid='pitch']/div[3]/div[4]/div/div/button/div/div[2]")).getText());
        fplUtils.removeOrRestorePlayer("Ward-Prowse");
        fplUtils.addPlayerToTeam("Dendoncker");

        WebElement addedPlayerSucces = driver.findElement(By.xpath("//div[@role='status']/div/div"));
        assertThat(addedPlayerSucces.getText()).isEqualTo("Dendoncker has been added to your squad.");
        WebElement makeTransferButton = driver.findElement(By.className("SaveBar-sc-1juyyag-0")).findElement(By.tagName("button"));
        assertThat(makeTransferButton.isEnabled()).isTrue();
        WebElement newPlayer = driver.findElement(By.xpath("//div[@data-testid='pitch']/div[3]/div[4]/div/div/button/div"));
        String newPlayerName = newPlayer.findElement(By.xpath("//div[1]")).getText();
        assertThat(newPlayerName).isEqualTo("Dendoncker");

        double newPlayerPrice = Double.parseDouble(newPlayer.findElement(By.xpath("//div[2]")).getText());
        double newMoneyRemaining = Double.parseDouble(driver.findElements(By.xpath("//div[@role='status']/div")).get(1).getText());
        assertThat(oldPlayerPrice - newPlayerPrice + oldMoneyRemaining).isEqualTo(newMoneyRemaining);

    }

    @Test
    public void freeTransfers() {
        fplUtils.clickTab("Transfers");
        WebElement layoutMain = driver.findElement(By.className("wXYnc"));
        WebElement table = layoutMain.findElement(By.xpath("//div[2]/div[3]"));
        int freeTransfers = Integer.parseInt(table.findElement(By.xpath("//div[4]/div[1]/div")).getText());
        if (freeTransfers == 2) {
            fplUtils.removeOrRestorePlayer("Laporte");
            fplUtils.removeOrRestorePlayer("Coady");
            fplUtils.addPlayerToTeam("Cancelo");
            fplUtils.addPlayerToTeam("Thiago Silva");
        } else if (freeTransfers == 1) {
            fplUtils.removeOrRestorePlayer("Laporte");
            fplUtils.addPlayerToTeam("Thiago Silva");
        }
        int freeTransfersLeftLeft = Integer.parseInt(table.findElement(By.xpath("//div[4]/div[1]/div")).getText());
        assertThat(freeTransfersLeftLeft).isEqualTo(0);
    }

    @Test
    public void tooManyPlayersFromOneClubTest() {
        fplUtils.clickTab("Transfers");
        fplUtils.removeOrRestorePlayer("Coutinho");
        fplUtils.addPlayerToTeam("De Bruyne");
        String errortooManyPlayersFromOneClub = driver.findElement(By.className("jqqekE")).getText();
        assertThat(errortooManyPlayersFromOneClub).isEqualTo("Too many players selected from Man City");
    }

    @Test
    public void substitutionsTest(){
        fplUtils.substitution("Martinelli", "Jesus");
        assertThat(fplUtils.getPlayerByName("Martinelli", true)).isNotNull();
        assertThat(fplUtils.getPlayerByName("Jesus", false)).isNotNull();

    }

    @AfterAll
    static void afterTest() {
       // driver.quit();
    }
}