package com;

public class PersonContainer {
    private PName name;

    public PersonContainer(PName name) {
        this.setName(name);
    }

	public PName getName() {
		return name;
	}

	public void setName(PName name) {
		this.name = name;
	}
}