public class A {
	static void m1(){
		System.out.println("A m1");
	}
	void m2(){
		m3();
	}
} 
class B extends A {
	static void m1(){
		System.out.println("B M1");
	}
	void m2(){
		System.out.println("B M2");
	}
}

// Now if we create an object like this and call method m1()
A obj = new B();
obj.m1();

/**
Answer
Since static methods are bound to classes, hence at compile time, since obj reference is of class A, A's m1() would be called.
If m1 would've been non static, then it would've been a case of runtime polymorphism and m1 would be called on the object instance i.e. B
**/

method(){
	try{
		E1;
	} catch (E1){
		E2;
	} finally {
		E3;
	}
}

/**
Here E1, E2, E3 are custom errors, if we call the above method which error will be thrown and why?
Answer
Similar to return statements, exception is finally block would override exception in catch block. So E3 would be thrown
**/
