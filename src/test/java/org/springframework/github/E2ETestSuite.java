package org.springframework.github;

import com.googlecode.junittoolbox.SuiteClasses;
import com.googlecode.junittoolbox.WildcardPatternSuite;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@Ignore
@RunWith(WildcardPatternSuite.class)
@SuiteClasses("**/*E2E.class")
public class E2ETestSuite {

}
