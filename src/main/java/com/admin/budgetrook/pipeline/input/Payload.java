package com.admin.budgetrook.pipeline.input;

public abstract class Payload<T>{
	private T value;
	
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

	@Override
	public String toString() {
		return value.toString();
	}
	
	
}
