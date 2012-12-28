import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.kskills.indianajones.travel.model.Travel;
import com.kskills.indianajones.travel.model.Step;
import com.kskills.indianajones.travel.reader.Reader;

import static org.junit.Assert.*;

public class TestTravelReader {

	@Test
	public void testParse() throws IOException {
		String fileItinerary = "sofia-budapest.txt";
		final Reader reader = new Reader(fileItinerary);

		final Travel travel = reader.readLines();
		assertNotNull(travel);

		final Step title = travel.getStart();

		assertItem("06:34", "Sofia", "Budapest", title);
		final List<Step> itinerary = travel.getSteps();
		assertItem("06:35", "15:00", "Sofia", "Budapest", itinerary.get(0));
		assertItem("06:50", "07:45", "Sofia", "Bucarest", itinerary.get(1));
		assertItem("14:50", "06:30", "Bucarest", "Budapest", itinerary.get(2));
	}

	private void assertItem(String start, String departure, String arrival,
			Step item) {
		assertEquals(start, item.getStartAt().toString());
		assertEquals(departure, item.getFrom());
		assertEquals(arrival, item.getTo());
	}

	private void assertItem(String start, String end, String departure,
			String arrival, Step Item) {
		assertItem(start, departure, arrival, Item);
		assertEquals(end, Item.getDuration().toString());
	}
}
