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
                    + " INFRAESTRUCTURA I"
                    + " WHERE P.ID_Plantel=A.FK_ID_Plantel"
                    + " AND P.ID_Plantel=I.FK_ID_Plantel"
                    + " AND P.ID_Plantel=?";

            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setInt(1, idPlantel);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                existe = true;

            } else {
                existe = false;

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

    public final LinkedList selectDataToDataSheet(int idPlantel) {

        LinkedList listToSend = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            SQLSentence = ""
                    + "SELECT P.nombre"
                    + ", P.direccion"
                    + ", M.municipio"
                    + ", E.estado"
                    + ", P.claveCentroTrabajo"
                    + ", P.anioCreacion"
                    + ", P.telefono"
                    + ", P.correo"
                    + ", P.latitud"
                    + ", P.longitud"
                    + ", P.nombreCompletoDirector"
                    + ", A.personalAdmin"
                    + ", A.docentes"
                    + ", A.matricula"
                    + ", A.turno"
                    + ", A.periodoEscolar"
                    + ", A.carrerasVigentes"
                    + ", A.carrerasLiquidadas"
                    + ", I.superficiePredio"
                    + ", I.superficieConstruida"
                    + ", I.aulasDidacticas"
                    + ", I.laboratorios"
                    + ", I.biblioteca"
                    + ", I.talleresComputo"
                    + ", I.otrosTalleres"
                    + ", I.areasAdmin"
                    + ", I.cafeteria"
                    + ", I.salaAudio"
                    + ", I.casetaVigilancia"
                    + ", I.bardaPerimetral"
                    + ", I.areasDeportivas"
                    + ", A.ID_Academico"
                    + ", I.ID_Infraestructura"
                    + ", M.ID_Municipio"
                    + ", E.ID_Estado"
                    + " from PLANTEL P, ACADEMICO A, INFRAESTRUCTURA I, MUNICIPIO M, ESTADO E"
                    + " WHERE A.FK_ID_Plantel=P.ID_Plantel"
                    + " AND I.FK_ID_Plantel=P.ID_Plantel"
                    + " AND P.FK_ID_Municipio=M.ID_Municipio"
                    + " AND P.FK_ID_Estado=E.ID_Estado"
                    + " AND A.fechaActualizacion=I.fechaActualizacion"
                    + " AND P.ID_Plantel=?"
                    + " AND A.fechaActualizacion=I.fechaActualizacion";

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
                listToSend.add(rs.getString(12));
                listToSend.add(rs.getString(13));
                listToSend.add(rs.getString(14));
                listToSend.add(rs.getString(15));
                listToSend.add(rs.getString(16));
                listToSend.add(rs.getString(17));
                listToSend.add(rs.getString(18));
                listToSend.add(rs.getString(19));
                listToSend.add(rs.getString(20));
                listToSend.add(rs.getString(21));
                listToSend.add(rs.getString(22));
                listToSend.add(rs.getString(23));
                listToSend.add(rs.getString(24));
                listToSend.add(rs.getString(25));
                listToSend.add(rs.getString(26));
                listToSend.add(rs.getString(27));
                listToSend.add(rs.getString(28));
                listToSend.add(rs.getString(29));
                listToSend.add(rs.getString(30));
                listToSend.add(rs.getString(31));
                listToSend.add(rs.getString(32));
                listToSend.add(rs.getString(33));
                listToSend.add(rs.getString(34));
                listToSend.add(rs.getString(35));

            }

            endConnection(jscp, conn, pstmt, rs);
        } catch (Exception ex) {

            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listToSend;
    }

    public final String selectNombrePlantel(int idPlantel) {

        String nombrePlantel = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SQLSentence = ""
                    + " SELECT P.nombre"
                    + " FROM PLANTEL P"
                    + " WHERE P.ID_Plantel=?";
            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setInt(1, idPlantel);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                nombrePlantel = rs.getString(1);
            }
        } catch (Exception ex) {

            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nombrePlantel;
    }

    //Metodo que consulta las Etapa de Desarrollo.
    public final LinkedList selectEtapasDesarrollo(int idPlantel) {

        LinkedList listToSend = null;
        LinkedList listAux = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SQLSentence = ""
                    + " SELECT E.ID_Etapa"
                    + " , E.fechaFin"
                    + " , E.nombreEtapa"
                    + " , E.descripccion"
                    + " , E.status"
                    + " , E.avance"
                    + " , E.numeroEtapa"
                    + " FROM ETAPA E"
                    + " WHERE E.FK_ID_Plantel=?";
            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setInt(1, idPlantel);
            rs = pstmt.executeQuery();

            listToSend = new LinkedList();

            while (rs.next()) {
                listAux = new LinkedList();
                listAux.add(rs.getString(1));
                listAux.add(rs.getString(2).toString().substring(0, 4));
                listAux.add(rs.getString(3));
                listAux.add(rs.getString(4));
                listAux.add(rs.getString(5));
                listAux.add(rs.getString(6));
                listAux.add(rs.getString(7));
                listToSend.add(listAux);
            }
        } catch (Exception ex) {

            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listToSend;
    }

    //Método que Detecta la Etapa mas Reciente de un Plantel.
    public final int selectNumeroEtapasDesarollo(int idPlantel) {

        int etapas = 0;
        LinkedList numeroEtapas = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SQLSentence = ""
                    + " SELECT E.numeroEtapa"
                    + " FROM ETAPA E"
                    + " WHERE E.FK_ID_Plantel=?";
            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setInt(1, idPlantel);
            rs = pstmt.executeQuery();
            numeroEtapas = new LinkedList();

            while (rs.next()) {
                numeroEtapas.add(rs.getString(1));

            }
            for (int i = 0; i < numeroEtapas.size(); i++) {
                int numeroEtapa = Integer.parseInt(numeroEtapas.get(i).toString());
                if (numeroEtapa > etapas) {
                    etapas = numeroEtapa;
                }
            }
        } catch (Exception ex) {

            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return etapas;
    }

    //Método que Consulta las Actividades de una Etapa de Desarrollo
    public final LinkedList selectActividades(int idEtapa) {

        LinkedList listToSend = null;
        LinkedList listAux = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            SQLSentence = ""
                    + "SELECT A.nombreActividad"
                    + " ,A.descripccion"
                    + " ,A.responsable"
                    + " ,A.costoOperacion"
                    + " ,A.porcentajeAvance"
                    + " FROM ACTIVIDAD A"
                    + " ,ETAPA E"
                    + " WHERE A.FK_ID_Etapa=E.ID_Etapa"
                    +" AND A.FK_ID_Etapa=?";

            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setInt(1, idEtapa);
            rs = pstmt.executeQuery();

            listToSend = new LinkedList();

            while (rs.next()) {
                listAux = new LinkedList();
                listAux.add(rs.getString(1));
                listAux.add(rs.getString(2));
                listAux.add(rs.getString(3));
                listAux.add(rs.getString(4));
                listAux.add(rs.getString(5));
                listToSend.add(listAux);
            }
        } catch (Exception ex) {

            endConnection(jscp, conn, pstmt, rs);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listToSend;
    }

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="INSERT">
    public final Transporter insertFichaTecnica(
            String personalAdmin,
            String docentes,
            String matricula,
            String turno,
            String carrerasVigentes,
            String carrerasLiquidas,
            String fechaActualizacion,
            String periodoEscolar,
            String idPlantel,
            String superficiePredio,
            String superficieConstruccion,
            String aulasDidacticas,
            String laboratorios,
            String talleresComputo,
            String otrosTalleres,
            String areaAdmin,
            String biblioteca,
            String salaAudio,
            String casetaVigilancia,
            String cafeteria,
            String bardaPerimetral,
            String areasDeportivas) {
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

    public final Transporter insertEtapaDesarrollo(int numeroEtapa,
            String nombreEtapa,
            String descripccionEtapa,
            String fechaInicioEtapa,
            String fechaFinEtapa,
            String statusEtapa,
            String fechaActualización,
            String tipoEtapa,
            double avanceEtapa,
            int numeroActividades,
            int idPlantel) {
        Transporter tport = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        try {

            SQLSentence = ""
                    + " INSERT INTO ETAPA ( "
                    + " numeroEtapa"
                    + " , nombreEtapa"
                    + " , descripccion"
                    + " , fechaInicio"
                    + " , fechaFin"
                    + " , status"
                    + " , fechaActualizacion"
                    + " , tipoEtapa"
                    + " , avance"
                    + " , numeroActividades"
                    + " , FK_ID_Plantel"
                    + ")"
                    + " VALUES ("
                    + " ?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " ,?"
                    + " );";
            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setInt(1, numeroEtapa);
            pstmt.setString(2, nombreEtapa);
            pstmt.setString(3, descripccionEtapa);
            pstmt.setString(4, fechaInicioEtapa);
            pstmt.setString(5, fechaFinEtapa);
            pstmt.setString(6, statusEtapa);
            pstmt.setString(7, fechaActualización);
            pstmt.setString(8, tipoEtapa);
            pstmt.setDouble(9, avanceEtapa);
            pstmt.setInt(10, numeroActividades);
            pstmt.setInt(11, idPlantel);
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
    //<editor-fold defaultstate="collapsed" desc="UPDATE">
    public final Transporter updateFichaTecnica(int idPlantel, int idAcademico, int idInfraestructura, String nombrePlantel, String direccion, String claveTrabajo, int anioCreacion, String telefono, String correo, String latitud, String longitud, String director, int personalAministrativo, int docentes, int matricula, String turno, String periodoEscolar, String carrerasVigentes, String carrerasLiquidadas, double superficiePredio, double superficieConstruccion, int aulasDidacticas, int laboratorios, String biblioteca, int talleresComputo, String otrosTalleres, String areaAdministrativa, String cafeteria, String salaAudio, String casetaVigilancia, String bardaPerimetral, int areasDeportivas, String fechaActualizacion
    ) {
        Transporter tport = null;
        JSpreadConnectionPool jscp = null;
        Connection conn = null;
        String SQLSentence = null;
        PreparedStatement pstmt = null;
        try {
            SQLSentence = ""
                    + "UPDATE  PLANTEL SET"
                    + " nombre = ?"
                    + ", direccion = ?"
                    + ", claveCentroTrabajo = ?"
                    + ", anioCreacion = ?"
                    + ", telefono = ?"
                    + ", correo = ?"
                    + ", latitud = ?"
                    + ", longitud = ?"
                    + ", nombreCompletoDirector = ?"
                    + " WHERE ID_Plantel = ?;"
                    + " UPDATE ACADEMICO SET"
                    + " personalAdmin = ?"
                    + ", docentes = ?"
                    + ", matricula = ?"
                    + ", turno = ?"
                    + ", periodoEscolar = ?"
                    + ", carrerasVigentes = ?"
                    + ", carrerasLiquidadas = ?"
                    + ", fechaActualizacion = ?"
                    + " WHERE ID_Academico = ?;"
                    + " UPDATE INFRAESTRUCTURA SET"
                    + " superficiePredio = ?"
                    + ", superficieConstruida = ?"
                    + ", aulasDidacticas = ?"
                    + ", laboratorios = ?"
                    + ", biblioteca = ?"
                    + ", talleresComputo = ?"
                    + ", otrosTalleres = ?"
                    + ", areasAdmin = ?"
                    + ", cafeteria = ?"
                    + ", salaAudio = ?"
                    + ", casetaVigilancia = ?"
                    + ", bardaPerimetral = ?"
                    + ", areasDeportivas = ?"
                    + ", fechaActualizacion = ?"
                    + " WHERE ID_Infraestructura = ?;";
            jscp = JSpreadConnectionPool.getSingleInstance();
            conn = jscp.getConnectionFromPool();
            pstmt = conn.prepareStatement(SQLSentence);
            pstmt.setQueryTimeout(statementTimeOut);
            pstmt.setString(1, nombrePlantel);
            pstmt.setString(2, direccion);
            pstmt.setString(3, claveTrabajo);
            pstmt.setInt(4, anioCreacion);
            pstmt.setString(5, telefono);
            pstmt.setString(6, correo);
            pstmt.setString(7, latitud);
            pstmt.setString(8, longitud);
            pstmt.setString(9, director);
            pstmt.setInt(10, idPlantel);
            pstmt.setInt(11, personalAministrativo);
            pstmt.setInt(12, docentes);
            pstmt.setInt(13, matricula);
            pstmt.setString(14, turno);
            pstmt.setString(15, periodoEscolar);
            pstmt.setString(16, carrerasVigentes);
            pstmt.setString(17, carrerasLiquidadas);
            pstmt.setString(18, fechaActualizacion);
            pstmt.setInt(19, idAcademico);
            pstmt.setDouble(20, superficiePredio);
            pstmt.setDouble(21, superficieConstruccion);
            pstmt.setInt(22, aulasDidacticas);
            pstmt.setInt(23, laboratorios);
            pstmt.setString(24, biblioteca);
            pstmt.setInt(25, talleresComputo);
            pstmt.setString(26, otrosTalleres);
            pstmt.setString(27, areaAdministrativa);
            pstmt.setString(28, cafeteria);
            pstmt.setString(29, salaAudio);
            pstmt.setString(30, casetaVigilancia);
            pstmt.setString(31, bardaPerimetral);
            pstmt.setInt(32, areasDeportivas);
            pstmt.setString(33, fechaActualizacion);
            pstmt.setInt(34, idInfraestructura);
            int rowCount = pstmt.executeUpdate();
            endConnection(jscp, conn, pstmt);
            tport = new Transporter(0, "Filas afectadas: " + rowCount);

        } catch (Exception ex) {
            endConnection(jscp, conn, pstmt);
            Logger.getLogger(QUID.class.getName()).log(Level.SEVERE, null, ex);
            tport = new Transporter(1, "Error inesperado.");
        }
        return tport;
    }

    //</editor-fold>
}
