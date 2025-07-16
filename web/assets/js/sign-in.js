async function SignIn() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const SignIn = {
        email: email,
        password: password
    };

    const SignInJson = JSON.stringify(SignIn);

    const response = await fetch(
        "SignIp",
        {
            method: "POST",
            body: SignInJson,
            headers: {
                "Content-type": "application/json"
            }
        }
    );

    if (response.ok) { //Success
        const json = await response.json();
        if (json.status) {
            if (json.message === "1") {
                window.location = "verify-account.html";
            } else {
                window.location = "index.html";
            }
        } else {
            document.getElementById("message").innerHTML = json.message;
        }
    } else {
        document.getElementById("message").innerHTML = ("Sign In Failed. Try again Later.");

    }
}