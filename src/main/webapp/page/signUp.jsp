<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>DireElective - Sign up</title>
        <link rel="stylesheet" href="../style/SignUp.css" type="text/css">
        <link rel="shortcut icon" href="../image/icon.png" type="image/png">
    </head>
    <body>
        <%@include file="../WEB-INF/pageFragment/menu.jspf"%>
        <main>
            <div class="box">
                <header>
                     <h1><fmt:message key="signUp.header"/></h1>
                </header>
                <c:if test="${not empty exception}">
                    <p class="exception"><c:out value="${exception}"/></p>
                    <c:remove var="exception" scope="session"/>
                </c:if>
                <form id="signUp" method="post" action="${pageContext.request.contextPath}/Controller?command=signUp">
                    <label>
                        <input type="text" placeholder="Name" name="name" required pattern="^[A-Za-z]+$" title="This field must consist only characters with first at upper case">
                    </label>
                    <label>
                        <input type="text" placeholder="Surname" name="surName" required pattern="^[A-Za-z]+$" title="This field must consist only characters with first at upper case"><br>
                    </label>
                    <label>
                        <br><input type="email" placeholder="Enter Email" name="email" required><br>
                    </label>
                    <label>
                        <br><input onkeyup="validatePassword()" name="password" id="password" type="password" placeholder="Enter Password" required
                        pattern="(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$" title="Password must be at least 8 characters long with 1 or more digits">
                    </label>
                    <label>
                        <input onkeyup="validatePassword()" id="confirmPassword" type="password" placeholder="Repeat Password" name="psw-repeat" required><br>
                    </label>
                    <label>
                        <br><b style="font-family: Arial, sans-serif"><fmt:message key="signUp.birthDate"/></b>
                        <input type="date" id="date" onkeydown="return false" name="birthDate" required>
                        <button type="submit" class="loginButton"><fmt:message key="signUp.createAccount"/></button>
                    </label>
                </form>
            </div>
        </main>
        <script src="../script/SignUp.js"></script>
    </body>
</html>
