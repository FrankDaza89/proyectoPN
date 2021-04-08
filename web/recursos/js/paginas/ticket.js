/* global bootbox */

$(document).ready(function () {
     $validatorTicket = $('#ticket-form').validate({
        errorPlacement: function (error, element) {
            $(element)
                    .closest('form')
                    .find('label[for="' + element.attr('id') + '"]')
                    .append(error);
        },
        errorElement: 'span',
        rules: {        
            'contacto-ticket': {
                required: true
            },
            'medio-comuni-ticket': {
                required: true,
                valueNotEquals: '0'
            },
            'incidencia-ticket': {
                required: true,
                valueNotEquals: '0'
            },
            'observ-ticket': {
                required: true
            }
        },
        messages: {           
            'contacto-ticket': {
                required: ' (Este campo es obligatorio)'
            },
            'medio-comuni-ticket': {
                required: ' (Este campo es obligatorio)',
                valueNotEquals: ' (Seleccione una opción)'
            },
            'incidencia-ticket': {
                required: ' (Este campo es obligatorio)',
                valueNotEquals: ' (Seleccione una opción)'
            },
            'observ-ticket': {
                required: ' (Este campo es obligatorio)'
            }
        }
    });
});

$(document).on('click', '#btn-buscar', function () {
    var busqueda = {
        'identificacionCliente': $('#ident-cliente-bus').val(),
        'nombreCliente': $('#nombre-cliente-bus').val(),
        'numeroTicket': $('#numero-ticket-bus').val(),
        'estado': $('#estado-ticket').val(),
        'prioridad': $('#prioridad-ticket').val()
        
    };
    document.getElementById('busqueda').value = JSON.stringify(busqueda);
    buscar();
    
    document.getElementById('form').reset();
});

function mostrarTabla() {
    $('#tabla').attr('class', 'table table-bordered table-hover');
    $('#tabla').DataTable({
        lengthMenu: [[5, 10, 20, -1], [5, 10, 20, 'Todos']],
        //sort: true,
        searching: false,
        language: {
            //search: 'Buscar:',
            zeroRecords: 'No se encontraron resultados.',
            lengthMenu: 'Mostrar _MENU_ registros',
            info: 'Mostrando del _START_ al _END_ de _TOTAL_',
            infoFiltered: '(filtrado de _MAX_ registros)',
            'paginate': {
                'previous': 'Anterior',
                'next': 'Siguiente'
            }
        },
        columnDefs: [{
                "targets": 7,
                "data": null,
                "defaultContent": '<a id="edit" class="btn btn-primary" ><i class="fa fa-pencil"></i></a> <a id="estate" class="btn btn-primary" ><i class="fa fa-cog"></i></a> <a id="remove" class="btn btn-primary" ><i class="fa fa-trash"></i></a>',
                "orderable": false
            },
            {"width": "15%", "targets": 7}
        ]
    });

    $('#tabla tbody').on('click', 'a', function () {
        var data = $(this).parents('tr');
        var idFila = parseInt(data[0].id);
        document.getElementById('idTicketSeleccionado').value = parseInt(data[0].id);
        console.log(idFila);
        if (this.id === 'edit') {
            obtenerTicket();
            $('#registro').modal('show');
        }
        if (this.id === 'remove') {
            var nombre = data.find('td:eq(1)').text();
            mostrarEliminar(nombre);
        }if (this.id === 'estate') {
            obtenerEstado();
            $('#cambioestado').modal('show');
        }
    });
};

function mostrarEliminar(nombre) {
    bootbox.dialog({
        message: "<p class='text-justify'>¿Está seguro de que desea eliminar el registro: " + nombre + "?</p>",
        title: "Eliminar Ticket",
        buttons: {
            danger: {
                label: "Si",
                className: "btn btn-primary",
                callback: function () {
                    removerAsignacion();
                    document.location.reload();
                }
            },
            confirm: {
                label: "No",
                className: "btn btn-primary",
                callback: function () {
                    this.hide();
                }
            }
        }
    });
}

function validarTicket() {
    $validatorTicket = $('#ticket-form').validate({
        errorPlacement: function (error, element) {
            $(element)
                    .closest('form')
                    .find('label[for="' + element.attr('id') + '"]')
                    .append(error);
        },
        errorElement: 'span',
        rules: {        
            'contacto-ticket': {
                required: true
            },
            'medio-comuni-ticket': {
                required: true,
                valueNotEquals: '0'
            },
            'incidencia-ticket': {
                required: true,
                valueNotEquals: '0'
            },
            'observ-ticket': {
                required: true
            }
        },
        messages: {           
            'contacto-ticket': {
                required: ' (Este campo es obligatorio)'
            },
            'medio-comuni-ticket': {
                required: ' (Este campo es obligatorio)',
                valueNotEquals: ' (Seleccione una opción)'
            },
            'incidencia-ticket': {
                required: ' (Este campo es obligatorio)',
                valueNotEquals: ' (Seleccione una opción)'
            },
            'observ-ticket': {
                required: ' (Este campo es obligatorio)'
            }
        }
    });
}
;

$(document).on('click', '#btn-ticket', function (e) {
    document.getElementById('medios-select').value = $('#medio-comuni-ticket').val();
    document.getElementById('incidencias-select').value = $('#incidencia-ticket').val();
     document.getElementById('cliente-select').value = $('#clienteS-ticket').val();
    
    var $valid = $('#ticket-form').valid();
    if (!$valid) {
        $validatorTicket.focusInvalid();
        return false;
    } else {
        var bandera = document.getElementById('banderaModo').value;
        if (bandera === "1") {
            actualizarTicket();
            limpiarVariables();
        } else {
            registrarTicket();
        }
        $('#registro').modal('hide');
    }
});

$(document).on('click', '#btn-ticket-estado', function (e) {
    document.getElementById('cambio-estado-select').value = $('#cambio-es-ticket').val();    
    var $valid = $('#ticket-estado-form').valid();
    if (!$valid) {
        $validatorTicket.focusInvalid();
        return false;
    } else {     
            cambiarEstado();
            limpiarVariables();        
        $('#cambioestado').modal('hide');
    }
});




$(document).on('click', '#btn-cerrar', function (e) {
    limpiarVariables();
    document.getElementById("ticket-form").reset();
});








