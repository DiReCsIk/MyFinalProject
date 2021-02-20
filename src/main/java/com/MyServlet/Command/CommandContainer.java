package com.MyServlet.Command;
//TODO toLong all and toString

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands;

    static {
        commands = new HashMap<>();
        commands.put("signIn", new SignInCommand());
        commands.put("signUp", new SignUpCommand());
        commands.put("signOut", new SignOutCommand());
        commands.put("getStudentCourses", new GetCoursesCommand());
        commands.put("getUserInfo", new GetUserInfoFromCookieCommand());
        commands.put("updateUserInfo", new UpdateUserInfoCommand());
        commands.put("getTeacherCourseInfo", new GetTeacherCourseCommand());
        commands.put("enrollStudentOnCourse", new EnrollStudentOnCourseCommand());
        commands.put("getStudentsInfo", new GetStudentsInfoCommand());
        commands.put("getAdministratorsInfo", new GetAdministratorsInfoCommand());
        commands.put("getTeachersInfo", new GetTeachersInfoCommand());
        commands.put("initAdminUserManage", new InitAdminUserCommand());
        commands.put("initAdminCourseManage", new InitAdminCourseCommand());
        commands.put("setBanStatus", new SetBanStatusCommand());
        commands.put("riseUserPosition", new RiseUserPositionCommand());
        commands.put("declineUserPosition", new DeclineUserPositionCommand());
        commands.put("getAdminCourses", new GetAdminCoursesCommand());
        commands.put("adminUpdateCourse", new AdminUpdateCourseCommand());
        commands.put("adminCreateCourse", new AdminCreateCourseCommand());
        commands.put("adminRemoveCourse", new AdminRemoveCourseCommand());
        commands.put("updateCourseInfo", new UpdateCourseInfoCommand());
        commands.put("createCourse", new CreateCourseCommand());
        commands.put("getTeacherFinishedCourse", new GetTeacherFinishedCoursesCommand());
        commands.put("initStudentCourses",new InitStudentCoursesCommand());
        commands.put("initAvailableCourses", new InitAvailableCoursesCommand());
        commands.put("getAvailableCourses", new GetAvailableCoursesCommand());
        commands.put("initTeacherCourse", new InitTeacherCourseCommand());
        commands.put("setStudentMarkCommand", new SetStudentMarkCommand());
    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
