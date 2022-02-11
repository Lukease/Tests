public class Player {
    private double price;
    private  String name;
    private String position;
    private String club;
    private int playerIndex;


    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public Player(double price, String name, String position, String club, int playerIndex) {
        this.price = price;
        this.name = name;
        this.position = position;
        this.club = club;
        this.playerIndex = playerIndex;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }
}