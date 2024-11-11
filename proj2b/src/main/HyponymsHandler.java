package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class HyponymsHandler extends NgordnetQueryHandler {

    private final WordNetReader wr;

    public HyponymsHandler() {
        wr = new WordNetReader();
    }

    @Override
    public String handle(NgordnetQuery q) {
        String result = wr.hyponyms(q.words());
        System.out.println(result);
        return result;
    }
}
