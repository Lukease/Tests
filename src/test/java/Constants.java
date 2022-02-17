import java.util.HashMap;

public class Constants {
    private HashMap<String, String> clubNames = new HashMap<>();
    private HashMap<String, String> playerPositions = new HashMap<>();

    public Constants() {
        this.clubNames.put("LIV", "Liverpool");
        this.clubNames.put("MCI", "Man City");
        this.clubNames.put("AVL", "Aston Villa");
        this.clubNames.put("WHU", "West Ham");
        this.clubNames.put("WOL", "Wolves");
        this.clubNames.put("WAT", "Watford");
        this.clubNames.put("TOT", "Spurs");
        this.clubNames.put("SOU", "Southampton");
        this.clubNames.put("NOR", "Norwich");
        this.clubNames.put("NEW", "Newcastle");
        this.clubNames.put("MUN", "Man Udt");
        this.clubNames.put("LEI", "Leicester");
        this.clubNames.put("LEE", "Leeds");
        this.clubNames.put("EVE", "Everton");
        this.clubNames.put("CRY", "Crystal Palace");
        this.clubNames.put("CHE", "Chelsea");
        this.clubNames.put("BUR", "Burnley");
        this.clubNames.put("BHA", "Brighton");
        this.clubNames.put("BRE", "Brentford");
        this.clubNames.put("ARS", "Arsenal");
        this.playerPositions.put("GKP", "Goalkeeper");
        this.playerPositions.put("DEF", "Defender");
        this.playerPositions.put("MID", "Miedfielder");
        this.playerPositions.put("FWD", "Forward");
    }

    public String getClubByShortcut(String shortcut) {
        return clubNames.get(shortcut);
    }

    public String getPositionByShortcut(String shortcut) {
        return playerPositions.get(shortcut);
    }
}
