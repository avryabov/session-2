package ru.sbt.jschool.session2.TableCell;

import ru.sbt.jschool.session2.Align;

/**
 */
public class StringCell implements TableCell {
    @Override
    public int length(Object obj) {
        return data(obj).length();
    }

    @Override
    public Align align(Object obj) {
        return Align.LEFT;
    }

    @Override
    public String data(Object obj) {
        return (String) obj;
    }
}
