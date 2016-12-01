/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jspread.core.db.QUID;
import jspread.core.models.Transporter;
import jspread.core.util.FileUtil;
import jspread.core.util.PageParameters;
import jspread.core.util.SessionUtil;
import jspread.core.util.StringUtil;
import jspread.core.util.SystemUtil;
import jspread.core.util.UTime;
import jspread.core.util.UserUtil;
import jspread.core.util.WebUtil;
import jspread.core.util.security.JHash;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author emmanuel
 */
public final class controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        request.setCharacterEncoding(PageParameters.getParameter("charset").toString());
        response.setCharacterEncoding(PageParameters.getParameter("charset").toString());
        response.setContentType(PageParameters.getParameter("servletSetContentType").toString());
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session;
        PrintWriter out;
        QUID quid;
        LinkedList<String> userAccess;
        quid = new QUID();
        quid.setRequest(request);
        session = request.getSession(true);
        SessionUtil.addIfNotExistSession(session);
        out = response.getWriter();
        try {
            try {
                if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                    response.sendRedirect(PageParameters.getParameter("SiteOnMaintenanceURL").toString());
                } else // <editor-fold defaultstate="collapsed" desc="Realizando LogIn de usuario">
                //si proviene de la página  de login aqui se detectara y se validara al usuario
                {
                    if (request.getParameter("LogInPage") != null) {
                        //aqui consulta el usuario en Base de Datos.
//                        if (request.getParameter("captcha").equals(session.getAttribute("captcha")) && request.getParameter("captcha").equalsIgnoreCase("") == false) {
                        if (true) {
                            if (request.getParameter("user").equalsIgnoreCase("") == false && request.getParameter("pass").equalsIgnoreCase("") == false) {

                                LinkedList infoUser = null;
                                LinkedList<String> accessos = null;

                                infoUser = quid.selectIdUsuario(request.getParameter("user"), request.getParameter("pass"), "ACTIVO");

                                if (infoUser != null) {
                                    session.removeAttribute("captcha");
                                    SessionUtil.clearNCloseSession(session);
                                    session = request.getSession(true);
                                    session.setAttribute("tipoRol", infoUser.get(3));
                                    session.setAttribute("userID", infoUser.get(0));
                                    session.setAttribute("userName", infoUser.get(1));
                                    session.setAttribute("FK_ID_Plantel", infoUser.get(2));
                                    //asignacion de permisos
                                    //LinkedList<String> accessos = new LinkedList();
                                    accessos = quid.selectPermisosUsuarios(infoUser.get(0).toString());
                                    session.setMaxInactiveInterval(3 * 60 * 60); // 2hrs * 60 min * 60 seg

                                    //accesos del usaurio y parametros del mismo
                                    session.setAttribute("userAccess", accessos);
                                    SessionUtil.addIfNotExistSession(session);
                                    request.getRequestDispatcher(PageParameters.getParameter("mainMenuServLet")).forward(request, response);
                                } else {
                                    this.getServletConfig().getServletContext().getRequestDispatcher(
                                            "" + PageParameters.getParameter("msgUtil")
                                            + "/msgNRedirectFull.jsp?title=Error&type=error&msg=Usuario o password incorrectos.&url=/" + PageParameters.getParameter("appName") + PageParameters.getParameter("LogInPage")).forward(request, response);
                                }
                            } else {
                                this.getServletConfig().getServletContext().getRequestDispatcher(
                                        "" + PageParameters.getParameter("msgUtil")
                                        + "/msgNRedirectFull.jsp?title=Error&type=error&msg=El usuario y password no pueden estar vacíos.&url=/" + PageParameters.getParameter("appName") + PageParameters.getParameter("LogInPage")).forward(request, response);
                            }
                        } else {
                            this.getServletConfig().getServletContext().getRequestDispatcher(
                                    "" + PageParameters.getParameter("msgUtil")
                                    + "/msgNRedirectFull.jsp?title=Error&type=error&msg=Código de verificación incorrecto.&url=/" + PageParameters.getParameter("appName") + PageParameters.getParameter("LogInPage")).forward(request, response);
                        }
                        // </editor-fold> 
                        // <editor-fold defaultstate="expanded" desc="Validando que sea un usuario logeado">
                    } else if (session.getAttribute("userAccess") == null) {
                        response.sendRedirect("/" + PageParameters.getParameter("appName") + PageParameters.getParameter("LogInPage"));
                        // </editor-fold> 
                    } else // <editor-fold defaultstate="collapsed" desc="Cerrando sesion">
                    {
                        if (request.getParameter("exit") != null) {
                            //session.invalidate();
                            this.clearNCloseSession(session, request, response, quid, out);
                            //quid.insertLog("SysLogOut", "exit", "", "", "", "");
                            response.sendRedirect(PageParameters.getParameter("mainContext") + PageParameters.getParameter("LogInPage").toString());
                            // </editor-fold>
                            // <editor-fold defaultstate="collapsed" desc="Revisando de que form viene">
                        } else if (request.getParameter("FormForm") != null) {
                            switch (request.getParameter("FormForm")) {
                                case "agregaFichaTecnica":
                                    this.agregaFichaTecnica(session, request, response, quid, out);
                                    break;
                                case "modificaFichaTecnica":
                                    this.modificaFichaTecnica(session, request, response, quid, out);
                                    break;
                                case "agregaEtapaDesarrollo":
                                    this.agregaEtapaDesarrollo(session, request, response, quid, out);
                                    break;
                                case "modificaEtapaDesarrollo":
                                    this.modificaEtapaDesarrollo(session, request, response, quid, out);
                                    break;
                                case "eliminaStage":
                                    this.eliminaEtapa(session, request, response, quid, out);
                                    break;
                                case "agregaActivity":
                                    this.agregaActividad(session, request, response, quid, out);
                                    break;
                                case "modificaActivity":
                                    this.modificaActividad(session, request, response, quid, out);
                                    break;
                                case "eliminaActivity":
                                    this.eliminaActividad(session, request, response, quid, out);
                                    break;
                            }
                            // </editor-fold>
                           } else if (ServletFileUpload.isMultipartContent(new ServletRequestContext(request))) {
                            this.subirArchivo(session, request, response, quid, out);
                           
                        } else {
                            out.println("UPS.... Algo malo ha pasado");
                        }
                    }
                }

            } catch (Exception ex) {
                Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            quid = null;
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Cierre de Sesion.">
    private void clearNCloseSession(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        String param = "";
        Enumeration enu = session.getAttributeNames();
        while (enu.hasMoreElements()) {
            param = enu.nextElement().toString();
            session.setAttribute(param, null);
            session.removeAttribute(param);
        }
        session.invalidate();
        session = null;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Metodos del Sistema.">  

    private void agregaFichaTecnica(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        String access4Insert = "addDataSheet";
        LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
        if (UserUtil.isAValidUser(access4Insert, userAccess)) {
            if (this.validaFormFichaTecnica(session, request, response, quid, out)) {

                Transporter tport = quid.insertFichaTecnica(
                        request.getParameter("valuePersonalAdmin"), request.getParameter("valueDocentes"), request.getParameter("valueMatricula"), request.getParameter("valueTurno"), request.getParameter("valueCarrerasVigentes"), request.getParameter("valueCarrerasLiquidadas"), UTime.calendar2aaaamd(Calendar.getInstance()), request.getParameter("valuePeriodoEscolar"), WebUtil.decode(session, request.getParameter("idPlantel")), request.getParameter("valueSuperficiePredio"), request.getParameter("valueSuperficieConstruida"), request.getParameter("valueAulasDidacticas"), request.getParameter("valueLaboratorios"), request.getParameter("valueTalleresComputo"), request.getParameter("valueOtrosTalleres"), request.getParameter("valueAreaAdministrativa"), request.getParameter("valueBiblioteca"), request.getParameter("valueSalaMedios"), request.getParameter("valueCaseta"), request.getParameter("valueCafeteria"), request.getParameter("valueBardaPerimetral"), request.getParameter("valueAreasDeportivas")
                );

                if (tport.getCode() == 0) {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msgNRedirectFull.jsp?title=Ficha Técnica&type=info&msg=Se ha Agregado Ficha Técnica de Plantel.&url=" + PageParameters.getParameter("mainMenu")).forward(request, response);

                } else {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msg.jsp?title=Error&type=error&msg=Ocurrio un error al actualizar los datos.").forward(request, response);
                }
            }
        } else {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirectFull.jsp?title=Error&type=error&msg=Usted no Cuenta con el permiso para realizar esta acción.&url=" + PageParameters.getParameter("mainMenu")).forward(request, response);

        }
    }

    private boolean validaFormFichaTecnica(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        boolean valido = false;

        if (request.getParameter("valuePersonalAdmin").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el número de Personal Administrativo.").forward(request, response);
        } else if (request.getParameter("valueDocentes").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el número de Docentes de Plantel.").forward(request, response);
        } else if (request.getParameter("valueMatricula").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el número de Matrícula de Plantel.").forward(request, response);
        } else if (request.getParameter("valueTurno").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el Turno o Turnos de Plantel.").forward(request, response);
        } else if (request.getParameter("valuePeriodoEscolar").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba Periodo Escolar.").forward(request, response);
        } else if (request.getParameter("valueCarrerasVigentes").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba las carreras vigentes de Plantel.").forward(request, response);
        } else if (request.getParameter("valueCarrerasLiquidadas").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba las carreras en Liquidación de Plantel.").forward(request, response);
        } else if (request.getParameter("valueSuperficiePredio").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba la Superficie Predio de Plantel.").forward(request, response);
        } else if (request.getParameter("valueSuperficieConstruida").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba la Superficie Construida de Plantel.").forward(request, response);
        } else if (request.getParameter("valueAulasDidacticas").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el número de Aulas Didácticas de Plantel.").forward(request, response);
        } else if (request.getParameter("valueLaboratorios").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el número de Laboratorios de Plantel.").forward(request, response);
        } else if (request.getParameter("valueBiblioteca").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba si el Plantel tiene o no Biblioteca.").forward(request, response);
        } else if (request.getParameter("valueTalleresComputo").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el número de Talleres de Cómputo.").forward(request, response);
        } else if (request.getParameter("valueAreaAdministrativa").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba si el Plantel tiene o no Área Administrativa.").forward(request, response);
        } else if (request.getParameter("valueCafeteria").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba si el Plantel tiene o no Cafetería.").forward(request, response);
        } else if (request.getParameter("valueSalaMedios").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba si el Plantel tiene o no Sala de Medios o Sala AudioVisual.").forward(request, response);
        } else if (request.getParameter("valueCaseta").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba si el Plantel tiene o no Caseta de Vigilancia.").forward(request, response);
        } else if (request.getParameter("valueBardaPerimetral").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba si el Plantel tiene o no Barda Perimetral.").forward(request, response);
        } else if (request.getParameter("valueAreasDeportivas").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el número de Areas Deportivas del Plantel.").forward(request, response);
        } else {
            valido = true;
        }
        return valido;
    }

    private void modificaFichaTecnica(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        String access4Insert = "updateDataSheet";
        LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
        if (UserUtil.isAValidUser(access4Insert, userAccess)) {
            if (this.validaFormFichaTecnica(session, request, response, quid, out)) {

                Transporter tport = quid.updateFichaTecnica(
                        Integer.parseInt(WebUtil.decode(session, request.getParameter("idPlantel"))), Integer.parseInt(WebUtil.decode(session, request.getParameter("idAcademico"))), Integer.parseInt(WebUtil.decode(session, request.getParameter("idInfraestructura"))), request.getParameter("valueNombrePlantel"), request.getParameter("valueDireccion"), request.getParameter("valueCCT"), Integer.parseInt(request.getParameter("valueAnioCreacion")), request.getParameter("valueTelefono"), request.getParameter("valueCorreo"), request.getParameter("valueLatitud"), request.getParameter("valueLongitud"), request.getParameter("valueDirector"), Integer.parseInt(request.getParameter("valuePersonalAdmin")), Integer.parseInt(request.getParameter("valueDocentes")), Integer.parseInt(request.getParameter("valueMatricula")), request.getParameter("valueTurno"), request.getParameter("valuePeriodoEscolar"), request.getParameter("valueCarrerasVigentes"), request.getParameter("valueCarrerasLiquidadas"), Double.parseDouble(request.getParameter("valueSuperficiePredio")), Double.parseDouble(request.getParameter("valueSuperficieConstruida")), Integer.parseInt(request.getParameter("valueAulasDidacticas")), Integer.parseInt(request.getParameter("valueLaboratorios")), request.getParameter("valueBiblioteca"), Integer.parseInt(request.getParameter("valueTalleresComputo")), request.getParameter("valueOtrosTalleres"), request.getParameter("valueAreaAdministrativa"), request.getParameter("valueCafeteria"), request.getParameter("valueSalaMedios"), request.getParameter("valueCaseta"), request.getParameter("valueBardaPerimetral"), Integer.parseInt(request.getParameter("valueAreasDeportivas")), UTime.calendar2aaaamd(Calendar.getInstance())
                );

                if (tport.getCode() == 0) {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msgNRedirectFull.jsp?title=Ficha Técnica&type=info&msg=Se ha Modificado Ficha Técnica de Plantel.&url=" + PageParameters.getParameter("mainMenu")).forward(request, response);

                } else {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msg.jsp?title=Error&type=error&msg=Ocurrio un error al actualizar los datos.").forward(request, response);
                }
            }
        } else {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirectFull.jsp?title=Error&type=error&msg=Usted no Cuenta con el permiso para realizar esta acción.&url=" + PageParameters.getParameter("mainMenu")).forward(request, response);

        }
    }

    private void agregaEtapaDesarrollo(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        String access4Insert = "addStage";
        LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
        if (UserUtil.isAValidUser(access4Insert, userAccess)) {
            if (this.validaFormAgregaEtapaDesarrollo(session, request, response, quid, out)) {
                int numeroEtapa = quid.selectNumeroEtapasDesarollo(Integer.parseInt(WebUtil.decode(session, request.getParameter("idPlantel")))) + 1;
                Transporter tport = quid.insertEtapaDesarrollo(
                        numeroEtapa,
                        request.getParameter("valueNombreEtapa"),
                        request.getParameter("valueDescripcionStage"),
                        request.getParameter("valueFechaInicioEtapa"),
                        request.getParameter("valueFechaFinEtapa"),
                        request.getParameter("valueStatusEtapa"),
                        UTime.calendar2aaaamd(Calendar.getInstance()),
                        request.getParameter("valueTipoEtapa"),
                        0.0,
                        Integer.parseInt(request.getParameter("valueNumeroActividades")),
                        Integer.parseInt(WebUtil.decode(session, request.getParameter("idPlantel"))));
                if (tport.getCode() == 0) {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msgNRedirectFull.jsp?title=Etapas de Desarrollo&type=info&msg=Se ha Agregado una Etapa de Desarrollo al Plantel.&url="
                            + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaEtapaDesarrollo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel")).forward(request, response);

                } else {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msg.jsp?title=Error&type=error&msg=Ocurrio un error al Agregar Etapa de Desarrollo.").forward(request, response);
                }
            }
        } else {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirectFull.jsp?title=Error&type=error&msg=Usted no Cuenta con el permiso para realizar esta acción.&url="
                    + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaEtapaDesarrollo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel")).forward(request, response);

        }
    }

    private boolean validaFormAgregaEtapaDesarrollo(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        boolean valido = false;

        if (request.getParameter("valueNombreEtapa").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba Nombre de la Etapa.").forward(request, response);
        } else if (request.getParameter("valueDescripcionStage").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor  escriba la descripcción de la Etapa.").forward(request, response);
        } else if (!UTime.validaFecha(request.getParameter("valueFechaInicioEtapa"))) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba Correctamente la Fecha de Inicio.").forward(request, response);
        } else if (!UTime.validaFecha(request.getParameter("valueFechaFinEtapa"))) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba Correctamente la Fecha de Finalización.").forward(request, response);
        } else if (request.getParameter("valueStatusEtapa").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor coloque un Estatus de la Etapa.").forward(request, response);
        } else if (request.getParameter("valueTipoEtapa").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el Tipo de Etapa.").forward(request, response);
        } else if (!StringUtil.isValidDouble(request.getParameter("valueNumeroActividades"))) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el número de Actividades para la Etapa.").forward(request, response);
        } else {
            valido = true;
        }
        return valido;
    }

    private void modificaEtapaDesarrollo(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        String access4Insert = "updateStage";
        LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
        if (UserUtil.isAValidUser(access4Insert, userAccess)) {
            if (this.validaFormAgregaEtapaDesarrollo(session, request, response, quid, out)) {

                Transporter tport = quid.updateEtapa(
                        Integer.parseInt(WebUtil.decode(session, request.getParameter("idEtapa"))),
                        request.getParameter("valueNombreEtapa"),
                        request.getParameter("valueDescripcionStage"),
                        request.getParameter("valueFechaInicioEtapa"),
                        request.getParameter("valueFechaFinEtapa"),
                        request.getParameter("valueStatusEtapa"),
                        request.getParameter("valueTipoEtapa"),
                        Integer.parseInt(request.getParameter("valueNumeroActividades")),
                        UTime.calendar2aaaamd(Calendar.getInstance())
                );
                if (tport.getCode() == 0) {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msgNRedirectFull.jsp?title=Etapas de Desarrollo&type=info&msg=Se ha Modificado la Etapa de Desarrollo.&url="
                            + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaEtapaDesarrollo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel")).forward(request, response);

                } else {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msg.jsp?title=Error&type=error&msg=Ocurrio un error al Modificar la Etapa de Desarrollo.").forward(request, response);
                }
            }
        } else {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirectFull.jsp?title=Error&type=error&msg=Usted no Cuenta con el permiso para realizar esta acción.&url="
                    + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaEtapaDesarrollo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel")).forward(request, response);

        }
    }
    
    private void eliminaEtapa(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        String access4Insert = "deleteStage";
        LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
        if (UserUtil.isAValidUser(access4Insert, userAccess)) {
            Transporter tport = quid.deleteActividades(Integer.parseInt(request.getParameter("idEtapa")));
            Transporter tport2 = quid.deleteEtapa(Integer.parseInt(request.getParameter("idEtapa")));
            
            if (tport.getCode() == 0 && tport2.getCode() == 0) {
                this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msgNRedirectFull.jsp?title=Etapas de Desarrollo&type=info&msg=Se ha Eliminado la Etapa de Desarrollo.&url="
                            + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaEtapaDesarrollo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel")).forward(request, response);

            } else {
                this.getServletConfig().getServletContext().getRequestDispatcher(
                        "" + PageParameters.getParameter("msgUtil")
                        + "/msg.jsp?title=Error&type=error&msg=Ocurrio un error al Eliminar la Etapa.").forward(request, response);
            }

        } else {

           this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirectFull.jsp?title=Error&type=error&msg=Usted no Cuenta con el permiso para realizar esta acción.&url="
                    + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaEtapaDesarrollo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel")).forward(request, response);

        }
    }

    private void agregaActividad(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        String access4Insert = "addActivity";
        LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
        if (UserUtil.isAValidUser(access4Insert, userAccess)) {
            if (this.validaFormAgregaActividad(session, request, response, quid, out)) {
                Transporter tport = quid.insertActividad(
                        request.getParameter("valueNombreActividad"),
                        request.getParameter("valueDescripccionActividad"),
                        request.getParameter("valueCantidadActividad"),
                        Double.parseDouble(request.getParameter("valueCostoOperacionActividad")),
                        request.getParameter("valueResponsableActividad"),
                        request.getParameter("valueStatusActividad"),
                        request.getParameter("valueFechaInicioActividad"),
                        request.getParameter("valueFechaFinActividad"),
                        Double.parseDouble(request.getParameter("valueAvanceActividad")),
                        UTime.calendar2aaaamd(Calendar.getInstance()),
                        Integer.parseInt(WebUtil.decode(session, request.getParameter("idEtapa"))));
                LinkedList data = quid.selectNumberOfActivitiesAndAvance(Integer.parseInt(WebUtil.decode(session, request.getParameter("idEtapa"))));
                double avance = calculaAvanceEtapa(data, Double.parseDouble(request.getParameter("valueAvanceActividad")));
                Transporter tport2 = quid.updateAdvanceToStage(Integer.parseInt(WebUtil.decode(session, request.getParameter("idEtapa"))), avance);

                if (tport.getCode() == 0 && tport2.getCode() == 0) {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msgNRedirectFull.jsp?title=Actividades&type=info&msg=Se ha Agregado una Actividad a la Etapa.&url="
                            + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaActividad.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel") + "_param_idEtapa=" + request.getParameter("idEtapa")).forward(request, response);

                } else {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msg.jsp?title=Error&type=error&msg=Ocurrio un error Agregar Etapa de Desarrollo.").forward(request, response);
                }
            }
        } else {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirectFull.jsp?title=Error&type=error&msg=Usted no Cuenta con el permiso para realizar esta acción.&url="
                    + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaActividad.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel") + "_param_idEtapa=" + request.getParameter("idEtapa")).forward(request, response);

        }
    }

    private boolean validaFormAgregaActividad(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        boolean valido = false;

        if (request.getParameter("valueNombreActividad").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el Nombre de la Actividad.").forward(request, response);
        } else if (request.getParameter("valueDescripccionActividad").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor  escriba la descripcción de la Actividad.").forward(request, response);
        } else if (request.getParameter("valueCantidadActividad").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba la cantidad de Espacios a Construir.").forward(request, response);
        } else if (!StringUtil.isValidDouble(request.getParameter("valueCostoOperacionActividad"))) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el costo de Operación.").forward(request, response);
        } else if (request.getParameter("valueResponsableActividad").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el Responsable de la Actividad.").forward(request, response);
        } else if (request.getParameter("valueStatusActividad").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor seleccione el Estatus.").forward(request, response);
        } else if (!UTime.validaFecha(request.getParameter("valueFechaInicioActividad"))) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba Correctamente la Fecha de Inicio de la Actividad.").forward(request, response);
        } else if (!UTime.validaFecha(request.getParameter("valueFechaFinActividad"))) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba Correctamente la Fecha de Finalización de la Actividad.").forward(request, response);

        } else if (!StringUtil.isValidDouble(request.getParameter("valueAvanceActividad"))) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msg.jsp?title=Error&type=error&msg=Por Favor escriba el Avance de la Actividad.").forward(request, response);
        } else {
            valido = true;
        }
        return valido;
    }

    private void modificaActividad(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        String access4Insert = "updateActivity";
        LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
        if (UserUtil.isAValidUser(access4Insert, userAccess)) {
            if (this.validaFormAgregaActividad(session, request, response, quid, out)) {

                Transporter tport = quid.updateActividad(
                        Integer.parseInt(WebUtil.decode(session, request.getParameter("idActividad"))),
                        request.getParameter("valueNombreActividad"),
                        request.getParameter("valueDescripccionActividad"),
                        request.getParameter("valueCantidadActividad"),
                        Double.parseDouble(request.getParameter("valueCostoOperacionActividad")),
                        request.getParameter("valueResponsableActividad"),
                        request.getParameter("valueStatusActividad"),
                        request.getParameter("valueFechaInicioActividad"),
                        request.getParameter("valueFechaFinActividad"),
                        Double.parseDouble(request.getParameter("valueAvanceActividad")),
                        UTime.calendar2aaaamd(Calendar.getInstance()));
                LinkedList data = quid.selectNumberOfActivitiesAndAvance(Integer.parseInt(WebUtil.decode(session, request.getParameter("idEtapa"))));
                double avance = calculaAvanceEtapa(data, Double.parseDouble(request.getParameter("valueAvanceActividad")));
                Transporter tport2 = quid.updateAdvanceToStage(Integer.parseInt(WebUtil.decode(session, request.getParameter("idEtapa"))), avance);

                if (tport.getCode() == 0 && tport2.getCode() == 0) {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msgNRedirectFull.jsp?title=Actividades&type=info&msg=Se ha Modificado la Actividad a la Etapa.&url="
                            + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaActividad.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel") + "_param_idEtapa=" + request.getParameter("idEtapa")).forward(request, response);

                } else {
                    this.getServletConfig().getServletContext().getRequestDispatcher(
                            "" + PageParameters.getParameter("msgUtil")
                            + "/msg.jsp?title=Error&type=error&msg=Ocurrio un error al Modificar Actividad de la Etapa.").forward(request, response);
                }
            }
        } else {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirectFull.jsp?title=Error&type=error&msg=Usted no Cuenta con el permiso para realizar esta acción.&url="
                    + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaActividad.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel") + "_param_idEtapa=" + request.getParameter("idEtapa")).forward(request, response);

        }
    }

    private void eliminaActividad(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        String access4Insert = "deleteActivity";
        LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
        if (UserUtil.isAValidUser(access4Insert, userAccess)) {
            Transporter tport = quid.deleteActividad(Integer.parseInt(request.getParameter("idActividad")));
            if (tport.getCode() == 0) {
                this.getServletConfig().getServletContext().getRequestDispatcher(
                        "" + PageParameters.getParameter("msgUtil")
                        + "/msgNRedirectFull.jsp?title=Actividades&type=info&msg=Se ha Eliminado la Actividad.&url="
                        + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaActividad.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel") + "_param_idEtapa=" + request.getParameter("idEtapa")).forward(request, response);

            } else {
                this.getServletConfig().getServletContext().getRequestDispatcher(
                        "" + PageParameters.getParameter("msgUtil")
                        + "/msg.jsp?title=Error&type=error&msg=Ocurrio un error al Eliminar la Actividad.").forward(request, response);
            }

        } else {

            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirectFull.jsp?title=Error&type=error&msg=Usted no Cuenta con el permiso para realizar esta acción.&url="
                    + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/consultaActividad.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_idPlantel=" + request.getParameter("idPlantel") + "_param_idEtapa=" + request.getParameter("idEtapa")).forward(request, response);

        }
    }

    //Metodo que calcula el avance de la etapa con respecto al avance de las Actividades.
    private double calculaAvanceEtapa(LinkedList data, double advanceToActivity) {
        int numeroActividades = Integer.parseInt(data.get(0).toString());
        double PAT = Double.parseDouble(data.get(1).toString());
        double PATaux = 0;
        double PA = 0;
        PA = 100 / numeroActividades;
        PATaux = (advanceToActivity * PA) / 100;
        PAT += PATaux;

        return PAT;
    }
    
    private void subirArchivo(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out) throws Exception {
        String FormFrom = "";
        if (ServletFileUpload.isMultipartContent(new ServletRequestContext(request))) {
            System.out.println("Tamaño del archivo: " + request.getContentLength());
            if (PageParameters.getParameter("fileSizeLimited").equals("1")
                    && (request.getContentLength() == -1//Este valor aparece cuando se desconoce el tamano
                    || request.getContentLength() > Integer.parseInt(PageParameters.getParameter("maxSizeToUpload")))) {
                this.getServletConfig().getServletContext().getRequestDispatcher(
                        "" + PageParameters.getParameter("msgUtil")
                        + "/msgNBack.jsp?title=Error&type=error&msg=El tamaño máximo del archivo es de " + StringUtil.formatDouble1Decimals(Double.parseDouble(PageParameters.getParameter("maxSizeToUpload")) / 1048576) + " MBytes.").forward(request, response);
            } else {    
                
                ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
                List items = upload.parseRequest(request);
                Iterator i = items.iterator();
                LinkedList filesToUpload = new LinkedList();
                HashMap parameters = new HashMap();
                while (i.hasNext()) {
                    FileItem item = (FileItem) i.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equalsIgnoreCase("FormFrom")) {
                            FormFrom = item.getString();
                        } else {
                            parameters.put(item.getFieldName(), item.getString());
                        }
                    } else {
                        filesToUpload.add(item);
                    }
                }
                switch (FormFrom) {
                    
                    case "insertObjetoArchivo":
                        this.insertarObjetoArchivo(session, request, response, quid, out, parameters, filesToUpload, FormFrom);
                        break;
                }
            }
        }
    }
    
    private void insertarObjetoArchivo(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out, HashMap parameters, LinkedList filesToUpload, String FormFrom) throws Exception {
        if (parameters.get("idTipoArchivo") == null || parameters.get("idTipoArchivo").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirect.jsp?title=Error&type=error&msg=Seleccione el tipo de archivo.&url=" + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/Insert_ObjetoArchivo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis())
                    + "_param_nombreObjeto=" + parameters.get("nombreObjeto") + "_param_idObjeto=" + parameters.get("idObjeto")).forward(request, response);
        } else if (parameters.get("nombreArchivo") == null || parameters.get("nombreArchivo").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirect.jsp?title=Error&type=error&msg=Escriba el nombre del archivo.&url=" + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/Insert_ObjetoArchivo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis())
                    + "_param_nombreObjeto=" + parameters.get("nombreObjeto") + "_param_idObjeto=" + parameters.get("idObjeto")).forward(request, response);
        } else if (parameters.get("descripcion") == null
                || parameters.get("descripcion").toString().trim().equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirect.jsp?title=Error&type=error&msg=Escriba una descripción.&url=" + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/Insert_ObjetoArchivo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis())
                    + "_param_nombreObjeto=" + parameters.get("nombreObjeto") + "_param_idObjeto=" + parameters.get("idObjeto")).forward(request, response);
        } else if (parameters.get("tipoAcceso").equals("")) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirect.jsp?title=Error&type=error&msg=Seleccione el tipo de acceso para el archivo.&url=" + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/Insert_ObjetoArchivo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis())
                    + "_param_nombreObjeto=" + parameters.get("nombreObjeto") + "_param_idObjeto=" + parameters.get("idObjeto")).forward(request, response);
        } else if (filesToUpload.isEmpty()) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirect.jsp?title=Error&type=error&msg=No ha seleccionado ningún archivo.&url=" + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/Insert_ObjetoArchivo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis())
                    + "_param_nombreObjeto=" + parameters.get("nombreObjeto") + "_param_idObjeto=" + parameters.get("idObjeto")).forward(request, response);
        } else if (!filesToUpload.isEmpty()) {
            String idObjeto = WebUtil.decode(session, parameters.get("idObjeto").toString());
            String fechaActualizacion = UTime.calendar2SQLDateFormat(Calendar.getInstance());
            String descripcion = WebUtil.decode(session, parameters.get("descripcion").toString());
            String ubicacionFisica = PageParameters.getParameter("folderDocs");
            String idTipoArchivo = WebUtil.decode(session, parameters.get("idTipoArchivo").toString());
            String nombreObjeto = WebUtil.decode(session, parameters.get("nombreObjeto").toString());
            String keyWords = parameters.get("keywords").toString();
            String nombreArchivo = parameters.get("nombreArchivo").toString();
            String FK_ID_Plantel = session.getAttribute("FK_ID_Plantel").toString();
            
            //File verifyFolder = new File(PageParameters.getParameter("folderDocs"));
            File verifyFolder = new File(ubicacionFisica);
            if (!verifyFolder.exists()) {
                verifyFolder.mkdirs();
            }
            int sucess = 0;
            for (int i = 0; i < filesToUpload.size(); i++) {
                FileItem itemToUpload = null;
                itemToUpload = (FileItem) filesToUpload.get(i);

                String extension = FileUtil.getExtension(itemToUpload.getName());
                String hashName = JHash.getFileDigest(itemToUpload.get(), "MD5") + extension;

                long tamanio = itemToUpload.getSize();

                if (this.validarDocumentExtension(session, request, response, quid, out, extension)) {
                    File fileToWrite = new File(ubicacionFisica, hashName);
                    Transporter tport = quid.insertArchivo4Objeto(
                            idObjeto,
                            nombreObjeto,
                            idTipoArchivo,
                            nombreArchivo,
                            descripcion,
                            ubicacionFisica,
                            extension,
                            fechaActualizacion,
                            tamanio,
                            WebUtil.decode(session, parameters.get("tipoAcceso").toString()),
                            keyWords,
                            hashName,
                            FK_ID_Plantel);
                    if (tport.getCode() == 0) {
                        if (!fileToWrite.exists()) {
                            itemToUpload.write(fileToWrite);
                        }
                        sucess += 1;
                    }
                } else {
                    sucess = -1;
                }
            }
            if (sucess != -1) {
                this.getServletConfig().getServletContext().getRequestDispatcher(
                        "" + PageParameters.getParameter("msgUtil")
                        + "/msgNRedirect.jsp?title=Operación Exitosa&type=info&msg=Se han guardado " + sucess + " de " + filesToUpload.size() + " archivos.&url=" + PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/Insert_ObjetoArchivo.jsp?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis())
                        + "_param_nombreObjeto=" + parameters.get("nombreObjeto") + "_param_idObjeto=" + parameters.get("idObjeto")).forward(request, response);
            }

        }

    }
    
    private boolean validarDocumentExtension(HttpSession session, HttpServletRequest request, HttpServletResponse response, QUID quid, PrintWriter out, String extension) throws Exception {
        boolean valido = false;
        if (!SystemUtil.isSystemAllowedExtension(extension)) {
            this.getServletConfig().getServletContext().getRequestDispatcher(
                    "" + PageParameters.getParameter("msgUtil")
                    + "/msgNRedirect.jsp?title=Error&type=error&msg=Tipo de archivo no valido.&url=" + PageParameters.getParameter("mainController") + "?" + WebUtil.encode(session, "imix") + "=" + WebUtil.encode(session, UTime.getTimeMilis()) + "_param_exit=1").forward(request, response);
        } else {
            valido = true;
        }
        return valido;
    }

    

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
           
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>
}
