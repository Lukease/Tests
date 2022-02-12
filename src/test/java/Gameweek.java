import java.util.List;

public class Gameweek {
    private Integer averagePoints;
    private Integer highestPoints;
    private List<Player> teamOfTheGameweek;
    private Integer number;

    public Gameweek(Integer averagePoints, Integer highestPoints, List<Player> teamOfTheGameweek, Integer number) {
        this.averagePoints = averagePoints;
        this.highestPoints = highestPoints;
        this.teamOfTheGameweek = teamOfTheGameweek;
        this.number = number;
    }

    public Integer getAveragePoints() {
        return averagePoints;
    }

    public void setAveragePoints(Integer averagePoints) {
        this.averagePoints = averagePoints;
    }

    public Integer getHighestPoints() {
        return highestPoints;
    }

    public void setHighestPoints(Integer highestPoints) {
        this.highestPoints = highestPoints;
    }

    public List<Player> getTeamOfTheGameweek() {
        return teamOfTheGameweek;
    }

    public void setTeamOfTheGameweek(List<Player> teamOfTheGameweek) {
        this.teamOfTheGameweek = teamOfTheGameweek;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
