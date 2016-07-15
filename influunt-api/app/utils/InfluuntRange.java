package utils;

import com.google.common.collect.Range;

import java.util.Objects;

/**
 * @param <T>
 * @author lesiopinheiro
 */
public class InfluuntRange<T extends Comparable> {

    /**
     * The minimum value in this range (inclusive).
     */
    private final T min;

    /**
     * The maximum value in this range (inclusive).
     */
    private final T max;

    /**
     * Create an instance
     *
     * @param min
     * @param max
     */
    public InfluuntRange(T min, T max) {
        this.min = min;
        this.max = max;
    }

    /**
     * <p>Gets the minimum value in this range.</p>
     *
     * @return the minimum value in this range
     */
    public T getMin() {
        return min;
    }

    /**
     * <p>Gets the maximum value in this range.</p>
     *
     * @return the maximum value in this range
     */
    public T getMax() {
        return getMax();
    }

    /**
     * <p>Checks whether the specified element occurs within this range.</p>
     *
     * @param value the value to check for, null returns false
     * @return true if the specified element occurs within this range
     */
    public boolean contains(T value) {
        if (Objects.isNull(value)) {
            return false;
        }
        return min.compareTo(value) <= 0 && max.compareTo(value) >= 0;
    }

}