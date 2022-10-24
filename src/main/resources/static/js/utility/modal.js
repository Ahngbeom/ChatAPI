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

/**
 * @param data [target, title, body]
 *
 * - [target] is modal element to be renewal.
 * - [type] is set modal-header color.
 * - [title] is be inserted into .modal-title.
 * - [body] is be inserted into .modal-body.
 * @param btns [...args]
 *
 * - Be add button tag in modal-footer.
 * - But, the close button is fixed.
 */
const renewalModal = function (data, ...btns) {
    if (data.type === "danger") {
        data.target.querySelector(".modal-header").style.backgroundColor = "#ee7777";
    } else if (data.type === "warning") {
        data.target.querySelector(".modal-header").style.backgroundColor = "#d0ce63";
    } else if (data.type === "success") {
        data.target.querySelector(".modal-header").style.backgroundColor = "#88dc51";
    }
    data.target.querySelector(".modal-title").innerHTML = data.title;

    if (data.target.querySelector(".modal-body")) {
        if (data.body != null) {
            data.target.querySelector(".modal-body").classList.remove("visually-hidden");
            data.target.querySelector(".modal-body").insertHTML = data.body;
        } else
            data.target.querySelector(".modal-body").classList.add("visually-hidden");
    }

    data.target.querySelectorAll(".modal-footer button").forEach(button => {
        if (!button.hasAttribute("data-bs-dismiss"))
            button.remove();
    });
    if (btns.length > 0) {
        data.target.querySelector(".modal-footer").insertAdjacentHTML("beforeend", btns);
    }

    // if (data.interactionBtnText !== undefined && data.interactionBtnType !== undefined) {
    //     data.target.querySelector("#modalInteractionBtn").classList.forEach(value => {
    //         if (value.match(bootstrapBtnTypeReg) || value.match(bootstrapOutlineBtnTypeReg))
    //             data.target.querySelector("#modalInteractionBtn").classList.replace(value, "btn-" + data.interactionBtnType);
    //         else
    //             data.target.querySelector("#modalInteractionBtn").classList.add("btn-" + data.interactionBtnType);
    //     });
    //     data.target.querySelector("#modalInteractionBtn").innerHTML = data.interactionBtnText;
    // }

}