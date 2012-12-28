import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({ 
		TestTime.class, TestStep.class,
		TestTravelReader.class,
		TestFastTravelFinder.class, TestAllSolutions.class
})
@RunWith(Suite.class)
public class SuiteTest {

}
