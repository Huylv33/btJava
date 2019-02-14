import java.io.File;
import java.util.ArrayList;

public class ReadDir {
	private String nameDir;
	private ArrayList<File> files = new ArrayList<File>();
	private ArrayList<Class> classes = new ArrayList<Class>();
	private ArrayList<String> paths = new ArrayList<String>();
	public ReadDir(){};
	public ReadDir(String nameDir){
		this.nameDir = nameDir;
	}
	public void setNameDir(String s){
		nameDir = s;
	}
	public String getNameDir(){
		return nameDir;
	}
	public void ReadADir(){
		File dir = new File(nameDir);
		File[] javaFiles = dir.listFiles(new JavaFileFilter());
		for (File javaFile: javaFiles){
			files.add(javaFile);
		}
	}
	public void setPaths(){
		for (File file: files){
			paths.add(file.getAbsolutePath());
		}
	}
	public ArrayList<String> getPaths(){
		return paths;
	}
	public ArrayList<File> getFiles(){
		return files;
	}
	public void setClasses(){
		for (int i = 0; i < files.size(); i++){
			Analyze obj = new Analyze(files.get(i));
			obj.analyze();
			classes.add(obj.getAClass());
		}
	}
	public ArrayList<Class> getClasses(){
		return classes;
	}
	public void printClassInforInADir(){
		for (int i = 0; i < classes.size(); i++){
			System.out.println(classes.get(i).toString());
		}
		
//		ReadADir();
//		for (int i = 0; i < files.size(); i++){
//			Analyze obj = new Analyze(files.get(i));
//			obj.analyze();
//			System.out.println(obj.getAClass().toString());
//			System.out.println("--------------------------------");
//		}
	}
}
