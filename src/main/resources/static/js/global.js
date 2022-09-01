/** Regular Expression */
const koreanReg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
const koreanConsonantsAndVowels = /[ㄱ-ㅎ|ㅏ-ㅣ]/;
const specialCharReg = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/g; // Not Contain UnderBar('_')
const alphabetUpperReg = /[A-Z]/;

/** Chat */
let socket = null;
let stompClient = null;

const mainContainer = document.getElementById("mainContainer");
const navContainer = document.getElementById("navContainer");

if (document.querySelector("#chatModal")) {
    const chatModal = new bootstrap.Modal("#chatModal");
    const chatModalElement = document.getElementById("chatModal");
    const sendMessageBtn = document.getElementById("chat-send-message-btn");
}

/** Page */
const alertArea = document.querySelector("#alert-area");

/** Loading Spinner */
const loadingSpinnerToBtn = '<span class=\"spinner-border spinner-border-sm mr-3\" role=\"status\" aria-hidden=\"true\"></span>';

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


// $.ajaxSetup({
//     beforeSend: function (xhr, settings) {
//         if (settings.type === 'GET' || settings.type === 'POST' || settings.type === 'PUT'
//             || settings.type === 'DELETE') {
//             if (!(/^http:.*/.test(settings.url) || /^https:.*/
//                 .test(settings.url))) {
//                 xhr.setRequestHeader("X-XSRF-TOKEN", Cookies.get('XSRF-TOKEN'));
//             }
//         }
//     }
// });


