package asepsis.bluej.datalayer;

import asepsis.bluej.domain.Assignment;
import asepsis.bluej.domain.Course;

import java.util.ArrayList;
import java.util.List;

public class FakeAsepsisAdapter implements AsepsisAdapter {
    private List<Course> courses;

    public FakeAsepsisAdapter() {
        courses = makeCourses(4, 7);
    }

    @Override
    public List<Course> getCourses() {
        return courses;
    }

    private List<Course> makeCourses(int courseCount, int assignmentCount) {
        List<Course> courses = new ArrayList<Course>();
        for (int i = 1; i <= courseCount; i++) {
            String courseName = "Course" + i;
            courses.add(new Course(courseName, makeAssignments(courseName, assignmentCount)));
        }
        return courses;
    }

    private List<Assignment> makeAssignments(String courseName, int count) {
        List<Assignment> assignments = new ArrayList<Assignment>();
        for (int i = 1; i <= count; i++)
            assignments.add(new Assignment(courseName + "-Assignment" + i));
        return assignments;
    }
}
