package com.beyblade.scroreboard.middleware;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoogleSheetApiService {

    @Autowired
    private Sheets sheets;

    @Value("${google.sheet.id}")
    private String spreadsheetId;

    public List<List<Object>> readData(String range) throws Exception {
        ValueRange response = sheets.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        return response.getValues();
    }

    public void updateCell(String sheetName, String cell, Object value) throws Exception {
        String range = sheetName + "!" + cell; // Example: "Sheet1!C7"

        ValueRange body = new ValueRange()
                .setValues(List.of(List.of(value)));  // Single cell update

        sheets.spreadsheets().values()
                .update(spreadsheetId, range, body)
                .setValueInputOption("RAW")
                .execute();
    }

    public String findCell(String sheetName, String valueToFind) throws Exception {
        String range = sheetName; // Reads entire sheet

        ValueRange response = sheets.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> rows = response.getValues();

        if (rows == null) return null;

        for (int row = 0; row < rows.size(); row++) {
            List<Object> cols = rows.get(row);

            for (int col = 0; col < cols.size(); col++) {
                String colValue = cols.get(col).toString().replaceAll("\\s+", "");
                if (colValue.equalsIgnoreCase(valueToFind)) {

                    // Convert numeric column index to Excel-style column letters
                    String columnLetter = convertToColumnLetter(col + 1);

                    // Row index +1 because Sheets uses 1-based index
                    return columnLetter + (row + 1);
                }
            }
        }

        return null; // value not found
    }

    public int getRowNumber(String targetValue) throws Exception {
        List<List<Object>> columnData = readColumn("Sheet1!A:A");

        for (int i = 0; i < columnData.size(); i++) {
            if (!columnData.get(i).isEmpty()) {
                Object cellValue = columnData.get(i).get(0);

                if (cellValue != null && cellValue.toString().equalsIgnoreCase(targetValue)) {
                    return i + 1; // Sheets uses 1-based row index
                }
            }
        }
        return -1; // Not found
    }

    public String getCellValue(String range) throws Exception {
        ValueRange response = sheets.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            return null; // Cell is empty
        }

        return values.get(0).get(0).toString(); // Return the cell value
    }

    private String convertToColumnLetter(int column) {
        StringBuilder sb = new StringBuilder();
        while (column > 0) {
            int rem = (column - 1) % 26;
            sb.append((char) ('A' + rem));
            column = (column - 1) / 26;
        }
        return sb.reverse().toString();
    }

    private List<List<Object>> readColumn(String range) throws Exception {
        System.out.println("ID = " + spreadsheetId);
        ValueRange response = sheets.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response.getValues();
    }
}
