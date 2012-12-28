package com.kskills.indianajones.travel.finder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.kskills.indianajones.travel.model.Step;
import com.kskills.indianajones.travel.model.Travel;

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
