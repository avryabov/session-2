package ru.sbt.jschool.session2.TableCell;

import ru.sbt.jschool.session2.Align;

import java.sql.Timestamp;

/**
 */
public class TimestampCell implements TableCell {
    @Override
    public int length(Object obj) {
        return data(obj).length();
    }

    @Override
    public Align align(Object obj) {
        return Align.RIGHT;
    }

    @Override
    public String data(Object obj) {
        return ((Timestamp) obj).toString();
    }
}
