package com.kskills.indianajones.travel.model;

import java.util.ArrayList;
import java.util.List;

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
