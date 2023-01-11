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
let photoArray = []
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
            document.getElementById("text").style.display = "none"
            console.log(typeof data,data,data.length)
            data.forEach(path=>{
                console.log(path)
                photoArray.push(path)
            })
            console.log(photoArray)
            genImages()
        })
        .catch(error => console.log(error))
}
const delPhoto = (id) =>{
    console.log(id)
    const photoArray2 = photoArray.filter(item=>item!==id)
    photoArray = photoArray2
    console.log(photoArray)
    if(photoArray.length == 0){
        document.getElementById("text").style.display = "block"
    }
    genImages()
}
const genImages = () =>{
    document.getElementById("data_in_container").innerHTML = ""
    photoArray.forEach(path=>{
        document.getElementById("data_in_container").innerHTML += `<div class="image_car"><img src="/thumb?id=${path}" alt="obrazek" width="200px" height="200px"/><div class="del_button" onclick="delPhoto('${path}')"><p>X</p></div></div>`
    })
}
document.getElementById("save").onclick = async() =>{
    const body = JSON.stringify({data:photoArray})
    fetch("/savePhotos", { method: "post", body })
        .then(response => response.text())
        .then(data => {
            alert(data)
        })
        .catch(error => console.log(error))
}