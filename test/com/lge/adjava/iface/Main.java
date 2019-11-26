package com.lge.adjava.iface;

import org.junit.jupiter.api.Test;

class Main {
	interface PIface {
		default void foo() {
			System.out.println("PIface");
		}
	}
	
	class CMain extends Class implements CIface22{
		
	}
	
	
	interface IMain extends CIface22, CIface23{
		@Override
		default void foo() {
			CIface22.super.foo();
		}
	}
	
	interface CIface22 extends PIface{
		default void foo() {
			System.out.println("CIface22");
		}
	}
	interface CIface23 extends PIface{
		default void foo() {
			System.out.println("CIface23");
		}
	}
	
	
	
	interface CIface21 extends PIface{
		void foo();
	}
	
	
	interface CIface extends PIface{		
	}
	
	class Class implements PIface{
		public void foo() {
			System.out.println("Class");
		}
	}

	

	@Test
	void test() {
		PIface piface = new Class();
		piface.foo();
	}
	
	
	
	
	
	
	
}
