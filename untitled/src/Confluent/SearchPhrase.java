package Confluent;
import java.util.*;

public class SearchPhrase {
    HashMap<String, HashMap<Integer, HashSet<Integer>>> wordToDocumentIDToIndexes;

    public SearchPhrase() {
        this.wordToDocumentIDToIndexes = new HashMap<>();
    }
//    Cloud computing is the on-demand availability of computer system resources.
    public void addDocument(int docId, String text) {
        text = sanitize(text);
        String[] textStrings = text.split(" ");

        for (int i = 0; i < textStrings.length; i++) {
            String word = textStrings[i];
            wordToDocumentIDToIndexes.putIfAbsent(word, new HashMap<>());

            HashMap<Integer, HashSet<Integer>> documentIDToIndexes = wordToDocumentIDToIndexes.get(word);

            documentIDToIndexes.putIfAbsent(docId, new HashSet<>());
            HashSet<Integer> indexes = documentIDToIndexes.get(docId);
            indexes.add(i);
        }

    }

    public List<Integer> search(String phrase) {

        phrase = sanitize(phrase);

        String[] words = phrase.split(" ");

        HashMap<Integer, HashSet<Integer>> prevWordDocumentIdToIndexes = wordToDocumentIDToIndexes.get(words[0]);

        if (prevWordDocumentIdToIndexes == null) return new ArrayList<>();

        HashSet<Integer> result = new HashSet<>(prevWordDocumentIdToIndexes.keySet());

        for (int i = 1; i < words.length; i++) {
            HashMap<Integer, HashSet<Integer>> currentWordDocumentIdToIndexes = wordToDocumentIDToIndexes.get(words[i]);

            if (currentWordDocumentIdToIndexes == null) return new ArrayList<>();

            HashSet<Integer> commonDocIds = getCommonDocIdsWithIndexMatching(prevWordDocumentIdToIndexes, currentWordDocumentIdToIndexes);
            result.retainAll(commonDocIds);

            prevWordDocumentIdToIndexes = currentWordDocumentIdToIndexes;
        }

        return new ArrayList<>(result);
    }

    private String sanitize(String phrase) {
        return phrase.replace(".", "").replace(",", "").toLowerCase();
    }

    private HashSet<Integer> getCommonDocIdsWithIndexMatching(HashMap<Integer, HashSet<Integer>> prevWordDocumentIDToIndexes, HashMap<Integer, HashSet<Integer>> currentWordDocumentIDToIndexes) {
        HashSet<Integer> commonDocIds = new HashSet<>();
        for (int docId: currentWordDocumentIDToIndexes.keySet()) {
            if (!prevWordDocumentIDToIndexes.containsKey(docId)) {
                continue;
            }
            HashSet<Integer> prevWordIndexes = prevWordDocumentIDToIndexes.get(docId);
            HashSet<Integer> indexes = currentWordDocumentIDToIndexes.get(docId);

            for (int index: indexes) {
                if (prevWordIndexes.contains(index - 1)) {
                    commonDocIds.add(docId);
                    break;
                }
            }
        }

        return commonDocIds;
    }


}
