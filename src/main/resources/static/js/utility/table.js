const createTableElement = function (data) {
    data.forEach(user => {
        console.log(user);
    });
    const table = document.createElement('table');
    table.setAttribute('class', 'table table-success table-striped');
    const thead = table.createTHead();
    const thead_row = thead.insertRow(0);
    thead_row.insertCell(0).innerHTML = "#";
    thead_row.insertCell(1).innerHTML = "Username";
    thead_row.insertCell(2).innerHTML = "Nickname";
    thead_row.insertCell(3).innerHTML = "Authority";
    thead_row.insertCell(4).innerHTML = "MBTI-List";

    const tbody = table.createTBody();
    const tbody_row = tbody.insertRow(0);
    tbody_row.insertCell(0).innerHTML = "?";
    tbody_row.insertCell(1).innerHTML = "?";
    tbody_row.insertCell(2).innerHTML = "?";
    tbody_row.insertCell(3).innerHTML = "?";
    tbody_row.insertCell(4).innerHTML = "?";

    return table;
}