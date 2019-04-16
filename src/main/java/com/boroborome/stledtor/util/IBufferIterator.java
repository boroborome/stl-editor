package com.boroborome.stledtor.util;

import java.util.Iterator;

public interface IBufferIterator<E> extends Iterator<E> {
    /**
     * 执行next后保存next的结果
     *
     * @return
     */
    E current();
}
