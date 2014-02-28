package asepsis.bluej.domain;

import java.util.List;

import static com.google.common.base.Objects.toStringHelper;

public class Course {
    private String name;
    private List<Assignment> assignments;

    public Course(String name, List<Assignment> assignments) {
        checkArguments(name, assignments);

        this.name = name;
        this.assignments = assignments;
    }

    private void checkArguments(String name, List<Assignment> assignments) {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null");
        if (assignments == null)
            throw new IllegalArgumentException("Assignments cannot be null");
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return assignments.equals(course.assignments) && name.equals(course.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + assignments.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
            .add("name", name)
            .add("assignments", assignments)
            .toString();
    }
}
