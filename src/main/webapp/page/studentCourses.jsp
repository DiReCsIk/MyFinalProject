<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<html>
    <head>
        <title>DireElective - My courses</title>
        <link rel="stylesheet" href="../style/StudentCourses.css">
        <link rel="shortcut icon" href="../image/icon.png" type="image/png">
        <script src="../script/StudentCourses.js"></script>
    </head>
    <body>
        <%@include file="../WEB-INF/pageFragment/menu.jspf"%>
        <c:if test="${sessionScope.user == null || sessionScope.user.userRole ne UserRole.STUDENT}">
            <script>document.location.href="/page/main.jsp"</script>
        </c:if>
        <main>
            <header>
                <h1 class="h1Style">
                    <fmt:message key="studentCourses.header"/>
                </h1>
            </header>
            <form id="courseForm" action="Controller" method="get">
                <label>
                    <input id="command" hidden name="command" value="getStudentCourses">
                    <input hidden name="pageNumber" value="${pageNumber}">
                    <input hidden name="courseType" value="${courseType}">
                    <input hidden name="courseName" id="courseName">
                    <input id="sortBy" hidden name="sortBy">
                </label>
            </form>
            <form id="teacherForm" action="Controller" method="get">
                <label>
                    <input id="teacherCommand" hidden name="command" value="getTeacherFinishedCourse">
                    <input hidden name="pageNumber" value="1">
                    <input hidden name="courseType" value="finished">
                    <input hidden id="teacherID" name="teacherID">
                    <input hidden name="teacherNameAndSurname" id="teacherNameAndSurname">
                    <input hidden name="sortBy" value="${sortBy}">
                </label>
            </form>
            <div class="container">
                <div class="courseList">
                    <a href="Controller?command=getStudentCourses&pageNumber=1&courseType=notStarted&sortBy=${sortBy}"><fmt:message key="studentCourses.notStarted"/></a>
                    <a href="Controller?command=getStudentCourses&pageNumber=1&courseType=inProgress&sortBy=${sortBy}"><fmt:message key="studentCourses.inProgress"/></a>
                    <a href="Controller?command=getStudentCourses&pageNumber=1&courseType=finished&sortBy=${sortBy}"><fmt:message key="studentCourses.finished"/></a>
                </div>
                <div class="selectTeachers" id="selectTeachers">
                    <c:if test="${courseType eq 'finished'}">
                        <p><fmt:message key="studentCourses.paragraph"/></p>
                        <label>
                            <select id="select" onchange="changeTeacher()">
                                <c:choose>
                                    <c:when test="${teacherNameAndSurname != null and teacherNameAndSurname ne 'All courses'
                                            and teacherNameAndSurname ne '%'}">
                                        <option>${teacherNameAndSurname}</option>
                                    </c:when>
                                </c:choose>
                                <option><fmt:message key="studentCourses.selectDefault"/></option>
                                <c:forEach items="${teachers}" var="teacher" varStatus="i">
                                    <c:if test="${teacher ne teacherNameAndSurname}">
                                        <option id="${teachersID.get(i.index)}">${teacher}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </label>
                    </c:if>
                </div>
                <c:choose>
                    <c:when test="${courseList.size() eq 0}">
                        <script>noCourses()</script>
                        <p class="emptyTableText"><fmt:message key="studentCourses.emptyTable"/></p>
                    </c:when>
                    <c:otherwise>
                        <tag:fillCourseTable courseList="${courseList}" teacherData="${teacherData}"
                                             courseType="${courseType}" marks="${marks}"/>
                    </c:otherwise>
                </c:choose>
                <footer class="pagination">
                    <c:if test="${pageNumber != 1}">
                        <a href="Controller?command=getStudentCourses&pageNumber=${pageNumber - 1}&courseType=${courseType}&sortBy=${sortBy}">Previous</a>
                    </c:if>
                    <c:if test="${pageNumber != maxPage && courseList.size() != 0}">
                        <a style="margin-left: 1%" href="Controller?command=getStudentCourses&pageNumber=${pageNumber+1}&courseType=${courseType}&sortBy=${sortBy}">Next</a>
                    </c:if>
                </footer>
            </div>
        </main>
    <script src="../script/Course.js"></script>
    </body>
</html>
