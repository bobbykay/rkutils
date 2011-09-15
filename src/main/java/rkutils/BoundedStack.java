package rkutils;

public class BoundedStack {

	private int[] data;
	private int depth;
	
	BoundedStack(int i) {
		data = new int[i];
		depth = 0;
	}

	public int size() {
		return 0;
	}

	public int pop() throws Exception {
		if (depth == 0) throw new Empty();
		return data[--depth];
	}
	
	static class Empty extends Exception {
	}
	
	static class Full extends Exception {
	}

	public void push(int elem) throws Full {
		if (depth >= 10) {
			throw new Full();
		}
		data[depth++] = elem;
	}
	
	public int depth() {
		return depth;
	}
	
}
