export const removeChildNode = function (target) {
    let firstElemChild = target.firstElementChild;
    while (firstElemChild) {
        firstElemChild.remove();
        firstElemChild = target.firstElementChild;
    }
}

export const replaceChildNode = function (target, replaceHTML) {
    removeChildNode(target);
    if (typeof replaceHTML === "object")
        target.insertAdjacentElement("beforeend", replaceHTML);
    else
        target.innerHTML = replaceHTML;
}