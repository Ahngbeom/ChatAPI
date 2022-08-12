
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


const myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), {});

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
            success: function (data, status, xhr) {
                let mbtiHeaderExtractByOfficialSite = $($.parseHTML(data)).find("header")[0];
                let mbtiArticleExtractByOfficialSite = $($.parseHTML(data)).find("article")[0];

                document.querySelector("#staticBackdrop .modal-body").innerHTML = "<a href='https://www.16personalities.com/" + mbtiResult + "-personality'>Source</a>";
                document.querySelector("#staticBackdrop .modal-body").append(mbtiHeaderExtractByOfficialSite.querySelector("img"));

                mbtiJson = {
                    "mbti": mbtiHeaderExtractByOfficialSite.querySelector("div[class='code']").innerHTML,
                    "personality": mbtiHeaderExtractByOfficialSite.querySelector("span").innerHTML,
                    "introduction": mbtiArticleExtractByOfficialSite.querySelectorAll("div[class='definition'] p").item(1).innerText
                    // "introduction": ""
                };
            },
            error: function (data, status, xhr) {
                console.log(data);
            }
        });
    }
    return mbtiJson;
}

const MBTISubmit = function () {

    const mbtiJson = getMBTIInformation();

    const mbtiSubmitBtn = document.querySelector("#staticBackdropSubmit");

    mbtiSubmitBtn.addEventListener('click', function (e) {
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
            },
            error: function (data) {
                console.log(data);
            }
        });
    });


    if (mbtiJson !== null) {
        console.log(mbtiJson);

        document.querySelector("#staticBackdrop .modal-header #staticBackdropLabel").innerHTML = mbtiJson.mbti;
        document.querySelector("#staticBackdrop .modal-header #staticBackdropLabel").innerHTML += "<span class='d-block'>" + mbtiJson.personality + "</span>";

        document.querySelector("#staticBackdrop .modal-body").append(mbtiJson.introduction);

        myModal.show();

        // $.each($($.parseHTML(data)).filter(".type-info"), function (i, el) {
        //     console.log(el);
        //     // if (el.nodeName === "TITLE") {
        //     //     console.log(el.text.replace("| 16Personalities", "").trim());
        //     //     console.log(el.text);
        //     // }
        // });
    }
}

