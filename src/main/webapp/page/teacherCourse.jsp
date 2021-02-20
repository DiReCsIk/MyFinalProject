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
                    My course
                </h1>
            </header>
            <div class="container">
                <header>
                    <h2 class="h2Style">
                        Здравствуйте, ${user.name}, на этой странице вы можете увидить список студентов на вашем курсе и всю
                        необходимую Вам информацию о них. Здесь же - вы можете выставить их успеваемость.
                    </h2>
                    <h3 class="h3Style">
                        Чтобы выставить успеваемость - нажмите по значению в колонке оценки, впишите необходимое число (0-100).
                    </h3>
                </header>
                <form id="studentSetMark" action="Controller" method="post">
                    <label>
                        <input hidden name="command" value="setStudentMarkCommand">
                        <input id="studentID" hidden name="studentID">
                        <input id="mark" hidden name="newMark">
                    </label>
                </form>
                <div class="courseTable">
                    <c:choose>
                        <c:when test="${studentsName.size() eq 0}">
                            <p class="emptyTableText">There are no students on your course :c</p>
                        </c:when>
                        <c:otherwise>
                            <table id="studentTable">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Last name</th>
                                        <th>Birthday</th>
                                        <th>Mark</th>
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
