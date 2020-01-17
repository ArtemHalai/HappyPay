package util;

import model.UserAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserAccountValidity.class)
public class UserAccountValidityTest {

    @Mock
    private UserAccount userAccount;

    @Test
    public void checkUserIdAndValidityAreValid_ReturnsTrue_WhenUserIdIsMoreThanZeroAndUserAccountValidityFieldIsGreaterThanCurrentTime() {
        stub(method(UserAccountValidity.class, "checkUserIdIsValid", UserAccount.class)).toReturn(true);
        stub(method(UserAccountValidity.class, "checkUserAccountIsValid", UserAccount.class)).toReturn(true);
        boolean result = UserAccountValidity.checkUserIdAndValidityAreValid(userAccount);

        assertTrue(result);
    }

}