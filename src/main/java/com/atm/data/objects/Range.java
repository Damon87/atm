package com.atm.data.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Range<T extends Comparable<T>> {

    private T min;
    private T max;

    public static <T extends Comparable<T>> Range<T> of(T min, T max) {
        return new Range<>(min, max);
    }

    public <E extends Comparable<E>> Range<E> map(Function<T, E> mapFunction) {
        E newMin = min == null ? null : mapFunction.apply(min);
        E newMax = max == null ? null : mapFunction.apply(max);

        return Range.of(newMin, newMax);
    }

}
