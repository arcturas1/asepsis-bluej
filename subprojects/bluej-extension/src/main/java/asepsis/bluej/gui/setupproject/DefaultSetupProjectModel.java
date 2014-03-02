package asepsis.bluej.gui.setupproject;

import asepsis.bluej.domain.Assignment;
import asepsis.bluej.domain.Course;
import asepsis.bluej.gui.event.EventDispatcher;
import asepsis.bluej.gui.event.EventListener;
import asepsis.bluej.gui.eventbus.SetupProjectCanceledEvent;
import asepsis.bluej.gui.eventbus.SetupProjectCompletedEvent;
import asepsis.bluej.gui.eventbus.SetupProjectRequestEvent;
import asepsis.bluej.repository.CourseRepository;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class DefaultSetupProjectModel implements SetupProjectModel {
    private EventDispatcher closeDialogRequest = new EventDispatcher();
    private EventDispatcher showDialogRequest = new EventDispatcher();
    private EventBus eventBus;
    private CourseRepository repo;

    public DefaultSetupProjectModel(EventBus eventBus, CourseRepository repo) {
        this.eventBus = eventBus;
        this.repo = repo;
    }

    @Override
    public String[] getCourseNames() {
        List<String> names = new ArrayList<String>();
        for (Course c : repo.findAll())
            names.add(c.getName());

        return names.toArray(new String[names.size()]);
    }

    @Override
    public String[] getAssignmentNamesFor(String course) {
        List<String> names = new ArrayList<String>();
        for (Assignment a :  repo.find(course).getAssignments())
            names.add(a.getName());

        return names.toArray(new String[names.size()]);
    }

    @Override
    public void acceptDialog(String course, String assignment) {
        closeDialogRequest.tellListeners();
        eventBus.post(new SetupProjectCompletedEvent(course, assignment));
    }

    @Override
    public void cancelDialog() {
        closeDialogRequest.tellListeners();
        eventBus.post(new SetupProjectCanceledEvent());
    }

    @Override
    public void whenShowDialogRequested(EventListener listener) {
        showDialogRequest.heardBy(listener);
    }

    @Override
    public void whenCloseDialogRequested(EventListener listener) {
        closeDialogRequest.heardBy(listener);
    }

    @Subscribe
    public void onSetupProjectRequest(SetupProjectRequestEvent ignored) {
        showDialogRequest.tellListeners();
    }
}