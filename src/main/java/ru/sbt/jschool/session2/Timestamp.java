package ru.sbt.jschool.session2;

/**
 */
public class Timestamp implements TableCell {

    private String str;

    public Timestamp(String str) {
        this.str = java.sql.Timestamp.valueOf(str).toString();
    }

    @Override
    public int length() {
        return str.length();
    }

    @Override
    public Align align() {
        return Align.RIGHT;
    }

    @Override
    public String toString() {
        return str;
    }
}
