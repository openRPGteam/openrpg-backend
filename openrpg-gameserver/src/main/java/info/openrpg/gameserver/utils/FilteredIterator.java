package info.openrpg.gameserver.utils;

import java.util.Iterator;
import java.util.Objects;

public final class FilteredIterator<E> implements Iterator<E> {
    private final Iterator<E> iterator;
    private final Filter<E> filter;

    private boolean hasNext = true;
    private E next;

    public FilteredIterator(final Iterator<E> iterator, final Filter<E> filter) {
        this.iterator = iterator;
        Objects.requireNonNull(iterator);

        if (filter == null) {
            this.filter = new AcceptAllFilter<E>();
        } else {
            this.filter = filter;
        }

        this.findNext();
    }

    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public E next() {
        E returnValue = this.next;
        this.findNext();
        return returnValue;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void findNext() {
        while (this.iterator.hasNext()) {
            this.next = iterator.next();
            if (this.filter.accept(this.next)) {
                return;
            }
        }
        this.next = null;
        this.hasNext = false;
    }

    private static final class AcceptAllFilter<T> implements Filter<T> {
        public boolean accept(final T type) {
            return true;
        }
    }
}