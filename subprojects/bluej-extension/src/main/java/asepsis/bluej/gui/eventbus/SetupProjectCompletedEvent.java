package asepsis.bluej.gui.eventbus;

public class SetupProjectCompletedEvent {
    private final String courseName;
    private final String assignmentName;

    public SetupProjectCompletedEvent(String courseName, String assignmentName) {
        this.courseName = courseName;
        this.assignmentName = assignmentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}
