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
    List<Player> bench = new ArrayList<>();
    WebDriver driver;
    HashMap<String, List<Integer>> positionsIndexes = new HashMap<>();
    List<String> formationsOrder = new ArrayList<>();
    List<Formation> teamFormations = new ArrayList<>();
    private final String GOALKEEPER = "Goalkeeper";
    private final String DEFENDER = "Defender";
    private final String MIDFIELDER = "Midfielder";
    private final String FORWARD = "Forward";
    private final String BENCH = "Bench";
    JavascriptExecutor jse;
    Formation currentFormation;

    public HashMap<String, List<Player>> getMyTeam() {
        return myTeam;
    }

    public List<String> getFormationsOrder() {
        return formationsOrder;
    }

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
        Player player = getPlayerByName(playerName, true);
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-testid='pitch']/div[" + (formationsOrder.indexOf(player.getPosition()) + 1) + "]/div[" +
                +player.getPlayerIndex() + "]/div/div/div/div/button")));
        driver.findElement(By.xpath("//div[@data-testid='pitch']/div[" + (formationsOrder.indexOf(player.getPosition()) + 1) + "]/div[" +
                +player.getPlayerIndex() + "]/div/div/div/div/button")).click();
    }

    public void substitution(String playerInName, String playerOutName) {
        Player playerIn = getPlayerByName(playerInName, false);
        Player playerOut = getPlayerByName(playerOutName, true);
        driver.findElement(By.xpath("//div[@data-testid='pitch']/div[" + (formationsOrder.indexOf(playerOut.getPosition()) + 1) + "]/div[" +
                +playerOut.getPlayerIndex() + "]/div/div/div/div/button")).click();
        List<WebElement> bench = driver.findElements(By.className("eFjEEk"));
        bench.get(playerIn.getPlayerIndex()-1).findElements(By.className("fBGUqJ")).get(1).click();
        driver.findElement(By.className("csDFXJ")).click();
        getTeam();
    }

    public void makeCaptain(String capitainName) {
        Player capitan = getPlayerByName(capitainName, true);
        driver.findElement(By.xpath("//div[@data-testid='pitch']/div[" + (formationsOrder.indexOf(capitan.getPosition()) + 1) + "]/div[" +
                +capitan.getPlayerIndex() + "]/div/div/button")).click();
        driver.findElement(By.xpath("//div[@role='document']/div[2]/ul/li[2]/button")).click();
    }

    public void makeViceCaptain(String viceCapitainName) {
        Player viceCapitain = getPlayerByName(viceCapitainName, true);
        driver.findElement(By.xpath("//div[@data-testid='pitch']/div[" + (formationsOrder.indexOf(viceCapitain.getPosition()) + 1) + "]/div[" +
                +viceCapitain.getPlayerIndex() + "]/div/div/button")).click();
        driver.findElement(By.xpath("//div[@role='document']/div[2]/ul/li[3]/button")).click();
    }

    public void addPlayerToTeam(String addPlayerName) {
        WebElement search = driver.findElement(By.id("search"));
        search.clear();
        search.sendKeys(addPlayerName);
        driver.findElement(By.className("kGMjuJ")).findElement(By.xpath("//td[2]/button")).click();
        driver.findElement(By.xpath("//div[@role='document']/div[2]/ul/li[1]/button")).click();
    }

    public void sortPlayerBy(String sortBy) {
        clickTab("Transfers");
        new Select(driver.findElement(By.id("sort"))).selectByIndex(playerSelectBy.get(sortBy));
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
        initiateTeam();
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

    public Player getPlayerByName(String name, boolean onPitch) throws PlayerNotFoundException {
        if (onPitch) {
            return players.stream().filter(player -> player.getName().equals(name)).findFirst().orElseThrow(() -> new PlayerNotFoundException("Player " + name + " not found"));
        } else {
            return bench.stream().filter(player -> player.getName().equals(name)).findFirst().orElseThrow(() -> new PlayerNotFoundException("Player " + name + " not found"));
        }
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
        WebElement benchPath = driver.findElement(By.className("blddiH"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(pitch.findElement(By.className("Pitch__ElementRow-sc-1mctasb-1"))));
        List<WebElement> formations = pitch.findElements(By.className("Pitch__ElementRow-sc-1mctasb-1"));
        List<WebElement> bench = benchPath.findElements(By.className("iAuEaL"));
        List<WebElement> goalkeeperDivs = formations.get(0).findElements(By.className("gAzdNx"));
        List<WebElement> defendersDivs = formations.get(1).findElements(By.className("gAzdNx"));
        List<WebElement> midfieldersDivs = formations.get(2).findElements(By.className("gAzdNx"));
        List<WebElement> forwardsDivs = formations.get(3).findElements(By.className("gAzdNx"));
        List<WebElement> benchDivs = bench.get(0).findElements(By.className("eFjEEk"));
        currentFormation = new Formation(defendersDivs.size(), midfieldersDivs.size(), forwardsDivs.size());

        List<Player> goalkeepers = new ArrayList<>();
        String goalkeeperName = goalkeeperDivs.get(2).findElement(By.className("PitchElementData__ElementName-sc-1u4y6pr-0")).getText();
        Player goalkeeper = getPlayerByName(goalkeeperName, true);
        goalkeepers.add(new Player(goalkeeper.getPrice(), goalkeeperName, GOALKEEPER, goalkeeper.getClub(), 3));
        myTeam.put(GOALKEEPER, goalkeepers);

        List<Player> defenders = new ArrayList<>();
        for (int i = 0; i < currentFormation.getDefenders(); i++) {
            String playerName = defendersDivs.get(i).findElement(By.className("PitchElementData__ElementName-sc-1u4y6pr-0")).getText();
            Player player = getPlayerByName(playerName, true);
            int playerIndex = i + 1;
            defenders.add(new Player(player.getPrice(), playerName, DEFENDER, player.getClub(), playerIndex));
        }
        myTeam.put(DEFENDER, defenders);

        List<Player> midfielders = new ArrayList<>();
        for (int i = 0; i < currentFormation.getMidfielders(); i++) {
            String playerName = midfieldersDivs.get(currentFormation.getMidfieldersIndexes().get(i) - 1).findElement(By.className("PitchElementData__ElementName-sc-1u4y6pr-0")).getText();
            Player player = getPlayerByName(playerName, true);
            int playerIndex = currentFormation.getMidfieldersIndexes().get(i);
            midfielders.add(new Player(player.getPrice(), playerName, MIDFIELDER, player.getClub(), playerIndex));
        }
        myTeam.put(MIDFIELDER, midfielders);

        List<Player> forwards = new ArrayList<>();
        for (int i = 0; i < currentFormation.getForwards(); i++) {
            String playerName = forwardsDivs.get(currentFormation.getForwardsIndexes().get(i) - 1).findElement(By.className("PitchElementData__ElementName-sc-1u4y6pr-0")).getText();
            Player player = getPlayerByName(playerName, true);
            int playerIndex = i + 1;
            forwards.add(new Player(player.getPrice(), playerName, FORWARD, player.getClub(), playerIndex));
        }
        myTeam.put(FORWARD, forwards);

        List<Player> benchsPlayer = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String playerName = benchDivs.get(i).findElement(By.className("PitchElementData__ElementName-sc-1u4y6pr-0")).getText();
            Player player = getPlayerByName(playerName, true);
            int playerIndex = i + 1;
            benchsPlayer.add(new Player(player.getPrice(), playerName, player.getPosition(), player.getClub(), playerIndex));
        }
        this.bench = benchsPlayer;
        myTeam.put(BENCH, benchsPlayer);
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
