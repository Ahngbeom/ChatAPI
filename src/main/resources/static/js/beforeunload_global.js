/***** Variables and functions to declare before HTML content is loaded *****/

const USER_INFO = (function () {
    let userInfo;
    $.ajax({
        type: "GET",
        url: "/api/user/info",
        async: false,
        contentType: 'application/json; charset=utf-8',
        dataType: 'JSON',
        success: function (data) {
            console.log(data);
            userInfo = data;
        },
    });
    return userInfo;
})();

/** Regular Expression **/
const koreanReg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
const koreanConsonantsAndVowels = /[ㄱ-ㅎ|ㅏ-ㅣ]/;
const specialCharReg = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/g; // Not Contain UnderBar('_')
const alphabetUpperReg = /[A-Z]/;
const bootstrapBtnTypeReg = /btn-primary|btn-secondary|btn-success|btn-danger|btn-warning|btn-info|btn-light|btn-dark|btn-link/g;
const bootstrapOutlineBtnTypeReg = /btn-outline-primary|btn-outline-secondary|btn-outline-success|btn-outline-danger|btn-outline-warning|btn-outline-info|btn-outline-light|btn-outline-dark/g;

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




