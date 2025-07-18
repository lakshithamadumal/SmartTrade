window.onload = async function () {
    const response = await fetch("LoadProductData");

    if (response.ok) { // success
        const json = await response.json();
        if (json.status) { // if true

            loadSelect("brand" , json.brandList , "name");
            loadSelect("model" , json.modelList , "name");
            loadSelect("color" , json.colorlList , "name");
            loadSelect("condition" , json.qualitylList , "name");
            loadSelect("storage" , json.storagelList , "name");

        } else { // when status false
            document.getElementById("message").innerHTML = "Unable to get product data!";
        }
    } else {
        document.getElementById("message").innerHTML = "Unable to get product data! Please try again later.";
    }
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