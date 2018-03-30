package ru.sbt.jschool.session2.TableCell;

import ru.sbt.jschool.session2.Align;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 */
public class NumberCell implements TableCell {
    private DecimalFormat numberFormat = new DecimalFormat("#0");
    private final char nonBreakingSpace = 0xA0;

    {
        numberFormat.setGroupingUsed(true);
        numberFormat.setGroupingSize(3);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator(nonBreakingSpace);
        numberFormat.setDecimalFormatSymbols(decimalFormatSymbols);
    }

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
        return numberFormat.format((Integer) obj);
    }


}
