<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<html>
<head>
    <title>500 - Error</title>
    <link rel="shortcut icon" href="../image/icon.png">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700" rel="stylesheet">
    <link rel="stylesheet" href="../style/Error.css">
</head>
<body>
<div class="message">
    <h1>500</h1>
    <h3>Server Error</h3>
    <h2>It's not you, it's me.</h2>
    <button><a href="<c:url value="/page/main.jsp"/>">Go Home</a></button>
</div>
</body>
</html>
