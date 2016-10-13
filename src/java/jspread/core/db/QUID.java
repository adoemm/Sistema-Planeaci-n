/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jspread.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import jspread.core.models.Transporter;
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
                existe = false;
            } else {
                existe = true;
            }

            endConnection(jscp, conn, pstmt, rs);
        } catch (Exception ex) {

            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }

    public final LinkedList selectDataPlantel(int idPlantel) {

        LinkedList listToSend = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            SQLSentence = ""
                    + "SELECT P.nombre"
                    + " , P.direccion"
                    + " , M.municipio"
                    + " , E.estado"
                    + " , P.anioCreacion"
                    + " , P.claveCentroTrabajo"
                    + " , P.telefono"
                    + " , P.correo"
                    + " , P.latitud"
                    + " , P.longitud"
                    + " , P.nombreCompletoDirector"
                    + " FROM PLANTEL P"
                    + " , MUNICIPIO M"
                    + " , ESTADO E"
                    + " WHERE P.FK_ID_Estado=E.ID_Estado"
                    + " AND P.FK_ID_Municipio=M.ID_Municipio"
                    + " AND P.ID_Plantel=?";

            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setInt(1, idPlantel);
            rs = pstmt.executeQuery();
            listToSend = new LinkedList();
            while (rs.next()) {
                listToSend.add(rs.getString(1));
                listToSend.add(rs.getString(2));
                listToSend.add(rs.getString(3));
                listToSend.add(rs.getString(4));
                listToSend.add(rs.getString(5));
                listToSend.add(rs.getString(6));
                listToSend.add(rs.getString(7));
                listToSend.add(rs.getString(8));
                listToSend.add(rs.getString(9));
                listToSend.add(rs.getString(10));
                listToSend.add(rs.getString(11));
            }

            endConnection(jscp, conn, pstmt, rs);
        } catch (Exception ex) {

            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listToSend;

    }

    public final LinkedList selectDataPlantel(String nombrePlantel) {

        LinkedList listToSend = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            SQLSentence = ""
                    + "SELECT P.nombre"
                    + " , P.direccion"
                    + " , M.municipio"
                    + " , E.estado"
                    + " , P.anioCreacion"
                    + " , P.claveCentroTrabajo"
                    + " , P.telefono"
                    + " , P.correo"
                    + " , P.latitud"
                    + " , P.longitud"
                    + " , P.nombreCompletoDirector"
                    + " FROM PLANTEL P"
                    + " , MUNICIPIO M"
                    + " , ESTADO E"
                    + " WHERE P.FK_ID_Estado=E.ID_Estado"
                    + " AND P.FK_ID_Municipio=M.ID_Municipio"
                    + " AND P.nombre=?";

            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setString(1, nombrePlantel);
            rs = pstmt.executeQuery();
            listToSend = new LinkedList();
            while (rs.next()) {
                listToSend.add(rs.getString(1));
                listToSend.add(rs.getString(2));
                listToSend.add(rs.getString(3));
                listToSend.add(rs.getString(4));
                listToSend.add(rs.getString(5));
                listToSend.add(rs.getString(6));
                listToSend.add(rs.getString(7));
                listToSend.add(rs.getString(8));
                listToSend.add(rs.getString(9));
                listToSend.add(rs.getString(10));
                listToSend.add(rs.getString(11));
            }

            endConnection(jscp, conn, pstmt, rs);
        } catch (Exception ex) {

            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listToSend;
    }
    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="INSERT">

    public final Transporter insertFichaTecnica(
              String personalAdmin
            , String docentes
            , String matricula
            , String turno
            , String carrerasVigentes
            , String carrerasLiquidas
            , String fechaActualizacion
            , String periodoEscolar
            , String idPlantel
            , String superficiePredio
            , String superficieConstruccion
            , String aulasDidacticas
            , String laboratorios
            , String talleresComputo
            , String otrosTalleres
            , String areaAdmin
            , String biblioteca
            , String salaAudio
            , String casetaVigilancia
            , String cafeteria
            , String bardaPerimetral
            , String areasDeportivas) {
        Transporter tport = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        try {

            SQLSentence = ""
                    + " INSERT INTO ACADEMICO ("
                    + " personalAdmin"
                    + " ,docentes"
                    + " ,matricula"
                    + " ,turno"
                    + " ,carrerasVigentes"
                    + " ,carrerasLiquidadas"
                    + " ,fechaActualizacion"
                    + " ,periodoEscolar"
                    + " ,FK_ID_Plantel"
                    + ")"
                    + " VALUES ("
                    + "  ?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " );"
                    + " INSERT INTO INFRAESTRUCTURA("
                    + "superficiePredio"
                    + ",superficieConstruida"
                    + ",aulasDidacticas"
                    + ",laboratorios"
                    + ",talleresComputo"
                    + ",otrosTalleres"
                    + ",areasAdmin"
                    + ",biblioteca"
                    + " ,salaAudio"
                    + ",casetaVigilancia"
                    + ",cafeteria"
                    + ",bardaPerimetral"
                    + ",areasDeportivas"
                    + ",fechaActualizacion"
                    + ",FK_ID_Plantel"
                    + ")"
                    + "VALUES ("
                    + "?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ",?"
                    + ");";

       
            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setString(1, personalAdmin);
            pstmt.setString(2, docentes);
            pstmt.setString(3, matricula);
            pstmt.setString(4, turno);
            pstmt.setString(5, carrerasVigentes);
            pstmt.setString(6, carrerasLiquidas);
            pstmt.setString(7, fechaActualizacion);
            pstmt.setString(8, periodoEscolar);
            pstmt.setString(9, idPlantel);
            pstmt.setString(10, superficiePredio);
            pstmt.setString(11, superficieConstruccion);
            pstmt.setString(12, aulasDidacticas);
            pstmt.setString(13, laboratorios);
            pstmt.setString(14, talleresComputo);
            pstmt.setString(15, otrosTalleres);
            pstmt.setString(16, areaAdmin);
            pstmt.setString(17, biblioteca);
            pstmt.setString(18, salaAudio);
            pstmt.setString(19, casetaVigilancia);
            pstmt.setString(20, cafeteria);
            pstmt.setString(21, bardaPerimetral);
            pstmt.setString(22, areasDeportivas);
            pstmt.setString(23, fechaActualizacion);
            pstmt.setString(24, idPlantel);
            pstmt.executeUpdate();
            tport = new Transporter(0, "El registro se creo correctamente");
            endConnection(jscp, conn, pstmt);
        } catch (Exception ex) {
            endConnection(jscp, conn, pstmt);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            tport = new Transporter(1, "Error inesperado. " + ex.getMessage());

        }
        return tport;
    }

    //</editor-fold>
}
