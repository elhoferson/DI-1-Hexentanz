package com.example.di_1_hexentanz;

import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


@Config(manifest= Config.NONE)
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Startscreen.class })

//@RunWith(RobolectricTestRunner.class)

public class StartScreenTest {

        private Startscreen tested;

        @Mock
        private Button mockButton;


    }
