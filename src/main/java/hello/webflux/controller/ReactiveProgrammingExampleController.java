package hello.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * Flux와 Mono를 사용하면 아주 쉽계 리액티브한 프로그래밍이 가능하다
 * 리액티브 스트림 구현체 Flux, Mono 를 사용하여 발생하는 데이터를 즉시 리액티브 하게 처리
 * 비동기로 동작 -> 논블로킹 방식으로 처리해야 한다

 * 리액티브 프로그램을 위한 필수 요소
 * 1. 데이터가 준비될때 마다 즉시 리액티브하게 처리 -> 리액티브 스트림 구현체 Flux, Mono 를 사용하여 발생하는 데이터를 즉시 리액티브 하게 처리
 * 2. 로직을 개발시 반드시 논 블럭킹 하게 구현해야한다 -> 이를 위해 "비동기 프로그래밍" 이 필요

 * 스레드란?
 * 1. 우리코드를 포함한 애플리케이션의 작업 흐름을 실행시켜주는 것이 스레드이다
 * 2. Netty는 적은 수의 스레드만을 사용하기 때문에 블로킹에 매우 취약하다
 */

@RestController
@RequestMapping("/reactive")
public class ReactiveProgrammingExampleController {

    // 1-9 까지 출력하는 api
    @GetMapping("/onenine/list")
    public List<Integer> produceOneToNineList() {
        List<Integer> sink = new ArrayList<>();
        for(int i=1; i <= 9; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } // 총 4.5초 소요
            sink.add(i);
        }
        return sink;
    }

    @GetMapping("/onenine/flux")
    public Flux<Integer> produceOneToNineFlux() {
        return Flux.create(sink -> {
            for (int i = 1; i <= 9; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } // 총 4.5초 소요
                sink.next(i);
            }
            sink.complete();
        });
    }

}
