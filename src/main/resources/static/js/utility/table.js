const createTableElement = function (data) {

    const table = document.createElement('table');
    table.setAttribute('class', 'table table-success table-striped text-center table-responsive');
    const thead = table.createTHead();
    const thead_row = thead.insertRow(0);
    thead_row.insertCell(0).innerHTML = "#";
    thead_row.insertCell(1).innerHTML = "Username";
    thead_row.insertCell(2).innerHTML = "Nickname";
    thead_row.insertCell(3).innerHTML = "Authority";
    thead_row.insertCell(4).innerHTML = "MBTI-List";

    const tbody = table.createTBody();
    data.forEach((user, index) => {
        const tbody_row = tbody.insertRow(index);
        tbody_row.insertCell(0).innerHTML = user.id;
        tbody_row.insertCell(1).innerHTML = user.username;
        tbody_row.insertCell(2).innerHTML = user.nickname;
        tbody_row.insertCell(3).innerHTML = "<button class='btn btn-light getUserAuthoritiesBtn' data-userno='" + user.id + "'>Import</button>";
        tbody_row.insertCell(4).innerHTML = "<button class='btn btn-light getUserMBTIListBtn' data-userno='" + user.id + "'>Import</button>";
        console.log(user, index);
    });

    return table;
}