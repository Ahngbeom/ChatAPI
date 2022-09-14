export const removeAllRowsInTable = function (table) {

    if (table && table.querySelector("tbody tr")) {
        table.querySelectorAll("tbody tr").forEach(row => {
            row.remove();
        });
    }
}