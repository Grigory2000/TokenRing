import java.io.File;
import java.io.IOException;

public class Main {

    public static String pathToLog = "C:/Users/Григорий/Desktop/SharedTokenRing/TokenRing/src/main/SimpleLog";
    public static  int nodeCount = 10;
    public static  int initialDataCount = 100;

    public static void main(String[] args) {

        TokenRing tokenRing = new TokenRing(nodeCount, initialDataCount, pathToLog);
        tokenRing.initRing();
        tokenRing.start();
    }
}
