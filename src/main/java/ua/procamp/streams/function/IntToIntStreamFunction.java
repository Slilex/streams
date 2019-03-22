package ua.procamp.streams.function;


import ua.procamp.streams.stream.IntStream;

public interface IntToIntStreamFunction extends IntFunction {
     IntStream applyAsIntStream(int value);
}
