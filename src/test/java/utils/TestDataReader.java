package utils;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDataReader {

    private static final String TESTDATA_PATH = Paths.get("testdata", "PetDataAndTestCases.xlsx").toString();

    public static Object[][] getTestDataByType(String type) throws Exception {
        ExcelUtils excel = new ExcelUtils(TESTDATA_PATH);
        List<Map<String, String>> all = excel.getSheetData("PetData");
        List<Map<String, String>> filtered = new ArrayList<>();
        for (Map<String, String> row : all) {
            String t = row.getOrDefault("Type", "").trim();
            if (t.equalsIgnoreCase(type)) {
                filtered.add(row);
            }
            if (t.equalsIgnoreCase("ALL")) {
                filtered.add(row);
            }
        }
        Object[][] data = new Object[filtered.size()][1];
        for (int i = 0; i < filtered.size(); i++) {
            data[i][0] = filtered.get(i);
        }
        return data;
    }
}
