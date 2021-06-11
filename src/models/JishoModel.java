package models;

import java.util.ArrayList;

public class JishoModel {
    private ArrayList<String> nLevel;
    private String reading;
    private String word;
    private ArrayList<String> englishDefinition;
    private ArrayList<String> partsOfSpeech;

    public JishoModel(ArrayList<String> nLevel, String reading, String word, ArrayList<String> englishDefinition, ArrayList<String> partsOfSpeech) {
        this.nLevel = nLevel;
        this.reading = reading;
        this.word = word;
        this.englishDefinition = englishDefinition;
        this.partsOfSpeech = partsOfSpeech;
    }

    public ArrayList<String> getnLevel() {
        return nLevel;
    }

    public void setnLevel(ArrayList<String> nLevel) {
        this.nLevel = nLevel;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<String> getEnglishDefinition() {
        return englishDefinition;
    }

    public void setEnglishDefinition(ArrayList<String> englishDefinition) {
        this.englishDefinition = englishDefinition;
    }

    public ArrayList<String> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    public void setPartsOfSpeech(ArrayList<String> partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }

    @Override
    public String
    toString() {
        return "JishoModel{" +
                "nLevel=" + nLevel +
                ", reading='" + reading + '\'' +
                ", word='" + word + '\'' +
                ", englishDefinition=" + englishDefinition +
                ", partsOfSpeech=" + partsOfSpeech +
                '}';
    }
}
