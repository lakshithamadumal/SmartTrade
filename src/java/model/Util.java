package model;

/**
 *
 * @author Laky
 */
public class Util {

    public static String generateCode() {
        int x = (int) (Math.random() * 1000000);
        return String.format("%06d", x);
    }
}
