// JavaScript Document

meses = new Array ("enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre");
             dia = new Array("Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado");
             data = new Date();
             index = data.getMonth();
             var an=data.getYear();
             if (an<1000){
                an = 1900+an;
             }
             indexdia=data.getDay();
             document.write(dia[indexdia]+"&nbsp;"+ data.getDate()+ "&nbsp;de&nbsp;" + meses[index] +"&nbsp;de&nbsp;" + an +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");