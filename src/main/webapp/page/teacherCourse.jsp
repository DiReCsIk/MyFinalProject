<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="pth" uri="/WEB-INF/tld/pagination.tld" %>

<html>
    <head>
        <title>DireElective - My course</title>
        <link rel="shortcut icon" href="../image/icon.png" type="image/png">
        <link rel="stylesheet" href="../style/TeacherCourse.css" type="text/css">
    </head>
    <body>
        <%@include file="../WEB-INF/pageFragment/menu.jspf" %>
        <c:if test="${sessionScope.user == null || sessionScope.user.userRole ne UserRole.TEACHER}">
            <script>document.location.href="/page/main.jsp"</script>
        </c:if>
        <main>
            <header>
                <h1 class="h1Style">
                    <fmt:message key="teacherCourse.header"/>
                </h1>
            </header>
            <div class="container">
                <header>
                    <h2 class="h2Style">
                        <fmt:message key="teacherCourse.welcome"/> ${user.name}, <fmt:message key="teacherCourse.secondHeader"/>
                    </h2>
                    <h3 class="h3Style">
                        <fmt:message key="teacherCourse.thirdHeader"/>
                    </h3>
                </header>
                <form id="studentSetMark" action="Controller" method="post">
                    <label>
                        <input hidden name="command" value="setStudentMarkCommand">
                        <input hidden name="pageNumber" value="${pageNumber}">
                        <input id="studentID" hidden name="studentID">
                        <input id="mark" hidden name="newMark">
                    </label>
                </form>
                <div class="courseTable">
                    <c:choose>
                        <c:when test="${studentsName.size() eq 0}">
                            <p class="emptyTableText"><fmt:message key="teacherCourse.emptyTable"/></p>
                        </c:when>
                        <c:otherwise>
                            <table id="studentTable">
                                <thead>
                                    <tr>
                                        <th><fmt:message key="teacherCourse.name"/></th>
                                        <th><fmt:message key="teacherCourse.surName"/></th>
                                        <th><fmt:message key="teacherCourse.birthDate"/></th>
                                        <th><fmt:message key="teacherCourse.mark"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach varStatus="i" begin="0" end="${studentsName.size() - 1}">
                                        <tr>
                                            <td>${studentsName.get(i.index)}</td>
                                            <td>${studentsSurname.get(i.index)}</td>
                                            <td>${studentsBirthDay.get(i.index)}</td>
                                            <td id="${studentsID.get(i.index)}" onclick="tdOnClick(this)" onblur="tdOnBlur(this)" contenteditable="true">${studentsMark.get(i.index)}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:if test="${studentsName.size() ne 0}">
                    <footer class="pagination">
                        <pth:pagination pageNumber="${pageNumber}" command="getTeacherCourseInfo" maxPage="${maxPage}"/>
                    </footer>
                </c:if>
            </div>
        </main>
        <script src="../script/TeacherCourse.js"></script>
    </body>
</html>
