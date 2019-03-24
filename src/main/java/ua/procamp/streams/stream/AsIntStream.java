package ua.procamp.streams.stream;

import ua.procamp.streams.arrays.InterimArray;
import ua.procamp.streams.function.IntBinaryOperator;
import ua.procamp.streams.function.IntFunction;
import ua.procamp.streams.function.IntToIntStreamFunction;
import ua.procamp.streams.function.IntPredicate;
import ua.procamp.streams.function.IntUnaryOperator;
import ua.procamp.streams.function.IntConsumer;

public final class AsIntStream implements IntStream {
    private InterimArray<Integer> values;
    private InterimArray<IntFunction> operations;

    private AsIntStream() {
        values = new InterimArray<>();
        operations = new InterimArray<>();
    }

    public static IntStream of(final int... values) {

        AsIntStream stream = new AsIntStream();
        Integer[] integerValues =
                new Integer[values.length];

        for (int i = 0; i < values.length; i++) {
            integerValues[i] = values[i];
        }
        stream.values.add(integerValues);

        return stream;

    }

    @Override
    public Double average() {
        if (this.values.isEmpty()) {
            throw new IllegalArgumentException("Illegal capacity of values");
        }
        this.updateOperationsList();
        return (double) this.sum() / this.count();
    }

    @Override
    public Integer max() {
        if (this.values.isEmpty()) {
            throw new IllegalArgumentException("Illegal capacity of values");
        }
        this.updateOperationsList();

        return reduce(Integer.MIN_VALUE, (m, v) -> apply(m, v, m > v));
    }

    private int apply(final int m, final int v, final boolean b) {
        if (b) {
            return m;
        } else {
            return v;
        }
    }

    @Override
    public Integer min() {
        if (this.values.isEmpty()) {
            throw new IllegalArgumentException("Illegal capacity of values");
        }
        this.updateOperationsList();

        return reduce(Integer.MAX_VALUE, (m, v) -> apply(m, v, m < v));
    }

    @Override
    public long count() {
       this.updateOperationsList();
       return (long) this.values.size();
    }

    @Override
    public Integer sum() {
        if (this.values.isEmpty()) {
            throw new IllegalArgumentException("Illegal capacity of values");
        }
        this.updateOperationsList();
        return reduce(0, (sum, v) -> sum += v);
    }

    @Override
    public IntStream filter(final IntPredicate predicate) {
       this.operations.add(predicate);
       return this;
    }

    @Override
    public void forEach(final IntConsumer action) {
        this.updateOperationsList();
        for (int i = 0; i < this.values.size(); i++) {
            action.accept(this.values.get(i));
       }
    }

    @Override
    public IntStream map(final IntUnaryOperator mapper) {
        this.operations.add(mapper);
        return this;
    }

    @Override
    public IntStream flatMap(final IntToIntStreamFunction func) {
        this.operations.add(func);
        return this;
    }

    @Override
    public int reduce(final int identity, final IntBinaryOperator op) {
        this.updateOperationsList();
        int res = identity;
        for (int i = 0; i < this.values.size(); i++) {
            res = op.apply(res, this.values.get(i));
        }

        return res;
    }

    @Override
    public int[] toArray() {
        this.updateOperationsList();
        int[] ans = new int[this.values.size()];

        for (int i = 0; i < this.values.size(); i++) {
            ans[i] = this.values.get(i);
        }

        return ans;
    }


    private void usePredicate(final IntPredicate predicate) {
        InterimArray<Integer> interimArray = new InterimArray<>();

        for (int i = 0; i < this.values.size(); i++) {
            if (predicate.test(this.values.get(i))) {
                interimArray.add(this.values.get(i));
            }
        }

        this.values = interimArray;
    }

    private void useUnaryOperator(final IntUnaryOperator operator) {
        InterimArray<Integer> interimArray = new InterimArray<>();

        for (int i = 0; i < this.values.size(); i++) {
            interimArray.add(operator.apply(this.values.get(i)));
        }

        this.values = interimArray;
    }

    private void useStreamFunction(final IntToIntStreamFunction function) {
        InterimArray<Integer> interimArray = new InterimArray<>();

        for (int i = 0; i < this.values.size(); i++) {
            AsIntStream asIntStream =
                    (AsIntStream) function.applyAsIntStream(
                            this.values.get(i));
            interimArray.add(asIntStream.values);
        }

        this.values = interimArray;
    }

    private void updateOperationsList() {
        for (int i = 0; i < this.operations.size(); i++) {
            if (this.operations.get(i) instanceof IntPredicate) {
                usePredicate((IntPredicate) this.operations.get(i));
            }
            if (this.operations.get(i) instanceof IntUnaryOperator) {
                useUnaryOperator((IntUnaryOperator) this.operations.get(i));
            }
            if (this.operations.get(i) instanceof IntToIntStreamFunction) {
                useStreamFunction(
                        (IntToIntStreamFunction) this.operations.get(i));
            }
        }
    }
}