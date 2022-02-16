import java.util.HashMap;

public class Constants {
    private HashMap<String, String> clubNames = new HashMap<>();
    private HashMap<String, String> playerPositions = new HashMap<>();

    public Constants() {
        this.clubNames.put("LIV", "Liverpool");
        this.clubNames.put("MCI", "Manchester City");
        this.clubNames.put("AVL", "Aston Villa");
        this.clubNames.put("WHU", "West Ham");
        this.clubNames.put("WAT", "Watford");
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
