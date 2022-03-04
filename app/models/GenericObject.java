package models;

import java.io.Serializable;

public class GenericObject implements Cloneable, Serializable {

	private static final long serialVersionUID = -7706958049099297338L;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}