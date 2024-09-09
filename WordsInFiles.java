
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
import java.io.*;

public class WordsInFiles {
    private HashMap<String, ArrayList<String>> map;
    
    public WordsInFiles() {
        map = new HashMap<String, ArrayList<String>>();
    }
    
    private void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f);
        for(String s: fr.words()) {
            if(!map.containsKey(s)) {
                ArrayList<String> al = new ArrayList<String>();
                al.add(f.getName());
                map.put(s, al);
            } else {
                ArrayList<String> al = map.get(s);
                if(!al.contains(f.getName())) {
                    al.add(f.getName());
                    map.put(s, al);
                }
            }
        }
    }
    
    private void buildWordMap() {
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }
    
    private int maxNumber() {
        int maxSize = 0;
        for(String s: map.keySet()) {
            int currSize = map.get(s).size();
            if(currSize > maxSize) {
                maxSize = currSize;
            }
        } 
        return maxSize;
    }
    
    private ArrayList<String> wordsInNumFiles(int num) {
        ArrayList<String> words = new ArrayList<String>();
        for(String s: map.keySet()) {
            int fileNum = map.get(s).size();
            if(fileNum == num) {
                words.add(s);
            }
        } 
        return words;
    }
    
    private void printFilesIn(String word) {
        ArrayList<String> al = map.get(word);
        for(String s : al){
            System.out.println(s);
        }
    }
    
    public void tester() {
        buildWordMap();
        System.out.println("Max number of files: " + maxNumber());
        System.out.println("Words in 5 files: ");
        int count = 0;
        for(String s: wordsInNumFiles(4)){
            System.out.println(s);
            count++;
        }
        System.out.println("Words: " + count);
        System.out.println("Files with 'tree':");
        printFilesIn("tree");
    }
}
