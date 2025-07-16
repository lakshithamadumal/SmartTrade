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
}