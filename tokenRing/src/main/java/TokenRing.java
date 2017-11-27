import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TokenRing {
    private String path;

    static int dataCount = 0;

    static int nodeNumber;

    static int initialDataCount;

    FileHandler fh;

    public static Logger logger = Logger.getLogger("SimpleLog");

    static List<Long> executionTimeList = new ArrayList<>();

    static Map<Integer, Node> nodeList = new HashMap();
    public static List<Long> deliveryTimeList = new ArrayList<>();

    TokenRing(int nodeNumber, int initialDataCount, String path) {

        this.nodeNumber = nodeNumber - 1;

        this.initialDataCount = initialDataCount;

        this.path = path;

        initLogger();
    }

    private void initLogger() {
        try {
            fh = new FileHandler(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setUseParentHandlers(false);
    }

    public int randomizeNode(int currentNode, int nodeNumber) {
        int randomNum;
        while (true) {
            randomNum = ThreadLocalRandom.current().nextInt(0, nodeNumber + 1);
            if (randomNum != currentNode) {
                return randomNum;
            }
        }
    }

    public void initRing() {
        for (int i = 0; i <= nodeNumber; i++)
            nodeList.put(i, new Node(i));


        for (int i = 0; i <= nodeNumber; i++) {
            for (int j = 0; j < initialDataCount; j++) {
                nodeList.get(i).sendData(new PackageProperties(randomizeNode(i, nodeNumber)));
            }
        }
    }

    public long averageTime(List<Long> dataList) {
        long allTime = 0;

        for (Long time :
                dataList) {
            allTime += time;
        }

        return allTime / (1000000*executionTimeList.size());
    }

    public void start() {
            for (int i = 0; i <= nodeNumber; i++) {
                nodeList.get(i).start();
            }
            while (Thread.activeCount() != 2) {
            }
            logger.info( "Average latency: " + averageTime(executionTimeList) + " millis");

            logger.info("Average delivery latency: " + averageTime(deliveryTimeList) + " millis");

            logger.info("Throughput: " + dataCount);
    }
}
