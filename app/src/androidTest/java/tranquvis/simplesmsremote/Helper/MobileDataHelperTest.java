package tranquvis.simplesmsremote.Helper;

import org.junit.Test;

import tranquvis.simplesmsremote.AppContextTest;
import tranquvis.simplesmsremote.Aspects.ExecSequentially.ExecSequentially;

import static org.junit.Assert.*;

/**
 * Created by Andi on 03.09.2016.
 */
public class MobileDataHelperTest extends AppContextTest
{
    @Test
    @ExecSequentially("mobile data")
    public void testSetMobileDataStateEnabled() throws Exception {
        MobileDataHelper.SetMobileDataState(appContext, true);

        boolean enabled = TryUntil(new TryMethod<Boolean>() {
            @Override
            public Boolean run() throws Exception {
                return MobileDataHelper.IsMobileDataEnabled(appContext);
            }
        }, true);
        assertTrue(enabled);
    }

    @Test
    @ExecSequentially("mobile data")
    public void testSetMobileDataStateDisabled() throws Exception {
        MobileDataHelper.SetMobileDataState(appContext, false);

        boolean enabled = TryUntil(new TryMethod<Boolean>() {
            @Override
            public Boolean run() throws Exception {
                return MobileDataHelper.IsMobileDataEnabled(appContext);
            }
        }, false);
        assertFalse(enabled);
    }

    @Test
    @ExecSequentially("mobile data")
    public void testIsMobileDataEnabled() throws Exception {
        MobileDataHelper.IsMobileDataEnabled(appContext);
    }
}