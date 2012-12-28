import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

class kskills {

	public static void main(String[] args) throws Exception {
		kskills kskills = new kskills();
		Travel travel = kskills.new Reader(System.in).readLines();
		FastTravelFinder finder = kskills.new FastTravelFinder(travel);
		String time = finder.findBestTime();
		System.out.println(time);
	}

	public class Travel {

		private Step start;
		private List<Step> steps = new ArrayList<>();

		public Travel(Step start, List<Step> ways) {
			this.start = start;
			this.steps = ways;
		}

		public Step getStart() {
			return start;
		}

		public List<Step> getSteps() {
			return steps;
		}

		@Override
		public String toString() {
			return "start point: " + start + " " + "\n => steps: " + steps + "";
		}

	}

	public class Step implements Comparable<Step> {

		private final Time startAt;
		private final Time endAt;
		private Time duration = Time.fromString("00:00");

		private final String from;
		private final String to;

		private List<Step> nextSteps = new ArrayList<>();

		public Step(Step step) {
			this(step.getStartAt(), step.getFrom(), step.getTo());
		}

		public Step(Time start, String from, String to) {
			this(start, null, from, to);
		}

		public Step(Time start, Time duration, String from, String to) {
			this.startAt = start;
			this.endAt = startAt.add(duration);
			this.duration = duration;
			this.from = from;
			this.to = to;
		}

		public boolean endsBefore(Step other) {
			return getEndAt().before(other.getStartAt());
		}

		// Getters & setters.

		public Time getStartAt() {
			return startAt;
		}

		public Time getEndAt() {
			return endAt;
		}

		public Time getDuration() {
			return duration;
		}

		public String getTo() {
			return to;
		}

		public String getFrom() {
			return from;
		}

		public List<Step> getNextSteps() {
			return nextSteps;
		}

		public void setNextSteps(List<Step> nextSteps) {
			this.nextSteps = nextSteps;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Step)) {
				return false;
			}

			Step other = (Step) obj;
			return getFrom().equals(other.getFrom()) && getTo().equals(other.getTo());
		}

		@Override
		public int hashCode() {
			return to.hashCode() + from.hashCode();
		}

		@Override
		public String toString() {
			return getFrom() + " -> " + getTo() + " start at " + getStartAt() + " end at " + getEndAt() + " in "
					+ getDuration();
		}

		@Override
		public int compareTo(Step o) {
			return getEndAt().getTotalSeconds().compareTo(o.getEndAt().getTotalSeconds());
		}

	}

	public static class Time {

		private static final String SEPARATOR = ":";

		private Integer hours;
		private Integer minutes;

		public Time(Integer hours, Integer minutes) {
			super();
			this.hours = hours;
			this.minutes = minutes;
		}

		public static Time empty() {
			return new Time(0, 0);
		}

		public Time add(Time other) {
			if (other == null) {
				return empty();
			}

			Long totalOther = other.getTotalSeconds();
			Long totalThis = getTotalSeconds();
			Long sumInMillis = 1000 * (totalOther + totalThis);

			Integer hours = (int) TimeUnit.MILLISECONDS.toHours(sumInMillis);
			Integer minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(sumInMillis) - TimeUnit.HOURS.toMinutes(hours));

			return new Time(hours, minutes);
		}

		public Time add(String duration) {
			return add(fromString(duration));
		}

		public boolean before(Time other) {
			if (other == null) {
				return true;
			}

			return getTotalSeconds().compareTo(other.getTotalSeconds()) <= 0;
		}

		public static Time fromString(String value) {
			String[] split = value.split(SEPARATOR);
			Integer hours = Integer.valueOf(split[0]);
			Integer minutes = Integer.valueOf(split[1]);

			return new Time(hours, minutes);
		}

		public Integer getHours() {
			return hours;
		}

		public void setHours(Integer hours) {
			this.hours = hours;
		}

		public Integer getMinutes() {
			return minutes;
		}

		public void setMinutes(Integer minuts) {
			this.minutes = minuts;
		}

		public Long getTotalSeconds() {
			return TimeUnit.HOURS.toSeconds(getHours()) + TimeUnit.MINUTES.toSeconds(getMinutes());
		}

		@Override
		public String toString() {
			final NumberFormat nb = NumberFormat.getInstance();
			nb.setMinimumIntegerDigits(2);

			return nb.format(hours) + SEPARATOR + nb.format(minutes);
		}

	}

	public class Reader {

		private static final String ITEM_SEPARATOR = ";";

		private BufferedReader br;

		public Reader(InputStream in) {
			this.br = new BufferedReader(new InputStreamReader(in));
		}

		public Reader(String fileName) throws FileNotFoundException {
			final File file = new File(fileName);
			this.br = new BufferedReader(new FileReader(file));
		}

		public Travel readLines() throws IOException {
			Step startPoint = readTitle(br.readLine());
			Integer nbSchedules = Integer.parseInt(br.readLine());
			List<Step> steps = readSteps(nbSchedules);

			return new Travel(startPoint, steps);
		}

		// 08:20;Paris;Berlin
		private Step readTitle(String value) {
			final String[] split = value.split(ITEM_SEPARATOR);
			checkNonNull(split);
			checkMinSizeArray(split, 2);

			Time startAt = Time.fromString(split[0]);
			String from = split[1];
			String to = split[2];

			return new Step(startAt, from, to);
		}

		// 06:35;Sofia;Budapest;15:00
		private List<Step> readSteps(Integer nbSteps) throws IOException {
			List<Step> items = new ArrayList<Step>();

			for (int i = 0; i < nbSteps; i++) {
				String[] values = checkItem(br.readLine());

				Time startAt = Time.fromString(values[0]);
				Time duration = Time.fromString(values[3]);
				String from = values[1];
				String to = values[2];
				Step step = new Step(startAt, duration, from, to);

				items.add(step);
			}

			return items;
		}

		private String[] checkItem(String value) {
			String[] split = value.split(ITEM_SEPARATOR);
			checkNonNull(split);
			checkMinSizeArray(split, 4);

			return split;
		}

		private void checkNonNull(Object object) {
			if (object == null) {
				throw new IllegalArgumentException("object must be non null");
			}
		}

		private void checkMinSizeArray(String[] array, int exceptedSize) {
			if (array.length < exceptedSize) {
				throw new IllegalArgumentException("values.length is lower than " + exceptedSize);
			}
		}

	}

	public class FastTravelFinder {

		private Step startPoint;
		private List<Step> steps;

		public FastTravelFinder(Travel travel) {
			this.startPoint = new Step(travel.getStart());
			this.steps = new ArrayList<>(travel.getSteps());
		}

		public String findBestTime() {
			this.buildInitialStarts();
			this.buildAllNextSteps();

			List<Step> finalFoundSteps = findFinalStep(startPoint.getNextSteps());
			if (finalFoundSteps.isEmpty()) {
				throw new IllegalStateException("No arrival found for " + startPoint.getTo());
			}

			// Juste one solution.
			if (finalFoundSteps.size() == 1) {
				return getFirstStepEndTimeToString(finalFoundSteps);
			}

			// Many solutions: sort list to find the first ending step.
			Collections.sort(finalFoundSteps);

			return getFirstStepEndTimeToString(finalFoundSteps);
		}

		public void buildInitialStarts() {
			Iterator<Step> it = steps.iterator();
			while (it.hasNext()) {
				Step current = it.next();
				if (isSameFrom(current, startPoint)) {
					startPoint.getNextSteps().add(current);

					// Remove step from list to avoid reiteration on the step.
					it.remove();
				}
			}

			if (startPoint.getNextSteps().isEmpty()) {
				throw new IllegalStateException("None start points found!");
			}
		}

		public void buildAllNextSteps() {
			for (Step nextStep : startPoint.getNextSteps()) {
				iterNextSteps(nextStep);
			}
		}

		private void iterNextSteps(Step nextStep) {
			for (Step step : steps) {
				if (nextStep.equals(step)) {
					continue;
				}
				if (isNextStep(nextStep, step) && nextStep.endsBefore(step)) {
					nextStep.getNextSteps().add(step);

					iterNextSteps(step);
				}
			}
		}

		private String getFirstStepEndTimeToString(List<Step> steps) {
			return steps.get(0).getEndAt().toString();
		}

		private List<Step> findFinalStep(List<Step> nextSteps) {
			if (nextSteps.isEmpty()) {
				return new ArrayList<>();
			}

			List<Step> finalSteps = new ArrayList<>();

			for (Step step : nextSteps) {
				if (isSameTo(step, startPoint)) {
					finalSteps.add(step);
				}

				finalSteps.addAll(findFinalStep(step.getNextSteps()));
			}

			return finalSteps;
		}

		public boolean isNextStep(Step current, Step next) {
			return current.getTo().equals(next.getFrom());
		}

		public boolean isSameFrom(Step item1, Step item2) {
			return item1.getFrom().equals(item2.getFrom());
		}

		public boolean isSameTo(Step item1, Step item2) {
			return item1.getTo().equals(item2.getTo());
		}

		public Step getStartPoint() {
			return startPoint;
		}

	}


}
