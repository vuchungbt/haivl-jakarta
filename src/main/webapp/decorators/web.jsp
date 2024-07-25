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

<%--footer--%>
<%--<%@include file="/common/web/footer.jsp"%>--%>
<%--<sitemesh:include page="/common/web/footer.jsp"/>--%>
<%--footer--%>
</body>

<%--      <script  src="<c:url value='/template/web/js/token/refreshToken.js'/> "></script>--%>
<%--      <script   src="<c:url value='/template/web/js/bootstrap.bundle.min.js'/>"></script>--%>
<%--      <script   src="<c:url value='/template/web/js/customize.js'/> "></script>--%>
<%--      <script src="<c:url value='/template/web/js/loginDirection/loginDirection.js'/> "></script>--%>

</html>

