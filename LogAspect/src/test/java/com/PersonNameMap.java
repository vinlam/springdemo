package com;

import java.util.Map;

public class PersonNameMap {

	private Map<String, String> nameMap;

    public PersonNameMap(Map<String, String> nameMap) {
        this.setNameMap(nameMap);
    }

	public Map<String, String> getNameMap() {
		return nameMap;
	}

	public void setNameMap(Map<String, String> nameMap) {
		this.nameMap = nameMap;
	}

}
