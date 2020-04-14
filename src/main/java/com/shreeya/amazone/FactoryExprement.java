package com.shreeya.amazone;

import org.testng.annotations.Factory;

public class FactoryExprement {
	
	@Factory
	public Object[] myfactory() {
		return new Object [] {new TestFactory1("Women cloths"),new TestFactory2("men cloths")};
	}

}
