const staticBackdropModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), {});
const staticBackdropModalElem = document.querySelector("#staticBackdrop");

let mbtiRadioJson = {
    'focused': null,
    'recognition': null,
    'decision': null,
    'coping': null
}

function mbtiResultConverter(jsonData) {
    let result = "";
    $.each(jsonData, function (key, value) {
        switch (key) {
            case "focused":
                console.log(value);
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

const getRadioValue = function (topic) {
    document.querySelectorAll(".form-check-input[name='" + topic + "']").forEach(input => {
        if (input.checked)
            mbtiRadioJson[topic] = input.getAttribute('id').substring(input.getAttribute('name').length);
        else
            return false;

        removeEventListener('change', radioChangeEventCallback);
        input.addEventListener('change', radioChangeEventCallback);
    });
}

function radioChangeEventCallback() {
    getMBTIInformation();
}

const getMBTIInformation = function () {
    getRadioValue("focused");
    getRadioValue("recognition");
    getRadioValue("decision");
    getRadioValue("coping");

    let isComplete = true;
    $.each(mbtiRadioJson, function (key, value) {
        if (value === null) {
            alert("Did not selected something");
            return (isComplete = false);
        }
    });

    console.log(mbtiRadioJson, isComplete);

    let mbtiJson = null;

    if (isComplete) {
        const mbtiResult = mbtiResultConverter(mbtiRadioJson);
        console.log(mbtiResult);
        $.ajax({
            type: 'GET',
            url: "https://www.16personalities.com/" + mbtiResult + "-personality",
            async: false,
            success: function (data) {
                let mbtiHeaderExtractByOfficialSite = $($.parseHTML(data)).find("header")[0];
                let mbtiArticleExtractByOfficialSite = $($.parseHTML(data)).find("article")[0];

                mbtiJson = {
                    "mbti": mbtiHeaderExtractByOfficialSite.querySelector("div[class='code']").innerHTML,
                    "personality": mbtiHeaderExtractByOfficialSite.querySelector("span").innerHTML,
                    "introduction": mbtiArticleExtractByOfficialSite.querySelectorAll("div[class='definition'] p").item(1).innerText
                };

                staticBackdropModalElem.querySelector(".modal-header #staticBackdropLabel").innerHTML = mbtiJson.mbti;
                staticBackdropModalElem.querySelector(".modal-header #staticBackdropLabel").innerHTML += "<span class='d-block'>" + mbtiJson.personality + "</span>";
                staticBackdropModalElem.querySelector(".modal-body").innerHTML = "";
                staticBackdropModalElem.querySelector(".modal-body").append(mbtiHeaderExtractByOfficialSite.querySelector("img"));
                staticBackdropModalElem.querySelector(".modal-body").append(document.createElement("div"));
                staticBackdropModalElem.querySelector(".modal-body div").innerHTML = mbtiJson.introduction;
                staticBackdropModalElem.querySelector(".modal-body").append(document.createElement("a"));
                staticBackdropModalElem.querySelector(".modal-body a").setAttribute("href", "https://www.16personalities.com/" + mbtiResult + "-personality");
                staticBackdropModalElem.querySelector(".modal-body a").setAttribute("target", "_blank");
                staticBackdropModalElem.querySelector(".modal-body a").innerHTML = "View details on the official site";

                mbtiJson = {
                    "mbti": mbtiHeaderExtractByOfficialSite.querySelector("div[class='code']").innerHTML,
                    "personality": mbtiHeaderExtractByOfficialSite.querySelector("span").innerHTML,
                    "introduction": mbtiArticleExtractByOfficialSite.querySelectorAll("div[class='definition'] p").item(1).innerText
                };
            },
            error: function (data) {
                console.log(data);
            }
        });
    }
    return mbtiJson;
}

const MBTISubmit = function () {

    const mbtiJson = getMBTIInformation();

    const mbtiSubmitBtn = document.querySelector("#staticBackdropSubmit");

    if (mbtiJson !== null) {
        console.log(mbtiJson);

        staticBackdropModal.show();

        // $.each($($.parseHTML(data)).filter(".type-info"), function (i, el) {
        //     console.log(el);
        //     // if (el.nodeName === "TITLE") {
        //     //     console.log(el.text.replace("| 16Personalities", "").trim());
        //     //     console.log(el.text);
        //     // }
        // });
    }

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
                staticBackdropModalElem.querySelector(".modal-header").innerHTML = "Alert";
                staticBackdropModalElem.querySelector(".modal-body").innerHTML = "Registration was successful.";
                mbtiSubmitBtn.addEventListener('click', function () {
                    location.href = "/mbti";
                }, {once: true});
            },
            error: function (data) {
                console.log(data);
            }
        });
    });


}

