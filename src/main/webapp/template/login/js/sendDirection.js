    let urlParams = new URLSearchParams(window.location.search);
    let sendDirection = urlParams.get("send-direction") || '';
    let stateParam = urlParams.get("state");
    if (!sendDirection&&stateParam) {
        try {
            let stateObj = JSON.parse(decodeURIComponent(stateParam));
            sendDirection = stateObj["send-direction"] || '';
        } catch (error) {
        console.error("Error parsing state parameter:", error);
        }
    }
    let googleLogin = document.getElementById("google-id");
    let facebookLogin = document.getElementById("facebook-id");
    if(googleLogin){
        googleLogin.addEventListener('click', function (event){
            event.preventDefault();
            let sateParam = (JSON.stringify({"send-direction": sendDirection, provider:'google' }))
            let newHref = this.href + "&state=" + sateParam;
            window.location.href = newHref;
        })
    }
    if(facebookLogin){
        facebookLogin.addEventListener('click',function (event){
            event.preventDefault();
            let stateParam = encodeURIComponent(JSON.stringify({"send-direction": sendDirection, provider:'facebook'}))
            let newHref = this.href + "&state" + stateParam;
            window.location.href = newHref;
        })
    }