package battleship;

import battleship.exceptions.InvalidCoordinatesException;
import battleship.exceptions.InvalidShipLengthException;
import battleship.exceptions.TooCloseToAnotherShipsException;
import battleship.ship.*;

import java.util.Scanner;

public class Player {

    private String name;
    public BattleField battleField;

    Scanner in;
    public Player(String name, Scanner in) {
        this.name = name;
        battleField = new BattleField();
        this.in = in;
    }

    String getName() {
        return name;
    }


    public void prepareField() throws Exception {

        Ship[] ships = new Ship[]{
                new AircraftCarrierShip(),
                new BattleShip(),
                new SubmarineShip(),
                new CruiserShip(),
                new DestroyerShip()
        };

        battleField.displayToPlayer();

        for (Ship ship : ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n",
                    ship.getName(), ship.getLength());
            System.out.println();
            while (true) {
                try {
                    String[] strings = in.nextLine().split(" ");
                    String head = strings[0];
                    String tail = strings[1];

                    int i1 = head.charAt(0) - 'A';
                    int j1 = Helper.getJ(head) - 1;
                    int i2 = tail.charAt(0) - 'A';
                    int j2 = Helper.getJ(tail) - 1;

                    System.out.println();
                    ship.setCoordinates(i1, j1, i2, j2);
                    battleField.setShip(ship);
                    battleField.displayToPlayer();
                    break;
                } catch (InvalidCoordinatesException e) {
                    System.out.println("Error! Wrong ship location! Try again:");
                    System.out.println();
                } catch (InvalidShipLengthException e) {
                    System.out.printf("Error! Wrong length of the %s! Try again: \n", ship.getName());
                    System.out.println();
                } catch (TooCloseToAnotherShipsException e) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    System.out.println();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void attackEnemy(Player enemy) {
        while (true) {
            String coord = in.nextLine();
            int i = coord.charAt(0) - 'A';
            int j = Helper.getJ(coord) - 1;
            System.out.println();
            try {
                switch (enemy.takeShot(i, j)) {
                    case SHIP_SANK:
                        System.out.println("You sank a ship!");
                        break;
                    case MISS:
                        System.out.println("You missed!");
                        break;
                    case HIT:
                        System.out.println("You hit a ship!");
                        break;
                    case ALL_SHIPS_SANK:
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        break;
                }
                break;
            } catch (InvalidCoordinatesException e) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            }
        }
    }
    private Shots takeShot(int i, int j) throws InvalidCoordinatesException {
        boolean isShot = battleField.takeShot(i, j);
        if (isShot) {
            if (battleField.shipsAreDead()) {
                return Shots.ALL_SHIPS_SANK;
            }
            if (battleField.isShipDead(i, j)) {
                System.out.println("You sank a ship!");
                return Shots.SHIP_SANK;
            } else {
                return Shots.HIT;
            }
        } else {
            return Shots.MISS;
        }

    }

    public void displayMeMyField() {
        battleField.displayToPlayer();
    }

    public void displayEnemyMyField() {
        battleField.displayToEnemy();
    }
    public boolean isLoser() {
        return battleField.shipsAreDead();
    }
}
