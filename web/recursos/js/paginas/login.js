$(document).ready(function () {   
    $validatorLogin = $('#login-form').validate({
        errorPlacement: function (error, element) {
            error.insertBefore(element);
        },
        errorElement: 'span',
        rules: {
            'usuario-login': {
                required: true
            },
            'pass-login': {
                required: true
            }
        },
        messages: {
            'usuario-login': {
                required: ' campo obligatorio '
            },
            'pass-login': {
                required: ' campo obligatorio '
            }
        }
    });   
});

$(document).on('click', '#btn-login', function (e) {
     var $valid1 = $('#login-form').valid();
     if($valid1){
        login();
        document.getElementById("login-form").reset();      
     }
       
});