/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$('#b_profilepic, #b_addicon').click(function () {
    $('#b_uploadprofilepic').click();
});

$("#b_uploadprofilepic").change(function(){
   $("#b_uploadprofile").submit(); 
});

//auto dismiss of alert in 7 second
$(document).ready(function(){
    setTimeout(function(){
        $(".auto-dismis").alert("close");
    }, 7000); 
});