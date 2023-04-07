import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class SpellChecker{
    public static void printArray(String []corrections){
        for (int j = 0; j < corrections.length; j++){
            System.out.print(corrections[j]+" ");
        }
        System.out.println("");
    }

    public static String[] longest_common_substrings(String word, String []suggestions){
        ArrayList<String> lcs_suggestions = new ArrayList<String>();
        HashMap<Integer, ArrayList<String>> substrings = new HashMap<Integer, ArrayList<String>>();
        // generate all substrings of word
        for (int i = word.length(); i > 0; i--){
            for (int j = 0; j < i; j++){
                String substring = word.substring(j, i);
                if (!substrings.containsKey(i-j)){
                    substrings.put(i-j, new ArrayList<String>());
                } 
                substrings.get(i-j).add(substring);
            }
        }

        // starting with the longest substring, see if suggestions words contain this substring
        // if they do, use that suggestion
        for (int i = word.length(); i > 0; i--){
            for (String substring: substrings.get(i)){
                for (int j = 0; j < suggestions.length; j++){
                    if (suggestions[j].indexOf(substring) != -1){
                        lcs_suggestions.add(suggestions[j]);
                    }
                }
            }
            if (lcs_suggestions.size() > 0){
                break;
            }
        }
        String []result = new String[lcs_suggestions.size()];
        return lcs_suggestions.toArray(result);
    }
    public static void main(String []args){
        Words wordDictionary = new Words("english.txt");
        System.out.println("Enter a sentence (no commas): ");
        Scanner inputReader = new Scanner(System.in);
        String sentence = inputReader.nextLine();

        String []wordList = sentence.split(" ");
        for (int i = 0; i < wordList.length; i++){
            if (!wordDictionary.isWord(wordList[i])){
                System.out.println("Correcting word " + wordList[i]);
                String []correctionsReplace = wordDictionary.getReplaceCorrections(wordList[i]);
                System.out.print("Replace corrections: ");
                printArray(correctionsReplace);
                String []correctionsDelete = wordDictionary.getDeleteCorrections(wordList[i]);
                System.out.print("Delete corrections: ");
                printArray(correctionsDelete);
                String []correctionsAdd = wordDictionary.getAddCorrections(wordList[i]);
                System.out.print("Add corrections: ");
                printArray(correctionsAdd);

                String []allCorrections = new String[correctionsReplace.length + correctionsDelete.length + correctionsAdd.length];
                int index = 0;
                for (int j = 0; j< correctionsReplace.length; j++){
                    allCorrections[index] = correctionsReplace[j];
                    index++;
                }
                for (int j = 0; j < correctionsDelete.length; j++){
                    allCorrections[index] = correctionsDelete[j];
                    index++;
                }
                for (int j = 0; j < correctionsAdd.length; j++){
                    allCorrections[index] = correctionsAdd[j];
                    index++;
                }

                String []lcs_corrections = longest_common_substrings(wordList[i], allCorrections);
                System.out.println("Longest Common Substring corrections: ");
                printArray(lcs_corrections);
            }
        }

    }
}