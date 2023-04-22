package postgres.jdbc;

import postgres.data.Table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetConverter {
    private final ResultSet rs;
    public ResultSetConverter(ResultSet rs) {
        this.rs = rs;
    }

    public Table convert() throws SQLException {
        // StringBuilder sb = new StringBuilder();
        ResultSetMetaData metaData = rs.getMetaData();
        List<String> columnNames = new ArrayList<>();
        // column count starts at 1
        for (int i = 1; i <= metaData.getColumnCount(); ++i) {
            String name = metaData.getColumnName(i);
            // sb.append(name).append("\t");
            columnNames.add(name);
        }
        Table res = new Table(columnNames);
        // sb.append("\n");
        while (rs.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= metaData.getColumnCount(); ++i) {
                String value = rs.getString(i);
                row.add(value);
                //sb.append(value).append("\t");
            }
            res.addRow(row);
        }
        // return sb.toString();
        return res;
    }
}
