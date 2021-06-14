package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JishoResponseModel {
    private static ArrayList<JishoResponseModel> jishoModelArrayList = getWords();
    private ArrayList<String> nLevel;
    private String reading;
    private String word;
    private ArrayList<String> englishDefinition;
    private ArrayList<String> partsOfSpeech;

    public JishoResponseModel(ArrayList<String> nLevel, String reading, String word, ArrayList<String> englishDefinition, ArrayList<String> partsOfSpeech) {
        this.nLevel = nLevel;
        this.reading = reading;
        this.word = word;
        this.englishDefinition = englishDefinition;
        this.partsOfSpeech = partsOfSpeech;
    }

    public static ArrayList<JishoResponseModel> getJishoModelArrayList() {
        return jishoModelArrayList;
    }

    public static void setJishoModelArrayList(ArrayList<JishoResponseModel> jishoModelArrayList) {
        JishoResponseModel.jishoModelArrayList = jishoModelArrayList;
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

    public static ArrayList<JishoResponseModel> getWords(){
        ArrayList<JishoResponseModel> jishoModelArrayList = new ArrayList<>();
        try(java.io.InputStream is = new URL("https://jisho.org/api/v1/search/words?keyword=%23jlpt-n5").openStream()) {

            String contents = new String(is.readAllBytes());
            JSONObject object = new JSONObject(contents);
            JSONArray jsonArray = (JSONArray) object.get("data");

            for (Object obj : jsonArray) {
                JSONObject object1 = (JSONObject) obj;
                // get n level(s)
                JSONArray jlpt = (JSONArray) object1.get("jlpt");
                ArrayList<String> levels = new ArrayList<>();
                for (Object key: jlpt) {
                    levels.add((String)key);
                }

                // get japanese word in hiragana/katakana and kanji
                JSONArray japanese = (JSONArray) object1.get("japanese");
                JSONObject japaneseObj = (JSONObject) japanese.get(0);
                String reading = (String) japaneseObj.get("reading");
                String word = (String) japaneseObj.get("word");

                // get english translation
                JSONArray part_of_speech = (JSONArray) object1.get("senses");
                JSONObject english = (JSONObject)part_of_speech.get(0);
                JSONArray english_def = (JSONArray) english.get("english_definitions");
                ArrayList<String> englishDefArrayList = new ArrayList<>();
                for (Object key : english_def) {
                    englishDefArrayList.add((String) key);
                }
                // get part of speech
                JSONArray partsOfSpeech = (JSONArray) english.get("parts_of_speech");
                ArrayList<String> partsOfSpeechArrayList = new ArrayList<>();
                for (Object key : partsOfSpeech) {
                    partsOfSpeechArrayList.add((String) key);
                }

                jishoModelArrayList.add(new JishoResponseModel(levels, reading, word, englishDefArrayList, partsOfSpeechArrayList));

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Incorrect url");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem reading data");
        }
        return jishoModelArrayList;

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
