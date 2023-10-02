/*!
* Start Bootstrap - Shop Homepage v5.0.6 (https://startbootstrap.com/template/shop-homepage)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

function ajaxTest(id, password) {

    var json = {"id" : id, "pw" : password};

    $.ajax({
        url:'ajaxTest.do',
        dataType:'json',
        contentType:'application/json',
        data:JSON.stringify(json),
        method:'POST',
        success:function(t){
            var id = t.id;
            alert(id);
        },
        error:function(t){
            console.error("Error! load fail.");
        }
    });
}

