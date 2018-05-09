package com.admin.budgetrook.pipeline.manager;

import java.util.ArrayList;
import java.util.List;

import com.admin.budgetrook.pipeline.step.Step;

public class Pipeline<T> {
	private List<Step<T>> steps;
	private T partial;

	public List<Step<T>> getSteps() {
		return steps;
	}

	public void setSteps(List<Step<T>> steps) {
		this.steps = steps;
	}
	
	public Pipeline<T> add(Step<T> step){
		this.steps.add(step);
		return this;
	}

	public Pipeline() {
		this.steps = new ArrayList<Step<T>>();
	}

	public Pipeline(List<Step<T>> steps) {
		this.steps = steps;
	}

	public T executePipeline(T input) {
		for (Step<T> step : steps) {
			partial = step.executeStep(input);
			input = partial;
		}
		return input;
	}
}
