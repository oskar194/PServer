package com.admin.budgetrook.pipeline.command;

public interface Command<T>{
	public T execute(T input);
}
