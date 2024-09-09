
/**
 * Write a description of CodonCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;

public class CodonCount {
    private HashMap<String, Integer> map;
    
    public CodonCount() {
        map = new HashMap<String, Integer>();
    }
    
    private void buildCodonMap(int start, String dna){
        map.clear();
        for(int i = start; i < dna.length(); i += 3) {
            int end = i + 3;
            String codon = "";
            if(end < dna.length()){
                codon = dna.substring(i, end);
                if(map.containsKey(codon)) {
                    map.put(codon, map.get(codon) + 1);
                } else {
                    map.put(codon, 1);
                }
            }
            
        }
    }
    
    private String getMostCommonCodon() {
        String common = ""; 
        int value = 0;
        for(String s : map.keySet()) {
            int currVal = map.get(s);
            if(currVal > value) {
                value = currVal;
                common = s;
            } else if(currVal == value) {
                common = common + " " + s;
            }
        }
        return common;
    }
    
    private void printCodonCounts(int start, int end) {
        for(String s : map.keySet()) {
            if(map.get(s) >= start && map.get(s) <= end) {
                System.out.println(s + "\t" + map.get(s));
            }
        }
    }
    
    private int countUniqueCodons() {
        int count = 0;
        for(String s : map.keySet()) {
            count++;
        }
        return count;
    }
    
    public void tester() {
        FileResource fr = new FileResource();
        String dna = fr.asString().toUpperCase();
        buildCodonMap(0, dna);
        System.out.println("Most common codon: " + getMostCommonCodon());
        System.out.println("Most common occurences: " + map.get(getMostCommonCodon()));
        printCodonCounts(7, 7);
        System.out.println("Unique codons: " + countUniqueCodons());
    }
}
