var URL = 'http://localhost:8080/jcindustries/'

document.getElementById("shoe").onclick = () => {
    console.log('I have been clicked');
    let xhr = new XMLHttpRequest();
    xhr.open("POST", URL + "app/addemp");
    let employee = {};
    employee.username = document.getElementById('empusername').value;
    employee.password = document.getElementById('emppassword').value;
    employee.password = document.getElementById('empname').value;
    xhr.onreadystatechange = () => {
        if(xhr.readyState === 4)
        {
            if(xhr.status === 200)
            {
                console.log('Success');
                console.log('response: ' + xhr.responseText);
                location.assign(URL + 'app/' + xhr.responseText);
            }
        }
    };
    xhr.send(JSON.stringify(employee));
};