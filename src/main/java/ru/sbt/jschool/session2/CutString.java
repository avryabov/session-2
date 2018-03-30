package ru.sbt.jschool.session2;


public class CutString implements TableCell {
    private String str;
    private int lengthLimit;

    public CutString(String str, int lengthLimit) {
        this.str = str;
        this.lengthLimit = lengthLimit;
    }

    @Override
    public int length() {
        return Math.min(str.length(), lengthLimit);
    }

    @Override
    public Align align() {
        return Align.LEFT;
    }

    @Override
    public String toString() {
        if (str.length() <= lengthLimit) {
            return str;
        } else {
            return str.substring(0, lengthLimit - 3) + "...";
        }
    }
}
