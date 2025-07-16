async function VerifyAccount() {

    const verificationCode = document.getElementById("verifyCode").value;

    const verification = {
        verificationCode: verificationCode
    };

    const verificationJson = JSON.stringify(verification);

    const response = await fetch(
        "VerifyAccount",
        {
            method: "POST",
            body: verificationJson,
            headers: {
                "Content-type": "application/json"
            }
        }
    );

    if (response.ok) {
        const json = await response.json();
        if (json.status) {
            window.location = "index.html";
        } else {
            document.getElementById("message").innerHTML = json.message;
        }

    } else {
        document.getElementById("message").innerHTML = "Sign Up Failed. Try again Later.";
    }
}