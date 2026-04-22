package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private Object[] tArray;
    private int size;
    private int firstAddPosition;
    private int lastAddPosition;
    public ArrayDeque() {
        tArray = new Object[8];
        size = 0;
        firstAddPosition = 4;
        lastAddPosition = 5;
    }
    private void resize(int capacity) {
        Object[] newArray = new Object[capacity];
        for (int i = 0; i < size(); ++i) {
            newArray[i] = get(i);
        }
        tArray = newArray;
        firstAddPosition = newArray.length - 1;
        lastAddPosition = size;
    }
    public void addFirst(T item) {
        if (size == tArray.length) {
            resize(size * 2);
        }
        tArray[firstAddPosition] = item;
        firstAddPosition = (--firstAddPosition + tArray.length) % tArray.length;
        ++size;
    }
    public void addLast(T item) {
        if (size == tArray.length) {
            resize(size * 2);
        }
        tArray[lastAddPosition] = item;
        lastAddPosition = ++lastAddPosition % tArray.length;
        ++size;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        for (int i = 0; i < size(); ++i) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int firstValuePosition = (firstAddPosition + 1) % tArray.length;
        T firstValue = (T) tArray[firstValuePosition];
        tArray[firstValuePosition] = null;
        --size;
        //下面的first位置原来在if下面，然后rezie之后first就不是原来的了，导致了数组位置寻找的错误
        firstAddPosition = ++firstAddPosition % tArray.length;
        //下面的double类型转换是我更正之后的，原来我是对商进行了转换，导致精度丢失，每次都会缩小数组，导致了程序崩溃
        if ((double) size / tArray.length < 0.25 && tArray.length >= 16) {
            resize((int) (tArray.length / 2));
        }
        return firstValue;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int lastValuePosition = (lastAddPosition - 1 + tArray.length) % tArray.length;
        T lastValue = (T) tArray[lastValuePosition];
        tArray[lastValuePosition] = null;
        --size;
        lastAddPosition = (--lastAddPosition + tArray.length) % tArray.length;
        if ((double) size / tArray.length < 0.25 && tArray.length >= 16) {
            resize((int) (tArray.length / 2));
        }
        return lastValue;
    }
    public T get(int index) {
        if (size <= index || index < 0) {
            return null;
        }
        return (T) tArray[(firstAddPosition + index + 1) % tArray.length];
    }
    public Iterator<T> iterator() {
        ArrayDeque.DequeIterator interator = new ArrayDeque.DequeIterator();
        return interator;
    }

    private class DequeIterator implements Iterator<T> {
        int currentIndex = 0;
        T currentValue = get(currentIndex);
        public boolean hasNext() {
            return currentIndex < size;
        }
        public T next() {
            T item = get(currentIndex);
            ++currentIndex;
            return item;
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Deque)) {
            return false;
        }

        Deque<?> other = (Deque<?>) o;
        int oSize = other.size();

        if (size() != oSize) {
            return false;
        }
        for (int i = 0; i < size(); ++i) {
            T myItem = this.get(i);
            Object otherItem = other.get(i);
            if (!myItem.equals(otherItem)) {
                return false;
            }
        }
        return  true;
    }
}
