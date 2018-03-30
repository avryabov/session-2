package ru.sbt.jschool.session2;

/**
 */
public interface TableCell {

    enum Align {LEFT, MIDDLE, RIGHT}

    int length();

    Align align();

    String toString();

    static TableCell valueOf(String str, Align align) {
        return new TableCell() {
            @Override
            public int length() {
                return str.length();
            }

            @Override
            public Align align() {
                return align;
            }

            @Override
            public String toString() {
                return str;
            }
        };
    }

}
