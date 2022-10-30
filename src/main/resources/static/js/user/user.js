import {ajaxGetUserAuthorities} from "./authority.js";
import {ajaxAdminGetUserMbtiList} from "/js/mbti/getInfo.js";
import {replaceChildNode} from "/js/utility/changeElement.js";

const removeUserConfirmBtn = document.querySelector(".removeUserConfirmBtn");
const simpleInquiryModal = document.querySelector("#simpleInquiryModal");

export const userListContentLoad = function () {
    replaceChildNode(bannerContainer, "<div class=\"d-flex d-block flex-column\">\n" +
        "        <h1 class=\"d-flex justify-content-center\">User List</h1>\n" +
        "    </div>");
    replaceChildNode(contentContainer, createUsersTableElement(getUserList()));
    // contentContainer.insertAdjacentElement("beforeend", createUsersTableElement(getUserList()));
    enableUserInfoInteractionBtn();
};

export const getUserList = function () {
    let result = null;
    $.ajax({
        type: 'GET',
        url: '/user-list',
        async: false,
        contentType: 'application/json; charset=utf-8',
        dataType: 'JSON',
        success: function (data) {
            result = data;
        },
        error: function (data) {
            console.log(data);
        }
    });
    return result;
};

export const enableUserInfoInteractionBtn = function () {
    const getUserAuthoritiesBtnList = document.querySelectorAll(".getUserAuthoritiesBtn");
    const getUserMBTIListBtnList = document.querySelectorAll(".getUserMBTIListBtn");
    getUserAuthoritiesBtnList.forEach(btn => {
        btn.addEventListener('click', function (e) {
            ajaxGetUserAuthorities(e.currentTarget.dataset['username']);
        });
    });
    getUserMBTIListBtnList.forEach(btn => {
        btn.addEventListener('click', function (e) {
            ajaxAdminGetUserMbtiList(e.currentTarget.dataset['username']);
        });
    });
}
enableUserInfoInteractionBtn();

export const createUsersTableElement = function (data) {
    const table = document.createElement('table');
    table.setAttribute('class', 'table table-success table-striped text-center table-responsive');
    const thead = table.createTHead();
    const thead_row = thead.insertRow(0);
    thead_row.insertCell(0).innerHTML = "#";
    thead_row.insertCell(1).innerHTML = "Username";
    thead_row.insertCell(2).innerHTML = "Nickname";
    thead_row.insertCell(3).innerHTML = "Authority";
    thead_row.insertCell(4).innerHTML = "MBTI-List";

    const tbody = table.createTBody();
    data.forEach((user, index) => {
        const tbody_row = tbody.insertRow(index);
        tbody_row.insertCell(0).innerHTML = user.id;
        tbody_row.insertCell(1).innerHTML = user.username;
        tbody_row.insertCell(2).innerHTML = user.nickname;
        tbody_row.insertCell(3).innerHTML = "<button class='btn btn-light getUserAuthoritiesBtn' data-userno='" + user.id + "'>조회</button>";
        tbody_row.insertCell(4).innerHTML = "<button class='btn btn-light getUserMBTIListBtn' data-userno='" + user.id + "'>조회</button>";
        // console.log(user, index);
    });

    return table;
}

$(".removeUserConfirmBtn").on('click', function (e) {
    // renewalModal({
    //     target: simpleInquiryModal,
    //     type: "danger",
    //     title: "정말 삭제하시겠습니까?",
    //     body: "<span class='fw-bold fst-italic'>'삭제'</span> 를 입력해주세요." +
    //         "<input type='text' class='form-control'/>"
    // }, "<button class='btn btn-danger' disabled>삭제</button>")

    console.log(e.target.dataset['username']);

    renewalModal({
        target: simpleInquiryModal,
        type: "danger",
        title: "' <span class='fw-bold fst-italic'>" + e.target.dataset['username'] + "</span> ' 계정을 삭제하시겠습니까?",
    }, "<button type='button' class='btn btn-danger' id='removeUserBtn'>삭제</button>")

    simpleInquiryModal.querySelector("#removeUserBtn").addEventListener('click', function () {
        $.ajax({
            type: 'GET',
            url: "/api/user/remove/" + e.target.dataset['username'],
            success: function () {
                renewalModal({
                    target: simpleInquiryModal,
                    type: "success",
                    title: "삭제되었습니다."
                });
                showModalTarget(simpleInquiryModal);
            },
            error: function (xhr) {
                console.error(xhr.responseText);
            }
        });
    });

    showModalTarget(simpleInquiryModal);

});




