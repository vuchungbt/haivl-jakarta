<%@include file="/common/taglib.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>SiteMesh3 JSP and Page Title: <sitemesh:write property="title"/></title>
    <sitemesh:write property="head"/>

    <link href="<c:url value='/template/web/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value='/template/web/css/customize.css'/> " rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value='/template/web/css/dropzone.css'/> " rel="stylesheet" type="text/css" media="all">
    <link href="<c:url value='/template/web/css/glightbox.min.css'/> " rel="stylesheet" type="text/css" media="all">

    <script src="<c:url value='/template/web/js/kit.42d5adcbca.js' />"></script>

    <!-- Dark mode -->
    <script  src="<c:url value='/template/web/js/theme.js' />"></script>

    <script  src="/template/web/js/token/refreshToken.js"></script>
</head>
<body>
    <div>Text in web.jsp decorator </div>
    <sitemesh:write property="body"/>

</body>
</html>