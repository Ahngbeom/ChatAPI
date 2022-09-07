/***** Variables and functions to declare before HTML content is loaded *****/

/** Regular Expression **/
const koreanReg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
const koreanConsonantsAndVowels = /[ㄱ-ㅎ|ㅏ-ㅣ]/;
const specialCharReg = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/g; // Not Contain UnderBar('_')
const alphabetUpperReg = /[A-Z]/;
const bootstrapBtnTypeReg = /btn-primary|btn-secondary|btn-success|btn-danger|btn-warning|btn-info|btn-light|btn-dark|btn-link/g;
const bootstrapOutlineBtnTypeReg = /btn-outline-primary|btn-outline-secondary|btn-outline-success|btn-outline-danger|btn-outline-warning|btn-outline-info|btn-outline-light|btn-outline-dark/g;

/** Chat **/
let socket = null;
let stompClient = null;

if (document.querySelector("#chatModal")) {
    const chatModal = new bootstrap.Modal("#chatModal");
    const chatModalElement = document.getElementById("chatModal");
    const sendMessageBtn = document.getElementById("chat-send-message-btn");
}

/** Page */
const alertArea = document.querySelector("#alert-area");

/** Loading Spinner */
const loadingSmallSpinnerSpan = '<span class=\"spinner-border spinner-border-sm mr-3\" role=\"status\" aria-hidden=\"true\"></span>';
const loadingSpinnerSpan = '<span class=\"spinner-border mr-3\" role=\"status\" aria-hidden=\"true\"></span>';

/** Popover */
const setPopoverToSignUpForm = function (triggerType, targetElement, message) {
    const popover = new bootstrap.Popover(targetElement, {
        container: 'body',
        placement: 'top',
        content: message,
        trigger: triggerType
    });
    targetElement.addEventListener('focusout', function () {
        popover.hide();
    });
    targetElement.addEventListener('input', function () {
        popover.hide();
    });
    // targetElement.popover = popover;
    return popover;
}




