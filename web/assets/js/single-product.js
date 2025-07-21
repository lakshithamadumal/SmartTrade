async function loadData() {
    const searchParams = new URLSearchParams(window.location.search);
    if (searchParams.has("id")) {
        const productId = searchParams.get("id");
        console.log(productId);

        const response = await fetch("LoadSingleProduct?id=" + productId);
        if (response.ok) {
            const json = await response.json();
            if (json.status) {
                console.log(json);
                document.getElementById("product-title").innerHTML = json.product.title;
                document.getElementById("published-on").innerHTML = json.product.created_at;
                document.getElementById("product-price").innerHTML = json.product.price;
                document.getElementById("brand").innerHTML = json.product.model.brand.name;
                document.getElementById("model").innerHTML = json.product.model.name;
                document.getElementById("condition").innerHTML = json.product.quality.value;
                document.getElementById("qty").innerHTML = json.product.qty;
            } else {

            }

        } else {
        }
    } else {
    }
}
