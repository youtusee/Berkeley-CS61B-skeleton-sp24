package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;

public class HistoryHandler extends NgordnetQueryHandler {

    private NGramMap ngm;

    public HistoryHandler(NGramMap map) {
        ngm = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String word : q.words()) {
            TimeSeries ts = new TimeSeries();
            ts = ngm.weightHistory(word, q.startYear(), q.endYear());
            lts.add(ts);
            labels.add(word);
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        return Plotter.encodeChartAsString(chart);
    }
}
