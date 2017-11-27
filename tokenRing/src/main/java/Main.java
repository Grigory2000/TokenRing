
public class Main {

    public static String pathToLog = "C:/Users/Григорий/Desktop/SharedTokenRing/TokenRing/src/main/SimpleLog";
    public static  int nodeCount = 20;
    public static  int initialDataCount = 10;
    public static int n = 5;

    public static void main(String[] args) {

        TokenRing tokenRing = new TokenRing(nodeCount, initialDataCount, pathToLog);
        for(int i = 1; i <= n; i++) {
            tokenRing.initRing();
            tokenRing.start();
        }
    }
}
