function createRequestObject(){
    var req;
    if(window.XMLHttpRequest){

        req = new XMLHttpRequest();
    }
    else if(window.ActiveXObject){
        //For IE 5+
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else{
    }

    return req;
}

//Make the XMLHttpRequest Object
var http = createRequestObject();

function idSendRequest(id, method, url){
    if(method == 'POST' || method == 'GET'){
        //http.open(method,url+'?id='+id);
        //http.onreadystatechange = handleResponse;
        http.open(method,url);
        http.onreadystatechange = function() {
            handleResponse(id);
        };
        http.send(null);
    }
}

function handleResponse2(){
    if(http.readyState == 4 && http.status == 200){
        var response = http.responseText;
        if(response){
            if(response.indexOf('|') != -1) {
                update = response.split('|');
                //document.getElementById(''+update[0].trim()).innerHTML = update[1];
                document.getElementById(update[0].trim()).innerHTML = update[1];
            }
        }
    }
}
    
function handleResponse(id){
    if(http.readyState == 4 && http.status == 200){
        var response = http.responseText;
        if(response){
            document.getElementById(id).innerHTML = response;
        }
    }
}