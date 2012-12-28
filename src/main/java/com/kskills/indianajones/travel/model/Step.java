package com.kskills.indianajones.travel.model;

import java.util.ArrayList;

import java.util.List;

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
		return getFrom() + " -> " + getTo() + " start at " + getStartAt() + " end at " 
					+ getEndAt() + " in " + getDuration();
	}

	@Override
	public int compareTo(Step o) {
		return getEndAt().getTotalSeconds().compareTo(o.getEndAt().getTotalSeconds());
	}

}
