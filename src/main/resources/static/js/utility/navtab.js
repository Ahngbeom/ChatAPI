import {removeAllRowsInTable} from "/js/utility/table.js";
import {ajaxGetMyMBTIList, mbtiInfoToTable} from "/js/mbti/table.js";
import {getUserList, createUsersTableElement} from "/js/user/user.js";

export const navTab = document.querySelector("#navBottomContainer .nav-tabs");
export const navTabItems = document.querySelectorAll("#navBottomContainer .nav-tabs .nav-link");
export const bannerContainer = document.querySelector("#bannerContainer");

const getUserListTab = navTab.querySelector("#NavTab-UserList");

// (function locationPathnameDetector() {
//     const activeNabTab = navTab.querySelector(".active");
//     activeNabTab != null ? activeNabTab.classList.remove("active") : false;
//     switch (location.pathname) {
//         case '/':
//             document.querySelector("a[href='/']").classList.add("active");
//             break;
//         case '/mbti/list':
//             document.querySelector("a[href='/mbti/list']").classList.add("active");
//             break;
//     }
// }());

const removeChildNode = function (target) {
    let firstElemChild = target.firstElementChild;
    while (firstElemChild) {
        firstElemChild.remove();
        firstElemChild = target.firstElementChild;
    }
}

navTabItems.forEach(tab => {
    if (tab.id !== "NavTab-Home") {
        tab.addEventListener('click', function () {
            removeChildNode(bannerContainer);
            bannerContainer.innerHTML += "<div class=\"d-flex d-block flex-column\">\n" +
            "        <h1 class=\"d-flex justify-content-center\">" + tab.innerHTML + "</h1>\n" +
            "        <h4 class=\"d-flex justify-content-center\">Myers-Briggs Type Indicator</h4>\n" +
            "    </div>";
            console.log("Click Nav Tab");
            navTab.querySelector(".active").classList.remove("active");
            tab.classList.add("active");
            removeChildNode(document.querySelector("#contentContainer"));
            sessionStorage.setItem("content-status", tab.id);
        });
    }
});

document.querySelector("#NavTab-MyMBTIList").addEventListener('click', function () {
    contentContainer.innerHTML = "<div id=\"mbtiListDiv\" class=\"flex-column\">\n" +
        "        <div class=\"d-flex justify-content-end\" id=\"btnAreaForMbtiTable\">\n" +
        "            <button class=\"btn btn-info me-3\" onclick=\"location.href='/mbti/register'\">Add</button>\n" +
        "        </div>\n" +
        "        <table class=\"table table-striped table-hover table-responsive\" id='mbtiTable'>\n" +
        "            <thead>\n" +
        "            <tr>\n" +
        "                <th class=\"col-1\" colspan=\"1\" scope=\"col\">#</th>\n" +
        "                <th class=\"col-6\" colspan=\"1\" scope=\"col\">MBTI</th>\n" +
        "                <th class=\"col-4\" colspan=\"1\" scope=\"col\">Personality</th>\n" +
        "                <th class=\"col-1\" colspan=\"1\" scope=\"col\"></th>\n" +
        "                <!--                <th class=\"col-1\" colspan=\"1\" scope=\"col\">Chatting</th>-->\n" +
        "            </tr>\n" +
        "            </thead>\n" +
        "            <tbody class=\"\">\n" +
        "            </tbody>\n" +
        "        </table>\n" +
        "    </div>";

    let myMBTIList = ajaxGetMyMBTIList();

    if (myMBTIList.length === 0) {
        console.log("MBTI 정보가 없습니다.");
        contentContainer.querySelector("table tbody").innerHTML = "<tr><td colspan='12'><div class='d-flex flex-column align-items-center'></div></td></tr>"
        contentContainer.querySelector("table tbody tr td div").innerHTML = "<h3>MBTI 정보가 없습니다.</h3>" + "<a href='/mbti/register'>MBTI 등록</a>";
    } else {
        mbtiInfoToTable(myMBTIList);
    }
});

getUserListTab != null ? getUserListTab.addEventListener('click', function () {
    contentContainer.insertAdjacentElement("beforeend", createUsersTableElement(getUserList()));
    // const userList = getUserList();
    // userList.forEach(user => {
    //    console.log(user);
    // });
}) : false;



