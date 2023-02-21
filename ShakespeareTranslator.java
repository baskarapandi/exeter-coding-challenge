package translate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShakespeareTranslator {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        Map<String, String> dictionary = new HashMap<>();
        Set<String> findWords = new HashSet<>();
        Map<String, Integer> frequency = new HashMap<>();
        try {
            // Read in the English-to-French dictionary file
            BufferedReader dictReader = new BufferedReader(new FileReader("C:\\Users\\baska\\Desktop\\New folder (2)\\french_dictionary.csv"));
            String dictLine;
            while ((dictLine = dictReader.readLine()) != null) {
                String[] dictEntry = dictLine.split(",");
                dictionary.put(dictEntry[0], dictEntry[1]);
            }
            dictReader.close();
            // Read in the "find words" file
            BufferedReader findReader = new BufferedReader(new FileReader("C:\\Users\\baska\\Desktop\\New folder (2)\\find_words.txt"));
            String findLine;
            while ((findLine = findReader.readLine()) != null) {
                findWords.add(findLine);
            }
            findReader.close();
            // Read in the Shakespeare text file
            BufferedReader shakespeareReader = new BufferedReader(new FileReader("C:\\Users\\baska\\Desktop\\New folder (2)\\t8.shakespeare.txt"));
            StringBuilder sb = new StringBuilder();
            String shakespeareLine;
            while ((shakespeareLine = shakespeareReader.readLine()) != null) {
                sb.append(shakespeareLine);
            }
            shakespeareReader.close();
            String shakespeareText = sb.toString();
            // Replace English words with French translations in the Shakespeare text
            String[] words = shakespeareText.split("\\s+");
            StringBuilder translatedText = new StringBuilder();
            for (String word : words) {
                if (findWords.contains(word)) {
                    String frenchTranslation = dictionary.get(word);
                    if (frenchTranslation != null) {
                        translatedText.append(frenchTranslation);
                        frequency.put(word, frequency.getOrDefault(word, 0) + 1);
                    } else {
                        translatedText.append(word);
                    }
                } else {
                    translatedText.append(word);
                }
                translatedText.append(" ");
            }
            // Write the translated text to a file
            FileWriter writer = new FileWriter("C:\\Users\\baska\\Desktop\\New folder (2)\\t8.Shakespeare.translated.txt");
            writer.write(translatedText.toString());
            writer.close();
            // Write the word frequency data to a CSV file
            FileWriter freqWriter = new FileWriter("C:\\Users\\baska\\Desktop\\New folder (2)\\frequency.csv");
            freqWriter.write("word,frenchTranslation,frequency\n");
            for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
            	String Englishword=entry.getKey();
            	String frenchword =dictionary.get(Englishword);
            	int count =entry.getValue();
                freqWriter.write(String.format("%s,%s,%d\n",Englishword ,frenchword ,count ));
            }
            freqWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long timeTaken = (endTime - startTime)/1000;
        long memoryUsed = endMemory - startMemory;
        try {
            // Write the performance data to a file
            FileWriter performanceWriter = new FileWriter("C:\\Users\\baska\\Desktop\\New folder (2)\\performance.txt");
            performanceWriter.write("Time taken: " + (timeTaken/60) + " minutes " +timeTaken%60+ "seconds\n");
            performanceWriter.write("Memory used: " + memoryUsed/(1024*1024) + " MB\n");
            performanceWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}