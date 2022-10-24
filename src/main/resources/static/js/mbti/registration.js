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
            case "fulfillment":
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
        fulfillment: null
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
    console.log(mbtiResult);
    let mbtiJson;
    $.ajax({
        type: 'GET',
        // url: "https://www.16personalities.com/" + mbtiResult + "-personality",
        url: "/api/mbti/info/" + mbtiResult,
        contentType: "application/json; charset=utf-8",
        dataType: "JSON",
        // cache: false,
        async: false,
        success: function (data) {
            mbtiJson = {
                // shortMbti: data.code,
                code: data.code,
                personality: data.personality,
                introduction: data.introduction,
                imgSrc: data.imgSrc
            };
            renewalModal({
                    target: mbtiRegisterModalElem,
                    title: "MBTI Result",
                    body: insertMBTIDataToModal(mbtiRegisterModalElem, mbtiJson)
                }, "<button type=\"button\" class=\"btn btn-primary\" id=\"modalInteractionBtn\">Submit</button>"
            );
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
    const mbtiSubmitBtn = mbtiRegisterModalElem.querySelector("#modalInteractionBtn");
    const mbtiRadioJson = getRadioValueForMBTI();
    const mbtiJson = getMBTIInformation(mbtiRadioJson);

    $("#mbtiRegisterModal #modalInteractionBtn").one("click", function () {
        console.log(JSON.stringify(mbtiJson));
        $.ajax({
            async: false,
            type: 'POST',
            url: '/api/mbti/registration',
            data: JSON.stringify(mbtiJson),
            dataType: 'JSON',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                console.log(data);
                mbtiRegisterModalElem.querySelector(".modal-header").innerHTML = "Alert";
                mbtiRegisterModalElem.querySelector(".modal-body").innerHTML = "Registration was successful.";
                mbtiSubmitBtn.innerHTML = "Return to MBTI List";
                $("#mbtiRegisterModal #modalInteractionBtn").one("click", function () {
                    location.href = "/mbti/list";
                });
                $("#mbtiRegisterModal button[data-bs-dismiss='modal']").one("click", function () {
                    location.reload();
                });
            },
            error: function (data) {
                console.log(data);
            }
        });
    });
});

