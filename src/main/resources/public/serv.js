fetchPostAsync = async () => {

    const data = JSON.stringify({
        model:document.getElementById("model").value,
        rok:document.getElementById("rok").value,
        data: [
            {name: "1", value: document.getElementById("k").checked},
            {name: "2", value: document.getElementById("p").checked},
            {name: "3", value: document.getElementById("t").checked},
            {name: "4", value: document.getElementById("b").checked},
],
        color:document.getElementById("kolor").value
    })

    const options = {
        method: "POST",
        body: data,
    };

    let response = await fetch("/add", options)

    if (!response.ok)
        return response.status
    else
        return await response.json() // response.json

}
document.getElementById("addBtn").onclick = async () => {
    let json = await fetchPostAsync()
    alert(json)
}