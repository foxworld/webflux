package hello.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Subscriber - Publisher 패턴이란?
 * 한글로 구독자 - 발행자
 * Flux를 구독하면 발행이 시작된다
 * 이 간단한 개념이 Subscriber - Publisher 패턴이다
 *
 * Flux 로 어떻게 블로킹을 회피할 수 있을까?
 * 스레드 1개반 사용해서는 절대로 블로킹을 회피할 수 없다.
 * Reactor 의 스케줄러를 사용해서 스레드를 추가 할당해야지만 플로킹 히피가 가능하다.
 *
 * 스케줄러가 제공하는 스레드는 톰켓의 스레드처럼 어느정도 블로킹 되어도 괜찮다
 * 이스레드는 우리가 원하는 곳에 마음대로 사용할 수 있다.
 *
 * 스케줄러가 제공하는 스레드가 중요한 스레드 (이밴트 루프 스레드) 대신 대기 하는것이 블로킹 회피의 기본적인 전력이다
 *
 * 이밴트를 발생시켜 OS에게 대기를 위임하는 방법은 바로 다음 시간에 다룬다
 */

@Slf4j
@SpringBootTest
public class SubscriberPublisherAsyncTest {

    @Test
    public void produceOneToNineFluxTest() {
        Flux<Integer> intFlux = Flux.<Integer>create(sink -> {
            for (int i = 1; i <= 9; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sink.next(i);
            }
            sink.complete();
        }).subscribeOn(Schedulers.boundedElastic()); // 스레드 추가 할당 -> 블로킹 회피 가능

        intFlux.subscribe(data -> {
            log.debug("처리되고 있는 스레드 이름 {}", Thread.currentThread().getName());
            log.info("webFlux가 구독 중!!: {}", data);
        });
        log.info("Netty 이밴트 루프로 스레드 복귀!");

        /**
         * 테스트 환경이라 메인 스레드는 자기 할일을 다하면 죽어버린다
         * 메인 스레드가 있어야지만 다른 스레드들도 일을 하고 있을 수 있다
         * 그래서 테스트를 위해 메인 스레드를 담시 잡아둔다.
         */
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
