package postgres.data;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<String> columns;
    private final List<List<String>> tableMatrix = new ArrayList<>();
    private Integer rowCount = 0;

    public Table(List<String> columns) {
        this.columns = columns;
    }

    public boolean addRow(List<String> row) {
        if (row.size() != columns.size()) {
            return false;
        }
        tableMatrix.add(row);
        rowCount++;
        return true;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<List<String>> getTableMatrix() {
        return tableMatrix;
    }

    public Integer getRowCount() {
        return rowCount;
    }
}
