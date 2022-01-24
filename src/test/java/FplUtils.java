import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FplUtils {
    HashMap<String, List<Player>> myTeam = new HashMap<>();
    final int TABS_NUMBER = 13;
    HashMap<String, Integer> tabIndexes = new HashMap<>();
    HashMap<String, Integer> playerSelectBy = new HashMap<>();
    HashMap<String, Integer> playerName = new HashMap<>();
    List<Player> players = new ArrayList<>();
    WebDriver driver;
    HashMap<String, List<Integer>> positionsIndexes = new HashMap<>();
    List<String> formationsOrder = new ArrayList<>();
    List<Formation> teamFormations = new ArrayList<>();
    private final String GOALKEEPER = "Goalkeeper";
    private final String DEFENDER = "Defender";
    private final String MIDFIELDER = "Midfielder";
    private final String FORWARD = "Forward";
    JavascriptExecutor jse;
    Formation currentFormation;

    public HashMap<String, Integer> getTabIndexes() {
        return tabIndexes;
    }

    public void clickTab(String tab) {
        jse.executeScript("window.scrollBy(0,-1000)");
        WebElement button = driver.findElement(By.xpath("//nav[@role='navigation']/ul/li[" + tabIndexes.get(tab) + "]/a"));
        if (!button.getDomProperty("href").equals(driver.getCurrentUrl())) {
            button.click();
        }
    }

    public void vievPlayersBy(String selectBy) {
        clickTab("Transfers");
        new Select(driver.findElement(By.id("filter"))).selectByIndex(playerSelectBy.get(selectBy));
    }

    public void removeOrRestorePlayer(String playerName) {
        Player player = getPlayerByName(playerName);
        driver.findElement(By.xpath("//div[@data-testid='pitch']/div[" + (formationsOrder.indexOf(player.getPosition()) + 1) + "]/div[" +
                +player.getPlayerIndex() + "]/div/div/div/div/button")).click();
    }

    public void substitution(String playerIn, String playerOut) {
        Player player1 = getPlayerByName(playerIn);
        driver.findElement(By.className("blddiH")).findElement(By.xpath("//div/div[" + (player1.getPlayerIndex()) + "]/div[1]/div/div/div[1]")).click();
        driver.findElement(By.xpath("//div[@role='document']/div[2]/ul/li/button")).click();
        Player player2 = getPlayerByName(playerOut);
        driver.findElement(By.xpath("//div[@data-testid='pitch']/div[" + (formationsOrder.indexOf(player2.getPosition()) + 1) + "]/div[" +
                +player2.getPlayerIndex() + "]/div/div/div/div/button")).click();
        //Todo: zrob dokonywanie zmian
    }

    public void makeCaptain(String name) {
        Player capitan = getPlayerByName(name);
        driver.findElement(By.xpath("//div[@data-testid='pitch']/div[" + (formationsOrder.indexOf(capitan.getPosition()) + 1) + "]/div[" +
                +capitan.getPlayerIndex() + "]/div/div/button")).click();
        driver.findElement(By.xpath("//div[@role='document']/div[2]/ul/li[2]/button")).click();
        //Todo: zrob wybieranie kapitana
    }

    public void makeViceCaptain(String name) {
        Player viceCapitain = getPlayerByName(name);
        driver.findElement(By.xpath("//div[@data-testid='pitch']/div[" + (formationsOrder.indexOf(viceCapitain.getPosition()) + 1) + "]/div[" +
                +viceCapitain.getPlayerIndex() + "]/div/div/button")).click();
        driver.findElement(By.xpath("//div[@role='document']/div[2]/ul/li[3]/button")).click();
        //Todo: zrob wybieranie vice kapitana
    }

    public void addPlayerToTeam(String name) {
        //Todo: Dodaj zawodnika do dryzyny
    }

    public void sortPlayerBy(String sortBy) {
        clickTab("Transfers");
        new Select(driver.findElement(By.id("sort"))).selectByIndex(playerSelectBy.get(sortBy));
        //Todo: posortuj zawodnikow po
    }

    public void searchPlayer(String name) {
        clickTab("Transfers");
        driver.findElement(By.id("search")).sendKeys(name);
        //Todo: szukanie zawodnika po nazwie
    }

    public void maxCostOfPlayer(double cost) {
        clickTab("Transfers");
        new Select(driver.findElement(By.id("maxCost"))).selectByIndex(playerSelectBy.get(cost));
        //Todo: wybierz cene
    }

    public FplUtils(WebDriver driver) {
        this.driver = driver;
        this.jse = (JavascriptExecutor) driver;
        initiateTabIndexes();
        initiatePlayerSelectBy();
        positionsIndexes.put(GOALKEEPER, Arrays.asList(2, 3));
        positionsIndexes.put(DEFENDER, Arrays.asList(1, 2, 3, 4, 5));
        positionsIndexes.put(MIDFIELDER, Arrays.asList(1, 2, 3, 4, 5));
        positionsIndexes.put(FORWARD, Arrays.asList(1, 2, 3));
        formationsOrder = Arrays.asList(GOALKEEPER, DEFENDER, MIDFIELDER, FORWARD);
        //initiateTeam();
        getTeam();
    }

    private void initiateTeam() {
        clickTab("Transfers");
        WebElement pitch = driver.findElement(By.xpath("//div[@data-testid='pitch']"));
        getPlayers(pitch);
    }

    private void initiatePlayerSelectBy() {
        clickTab("Transfers");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filter")));
        Select playerCharacteristic = new Select(driver.findElement(By.id("filter")));
        List<WebElement> options = playerCharacteristic.getOptions();
        for (int i = 0; i < options.size(); i++)
            playerSelectBy.put(options.get(i).getText(), i);

    }

    private void initiateTabIndexes() {

        List<WebElement> tabs = null;
        do {
            tabs = driver.findElements(By.xpath("//nav[@role='navigation']/ul/li"));
        } while (tabs.size() != TABS_NUMBER);
        for (int i = 0; i < tabs.size(); i++)
            tabIndexes.put(tabs.get(i).findElement(By.tagName("a")).getText(), i + 1);
    }

    private Player getPlayerByName(String name) throws PlayerNotFoundException {
        return players.stream().filter(player -> player.getName().equals(name)).findFirst().orElseThrow(() -> new PlayerNotFoundException("Player " + name + " not found"));
    }

    private void getPlayers(WebElement pitch) {
        int formationIndex = 1;
        for (String formation : formationsOrder) {
            for (int i : positionsIndexes.get(formation)) {
                System.out.println(formationIndex + " " + i);
                WebElement player = pitch.findElement(By.xpath("//div[" + formationIndex + "]/div[" + i + "]/div/div/button/div"));
                List<WebElement> playerElement = player.findElements(By.tagName("div"));
                String playerName = playerElement.get(0).getText();
                double playerPrice1 = Double.parseDouble(playerElement.get(1).getText());
                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[" + formationIndex + "]/div[" + i + "]/div/div/div/div[2]/button")));
                player.findElement(By.xpath("//div[" + formationIndex + "]/div[" + i + "]/div/div/div/div[2]/button")).click();
                String playerClub1 = driver.findElement(By.className("dUWnSR")).getText();
                players.add(new Player(playerPrice1, playerName, formation, playerClub1, i));
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.elementToBeClickable(By.className("ejzwPB")));
                driver.findElement(By.className("ejzwPB")).click();
            }
            formationIndex++;
        }
    }

    private void getTeam() {
        clickTab("Pick Team");
        WebElement pitch = driver.findElement(By.xpath("//div[@data-testid='pitch']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(pitch.findElement(By.className("Pitch__ElementRow-sc-1mctasb-1"))));
        List<WebElement> formations = pitch.findElements(By.className("Pitch__ElementRow-sc-1mctasb-1"));
        List<WebElement> defendersDivs = formations.get(1).findElements(By.className("gAzdNx"));
        List<WebElement> midfieldersDivs = formations.get(2).findElements(By.className("gAzdNx"));
        List<WebElement> forwardsDivs = formations.get(3).findElements(By.className("gAzdNx"));
        currentFormation = new Formation(defendersDivs.size(), midfieldersDivs.size(), forwardsDivs.size());

        List<Player> defenders = new ArrayList<>();
        for (int i = 0; i < currentFormation.getDefenders(); i++) {
            defendersDivs.get(i).findElement(By.xpath("./div/div/div/div[2]/button")).click();
            double playerPrice = Double.parseDouble(driver.findElement(By.xpath("//div[@role='document']/div[2]/div/ul/li[4]/div")).getText().replace("£", ""));
            String playerName = driver.findElement(By.xpath("//div[@role='document']/div[2]/div/div/div/div/h2")).getText();
            String playerClub = driver.findElement(By.xpath("//div[@role='document']/div[2]/div/div/div/div/div")).getText();
            driver.findElement(By.xpath("//div[@role='document']/div/div[2]/button")).click();
            int playerIndex = i + 1;
            defenders.add(new Player(playerPrice, playerName, DEFENDER, playerClub, playerIndex));
        }
        myTeam.put(DEFENDER,defenders);
    }

    /*private void initializeFormations() {
        teamFormations.add(new Fromation(3, 5, 2, Arrays.asList(1, 2, 3, 4, 5), Arrays.asList(2, 4)));
        teamFormations.add(new Fromation(3, 4, 3, Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 2, 3)));
        teamFormations.add(new Fromation(4, 3, 3, Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3)));
        teamFormations.add(new Fromation(4, 4, 2, Arrays.asList(1, 2, 3, 4), Arrays.asList(2, 4)));
        teamFormations.add(new Fromation(4, 5, 1, Arrays.asList(1, 2, 3, 4, 5), Arrays.asList(3)));
        teamFormations.add(new Fromation(5, 2, 3, Arrays.asList(2, 4), Arrays.asList(1, 2, 3)));
        teamFormations.add(new Fromation(5, 3, 2, Arrays.asList(1, 2, 3), Arrays.asList(2, 4)));
        teamFormations.add(new Fromation(5, 4, 1, Arrays.asList(1, 2, 3, 4), Arrays.asList(3)));
    }

     */

}
