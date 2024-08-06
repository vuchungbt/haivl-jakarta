<%--
  Created by IntelliJ IDEA.
  User: Thinh
  Date: 7/19/2024
  Time: 2:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng nhập</title>
</head>
<body>
<div class="container ">
    <div class="row">
        <aside class="col-sm-4 mx-auto">
            <div class="card">
                <article class="card-body">
                    <h4 class="card-title mb-4 mt-1">Sign in</h4>
                    <p>
                        <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/login&response_type=code&client_id=849071406479-n1efj6bifntnb87eqphc2qm5ggsdgr9i.apps.googleusercontent.com&approval_prompt=force"
                           id = "google-id" class="btn btn-block btn-outline-info"> <i class="fab fa-google"></i>   Login via Google</a>
                        <a href="https://www.facebook.com/v20.0/dialog/oauth?client_id=1027609085753624&redirect_uri=http://localhost:8080/login&scope=email,public_profile"
                           id = "facebook-id" class="btn btn-block btn-outline-primary"><i class="fab fa-facebook-f"></i>   Login via facebook
                        </a>
                    </p>
                </article>
            </div> <!-- card.// -->

        </aside> <!-- col.// -->
    </div> <!-- row.// -->

</div>
<!--container end.//-->
</body>
</html>