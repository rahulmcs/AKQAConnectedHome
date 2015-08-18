package com.smac0.akqaconnectedhome.presenters;

import android.support.test.InstrumentationRegistry;
import android.test.AndroidTestCase;

import com.smac0.akqaconnectedhome.R;
import com.smac0.akqaconnectedhome.fragments.presenters.BathWaterFragmentPresenter;
import com.smac0.akqaconnectedhome.fragments.views.BathView;
import com.smac0.akqaconnectedhome.model.BathWaterTemperature;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by rahulchaudhari on 17/08/15.
 */
public class BathWaterFragmentPresenterTest extends AndroidTestCase{

    BathWaterFragmentPresenter bathWaterFragmentPresenter;

    BathView mockBathView;

    Throwable mockThrowable;

    BathWaterTemperature mockBathWaterTemperature;

    @Before
    @Override
    protected void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        super.setUp();
        System.setProperty("dexmaker.dexcache", InstrumentationRegistry.getTargetContext().getCacheDir().getPath());
        bathWaterFragmentPresenter = new BathWaterFragmentPresenter();
        mockBathView = mock(BathView.class);
        bathWaterFragmentPresenter.setView(mockBathView);
    }

    @Test
    public void testOnNext() {
        mockBathWaterTemperature = mock(BathWaterTemperature.class);
        bathWaterFragmentPresenter.onNext(mockBathWaterTemperature);

        verify(mockBathView).setWaterTemperature(mockBathWaterTemperature);
        verify(mockBathView).hideProgress();
    }

    @Test
    public void testOnError() {
        mockThrowable = mock(Throwable.class);
        given(mockBathView.getContext()).willReturn(InstrumentationRegistry.getTargetContext());
        bathWaterFragmentPresenter.onError(mockThrowable);

        verify(mockBathView).hideProgress();
        verify(mockBathView).showWaterTemperatureFetchError(InstrumentationRegistry.getTargetContext().getString(R.string.error_bath_temperature_failure));
    }

    //TODO add more tests for other methods of BathWaterFragmentPresenter.
    // Some of these use private methods and we will need Powermockito
    //TODO Also add tests for the Rest service layer
}
