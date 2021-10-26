package automation;

import org.testng.annotations.Test;

import com.hubspot.keyFramwork.engine.KeywordEngine;

public class LoginTest {
	@Test
	public void TestRunner() {
		KeywordEngine k= new KeywordEngine();
		k.startExecution("login");
	}
}
