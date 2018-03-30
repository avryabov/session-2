package ru.sbt.jschool.session2;


public class CutString {
    String str;
    int lengthLimit;

    public CutString(String str, int lengthLimit) {
        this.str = str;
        this.lengthLimit = lengthLimit;
    }

    @Override
    public String toString() {
        return str;
    }
}
