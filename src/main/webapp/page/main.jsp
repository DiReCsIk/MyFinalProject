<%@ page contentType="text/html; charset=UTF-8"%>
<html lang="${sessionScope.lang}">
    <head>
        <title>DireElective - Main page</title>
        <link rel="shortcut icon" href="../image/icon.png" type="image/png">
        <link rel="stylesheet" href="../style/Main.css" type="text/css">
    </head>
    <body>
        <%@include file="../WEB-INF/pageFragment/menu.jspf" %>
        <main>
            <div class="data">
                <header class="theme">
                    <h1><fmt:message key="main.header"/></h1>
                </header>
                <div class="text">
                    <p><fmt:message key="main.firstParagraph"/></p>
                    <p><fmt:message key="main.secondParagraph"/></p>
                </div>
                <div class="dropDown">
                    <button class="dropButton"><fmt:message key="menu.selectLanguage"/></button>
                    <div class="dropDown-content">
                        <a onclick="location.href='/page/main.jsp?lang=ru'">RU</a>
                        <a onclick="location.href='/page/main.jsp?lang=en'">EN</a>
                    </div>
                </div>
            </div>
        </main>
    </body>
</html>