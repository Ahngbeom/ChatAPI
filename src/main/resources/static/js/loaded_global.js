/***** Variables and functions to be used after the HTML content has been loaded *****/

// const navTop = document.querySelector("#navTopContainer");
// const navBottom = document.querySelector("#navBottomContainer .nav-tabs");
const mainContainer = document.getElementById("mainContainer");
const contentContainer = document.getElementById("contentContainer");

const bannerContainer = document.querySelector("#bannerContainer");

const confirmModalElem = document.querySelector("#confirmModal");
const confirmModalCancelBtn = confirmModalElem.querySelector("#confirmModalCancelBtn");
const confirmModalAcceptBtn = confirmModalElem.querySelector("#confirmModalAcceptBtn");

toastDetector();
