var modelList;

window.onload = async function () {
    const response = await fetch("LoadProductData");

    if (response.ok) { // success
        const json = await response.json();
        if (json.status) { // if true

            loadSelect("brand", json.brandList, "name");
            // loadSelect("model", json.modelList, "name");
            modelList = json.modelList;
            loadSelect("color", json.colorlList, "name");
            loadSelect("condition", json.qualitylList, "name");
            loadSelect("storage", json.storagelList, "name");

        } else { // when status false
            document.getElementById("message").innerHTML = "Unable to get product data!";
        }
    } else {
        document.getElementById("message").innerHTML = "Unable to get product data! Please try again later.";
    }
    MyAccount();
    GetCityData();
}

function loadSelect(selectId, list, property) {

    const select = document.getElementById(selectId);

    list.forEach(item => {
        const option = document.createElement("option");
        option.value = item.id;
        option.innerHTML = item[property];
        select.appendChild(option);
    });

}

function loadModels() {
    const brandId = document.getElementById("brand").value;

    const modelSelect = document.getElementById("model");
    modelSelect.length = 1;

    modelList.forEach(item => {
        if (item.brand.id == brandId) {
            const option = document.createElement("option");
            option.value = item.id;
            option.innerHTML = item.name;
            modelSelect.appendChild(option);
        }
    });
}

async function saveProduct() {
    const brandId = document.getElementById("brand").value;
    const modelId = document.getElementById("model").value;
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;
    const storageId = document.getElementById("storage").value;
    const colorId = document.getElementById("color").value;
    const conditionId = document.getElementById("condition").value;
    const price = document.getElementById("price").value;
    const qty = document.getElementById("qty").value;
    const image1 = document.getElementById("img1").files[0];
    const image2 = document.getElementById("img2").files[0];
    const image3 = document.getElementById("img3").files[0];

    const form = new FormData();
    form.append("brandId", brandId);
    form.append("modelId", modelId);
    form.append("title", title);
    form.append("description", description);
    form.append("storageId", storageId);
    form.append("colorId", colorId);
    form.append("conditionId", conditionId);
    form.append("price", price);
    form.append("qty", qty);
    form.append("image1", image1);
    form.append("image2", image2);
    form.append("image3", image3);

    const response = await fetch("SaveProduct", {
        method: "POST",
        body: form,
    });

    const result = await response.text();
    console.log("Server Response:", result);
}



async function MyAccount() {

    const response = await fetch("MyAccount");
    if (response.ok) {
        const json = await response.json();

        document.getElementById("username").innerHTML = `Hello, ${json.firstName} ${json.lastName}`;
        document.getElementById("since").innerHTML = `Smart Trade Member Since, ${json.since}`;
        document.getElementById("firstName").value = json.firstName;
        document.getElementById("lastName").value = json.lastName;
        document.getElementById("currentPassword").value = json.password;

    } else {

    }
}


async function GetCityData() {
    try {
        const response = await fetch("CityData"); // assuming correct servlet mapping
        if (response.ok) {
            const json = await response.json(); // <-- FIXED this line

            const citySelect = document.getElementById("citySelect");
            let option = document.createElement("option");
            json.forEach(city => {

                let option = document.createElement("option");
                option.innerHTML = city.name;
                option.value - city.id;
                citySelect.appendChild(option);
            });



        } else {
            console.error("Server error:", response.status);
        }
    } catch (error) {
        console.error("Fetch failed:", error);
    }
}


async function SaveChanges() {

    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const lineOne = document.getElementById("lineOne").value;
    const lineTwo = document.getElementById("lineTwo").value;
    const postalCode = document.getElementById("postalCode").value;
    const cityId = document.getElementById("citySelect").value;
    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    const userDataObject = {
        firstName: firstName,
        lastName: lastName,
        lineOne: lineOne,
        lineTwo: lineTwo,
        postalCode: postalCode,
        cityId: cityId,
        currentPassword: currentPassword,
        newPassword: newPassword,
        confirmPassword: confirmPassword
    };


    const response = await fetch("MyAccount", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userDataObject)
    });

    if (response.ok) {
        const json = await response.json();
        if (json.status) {
            if (json.message === "User Profile Updated!") {
                window.location.reload();
            } else {
                document.getElementById("message").innerHTML = json.message;
            }
        } else {
            document.getElementById("message").innerHTML = json.message;
        }
    } else {
        document.getElementById("message").innerHTML = json.message;

    }

}
