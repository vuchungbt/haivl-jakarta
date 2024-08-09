<%@include file="/common/taglib.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        <sitemesh:write property="title"/>
    </title>
    <link href="<c:url value='/public/web/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value='/public/web/css/customize.css'/> " rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value='/public/web/css/dropzone.css'/> " rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value='/public/web/css/glightbox.min.css'/> " rel="stylesheet" type="text/css" media="all">
    <script src="<c:url value='/public/web/js/kit.42d5adcbca.js' />"></script>

    <!-- Dark mode -->
    <script  src="<c:url value='/public/web/js/theme.js' />"></script>

    <link rel="stylesheet" href="<c:url value='/public/web/css/dropify.min.css' />" />

  
</head>
<body>

    <%@ include file="/common/web/header-bar.jsp" %>

    <sitemesh:write property="body"/>

    <!-- Modal -->
              <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModal"
                aria-hidden="true">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h1 class="modal-title fs-5" id="exampleModalLabel">Login</h1>
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="d-grid gap-2 pt-2">
                            <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/login&response_type=code&client_id=849071406479-n1efj6bifntnb87eqphc2qm5ggsdgr9i.apps.googleusercontent.com&approval_prompt=force"
                               id = "google-id" class="btn btn-block btn-outline-info"> <i class="fab fa-google"></i>   Login via Google</a>
                            <a href="https://www.facebook.com/v20.0/dialog/oauth?client_id=1027609085753624&redirect_uri=http://localhost:8080/login&scope=email,public_profile"
                               id = "facebook-id" class="btn btn-block btn-outline-primary"><i class="fab fa-facebook-f"></i>   Login via facebook </a>
                        </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                  </div>
                </div>
              </div>

<%--footer--%>
    <%@include file="/common/web/footer.jsp"%>
<%--footer--%>
</body>

<%--      <script  src="<c:url value='/template/web/js/token/refreshToken.js'/> "></script>--%>
<%--      <script   src="<c:url value='/template/web/js/bootstrap.bundle.min.js'/>"></script>--%>
<%--      <script   src="<c:url value='/template/web/js/customize.js'/> "></script>--%>
<%--      <script src="<c:url value='/template/web/js/loginDirection/loginDirection.js'/> "></script>--%>
<script src="<c:url value='/public/login/js/sendDirection.js'/> " type="text/javascript"></script>

</html>

