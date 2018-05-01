package main;

public class Main {
    public static void main(String[] args) {
        BoardView view = null;
        for (int i = 0; i < 1000; i++) {
            view = new BoardView(true);
        }
        System.out.println(view);
    }
}
