/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function calendarField(id){
    new JsDatePick({
        useMode:2,
        target:id,
        dateFormat:"%Y-%M-%d"
    /*selectedDate:{				This is an example of what the full configuration offers.
                                        day:5,						For full documentation about these settings please see the full version of the code.
                                        month:9,
                                        year:2006
                                },
                                yearsRange:[1978,2020],
                                limitToToday:false,
                                cellColorScheme:"beige",
                                dateFormat:"%m-%d-%Y",
                                imgPath:"img/",
                                weekStartDay:1*/
    });    
}