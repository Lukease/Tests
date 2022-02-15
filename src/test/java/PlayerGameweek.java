public class PlayerGameweek {
    private Integer points;
    private Integer minutes;
    private Integer goals;
    private Integer assists;
    private Integer cleanSheets;
    private Integer yellowCards;
    private Integer redCard;
    private Integer penaltySaved;
    private Integer penaltyMissed;
    private Integer bonus;
    private Integer saves;
    private Integer ownGoal;
    private Integer gameweek;
    private Integer goalsConceded;

    public PlayerGameweek(Integer points, Integer minutes, Integer goals, Integer assists, Integer cleanSheets, Integer yellowCards, Integer redCard, Integer penaltySaved, Integer penaltyMissed, Integer bonus, Integer saves, Integer ownGoal, Integer gameweek, Integer goalsConceded) {
        this.points = points;
        this.minutes = minutes;
        this.goals = goals;
        this.assists = assists;
        this.cleanSheets = cleanSheets;
        this.yellowCards = yellowCards;
        this.redCard = redCard;
        this.penaltySaved = penaltySaved;
        this.penaltyMissed = penaltyMissed;
        this.bonus = bonus;
        this.saves = saves;
        this.ownGoal = ownGoal;
        this.gameweek = gameweek;
        this.goalsConceded = goalsConceded;
    }

    public PlayerGameweek(Integer points, Integer gameweek) {
        this.points = points;
        this.gameweek = gameweek;
    }

    public Integer getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(Integer goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getCleanSheets() {
        return cleanSheets;
    }

    public void setCleanSheets(Integer cleanSheets) {
        this.cleanSheets = cleanSheets;
    }

    public Integer getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(Integer yellowCards) {
        this.yellowCards = yellowCards;
    }

    public Integer getRedCard() {
        return redCard;
    }

    public void setRedCard(Integer redCard) {
        this.redCard = redCard;
    }

    public Integer getPenaltySaved() {
        return penaltySaved;
    }

    public void setPenaltySaved(Integer penaltySaved) {
        this.penaltySaved = penaltySaved;
    }

    public Integer getPenaltyMissed() {
        return penaltyMissed;
    }

    public void setPenaltyMissed(Integer penaltyMissed) {
        this.penaltyMissed = penaltyMissed;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getSaves() {
        return saves;
    }

    public void setSaves(Integer saves) {
        this.saves = saves;
    }

    public Integer getOwnGoal() {
        return ownGoal;
    }

    public void setOwnGoal(Integer ownGoal) {
        this.ownGoal = ownGoal;
    }

    public Integer getGameweek() {
        return gameweek;
    }

    public void setGameweek(Integer gameweek) {
        this.gameweek = gameweek;
    }
}