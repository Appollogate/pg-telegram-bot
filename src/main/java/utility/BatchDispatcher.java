package utility;

import postgres.data.Table;

public class BatchDispatcher {
    private final Table table;
    private final Integer batchSize;
    private final Boolean showHeaders;
    private final Boolean showBatchNumber;
    private Integer currentBatch = 0;
    private Integer maxBatchCount;

    public BatchDispatcher(Table table, Integer batchSize, Boolean showHeaders, Boolean showBatchNumber) {
        this.table = table;
        this.batchSize = batchSize;
        this.showHeaders = showHeaders;
        this.showBatchNumber = showBatchNumber;
        setMaxBatchCount();
    }

    public String getCurrentBatch() {
        return getBatchByNumber(currentBatch);
    }

    public String getNextBatch() {
        if (currentBatch < maxBatchCount - 1) {
            currentBatch++;
        }
        return getBatchByNumber(currentBatch);
    }

    public String getPrevBatch() {
        if (currentBatch > 0) {
            currentBatch--;
        }
        return getBatchByNumber(currentBatch);
    }

    private String getHeaderRow() {
        StringBuilder sb = new StringBuilder();
        for (var columnName : table.getColumns()) {
            sb.append(columnName).append("\t");
        }
        return sb.toString();
    }

    private String getBatchByNumber(Integer batchNumber) {
        StringBuilder sb = new StringBuilder();
        if (showHeaders) {
            sb.append(getHeaderRow()).append("\n");
        }
        String delimeter = "";
        for (int i = batchNumber * batchSize; i < (batchNumber + 1) * batchSize && i < table.getRowCount(); ++i) {
            var row = table.getTableMatrix().get(i);
            sb.append(delimeter);
            delimeter = "\n";
            for (var el : row) {
                sb.append(el).append("\t");
            }
        }
        if (showBatchNumber) {
            sb.append("\n(").append(currentBatch + 1).append(" batch out of ").append(maxBatchCount).append(")");
        }
        return sb.toString();
    }

    private void setMaxBatchCount() {
        maxBatchCount = (int) Math.ceil(table.getRowCount() / batchSize.doubleValue());
    }
}
