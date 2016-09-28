/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jspread.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author emmanuel
 */
public final class QUID {

    private HttpServletRequest request = null;
    private int statementTimeOut = 17; //esta varaible esta en segundos
    private int reportStatementTimeOut = 600; //esta varaible esta en segundos y equivale a 10 min

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    //<editor-fold defaultstate="collapsed" desc="Cierre Conexiones">
    private void endConnection(Statement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void endConnection(PreparedStatement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void endConnection(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void endConnection(JSpreadConnectionPool jscp, Connection connection, Statement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (connection != null) {
            jscp.returnConnectionToPool(connection);
        }
    }

    private void endConnection(JSpreadConnectionPool jscp, Connection connection, Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (connection != null) {
            jscp.returnConnectionToPool(connection);
        }
    }

    private void endConnection(JSpreadConnectionPool jscp, Connection connection) {
        if (connection != null) {
            jscp.returnConnectionToPool(connection);
        }
    }

    private void endConnection(Connection connection, Statement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void endConnection(Connection connection, PreparedStatement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ex) {
                Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="QUERY">
    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="INSERT">
    //</editor-fold>
}
