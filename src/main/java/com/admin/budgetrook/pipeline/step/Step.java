package com.admin.budgetrook.pipeline.step;

import java.util.ArrayList;
import java.util.List;

import com.admin.budgetrook.pipeline.command.Command;

public class Step<T> {
	protected List<Command<T>> commands;
	private T partial;

	public Step() {
		commands = new ArrayList<Command<T>>();
	}

	public Step(List<Command<T>> commands) {
		this.commands = commands;
	}
	
	public Step<T> add(Command<T> command){
		this.commands.add(command);
		return this;
	}

	public T executeStep(T input) {
		for (Command<T> command : commands) {
			partial = command.execute(input);
			input = partial;
		}
		return input;
	}
}
