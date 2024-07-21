document.addEventListener('DOMContentLoaded', function (){
    var pathSegments = window.location.pathname.split('/');
    var currentUrl = pathSegments.length > 1 ? pathSegments[1] : '';
    var loginLink = document.getElementById('login-link');
    if(loginLink&&currentUrl.length>1){
    loginLink.href = loginLink.href + "?send-direction=" + currentUrl;
}
})