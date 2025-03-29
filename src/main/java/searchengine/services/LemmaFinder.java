package searchengine.services;

import java.util.HashMap;

public interface LemmaFinder {

    HashMap<String, Integer> getLemmasAndFrequency(String text);

}
