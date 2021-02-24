<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>DireElective - About us</title>
    <link rel="shortcut icon" href="../image/icon.png" type="image/png">
    <link rel="stylesheet" href="../style/AboutUs.css" type="text/css">
</head>
<body>
<%@include file="../WEB-INF/pageFragment/menu.jspf" %>
<main>
    <header>
        <h1 class="header"><fmt:message key="aboutUs.header"/></h1>
    </header>
    <div class="proud">
        <p><fmt:message key="aboutUs.firstParagraph"/></p>
        <p><fmt:message key="aboutUs.secondParagraph"/></p>
    </div>
    <div class="img">
        <img src="../image/cup.png" alt="cup">
    </div>
        <table>
            <thead>
            <tr>
                <th>#</th>
                <th><fmt:message key="aboutUs.name"/></th>
                <th><fmt:message key="aboutUs.surname"/></th>
                <th><fmt:message key="aboutUs.birthdate"/></th>
                <th><fmt:message key="aboutUs.studentCount"/></th>
                <th><fmt:message key="aboutUs.courseCount"/></th>
            </tr>
            </thead>
            <tbody>
                <c:forEach varStatus="i" begin="0" end="${teachersMap.get('id').size() - 1}">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${teachersMap.get('name').get(i.index)}</td>
                        <td>${teachersMap.get('surName').get(i.index)}</td>
                        <td>${teachersMap.get('birthDay').get(i.index)}</td>
                        <td>${teachersMap.get('studentCount').get(i.index)}</td>
                        <td>${teachersCourseCount.get(i.index)}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </main>
</body>
</html>
