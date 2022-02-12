public class FplLeague {
    private String leagueName;
    private String teamName;
    private int rank;
    private int gameWeekPoints;
    private int totalPoints;
    private String manager;


    public FplLeague(String leagueName, String teamName, int rank, int gameWeekPoints, int totalPoints, String manager) {
        this.leagueName = leagueName;
        this.teamName = teamName;
        this.rank = rank;
        this.gameWeekPoints = gameWeekPoints;
        this.totalPoints = totalPoints;
        this.manager = manager;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getGameWeekPoints() {
        return gameWeekPoints;
    }

    public void setGameWeekPoints(int gameWeekPoints) {
        this.gameWeekPoints = gameWeekPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
