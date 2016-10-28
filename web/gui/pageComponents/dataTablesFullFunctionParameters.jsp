<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@page import="jspread.core.util.PageParameters"%>

<script type="text/javascript" language="javascript" charset="utf-8">
    $(document).ready(function() {
        var indiFiltering = $('#example').dataTable({
            "bJQueryUI": true,
            "bAutoWidth": false,
            "aaSorting": [], //evita el ordermaniento de la primera columna
            "bDestroy": true, //permite reinicializar la tabla
            "bRetrieve": true, //permite reinicializar la tabla
            //"bScrollInfinite": false,
            //"sScrollY": "800px",
            //"sScrollX": "100%",
            //"sScrollXInner": "365%",
            "bScrollCollapse": true,
            "bPaginate": true,
            "sPaginationType": "full_numbers",
            "bProcessing": true,
            "bFilter": true,
            //"bServerSide": true,
            //"bSort": true,
            "bDeferRender": true,
            "oLanguage": {
                "sProcessing": "Procesando...",
                "sLengthMenu": "Mostrar _MENU_ registros",
                "sZeroRecords": "No se encontraron resultados",
                "sEmptyTable": "Ningún dato disponible en esta tabla",
                "sInfo": "Del _START_ al _END_ de _TOTAL_ registros",
                "sInfoEmpty": "Registros del 0 al 0 de un total de 0 registros",
                "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sInfoPostFix": "",
                "sSearch": "Buscar:",
                "sUrl": "",
                "sInfoThousands": ",",
                "sLoadingRecords": "Cargando...",
                "oPaginate": {
                    "sFirst": "<<",
                    "sLast": ">>",
                    "sNext": ">",
                    "sPrevious": "<"
                },
                "oAria": {
                    "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                    "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                }
            },
            /*
             "fnDrawCallback": function(oSettings) {
             // Need to redo the counters if filtered or sorted 
             if (oSettings.bSorted || oSettings.bFiltered) {
             for (var i = 0, iLen = oSettings.aiDisplay.length; i < iLen; i++) {
             var nTr = oSettings.aoData[ oSettings.aiDisplay[i] ].nTr;
             
             // Update the index column and do so without redrawing the table
             this.fnUpdate(i + 1, nTr, 0, false, false);
             }
             }
             },
             */
            "fnInitComplete": function() {
                this.fnAdjustColumnSizing();

            }
        });

        var datatableOSettings = {
            "bJQueryUI": true,
            "bAutoWidth": false,
            //"bScrollInfinite": false,
            //"sScrollY": "800px",
            //"sScrollX": "100%",
            //"sScrollXInner": "365%",
            "bScrollCollapse": true,
            "bPaginate": true,
            "sPaginationType": "full_numbers",
            "bProcessing": true,
            "bFilter": true,
            "bSort": true,
            "oLanguage": {
                "sProcessing": "Procesando...",
                "sLengthMenu": "Mostrar _MENU_ registros",
                "sZeroRecords": "No se encontraron resultados",
                "sEmptyTable": "Ningún dato disponible en esta tabla",
                "sInfo": "Del _START_ al _END_ de _TOTAL_ registros",
                "sInfoEmpty": "Registros del 0 al 0 de un total de 0 registros",
                "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sInfoPostFix": "",
                "sSearch": "Buscar:",
                "sUrl": "",
                "sInfoThousands": ",",
                "sLoadingRecords": "Cargando...",
                "oPaginate": {
                    "sFirst": "<<",
                    "sLast": ">>",
                    "sNext": ">",
                    "sPrevious": "<"
                },
                "oAria": {
                    "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                    "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                }
            },
            /*
             "fnDrawCallback": function(oSettings) {
             // Need to redo the counters if filtered or sorted 
             if (oSettings.bSorted || oSettings.bFiltered) {
             for (var i = 0, iLen = oSettings.aiDisplay.length; i < iLen; i++) {
             var nTr = oSettings.aoData[ oSettings.aiDisplay[i] ].nTr;
             
             // Update the index column and do so without redrawing the table
             this.fnUpdate(i + 1, nTr, 0, false, false);
             }
             }
             },
             */
            "fnInitComplete": function() {
                this.fnAdjustColumnSizing();
            }
        };
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var oTable;
        $('#form').submit(function() {
            var sData = oTable.$('input').serialize();
            alert("The following data would have been submitted to the server: \n\n" + sData);
            return false;
        });
        oTable = $('#example').dataTable();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
        /* Add a select menu for each TH element in the table footer */
        $("tfoot th").each(function(i) {
            this.innerHTML = fnCreateSelect(indiFiltering.fnGetColumnData(i));
            $('select', this).change(function() {
                indiFiltering.fnFilter($(this).val(), i);
            });
        });

        /////Agrear o quitar esta seccion para el uso del ajax

        /*
         oTable.$("td[id!='rOnly']").editable('<%out.print(PageParameters.getParameter("mainContext") + PageParameters.getParameter("ajaxFuntions"));%>/UpdateCatalogoOrganismos.jsp', {
         indicator: "Guardando...",
         submitdata: {_method: "post"},
         tooltip: "Click para editar...",
         type: "text",
         "callback": function(sValue, y) {
         var aPos = oTable.fnGetPosition(this);
         oTable.fnUpdate(sValue, aPos[0], aPos[1]);
         },
         "submitdata": function(value, settings) {
         return {
         "row_id": this.parentNode.getAttribute('id'),
         "column": oTable.fnGetPosition(this)[2]
         };
         },
         "height": "14px",
         "width": "100%"
         });
         */

        //$(window).bind('resize', function () {
        //    oTable.fnAdjustColumnSizing();
        //} );

        $('#target').click(function() {
            oTable.fnAdjustColumnSizing();
        });
    });
    (function($) {
        /*
         * Function: fnGetColumnData
         * Purpose:  Return an array of table values from a particular column.
         * Returns:  array string: 1d data array 
         * Inputs:   object:oSettings - dataTable settings object. This is always the last argument past to the function
         *           int:iColumn - the id of the column to extract the data from
         *           bool:bUnique - optional - if set to false duplicated values are not filtered out
         *           bool:bFiltered - optional - if set to false all the table data is used (not only the filtered)
         *           bool:bIgnoreEmpty - optional - if set to false empty values are not filtered from the result array
         * Author:   Benedikt Forchhammer <b.forchhammer /AT\ mind2.de>
         */
        $.fn.dataTableExt.oApi.fnGetColumnData = function(oSettings, iColumn, bUnique, bFiltered, bIgnoreEmpty) {
            // check that we have a column id
            if (typeof iColumn == "undefined")
                return new Array();
            // by default we only want unique data
            if (typeof bUnique == "undefined")
                bUnique = true;
            // by default we do want to only look at filtered data
            if (typeof bFiltered == "undefined")
                bFiltered = true;
            // by default we do not want to include empty values
            if (typeof bIgnoreEmpty == "undefined")
                bIgnoreEmpty = true;
            // list of rows which we're going to loop through
            var aiRows;
            // use only filtered rows
            if (bFiltered == true)
                aiRows = oSettings.aiDisplay;
            // use all rows
            else
                aiRows = oSettings.aiDisplayMaster; // all row numbers

            // set up data array	
            var asResultData = new Array();
            for (var i = 0, c = aiRows.length; i < c; i++) {
                iRow = aiRows[i];
                var aData = this.fnGetData(iRow);
                var sValue = aData[iColumn];
                // ignore empty values?
                if (bIgnoreEmpty == true && sValue.length == 0)
                    continue;
                // ignore unique values?
                else if (bUnique == true && jQuery.inArray(sValue, asResultData) > -1)
                    continue;
                // else push the value onto the result data array
                else
                    asResultData.push(sValue);
            }

            return asResultData;
        }
    }(jQuery));
    function fnCreateSelect(aData)
    {
        var r = '<select width="100" style="width: 100px"><option value=""></option>', i, iLen = aData.length;
        for (i = 0; i < iLen; i++)
        {
            r += '<option value="' + aData[i] + '">' + aData[i] + '</option>';
        }
        return r + '</select>';
    }

    function fnShowHide(iCol, button)
    {
        var contAux = 0;
        var newName;
        var buttonNameAux = document.getElementById(button.id).value;
        //alert(""+buttonNameAux);
        var buttonName = buttonNameAux.toString().split(" ");
        //alert(""+buttonName[0]);
        if (buttonName[0] == "Ver" || buttonName[0] == "ver") {
            contAux++;
            newName = "Ocultar";
            while (contAux < buttonName.length) {
                newName = newName + " " + buttonName[contAux];
                contAux++;
            }
            document.getElementById(button.id).value = newName;
        } else {
            contAux++;
            newName = "Ver";
            while (contAux < buttonName.length) {
                newName = newName + " " + buttonName[contAux];
                contAux++;
            }
            document.getElementById(button.id).value = newName;
        }

        /* Get the DataTables object again - this is not a recreation, just a get of the object */
        var hideShowCol = $('#example').dataTable();
        var bVis = hideShowCol.fnSettings().aoColumns[iCol].bVisible;
        hideShowCol.fnSetColumnVis(iCol, bVis ? false : true);
    }
</script>