package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> defaultComparator;
    public MaxArrayDeque(Comparator<T> c){
        this.defaultComparator =c;
    }
    public T max(){
        return max(defaultComparator);
    }
    public T max(Comparator<T> c){
        if(this.size()==0){
            return null;
        }
        T currentMaxValue = this.get(0);
        if (size()==1){
            return currentMaxValue;
        }
        else {
            for (int i = 0; i < size() - 1; ++i) {
                if (c.compare(currentMaxValue, this.get(i + 1)) < 0) {
                    currentMaxValue = this.get(i + 1);
                }
            }
            return currentMaxValue;
        }
    }
}

