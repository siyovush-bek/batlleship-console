package battleship;

import java.util.Scanner;

public class Game {
    private Player player1;
    private Player player2;

    private Scanner in;

    Game(Scanner in) {
        this.in = in;
        player1 = new Player("Player 1", this.in);
        player2 = new Player("Player 2", this.in);
    }

    private void waitForEnter() {
        String pressEnter;
        do {
            pressEnter = in.nextLine();
        } while (!pressEnter.isEmpty());
    }

    public void start() {
        try {
            // making fields
            System.out.printf("%s, place your ships on the game field\n", player1.getName());
            player1.prepareField();
            System.out.println("Press Enter and pass the move to another player");
            waitForEnter();
            Helper.clearConsole();
            System.out.printf("%s, place your ships on the game field\n", player2.getName());
            player2.prepareField();

            // taking shots
            System.out.println("Press Enter and pass the move to another player");
            waitForEnter();
            Helper.clearConsole();

            while(!player1.isLoser() && !player2.isLoser()) {
                player2.displayEnemyMyField();
                System.out.println("---------------------");
                player1.displayMeMyField();
                System.out.printf("%s, it's your turn:\n", player1.getName());
                player1.attackEnemy(player2);

                System.out.println("Press Enter and pass the move to another player");
                waitForEnter();
                Helper.clearConsole();

                player1.displayEnemyMyField();
                System.out.println("---------------------");
                player2.displayMeMyField();
                System.out.printf("%s, it's your turn:\n", player2.getName());
                player2.attackEnemy(player1);

                System.out.println("Press Enter and pass the move to another player");
                waitForEnter();
                Helper.clearConsole();
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
