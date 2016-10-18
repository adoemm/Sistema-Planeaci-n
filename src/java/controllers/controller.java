/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Parameter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jspread.core.db.QUID;
import jspread.core.models.Transporter;
import jspread.core.util.PageParameters;
import jspread.core.util.SessionUtil;
import jspread.core.util.UTime;
import jspread.core.util.UserUtil;
import jspread.core.util.WebUtil;
import systemSettings.SystemSettings;

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
                            }
                            // </editor-fold>
                        } else {
                            out.println("UPS.... Algo malo ha pasado");
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
                        Integer.parseInt(WebUtil.decode(session,request.getParameter("idPlantel")))
                        , Integer.parseInt(WebUtil.decode(session,request.getParameter("idAcademico")))
                        , Integer.parseInt(WebUtil.decode(session,request.getParameter("idInfraestructura")))
                        , request.getParameter("valueNombrePlantel")
                        , request.getParameter("valueDireccion")
                        , request.getParameter("valueCCT")
                        , Integer.parseInt(request.getParameter("valueAnioCreacion"))
                        , request.getParameter("valueTelefono")
                        , request.getParameter("valueCorreo")
                        , request.getParameter("valueLatitud")
                        , request.getParameter("valueLongitud")
                        , request.getParameter("valueDirector")
                        , Integer.parseInt(request.getParameter("valuePersonalAdmin"))
                        , Integer.parseInt(request.getParameter("valueDocentes"))
                        , Integer.parseInt(request.getParameter("valueMatricula"))
                        , request.getParameter("valueTurno")
                        , request.getParameter("valuePeriodoEscolar")
                        , request.getParameter("valueCarrerasVigentes")
                        , request.getParameter("valueCarrerasLiquidadas")
                        , Double.parseDouble(request.getParameter("valueSuperficiePredio"))
                        , Double.parseDouble(request.getParameter("valueSuperficieConstruida"))
                        , Integer.parseInt(request.getParameter("valueAulasDidacticas"))
                        , Integer.parseInt(request.getParameter("valueLaboratorios"))
                        , request.getParameter("valueBiblioteca")
                        , Integer.parseInt(request.getParameter("valueTalleresComputo"))
                        , request.getParameter("valueOtrosTalleres")
                        , request.getParameter("valueAreaAdministrativa")
                        , request.getParameter("valueCafeteria")
                        , request.getParameter("valueSalaMedios")
                        , request.getParameter("valueCaseta")
                        , request.getParameter("valueBardaPerimetral")
                        , Integer.parseInt(request.getParameter("valueAreasDeportivas"))
                        , UTime.calendar2aaaamd(Calendar.getInstance())
                       
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
