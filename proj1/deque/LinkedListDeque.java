package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T> ,Deque<T> {
    private class TNode {
        T item;
        TNode next;
        TNode prev;

        public TNode() {
            this.item = null;
            this.next = null;
            this.prev = null;
        }

        public TNode(T t, TNode n, TNode p) {
            this.item = t;
            this.prev = p;
            this.next = n;
        }
    }

    private int size;
    private TNode sentinelNode;

    public LinkedListDeque() {
        sentinelNode = new TNode(null, null, null);
        sentinelNode.next = sentinelNode;
        sentinelNode.prev = sentinelNode;
        size = 0;
    }

    public void addFirst(T firstT) {
        TNode firstNode = new TNode(firstT, sentinelNode.next, sentinelNode);
        sentinelNode.next.prev = firstNode;
        sentinelNode.next = firstNode;
        ++size;
    }

    public void addLast(T lastT) {
        TNode lastNode = new TNode(lastT, sentinelNode, sentinelNode.prev);
        sentinelNode.prev.next = lastNode;
        sentinelNode.prev = lastNode;
        ++size;
    }


    public int size() {
        return size;
    }

    public void printDeque() {
        TNode t = sentinelNode.next;
        while (t != sentinelNode) {
            System.out.print(t.item + " ");
            t = t.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        T removeT = sentinelNode.next.item;
        sentinelNode.next = sentinelNode.next.next;
        sentinelNode.next.prev = sentinelNode;
        if (removeT != null) {
            --size;
        }
        return removeT;
    }

    public T removeLast() {
        T removeT = sentinelNode.prev.item;
        sentinelNode.prev = sentinelNode.prev.prev;
        sentinelNode.prev.next = sentinelNode;
        if (removeT != null) {
            --size;
        }
        return removeT;
    }

    public Iterator<T> iterator() {
        DequeIterator interator = new DequeIterator();
        return interator;
    }

    private class DequeIterator implements Iterator<T>{
        TNode currentNode = sentinelNode.next;
        public boolean hasNext(){
            return currentNode != sentinelNode;
        }
        public T next(){
            T currentItem = currentNode.item;
            currentNode = currentNode.next;
            return currentItem;
        }
    }

    public T get(int index) {
        if (size <= index || index <0) {
            return null;
        }
        if (index < size/2){
            TNode targetNode = sentinelNode.next;
            while (index > 0) {
                targetNode = targetNode.next;
                --index;
            }
            return targetNode.item;
        }else {
            TNode targetNode = sentinelNode.prev;
            int steps = size -1-index;
            while (steps > 0) {
                targetNode = targetNode.prev;
                --steps;
            }
            return targetNode.item;
        }
    }

    public T getRecursive(int index) {
        if (size <= index) {
            return null;
        } else {
            return getRecursiveHelper(sentinelNode.next,index).item;
        }
    }

    private TNode getRecursiveHelper(TNode node, int index){
        TNode targetNode = node;
        if (index == 0){
            return targetNode;
        }else {
            targetNode = targetNode.next;
            return getRecursiveHelper(targetNode,--index);
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

