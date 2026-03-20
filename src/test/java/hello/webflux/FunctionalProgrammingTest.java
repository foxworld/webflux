package hello.webflux;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
// ...existing code...

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

// ...existing code...

@Slf4j
// ...existing code...
class FunctionalProgrammingTest {

    @Test
    public void  produceOneToNineListTest() {
        List<Integer> sink = new ArrayList<>();
        for(int i=1; i <= 9; i++) {
            sink.add(i);
        }

        // 모든 값에 *2를 함
        sink = map(sink, (data) -> data * 2);
        // 4의 배수만 남기자
        sink = filter(sink, (data) -> data % 4 == 0);
        foreach(sink, (data) -> log.info("value: {}", data));
    }

    @Test
    public void produceOneToNineStreamTest() {
        IntStream.rangeClosed(1,9).boxed()   // loop 1~9
                .map((data) -> data * 2) // 2 * data 9번 넣기
                .filter((data) -> data % 4 == 0) // 4의 배수만 찾기
                .forEach((data) -> log.info("value: {}", data)); // 찾은것을 루프로 출력
    }

    private void foreach(List<Integer> sink, Consumer<Integer> consumer) {
        for (Integer i : sink) {
            consumer.accept(i);
        }
    }

    @Nonnull
    private List<Integer> filter(List<Integer> sink, Function<Integer, Boolean> predicate) {
        List<Integer> newSink2 = new ArrayList<>();
        for(Integer data : sink) {
            if(predicate.apply(data)) {
                newSink2.add(data);
            }
        }
        sink = newSink2;
        return sink;
    }

    @Nonnull
    private List<Integer> map(List<Integer> sink, Function<Integer, Integer> mapper) {
        List<Integer> newSink1 = new ArrayList<>();
        for(Integer data : sink) {
            newSink1.add(mapper.apply(data));
        }
        sink = newSink1;
        return sink;
    }


}