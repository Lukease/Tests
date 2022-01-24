import java.util.List;

public class Formation {
    private int defenders;
    private int midfielders;
    private int forwards;
    private List<Integer> midfieldersIndexes;
    private List<Integer> forwardsIndexes;

    public List<Integer> getMidfieldersIndexes() {
        return midfieldersIndexes;
    }

    public void setMidfieldersIndexes(List<Integer> midfieldersIndexes) {
        this.midfieldersIndexes = midfieldersIndexes;
    }

    public List<Integer> getForwardsIndexes() {
        return forwardsIndexes;
    }

    public void setForwardsIndexes(List<Integer> forwardsIndexes) {
        this.forwardsIndexes = forwardsIndexes;
    }

    public Formation(int defendersDivsSize, int midfieldersDivsSize, int forwardsDivsSize) {
        this.defenders = defendersDivsSize;
        if (midfieldersDivsSize == 5 && forwardsDivsSize == 3) {
            this.midfielders = 2;
            this.forwards = 3;
        } else {
            this.midfielders = midfieldersDivsSize;
            this.forwards = 10 - (this.midfielders + this.defenders);
        }
        System.out.println(this.defenders + "" + this.midfielders + "" + this.forwards);
        this.midfieldersIndexes = midfieldersIndexes;
        this.forwardsIndexes = forwardsIndexes;
    }

    public int getDefenders() {
        return defenders;
    }

    public void setDefenders(int defenders) {
        this.defenders = defenders;
    }

    public int getMidfielders() {
        return midfielders;
    }

    public void setMidfielders(int midfielders) {
        this.midfielders = midfielders;
    }

    public int getForwards() {
        return forwards;
    }

    public void setForwards(int forwards) {
        this.forwards = forwards;
    }

}
