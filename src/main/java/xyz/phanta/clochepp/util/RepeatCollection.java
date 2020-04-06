package xyz.phanta.clochepp.util;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RepeatCollection<T> extends AbstractCollection<T> {

    private final T value;
    private final int count;

    public RepeatCollection(T value, int count) {
        this.value = value;
        this.count = count;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new RepeatIterator();
    }

    private class RepeatIterator implements Iterator<T> {

        private int remaining = count;

        @Override
        public boolean hasNext() {
            return remaining > 0;
        }

        @Override
        public T next() {
            if (remaining <= 0) {
                throw new NoSuchElementException();
            }
            --remaining;
            return value;
        }

    }

}
