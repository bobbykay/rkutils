package rkutils;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import rkutils.BoundedStack;

public class StackTest {
	private BoundedStack s;

	@Before
	public void initialize() {
		s = new BoundedStack(3);
	}

	@Test
	public void emptyStackShouldHaveZeroSize() {
		assertEquals(0, s.size());
	}
	
	@Test(expected = BoundedStack.Empty.class)
	public void emptyStackShouldThrowWhenPopped() throws Exception {
		s.pop();
	}
	
	@Test
	public void depthShouldBeOneAfterOnePush() throws BoundedStack.Full {
		s.push(0);
		assertEquals(1,s.depth());
	}
	
	@Test
	public void stackShouldReturnSameWhenPoppedAsPushed() throws Exception {
		s.push(10);
		assertEquals(10, s.pop());
	}
	
	
}
