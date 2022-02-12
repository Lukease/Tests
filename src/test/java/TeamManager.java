public class TeamManager {
    private String teamName;
    private String managerName;
    private Integer totalPoints;
    private String id;

    public TeamManager(String teamName, String managerName,  Integer totalPoints, String id) {
        this.teamName = teamName;
        this.managerName = managerName;
        this.totalPoints = totalPoints;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
}
