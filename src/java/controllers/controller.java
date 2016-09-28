/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author emmanuel
 */
public final class controller extends HttpServlet{
    
  
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected  void processRequest(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException, Exception {
   
           System.out.println("Ejecuto metodo");
   }
    
    
    
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
   
    @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
   {
       
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
   protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException
   {
       
       try {
           processRequest(request, response);
       } catch (Exception ex) {
           Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   // </editor-fold>
}
