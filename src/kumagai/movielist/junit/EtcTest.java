package kumagai.movielist.junit;

import junit.framework.*;

public class EtcTest
	extends TestCase
{
	public void test1()
	{
		assertEquals(-1, "1".compareTo("2"));
	}
}
