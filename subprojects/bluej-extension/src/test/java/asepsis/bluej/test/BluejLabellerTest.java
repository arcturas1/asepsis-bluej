package asepsis.bluej.test;

import asepsis.bluej.BluejLabeller;
import bluej.extensions.BlueJ;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BlueJ.class)
public class BluejLabellerTest {
    @Test
    public void getLabel_ReturnsLabelFromBluejProxy_WhenCalled() {
        BlueJ proxyStub = mock(BlueJ.class);
        when( proxyStub.getLabel("someLabel") ).thenReturn("Some description");
        BluejLabeller labeller = new BluejLabeller(proxyStub);

        String res = labeller.getLabel("someLabel");

        assertThat(res, is("Some description"));
        verify(proxyStub).getLabel("someLabel");
    }
}
