<%@ page contentType="text/html; charset=UTF-8" %>
<html>
    <head>
        <title>DireElective - My data</title>
        <link rel="shortcut icon" href="../image/icon.png" type="image/png">
        <link rel="stylesheet" href="../style/MyData.css" type="text/css">
    </head>
    <body>
        <%@include file="../WEB-INF/pageFragment/menu.jspf" %>
        <c:if test="${sessionScope.user == null}">
            <script>document.location.href="/page/main.jsp"</script>
        </c:if>
        <main>
            <header>
                <h1 class="h1Style">
                    <fmt:message key="myData.header"/>
                </h1>
            </header>
            <div class="container">
               <ul id="list" class="dataList">
                   <li><fmt:message key="myData.name"/><p>${sessionScope.user.name}</p></li>
                   <li><fmt:message key="myData.surName"/><p>${sessionScope.user.surName}</p></li>
                   <li><fmt:message key="myData.birthDate"/><p>${sessionScope.user.birthDate}</p></li>
                   <li><fmt:message key="myData.email"/><p>${sessionScope.email}</p></li>
                   <li><fmt:message key="myData.role"/><p>${sessionScope.user.userRole}</p></li>
               </ul>
                <form id="changeForm" class="form" method="post" action="${pageContext.request.contextPath}/Controller?command=updateUserInfo">
                    <label><fmt:message key="myData.newName"/><br>
                        <input type="text" value="${user.name}" placeholder="Name" name="name" required
                               pattern="^[A-Za-z]+$" title="This field must consist only characters with first at upper case">
                    </label>
                    <label><fmt:message key="myData.newSurname"/><br>
                        <input type="text" value="${user.surName}" placeholder="Last name" name="surName"
                               required pattern="^[A-Za-z]+$"
                               title="This field must consist only characters with first at upper case">
                    </label>
                    <label><fmt:message key="myData.newBirthDate"/><br>
                        <input id="date" type="date" onkeydown="return false" value="${sessionScope.user.birthDate}"
                               placeholder="Birthday" name="newBirthday" required
                               title="This field must consist only characters with first at upper case">
                    </label>
                    <label><fmt:message key="myData.oldPassword"/><br>
                        <input type="password" placeholder="Old password" name="currentPassword" required
                               pattern="(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                               title="Password must be at least 8 characters long with 1 or more digits">
                    </label>
                    <label><fmt:message key="myData.newPassword"/><br>
                            <input type="password" placeholder="New password" name="newPassword"
                                   pattern="(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"
                                   title="Password must be at least 8 characters long with 1 or more digits">
                    </label>
                    <input type="submit" id="confirmButton" class="confirmButtonStyle" value="<fmt:message key="myData.confirm"/>">
                </form>
                <c:if test="${not empty exception}">
                    <p class="exception" id="exception"><c:out value="${exception}"/></p>
                    <c:remove var="exception" scope="session"/>
                </c:if>
            </div>
            <div id="changeButton" class="changeButtonStyle" onclick="changeDataClick()"><span><fmt:message key="myData.changeData"/></span></div>
            <div id="undoButton" class="undoButtonStyle" onclick="undoButtonClick()"><span><fmt:message key="myData.undoChanges"/></span></div>
        </main>
        <script src="../script/MyData.js"></script>
    </body>
</html>
