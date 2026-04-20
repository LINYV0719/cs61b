package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void TestArrayDeque(){
        StudentArrayDeque<Integer> buggyA = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> correctA = new ArrayDequeSolution<>();
        String message = "";
        for (int i = 0; i < 500; i += 1) {
            int numberBetweenZeroAndOne = StdRandom.uniform(5);
            if (numberBetweenZeroAndOne == 0) {
                buggyA.addLast(i);
                correctA.addLast(i);
                message = message + "addLast(" + i + ")\n";
            } else if (numberBetweenZeroAndOne == 1) {
                buggyA.addFirst(i);
                correctA.addFirst(i);
                message = message + "addFirst(" + i + ")\n";
            } else if (numberBetweenZeroAndOne == 2) {
                if (buggyA.isEmpty()){
                    continue;
                }
                Integer b = buggyA.removeFirst();
                Integer c = correctA.removeFirst();
                message = message + "removeFirst()" + "\n";
                assertEquals(message,c,b);
            } else if (numberBetweenZeroAndOne == 3) {
                if (buggyA.isEmpty()){
                    continue;
                }
                Integer b = buggyA.removeLast();
                Integer c = correctA.removeLast();
                message = message + "removeLast()" + "\n";
                assertEquals(message,c,b);
            } else {
                if (buggyA.isEmpty()){
                    continue;
                }
                int size = buggyA.size();
                int getRandomIndex = StdRandom.uniform(size);
                message = message + "get(" + getRandomIndex + ")\n";
                assertEquals(message,correctA.get(getRandomIndex),buggyA.get(getRandomIndex));
            }
        }

        buggyA.printDeque();
    }
}
