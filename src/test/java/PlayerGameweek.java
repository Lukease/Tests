public class PlayerGameweek {
    private Integer points;
    private Integer minutes;
    private Integer goals;
    private Integer assists;
    private Integer cleanSheets;
    private Integer yellowCards;
    private Integer redCard;
    private Integer penaltySaves;
    private Integer bonus;
    private Integer saves;
    private Gameweek gameweek;

    public PlayerGameweek(Integer points, Integer minutes, Integer goals, Integer assists, Integer cleanSheets, Integer yellowCards, Integer redCard, Integer penaltySaves, Integer bonus, Integer saves, Gameweek gameweek) {
        this.points = points;
        this.minutes = minutes;
        this.goals = goals;
        this.assists = assists;
        this.cleanSheets = cleanSheets;
        this.yellowCards = yellowCards;
        this.redCard = redCard;
        this.penaltySaves = penaltySaves;
        this.bonus = bonus;
        this.saves = saves;
        this.gameweek = gameweek;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public Integer getGoals() {
        return goals;
    }

    public Integer getAssists() {
        return assists;
    }

    public Integer getCleanSheets() {
        return cleanSheets;
    }

    public Integer getYellowCards() {
        return yellowCards;
    }

    public Integer getRedCard() {
        return redCard;
    }

    public Integer getPenaltySaves() {
        return penaltySaves;
    }

    public Integer getBonus() {
        return bonus;
    }

    public Integer getSaves() {
        return saves;
    }

    public Gameweek getGameweek() {
        return gameweek;
    }
}
