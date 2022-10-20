export const insertMBTIDataToModal = function (target, mbtiJson) {
    return "<h1>" + mbtiJson.code + "</h1>" +
        "<h3 class='d-block'>" + mbtiJson.personality + "</h3>" +
        "<img src='" + mbtiJson.imgSrc + "'/>" +
        "<div>" + mbtiJson.introduction + "</div>" +
        "<a href='https://www.16personalities.com/" + mbtiJson.shortMbti + "-personality' target='_blank'>View details on the official site</a>";
}

