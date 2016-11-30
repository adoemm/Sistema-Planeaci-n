<%@page import="jspread.core.util.SystemUtil"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<%    try {
        if (fine) {
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                LinkedList<String> access4ThisPage = new LinkedList();
                access4ThisPage.add("filesStage");
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    Iterator it = null;
                    LinkedList listAux = null;
%>
<!DOCTYPE html>
<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Archivos</title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>
        <script type="text/javascript" language="javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
        </script>
        <script type="text/javascript">
            var xhr = new XMLHttpRequest();
            var conecction = null;
            var flag = 1;
            function showButton(idBoton) {
                var boton = document.getElementById(idBoton);
                boton.style.display = "block";

            }
            function hideButton(idBoton) {
                var boton = document.getElementById(idBoton);
                boton.style.display = "none";

            }
            function testConection() {
                var result2 = 0;
                if (flag === 1) {
                    result2 = Ping('<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/PingPage.jsp');
                    if (result2 === 0) {
                        cancelarUpload();
                        alert("Se perdio la conexión con el servidor.");
                    }
                }
                return result2;
            }
            function Ping(recurso) {
                result = 0;
                $.ajax({
                    type: 'POST'
                    , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                    , cache: false
                    , url: recurso
                    , data: ''
                    , async: false
                    , timeout: 5000
                    , success: function (data) {
                        result = 1;
                    }
                    , error: function () {
                        window.clearInterval(conecction);
                        conecction = null;
                        flag = 0;
                        result = 0;
                    }
                });
                return result;
            }
            function eliminarArchivo(idTableArchivo, mainID, idArchivo, target) {//mainID es el ID del bien o del software
                $.ajax({type: 'POST'
                    , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                    , cache: false
                    , async: true
                    , url: "<%=PageParameters.getParameter("mainController")%>"
                    , data: '<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&mainID=' + mainID + '&FormFrom=' + target + '&idTableArchivo=' + idTableArchivo + '&idArchivo=' + idArchivo
                    , success: function (response) {
                        if (response.indexOf("title: \"Error\"") >= 0) {
                            $('#wrapper').find('#divResult').html(response);
                        } else {
                            $('#wrapper').find('#divDocumentos').html(response);
                        }
                    }});
            }
            function getDocumentos(mainID) {
                $('#wrapper').find('#divDocumentos').html('');
                if (mainID !== null) {
                    $.ajax({type: 'POST'
                        , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                        , cache: false
                        , async: true
                        , url: "<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("ajaxFunctions")%>/getArchivos.jsp"
                        , data: '<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&FormFrom=<%=request.getParameter("FormFrom")%>&mainID=' + mainID
                        , success: function (response) {
                            $('#wrapper').find('#divDocumentos').html(response);
                        }});
                }
            }
            function msg(msg, tipo) {
                $.msgBox({
                    title: "Información",
                    content: msg,
                    type: tipo,
                    buttons: [{value: "Aceptar"}],
                    opacity: 0.75,
                    success: function (result) {
                    }
                });
            }
            function restartForm() {
                var pBar = document.getElementById("progressBar");
                pBar.value = 0;
                document.getElementById('progressNumber').innerHTML = '0%';
            }
            function fileSelected() {
                var file = document.getElementById('fileToUpload').files[0];
                if (file) {
                    var fileSize = 0;
                    if (file.size > 1024 * 1024)
                        fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
                    else
                        fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
                    document.getElementById('fileName').innerHTML = 'Nombre del archivo: ' + file.name;
                    document.getElementById('fileSize').innerHTML = 'Tamaño del archivo: ' + fileSize;
                    document.getElementById('fileType').innerHTML = 'Tipo de archivo: ' + file.type;
                }
            }
//funcion para subir multiples archivos y formularios con diversos campos
//la funcion encripta los textarea y los input tipo text por cuestiones de acentos y ñ's
            function uploadMultipleFiles(form) {
                var fd = new FormData();
                for (i = 0; i < form.elements.length; i++)
                {
                    if (form.elements[i].type === "file") {
                        fd.append("fileToUpload", form.elements[i].files[0]);
                    } else if (form.elements[i].type === "text"
                            || form.elements[i].type === "textarea") {
                        var valor = "";
                        $.ajax({type: 'POST'
                            , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                            , cache: false
                            , async: true
                            , url: "<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("ajaxFunctions")%>/getEncodeParam.jsp"
                            , data: '<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&param=' + document.getElementById('descripcion').value
                            , success: function (response) {
                                valor = response.toString();
                            }
                        });
                        fd.append(form.elements[i].name, valor);
                    } else {
                        fd.append(form.elements[i].name, form.elements[i].value);
                    }
                }
                xhr.upload.addEventListener("progress", uploadProgress, false);
                xhr.addEventListener("load", uploadComplete, false);
                xhr.upload.addEventListener("error", uploadFailed, false);
                xhr.upload.addEventListener("abort", uploadCanceled, false);
                document.getElementById('status').innerHTML = "Enviando";
                xhr.open("POST", "<%=PageParameters.getParameter("mainController")%>", true);
            <%
                if (Integer.parseInt(PageParameters.getParameter("timeOutToUploadFile").toString()) > 0) {
            %>
                xhr.timeout = <%=PageParameters.getParameter("timeOutToUploadFile")%>;
            <%
                }
            %>
                xhr.ontimeout = function () {
                    alert("Tiempo de espera agotado.");
                    restartForm();
                };
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 404) {
                            alert("Recurso no encontrado.");
                            restartForm();
                        }
                    }
                };
                if (navigator.onLine) {
                    var result3 = null;
                    flag = 1;
                    result3 = testConection();
                    if (result3 === 1) {
                        startTestConecction();
                        xhr.send(fd);
                    }
                } else {
                    alert("Sin conexión. Por favor verifique su conexión de red.");
                }
            }
//la funcion encrpta los textarea y los input tipo tex por cuestiones de acentos y ñ's
            function uploadFile() {
                var fd = new FormData();
                fd.append("fileToUpload", document.getElementById('fileToUpload').files[0]);
                fd.append("FormFrom", "insertObjetoArchivo");
                fd.append("nombreObjeto", "<%=request.getParameter("nombreObjeto")%>");
                fd.append("idObjeto", document.getElementById('idObjeto').value);
                fd.append("idTipoArchivo", document.getElementById('idTipoArchivo').value);
                fd.append("nombreArchivo", document.getElementById('nombreArchivo').value);
                fd.append("tipoAcceso", document.getElementById('tipoAcceso').value);
                fd.append("keywords", document.getElementById('keywords').value);

                var desc = "";
                $.ajax({type: 'POST'
                    , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                    , cache: false
                    , async: false
                    , url: "<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("ajaxFunctions")%>/getEncodeParam.jsp"
                    , data: '<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&param=' + document.getElementById('descripcion').value
                    , success: function (response) {
                        $('#wrapper').find('#divDesc').html(response.toString());
                        desc = response.toString();

                    }});
                fd.append("descripcion", desc);
                //var xhr = new XMLHttpRequest();
                xhr.upload.addEventListener("progress", uploadProgress, false);
                xhr.addEventListener("load", uploadComplete, false);
                xhr.upload.addEventListener("error", uploadFailed, false);
                xhr.upload.addEventListener("abort", uploadCanceled, false);
                document.getElementById('status').innerHTML = "Enviando";
                xhr.open("POST", "<%=PageParameters.getParameter("mainController")%>", true);
            <%
                if (Integer.parseInt(PageParameters.getParameter("timeOutToUploadFile").toString()) > 0) {
            %>
                xhr.timeout = <%=PageParameters.getParameter("timeOutToUploadFile")%>;
            <%
                }
            %>
                xhr.ontimeout = function () {
                    alert("Tiempo de espera agotado.");
                    restartForm();
                };
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 404) {
                            alert("Recurso no encontrado.");
                            restartForm();
                        }
                    }
                };
                if (navigator.onLine) {
                    var result3 = null;
                    flag = 1;
                    result3 = testConection();
                    if (result3 === 1) {
                        startTestConecction();
                        xhr.send(fd);
                    }
                } else {
                    alert("Sin conexión. Por favor verifique su conexión de red.");
                }
            }

            function uploadProgress(evt) {
                if (evt.lengthComputable) {
                    var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                    var pBar = document.getElementById("progressBar");
                    pBar.value = percentComplete;
                    document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '% Procesando... ';
                }
                else {
                    document.getElementById('progressNumber').innerHTML = ' no es posible calcular. ';
                }
            }

            function uploadComplete(evt) {
                closeDialogBox('floatBoxAdjuntar');
                var response = evt.target.responseText;
                document.getElementById('status').innerHTML = "" + response;
                $('#wrapper').find('#divResult').html(response);
//                restartForm();
//                if (response.indexOf("title: \"Error\"") < 0) {
//                    document.getElementById('descripcion').value = '';
//                    document.getElementById('idTipoArchivo').selectedIndex = "0";
//                }
            }

            function uploadFailed(evt) {
                if (xhr.readyState === 4) {
                    if (xhr.status === 0) {
                        alert("Verifique que el tamaño del archivo no exceda de 1.5MB o verifique su conexión a internet.");
                    }
                } else {
                    alert("Ocurrio un error al subir el archivo.");
                }
                restartForm();
            }

            function uploadCanceled(evt) {
                alert("La operación ha sido cancelada.");
                restartForm();
            }
            function cancelarUpload() {
                xhr.abort();
            }

            window.history.forward();
            function noBack() {
                window.history.forward();
            }
            function startTestConecction() {
                conecction = setInterval(testConection, 5000);
            }
//            function openDialogBox(idFloatBox) {
//                document.getElementById('overlay').style.display = 'block';
//                document.getElementById(idFloatBox).style.display = 'block';
//            }
//            function closeDialogBox(idFloatBox) {
//                document.getElementById(idFloatBox).style.display = 'none';
//                document.getElementById('overlay').style.display = 'none';
//            }
            function getDetallesArchivo(idArchivo) {
                if (idArchivo !== null) {
                    $.ajax({type: 'POST'
                        , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                        , cache: false
                        , async: true
                        , url: "<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("ajaxFunctions")%>/getDetalles4Archivo.jsp"
                        , data: '<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&idArchivo=' + idArchivo
                        , success: function (response) {
                            $('#wrapper').find('#divDetallesArchivo').html(response);
                            openDialogBox('floatBoxDetalles');
                        }});
                }
            }
            function enviarInfo(form) {
                closeDialogBox('floatBoxDetalles');
                $.ajax({type: 'POST'
                    , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                    , cache: false
                    , async: false
                    , url: '<%=PageParameters.getParameter("mainController")%>'
                    , data: $(form).serialize()
                    , success: function (response) {
                        $('#wrapper').find('#divResult').html(response);
                        if (!(response.indexOf("title: \"Error\"") >= 0)) {
                            location.reload();
                        }
                    }});
            }
        </script>
        <%@ include file="/gui/pageComponents/dataTablesFullFunctionParameters.jsp"%>
    </head>
    <body  onload="populate();">
        <div id="wrapper">
            <div id="divBody">
                <jsp:include page='<%=("" + PageParameters.getParameter("logo"))%>' />
                
                <div class="form-container" width="100%">
                    <p></p>
                    <table width="100%" height="25px" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="64%" height="25" align="left" valign="top">
                            </td>
                            <td width="36" align="right" valign="top">
                                <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                            </td>
                        </tr>                        
                    </table>
                    <br>
                    <br>
                    <div id="contenent_info" >
                        <form name="usuario" method="post" action="" enctype="application/x-www-form-urlencoded" id="form">
                            <fieldset>
                                <legend>Archivos</legend>
                                <div style="text-align: right;">
                                <img src="<%=PageParameters.getParameter("imgRsc")%>/icons/window-close.png" onclick="window.close();" title="Cerrar esta ventana" width="28" height="28" alt="Cerrar esta ventana">
                                </div>
                                <table border="0" width="100%" cellpadding="0" cellspacing="0" class="display" id="example">
                                    <thead>
                                        <tr>
                                            <th>NP</th>
                                            <th>Tipo</th>
                                            <th>Nombre</th>
                                            <th>Tamaño MB</th>
                                            <th>Ext</th>
                                            <th>Acceso</th>
                                            <th>Fecha Actualización</th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                                <table border="0" width="100%" cellpadding="0" cellspacing="0" class="display" id="exampleFilters">
                                    <tfoot>
                                        <tr>
                                            <th>NP</th>
                                            <th>Tipo</th>
                                            <th>Nombre</th>
                                            <th>Tamaño</th>
                                            <th>Ext</th>
                                            <th>Fecha Actualización</th>
                                        </tr>
                                    </tfoot>
                                    <thead>
                                        <tr>
                                            <th>NP</th>
                                            <th>Tipo</th>
                                            <th>Nombre</th>
                                            <th>Tamaño</th>
                                            <th>Ext</th>
                                            <th>Fecha Actualización</th>
                                        </tr>
                                    </thead>
                                </table>
                            </fieldset>
                        </form>
                    </div> 
                    <div class="form-container" width="100%"> 
                        <input type="button" value="Adjuntar Archivo" onclick="openDialogBox('floatBoxAdjuntar');">
                        <div id="overlay" class="overlay" ></div>
                        <div id="floatBoxAdjuntar" class="floatBox">
                            <div class="closeButton2" onclick="closeDialogBox('floatBoxAdjuntar');"></div>
                            <div>
                                <form name="filetoUpload" id="filetoUpload" enctype="multipart/form-data" method="post" action="">
                                    <input type="hidden" name="idObjeto" id="idObjeto" value="<%=request.getParameter("idObjeto")%>">
                                    <input type="hidden" name="FormFrom" id="FormFrom" value="insertObjetoArchivo">
                                    <fieldset style="border-radius: 10px;">
                                        <legend style="text-align: left;">Adjuntar Archivo</legend>  
                                        <div>
                                            <div style="text-align: left;">
                                                <label for="idTipoArchivo">*Tipo de Archivo</label>
                                                <select name="idTipoArchivo" id="idTipoArchivo">
                                                    <option value=""></option>
                                                    <%
                                                        Iterator t = /*QUID.select_Tipo_Archivo().iterator();*/ null;
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
                                                <input type="text" name="nombreArchivo" id="nombreArchivo" style="margin-left: 52px;">
                                            </div>
                                            <div style="text-align: left;">
                                                <label for="descripcion">*Descripción</label>
                                                <textarea name="descripcion" id="descripcion" cols="40" rows="5" style="resize:none"></textarea>
                                            </div>
                                            <div  style="text-align: left;">
                                                <label for="tipoAcceso">*Tipo de Accesso</label>    
                                                <select name="tipoAcceso" id="tipoAcceso" style="margin-left: 73px;">
                                                    <option value=""></option>
                                                    <option value="<%=WebUtil.encode(session, "PRIVADO")%>">Privado</option>
                                                    <option value="<%=WebUtil.encode(session, "PUBLICO")%>">Público</option>
                                                </select>
                                            </div>    
                                            <div  style="text-align: left;">
                                                <label for="keywords">*Keywords (separadas por ,)</label>
                                                <input type="text" name="keywords" id="keywords">
                                            </div>
                                        </div>
                                        <div>
                                            <table border="0" align="left" width="95%">
                                                <tr align="left">
                                                    <td width="70%">
                                                        <img src="<%=PageParameters.getParameter("imgRsc")%>/icons/Gnome-Document-Open-64.png" title="Abrir Archivo" width="36" height="36" alt="Abrir Archivo">
                                                        <label  for="fileToUpload">Seleccione un archivo</label>
                                                    </td>
                                                    <td>
                                                        <div id="divUpload" style="margin-top: 45px;">
                                                            <input  type="file" name="fileToUpload" id="fileToUpload" onchange="fileSelected();" size="75"/>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table> 
                                        </div>
                                        <div style="text-align: left;">                     
                                            <div>
                                                <table border="0">
                                                    <tr> 
                                                        <td><img src="<%=PageParameters.getParameter("imgRsc")%>/icons/Gnome-Dialog-Information-64.png" title="Información" width="48" height="48" alt="Información">
                                                        </td>
                                                        <td>
                                                            <div id="status">
                                                                Seleccione un archivo.
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                            <p></p>
                                            <p></p>
                                            <div id="fileName"></div>
                                            <div id="fileSize"></div>
                                            <div id="fileType"></div>
                                            <p></p> 
                                            <progress id="progressBar" class="progressBarThin" max="100" value="0" weight ="100%" width="100%"></progress>
                                            <div id="progressNumber"></div>
                                            <div style=" text-align: right;">
                                                <input type="button" onclick="uploadFile();
                                                        return false;" value="Guardar" name="enviar" id="enviar">
                                                <input type="button" id="cancelar" name="cancelar" value="Cancelar" onclick="cancelarUpload();">
                                                <input align="left"type="button" value="Cerrar" name="subirDespues" id="subirDespues" onclick="closeDialogBox('floatBoxAdjuntar');"/>        
                                            </div>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>
                        </div>
                        <div id="floatBoxDetalles" class="floatBox">
                            <div class="closeButton2" onclick="closeDialogBox('floatBoxDetalles');"></div>
                            <div id="divDetallesArchivo"></div>
                        </div>
                    </div>
                    <div id="divResult"></div>
                </div>
                <div id="divFoot">
                    <jsp:include page='<%=(PageParameters.getParameter("footer"))%>' />
                </div> 
                <script type="text/javascript" language="javascript" charset="utf-8">
                    function populate()
                    {
                        var t = $('#example').dataTable();
                        t.fnClearTable();
                        var data = new Array();
                        var ids = new Array();
                        var cells = new Array();

                    <%
                        int cont = 0;
                        it = QUID.select_ObjetoArchivo(WebUtil.decode(session, request.getParameter("nombreObjeto")),
                                WebUtil.decode(session, request.getParameter("idObjeto")),
                                session.getAttribute("FK_ID_Plantel").toString(),
                                SystemUtil.haveAcess("publicAccess", userAccess),
                                SystemUtil.haveAcess("VerTodo", userAccess)).iterator();
                        while (it.hasNext()) {
                            listAux = (LinkedList) it.next();
                    %>
                        ids[<%=cont%>] = '<%=WebUtil.encode(session, listAux.get(0))%>';
                        cells[0] = '<%=cont + 1%>';
                        cells[1] = '<%=listAux.get(8).toString().replaceAll("'", "&#39;")%>';
                        cells[2] = '<%=listAux.get(6).toString().replaceAll("'", "&#39;")%>';
                        cells[3] = '<%=listAux.get(5).toString().replaceAll("'", "&#39;")%>';
                        cells[4] = '<%=listAux.get(2).toString().replaceAll("'", "&#39;")%>';
                        cells[5] = '<%=listAux.get(9).toString().replaceAll("'", "&#39;")%>';
                        cells[6] = '<%=listAux.get(3).toString().replaceAll("'", "&#39;")%>';
                        cells[7] = '<img src="<%=PageParameters.getParameter("imgRsc")%>/icons/<%=SystemUtil.getMymeTypeIcon(listAux.get(2).toString())%>" title="Detalles" width="22" height="23" alt="Detalles" onclick="getDetallesArchivo(\'<%=WebUtil.encode(session, listAux.get(0))%>\');">';
                        cells[8] = '<a target="_blank" href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/DescargaArchivo.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&idArchivo=<%=WebUtil.encode(session, listAux.get(0))%>&see=<%=WebUtil.encode(session, "1")%>&hash=<%=WebUtil.encode(session, "si")%>"><img src="<%=PageParameters.getParameter("imgRsc")%>/icons/Gnome-Go-Down-64.png" title="Descargar" width="22" height="23" alt="Descargar"></a>';
                        cells[9] = '<a href="<%=PageParameters.getParameter("mainController")%>?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&idArchivo=<%=WebUtil.encode(session, listAux.get(0))%>&FormFrom=deleteObjetoArchivo&idObjetoArchivo=<%=WebUtil.encode(session, listAux.get(7))%>&idObjeto=<%=request.getParameter("idObjeto")%>&nombreObjeto=<%=request.getParameter("nombreObjeto")%>"><img src="<%=PageParameters.getParameter("imgRsc")%>/icons/Gnome-Process-Stop-64.png" title="Eliminar" width="22" height="23" alt="Eliminar"></a>';
                        data[<%=cont%>] = t.fnAddData(cells, false);
                    <%
                            cont++;
                        }
                    %>

                        $("tfoot th").each(function (i) {
                            this.innerHTML = fnCreateSelect(t.fnGetColumnData(i));
                            $('select', this).change(function () {
                                t.fnFilter($(this).val(), i);
                            });
                        });
                        t.fnDraw();
                        document.getElementById('contenent_info').style.display = 'block';
                    }
                </script>
            </div>
        </div>
    </body>
</html>
<%
} else {
    //System.out.println("Usuario No valido para esta pagina");
%>                
<%@ include file="/gui/pageComponents/invalidUser.jsp"%>
<%    }
} else {
    //System.out.println("No se ha encontrado a imix");
%>
<%@ include file="/gui/pageComponents/invalidParameter.jsp"%>
<%        }
    }
} catch (Exception ex) {
    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
%>
<%@ include file="/gui/pageComponents/handleUnExpectedError.jsp"%>
</body>
</html>
<%
        //response.sendRedirect(redirectURL);
    }
%>