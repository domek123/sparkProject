GetData = async (href) => {
    const options = {
        method: "POST",
        body:{}
    };
    let response = await fetch("/"+href, options)

    if (!response.ok)
        return response.status
    else
        return await response.json() // response.json
}
const allCarInvoices = []
const yearCarInvoices = []
const priceCarInvoices = []
generateInvoice = async (id,body,array,href,name)=>{
    const options = {
        method: "POST",
        body:JSON.stringify(body)
    };
    await fetch("/"+href, options).then(response=>response.json()).then(data=>{
        array.push(data)
        let str = ''
        array.forEach(item=>{
            str+= `<a href='/invoices?id=${name}${item}'>pobierz</a>`
        })
        document.getElementById(id).innerHTML = str
    })
}
document.getElementById("allInvoicesBtn").onclick = () => generateInvoice(
    "allCarInvoices",
    {},
    allCarInvoices,
    "allCarInvoice",
    "invoice_all_cars_"
    )
document.getElementById("yearInvoicesBtn").onclick = () => generateInvoice(
    "yearCarInvoices",
    {year:document.getElementById("rok").value},
    yearCarInvoices,
    "yearCarInvoice",
    "invoice_year_cars_"
    )
document.getElementById("priceInvoicesBtn").onclick = () => generateInvoice(
    "priceCarInvoices",
    {min:document.getElementById("minPrice").value,max:document.getElementById("maxPrice").value},
    priceCarInvoices,
    "priceCarInvoice",
    "invoice_price_cars_",

    )

window.onload = async() =>{
    const data = await GetData("json")
    generatePage(data)
}
document.getElementById("genBtn").onclick = async()=>{
    const data = await GetData("generate")
    generatePage(data)
}

generatePage = (cars) =>{
    let i = 1;
    let html = ""

    html +="<div>";
    cars.forEach(c=>{
        html +="<div class='flex'>";
        html +="<div class='container-item'>"+i+"</div>";
        html +="<div class='container-item'>"+c['model']+"</div>";
        html +="<div class='container-item'>"+c['rok']+"</div>";
        html +="<div class='container-item flex-col'>";
        c['airbags'].forEach(item=>{
            html +="<div>"+item['name']+":" + item['value'] +"</div>";
        })
        html +="</div>";
        html +=`<div class='container-item box' style='background-color:`+c['color']+`;'></div>`;
        html += `<div class='container-item'>${c['data']}</div>`;
        html += `<div class='container-item'>${c['cena']}</div>`;
        html += `<div class='container-item'>${c['vat']}</div>`;
        html +="</div>";
        i++;
    })
    html +="</div>";
    document.getElementById("data-container").innerHTML = html
}
