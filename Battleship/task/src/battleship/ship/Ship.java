package battleship.ship;

import battleship.exceptions.InvalidCoordinatesException;
import battleship.exceptions.InvalidShipLengthException;

import static battleship.Config.FIELD_SIZE;

public class Ship {
    private int length;
    private String name;
    private boolean[] body;
    private int i1, j1, i2, j2;
    public Ship(String name, int length) throws InvalidShipLengthException {
        this.name = name;
        if(length <= 0){
            throw new InvalidShipLengthException();
        }
        this.length = length;
        this.body = new boolean[this.length];
    }

    private boolean isValidCoordinates(int i1, int j1, int i2, int j2){
        /*
         check the coordinates for following cases
         1) given ship is horizontal or vertical
         2) coordinates are not bigger than FIELD_SIZE
         3) coordinates do not cross areas where ships are already set
        */
        boolean result = (i1 < FIELD_SIZE && i1 >= 0);
        result = result && (i2 < FIELD_SIZE && i2 >= 0);
        result = result && (j1 < FIELD_SIZE && j1 >= 0);
        result = result && (j2 < FIELD_SIZE && j2 >= 0);
        result = result && (i1 == i2 || j1 == j2);
        return result;
    }

    public boolean isShot(int i, int j) {
        if((i <= this.i2 && i >= this.i1) &&
                (j <= this.j2 && j >= this.j1)) {
            int index = Math.max(i - this.i1, j - this.j1);
            this.body[index] = true;
            return true;
        }
        return false;
    }

    public boolean isDead() {
        boolean res = true;
        for(boolean b : this.body) {
            res = res && b;
            if(!res) break;
        }
        return res;
    }
    private int[] getShipArea() {
        return new int[]{
                this.i1 - 1, this.j1 - 1, this.i2 + 1, this.j2 + 1};
    }

    public boolean isInAnotherShipArea(Ship anotherShip){
        int[] area = anotherShip.getShipArea();

        boolean headInBusyArea = (this.i1 >= area[0]) && (this.i1 <= area[2]) &&
                (this.j1 >= area[1]) && (this.j1 <= area[3]);
        boolean tailInBusyArea = (this.i2 >= area[0]) && (this.i2 <= area[2]) &&
                (this.j2 >= area[1]) && (this.j2 <= area[3]);
        if (headInBusyArea || tailInBusyArea) {
            return true;
        }
        return false;
    }

    public void setCoordinates(int i1, int j1, int i2, int j2) throws
            InvalidCoordinatesException, InvalidShipLengthException {
        int iMin = Math.min(i1, i2), jMin = Math.min(j1, j2);
        int iMax = Math.max(i1, i2), jMax = Math.max(j1, j2);
        i1 = iMin; i2 = iMax;
        j1 = jMin; j2 = jMax;
        if(!isValidCoordinates(i1, j1, i2, j2)) {
            throw new InvalidCoordinatesException();
        }
        if((i2 - i1 != this.length-1) && (j2 - j1 != this.length-1)) {
            throw new InvalidShipLengthException();
        }
        this.i1 = i1; this.j1 = j1;
        this.i2 = i2; this.j2 = j2;

    }

    public String getName() {
        return this.name;
    }

    public int getLength() {
        return this.length;
    }
    public int[] getHead(){
        return new int[]{this.i1, this.j1};
    }

    public int[] getTail(){
        return new int[]{this.i2, this.j2};
    }



}
