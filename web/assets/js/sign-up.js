async function SignUp() {
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const user = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password
    };

    const userJson = JSON.stringify(user);

    const response = await fetch(
        "SignUp",
        {
            method: "POST",
            body: userJson,
            headers: {
                "Content-type": "application/json"
            }
        }
    );

    if (response.ok) { //Success
        const json = await response.json();
        if (json.status) {
            document.getElementById("message").className = "text-success";
            document.getElementById("message").innerHTML = json.message;
            window.location = "verify-account.html";
        } else {
            document.getElementById("message").innerHTML = json.message;
        }
    } else {
        document.getElementById("message").innerHTML = ("Sign Up Failed. Try again Later.");

    }
}