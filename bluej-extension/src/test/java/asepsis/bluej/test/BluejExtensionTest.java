package asepsis.bluej.test;

import asepsis.bluej.BluejExtension;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BluejExtensionTest {
    private BluejExtension ext;

    @Before
    public void setup() {
        ext = new BluejExtension();
    }

    @Test
    public void getVersion_ReturnsTheExtensionVersion_WhenCalled() {
        assertThat(ext.getVersion(), is("1.0.0"));
    }

    @Test
    public void getName_ReturnsTheExtensionName_WhenCalled() {
        assertThat(ext.getName(), is("ASEPSiS-BlueJ"));
    }
}
