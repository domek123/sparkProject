const container = document.getElementById("cars-container")

GetData = async () => {
    const options = {
        method: "POST",
        data:{}
    };
    let response = await fetch("/json", options)

    if (!response.ok)
        return response.status
    else
        return await response.json() // response.json
}


window.onload = async() =>{
    const data = await GetData()
    console.log(data)
    generatePage(data)
}
generatePage = (cars) =>{
    let i = 1;
    let html = ""

    html +="<div>";
    cars.forEach(c=>{
        html +="<div class='flex'>";
        html +="<div class='container-item'>"+i+"</div>";
        html +="<div class='container-item'>"+c['id']+"</div>";
        html +="<div class='container-item'>"+c['model']+"</div>";
        html +="<div class='container-item'>"+c['rok']+"</div>";
        html +="<div class='container-item flex-col'>";
        c['data'].forEach(item=>{
            html +="<div>"+item['name']+":" + item['value'] +"</div>";
        })
        html +="</div>";
        html +="<div class='container-item box' style='background-color:"+c['color']+";'></div>";
        html +="<div class='container-item'><button onclick='deleteCar(`"+c['id']+"`)'>delete car</button></div>";
        html +="<div class='container-item'><button onclick='updateCarShow(`"+c['model']+"`,`"+c['id']+"`,`"+c['rok']+"`)'>update car</button></div>";
        html +="</div>";
        i++;
    })
    html +="</div>";
    container.innerHTML = html
}
deleteCar = async(id) =>{
    const data = JSON.stringify({id:id})
    const options = {
        method: "POST",
        body:data
    };
    await fetch("/delete", options).then(response=>response.json()).then(data=>generatePage(data))
}
const hov = document.getElementById("hover")
const hov2 = document.getElementById("hover-container")
updateCarShow = (model,id,rok) =>{
    hov.style.display = "flex"
    hov2.style.display = "flex"
    document.getElementById("model").value = model
    document.getElementById("rok").value = rok
    document.getElementById("update").onclick = async () =>{
        await updateCar(id)
    }
}
const cancel = document.getElementById("cancel")
cancel.onclick = () =>{
    hov.style.display = "none"
    hov2.style.display = "none"
}
updateCar = async(id)=>{
    const data = JSON.stringify({id:id,model:document.getElementById("model").value,rok:document.getElementById("rok").value})
    const options = {
        method: "POST",
        body:data
    };
    await fetch("/update", options).then(response=>response.json()).then(data=>{
        generatePage(data)
        hov.style.display = "none"
        hov2.style.display = "none"})
}
