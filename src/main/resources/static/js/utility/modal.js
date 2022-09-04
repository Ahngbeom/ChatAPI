const showModalTarget = function (target) {
    const modal = new bootstrap.Modal(target);
    modal.show();
}

const hideModalTarget = function (target) {
    const modal = new bootstrap.Modal(target);
    modal.hide();
}

const disposeModalTarget = function (target) {
    const modal = new bootstrap.Modal(target);
    modal.dispose();
}

const renewalModal = function (data) {
    data.target.querySelector(".modal-title").innerHTML = data.title;
    data.target.querySelector(".modal-body").innerHTML = data.body;
}