const createAlert = function (type, message) {
    let divForAlert = "" +
        "<div class=\"d-flex justify-content-center align-items-center alert alert-" + type + " alert-dismissible fade show\">\n" +
            message +
        "    <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n" +
        "</div>";
    return divForAlert;
}