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

import ru.sbt.jschool.session2.TableCell.*;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class OutputFormatter {
    private PrintStream out;

    private class ColumnParam {
        int[] size;
        Align[] align;

        public ColumnParam(int[] size, Align[] align) {
            this.size = size;
            this.align = align;
        }
    }

    Map<Class, TableCell> typeMap = new HashMap<>();

    {
        typeMap.put(Object.class, new NullCell());
        typeMap.put(String.class, new StringCell());
        typeMap.put(Double.class, new MoneyCell());
        typeMap.put(Integer.class, new NumberCell());
        typeMap.put(Date.class, new DateCell());
        typeMap.put(Timestamp.class, new TimestampCell());
        typeMap.put(CutString.class, new CutStringCell());
    }

    public OutputFormatter(PrintStream out) {
        this.out = out;
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
                TableCell cell;
                if (data[j][i] == null)
                    cell = typeMap.get(Object.class);
                else
                    cell = typeMap.get(data[j][i].getClass());
                out.print("|" + alignString(cell.data(data[j][i]), columnParam.size[i], columnParam.align[i]));
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
                TableCell cell;
                if (data[j][i] == null)
                    cell = typeMap.get(Object.class);
                else
                    cell = typeMap.get(data[j][i].getClass());
                if (columnParam.align[i] == null)
                    columnParam.align[i] = cell.align(data[j][i]);
                if (cell.length(data[j][i]) > columnParam.size[i]) {
                    columnParam.size[i] = cell.length(data[j][i]);
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
