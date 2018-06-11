package com.admin.budgetrook.pipeline.input;

public abstract class Payload<T>{
	private T value;
	private T original;
	
	public Payload(T value){
		this.value = value;
	}
	
	public Payload(){
	}
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	public T getOriginal() {
		return original;
	}

	public void setOriginal(T original) {
		this.original = original;
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
	
}
