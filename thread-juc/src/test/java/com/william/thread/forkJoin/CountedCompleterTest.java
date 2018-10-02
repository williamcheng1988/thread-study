package com.william.thread.forkJoin;

import com.william.thread.forkJoin.countedcompleter.Configuration;
import com.william.thread.forkJoin.countedcompleter.FactorialTask;
import com.william.thread.forkJoin.countedcompleter.Searcher;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class CountedCompleterTest {
    private static final String J_LETTER = "J";
    private static final String NOT_EXISTENT_LETTER = "2";
    private static final String[] ALPHABET = {"A", "B", "C", "D", "E", "F", "G", "H", "I", J_LETTER};
    private static final Configuration DEFAULT_CONFIG = new Configuration(false, false, false);

    @Test
    public void should_fin_result_even_with_more_than_0_pending_tasks() {
        Searcher<String> searcher =
                new Searcher<>(null, ALPHABET, new AtomicReference<>(), 0, ALPHABET.length, J_LETTER, DEFAULT_CONFIG);
        searcher.setPendingCount(333);
        ForkJoinPool pool = new ForkJoinPool();
        String word = pool.invoke(searcher);

//        assertThat(word).isEqualTo(J_LETTER);
//        assertThat(searcher.getPendingCount()).isGreaterThan(0);
//        assertThat(searcher.isCompletedNormally()).isTrue();
    }

    @Test
    public void should_find_letter_without_additional_pending_tasks() {
        Searcher<String> searcher =
                new Searcher<>(null, ALPHABET, new AtomicReference<>(), 0, ALPHABET.length, J_LETTER, DEFAULT_CONFIG);
        ForkJoinPool pool = new ForkJoinPool();
        String word = pool.invoke(searcher);

//        assertThat(word).isEqualTo(J_LETTER);
//        assertThat(searcher.getPendingCount()).isEqualTo(0);
//        assertThat(searcher.isCompletedNormally()).isTrue();
    }

    @Test
    public void should_not_find_letter_because_missing_pending_tasks_incrementation() {
        Configuration configuration = new Configuration(true, false, false);
        Searcher<String> searcher =
                new Searcher<>(null, ALPHABET, new AtomicReference<>(), 0, ALPHABET.length, J_LETTER, configuration);
        ForkJoinPool pool = new ForkJoinPool();
        String word = pool.invoke(searcher);

//        assertThat(word).isNull();
//        assertThat(searcher.isCompletedNormally()).isTrue();
    }

    @Test
    public void should_not_end_because_of_too_many_tasks_and_complete_call_missing() throws InterruptedException {
        Configuration configuration = new Configuration(false, true, false);
        Searcher<String> searcher =
                new Searcher<>(null, ALPHABET, new AtomicReference<>(), 0, ALPHABET.length, J_LETTER, configuration);
        searcher.setPendingCount(333);
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(searcher);
        pool.awaitTermination(4, TimeUnit.SECONDS);

//        assertThat(searcher.isCompletedNormally()).isFalse();
    }

    @Test
    public void should_not_end_because_of_not_existent_letter_and_trycomplete_call_missing() throws InterruptedException {
        Configuration configuration = new Configuration(false, false, true);
        // Because of Searcher code we have to try to find not existent letter to prove that missing tryComplete() call
        // can deadlock.
        Searcher<String> searcher =
                new Searcher<>(null, ALPHABET, new AtomicReference<>(), 0, ALPHABET.length, NOT_EXISTENT_LETTER, configuration);
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(searcher);
        pool.awaitTermination(4, TimeUnit.SECONDS);

//        assertThat(searcher.isCompletedNormally()).isFalse();
    }

    @Test
    public void should_not_find_letter_not_existing_in_alphabet() {
        Searcher<String> searcher =
                new Searcher<>(null, ALPHABET, new AtomicReference<>(), 0, ALPHABET.length, NOT_EXISTENT_LETTER, DEFAULT_CONFIG);
        ForkJoinPool pool = new ForkJoinPool();
        String word = pool.invoke(searcher);

//        assertThat(word).isNull();
//        assertThat(searcher.getPendingCount()).isEqualTo(0);
//        assertThat(searcher.isCompletedNormally()).isTrue();
    }



    public static void main (String[] args) {
        List<BigInteger> list = new ArrayList<>();
        for (int i = 3; i < 20; i++) {
            list.add(new BigInteger(Integer.toString(i)));
        }
        ForkJoinPool.commonPool().invoke(new FactorialTask(null, list));
    }
}