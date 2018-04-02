/*
 * This is an example test project created in Eclipse to test NotePad which is a sample 
 * project located in AndroidSDK/samples/android-11/NotePad
 * 
 * 
 * You can run these test cases either on the emulator or on device. Right click
 * the test project and select Run As --> Run As Android JUnit Test
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

package mobileapps.osubardeals.osubardealsapp;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import mobileapps.osubardeals.osubardealsapp.Activities.MainActivity;


@RunWith(AndroidJUnit4.class)
public class LoginTest {

    private static final String NOTE_1 = "Note 1";
    private static final String NOTE_2 = "Note 2";


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private Solo solo;
    private String email = "drewg2009@gmail.com";
    private String pwd = "new";
    private String wrongPwd = "asdf";


    @Before
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());

    }

    @After
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    @Test
    public void testLoginWithRightCredentials() {


        solo.unlockScreen();
        solo.clickOnView(solo.getEditText(0));
        solo.enterText(0, email);
        solo.clickOnView(solo.getEditText(1));
        solo.enterText(1, pwd);
        solo.clickOnView(solo.getButton(0));
        solo.waitForView(solo.getView(R.id.card_layout));
        Assert.assertTrue(        solo.getText(0).getText().toString().equals("Home"));

    }

    @Test
    public void testLoginWithWrongCredentials() {

        solo.unlockScreen();
        solo.clickOnView(solo.getEditText(0));
        solo.enterText(0, email);
        solo.clickOnView(solo.getEditText(1));
        solo.enterText(1, wrongPwd);
        solo.clickOnView(solo.getButton(0));
        Assert.assertTrue(        solo.getText(0).getText().toString().equals("Login"));

    }

}
