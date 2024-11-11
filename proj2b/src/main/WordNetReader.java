package main;

import edu.princeton.cs.algs4.In;

import java.util.*;


public class WordNetReader {

    public DirectedGraph dg;
    private final HashMap<Integer, HashSet<String>> IDMapToSynsets;
    private final String SYNSETS_FILE;
    private final String HYPONYMS_FILE;

    public WordNetReader() {
        SYNSETS_FILE = Global.synsetFile;
        HYPONYMS_FILE = Global.hyponymFile;
        IDMapToSynsets = new HashMap<>();
        readSynsetsHelper();
        dg = new DirectedGraph(IDMapToSynsets.size());
        readHyponymsHelper();
    }

    public String hyponyms(List<String> words) {
        HashSet<String> hs = new HashSet<>(hyponymsForOneWord(words.getFirst()));
        for (String s : words) {
            hs.retainAll(hyponymsForOneWord(s));
        }
        List<String> result = new ArrayList<>(hs);
        Collections.sort(result);
        return "[" + String.join(", ", result) + "]";
    }

    public HashSet<String> hyponymsForOneWord(String word) {
        HashSet<String> result = new HashSet<>();
        HashSet<Integer> id;
        id = containsSynsets(word);
        if (id.isEmpty()) {
            return result;
        }
        HashSet<Integer> hs = new HashSet<>();
        for (Integer i : id) {
            hs.addAll(dg.traverse(i));
        }
        for (Integer i : hs) {
            result.addAll(IDMapToSynsets.get(i));
        }
        return result;
    }

    private HashSet<Integer> containsSynsets(String word) {
        HashSet<Integer> result = new HashSet<>();
        for (Integer i : IDMapToSynsets.keySet()) {
            if (IDMapToSynsets.get(i).contains(word)) {
                result.add(i);
            }
        }
        return result;
    }

    private void readSynsetsHelper() {

        In in = new In(SYNSETS_FILE);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            String[] synonyms = splitLine[1].split(" ");
            HashSet<String> hs = new HashSet<>(Arrays.asList(synonyms));
            IDMapToSynsets.put(Integer.parseInt(splitLine[0]), hs);
        }
    }

    private void readHyponymsHelper() {

        In in = new In(HYPONYMS_FILE);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            for (int i = 1; i < splitLine.length; i++) {
                dg.addEdge(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[i]));
            }
        }
    }
}
