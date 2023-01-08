document.querySelector("html").ondragover = function (e) {
    document.getElementById("text").innerText = "zdjecie nad dokumentem html"
e.preventDefault(); // usuwa domyślne zachowanie strony po wykonaniu zdarzenia, warto zakomentować i sprawdzić
e.stopPropagation(); // zatrzymuje dalszą propagację zdarzenia, warto zakomentować i sprawdzić
}
document.querySelector("html").ondragleave = function (e) {
    document.getElementById("text").innerText = "zdjecie poza dokumentem html"
    e.preventDefault();
    e.stopPropagation();
}
document.getElementById("data-container").ondragover = function (e) {
    document.getElementById("text").innerText = "upusc zdjecie"
    e.preventDefault();
    e.stopPropagation();
}
document.getElementById("data-container").ondrop = function (e) {
    console.log("drop na divie")
    e.stopPropagation();
    e.preventDefault();

    const files = e.dataTransfer.files;
    console.log(files)

    const fd = new FormData();

    console.log(files.length)
    for (let i = 0; i < files.length; i++) {
        fd.append('file', files[i]);
    }
    console.log(fd)
    const body = fd

    fetch("/upload", { method: "post", body })
        .then(response => response.json())
        .then(data => {
            alert(data)
            data.forEach(path=>{
                document.getElementById("data-container").innerHTML += `<img src="" alt="obrazek" width="100px" height="100px"/>`
            })

        })
        .catch(error => console.log(error))

}