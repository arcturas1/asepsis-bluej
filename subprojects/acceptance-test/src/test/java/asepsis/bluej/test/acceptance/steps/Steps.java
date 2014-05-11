package asepsis.bluej.test.acceptance.steps;

import asepsis.bluej.test.acceptance.Init;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.netbeans.jemmy.operators.*;
import org.netbeans.jemmy.util.NameComponentChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.io.File;
import java.util.*;

import static asepsis.bluej.test.acceptance.Init.bluejFrame;
import static asepsis.bluej.test.acceptance.Init.getTempDir1;
import static asepsis.bluej.test.acceptance.Init.getTempDir2;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Steps {
    private JDialogOperator extensionsDialog;
    private JDialogOperator extensionDetailsDialog;

    @Given("^the extension is placed in the extensions folder$")
    public void the_extension_is_placed_in_the_correct_folder() throws Throwable {
        // Already done by Gradle build
    }

    @When("^I open the installed extensions window$")
    public void I_open_the_installed_extensions_window() throws Throwable {
        new JMenuBarOperator(bluejFrame()).pushMenuNoBlock("Help|Installed Extensions");
        extensionsDialog = new JDialogOperator(bluejFrame(), "BlueJ:  Installed Extensions");
    }

    @Then("^I should see the extension listed$")
    public void I_should_see_the_extension_listed() throws Throwable {
        JTableOperator tbl = new JTableOperator(extensionsDialog);
        tbl.waitCell("loaded", 0, 1);
        tbl.waitCell("ASEPSiS-BlueJ", 0, 2);
        tbl.waitCell("System", 0, 3);
    }

    @When("^I open the extension details window$")
    public void I_open_the_extension_details_window() throws Throwable {
        JTableOperator tbl = new JTableOperator(extensionsDialog);
        tbl.clickOnCell(0, 0);
        extensionDetailsDialog = new JDialogOperator(bluejFrame(), "Extension Details");
    }

    @Then("^I should see the extensions name$")
    public void I_should_see_the_extensions_name() throws Throwable {
        new JLabelOperator(extensionDetailsDialog).waitText("ASEPSiS-BlueJ version 1.0.0");

    }

    @Then("^I should see the extensions description$")
    public void I_should_see_the_extensions_description() throws Throwable {
        new JTextAreaOperator(extensionDetailsDialog).waitText("Extends BlueJ with functionality provided by the ASEPSiS system.");
    }

    @Then("^I should see the extensions website$")
    public void I_should_see_the_extensions_website() throws Throwable {
        String txt = new JLabelOperator(extensionDetailsDialog, 4).getText();
        assertThat(txt, is("http://github.com/olerass/asepsis-bluej")); // TODO: jemmy doesn't find this label with waitText for some reason, fix
    }

    @When("^I create a new project$")
    public void I_create_a_new_project() throws Throwable {
        new JMenuBarOperator(bluejFrame()).pushMenuNoBlock("Project|New Project...");
        JFileChooserOperator chooser = new JFileChooserOperator( new JDialogOperator(bluejFrame(), "New Project") );
        chooser.chooseFile( new File(getTempDir1(), "testProject1").getAbsolutePath() );

        // BlueJ starts a new instance of itself at this point, we need to set the new instance as the main frame
        Init.setBluejFrame("Bluej:  testProject1");
    }

    @When("^I create another new project$")
    public void I_create_another_new_project() throws Throwable {
        new JMenuBarOperator(bluejFrame()).pushMenuNoBlock("Project|New Project...");
        Thread.sleep(200);
        JFileChooserOperator chooser = new JFileChooserOperator( new JDialogOperator(bluejFrame(), "New Project") );
        chooser.chooseFile( new File(getTempDir2(), "testProject2").getAbsolutePath() );

        // BlueJ starts a new instance of itself at this point, we need to set the new instance as the main frame
        Init.setBluejFrame("Bluej:  testProject2");
    }

    @Then("^ASEPSiS should say \"([^\"]*)\"$")
    public void ASEPSiS_should_say(String expectedStr) throws Throwable {
        JLabelOperator lbl = new JLabelOperator(bluejFrame(), new NameComponentChooser("asepsis.sidebar.status"));

        assertThat(lbl.getText(), is(expectedStr));
    }
}