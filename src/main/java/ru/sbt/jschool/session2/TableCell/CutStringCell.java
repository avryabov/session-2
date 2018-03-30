package ru.sbt.jschool.session2.TableCell;

import ru.sbt.jschool.session2.Align;
import ru.sbt.jschool.session2.CutString;

/**
 */
public class CutStringCell implements TableCell {

    @Override
    public int length(Object obj) {
        return Math.min(data(obj).length(), ((CutString) obj).getLengthLimit());
    }

    @Override
    public Align align(Object obj) {
        return Align.LEFT;
    }

    @Override
    public String data(Object obj) {
        CutString str = (CutString) obj;
        if (str.getStr().length() <= str.getLengthLimit()) {
            return str.getStr();
        } else {
            return str.getStr().substring(0, str.getLengthLimit() - 3) + "...";
        }
    }
}
