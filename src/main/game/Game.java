package main.game;

import main.players.Player;
import main.boards.Board;
import main.exceptions.TurnAfterFinishException;
import main.exceptions.UnhandledGameStatusException;

import java.util.Objects;

public class Game {
    private Player ai;
    private Player player;
    private boolean playerTurn;
    private int turnNumber;

    public Game() {
        restart();
    }

    public void restart() {
        Board playerBoard = new Board();
        Board aiBoard = new Board();

        player = new Player(playerBoard, aiBoard);
        ai = new Player(aiBoard, playerBoard);
        playerTurn = true;
        turnNumber = 0;
    }

    public void makeTurn(int x, int y) {
        if (getStatus() != GameStatus.IN_PROCESS) throw new TurnAfterFinishException();

        boolean wasHit;
        if (playerTurn) {
            ai.getOwn().hit(x, y);
            wasHit = player.getEnemy().hit(x, y);
        } else {
            player.getOwn().hit(x, y);
            wasHit = ai.getOwn().hit(x, y);
        }

        if (!wasHit)
            playerTurn = !playerTurn;

        turnNumber++;
    }

    public GameStatus getStatus() {
        if (!playerLostAllBoats(ai) && !playerLostAllBoats(player)) return GameStatus.IN_PROCESS;
        else if (!playerLostAllBoats(ai) && playerLostAllBoats(player)) return GameStatus.AI_WON;
        else if (playerLostAllBoats(ai) && !playerLostAllBoats(player)) return GameStatus.PLAYER_WON;
        else throw new UnhandledGameStatusException();
    }

    private boolean playerLostAllBoats(Player checking) {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                if (checking.getOwn().getBoard().getValue(i, j)) return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return playerTurn == game.playerTurn &&
                turnNumber == game.turnNumber &&
                Objects.equals(ai, game.ai) &&
                Objects.equals(player, game.player);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ai, player, playerTurn, turnNumber);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TURN #")
                .append(turnNumber)
                .append("\n")
                .append(player);
        return builder.toString();
    }
}
