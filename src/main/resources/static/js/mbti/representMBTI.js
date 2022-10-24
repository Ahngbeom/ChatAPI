const representMBTIUpdateSelect = document.querySelector("#representMBTIUpdateSelect");
const representMBTIUpdateBtn = document.querySelector("#representMBTIUpdateBtn");

representMBTIUpdateBtn.addEventListener('click', function () {
    const representMBTICode = $("#representMBTIUpdateSelect option:selected").val();
    if (representMBTICode === "") {
        renewalModal({
            target: confirmModalElem,
            title: "대표 MBTI 지정",
            body: "지정 또는 변경하실 대표 MBTI를 선택하세요."
        });
        showModalTarget(confirmModalElem);
        return;
    }

    console.log(representMBTICode);

    renewalModal({
        target: confirmModalElem,
        title: "대표 MBTI 지정",
        body: "대표 MBTI를 [" + representMBTICode + "] 으로 지정하시겠습니까?"
    }, "<button  type=\"button\" class='btn btn-primary' id='Accept'>Accept</button>");
    showModalTarget(confirmModalElem);

    confirmModalElem.querySelector("#Accept").addEventListener('click', function () {

        $.ajax({
            type: 'GET',
            url: '/api/mbti/assign-represent',
            async: false,
            contentType: 'application/json; charset=utf-8',
            data: {mbtiCode: representMBTICode},
            success: function (data) {
                if (data) {
                    renewalModal({
                        target: confirmModalElem,
                        type: "success",
                        title: "대표 MBTI를 [" + representMBTICode + "] 으로 지정하였습니다.",
                    }, "<button  type=\"button\" class='btn btn-primary' id='Confirm'>Confirm</button>");
                    showModalTarget(confirmModalElem);
                    confirmModalElem.querySelector("#Confirm").addEventListener('click', function () {
                        location.reload();
                    }, {once: true});
                }
            },
            error: function (xhr) {
                console.error(xhr.responseText);
            }
        });
    }, {once: true});
});