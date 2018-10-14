import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BlockingJavaExample {
    private void dbCall1(Consumer<String> consumer) {
        sleep(1);
        consumer.accept("test1");
    }

    private void dbCall2(Consumer<String> consumer) {
        sleep(2);
        consumer.accept("test2");
    }

    private void dbCall3(Consumer<String> consumer) {
        sleep(1);
        consumer.accept("test3");
    }

    void restCall(Consumer<String> consumer) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            dbCall1(consumer);
        });
        Thread thread2 = new Thread(() -> {
            dbCall2(consumer);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        dbCall3(consumer);
    }


    public static void main(String[] args) throws InterruptedException {
        new BlockingJavaExample()
                .restCall(System.out::println);
    }


    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
