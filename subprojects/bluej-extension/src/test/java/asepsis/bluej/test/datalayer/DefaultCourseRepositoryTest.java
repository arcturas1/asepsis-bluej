package asepsis.bluej.test.datalayer;

import asepsis.bluej.datalayer.AsepsisAdapter;
import asepsis.bluej.datalayer.DefaultCourseRepository;
import asepsis.bluej.domain.Assignment;
import asepsis.bluej.domain.Course;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultCourseRepositoryTest {
    private DefaultCourseRepository repo;

    @Before
    public void setUp() throws Exception {
        AsepsisAdapter dal = mock(AsepsisAdapter.class);
        when(dal.getCourses()).thenReturn(dummyCourses());
        repo = new DefaultCourseRepository(dal);
    }

    @Test
    public void findAll_ReturnsAllCourses_FromDAL() {
        assertThat(repo.findAll(), is(dummyCourses()));
    }

    @Test
    public void find_ReturnsCourseFromDALW_WhenItExists() {
        assertThat(repo.find("SomeCourse2"), is(dummyCourse2()));
    }

    @Test
    public void find_ReturnsNull_WhenCourseDoesntExist() {
        assertThat(repo.find("SomeNonExistantCourse"), is(nullValue()));
    }

    private List<Course> dummyCourses() {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course("SomeCourse", new ArrayList<Assignment>()));
        courses.add(dummyCourse2());
        return courses;
    }

    private Course dummyCourse2() {
        return new Course("SomeCourse2", new ArrayList<Assignment>());
    }
}
