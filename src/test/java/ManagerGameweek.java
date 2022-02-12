import java.util.List;

public class ManagerGameweek {
    private Integer points;
    private Integer rank;
    private Integer transfersMade;
    private List<Player> team;

    public ManagerGameweek(Integer points, Integer rank, Integer transfersMade, List<Player> team) {
        this.points = points;
        this.rank = rank;
        this.transfersMade = transfersMade;
        this.team = team;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getTransfersMade() {
        return transfersMade;
    }

    public void setTransfersMade(Integer transfersMade) {
        this.transfersMade = transfersMade;
    }

    public List<Player> getTeam() {
        return team;
    }

    public void setTeam(List<Player> team) {
        this.team = team;
    }
}
