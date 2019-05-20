/*package com.example.di_1_hexentanz;

import android.app.Activity;
import android.arch.lifecycle.ReportFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ReportFragment.class)

public class StartscreenTest {

    private Startscreen testscreen;

    @Test
    public void testOnCreate() {
        // Mock some data
        mockStatic(ReportFragment.class);
        Startscreen test = spy(new Startscreen());
        //().when(test).
        doNothing().when(test).setContentView(R.layout.activity_startscreen);
        doReturn(mock(AppCompatDelegate.class)).when(test).getDelegate();

        // Call the method
        //test.onCreate(Bundle.EMPTY);

        // Verify that it worked
        verify(test, times(1)).setContentView(R.layout.activity_startscreen);
        //verify(test, times(1)).initScreen();
    }
    /*
    @Before
    public void setUp() throws Exception {
        testscreen = spy(Startscreen.class);
        doReturn(mockCreateGame).when(testscreen).findViewById(anyInt());

        suppress(method(Activity.class, "onCreate", Bundle.class));
        suppress(method(Activity.class, "setContentView", int.class));
    }

    @Test
    public void shouldSetupListener() throws Exception {
        // When we call the onCreate...
        testscreen.onCreate(mock(Bundle.class));

        // Then the setOnClickListener method will be called.
        verify(mockCreateGame).setOnClickListener((View.OnClickListener) any());

    }

    @Test
    public void shouldStartActivity() throws Exception {
        // Let's setup our mockButton so that it will capture the listener
        ArgumentCaptor<View.OnClickListener> captor =
                ArgumentCaptor.forClass(View.OnClickListener.class);
        doNothing().when(mockCreateGame).setOnClickListener(captor.capture());

        // When we call the onCreate...
        testscreen.onCreate(mock(Bundle.class));

        // Listener should be in place (Was tested before) so now we need to check that it starts
        //  an activity.
        doNothing().when(testscreen).startActivity((Intent) any());
        doNothing().when(testscreen).finish();  // We should also test this to be called in a new test.
        captor.getValue().onClick(mockCreateGame);
        verify(testscreen).startActivity((Intent) any());

    }



}

*/