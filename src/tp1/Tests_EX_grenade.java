package tp1;

import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests_EX_grenade {
	public static final String DIR = "tests/ex_grenade/";
	public static final String FILE_PREFIXES[] = {
			"01_00-grenade",
			"01_01-grenade",
			"01_02-grenade_Mario",
			"01_03-grenade_Mario",
			"01_04-grenade_addObject",
			"01_05-grenade_load_save"
			};

	private void testN(int n) {
		String mapa = FILE_PREFIXES[n].substring(0, 2);
		TestsUtils.parameterizedTest(Paths.get(DIR + FILE_PREFIXES[n] + "_input.txt"), 
				          Paths.get(DIR + FILE_PREFIXES[n] + "_expected.txt"),
				          Paths.get(DIR + FILE_PREFIXES[n] + "_output.txt"),
				new String[] { mapa, "NO_COLORS" });
	}
	
	@Test
	public void testGR_00() { testN(0); }
	@Test
	public void testGR_01() { testN(1); }
	@Test
	public void testGR_02() { testN(2); }
	@Test
	public void testGR_03() { testN(3); }
	@Test
	public void testGR_04() { testN(4); }
	@Test
	public void testGR_05() { testN(5); }
}
