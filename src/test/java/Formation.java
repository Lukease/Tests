import java.util.ArrayList;
import java.util.Arrays;
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
            this.midfieldersIndexes = Arrays.asList(2, 4);
            this.forwardsIndexes = Arrays.asList(1, 2, 3);

        } else {
            this.midfielders = midfieldersDivsSize;
            this.forwards = 10 - (this.midfielders + this.defenders);
            this.midfieldersIndexes = new ArrayList<>();
            for (int i = 1; i <= midfielders; i++) {
                midfieldersIndexes.add(i);
            }
            if (forwards == 1) {
                this.forwardsIndexes = List.of(3);
            } else if (forwards == 2) {
                this.forwardsIndexes = List.of(2, 4);
            } else this.forwardsIndexes = List.of(1, 2, 3);

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
