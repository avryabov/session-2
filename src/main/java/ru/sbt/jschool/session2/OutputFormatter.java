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
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 */
public class OutputFormatter {
    private PrintStream out;

    private enum Align {LEFT, MIDDLE, RIGHT}

    private class ColumnParam {
        int[] size;
        Align[] align;

        public ColumnParam(int[] size, Align[] align) {
            this.size = size;
            this.align = align;
        }
    }

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

    private class Cell {
        String data;
        Align align;

        public Cell(Object obj) {
            if (obj == null) {
                data = "-";
                align = Align.LEFT;
            } else if (obj instanceof String) {
                data = (String) obj;
                align = Align.LEFT;
            } else if (obj instanceof Integer) {
                data = numberFormat.format((Integer) obj);
                align = Align.RIGHT;
            } else if (obj instanceof Double) {
                data = moneyFormat.format((Double) obj);
                align = Align.RIGHT;
            } else if (obj instanceof Timestamp) {
                data = obj.toString();
                align = Align.RIGHT;
            } else if (obj instanceof Date) {
                data = dateFormat.format((Date) obj);
                align = Align.RIGHT;
            } else if (obj instanceof CutString) {
                CutString cutStr = (CutString) obj;
                if (cutStr.str.length() <= cutStr.lengthLimit) {
                    data = cutStr.str;
                } else {
                    data = cutStr.str.substring(0, cutStr.lengthLimit - 3) + "...";
                }
                align = Align.LEFT;
            }
        }
    }

    public void output(String[] names, Object[][] data) {
        ColumnParam columnParam = columnParam(names, data);
        String horizontalBoundary = horizontalBoundary(columnParam);

        out.print(horizontalBoundary);
        for (int i = 0; i < names.length; i++) {
            out.print("|" + alignString(names[i], columnParam.size[i], Align.MIDDLE));
        }
        out.print("|\n");
        for (int j = 0; j < data.length; j++) {
            out.print(horizontalBoundary);
            for (int i = 0; i < data[j].length; i++) {
                Cell cell = new Cell(data[j][i]);
                out.print("|" + alignString(cell.data, columnParam.size[i], columnParam.align[i]));
            }
            out.print("|\n");
        }
        out.print(horizontalBoundary);
    }

    private String horizontalBoundary(ColumnParam param) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < param.size.length; i++) {
            stringBuilder.append("+");
            for (int j = 0; j < param.size[i]; j++) {
                stringBuilder.append("-");
            }
        }
        stringBuilder.append("+\n");
        return stringBuilder.toString();
    }

    private ColumnParam columnParam(String[] names, Object[][] data) {
        ColumnParam columnParam = new ColumnParam(new int[names.length], new Align[names.length]);
        for (int i = 0; i < columnParam.size.length; i++) {
            if (names[i].length() > columnParam.size[i]) {
                columnParam.size[i] = names[i].length();
            }
        }
        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < data[j].length; i++) {
                Cell cell = new Cell(data[j][i]);
                if (columnParam.align[i] == null)
                    columnParam.align[i] = cell.align;
                if (cell.data.length() > columnParam.size[i]) {
                    columnParam.size[i] = cell.data.length();
                }
            }
        }
        return columnParam;
    }

    private String alignString(String str, int size, Align align) {
        String result = "";
        switch (align) {
            case LEFT:
                result = String.format("%-" + size + "s", str);
                break;
            case RIGHT:
                result = String.format("%" + size + "s", str);
                break;
            case MIDDLE:
                int shift = (size - str.length()) / 2;
                if (shift != 0) {
                    result = String.format("%" + shift + "s", "");
                }
                result += String.format("%-" + (size - shift) + "s", str);
                break;
        }
        return result;
    }

}
