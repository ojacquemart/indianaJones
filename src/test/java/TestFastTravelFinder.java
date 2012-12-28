import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.kskills.indianajones.travel.finder.FastTravelFinder;
import com.kskills.indianajones.travel.model.Step;
import com.kskills.indianajones.travel.model.Travel;
import com.kskills.indianajones.travel.reader.Reader;

import static org.junit.Assert.*;

public class TestFastTravelFinder {

	private Travel travel;
	private FastTravelFinder finder;

	@Before
	public void setUp() throws IOException {
		final Reader reader = new Reader("paris-berlin_1840.txt");
		this.travel = reader.readLines();
		this.finder = new FastTravelFinder(travel);
	}

	@Test
	public void testFindInitialDepartures() {
		finder.buildInitialStarts();
		Step startPoint = finder.getStartPoint();
		assertNotNull(startPoint);
		assertEquals(2, startPoint.getNextSteps().size());
	}
	
	@Test
	public void testFind() {
		assertEquals("18:40", finder.findBestTime());
	}
	
	

}
