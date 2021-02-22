<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<html>
    <head>
        <title>DireElective - Available courses</title>
        <link rel="shortcut icon" href="../image/icon.png" type="image/png">
        <link rel="stylesheet" href="../style/AvailableCourses.css" type="text/css">
    </head>
    <body>
        <%@include file="../WEB-INF/pageFragment/menu.jspf"%>
        <c:if test="${sessionScope.user != null && sessionScope.user.userRole ne UserRole.STUDENT}">
            <script>document.location.href="/page/main.jsp"</script>
        </c:if>
        <main>
            <div class="container">
                <header>
                    <h1 id="firstElem" class="h1Style"><fmt:message key="availableCourses.header"/></h1>
                </header>
                <form id="courseForm" action="Controller" method="get">
                    <label>
                        <input hidden name="command" value="getAvailableCourses">
                        <input hidden name="pageNumber" value="${pageNumber}">
                        <input hidden id="courseName" name="courseName">
                        <input id="sortBy" hidden name="sortBy" value="${sortBy}">
                    </label>
                </form>
                <form id="StudentEnrollOnCourse" action="Controller" method="post">
                    <label>
                        <input hidden name="command" value="enrollStudentOnCourse">
                        <input id="courseID" hidden name="courseID">
                    </label>
                </form>
                <div class="selectCourses" id="selectTeachers">
                    <p><fmt:message key="availableCourses.courseName"/></p>
                    <label>
                        <select id="select" onchange="changeCourse()">
                            <c:choose>
                                <c:when test="${courseName != null and courseName ne 'All courses' and courseName ne '%'}">
                                    <option>${courseName}</option>
                                </c:when>
                            </c:choose>
                            <option><fmt:message key="availableCourses.selectDefault"/></option>
                            <c:forEach items="${coursesSelectName}" var="name" varStatus="i">
                                <c:if test="${name ne courseName}"><option>${name}</option></c:if>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <c:choose>
                    <c:when test="${courseList.size() eq 0}">
                        <script>document.getElementById("selectTeachers").style.display = 'none';</script>
                        <p class="emptyTableText"><fmt:message key="availableCourses.emptyTable"/></p>
                    </c:when>
                    <c:otherwise>
                        <tag:fillCourseTable courseList="${courseList}" teacherData="${teacherData}" courseType="available"
                                             marks="${marks}"/>
                    </c:otherwise>
                </c:choose>
                <c:if test="${not empty exception}">
                    <p class="exception" id="exception"><c:out value="${exception}"/></p>
                    <c:remove var="exception" scope="session"/>
                </c:if>
                <footer class="pagination">
                    <c:if test="${pageNumber != 1}">
                        <a href="Controller?command=getAvailableCourses&pageNumber=${pageNumber - 1}&courseType=available&sortBy=${sortBy}">Previous</a>
                    </c:if>
                    <c:if test="${pageNumber != maxPage && courseList.size() != 0}">
                        <a style="margin-left: 1%" href="Controller?command=getAvailableCourses&pageNumber=${pageNumber+1}&courseType=available&sortBy=${sortBy}">Next</a>
                    </c:if>
                </footer>
            </div>
        </main>
        <script src="../script/Course.js"></script>
    </body>
</html>
