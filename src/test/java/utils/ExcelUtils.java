package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class ExcelUtils {

    private String filePath;

    public ExcelUtils(String filePath) {
        this.filePath = filePath;
    }

    public List<Map<String, String>> getSheetData(String sheetName) throws Exception {
        List<Map<String, String>> rows = new ArrayList<>();
        try (InputStream is = new FileInputStream(this.filePath);
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) return rows;
            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = null;
            if (rowIterator.hasNext()) headerRow = rowIterator.next();
            if (headerRow == null) return rows;
            List<String> headers = new ArrayList<>();
            for (Cell c : headerRow) headers.add(c.getStringCellValue());

            while (rowIterator.hasNext()) {
                Row r = rowIterator.next();
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                   cell.setCellType(CellType.STRING);
                    map.put(headers.get(i), cell.getStringCellValue());
                }
                rows.add(map);
            }
        }
        return rows;
    }
}
