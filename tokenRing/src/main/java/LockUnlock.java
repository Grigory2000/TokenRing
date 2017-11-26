import java.util.Stack;

public class LockUnlock<T> extends Stack {
    private boolean lock = false;

    public void lock(Node node) {
        while (lock) {
            try {
                System.out.println("wait: " + node.getId());
                node.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock = true;
    }

    public void unlock() {
        lock = false;
    }
}
