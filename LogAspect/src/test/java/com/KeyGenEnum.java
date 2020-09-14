package com;

public enum KeyGenEnum {
	RA(new RSAKeyGen());

	private KeyGen keyGen;

	KeyGenEnum(KeyGen gen) {
		this.keyGen = gen;
	}

	public KeyGen getKeyGen() {
		return this.keyGen;
	}
}
