package battleship;

public class Helper {
    public final static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int getJ(String s) {
        return Integer.parseInt(s.substring(1));
    }
}
