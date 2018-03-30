package ru.sbt.jschool.session2.TableCell;

import ru.sbt.jschool.session2.Align;

/**
 */
public interface TableCell {

    int length(Object obj);

    Align align(Object obj);

    String data(Object obj);
}
