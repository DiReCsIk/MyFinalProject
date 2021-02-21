<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>DireElective - Sign in</title>
        <link rel="stylesheet" href="../style/SignIn.css" type="text/css">
        <link rel="shortcut icon" href="../image/icon.png" type="image/png">
    </head>
    <body>
        <%@include file="../WEB-INF/pageFragment/menu.jspf"%>
        <c:if test="${sessionScope.user ne null}">
            <script>document.location.href="/page/main.jsp"</script>
        </c:if>
        <main>
            <div class="box">
                <header>
                    <h1><fmt:message key="signIn.header"/></h1>
                </header>
                <c:if test="${not empty exception}">
                    <p class="exception"><c:out value="${exception}"/></p>
                    <c:remove var="exception" scope="session"/>
                </c:if>
                <form method="post" action="${pageContext.request.contextPath}/Controller?command=signIn">
                    <label>
                        <b><fmt:message key="signIn.email"/></b>
                        <br><input type="text" placeholder="Enter Email" name="email" required><br>
                    </label>
                    <label>
                        <br><b><fmt:message key="signIn.password"/></b>
                        <br><input type="password" placeholder="Enter Password" name="password" required><br>
                    </label>
                    <label>
                        <br><input type="checkbox"  name="rememberBox"><fmt:message key="signIn.rememberMe"/><br>
                    </label>
                    <button type="submit"><fmt:message key="signIn.signInButton"/></button>
                </form>
            </div>
        </main>
    </body>
</html>
