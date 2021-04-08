/* global bootbox */

$(document).ready(function () {
    var sesion = document.getElementById('tiempo-sesion').value;
    $(document).idleTimer(sesion);
});

$(document).on('idle.idleTimer', function (event, elem, obj) {
    console.log('Sesión expirada');
    mostrarSesionExpirada();
});

function mostrarSesionExpirada() {
    bootbox.dialog({
        message: "<p class='text-justify'>Su sesión ha expirado. Por favor vuelva a iniciar sesión para poder continuar.</p>",
        title: "Sesión Expirada",
        buttons: {
            danger: {
                label: "Aceptar",
                className: "btn btn-primary",
                callback: function () {
                    cerrarSesionDialogo();
                }
            }
        },
        closeButton: false
    });
}