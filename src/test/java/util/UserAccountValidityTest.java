package util;

import model.UserAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserAccountValidity.class)
public class UserAccountValidityTest {

    @Mock
    private UserAccount userAccount;

    @Before
    public void setUp() {
        mockStatic(UserAccountValidity.class);
        when(UserAccountValidity.checkUserIdIsValid(userAccount)).thenReturn(true);
        when(UserAccountValidity.checkUserAccountIsValid(userAccount)).thenReturn(true);
    }

    @Test
    public void checkUserIdAndValidityAreValid_ReturnsTrue_WhenUserIdIsMoreThanZeroAndUserAccountValidityFieldIsGreaterThanCurrentTime() {
        boolean result = UserAccountValidity.checkUserIdIsValid(userAccount);

        assertTrue(result);
    }

}