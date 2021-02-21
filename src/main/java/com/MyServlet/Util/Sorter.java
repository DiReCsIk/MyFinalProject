package com.MyServlet.Util;

import com.MyServlet.Entity.Course;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sorter {
    private static final Comparator<Course> SORT_BY_NAME_DESC = Comparator.comparing(Course::getName);
    private static final Comparator<Course> SORT_BY_NAME_ASC = Comparator.comparing(Course::getName).reversed();
    private static final Comparator<Course> SORT_BY_TERM_DESC = Comparator.comparing(Course::getTerm);
    private static final Comparator<Course> SORT_BY_TERM_ASC = Comparator.comparing(Course::getTerm).reversed();
    private static final Comparator<Course> SORT_BY_STUDENT_COUNT_DESC = Comparator.comparing(Course::getStudentCount);
    private static final Comparator<Course> SORT_BY_STUDENT_COUNT_ASC = Comparator.comparing(Course::getStudentCount).reversed();

    public static List<Course> sortCourseList(ArrayList<Course> courses, String sortBy) {
        switch (sortBy) {
            case "Name":
            case "Имя":
                return sortCoursesByNameDesc(courses);
            case "NameAsc":
            case "ИмяAsc":
                return sortCoursesByNameAsc(courses);
            case "Term":
            case "Длительность":
                return sortCoursesByTermDesc(courses);
            case "TermAsc":
            case "ДлительностьAsc":
                return sortCoursesByTermAsc(courses);
            case "Student count":
            case "Количество студентов":
                return sortCoursesByStudentCountDesc(courses);
            case "Student countAsc":
            case "Количество студентовAsc":
                return sortCoursesByStudentCountAsc(courses);
        }
        return courses;
    }

    private static List<Course> sortCoursesBy(ArrayList<Course> courses, Comparator<Course> comparator) {
        return courses.stream().sorted(comparator).collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Course> sortCoursesByNameDesc(ArrayList<Course> courses) {
        return sortCoursesBy(courses, SORT_BY_NAME_DESC);
    }

    public static List<Course> sortCoursesByNameAsc(ArrayList<Course> courses) {
        return sortCoursesBy(courses, SORT_BY_NAME_ASC);
    }

    public static List<Course> sortCoursesByTermDesc(ArrayList<Course> courses) {
        return sortCoursesBy(courses, SORT_BY_TERM_DESC);
    }

    public static List<Course> sortCoursesByTermAsc(ArrayList<Course> courses) {
        return sortCoursesBy(courses, SORT_BY_TERM_ASC);
    }

    public static List<Course> sortCoursesByStudentCountDesc(ArrayList<Course> courses) {
        return sortCoursesBy(courses, SORT_BY_STUDENT_COUNT_DESC);
    }

    public static List<Course> sortCoursesByStudentCountAsc(ArrayList<Course> courses) {
        return sortCoursesBy(courses, SORT_BY_STUDENT_COUNT_ASC);
    }
}
