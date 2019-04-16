package com.boroborome.stledtor.util;

import java.util.Iterator;

public abstract class AbstractBufferIterator<E> implements IBufferIterator<E> {
    protected E curItem;

    @Override
    public E current() {
        return curItem;
    }

    /**
     * 将it转换为一个AbstractBufferIterator
     *
     * @param it
     * @return
     */
    public static <T> AbstractBufferIterator<T> from(Iterator<T> it) {
        return new IteratorAdapter<T>(it);
    }

    public static <T> AbstractBufferIterator<T> from(T item) {
        return new SingleIteratorAdapter<T>(item);
    }

    private static class SingleIteratorAdapter<T> extends AbstractBufferIterator<T> {
        private T item;

        public SingleIteratorAdapter(T item) {
            this.item = item;
        }

        @Override
        public boolean hasNext() {
            return item != null;
        }

        @Override
        public T next() {
            T t = item;
            item = null;
            return t;
        }

        @Override
        public void remove() {
            item = null;
        }
    }

    private static class IteratorAdapter<T> extends AbstractBufferIterator<T> {
        private Iterator<T> it;

        /**
         * @param it
         */
        public IteratorAdapter(Iterator<T> it) {
            this.it = it;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public T next() {
            curItem = it.next();
            return curItem;
        }

        @Override
        public void remove() {
            it.remove();
        }
    }
}
