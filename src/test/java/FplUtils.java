import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
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
    private final Integer GAMEWEEKS_PLAYED = 24;
    HashMap<String, Player> allPlayers = new HashMap<>();
    private Constants constants = new Constants();

    public void getManagerGameweekInformation(Integer gameweek, TeamManager manager) {
        driver.get("https://fantasy.premierleague.com/entry/" + manager.getId() + "/event/" + gameweek);
        Integer points = Integer.parseInt(driver.findElement(By.className("EntryEvent__PrimaryValue-l17rqm-4")).getText().split("\n")[0]);
        Integer transfers = Integer.parseInt(driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div[3]/div/div[2]/div[2]/div[2]/div/a")).getText());
        Integer rank = Integer.parseInt(driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div[3]/div/div[2]/div[2]/div[2]/div/a")).getText());
        List<Player> pitchTeam = getListViewTeam(gameweek);
        manager.addManagerGameweek(new ManagerGameweek(points, rank, transfers, pitchTeam, pitchTeam.get(0)));
    }

    public void getPlayerGameweekAllInformation(String playerName, Integer gameweekNumber) {
        List<PlayerGameweek> playerStatistic = new ArrayList<>();
        clickTab("Transfers");
        WebElement search = driver.findElement(By.id("search"));
        search.clear();
        search.sendKeys(playerName);
        driver.findElement(By.className("kGMjuJ")).findElement(By.xpath("//td[1]/button")).click();
        Integer minutesPlayed = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[4]")).getText());
        if (minutesPlayed == 0) {
            System.out.println(playerName + " nie grał w kolejce numer " + gameweekNumber);
        } else {
            List<WebElement> body = driver.findElements(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr"));

            Integer points = Integer.parseInt((driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[3]")).getText()));
            Integer goals = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[5]")).getText());
            Integer asissts = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[6]")).getText());
            Integer cleanSheets = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[7]")).getText());
            Integer goalsConceded = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[8]")).getText());
            Integer ownGoals = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[9]")).getText());
            Integer penaltiesSaved = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[10]")).getText());
            Integer penaltiesMissed = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[11]")).getText());
            Integer yellowCards = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[12]")).getText());
            Integer redCards = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[13]")).getText());
            Integer saves = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[14]")).getText());
            Integer bonus = Integer.parseInt(driver.findElement(By.xpath("//div[@role='document']/div[2]/div[2]/div/div/div[1]/div/table/tbody/tr[" + gameweekNumber + "]/td[15]")).getText());
            playerStatistic.add(new PlayerGameweek(points, minutesPlayed, goals, asissts, cleanSheets, yellowCards, redCards, penaltiesSaved, penaltiesMissed, bonus, saves, ownGoals, gameweekNumber, goalsConceded));
        }
    }

    public void getLeagueInformation(Integer leagueNumber) {
        driver.get("https://fantasy.premierleague.com/leagues/" + leagueNumber + "/standings/c");
        String leagueName = driver.findElement(By.className("Title-sc-9c7mfn-0")).getText();
        List<FplLeague> teamList = new ArrayList<>();
        List<WebElement> leagueTable = driver.findElements(By.className("StandingsRow-fwk48s-0"));
        for (int i = 0; i < leagueTable.size(); i++) {
            String teamName = leagueTable.get(i).findElement(By.className("cA-DQBw")).getText();
            Integer totalPoints = Integer.parseInt(driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div/table/tbody/tr[" + (i + 1) + "]/td[4]")).getText());
            Integer rank = Integer.parseInt(driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div/table/tbody/tr[" + (i + 1) + "]/td/div/div")).getText());
            Integer gameweekPoints = Integer.parseInt(driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div/table/tbody/tr[" + (i + 1) + "]/td[3]")).getText());
            String managerName = driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div/table/tbody/tr[" + (i + 1) + "]/td[2]")).getText().split("\n")[1];
            teamList.add(new FplLeague(leagueName, teamName, rank, gameweekPoints, totalPoints, managerName));
        }
    }

    public void getGameweekInfo(Integer numberOfGameweek) {
        driver.get("https://fantasy.premierleague.com/entry/3459998/event/" + numberOfGameweek);
        List<Player> playersOfGameweek = new ArrayList<>();
        Integer averageGameweekPoints = Integer.parseInt(driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div[3]/div/div[2]/div[1]/div[1]/div")).getText());
        Integer highestGameweekPoints = Integer.parseInt(driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div[3]/div/div[2]/div[1]/div[2]/div")).getText());
        driver.get("https://fantasy.premierleague.com/dream-team/" + numberOfGameweek);
        playersOfGameweek = getPitchTeam();
        Gameweek gameweek = new Gameweek(averageGameweekPoints, highestGameweekPoints, playersOfGameweek, numberOfGameweek);
        System.out.println(gameweek);
    }

    public void getAllManagersAllGameweeksInfo() {
        driver.get("https://fantasy.premierleague.com/leagues/30013/standings/c");
        List<WebElement> leagueTable = driver.findElements(By.className("StandingsRow-fwk48s-0"));
        List<TeamManager> teamManagerList = new ArrayList<>();
        for (int i = 0; i < leagueTable.size(); i++) {
            String teamName = leagueTable.get(i).findElement(By.className("cA-DQBw")).getText();
            String managerName = driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div/table/tbody/tr[" + (i + 1) + "]/td[2]")).getText().split("\n")[1];
            Integer totalPoints = Integer.parseInt(driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div/table/tbody/tr[" + (i + 1) + "]/td[4]")).getText());
            String id = driver.findElement(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div/table/tbody/tr[" + (i + 1) + "]/td[2]/a")).getAttribute("href").split("/")[4];
            teamManagerList.add(new TeamManager(teamName, managerName, totalPoints, id));
        }
        for (int managerIndex = 0; managerIndex < leagueTable.size(); managerIndex++) {
            for (int gameweekIndex = 1; gameweekIndex <= GAMEWEEKS_PLAYED; gameweekIndex++) {
                getManagerGameweekInformation(gameweekIndex, teamManagerList.get(managerIndex));
            }
        }
    }

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
        bench.get(playerIn.getPlayerIndex() - 1).findElements(By.className("fBGUqJ")).get(1).click();
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
        /*initiatePlayerSelectBy();
        positionsIndexes.put(GOALKEEPER, Arrays.asList(2, 3));
        positionsIndexes.put(DEFENDER, Arrays.asList(1, 2, 3, 4, 5));
        positionsIndexes.put(MIDFIELDER, Arrays.asList(1, 2, 3, 4, 5));
        positionsIndexes.put(FORWARD, Arrays.asList(1, 2, 3));
        formationsOrder = Arrays.asList(GOALKEEPER, DEFENDER, MIDFIELDER, FORWARD);
        initiateTeam();
        getTeam();*/
        getAllManagersAllGameweeksInfo();
        getGameweekInfo(23);
        getLeagueInformation(30013);
        getPlayerGameweekAllInformation("Sterling", 9);

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

    private List<Player> getListViewTeam(Integer gameweek) {
        List<Player> playersGameweek = new ArrayList<>();
        driver.findElement(By.className("xSQCR")).click();
        List<WebElement> table = driver.findElements(By.xpath("//div[@id='root']/div[2]/div[2]/div[1]/div[4]/div/div/div/div/div[1]/table/tbody/tr"));
        for (int i = 0; i <= table.size(); i++) {
            List<WebElement> attributes = table.get(i).findElements(By.tagName("td"));
            String name = attributes.get(2).findElement(By.className("dwvEEF")).getText();
            Player player;
            if (allPlayers.containsKey(name)) {
                player = allPlayers.get(name);

            } else {
                List<WebElement> spans = attributes.get(2).findElements(By.tagName("span"));
                String club = constants.getClubByShortcut(spans.get(0).getText());
                String position = constants.getPositionByShortcut(spans.get(1).getText());
                player = new Player(name, position, club);
                allPlayers.put(name, player);
            }
            if (player.getPlayerGameweek(gameweek) == null) {
                boolean isCaptain = !attributes.get(1).findElements(By.className("guYEoa")).isEmpty();
                int points = Integer.parseInt(attributes.get(3).getText());
                if (isCaptain) {
                    points = points / 2;
                }
                Integer minutes = Integer.parseInt(attributes.get(4).getText());
                Integer goals = Integer.parseInt(attributes.get(5).getText());
                Integer assists = Integer.parseInt(attributes.get(6).getText());
                Integer cleanSheets = Integer.parseInt(attributes.get(7).getText());
                Integer goalsConceded = Integer.parseInt(attributes.get(8).getText());
                Integer ownGoals = Integer.parseInt(attributes.get(9).getText());
                Integer penaltiesSaved = Integer.parseInt(attributes.get(10).getText());
                Integer penaltiesMissed = Integer.parseInt(attributes.get(11).getText());
                Integer yellowCards = Integer.parseInt(attributes.get(12).getText());
                Integer redCards = Integer.parseInt(attributes.get(13).getText());
                Integer saves = Integer.parseInt(attributes.get(14).getText());
                Integer bonus = Integer.parseInt(attributes.get(15).getText());

                player.addPlayerGameweek(new PlayerGameweek(points, minutes, goals, assists, cleanSheets, yellowCards, redCards, penaltiesSaved, penaltiesMissed, bonus, saves, ownGoals, gameweek, goalsConceded));
            }
        }
        return playersGameweek;
    }

    private List<Player> getPitchTeam() {
        WebElement pitch = driver.findElement(By.xpath("//div[@data-testid='pitch']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(pitch.findElement(By.className("Pitch__ElementRow-sc-1mctasb-1"))));
        List<WebElement> formations = pitch.findElements(By.className("Pitch__ElementRow-sc-1mctasb-1"));
        List<WebElement> goalkeeperDivs = formations.get(0).findElements(By.className("gAzdNx"));
        List<WebElement> defendersDivs = formations.get(1).findElements(By.className("gAzdNx"));
        List<WebElement> midfieldersDivs = formations.get(2).findElements(By.className("gAzdNx"));
        List<WebElement> forwardsDivs = formations.get(3).findElements(By.className("gAzdNx"));
        currentFormation = new Formation(defendersDivs.size(), midfieldersDivs.size(), forwardsDivs.size());

        List<Player> team = new ArrayList<>();
        String goalkeeperName = goalkeeperDivs.get(2).findElement(By.className("PitchElementData__ElementName-sc-1u4y6pr-0")).getText();
        Integer goalkeeperPoints = Integer.parseInt(goalkeeperDivs.get(2).findElement(By.className("PitchElementData__ElementValue-sc-1u4y6pr-1")).getText().split(",")[0]);
        Player player = new Player(goalkeeperName, GOALKEEPER);
        player.addPlayerGameweek(new PlayerGameweek(goalkeeperPoints, 1));
        team.add(new Player(goalkeeperName, GOALKEEPER));


        for (int i = 0; i < currentFormation.getDefenders(); i++) {
            String playerName = defendersDivs.get(i).findElement(By.className("PitchElementData__ElementName-sc-1u4y6pr-0")).getText();
            String value = defendersDivs.get(i).findElement(By.className("PitchElementData__ElementValue-sc-1u4y6pr-1")).getText().split(",")[0];
            Integer defenderPoints = Integer.parseInt(value);
            player = new Player(goalkeeperName, GOALKEEPER);
            player.addPlayerGameweek(new PlayerGameweek(defenderPoints, 1));
            team.add(new Player(playerName, DEFENDER));
        }

        for (int i = 0; i < currentFormation.getMidfielders(); i++) {
            String playerName = midfieldersDivs.get(currentFormation.getMidfieldersIndexes().get(i) - 1).findElement(By.className("PitchElementData__ElementName-sc-1u4y6pr-0")).getText();
            Integer midfielderPoints = Integer.parseInt(midfieldersDivs.get(currentFormation.getMidfieldersIndexes().get(i) - 1).findElement(By.className("PitchElementData__ElementValue-sc-1u4y6pr-1")).getText().split(",")[0]);
            player = new Player(goalkeeperName, GOALKEEPER);
            player.addPlayerGameweek(new PlayerGameweek(midfielderPoints, 1));
            team.add(new Player(playerName, MIDFIELDER));
        }

        for (int i = 0; i < currentFormation.getForwards(); i++) {
            String playerName = forwardsDivs.get(currentFormation.getForwardsIndexes().get(i) - 1).findElement(By.className("PitchElementData__ElementName-sc-1u4y6pr-0")).getText();
            Integer forwardPoints = Integer.parseInt(forwardsDivs.get(currentFormation.getForwardsIndexes().get(i) - 1).findElement(By.className("PitchElementData__ElementValue-sc-1u4y6pr-1")).getText().split(",")[0]);
            player = new Player(goalkeeperName, GOALKEEPER);
            player.addPlayerGameweek(new PlayerGameweek(forwardPoints, 1));
            team.add(new Player(playerName, FORWARD));
        }
        return team;
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
