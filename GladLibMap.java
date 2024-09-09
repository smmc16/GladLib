import edu.duke.*;
import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> map;
    private ArrayList<String> duplicate;
    private ArrayList<String> considered;
    
    private int replacedWords;
    
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLibMap(){
        map = new HashMap<String, ArrayList<String>>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
        duplicate = new ArrayList<String>();
        considered = new ArrayList<String>();
        replacedWords = 0;
    }
    
    public GladLibMap(String source){
        map = new HashMap<String, ArrayList<String>>();
        initializeFromSource(source);
        myRandom = new Random();
        duplicate = new ArrayList<String>();
        considered = new ArrayList<String>();
        replacedWords = 0;
    }
    
    private void initializeFromSource(String source) {
        String[] arr = {"adjective", "noun", "color", "country", "name", "animal", "timeframe", "verb", "fruit"};
        for(String s: arr) {
            ArrayList<String> al = readIt(source + "/"+ s + ".txt"); 
            map.put(s, al);
        }
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        } else {
            if(!considered.contains(label)){
               considered.add(label); 
            }
            ArrayList<String> al = map.get(label);
            return randomFrom(al);
        }
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        while(true){
            if(!duplicate.contains(sub)) {
                duplicate.add(sub);
                break;
            }
            sub = getSubstitute(w.substring(first+1,last));
        }
        replacedWords++;
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    private int totalWordsInMap(){
        int total = 0;
        for(String s : map.keySet()){
            total = map.get(s).size() + total;
        }
        return total;
    }
    
    private int totalWordsConsidered(){
        int total = 0; 
        for(String s : considered){
            total = map.get(s).size() + total;
        }
        return total;
    }
    
    public void makeStory(){
        duplicate.clear();
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate3.txt");
        printOut(story, 60);
        System.out.println("Replaced words: " + replacedWords);
        replacedWords = 0;
        System.out.println("Total words: " + totalWordsInMap());
        System.out.println("Total words considered: " + totalWordsConsidered());
    }
    


}
