package ru.sbt.jschool.session2.TableCell;

import ru.sbt.jschool.session2.Align;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 */
public class MoneyCell implements TableCell {
    private DecimalFormat moneyFormat = new DecimalFormat("#0.00");
    private final char nonBreakingSpace = 0xA0;

    {
        moneyFormat.setGroupingUsed(true);
        moneyFormat.setGroupingSize(3);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator(nonBreakingSpace);
        moneyFormat.setDecimalFormatSymbols(decimalFormatSymbols);
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
        return moneyFormat.format((Double) obj);
    }


}
