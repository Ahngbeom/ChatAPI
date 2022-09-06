import {insertMBTIDataToModal} from "./util.js";

const mbtiRegisterModalElem = document.querySelector("#mbtiRegisterModal");
const checkMBTIResultBtn = document.querySelector("#checkMBTIResultBtn");

function mbtiResultConverter(jsonData) {
    let result = "";
    $.each(jsonData, function (key, value) {
        switch (key) {
            case "focused":
                if (value === "Extraversion")
                    result += "E";
                else
                    result += "I";
                break;
            case "recognition":
                if (value === "Sensing")
                    result += "S";
                else
                    result += "N";
                break;
            case "decision":
                if (value === "Thinking")
                    result += "T";
                else
                    result += "F";
                break;
            case "coping":
                if (value === "Judging")
                    result += "J";
                else
                    result += "P";
                break;
            default:
                alert("mbtiResultConverter Error");
                break;
        }
    });
    return result;
}

const getRadioValueForMBTI = function () {
    let mbtiRadioJson = {
        focused: null,
        recognition: null,
        decision: null,
        coping: null
    }
    for (const [key, value] of Object.entries(mbtiRadioJson)) {
        $(".form-check-input[name='" + key + "']").each((index, item) => {
            if (item.checked)
                mbtiRadioJson[key] = item.value;
        });
        if (mbtiRadioJson[key] === null) {
            alert("Please select all items.");
            return null;
        }
    }
    return mbtiRadioJson;
}

const getMBTIInformation = function (mbtiRadioJson) {
    console.log(mbtiRadioJson);
    if (!mbtiRadioJson)
        return null;

    const mbtiResult = mbtiResultConverter(mbtiRadioJson);
    let mbtiJson;
    $.ajax({
        type: 'GET',
        url: "https://www.16personalities.com/" + mbtiResult + "-personality",
        cache: false,
        async: false,
        success: function (data) {
            let mbtiHeaderExtractByOfficialSite = $($.parseHTML(data)).find("header")[0];
            let mbtiArticleExtractByOfficialSite = $($.parseHTML(data)).find("article")[0];

            mbtiJson = {
                shortMbti: mbtiResult,
                code: mbtiHeaderExtractByOfficialSite.querySelector("div[class='code']").innerHTML,
                personality: mbtiHeaderExtractByOfficialSite.querySelector("span").innerHTML,
                introduction: mbtiArticleExtractByOfficialSite.querySelectorAll("div[class='definition'] p").item(1).innerText,
                imgSrc: mbtiHeaderExtractByOfficialSite.querySelector("img").src
            };
            renewalModal({
                target: mbtiRegisterModalElem,
                title: "MBTI Result",
                body: insertMBTIDataToModal(mbtiRegisterModalElem, mbtiJson),
                interactionBtnType: "primary",
                interactionBtnText: "Submit"
            });
            showModalTarget(mbtiRegisterModalElem);
        },
        error: function (data) {
            console.error(data);
        }
    });
    console.log(mbtiJson);
    return mbtiJson;
}

checkMBTIResultBtn.addEventListener('click', function () {
    const mbtiSubmitBtn = document.querySelector("#mbtiRegisterModal #modalInteractionBtn");
    const mbtiRadioJson = getRadioValueForMBTI();
    const mbtiJson = getMBTIInformation(mbtiRadioJson);

    mbtiSubmitBtn.addEventListener('click', function () {
        console.log(JSON.stringify(mbtiJson));
        $.ajax({
            async: false,
            type: 'POST',
            url: '/mbti/registration',
            data: JSON.stringify(mbtiJson),
            dataType: 'JSON',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                console.log(data);
                mbtiRegisterModalElem.querySelector(".modal-header").innerHTML = "Alert";
                mbtiRegisterModalElem.querySelector(".modal-body").innerHTML = "Registration was successful.";
                mbtiSubmitBtn.innerHTML = "Return to MBTI List";
                mbtiSubmitBtn.addEventListener('click', function () {
                    location.href = "/mbti";
                }, {once: true});
            },
            error: function (data) {
                console.log(data);
            }
        });
    }, {once: true});
});

