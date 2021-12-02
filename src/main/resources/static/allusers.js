$(document).ready(function () {
    allreadUsers();
});

function allreadUsers() {
    fetch("/admin/users")
        .then(res => res.json())
        .then(userslist => {
            let output = ``
            if (userslist.length > 0){
                userslist.forEach(user =>{
                    let roless = "";
                    user.roles.forEach(role => roless += role.role + " ");
                output +=`<tr>
                                <td>${user.id}</td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.username}</td>
                                <td>${roless}</td>
                                <td><button class="btn btn-primary" id="editButton" data-toggle="modal" data-target="#editModal" onclick='editModal(${user.id})'>Edit</button></td>
                                <td><button type="button" class="btn btn-danger" id="deleteButton" data-toggle="modal" data-target="#deleteModal" onclick='deleteModal(${user.id})'>Delete</button></td>
                                </tr>`

                })
            document.getElementById("userslist").innerHTML = output;
        }})
}

function editModal(id) {
    fetch('admin/users/' + id)
        .then(res => res.json())
        .then(user => {
            $('#editUserId').attr('value', user.id)
            $('#editFirstname').attr('value', user.firstName)
            $('#editLastname').attr('value', user.lastName)
            $('#editLogin').attr('value', user.username)
            $('#editPassword').attr('value', user.password)
            $('#editRoles').attr('value', user.roles)
        })
}

function getUserRole(setRole) {
    let roles = []

    if (setRole.indexOf("ROLE_ADMIN") !== -1) {
        roles.push({id: 1, role: "ROLE_ADMIN", authority: "ROLE_ADMIN"})
    }else if (setRole.indexOf("ROLE_USER") !== -1) {
        roles.push({id: 2, role: "ROLE_USER", authority: "ROLE_USER"})
    }
    return roles
}

function updateUser() {

    let id = document.getElementById("editUserId").value;
    let name = document.getElementById("editFirstname").value;
    let lastname = document.getElementById("editLastname").value;
    let username = document.getElementById("editLogin").value;
    let password = document.getElementById("editPassword").value;
    let roles = getUserRole(Array.from(document.getElementById("editRoles").selectedOptions).map(options => options.value))

    let user = {
        id: id,
        firstName: name,
        lastName: lastname,
        username: username,
        password: password,
        roles: roles
    }

    fetch('admin/users/' + id, {
        method:"PUT",
        headers: {
            "Accept": "application/json",
            "Content-type":"application/json"
        },
        body:JSON.stringify(user)
    })
        .then(() => allreadUsers())
}

function refreshTable() {
    let table = document.getElementById('userslist')
    while (table.rows.length > 1) {
        table.deleteRow(1)
    }
    setTimeout(allreadUsers, 500);
}

function deleteModal(id) {
    fetch('admin/users/' + id)
        .then(res => res.json())
        .then(user => {
            $('#deleteUserId').attr('value', user.id)
            $('#deleteUserFirstname').attr('value', user.firstName)
            $('#deleteUserLastname').attr('value', user.lastName)
            $('#deleteUserLogin').attr('value', user.username)
            $('#deleteUserPassword').attr('value', user.password)
            $('#deleteRoles').attr('value', user.roles)
        })
}

function deleteUser() {
    let id = document.getElementById("deleteUserId").value;
    let name = document.getElementById("deleteUserFirstname").value;
    let lastname = document.getElementById("deleteUserLastname").value;
    let username = document.getElementById("deleteUserLogin").value;
    let password = document.getElementById("deleteUserPassword").value;

    let user = {
        id: id,
        firstName: name,
        lastName: lastname,
        username: username,
        password: password,
    }
    fetch('admin/users/' + id, {
        method:"DELETE",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }})
        .then(() => allreadUsers())
    refreshTable();
}

function createUser() {

    let firstname = document.getElementById("newFirstname").value
    let lastname = document.getElementById("newLastname").value
    let username = document.getElementById("newUsername").value
    let password = document.getElementById("newPassword").value
    let roles = getUserRole(Array.from(document.getElementById("newRoles").selectedOptions).map(options => options.value))

    let user = {
        firstName: firstname,
        lastName: lastname,
        username: username,
        password: password,
        roles: roles
    }

    fetch("admin/users", {
        method: "POST",
        headers: {
            // "Accept": "application/json",
            "Content-type": "application/json"
        },
        body: JSON.stringify(user),
    })
        .then(() => allreadUsers())
}


