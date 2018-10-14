import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class RxJavaExample {
    private Observable<String> dbCall1() {
        return Observable.fromArray("test1").delay(1, TimeUnit.SECONDS);
    }

    private Observable<String> dbCall2() {
        return Observable.fromArray("test2").delay(2, TimeUnit.SECONDS);
    }

    private Observable<String> dbCall3() {
        return Observable.fromArray("test3").delay(1, TimeUnit.SECONDS);
    }

    Observable<String> restCall() {
        Observable<String> call1 = dbCall1();
        Observable<String> call2 = dbCall2();
        Observable<String> call3 = dbCall3();

        return call1.mergeWith(call2)
                .concatWith(call3);
    }


    public static void main(String[] args) {
        new RxJavaExample()
                .restCall()
                .blockingSubscribe(System.out::println);
    }
}
