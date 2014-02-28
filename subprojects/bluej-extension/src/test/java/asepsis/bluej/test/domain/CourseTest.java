package asepsis.bluej.test.domain;

import asepsis.bluej.domain.Assignment;
import asepsis.bluej.domain.Course;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CourseTest {
    private Course course;

    @Before
    public void setUp() throws Exception {
        course = new Course("SomeName", dummyAssignments());
    }

    @Test
    public void getName_ReturnsName_WhenSetInConstructor() {
        assertThat(course.getName(), is("SomeName"));
    }

    @Test
    public void getAssignments_ReturnsAssignments_WhenSetInConstructor() {
        assertThat(course.getAssignments(), is(dummyAssignments()));
    }

    @Test
    public void toString_ReturnsDescriptiveString_WhenCalled() {
        assertThat(course.toString(), is("Course{name=SomeName, assignments=[Assignment{name=A1}]}"));
    }

    @Test
    public void equals_ReturnsTrue_IfCoursesHasSameNameAndAssignments() {
        Course course2 = new Course("SomeName", dummyAssignments());

        assertThat(course, is(equalTo(course2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_ThrowsIllegalArgumentException_IfNameIsNull() {
        new Course(null, dummyAssignments());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_ThrowsIllegalArgumentException_IfAssignmentsIsNull() {
        new Course("SomeName", null);
    }

    private List<Assignment> dummyAssignments() {
        List<Assignment> assignments = new ArrayList<Assignment>();
        assignments.add(new Assignment("A1"));
        return assignments;
    }
}