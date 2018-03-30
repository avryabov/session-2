package ru.sbt.jschool.session2.TableCell;

import ru.sbt.jschool.session2.Align;

/**
 */
public class NullCell implements TableCell {
    @Override
    public int length(Object obj) {
        return 1;
    }

    @Override
    public Align align(Object obj) {
        return Align.RIGHT;
    }

    @Override
    public String data(Object obj) {
        return "-";
    }
}
