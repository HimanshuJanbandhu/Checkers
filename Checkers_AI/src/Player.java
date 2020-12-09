public class Player{

    String name;

    public Player(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((Player)obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}