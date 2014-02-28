package asepsis.bluej.repository;

import asepsis.bluej.domain.Course;

import java.util.List;

public interface CourseRepository {
    List<Course> findAll();
    Course find(String name);
}
