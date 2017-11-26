public class PackageProperties {

    private int destNode;

    PackageProperties(int destinationNode) {
        this.destNode = destinationNode;
    }

    public int getDestNode() {
        return destNode;
    }

    private long startTime = System.nanoTime();

    public long getStartTime() {
        return startTime;
    }
}
