package models;

public class KanaProgress {
    private static final int MASTERY_COUNT = 10;
    private String mora;
    private String romanji;
    private int repetitionsCount;
    private int dontKnowCount;
    private int practiceCount;
    private int masteredCount;

    public KanaProgress(String mora, String romanji, int repetitionsCount, int dontKnowCount, int practiceCount, int masteredCount) {
        this.mora = mora;
        this.romanji = romanji;
        this.repetitionsCount = repetitionsCount;
        this.dontKnowCount = dontKnowCount;
        this.practiceCount = practiceCount;
        this.masteredCount = masteredCount;
    }

    public KanaProgress(String[] line){
        // todo add exception for incomplete data
        this.mora = line[0];
        this.romanji = line[1];
        this.repetitionsCount = Integer.parseInt(line[2]);
        this.dontKnowCount = Integer.parseInt(line[3]);
        this.practiceCount = Integer.parseInt(line[4]);
        this.masteredCount = Integer.parseInt(line[5]);

    }

    public double calculateProgress(){
        //todo better way?
        return (double)this.masteredCount/10;
    }

    public String getRomanji() {
        return romanji;
    }

    public void setRomanji(String romanji) {
        this.romanji = romanji;
    }

    public String getMora() {
        return mora;
    }

    public void setMora(String mora) {
        this.mora = mora;
    }

    public int getRepetitionsCount() {
        return repetitionsCount;
    }

    public void setRepetitionsCount(int repetitionsCount) {
        this.repetitionsCount = repetitionsCount;
    }

    public int getDontKnowCount() {
        return dontKnowCount;
    }

    public void setDontKnowCount(int dontKnowCount) {
        this.dontKnowCount = dontKnowCount;
    }

    public int getPracticeCount() {
        return practiceCount;
    }

    public void setPracticeCount(int practiceCount) {
        this.practiceCount = practiceCount;
    }

    public int getMasteredCount() {
        return masteredCount;
    }

    public void setMasteredCount(int masteredCount) {
        this.masteredCount = masteredCount;
    }

    @Override
    public String toString() {
        return "{" + mora + " " + romanji +
                ", repetitionsCount=" + repetitionsCount +
                ", dontKnowCount=" + dontKnowCount +
                ", practiceCount=" + practiceCount +
                ", masteredCount=" + masteredCount +
                '}';
    }
}
