<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="pth" uri="/WEB-INF/tld/pagination.tld" %>
<html>
    <head>
        <title>DireElective - User management</title>
        <link rel="shortcut icon" href="../image/icon.png" type="image/png">
        <link rel="stylesheet" href="../style/AdminUserManage.css" type="text/css">
        <script src="../script/AdminUser.js"></script>
    </head>
    <body>
        <%@include file="../WEB-INF/pageFragment/menu.jspf"%>
        <c:if test="${sessionScope.user == null || sessionScope.user.userRole ne UserRole.ADMINISTRATOR}">
            <script>document.location.href="/page/main.jsp"</script>
        </c:if>
        <main>
            <header>
                <h1>User manage</h1>
            </header>
            <div class="container">
                <div class="userList" id="userList">
                    <a href="Controller?command=getStudentsInfo&pageNumber=1">STUDENTS</a>
                    <a href="Controller?command=getTeachersInfo&pageNumber=1">TEACHERS</a>
                    <a href="Controller?command=getAdministratorsInfo&pageNumber=1">ADMINISTRATORS</a>
                </div>
                <form id="userForm" action="Controller" method="post">
                    <label>
                        <input id="command" hidden name="command">
                        <input hidden name="pageNumber" value="${pageNumber}">
                        <input id="userRaise" hidden name="userRaise">
                        <input id="userID" hidden name="userID">
                        <input id="banStatus" hidden name="banStatus">
                        <input id="userDecline" hidden name="userDecline">
                    </label>
                </form>
                <div>
                    <c:choose>
                        <c:when test="${dataList.size() eq 0}">
                            <script>noUsers()</script>
                            <p class="emptyTableText">There are no users in this category :c</p>
                        </c:when>
                        <c:otherwise>
                            <table id="userTable">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Surname</th>
                                        <th>Birthdate</th>
                                        <c:choose>
                                            <c:when test="${userType eq 'student'}">
                                                <th>Ban status</th>
                                                <th>Raise</th>
                                            </c:when>
                                            <c:when test="${userType eq 'teacher'}">
                                                <th>Decline</th>
                                                <th>Raise</th>
                                                <th>Course status</th>
                                            </c:when>
                                        </c:choose>
                                        <th>E-mail</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="userDataList" items="${dataList}" varStatus="i">
                                        <tr>
                                            <td>${userDataList.name}</td>
                                            <td>${userDataList.surName}</td>
                                            <td>${userDataList.birthDate}</td>
                                            <c:choose>
                                                <c:when test="${userType eq 'student'}">
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${userDataList.banStatus eq true}">
                                                                <button id="${userData.get("userID").get(i.index)}" onclick="unbanUser(this)">Заблокирован</button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button id="${userData.get("userID").get(i.index)}" onclick="banUser(this)">Нет</button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <button id="${userData.get("userID").get(i.index)}" onclick="upToTeacher(this)">To teacher</button>
                                                    </td>
                                                </c:when>
                                                <c:when test="${userType eq 'teacher'}">
                                                    <td>
                                                        <button id="${userData.get("userID").get(i.index)}" onclick="downToStudent(this)">To student</button>
                                                    </td>
                                                    <td>
                                                        <button id="${userData.get("userID").get(i.index)}" onclick="upToAdmin(this)">To administrator</button>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${courseStatus.get(i.index) ne 0}">In progress</c:when>
                                                            <c:otherwise>Free</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </c:when>
                                            </c:choose>
                                            <td>${userData.get("userEmail").get(i.index)}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:if test="${userDataList.size() ne 0}">
                    <footer class="pagination">
                        <c:choose>
                            <c:when test="${userType eq 'teacher'}">
                                <pth:pagination pageNumber="${pageNumber}" command="getTeachersInfo" maxPage="${maxPage}"/>
                            </c:when>
                            <c:when test="${userType eq 'administrator'}">
                                <pth:pagination pageNumber="${pageNumber}" command="getAdministratorsInfo" maxPage="${maxPage}"/>
                            </c:when>
                            <c:otherwise>
                                <pth:pagination pageNumber="${pageNumber}" command="getStudentsInfo" maxPage="${maxPage}"/>
                            </c:otherwise>
                        </c:choose>
                    </footer>
                </c:if>
                <c:if test="${not empty exception}">
                    <script>blockContent()</script>
                    <p class="exception"><c:out value="${exception}"/></p>
                    <c:remove var="exception" scope="session"/>
                </c:if>
            </div>
        </main>
    </body>
</html>
