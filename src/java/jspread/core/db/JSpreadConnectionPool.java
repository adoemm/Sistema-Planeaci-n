/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspread.core.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.driver.OracleConnection;

/**
 *
 * @author JeanPaul
 */
//utiliza el siguiente renglon para poder hacer tracer, je je je tambien se puede usar en otras clases
//JOptionPane.showMessageDialog(null, URLFinal + "tracer" + sqle, "tracer", JOptionPane.INFORMATION_MESSAGE);
public final class JSpreadConnectionPool {

    private String Classforname;//Tambien llamado Driver
    private String protocol;
    private String DBMS;
    private String host;
    private int port;
    private String schema = "";
    private String user;
    private String password;
    private String extraParameters = "";
    private String connectionType = "";
    private String DBURL;
    private int MAX_POOL_SIZE = 5;
    private int SET_SOCKET_TIMEOUT = 5;
    private static JSpreadConnectionPool si;
    private LinkedList<Connection> connectionPool = new LinkedList();
    private static final String version = "V0.13";
    private boolean isClosing = false;
    private boolean isTransInUse = false;
    private int priority = 1;
    private int priorityTimeOut = 2500;
    private boolean closeNOpen = false;
    /*
     int TRANSACTION_NONE = 0
     int TRANSACTION_READ_UNCOMMITTED = 1
     int TRANSACTION_READ_COMMITTED = 2
     int TRANSACTION_REPEATABLE_READ = 4
     int  TRANSACTION_SERIALIZABLE = 8
     */
    private int isolationLevel = Connection.TRANSACTION_SERIALIZABLE;

//    protected int getMAX_POOL_SIZE() {
//        return MAX_POOL_SIZE;
//    }
//
//    protected void setMAX_POOL_SIZE(int MAX_POOL_SIZE) {
//        this.MAX_POOL_SIZE = MAX_POOL_SIZE;
//    }
//
//    protected String getClassforname() {
//        return Classforname;
//    }
//
//    public void setClassforname(String Classforname) {
//        this.Classforname = Classforname;
//    }
//
//    protected String getHost() {
//        return host;
//    }
//
//    public void setHost(String host) {
//        this.host = host;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    protected void setUser(String user) {
//        this.user = user;
//    }
    public int getPriorityTimeOut() {
        return priorityTimeOut;
    }

    public synchronized int getPriority() {
        return priority;
    }

    public synchronized boolean isIsTransInUse() {
        return isTransInUse;
    }

    public String getClassforname() {
        return Classforname;
    }

    public void setClassforname(String Classforname) {
        this.Classforname = Classforname;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDBMS() {
        return DBMS;
    }

    public void setDBMS(String DBMS) {
        this.DBMS = DBMS;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setUser(String user) {
        this.user = user;
    }

//    public String getUser() {
//        return this.user;
//    }
    public void setPassword(String password) {
        this.password = password;
    }

//      public String getPassword() {
//        return this.password;
//    }
    public String getExtraParameters() {
        return extraParameters;
    }

    public void setExtraParameters(String extraParameters) {
        this.extraParameters = extraParameters;
    }

    public int getMAX_POOL_SIZE() {
        return MAX_POOL_SIZE;
    }

    public void setMAX_POOL_SIZE(int MAX_POOL_SIZE) {
        this.MAX_POOL_SIZE = MAX_POOL_SIZE;
    }
    
      public int getSOCKET_TIMEOUT() {
        return SET_SOCKET_TIMEOUT;
    }

    public void setSOCKET_TIMEOUT(int SET_SOCKET_TIMEOUT) {
        this.SET_SOCKET_TIMEOUT = SET_SOCKET_TIMEOUT;
    }

    public String getDBURL() {
        return DBURL;
    }

    public int getIsolationLevel() {
        return isolationLevel;
    }

    public void setIsolationLevel(int isolationLevel) {
        this.isolationLevel = isolationLevel;
    }

    public boolean isCloseNOpen() {
        return closeNOpen;
    }

    public void setCloseNOpen(boolean closeNOpen) {
        this.closeNOpen = closeNOpen;
    }

    public static JSpreadConnectionPool getSingleInstance() throws ClassNotFoundException, SQLException {
        // si will be null the first time this is called.
        //hay que generar el constructor especializado
        if (null == si) {
            si = new JSpreadConnectionPool();
        }
        return si;
    }

    public JSpreadConnectionPool() throws ClassNotFoundException, SQLException {
        //initialize();
    }

    public void initialize() throws ClassNotFoundException, SQLException {
        //Here we can initialize all the information that we need
        //Properties connProperties = new Properties();
        //connProperties.
        createDatabaseURL();
        //System.out.println("DBURL> "+DBURL);
        initializeConnectionPool();
    }

    public void createDatabaseURL() {
        if (DBMS.equalsIgnoreCase("derby")) {
            DBURL = protocol + ":" + DBMS + "://" + host + ":" + port + "/" + schema + ";" + "user=" + user + ";" + "password=" + password + ";" + extraParameters + "";
        } else if (DBMS.equalsIgnoreCase("mysql")) {
            DBURL = protocol + ":" + DBMS + "://" + host + ":" + port + "/" + schema + extraParameters + "";
        } else if (DBMS.equalsIgnoreCase("sqlserver")) {
            DBURL = protocol + ":" + DBMS + "://" + host + ":" + port + ";databaseName=" + schema + extraParameters + "";
        } else if (DBMS.equalsIgnoreCase("sqlserverSSL")) {
            //System.setProperty("javax.net.ssl.trustStore", "f:/DEO.cer");
            //System.setProperty("javax.net.ssl.trustStorePassword", "emc243000");
            DBURL = protocol + ":" + "sqlserver" + "://" + host + ":" + port + ";databaseName=" + schema + ";integratedSecurity=true;encrypt=true;trustServerCertificate=true";
        } else if (DBMS.toLowerCase().contains("access")) {
//            DBMS = "Driver={Microsoft Access Driver (*.mdb)};";
//            host = "127.0.0.1";
//            port = 1433;
//            schema = "DBQ=C:\\C.mdb;";
//            user = "root";
//            password = "pass";
//            extraParameters = "";
            DBURL = protocol + ":" + DBMS + schema + extraParameters + "";
        } else if (DBMS.toLowerCase().contains("h2") && connectionType.equalsIgnoreCase("Embedded")) {
            DBURL = protocol + ":" + DBMS + ":" + schema + extraParameters + "";
        } else if (DBMS.toLowerCase().contains("h2") && connectionType.equalsIgnoreCase("TCP")) {
            DBURL = protocol + ":" + DBMS + ":" + connectionType + "://" + host + ":" + port + "/" + schema + extraParameters + "";
        } else if (DBMS.toLowerCase().contains("h2") && connectionType.equalsIgnoreCase("SSL")) {
            DBURL = protocol + ":" + DBMS + ":" + connectionType + "://" + host + ":" + port + "/" + schema + extraParameters + "";
        } else if (DBMS.toLowerCase().contains("oracle")) {
            DBURL = protocol + ":" + DBMS + ":thin" + ":@" + host + ":" + port + ":" + schema + "";
        } else {
            throw new UnsupportedOperationException("DBMS not supported");
        }
    }

    private void initializeConnectionPool() throws ClassNotFoundException, SQLException {
        while (!checkIfConnectionPoolIsFull()) {
            System.out.println("Connection Pool is NOT full. Proceeding with adding new connections");
            //Adding new connection instance until the pool is full
            connectionPool.add(createNewConnectionForPool());
        }
        System.out.println("Connection Pool is full.");
    }

    private synchronized boolean checkIfConnectionPoolIsFull() {
        //Check if the pool size
        if (connectionPool.size() < MAX_POOL_SIZE) {
            return false;
        }
        return true;
    }

    //Creating a connection
    private Connection createNewConnectionForPool() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName(Classforname);
        connection = DriverManager.getConnection(DBURL, user, password);
        connection.setTransactionIsolation(isolationLevel);
        //System.out.println("Connection: " + connection);
        //System.out.println("Connection Isolation Level: " + connection.getTransactionIsolation());
        return connection;
    }

    public synchronized Connection getConnectionFromPool() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        //remainingConnections();
        //Check if there is a connection available. There are times when all the connections in the pool may be used up
        //System.out.println("Connection pool size: " + connectionPool.size());
        if (!isClosing) {
//            if (connectionPool.size() > 0) {
//                connection = (Connection) connectionPool.poll();
//            } else {
//                System.out.println("Ya no hay conexiones.");
//            }

            //System.out.println("conexion: " + connection);
            //if (connection.isClosed() || connection.isValid(250) == false) {
            if (connection == null) {
                //System.out.println("Conexion nula, creando conexcion");
                connection = this.createNewConnectionForPool();
            } else {
                //System.out.println("connection.isClosed():" + connection.isClosed());
                //System.out.println("connection.isValid():" + connection.isValid(200));                
                try {
                    if (connection.isClosed()) {
                        connection.close();
                        connection = null;
                        connection = this.createNewConnectionForPool();
                        if (DBMS.toLowerCase().contains("oracle")) {
                            ((OracleConnection) connection).setDefaultRowPrefetch(1000);
                        }
//                        Statement stmt = connection.createStatement();
//                        String sqlSentence = "ALTER SYSTEM SET result_cache_mode = FORCE";
//                        int update_count = stmt.executeUpdate(sqlSentence);
//                        stmt.close();
                    }
                } catch (Exception ex) {
                    if (connection.isValid(200) == false) {
                        //System.out.println("Conexion cerrada por time out, creando conexcion");
                        connection.close();
                        connection = null;
                        connection = this.createNewConnectionForPool();
                    }
                }
            }
            //Giving away the connection from the connection pool
        }
        return connection;
    }

    public synchronized void returnConnectionToPool(Connection connection) {
        //Adding the connection from the client back to the connection pool
        if (connection != null) {
            if (closeNOpen) {
                try {
                    connection.close();
                    connection = null;
                } catch (SQLException ex) {
                    Logger.getLogger(JSpreadConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            connectionPool.addFirst(connection);
        }
    }

    public synchronized Connection getConnectionFromPool4Trans() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        //remainingConnections();
        //Check if there is a connection available. There are times when all the connections in the pool may be used up
        //System.out.println("Connection pool size: " + connectionPool.size());
        if (!isClosing) {
            if (connectionPool.size() > 0) {
                connection = (Connection) connectionPool.poll();
            } else {
                System.out.println("Ya no hay conexiones.");
            }

            //System.out.println("conexion: " + connection);
            //if (connection.isClosed() || connection.isValid(250) == false) {
            if (connection == null) {
                //System.out.println("Conexion nula, creando conexcion");                
                connection = this.createNewConnectionForPool();
            } else {
                //System.out.println("connection.isClosed():" + connection.isClosed());
                //System.out.println("connection.isValid():" + connection.isValid(200));
                if (connection.isValid(200) == false) {
                    //System.out.println("Conexion cerrada por time out, creando conexcion");
                    connection.close();
                    connection = null;
                    connection = this.createNewConnectionForPool();
                }
            }
            //Giving away the connection from the connection pool
        }
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        connection.setAutoCommit(false);
        isTransInUse = true;
        priority = priority + 1;
        return connection;
    }

    public synchronized void returnTransConnectionToPool(Connection connection) {
        try {
            connection.setAutoCommit(true);
            connection.setTransactionIsolation(Connection.TRANSACTION_NONE);
            isTransInUse = false;
            priority = priority - 1;
            if (closeNOpen) {
                try {
                    connection.close();
                    connection = null;
                } catch (SQLException ex) {
                    Logger.getLogger(JSpreadConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            connectionPool.addFirst(connection);
        } catch (SQLException ex) {
            Logger.getLogger(JSpreadConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String remainingConnections() {
        System.out.println("" + connectionPool.size());
        return "" + connectionPool.size();
    }

    public void closePool() {
        isClosing = true;
        int cont = 0;
        while (connectionPool.size() != MAX_POOL_SIZE) {
            try {
                Thread.sleep(50);
                if (cont > 2400) {
                    break;
                }
                cont++;
            } catch (InterruptedException ex) {
                Logger.getLogger(JSpreadConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
        closeConnections();
    }

    private void closeConnections() {
        Connection connection = null;
        int cont = 0;
        while (cont < connectionPool.size()) {
            connection = connectionPool.get(cont);
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(JSpreadConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = null;
            cont++;
        }
    }
}
