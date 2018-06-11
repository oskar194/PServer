package com.admin.budgetrook.pipeline.manager;

import java.util.ArrayList;
import java.util.List;

import com.admin.budgetrook.pipeline.command.Command;

public class Pipeline<T> {
	private List<Command<T>> commands;
	private T partial;

	public List<Command<T>> getcommands() {
		return commands;
	}

	public void setcommands(List<Command<T>> commands) {
		this.commands = commands;
	}

	public Pipeline<T> add(Command<T> Command) {
		this.commands.add(Command);
		return this;
	}

	public Pipeline() {
		this.commands = new ArrayList<Command<T>>();
	}

	public Pipeline(List<Command<T>> commands) {
		this.commands = commands;
	}

	public T executePipeline(T input) {
		for (Command<T> Command : commands) {
			partial = Command.execute(input);
			input = partial;
		}
		return input;
	}
}
