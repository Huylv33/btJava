import java.io.File;
import java.io.FileFilter;
public class JavaFileFilter implements FileFilter{
	public boolean accept(File pathname) {
		if (!pathname.isFile()){
			return false;
		}
		
		if (pathname.getAbsolutePath().endsWith(".java")) {
			return true;
		}
		return false;
	}
}
