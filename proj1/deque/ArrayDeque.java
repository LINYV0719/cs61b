package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>,Iterable<T> {
    private Object[] Tarray;
    private int size;
    private int firstAddPosition;
    private int lastAddPosition;
    public ArrayDeque(){
        Tarray = new Object[8];
        size = 0;
        firstAddPosition = 4;
        lastAddPosition = 5;
    }
    private void resize(int capacity){
        Object[] newArray = new Object[capacity];
        for (int i=0;i<size();++i){
            newArray[i] = get(i);
        }
        Tarray = newArray;
        firstAddPosition=newArray.length-1;
        lastAddPosition=size;
    }
    public void addFirst(T item) {
        if (size == Tarray.length) {
            resize(size * 2);
        }
        Tarray[firstAddPosition] = item;
        firstAddPosition =(--firstAddPosition +Tarray.length)%Tarray.length;
        ++size;
    }
    public void addLast(T item) {
        if (size == Tarray.length) {
            resize(size * 2);
        }
        Tarray[lastAddPosition] = item;
        lastAddPosition = ++lastAddPosition % Tarray.length;
        ++size;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        for (int i=0;i<size();++i){
            System.out.print(get(i)+" ");
        }
        System.out.println();
    }
    public T removeFirst(){
        if(size == 0){
            return null;
        }
        int firstValuePosition = (firstAddPosition + 1) % Tarray.length;
        T firstValue = (T) Tarray[firstValuePosition];
        Tarray[firstValuePosition] = null;
        --size;
        if((double) (size / Tarray.length) < 0.25 && Tarray.length >= 16){
            resize((int) (Tarray.length/2));
        }
        firstAddPosition = ++firstAddPosition%Tarray.length;
        return firstValue;
    }
    public T removeLast(){
        if(size == 0){
            return null;
        }
        int lastValuePosition = (lastAddPosition - 1 + Tarray.length) % Tarray.length;
        T lastValue = (T) Tarray[lastValuePosition];
        Tarray[lastValuePosition] = null;
        --size;
        if((double) (size / Tarray.length) < 0.25 && Tarray.length >= 16){
            resize((int) (Tarray.length/2));
        }
        lastAddPosition = (--lastAddPosition + Tarray.length) %Tarray.length;
        return lastValue;
    }
    public T get(int index){
        if( size <= index || index < 0){
            return null;
        }
        return (T) Tarray[(firstAddPosition+index+1)%Tarray.length];
    }
    public Iterator<T> iterator() {
        ArrayDeque.DequeIterator interator = new ArrayDeque.DequeIterator();
        return interator;
    }

    private class DequeIterator implements Iterator<T>{
        int currentIndex =0;
        T currentValue = get(currentIndex);
        public boolean hasNext(){
            return currentIndex < size;
        }
        public T next(){
            T item = get(currentIndex);
            ++currentIndex;
            return item;
        }
    }

    public boolean equals(Object o){
        if(o == this){
            return true;
        } else if (!(o instanceof Deque)) {
            return false;
        }

        Deque<?> other = (Deque<?>) o;
        int oSize = other.size();

        if (size()!=oSize){
            return false;
        }
        for (int i=0;i<size();++i){
            T myItem = this.get(i);
            Object otherItem = other.get(i);
            if (!myItem.equals(otherItem)){
                return false;
            }
        }
        return  true;
    }
}
