package com.NeDonil.SequenceProcessor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

@Service
public class SequenceProcessorService {

    private final CacheManager cacheManager;

    @Autowired
    SequenceProcessorService(CacheManager manager){
        this.cacheManager = manager;
    }

    @Cacheable("max")
    public int maxNumber(String filename) throws FileNotFoundException {
        Cache cache = cacheManager.getCache("max");

        String fileChecksum = getFileChecksum(filename);
        var result = cache.get(fileChecksum);
        if(result != null){
            return (int)result.get();
        }

        var numbers = readFile(filename);

        Integer max = numbers.get(0);
        for(Integer i : numbers){
            if(i > max){
                max = i;
            }
        }

        cache.put(fileChecksum, max);
        return max;
    }

    @Cacheable("min")
    public int minNumber(String filename) throws FileNotFoundException{

        Cache cache = cacheManager.getCache("min");

        String fileChecksum = getFileChecksum(filename);
        var result = cache.get(fileChecksum);
        if(result != null){
            return (int)result.get();
        }

        var numbers = readFile(filename);

        Integer min = numbers.get(0);
        for(Integer i : numbers){
            if(i < min){
                min = i;
            }
        }

        cache.put(fileChecksum, min);
        return min;
    }

    @Cacheable("median")
    public int medianNumber(String filename) throws FileNotFoundException{
        Cache cache = cacheManager.getCache("median");

        String fileChecksum = getFileChecksum(filename);
        var result = cache.get(fileChecksum);
        if(result != null){
            return (int)result.get();
        }

        var numbers = readFile(filename);
        Collections.sort(numbers);

        var median = numbers.get(numbers.size() / 2);
        cache.put(fileChecksum, median);
        return median;
    }

    @Cacheable("average")
    public double average(String filename) throws FileNotFoundException{
        Cache cache = cacheManager.getCache("average");

        String fileChecksum = getFileChecksum(filename);
        var result = cache.get(fileChecksum);
        if(result != null){
            return (int)result.get();
        }

        var numbers = readFile(filename);

        Long sum = 0L;
        for(Integer i : numbers){
            sum += i;
        }

        var average = (double)sum / numbers.size();
        cache.put(fileChecksum, average);
        return average;
    }

    @Cacheable("sequence_increase")
    public ArrayList<ArrayList<Integer>> increaseSequences(String filename) throws FileNotFoundException{
        return findSequence(filename, true);
    }

    @Cacheable("sequence_decrease")
    public ArrayList<ArrayList<Integer>> decreaseSequences(String filename) throws FileNotFoundException{
        return findSequence(filename, false);
    }

    private ArrayList<ArrayList<Integer>> findSequence(String filename, boolean isIncrease) throws FileNotFoundException{

        var cacheName = isIncrease ? "sequence_increase" : "sequence_decrease";
        Cache cache = cacheManager.getCache(cacheName);

        String fileChecksum = getFileChecksum(filename);
        var result = cache.get(fileChecksum);
        if(result != null){
            return (ArrayList<ArrayList<Integer>>) result.get();
        }

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
        cache.put(fileChecksum, maxSequences);
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

    private static ArrayList<Integer> readFile(String filename) throws FileNotFoundException{
        File file = new File(filename);
        Scanner reader = new Scanner(file);

        var numbers = new ArrayList<Integer>();

        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            numbers.add(Integer.parseInt(data));
        }

        return numbers;
    }

    private static String  getFileChecksum(String filename)
    {
        try {
            File file = new File(filename);
            MessageDigest md5Digest;

            //Get file input stream for reading the file content
            FileInputStream fis = new FileInputStream(file);

            md5Digest = MessageDigest.getInstance("MD5");
            //Create byte array to read data in chunks
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;

            //Read file data and update in message digest
            while ((bytesCount = fis.read(byteArray)) != -1) {
                md5Digest.update(byteArray, 0, bytesCount);
            };

            //close the stream; We don't need it now.
            fis.close();

            //Get the hash's bytes
            byte[] bytes = md5Digest.digest();

            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e){
            System.out.println("Algorithm not found");
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

        return "";
    }
}

