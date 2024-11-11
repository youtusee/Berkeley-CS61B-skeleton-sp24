package main;

import edu.princeton.cs.algs4.In;

import java.util.*;


public class WordNetReader {

    public DirectedGraph dg;
    private final HashMap<Integer, HashSet<String>> IDMapToSynsets;
    private final HashMap<HashSet<String>, Integer> synsetsMapToID;

    public WordNetReader() {
        IDMapToSynsets = new HashMap<>();
        synsetsMapToID = new HashMap<>();
        readSynsetsHelper();
        dg = new DirectedGraph(IDMapToSynsets.size());
        readHyponymsHelper();
    }

    public String hyponyms(String word) {
        HashSet<Integer> id;
        id = containsSynsets(word);
        if (id.isEmpty()) {
            return "[]";
        }
        HashSet<Integer> hs = new HashSet<>();
        for (Integer i : id) {
            hs.addAll(dg.traverse(i));
        }
        TreeSet<String> ts = new TreeSet<>();
        for (Integer i : hs) {
            ts.addAll(IDMapToSynsets.get(i));
        }
        List<String> list = new ArrayList<>(ts);
        return "[" + String.join(", ", list) + "]";
    }

    private HashSet<Integer> containsSynsets(String word) {
        HashSet<Integer> result = new HashSet<>();
        for (HashSet<String> hs : synsetsMapToID.keySet()) {
            if (hs.contains(word)) {
                result.add(synsetsMapToID.get(hs));
            }
        }
        return result;
    }

    private void readSynsetsHelper() {
        String SYNSETS_FILE = "./data/wordnet/synsets16.txt";

        In in = new In(SYNSETS_FILE);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            String[] synonyms = splitLine[1].split(" ");
            HashSet<String> hs = new HashSet<>(Arrays.asList(synonyms));
            synsetsMapToID.put(hs, Integer.parseInt(splitLine[0]));
            IDMapToSynsets.put(Integer.parseInt(splitLine[0]), hs);
        }
    }

    private void readHyponymsHelper() {
        String HYPONYMS_FILE = "./data/wordnet/hyponyms16.txt";

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
