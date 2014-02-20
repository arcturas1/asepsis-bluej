package asepsis.bluej.test.acceptance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JTableOperator;

import static asepsis.bluej.test.acceptance.CucumberInit.bluejFrame;

public class Steps {
    private JDialogOperator extensionsDialog;

    @Given("^the extension is placed in the extensions folder$")
    public void the_extension_is_placed_in_the_correct_folder() throws Throwable {
        // Already done by Maven build
    }

    @When("^I open the installed extensions window$")
    public void I_open_the_installed_extensions_window() throws Throwable {
        Thread.sleep(2000);
        new JMenuBarOperator(bluejFrame()).pushMenuNoBlock("Help|Installed Extensions");
        Thread.sleep(2000);
        extensionsDialog = new JDialogOperator("BlueJ:  Installed Extensions");
    }

    @Then("^I should see the extension listed$")
    public void I_should_see_the_extension_listed() throws Throwable {
//        JTableOperator tbl = new JTableOperator(extensionsDialog);
//        tbl.waitCell("loaded", 0, 1);
//        tbl.waitCell("ASEPSiS-BlueJ", 0, 2);
//        tbl.waitCell("System", 0, 3);
    }
}