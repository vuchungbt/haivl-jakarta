<%@include file="/common/taglib.jsp"%>
<html>
<head>
    <title><sitemesh:write property="title"/></title>
    <%--    <link rel="stylesheet" href="<c:url value='/template/login/style.css'/>" />--%>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!------ Include the above in your HEAD tag ---------->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">


</head>
<body >

<div class="mainBody">
    <sitemesh:write property="body"/>
</div>
</body>

<script src="<c:url value='/public/login/js/sendDirection.js'/> " type="text/javascript"></script>
</html>
