$('#form').submit(function (ev) {
    ev.preventDefault();

    var res = $.ajax('ip', {
        data: {
            'ip': $('#ip')[0].value
        },
        method: 'POST'
    }).done(function (res) {
        $('#result').html(res);
    }).fail(function (res) {
        $('#result').html('Something went wrong');
    });
});