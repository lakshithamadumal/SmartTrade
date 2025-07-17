window.onload = async function () {

    const response = await fetch("MyAccount");
    if (response.ok) {
        const json = await response.json();

        document.getElementById("username").innerHTML = `Hello, ${json.firstName} ${json.lastName}`;
        document.getElementById("since").innerHTML = `Smart Trade Member Since, ${json.since}`;
        document.getElementById("firstName").value =json.firstName;
        document.getElementById("lastName").value =json.lastName;
        document.getElementById("currentPassword").value =json.password;

    } else {

    }
}