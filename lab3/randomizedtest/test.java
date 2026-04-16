package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {
    @Test
    public void testThreeAddThreeRemove (){
        AListNoResizing<Integer> a = new AListNoResizing<>();
        BuggyAList<Integer> b = new BuggyAList<>();
        a.addLast(4);
        b.addLast(4);
        a.addLast(5);
        b.addLast(5);
        a.addLast(6);
        b.addLast(6);
        assertEquals(a.removeLast(),b.removeLast());
        assertEquals(a.removeLast(),b.removeLast());
        assertEquals(a.removeLast(),b.removeLast());
    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggyL = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggyL.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                assertEquals(size,buggyL.size());
            }else if (operationNumber == 2){
                if(L.size() > 0 ){
                    Integer last = L.getLast();
                    assertEquals(L.getLast(),buggyL.getLast());
                }
            }else if (operationNumber == 3){
                if(L.size() > 0){
                    Integer last =L.removeLast();
                    assertEquals(last,buggyL.removeLast());
                }
            }
        }
    }


}
