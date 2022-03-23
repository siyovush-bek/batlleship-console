package battleship;


import java.util.Scanner;

public class Main {


//
//        System.out.println("The game starts!\n");
//        bf.displayToEnemy();
//        System.out.println("Take a shot!\n");
//
//        }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Game game = new Game(in);

        game.start();
    }
}
