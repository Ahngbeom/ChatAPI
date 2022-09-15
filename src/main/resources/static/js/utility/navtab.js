import {removeAllRowsInTable} from "/js/utility/table.js";

import {ajaxGetMyMBTIList, allMBTIListContentLoad, mbtiInfoToTable} from "/js/mbti/table.js";
import {
    getUserList,
    createUsersTableElement,
    enableUserInfoInteractionBtn,
    userListContentLoad
} from "/js/user/user.js";
import {createRowsForChatRoomList, getChatRoomList} from "/js/chat/getList.js";
import {removeChildNode, replaceChildNode} from "/js/utility/changeElement.js";

export const navTab = document.querySelector("#navBottomContainer .nav-tabs");
export const navTabItems = document.querySelectorAll("#navBottomContainer .nav-tabs .nav-link");

const homeTab = document.querySelector("#NavTab-Home");
const getMyMBTIListTab = document.querySelector("#NavTab-MyMBTIList");
const getUserListTab = navTab.querySelector("#NavTab-UserList");
const getChatRoomListTab = navTab.querySelector("#NavTab-ChatRoomList");

let currentActiveTab = navTab.querySelector(".nav-item .active");

document.addEventListener("DOMContentLoaded", function () {
    // console.log(location.hash);
    // console.log(history);
    currentActiveTab = navTab.querySelector(".nav-item .active");
    sessionStoredContentStatDetector();
});

// document.addEventListener("click", function () {
//     // console.log(location.hash);
//     // console.log(history);
//     sessionStoredContentStatDetector();
// });

const sessionStoredContentStatDetector = function () {
    const sessionStoredContentStat = navTab.querySelector("#" + sessionStorage.getItem("content-status"));
    sessionStoredContentStat != null ? sessionStoredContentStat.classList.add("active") : homeTab.classList.add("active");
    switch (navTab.querySelector(".nav-item .active").id) {
        case "NavTab-MyMBTIList":
            allMBTIListContentLoad();
            break;
        case "NavTab-UserList":
            userListContentLoad();
            break;
        case "NavTab-ChatRoomList":
            userListContentLoad();
            break;
        default:
            break;
    }
}

navTabItems.forEach(tab => {
    tab.addEventListener('click', function () {
        if (tab.id === "NavTab-Home") {
            sessionStorage.removeItem("content-status");
            tab.classList.add("active");
        } else {
            // location.href = location.href.substring(0, location.href.indexOf("#"));
            (currentActiveTab = navTab.querySelector(".nav-item .active")) != null ? currentActiveTab.classList.remove("active") : false;
            tab.classList.add("active");
            removeChildNode(document.querySelector("#contentContainer"));
            sessionStorage.setItem("content-status", tab.id);
        }
    });

});

getMyMBTIListTab != null ? getMyMBTIListTab.addEventListener('click', function () {
    allMBTIListContentLoad();
}) : false;

getUserListTab != null ? getUserListTab.addEventListener('click', function () {
    userListContentLoad();
}) : false;

getChatRoomListTab != null ? getChatRoomListTab.addEventListener('click', function () {
    const chatRoomList = getChatRoomList();

    contentContainer.innerHTML = "<div id=\"chatRoomListDiv\" class=\"flex-column\">\n" +
        "        <div class=\"d-flex justify-content-end\" id=\"btnAreaForchatRoomList\">\n" +
        "            <button class=\"btn btn-info me-3\" onclick=\"location.href='/chat/create'\">Create</button>\n" +
        "        </div>\n" +
        "        <table class=\"table table-striped table-hover table-responsive\" id='chatRoomTable'>\n" +
        "            <thead>\n" +
        "            <tr>\n" +
        "                <th class=\"col-7\" colspan=\"1\" scope=\"col\">Room Name</th>\n" +
        "                <th class=\"col-4\" colspan=\"1\" scope=\"col\">Concurrent connected User</th>\n" +
        "                <th class=\"col-1\" colspan=\"1\" scope=\"col\"></th>\n" +
        "            </tr>\n" +
        "            </thead>\n" +
        "            <tbody class=\"\">\n" +
        "            </tbody>\n" +
        "        </table>\n" +
        "    </div>";
    createRowsForChatRoomList(document.querySelector("#chatRoomTable"), chatRoomList);
}) : false;





