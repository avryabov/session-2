package ru.sbt.jschool.session2.TableCell;

import ru.sbt.jschool.session2.Align;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 */
public class DateCell implements TableCell {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

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
        return dateFormat.format((Date) obj);
    }
}
