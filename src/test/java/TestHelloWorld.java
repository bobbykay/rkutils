import junit.framework.TestCase;


public class TestHelloWorld extends TestCase {	
	public void testHelloWorld () {
		assertEquals("Hello World!", new HelloWorld().sayHello());
	}

}
