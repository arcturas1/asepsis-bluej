package asepsis.bluej.domain;

import asepsis.bluej.domain.Course;

import java.util.List;

public interface CourseRepository {
    List<Course> findAll();
    Course find(String name);
}
