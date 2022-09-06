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
    data.target.querySelector("#modalInteractionBtn").classList.forEach(value => {
        if (value.match(bootstrapBtnTypeReg) || value.match(bootstrapOutlineBtnTypeReg))
            data.target.querySelector("#modalInteractionBtn").classList.replace(value, "btn-" + data.interactionBtnType);
        else
            data.target.querySelector("#modalInteractionBtn").classList.add("btn-" + data.interactionBtnType);
    });
    data.target.querySelector("#modalInteractionBtn").innerHTML = data.interactionBtnText;
}