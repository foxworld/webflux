package hello.webflux;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
//@SpringBootTest
class FunctionalProgrammingTest {

    @Test
    public void  produceOneToNineListTest() {
        List<Integer> sink = new ArrayList<>();
        for(int i=1; i <= 9; i++) {
            sink.add(i);
        }

        // 모든 값에 *2를 함
        sink = map(sink, (data) -> data * 4);

        // 4의 배수만 남기자
        sink = filter(sink);

        foreach(sink);
    }

    private void foreach(List<Integer> sink) {
        for (Integer integer : sink) {
            log.info("value:{}", integer);
        }
    }

    @Nonnull
    private List<Integer> filter(List<Integer> sink) {
        List<Integer> newSink2 = new ArrayList<>();
        for(int i=0; i < 9; i++) {
            if(sink.get(i) % 4 == 0) {
                newSink2.add(sink.get(i));
            }
        }
        sink = newSink2;
        return sink;
    }

    @Nonnull
    private List<Integer> map(List<Integer> sink, Function<Integer, Integer> mapper) {
        List<Integer> newSink1 = new ArrayList<>();
        for(int i=0; i < 9; i++) {
            newSink1.add(mapper.apply(sink.get(i)));
        }
        sink = newSink1;
        return sink;
    }


}