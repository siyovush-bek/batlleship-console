package battleship;

import battleship.exceptions.InvalidCoordinatesException;
import battleship.exceptions.TooCloseToAnotherShipsException;
import battleship.ship.Ship;

import static battleship.Config.FIELD_SIZE;
import static battleship.Config.SHIP_COUNT;

public class BattleField {

    private char[][] arr = new char[FIELD_SIZE][FIELD_SIZE];
    private char[][] enemyView = new char[FIELD_SIZE][FIELD_SIZE];
    private int[] cols = new int[10];
    private char[] rows = new char[FIELD_SIZE];

    private int shipCount = 0;

    private int[][] shipAreaCoordinates = new int[4][SHIP_COUNT];

    Ship[] ships = new Ship[5];
    BattleField(){
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                this.arr[i][j] = '~';
                this.enemyView[i][j] = '~';
            }
        }
        for(int i = 0; i < FIELD_SIZE; i++) {
            this.rows[i] = (char)('A' + i);
        }
        for(int i = 0; i < FIELD_SIZE; i++) {
            this.cols[i] = i + 1;
        }
    }

    private void display(char[][] arr){
        System.out.print("  ");
        for(int i = 0; i < FIELD_SIZE; i++) {
            System.out.print(this.cols[i]);
            if(i != FIELD_SIZE - 1){
                System.out.print(' ');
            }
        }
        System.out.print('\n');

        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.print(this.rows[i]);
            System.out.print(' ');
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.print(arr[i][j]);
                if(j != FIELD_SIZE - 1) {
                    System.out.print(' ');
                }
            }
            System.out.print('\n');
        }
        System.out.println();
    }

    public void displayToPlayer(){
        display(this.arr);
    }

    public void displayToEnemy() {
        display(this.enemyView);
    }

    private boolean isCloseToAnotherShips(Ship ship) {
        for(int i = 0; i < this.shipCount; i++){
            if (ship.isInAnotherShipArea(this.ships[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean takeShot(int i, int j) throws InvalidCoordinatesException {
        if (i < 0 || j < 0 || i >= FIELD_SIZE || j >= FIELD_SIZE)
            throw new InvalidCoordinatesException();
        for(Ship ship : ships) {
            if(ship.isShot(i, j)) {
                this.arr[i][j] = 'X';
                this.enemyView[i][j] = 'X';
                return true;
            }
        }
        this.arr[i][j] = 'M';
        this.enemyView[i][j] = 'M';
        return false;
    }

    public boolean isShipDead(int i, int j) {
        for(Ship ship : ships) {
            if(ship.isShot(i, j) && ship.isDead()) {
                return true;
            }
        }
        return false;
    }

    public void setShip(Ship ship) throws
            InvalidCoordinatesException, TooCloseToAnotherShipsException {


        if (isCloseToAnotherShips(ship)){
            throw new TooCloseToAnotherShipsException();
        }

        int[] head = ship.getHead();
        int[] tail = ship.getTail();

        for(int i = head[0]; i <= tail[0]; i++){
            for(int j = head[1]; j <= tail[1]; j++) {
                this.arr[i][j] = 'O';
            }
        }


        this.ships[shipCount] = ship;
        this.shipCount++;
    }

    public boolean shipsAreDead() {
        boolean fullDead = true;
        for (Ship ship : this.ships) {
            fullDead = fullDead && ship.isDead();
            if (!fullDead) return false;
        }
        return fullDead;
    }


}