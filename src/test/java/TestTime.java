import org.junit.Test;

import com.kskills.indianajones.travel.model.Time;

import static org.junit.Assert.*;


public class TestTime {

	@Test
	public void testFromString() {
		Time duration = Time.fromString("06:00");
		assertEquals(Integer.valueOf(6), duration.getHours());
		assertEquals(Integer.valueOf(0), duration.getMinutes());
	}
	
	@Test
	public void testAdd() {
		assertEquals("16:30", Time.fromString("06:15").add(Time.fromString("10:15")).toString());
		assertEquals("14:35", Time.fromString("06:50").add(Time.fromString("07:45")).toString());
		assertEquals("16:00", Time.fromString("12:01").add(Time.fromString("03:59")).toString());
		assertEquals("23:00", Time.fromString("13:20").add(Time.fromString("09:40")).toString());
		assertEquals("09:40", Time.fromString("02:50")
									.add(Time.fromString("04:00"))
									.add(Time.fromString("02:50")).toString());
	}
	
	@Test
	public void testBefore() {
		assertTrue(Time.fromString("10:10").before(Time.fromString("10:11")));
		assertFalse(Time.fromString("10:11").before(Time.fromString("10:10")));
	}
}
