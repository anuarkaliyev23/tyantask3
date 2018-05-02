package main;


import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        BoardView own = new BoardView(true);
        System.out.println(own);
        Scanner scanner = new Scanner(System.in);

        int x = scanner.nextInt();
        int y = scanner.nextInt();

        own.hit(x, y);
        System.out.println(own);

    }
}
