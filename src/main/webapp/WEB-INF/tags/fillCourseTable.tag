<%@ attribute name="courseList" type="java.util.ArrayList"  required="true" %>
<%@ attribute name="teacherData" type="java.util.ArrayList" required="true" %>
<%@ attribute name="courseType" type="java.lang.String" required="true" %>
<%@ attribute name="marks" type="java.util.ArrayList" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tag description="Custom tag for iterating the course list" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization/localization"/>

<table id="courseTable">
    <thead>
        <tr>
            <th id="nameHeader" onclick="headerClick(this)"><fmt:message key="tag.name"/></th>
            <th id="themeHeader"><fmt:message key="tag.theme"/></th>
            <th id="startDateHeader"><fmt:message key="tag.startDate"/></th>
            <th id="endDateHeader"><fmt:message key="tag.endDate"/></th>
            <th id="teacherHeader"><fmt:message key="tag.teacher"/></th>
            <th id="termHeader" onclick="headerClick(this)"><fmt:message key="tag.term"/></th>
            <th id="studentCountHeader" onclick="headerClick(this)"><fmt:message key="tag.studentCount"/></th>
            <c:if test="${courseType eq 'finished'}">
                <th><fmt:message key="tag.mark"/></th>
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
                    <td class="transparentTD"><button  id="${course.id}" onclick="courseRegister(this)"><fmt:message key="tag.enroll"/></button></td>
                </c:if>
            </tr>
        </c:forEach>
    </tbody>
</table>
