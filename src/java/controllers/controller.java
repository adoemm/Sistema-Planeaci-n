/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
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
import jspread.core.util.PageParameters;
import jspread.core.util.SessionUtil;
import jspread.core.util.UTime;
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
                {
                    if (request.getParameter("LogInPage") != null) {
                        //aqui consulta el usuario en Base de Datos.
                        if (request.getParameter("captcha").equals(session.getAttribute("captcha")) && request.getParameter("captcha").equalsIgnoreCase("") == false) {
                            if (request.getParameter("user").equalsIgnoreCase("") == false && request.getParameter("pass").equalsIgnoreCase("") == false) {

                                LinkedList infoUser = null;
                                LinkedList<String> accessos = null;

                                infoUser = quid.select_idUsuario(request.getParameter("user"), request.getParameter("pass"), "ACTIVO");

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
                                    accessos = quid.select_permisosPorUsuarios(infoUser.get(0).toString());
                                    session.setMaxInactiveInterval(3 * 60 * 60); // 2hrs * 60 min * 60 seg
                                    accessos.add("LoggedUser");
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
