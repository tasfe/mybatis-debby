package com.debby.mybatis.helper;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.IColumnFilter;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rocky.hu
 * @date 2017-11-28 10:40 PM
 */
public class DBUnitHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBUnitHelper.class);

    private DataSource dataSource;
    private String connectionString;
    private String username;
    private String password;
    private Map<String, String> hasRun = new HashMap<String, String>();

    public DBUnitHelper() {
        this.connectionString = "jdbc:h2:mem:db1;MODE=MySQL;IGNORECASE=TRUE;DB_CLOSE_DELAY=-1";
        this.username = "sa";
        this.password = "";
    }

    public DBUnitHelper(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    public DBUnitHelper(DataSource dataSource)  {
        this.dataSource = dataSource;
    }

    public static void attemptClose(Connection o) {
        try {
            if (o != null) {
                o.close();
            }
        }
        catch (Exception e) {
            LOGGER.error("Problem closing Connection");
        }
    }

    private boolean checkRunList(String name) {
        if (!hasRun.containsKey(name)) {
            hasRun.put(name, "nothing yet");
            return false;
        }

        return true;
    }

    public void createTables(String sql) {
        executeSql(sql, getConnection());
    }

    public void createTableFromFile(String filePath) {
        if (!checkRunList(filePath)) {
            try {
                String sql = readFile(DBUnitHelper.class.getResourceAsStream(filePath));
                executeSql(sql, getConnection());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            if (dataSource != null) {
                connection = dataSource.getConnection();
            }
            else {
                connection = DriverManager.getConnection(connectionString, username, password);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    private void executeSql(String sql, Connection connection) {
        try {
            connection.createStatement().execute(sql);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            attemptClose(connection);
        }
    }

    private String readFile(InputStream file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        StringBuffer buffer = new StringBuffer();
        String next = null;
        while ((next = br.readLine()) != null) {
            buffer.append(next);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public void executeDataset(String testDataFile) {
        executeDataset(testDataFile, DatabaseOperation.CLEAN_INSERT, null, null);
    }

    public void executeDatasetAsRefresh(String testDataFile) {
        executeDataset(testDataFile, DatabaseOperation.REFRESH, null, null);
    }

    public void executeDatasetAsRefresh(String testDataFile, IColumnFilter columnFilter) {
        executeDataset(testDataFile, DatabaseOperation.REFRESH, columnFilter, null);
    }

    public void executeDatasetAsInsert(String testDataFile) {
        executeDataset(testDataFile, DatabaseOperation.INSERT, null, null);
    }

    public void executeDataset(String testDataFile, String schema) {
        executeDataset(testDataFile, DatabaseOperation.CLEAN_INSERT, null, schema);
    }

    private void executeDataset(String testDataFile, DatabaseOperation operation, IColumnFilter columnFilter, String schema)  {
        if (!checkRunList(testDataFile)) {
            Connection con = null;
            try {
                con = getConnection();
                IDatabaseConnection connection = new DatabaseConnection(con, schema);
                connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
                connection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES,isFullyQualifiedTableName());
                if (columnFilter != null) {
                    connection.getConfig().setProperty(DatabaseConfig.PROPERTY_PRIMARY_KEY_FILTER, columnFilter);
                }

                DataFileLoader loader = new FlatXmlDataFileLoader();
                IDataSet dataSet = loader.load(testDataFile);
                operation.execute(connection, dataSet);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                attemptClose(con);
            }
        }
    }

    public IDataSet getDataSet(String testDataFile) {
        DataFileLoader loader = new FlatXmlDataFileLoader();
        IDataSet dataSet = loader.load(testDataFile);

        return dataSet;
    }

    protected boolean isFullyQualifiedTableName() {
        return Boolean.FALSE;
    }

}
