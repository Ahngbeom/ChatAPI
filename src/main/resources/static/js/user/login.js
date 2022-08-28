let formData = new FormData($("#loginForm")[0]);
const signIn = function () {
    $.ajax({
        type: 'POST',
        url: '/login',
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
        success: function (data) {
            console.log(data);
            location.href = "/";
        },
        error: function (data) {
            console.error(data);
        }
    });
}