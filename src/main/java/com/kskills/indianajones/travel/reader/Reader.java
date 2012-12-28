package com.kskills.indianajones.travel.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.kskills.indianajones.travel.model.Step;
import com.kskills.indianajones.travel.model.Time;
import com.kskills.indianajones.travel.model.Travel;

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

	private static String[] checkItem(String value) {
		String[] split = value.split(ITEM_SEPARATOR);
		checkNonNull(split);
		checkMinSizeArray(split, 4);

		return split;
	}

	private static void checkNonNull(Object object) {
		if (object == null) {
			throw new IllegalArgumentException("object must be non null");
		}
	}

	private static void checkMinSizeArray(String[] array, int exceptedSize) {
		if (array.length < exceptedSize) {
			throw new IllegalArgumentException("values.length is lower than "
					+ exceptedSize);
		}
	}

}
