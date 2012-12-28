import static org.junit.Assert.*;

import org.junit.Test;

import com.kskills.indianajones.travel.model.Step;
import com.kskills.indianajones.travel.model.Time;

public class TestStep {

	@Test
	public void testBefore() {
		Step step1 = new Step(Time.fromString("08:30"), null, null, null);
		Step step2 = new Step(Time.fromString("11:30"), null, null, null);
		assertTrue(step1.endsBefore(step2));
	}
}
