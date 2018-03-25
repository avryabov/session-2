/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.sbt.jschool.session2;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 */
public class OutputFormatter {
    private PrintStream out;

    private enum Align {LEFT, MIDDLE, RIGHT}

    private int[] columnSize;
    private Align[] columnAlign;
    private String[][] dataStr;
    private String horizontalBoundary;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final char nonBreakingSpace = 0xA0;
    private DecimalFormat numberFormat = new DecimalFormat("#0");

    {
        numberFormat.setGroupingUsed(true);
        numberFormat.setGroupingSize(3);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator(nonBreakingSpace);
        numberFormat.setDecimalFormatSymbols(decimalFormatSymbols);
    }

    private DecimalFormat moneyFormat = new DecimalFormat("#0.00");

    {
        moneyFormat.setGroupingUsed(true);
        moneyFormat.setGroupingSize(3);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator(nonBreakingSpace);
        moneyFormat.setDecimalFormatSymbols(decimalFormatSymbols);
    }


    public OutputFormatter(PrintStream out) {
        this.out = out;
    }

    public void output(String[] names, Object[][] data) {
        columnSize = new int[names.length];
        columnAlign = new Align[names.length];
        dataStr = new String[data.length][data.length != 0 ? data[0].length : 0];

        parseDataToString(names, data);
        getHorizontalBoundary();

        out.print(horizontalBoundary);
        for (int i = 0; i < names.length; i++) {
            out.print("|" + alignString(names[i], columnSize[i], Align.MIDDLE));
        }
        out.print("|\n");
        for (int j = 0; j < data.length; j++) {
            out.print(horizontalBoundary);
            for (int i = 0; i < data[j].length; i++) {
                out.print("|" + alignString(dataStr[j][i], columnSize[i], columnAlign[i]));
            }
            out.print("|\n");
        }
        out.print(horizontalBoundary);
    }

    private void getHorizontalBoundary() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < columnSize.length; i++) {
            stringBuilder.append("+");
            for (int j = 0; j < columnSize[i]; j++) {
                stringBuilder.append("-");
            }
        }
        stringBuilder.append("+\n");
        horizontalBoundary = stringBuilder.toString();
    }

    private void parseDataToString(String[] names, Object[][] data) {
        for (int i = 0; i < columnSize.length; i++) {
            if (names[i].length() > columnSize[i]) {
                columnSize[i] = names[i].length();
            }
        }

        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data[j].length; i++) {
                Object obj = data[j][i];
                String str = "";
                if (obj == null) {
                    str = "-";
                    if (columnAlign[i] == null)
                        columnAlign[i] = Align.LEFT;
                }
                if (obj instanceof String) {
                    str = (String) obj;
                    columnAlign[i] = Align.LEFT;
                } else if (obj instanceof Integer) {
                    Integer number = (Integer) obj;
                    str = numberFormat.format(number);
                    columnAlign[i] = Align.RIGHT;
                } else if (obj instanceof Double) {
                    Double money = (Double) obj;
                    str = moneyFormat.format(money);
                    columnAlign[i] = Align.RIGHT;
                } else if (obj instanceof Date) {
                    Date date = (Date) obj;
                    str = dateFormat.format(date);
                    columnAlign[i] = Align.RIGHT;
                }
                if (str.length() > columnSize[i]) {
                    columnSize[i] = str.length();
                }
                dataStr[j][i] = str;
            }
        }

    }

    private String alignString(String cell, int size, Align align) {
        String str = "";
        switch (align) {
            case LEFT:
                str = String.format("%-" + size + "s", cell);
                break;
            case RIGHT:
                str = String.format("%" + size + "s", cell);
                break;
            case MIDDLE:
                int shift = (size - cell.length()) / 2;
                if (shift != 0) {
                    str = String.format("%" + shift + "s", "");
                }
                str += String.format("%-" + (size - shift) + "s", cell);
                break;
        }
        return str;
    }

}
