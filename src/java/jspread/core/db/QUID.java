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
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import jspread.core.util.security.JHash;

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
//Metodo que retorna el usuario, nombre completo, idPlantel y rol
    public final LinkedList selectIdUsuario(String usuario, String password, String status) {
        LinkedList listToSend = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            SQLSentence = ""
                    + " SELECT"
                    + " U.ID_Usuario"
                    + " , U.nombreCompleto"
                    + " , U.FK_ID_Plantel"
                    + " , R.nombreRol"
                    + " FROM USUARIO U"
                    + " , ROL_USUARIO RU"
                    + " , ROL R"
                    + " WHERE"
                    + " RU.FK_ID_Usuario=U.ID_Usuario"
                    + " AND RU.FK_ID_Rol=R.ID_Rol"
                    + " AND U.status = ?"
                    + " AND U.usuario = ?"
                    + " AND U.password = ?";
            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setString(1, status);
            pstmt.setString(2, usuario);
            pstmt.setString(3, JHash.getStringMessageDigest(password, JHash.MD5));

            rs = pstmt.executeQuery();

            if (rs.next()) {
                listToSend = new LinkedList();
                listToSend.add(rs.getString(1));
                listToSend.add(rs.getString(2));
                listToSend.add(rs.getString(3));
                listToSend.add(rs.getString(4));
            }

            endConnection(jscp, conn, pstmt, rs);
        } catch (Exception ex) {
            listToSend = null;
            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listToSend;
    }

    //Metodo que retorna los permisos de un Usuario.
    public final LinkedList selectPermisosUsuarios(String ID_Usuario) {
        LinkedList listToSend = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        LinkedList llaux = null;

        try {
            SQLSentence = ""
                    + " SELECT"
                    + " P.nombrePermiso"
                    + " FROM PERMISO P"
                    + " , ROL_PERMISO RP"
                    + " , ROL R"
                    + " , ROL_USUARIO RU"
                    + " WHERE RP.FK_ID_Permiso = P.ID_Permiso"
                    + " AND RP.FK_ID_Rol = R.ID_Rol"
                    + " AND RU.FK_ID_Rol = R.ID_Rol"
                    + " AND RU.FK_ID_Usuario = ?";

            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setInt(1, Integer.parseInt(ID_Usuario));

            rs = pstmt.executeQuery();

            listToSend = new LinkedList();
            while (rs.next()) {
                listToSend.add(rs.getString(1));
            }

            endConnection(jscp, conn, pstmt, rs);
        } catch (Exception ex) {
            listToSend = null;
            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listToSend;
    }

    //retorna el idPlantel y nombrePlantel de la Tabla Plantel
    public final LinkedList getSelectPlantel() {
        LinkedList listToSend = null;
        LinkedList listAux = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            SQLSentence = ""
                    + " SELECT"
                    + " P.ID_Plantel"
                    + " ,P.nombre"
                    + " FROM PLANTEL P"
                    + " ORDER BY nombre";

            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            rs = pstmt.executeQuery();
            listToSend = new LinkedList();
            while (rs.next()) {
                listAux = new LinkedList();
                listAux.add(rs.getString(1));
                listAux.add(rs.getString(2));
                listToSend.add(listAux);
            }

            endConnection(jscp, conn, pstmt, rs);
        } catch (Exception ex) {
            listToSend = null;
            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listToSend;
    }

    //Metodo que verifica si existe ficha Técnica de un plantel
    public final boolean getFichaTecnica(int idPlantel) {
        boolean existe = false;

        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            SQLSentence = ""
                    + "SELECT P.nombre "
                    + " FROM PLANTEL P, "
                    + " ACADEMICO A, "
                    + " ETAPA E, "
                    + " INFRAESTRUCTURA I"
                    + " WHERE P.ID_Plantel=A.FK_ID_Plantel"
                    + " AND P.ID_Plantel=E.FK_ID_Plantel"
                    + " AND P.ID_Plantel=I.FK_ID_Plantel"
                    + " AND P.ID_Plantel=?";

            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setInt(1, idPlantel);
            rs = pstmt.executeQuery();

            if (rs.getRow() == 0) {
                existe=false;
            } else {
                existe=true;
            }

            endConnection(jscp, conn, pstmt, rs);
        } catch (Exception ex) {
            
            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="INSERT">
    //</editor-fold>
}
