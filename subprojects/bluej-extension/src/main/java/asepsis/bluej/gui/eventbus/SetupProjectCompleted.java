package asepsis.bluej.gui.eventbus;

public class SetupProjectCompleted {
    private final String courseName;
    private final String assignmentName;

    public SetupProjectCompleted(String courseName, String assignmentName) {
        this.courseName = courseName;
        this.assignmentName = assignmentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    @Override
    public String toString() {
        return "SetupProjectCompleted";
    }
}
