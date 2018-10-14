import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ReactorJavaExample {
    private Duration seconds(int i) {
        return Duration.of(i, ChronoUnit.SECONDS);
    }

    private Flux<String> dbCall1() {
        return Flux.just("test1")
                .delayElements(seconds(1));
    }

    private Flux<String> dbCall2() {
        return Flux.just("test2")
                .delayElements(seconds(2));
    }

    private Flux<String> dbCall3() {
        return Flux.just("test3")
                .delayElements(seconds(1));
    }

    Flux<String> restCall() {
        Flux<String> call1 = dbCall1();
        Flux<String> call2 = dbCall2();
        Flux<String> call3 = dbCall3();

        return call1.mergeWith(call2)
                .concatWith(call3);
    }


    public static void main(String[] args) {
        new ReactorJavaExample()
                .restCall()
                .toStream()
                .forEach(System.out::println);
    }
}
