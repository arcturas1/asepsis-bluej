package asepsis.bluej.data;

import asepsis.bluej.domain.Course;
import asepsis.bluej.domain.CourseRepository;

import java.util.List;

public class DefaultCourseRepository implements CourseRepository {
    private AsepsisAdapter dal;

    public DefaultCourseRepository(AsepsisAdapter dal) {
        this.dal = dal;
    }

    @Override
    public List<Course> findAll() {
        return dal.getCourses();
    }

    @Override
    public Course find(String name) {
        List<Course> courses = dal.getCourses();
        for (Course c : courses) {
            if (c.getName().equals(name))
                return c;
        }

        return null;
    }
}
