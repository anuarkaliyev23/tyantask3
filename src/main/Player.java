package main;

import java.util.Objects;

public class Player {
    private BoardView own;
    private BoardView enemy;


    public Player(Board own, Board enemy) {
        this.own = new BoardView(own, true);
        this.enemy = new BoardView(enemy, false);
    }

    public BoardView getOwn() {
        return own;
    }

    public BoardView getEnemy() {
        return enemy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(own, player.own) &&
                Objects.equals(enemy, player.enemy);
    }

    @Override
    public int hashCode() {

        return Objects.hash(own, enemy);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OWN: \n")
                .append(own)
                .append("ENEMY: \n")
                .append(enemy);
        return builder.toString();
    }
}
