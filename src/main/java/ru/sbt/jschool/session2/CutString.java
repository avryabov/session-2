package ru.sbt.jschool.session2;


public class CutString {
    private String str;
    private int lengthLimit;

    public CutString(String str, int lengthLimit) {
        this.str = str;
        this.lengthLimit = lengthLimit;
    }

    public String getStr() {
        return str;
    }

    public int getLengthLimit() {
        return lengthLimit;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setLengthLimit(int lengthLimit) {
        this.lengthLimit = lengthLimit;
    }
}
