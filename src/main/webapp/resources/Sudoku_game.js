window.onload = function (ev) { httpGetAsync(url)};
var butt;
var wrong_fields = [];
var xmlHttp = new XMLHttpRequest();
var url = "/get_fields";
var resultJSON;
function httpGetAsync(theUrl)
{
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
        {
            resultJSON = JSON.parse(this.responseText);
            Set_fields();
        }

    }
    xmlHttp.open("GET", theUrl, true);
    xmlHttp.send(null);


}
function Set_fields()
{
    var i;

    for(i=0; i<  $('input[name="grid"]').length; i++)
    {
        if(resultJSON[Math.floor(i/9)][i%9]["current_value"]===0)
        {
            $('input[name="grid"]')[i].setAttribute("value", "");
        }
        else
        {
            $('input[name="grid"]')[i].setAttribute("value", resultJSON[Math.floor(i/9)][i%9]["fixed_value"]);
            $('input[name="grid"]')[i].setAttribute("readonly", true);
        }
    }

    butt = $("#check");
    butt.click(Check);
}

function Check()
{
    if(butt.html()=="Check") {
        butt.html("Continue");
        checkAndGetWrongFields();
        if(wrong_fields.length==0)
            alert("Gratulacje! Plansza rozwiązana prawidłowo.");
        else
        {
            for(i = 0; i<wrong_fields.length; i++)
                wrong_fields[i].style.backgroundColor = "red";
        }

    }
    else
    {
        for(i = 0; i<wrong_fields.length; i++) {
            if (wrong_fields[i].classList.contains("gray_field"))
                wrong_fields[i].style.backgroundColor = "lightgray";
            else
                wrong_fields[i].style.backgroundColor = "white";
        }

        butt.html("Check");
    }
}

function checkAndGetWrongFields() {
    wrong_fields.length = 0;
    var field, i;
    var input_fields =  $('input[name="grid"]');
    for (i = 0; i < input_fields.length; i++) {
        if ((field = input_fields[i]).value != resultJSON[Math.floor(i / 9)][i % 9]["fixed_value"]) {
            wrong_fields.push(field);
        }
    }
}

