package SqLiteDataHandlers;

import java.sql.ResultSet;

public interface IDataGetters {

    public ResultSet getAll(String tableName);

    public ResultSet getAllByCol(String tableName, String colName, String searchParam);
}
