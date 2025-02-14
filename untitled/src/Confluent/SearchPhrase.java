package Confluent;
import java.util.*;

public class SearchPhrase {
    HashMap<String, HashMap<Integer, HashSet<Integer>>> wordToDocumentIDToIndexes;
    HashSet<Integer> allDocIds;

    public SearchPhrase() {
        this.wordToDocumentIDToIndexes = new HashMap<>();
        this.allDocIds = new HashSet<>();
    }
//    Cloud computing is the on-demand availability of computer system resources.
    public void addDocument(int docId, String text) {
        allDocIds.add(docId);
        text = sanitize(text);
        String[] textStrings = text.split(" ");

        for (int i = 0; i < textStrings.length; i++) {
            String word = textStrings[i];
            if (!wordToDocumentIDToIndexes.containsKey(word)) {
                wordToDocumentIDToIndexes.put(word, new HashMap<>());
            }
            HashMap<Integer, HashSet<Integer>> documentIDToIndexes = wordToDocumentIDToIndexes.get(word);

            if (!documentIDToIndexes.containsKey(docId)) {
                documentIDToIndexes.put(docId, new HashSet<>());
            }

            HashSet<Integer> indexes = documentIDToIndexes.get(docId);
            indexes.add(i);
        }

    }

    public List<Integer> search(String phrase) {
        HashSet<Integer> docIds = null;

        phrase = sanitize(phrase);

        String[] textStrings = phrase.split(" ");

        for (int i = 1; i < textStrings.length; i++) {
            String currentWord = textStrings[i];
            String prevWord = textStrings[i - 1];

            if (!wordToDocumentIDToIndexes.containsKey(currentWord)) {
                return new ArrayList<>();
            }
            if (!wordToDocumentIDToIndexes.containsKey(prevWord)) {
                return new ArrayList<>();
            }

            HashMap<Integer, HashSet<Integer>> prevWordDocumentIDToIndexes = wordToDocumentIDToIndexes.get(prevWord);
            HashMap<Integer, HashSet<Integer>> currentWordDocumentIDToIndexes = wordToDocumentIDToIndexes.get(currentWord);

            HashSet<Integer> commonDocIds = this.getCommonDocIdsWithIndexMatching(prevWordDocumentIDToIndexes, currentWordDocumentIDToIndexes);
            if (docIds == null) {
                docIds = commonDocIds;
            }
            docIds.retainAll(commonDocIds);
        }

        if (docIds == null) return new ArrayList<>();

        List<Integer> result = new ArrayList<>(docIds);

        return result;
    }

    private String sanitize(String phrase) {
        phrase = phrase.replace(".", "");
        phrase = phrase.replace(",", "");

        return phrase.toLowerCase();
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
