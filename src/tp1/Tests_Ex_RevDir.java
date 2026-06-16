package tp1;

import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests_Ex_RevDir {
	public static final String DIR = "tests/ex_revDir/";
	public static final String FILE_PREFIXES[] = {
			"01_00-commands"
			};

	private void testN(int n) {
		String mapa = FILE_PREFIXES[n].substring(0, 2);
		TestsUtils.parameterizedTest(Paths.get(DIR + FILE_PREFIXES[n] + "_input.txt"), 
				          Paths.get(DIR + FILE_PREFIXES[n] + "_expected.txt"),
				          Paths.get(DIR + FILE_PREFIXES[n] + "_output.txt"),
				new String[] { mapa, "NO_COLORS" });
	}
	
	@Test
	public void test00_Command() { testN(0); }
}
