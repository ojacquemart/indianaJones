import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kskills.indianajones.travel.finder.FastTravelFinder;
import com.kskills.indianajones.travel.model.Travel;
import com.kskills.indianajones.travel.reader.Reader;

import static org.junit.Assert.*;

public class TestAllSolutions {

	@Test
	public void testFindAllSolutions() throws IOException {
		List<UseCase> list = new ArrayList<>();
		list.add(new UseCase("paris-berlin_1840.txt", "18:40"));
		list.add(new UseCase("madrid-milan_2150.txt", "21:50"));
		list.add(new UseCase("sofia-budapest_2120.txt", "21:20"));
		list.add(new UseCase("rome-prague_2300.txt", "23:00"));

		for (UseCase uc : list) {
			Reader reader = new Reader(uc.fileName);
			Travel travel = reader.readLines();
			FastTravelFinder finder = new FastTravelFinder(travel);
			assertEquals(uc.expected, finder.findBestTime());
		}

	}

	class UseCase {

		public UseCase(String fileName, String expected) {
			this.fileName = fileName;
			this.expected = expected;
		}

		String fileName;
		String expected;
	}

}
