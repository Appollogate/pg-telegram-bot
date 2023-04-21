package postgres.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetConverter {
    private final ResultSet rs;
    public ResultSetConverter(ResultSet rs) {
        this.rs = rs;
    }

    public String convert() throws SQLException {
        StringBuilder sb = new StringBuilder();
        ResultSetMetaData metaData = rs.getMetaData();
        // column count starts at 1
        for (int i = 1; i <= metaData.getColumnCount(); ++i) {
            String name = metaData.getColumnName(i);
            sb.append(name).append("\t");
        }
        sb.append("\n");
        while (rs.next()) {
            for (int i = 1; i <= metaData.getColumnCount(); ++i) {
                String value = rs.getString(i);
                sb.append(value).append("\t");
            }
        }
        return sb.toString();
    }
}
