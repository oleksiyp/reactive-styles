import java.util.function.Consumer;

public class BlockingJavaExample {
    private String dbCall1() {
        sleep(1);
        return "test1";
    }

    private String dbCall2() {
        sleep(2);
        return "test2";
    }

    private String dbCall3() {
        sleep(1);
        return "test3";
    }

    void restCall(Consumer<String> consumer) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            consumer.accept(dbCall1());
        });
        Thread thread2 = new Thread(() -> {
            consumer.accept(dbCall2());
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        consumer.accept(dbCall3());
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
