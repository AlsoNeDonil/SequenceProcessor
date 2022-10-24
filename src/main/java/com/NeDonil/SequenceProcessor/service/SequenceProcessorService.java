package com.NeDonil.SequenceProcessor.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@Service
public class SequenceProcessorService {

    public int maxNumber(String filename) throws FileNotFoundException {
        var numbers = readFile(filename);

        Integer max = numbers.get(0);
        for(Integer i : numbers){
            if(i > max){
                max = i;
            }
        }

        return max;
    }

    public int minNumber(String filename) throws FileNotFoundException{
        var numbers = readFile(filename);

        Integer min = numbers.get(0);
        for(Integer i : numbers){
            if(i < min){
                min = i;
            }
        }

        return min;
    }

    public int medianNumber(String filename) throws FileNotFoundException{
        return 0; // ToDo median
    }

    public double average(String filename) throws FileNotFoundException{
        var numbers = readFile(filename);

        Integer sum = 0;
        for(Integer i : numbers){
            sum += i;
        }

        return (double)sum / numbers.size();
    }

    public ArrayList<ArrayList<Integer>> increaseSequences(String filename) throws FileNotFoundException{
        return findSequence(filename, true);
    }

    public ArrayList<ArrayList<Integer>> decreaseSequences(String filename) throws FileNotFoundException{
        return findSequence(filename, false);
    }

    private ArrayList<Integer> readFile(String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner reader = new Scanner(file);

        var numbers = new ArrayList<Integer>();

        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            numbers.add(Integer.parseInt(data));
        }

        return numbers;
    }

    private ArrayList<ArrayList<Integer>> findSequence(String filename, boolean isIncrease) throws FileNotFoundException{
        var numbers = readFile(filename);

        var maxSequences = new ArrayList<ArrayList<Integer>>();
        maxSequences.add(new ArrayList<>()); // max sequences with initial empty sequence

        var testSequence = new ArrayList<Integer>();
        testSequence.add(numbers.get(0)); // test sequence with initial first element of numbers

        for(int i = 1; i < numbers.size(); i++){
            if( (numbers.get(i) - numbers.get(i-1) > 0) == isIncrease){ // check current sequence for increase/decrease
                testSequence.add(numbers.get(i));
            } else {
                checkSequenceLength(testSequence, maxSequences); // check if sequence is equal to maximum length or greater

                testSequence.clear();
                testSequence.add(numbers.get(i));
            }
        }

        checkSequenceLength(testSequence, maxSequences); // check remaining sequence
        return maxSequences;
    }

    private static void checkSequenceLength(ArrayList<Integer> sequence,
                                            ArrayList<ArrayList<Integer>> maxSequences){
        var tmpSequence = new ArrayList<>(sequence);
        if(sequence.size() == maxSequences.get(0).size()){
            maxSequences.add(tmpSequence);
        } else if (sequence.size() > maxSequences.get(0).size()){
            maxSequences.clear();
            maxSequences.add(tmpSequence);
        }
    }
}
