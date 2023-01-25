let params = new URLSearchParams(location.search);
let flag = true
let x,y,w,h
const path = params.get('path')
const DeleteLast = (text) =>{
    return text.substring(0,text.length - 2)
}
const genImage = (data)=>{
    const d = data.split(",")
    console.log(d)
    carPhoto.style.backgroundImage = `url("/image?id=${path}&rand=${Math.random()}")`
    carPhoto.style.width = d[0] + "px"
    carPhoto.style.height = d[1] + "px"
}
const carPhoto = document.getElementById("carPhoto")
window.onload = async() =>{
    const options = {
        method: "POST",
        body:JSON.stringify({id:path})
    };
    await fetch("/getSize",options).then(response=>response.text()).then((data)=>{
        genImage(data)
    })
}

const setDivWidth = (div,e2,e) =>{
    if(flag){
        div.style.width = (e2.offsetX-e.offsetX).toString()  + "px"
        div.style.height = (e2.offsetY-e.offsetY).toString()  + "px"
    }
}

document.getElementById("rotate").onclick = async() =>{
    const options = {
        method: "POST",
        body:JSON.stringify({id:path})
    };
    await fetch("/rotate",options).then(response=>response.text()).then((data)=>{
        genImage(data)
    })
}

const Flip = async(type) =>{
    const options = {
        method: "POST",
        body:JSON.stringify({id:path,type:type})
    };
    await fetch("/flip",options).then(response=>response.text()).then((data)=>{
        genImage(data)
    })
}
document.getElementById("flipH").onclick = async() => Flip("h")

document.getElementById("flipV").onclick = async() => Flip("v")

const div = document.createElement("div");
div.style.position = "absolute";
div.style.backgroundColor = "rgba(255,255,0,0.7)"
div.style.border= "1px solid orange"
div.style.width = "0px"
div.style.height = "0px"
carPhoto.addEventListener("mousedown",(e)=>{
    flag = true
    console.log("myszka klikneła",e.offsetX,e.offsetY)
    div.style.top = e.offsetY.toString() + "px";
    div.style.left = e.offsetX.toString() + "px";

    carPhoto.appendChild(div)
    carPhoto.addEventListener("mousemove",(e2)=>setDivWidth(div,e2,e))
})
carPhoto.addEventListener("mouseup",(e)=>{
    console.log("myszka klikneła",e.offsetX,e.offsetY)
    x = DeleteLast(div.style.left)
    y = DeleteLast(div.style.top)
    w = DeleteLast(div.style.width)
    h = DeleteLast(div.style.height)
    flag = false
})
document.getElementById("crop").onclick = async()=>{
    const options = {
        method: "POST",
        body:JSON.stringify({id:path,x,y,w,h})
    };
    if(div.style.width != "0px" && div.style.height != "0px"){
        await fetch("/crop",options).then(response=>response.text()).then((data)=>{
            genImage(data)
            div.style.width = "0px"
            div.style.height = "0px"
        })

    }

}





