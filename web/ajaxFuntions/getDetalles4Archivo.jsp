<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<%    try {
        if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
            LinkedList<String> access4ThisPage = new LinkedList();
            access4ThisPage.add("LoggedUser");
            LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");

            if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
%>
<form action="<%=PageParameters.getParameter("mainController")%>" id="updateArchivo" name="updateArchivo">
    <input type="hidden" name="<%=WebUtil.encode(session, "imix")%>" value="<%=WebUtil.encode(session, UTime.getTimeMilis())%>"/>
    <input type="hidden" name="FormFrom" value="updateArchivo"/>
    <input type="hidden" name="idArchivo" value="<%=request.getParameter("idArchivo")%>"/>
    <%
        LinkedList archivo = QUID.select_Archivo(WebUtil.decode(session, request.getParameter("idArchivo")));
    %>
    <fieldset style="border-radius: 10px;">
        <legend style="text-align: left;">Detalles</legend>
        <div style="text-align: left;">
            <label for="idTipoArchivo">*Tipo de Archivo</label>
            <select name="idTipoArchivo" id="idTipoArchivo">
                <option value="<%=WebUtil.encode(session, archivo.get(5))%>"><%=archivo.get(10)%></option>
                <%
                    Iterator t = QUID.select_Tipo_Archivo().iterator();
                    while (t.hasNext()) {
                        LinkedList datos = datos = (LinkedList) t.next();
                %>
                <option value="<%=WebUtil.encode(session, datos.get(0))%>"><%=datos.get(1)%></option>
                <%
                    }
                %>
            </select>
        </div>
        <div style="text-align: left;">
            <label for="nombreArchivo">*Nombre</label>
            <input type="text" name="nombreArchivo" id="nombreArchivo" value="<%=archivo.get(0)%>" style="margin-left: 52px;">
        </div>
        <div style="text-align: left;">
            <label for="descripcion">*Descripción</label>
            <textarea name="descripcion" id="descripcion" cols="40" rows="5" style="resize:none"><%=archivo.get(1)%></textarea>
        </div>
        <div  style="text-align: left;">
            <label for="tipoAcceso">*Tipo de Accesso</label>    
            <select name="tipoAcceso" style="margin-left: 73px;">
                <option value="<%=WebUtil.encode(session, archivo.get(11))%>"><%=archivo.get(11)%></option>
                <option value="<%=WebUtil.encode(session, "PRIVADO")%>">Privado</option>
                <option value="<%=WebUtil.encode(session, "PUBLICO")%>">Público</option>
            </select>
        </div>   
        <div style="text-align: left;">
            <label for="keywords">*Keywords (separadas por ,)</label>
            <input type="text" name="keywords" id="keywords" value="<%=archivo.get(7)%>">
        </div>
        <div style="text-align: left;">
            <label for="extension">*Extensión</label>
            <input type="text" name="extension" id="extension" value="<%=archivo.get(3)%>" style="margin-left: 121px;">
        </div>
        <div style="text-align: left;">
            <label for="tamanio">*Tamaño MB</label>
            <input type="text" name="tamanio" id="tamanio" value="<%=archivo.get(6)%>" style="margin-left: 109px;">
        </div>
        <div style="text-align: left;">
            <label for="fechaActualizacion">*Fecha Actualización</label>
            <input type="text" id="fechaActualizacion"   name="fechaActualizacion" value="<%=archivo.get(4)%>" size="10" MAXLENGTH="10" style="margin-left: 54px;">
        </div>
    </fieldset>
    <div style=" text-align: right; margin-right: 0px;">
        <a target="_blank" href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/DescargaArchivo.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&idArchivo=<%=request.getParameter("idArchivo")%>&see=<%=WebUtil.encode(session, "1")%>&hash=<%=WebUtil.encode(session, "si")%>">
            <input align="left" type="button" value="Descargar"/>
        </a>
        <input align="left" type="button" value="Guardar" onclick="enviarInfo(document.getElementById('updateArchivo'))"/>
        <input align="left" type="button" value="Cerrar" onclick="closeDialogBox('floatBoxDetalles');"/>
        <br>
        <br>
    </div>
</form>
<%
            } else {
                //System.out.println("Usuario No valido para esta pagina");
                out.print("Usted no cuenta con el permiso para accesar a esta pagina");
            }
        } else {
            //System.out.println("No se ha encontrado a imix");
            out.print("Peticion invalida");
        }
    } catch (Exception ex) {
        out.print("Lo sentimos la petición ha tenido un error :(");
    }
%>