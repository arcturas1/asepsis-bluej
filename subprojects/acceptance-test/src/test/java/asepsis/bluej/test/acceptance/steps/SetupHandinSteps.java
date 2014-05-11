package asepsis.bluej.test.acceptance.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.netbeans.jemmy.operators.*;
import org.netbeans.jemmy.util.NameComponentChooser;

import static asepsis.bluej.test.acceptance.Init.bluejFrame;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class SetupHandinSteps {
    private JDialogOperator setupDlg;

    @When("^I ask ASEPSiS to setup the project with a course and an assignment$")
    public void I_ask_ASEPSiS_to_setup_the_project_with_a_course_and_an_assignment() throws Throwable {
        Thread.sleep(1000);
        new JButtonOperator(bluejFrame(), new NameComponentChooser("asepsis.sidebar.setupBtn")).pushNoBlock();
        setupDlg = new JDialogOperator(bluejFrame(), "Setup handin");
        new JComboBoxOperator(setupDlg, new NameComponentChooser("asepsis.setup.dialog.courseList")).selectItem(0);
        new JComboBoxOperator(setupDlg, new NameComponentChooser("asepsis.setup.dialog.assignmentList")).waitItemSelected("Course1-Assignment1");
    }

    @When("^I accept the setup$")
    public void I_accept_the_setup() throws Throwable {
        new JButtonOperator(setupDlg, new NameComponentChooser("asepsis.setup.dialog.okBtn")).push();
        assertDialogClosed("Setup handin");
    }

    @When("^I cancel the setup$")
    public void I_cancel_the_setup() throws Throwable {
        new JButtonOperator(setupDlg, new NameComponentChooser("asepsis.setup.dialog.cancelBtn")).push();
        assertDialogClosed("Setup handin");
    }

    @When("^I close the project$")
    public void I_close_the_project() throws Throwable {
        new JMenuBarOperator(bluejFrame()).pushMenu("Project|Close");
    }

    @When("^I close the project window$")
    public void I_close_the_project_window() throws Throwable {
        bluejFrame().close();
    }

    @Then("^I should not be able to setup the project$")
    public void I_should_not_be_able_to_setup_the_project() throws Throwable {
        JButtonOperator setupBtn = new JButtonOperator(bluejFrame(), new NameComponentChooser("asepsis.sidebar.setupBtn"));
        assertThat("Setup button should be disabled", setupBtn.isEnabled(), is(false));
    }

    private void assertDialogClosed(String name) {
        assertNull( "Dialog '" + name + "' should have been closed", JDialogOperator.findJDialog(name, false, false) );
    }
}
