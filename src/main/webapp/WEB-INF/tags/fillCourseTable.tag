<%@ attribute name="courseList" type="java.util.ArrayList"  required="true" %>
<%@ attribute name="teacherData" type="java.util.ArrayList" required="true" %>
<%@ attribute name="courseType" type="java.lang.String" required="true" %>
<%@ attribute name="marks" type="java.util.ArrayList" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tag description="Custom tag for iterating the course list" pageEncoding="UTF-8" %>

<table id="courseTable">
    <thead>
        <tr>
            <th id="nameHeader" onclick="headerClick(this)">Name</th>
            <th id="themeHeader">Theme</th>
            <th id="startDateHeader">Start date</th>
            <th id="endDateHeader">End date</th>
            <th id="teacherHeader">Teacher</th>
            <th id="termHeader" onclick="headerClick(this)">Term</th>
            <th id="studentCountHeader" onclick="headerClick(this)">Student count</th>
            <c:if test="${courseType eq 'finished'}">
                <th>Mark</th>
            </c:if>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="course" items="${courseList}" varStatus="i">
            <tr>
                <td>${course.name}</td>
                <td>${course.theme}</td>
                <td>${course.startDate}</td>
                <td>${course.endDate}</td>
                <td>${teacherData[i.index]}</td>
                <td>${course.term} days</td>
                <td>${course.studentCount}</td>
                <c:if test="${courseType eq 'finished'}">
                    <td>
                        ${marks[i.index]}
                    </td>
                </c:if>
                <c:if test="${courseType eq 'available'}">
                    <td class="transparentTD"><button  id="${course.id}" onclick="courseRegister(this)">Записаться на курс</button></td>
                </c:if>
            </tr>
        </c:forEach>
    </tbody>
</table>
