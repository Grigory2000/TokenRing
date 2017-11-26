public class Node extends Thread {
    Metrics saved = new Metrics();

    private int nodeId;

    private LockUnlock<PackageProperties> lockUnlock = new LockUnlock<>();

    Node(int nodeId) {
        this.nodeId = nodeId;

    }

    public long getId() {
        return nodeId;
    }

    public void sendData(PackageProperties dataPackage) {
        lockUnlock.lock(this);
        TokenRing.dataCount++;

        if (dataPackage != null && dataPackage.getDestNode() == nodeId)
            saved.send(dataPackage);
        else
            lockUnlock.push(dataPackage);

        lockUnlock.unlock();
    }

    public PackageProperties getData() {
        lockUnlock.lock(this);
        PackageProperties properties = (PackageProperties) this.lockUnlock.pop();
        lockUnlock.unlock();
        return properties;
    }

    @Override
    public void run() {
        while (!lockUnlock.isEmpty()) {
            long beginTime = System.nanoTime();
            try {
                if (nodeId == TokenRing.nodeNumber) {
                    TokenRing.logger.info("Data sent from " + nodeId + " to " + 0);
                    TokenRing.nodeList.get(0).sendData(TokenRing.nodeList.get(nodeId).getData());
                } else {
                    TokenRing.logger.info("Data sent from " + nodeId + " to " + (nodeId + 1));
                    TokenRing.nodeList.get(nodeId + 1).sendData(TokenRing.nodeList.get(nodeId).getData());
                }
            } finally {
                long executionTime = System.nanoTime() - beginTime;
                TokenRing.executionTimeList.add(executionTime);
            }
        }
        Thread.yield();
    }
}
