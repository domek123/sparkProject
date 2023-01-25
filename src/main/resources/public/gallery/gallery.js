
GetData = async () => {
    const options = {
        method: "POST",
        data:{}
    };
    let response = await fetch("/getPhotos", options)

    if (!response.ok)
        return response.status
    else
        return await response.json() // response.json
}
window.onload = async() =>{
    const data = await GetData()
    console.log(data)
    genImages(data)
}
const genImages = (data) =>{
    data.forEach(path=>{
        document.getElementById("photos-container").innerHTML += `<div class="image_car"><img src="/thumb?id=${path}" alt="obrazek"/><a href="/imager/imager.html?path=${path}"><div class="edit_button"><p>edit</p></div></a></div>`
    })
}

