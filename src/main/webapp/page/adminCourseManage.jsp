<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="pth" uri="/WEB-INF/tld/pagination.tld" %>
<html>
    <head>
        <title>DireElective - Course management</title>
        <link rel="shortcut icon" href="../image/icon.png" type="image/png">
        <link rel="stylesheet" href="../style/AdminCourseManage.css" type="text/css">
        <script src="../script/StudentCourses.js"></script>
    </head>
    <body>
        <%@include file="../WEB-INF/pageFragment/menu.jspf"%>
        <c:if test="${sessionScope.user == null || sessionScope.user.userRole ne UserRole.ADMINISTRATOR}">
            <script>document.location.href="/page/main.jsp"</script>
        </c:if>
        <main>
            <header>
                <h1><fmt:message key="adminCourses.header"/></h1>
            </header>
            <div class="container">
                <div class="createCourse">
                    <button id="createCourse" onclick="createCourse()"><fmt:message key="adminCourses.createCourse"/></button>
                </div>
                <form id="courseForm" action="Controller" method="post">
                    <label>
                        <input id="command" hidden name="command">
                        <input hidden name="pageNumber" value="${pageNumber}">
                        <input id="courseID" hidden name="courseID">
                    </label>
                </form>
                <form id="courseFormGet" action="Controller" method="get">
                    <label>
                        <input id="commandGet" hidden name="command">
                        <input hidden name="pageNumber" value="${pageNumber}">
                        <input id="courseIDGet" hidden name="courseID">
                    </label>
                </form>
                <div>
                    <c:choose>
                        <c:when test="${courseList eq null || courseList.size() eq 0}">
                            <script>noCourses()</script>
                            <p class="emptyTableText"><fmt:message key="adminCourses.emptyTable"/></p>
                        </c:when>
                        <c:otherwise>
                            <table id="courseTable">
                                <thead>
                                    <tr>
                                        <th><fmt:message key="adminCourses.theme"/></th>
                                        <th><fmt:message key="adminCourses.name"/></th>
                                        <th><fmt:message key="adminCourses.startDate"/></th>
                                        <th><fmt:message key="adminCourses.endDate"/></th>
                                        <th><fmt:message key="adminCourses.teacher"/></th>
                                        <th><fmt:message key="adminCourses.term"/></th>
                                        <th><fmt:message key="adminCourses.studentCount"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="course" items="${courseList}" varStatus="i">
                                        <tr>
                                            <td>${course.theme}</td>
                                            <td>${course.name}</td>
                                            <td>${course.startDate}</td>
                                            <td>${course.endDate}</td>
                                            <td>${teacherData.get(i.index)}</td>
                                            <td>${course.term}</td>
                                            <td>${course.studentCount}</td>
                                            <td class="tableButton">
                                                <button id="${course.id}" class="updateButton defaultButton" onclick="updateCourse(this)"></button>
                                            </td>
                                            <td class="tableButton">
                                                <button id="${course.id}" class="removeButton defaultButton" onclick="removeCourse(this)"></button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:if test="${courseList.size() ne 0}">
                    <footer class="pagination">
                        <pth:pagination pageNumber="${pageNumber}" command="getAdminCourses" maxPage="${maxPage}"/>
                    </footer>
                </c:if>
                <c:if test="${not empty exception}">
                    <script>blockContent()</script>
                    <p class="exception"><c:out value="${exception}"/></p>
                    <c:remove var="exception" scope="session"/>
                </c:if>
            </div>
        </main>
        <script src="../script/AdminUser.js"></script>
    </body>
</html>
