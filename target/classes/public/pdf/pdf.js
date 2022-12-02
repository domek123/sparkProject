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
generateData = async () => {
    const options = {
        method: "POST",
        data:{}
    };
    let response = await fetch("/generate", options)

    if (!response.ok)
        return response.status
    else
        return await response.json() // response.json
}
generateInvoice = async (id) => {
    const options = {
        method: "POST",
        body:JSON.stringify({id:id})
    };
    let response = await fetch("/invoice", options)

    if (!response.ok)
        return response.status
    else
        return await response.json() // response.json
}
window.onload = async() =>{
    const data = await GetData()
    generatePage(data)
}
document.getElementById("genBtn").onclick = async()=>{
    const data = await generateData()
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
        c['airbags'].forEach(item=>{
            html +="<div>"+item['name']+":" + item['value'] +"</div>";
        })
        html +="</div>";
        html +=`<div class='container-item box' style='background-color:`+c['color']+`;'></div>`;
        html +="<div class='container-item'><button onclick='genVat(`"+c['id']+"`)'>generuj fakture VAT</button></div>";
        if(c['invoice']==true){
            html +="<div class='container-item'><a href='/invoices?id=" + c['id'] +"'>pobierz</a></div>";
        }
        html +="</div>";
        i++;
    })
    html +="</div>";
    document.getElementById("data-container").innerHTML = html
}
async function genVat(id){
    console.log(id)
    const data = await generateInvoice(id)
    generatePage(data)
}
