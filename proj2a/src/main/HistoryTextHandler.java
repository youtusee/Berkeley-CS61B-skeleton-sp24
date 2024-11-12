package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.ArrayList;
import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private NGramMap ngm;

    public HistoryTextHandler(NGramMap map) {
        ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        StringBuilder result = new StringBuilder();
        for (String word : q.words()) {
            List<String> output = new ArrayList<>();
            TimeSeries ts;
            ts = ngm.weightHistory(word, q.startYear(), q.endYear());
            for (Integer i : ts.keySet()) {
                output.add(i + "=" + ts.get(i));
            }
            result.append(word).append(": ").append("{").append(String.join(", ", output)).append("}").append("\n");
        }
        return result.toString();
    }
}
