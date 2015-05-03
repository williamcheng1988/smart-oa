package com.chz.smartoa.delegation.action;

public class Gongzi {
	private double base;
	private double shebao;
	
	public double getBase() {
		return base;
	}
	public void setBase(double base) {
		this.base = base;
	}
	public double getShebao() {
		return this.base*0.5+11;
	}
}
