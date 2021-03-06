<%@ page contentType="text/html; charset=UTF-8" %>
<html>
    <head>
        <title>DireElective - Create course</title>
        <link rel="shortcut icon" href="../image/icon.png" type="image/png">
        <link rel="stylesheet" href="../style/CreateCourse.css" type="text/css">
        <script src="../script/Course.js"></script>
    </head>
    <body>
    <%@include file="../WEB-INF/pageFragment/menu.jspf"%>
    <c:if test="${sessionScope.user == null || sessionScope.user.userRole ne UserRole.ADMINISTRATOR}">
        <script>document.location.href="/page/main.jsp"</script>
    </c:if>
    <div class="container">
        <header>
            <h1><fmt:message key="createCourse.header"/></h1>
        </header>
        <form id="course" method="post" action="${pageContext.request.contextPath}/Controller?command=createCourse">
            <label><fmt:message key="createCourse.courseName"/>
                <input type="text" placeholder="Course name" name="name" required/>
                <br></label>
            <label><fmt:message key="createCourse.theme"/>
                <input type="text" placeholder="Course theme" name="theme" required/>
                <br></label>
            <label><fmt:message key="createCourse.courseStartDate"/>
                <input id="startDate" onchange="dataChange()" type="date" placeholder="Course start date" name="startDate" required/>
                <br></label>
            <label><fmt:message key="createCourse.courseEndDate"/>
                <input id="endDate" type="date" placeholder="Course end date" name="endDate" required/>
                <br></label>
            <label><fmt:message key="createCourse.teacher"/>
                <input id="teacherID" hidden name="teacherID">
                <input id="courseID" hidden name="courseID" value="${courseID}">
                <select id="select" onchange="changeID()">
                    <c:forEach items="${teachers}" var="teacher" varStatus="i">
                        <option id="${teachersID.get(i.index)}">${teacher}</option>
                    </c:forEach>
                </select>
                <br>
                <button id="updateButton" type="submit" class="updateButton"><fmt:message key="createCourse.create"/></button>
                <button id="backButton" type="button" class="updateButton" onclick="history.go(-1)"><fmt:message key="createCourse.back"/></button>
            </label>
        </form>
        <c:if test="${not empty exception}">
            <script>blockContent()</script>
            <p class="courseException"><c:out value="${exception}"/></p>
            <c:remove var="exception" scope="session"/>
        </c:if>
    </div>
    <script src="../script/DataCourse.js"></script>
    <script src="../script/CreateCourse.js"></script>
    </body>
</html>
