import java.util.ArrayList;
import java.util.Stack;

public class Metrics {
    ArrayList<PackageProperties> savedTime = new ArrayList<>();

    public void send(PackageProperties dataPackage) {
        TokenRing.deliveryTimeList.add(System.nanoTime() - dataPackage.getStartTime());

        savedTime.add(dataPackage);
    }
}
