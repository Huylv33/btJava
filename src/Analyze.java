import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
public class Analyze {	// Phan tich 1 Class
	private Class aClass;
	private File file;
	private ArrayList<String> listLine = new ArrayList<String>(); // Danh sach cac dong lenh
	public Analyze() {
		// TODO Auto-generated constructor stub
		aClass = new Class();
	}
	public Analyze(File file){
		this.file = file;
		aClass = new Class();
	}
	public void setFile(File file){
		this.file = file;
	}
	public File getFile(){
		return file;
	}
	public Class getAClass(){
		return aClass;
	}
	public void removeComment(){ // Khu comment tao danh sach cac dong lenh
		String path = file.getAbsolutePath();
		try {
			RandomAccessFile rd = new RandomAccessFile(new File(path), "r");
			boolean isInWildCard = false;
			while (rd.getFilePointer() < rd.length()) {
				//////////
				String l = rd.readLine();
				l = l.trim();
				if (isInWildCard) {
					int indexECmt1 = l.indexOf("*/");
					if (indexECmt1 == -1)
						continue;
					l = l.substring(indexECmt1 + 2);
					isInWildCard = false;
				}

				int indexCmt2 = l.indexOf("//");
				if (indexCmt2 >= 0) {
					l = l.substring(0, indexCmt2);
				}
				while (true) {
					
					int StartCmt1 = l.indexOf("/*");
					int EndCmt1 = l.indexOf("*/");
					if (StartCmt1 >= 0 && EndCmt1 >= 0) {
						l = l.substring(0, StartCmt1) + l.substring(EndCmt1 + 2);
					} 
					else if (StartCmt1 >= 0 && EndCmt1 == -1) 
					{
						l = l.substring(0, StartCmt1);
						isInWildCard = true;
					}
				
					break;
				}
				if (l != null && !l.isEmpty()) {
					listLine.add(l);
				}
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String repairLine(String s){ // sua lai 1 dong code, ham nay su dung rat it:)
		String ss = "";
		String[] word = s.split("\\s+");
		for (int i = 0; i < word.length; i++){
			int len = word[i].length();
			char c = word[i].charAt(len - 1);
			char c1 = word[i].charAt(0);
			if (word[i].length() > 1){
				if (c == ';') word[i] = word[i].substring(0, len - 1) + " ;";
				else if (c == ',') word[i] = word[i].substring(0, len - 1) + " ,";
				else if (c == '{') word[i] = word[i].substring(0, len - 1) + " {";
				if (c1 == ',') word[i] = ", " + word[i].substring(1);
				else if (word[i].contains(",")){
					int num = word[i].indexOf(",");
					word[i] = word[i].substring(0, num) + " , " + word[i].substring(num + 1);
				}
			}
		}
		for (int i = 0; i < word.length; i++)
			ss = ss + word[i] + " ";
		return ss;
	}
	public void analyzeClassName(){ // Phan tich ten Class
		boolean analysed = false; // kiem tra xem tim thay ten class chua
		int len = listLine.size();
		for (int i = 0; i < len; i++){
			if (analysed) break;
			String[] words = listLine.get(i).split("\\s+");
			for (int j = 0; j < words.length; j++){
				if (words[j].equals("class") || words[j].equals("interface")){
					analysed = true;
					String ss = repairLine(listLine.get(i));
					String[] word = ss.split("\\s+");
					aClass.setNameClass(word[j + 1]);
					if (word[j].equals("interface")) aClass.setIsInterface(true);
					int k = j - 1;
					if (k == -1) aClass.setAccess("package");// muc truy cap  package
					while (k >= 0){
						switch (word[k]){
						case "public" : aClass.setAccess("public");break;
						case "private" : aClass.setAccess("private");break;
						case "protected" : aClass.setAccess("protected");break;
						case "abstract" : aClass.setIsAbstract(true);break;
						case "static" : aClass.setIsStatic(true);break;
						case "final" : aClass.setIsFinal(true);break;
						}
						k--;
					}
					int d = j + 1;
					while (d < word.length){
						switch (word[d]){
						case "extends": 
							aClass.setIsExtends(true);
							aClass.setParent(word[d + 1]);
							break;
						case "implements":
							aClass.setIsImplements(true);
							String s = repairLine(listLine.get(i));
							String[] list = s.split("\\s+");
							aClass.addSource(list[d + 1]);
							if (list[d + 2].equals(",")) aClass.addSource(list[d + 2]);
							break;
						}
						
						d++;
					}
				}
	
			}
			
		}
	}
	public boolean isAttribute(String s){ // dieu kien la phuong thuc, cac dieu kien nay khong day du 
		if (s.endsWith(";")){             	// nhung vi toi chi phan tich nhung dong ngay duoi ten class, 
			if (s.contains("=")) return true;// den khi co phuong thuc thi khong phan tich nua
			else 
				if (!s.contains("{") && !s.contains("(")) return true;
		}
		return false;
	}
	public void analyzeAttribute(){ // Ham phan tich thuoc tinh
		int i = 0;
		int len = listLine.size();
		while (i < len){
			String[] sublist;
			String sss = listLine.get(i).trim();
			sublist = sss.split("\\s+");
			int temp = 0;
			for (int ii = 0; ii < sublist.length; ii++){
				if (sublist[ii].equals("abstract")){
					temp = 1;
					break;
				}
				if (sublist[ii].equals("interface")){
					temp = 2;
					break;
				}
				if (sublist[ii].equals("class")) {
					temp = 3;
					break;
				}
			}
			
			if (temp != 0){
				while (true){
					i++;
					String ssss = listLine.get(i).trim();
					if (isAttribute(ssss)){
						Attribute at = new Attribute();
						if (temp == 2){
							at.setIsFinal(true);
							at.setIsStatic(true);
							at.setAccessModifer("public");
						}
						sublist = listLine.get(i).split("\\s+");
						int dem = 0;
						if (sublist[0].equals("public") || sublist[0].equals("private") || sublist[0].equals("protected")){
							dem ++;
							at.setAccessModifer(sublist[0]);
						}
						else at.setAccessModifer("package");
						if (sublist[dem].equals("static ")){
							dem++;
							at.setIsStatic(true);
						}
						if (sublist[dem].equals("final")){
							dem++;
							at.setIsFinal(true);
						}
						//
						if (listLine.get(i).contains("=")){
							int index1 = listLine.get(i).indexOf("=");
							at.setValue(listLine.get(i).substring(index1 + 1, listLine.get(i).length() - 1));
						}
						else at.setValue("");
						//
						
						if (listLine.get(i).contains("=")){
							
							for (int j = dem; j < sublist.length; j++){
								if (sublist[j].equals("=")){
									String s = sublist[j - 1];
									int index1 = 0;
									if (s.contains(">"))
										{
											index1 = s.indexOf(">");
										}
									int index2 = s.length();
									at.setNameAttribute(s.substring(index1, index2));
								}
								else if (sublist[j].contains("=") && sublist[j].indexOf("=") == 0){
									String s;
									 s = sublist[j - 1];
									 int index1 = 0;
									 if (s.contains(">"))
									 index1 = s.indexOf(">");
									 int index2 = s.length();
									 at.setNameAttribute(s.substring(index1, index2));
								
								}
								else if (sublist[j].contains("=") && sublist[j].indexOf("=") != 0){
									String s = sublist[j];
									int index1 = 0;
									if (s.contains(">"))
										index1 = s.indexOf(">");
									int index2 = s.indexOf("=");
									at.setNameAttribute(s.substring(index1, index2));
								}
							}
						}
						else {
							if (sublist[sublist.length - 1].equals(";")){
								String s = (sublist[sublist.length - 2]);
								int index1 = 0;
								if (s.contains(">"))
									 index1 = s.indexOf(">") + 1;
								int index2 = s.length();
								at.setNameAttribute(s.substring(index1, index2));
							}
							else {
								String s = sublist[sublist.length - 1];
								int index1 = 0;
								if (s.contains(">"))
									 index1 = s.indexOf(">") + 1;
								int index2 = s.length() - 1;
							
								at.setNameAttribute(s.substring(index1, index2));
								
							}
						}
						//
							if (listLine.get(i).contains(">")){
								int index2 = listLine.get(i).indexOf(">");
								int index1 = listLine.get(i).indexOf(sublist[dem].charAt(0));
								String str = listLine.get(i).substring(index1, index2 + 1);
								at.setTypeAttribute(str);
								int indexStartType = str.indexOf("<");
								int indexEndType = str.indexOf(">");
								String str2 = str.substring(indexStartType + 1, indexEndType);
								str2.trim();
								if (!str2.equals("int")
										&& !str2.equals("float")
										&& !str2.equals("long")
										&& !str2.equals("double")
										&& !str2.equals("short")
										&& !str2.equals("char")
										&& !str2.equals("boolean")
										&& !str2.equals("byte")
										&& !str2.equals("String")
										&& !str2.equals("Byte")
										&& !str2.equals("Double")
										&& !str2.equals("Integer")
										&& !str2.equals("Short")
										&& !str2.equals("Float")
										&& !str2.equals("Long")
										&& !str2.equals("File"))
										{
											aClass.addHasA(str2);
											aClass.setIsHasA(true);
										}
							}
							else {
								at.setTypeAttribute(sublist[dem]);
								String str2;
								if (sublist[dem].contains("[")){
									str2 = sublist[dem].substring(0, sublist[dem].indexOf("["));
								}
								else str2 = sublist[dem];
								if (!str2.equals("int")
										&& !str2.equals("float")
										&& !str2.equals("long")
										&& !str2.equals("double")
										&& !str2.equals("short")
										&& !str2.equals("char")
										&& !str2.equals("boolean")
										&& !str2.equals("byte")
										&& !str2.equals("String")
										&& !str2.equals("Byte")
										&& !str2.equals("Double")
										&& !str2.equals("Integer")
										&& !str2.equals("Short")
										&& !str2.equals("Float")
										&& !str2.equals("Long")
										&& !str2.equals("File"))
									{
										aClass.addHasA(str2);
										aClass.setIsHasA(true);
									}
							}
							aClass.addAttribute(at);
						}
						else break; 
						}
						break;
					}
			
					i++;
			}
	
	}
	public boolean isMethod(String s){ // Dieu kien la phuong thuc, ham nay la tu code cu nen rat do
		String nameClass = aClass.getNameClass();
		int len = s.length();
		String[] list = s.split("\\s+");
		if (s.contains("(") && s.contains(")") && s.charAt(len-1) != ';'){
			if (s.contains("+") || s.contains("-") || s.contains("*") || s.contains("/")|| s.contains("\""))
					return false;
			if (list[0].equals("else")) return false;
			if (list[0].charAt(0) == '}') return false;
			if (list.length > 1 )
				if (list[0].contains(nameClass) || list[1].contains(nameClass)) return true;
			if (list.length == 1)
				if (list[0].contains(nameClass)) return true;
			for (int i = 0; i < list.length; i++){
				if (list[i].equals("public") || list[i].equals("protected") || list[i].equals("private"))
					return true;
			if (list.length > 1)
			if (!list[0].contains("(") && list[1].charAt(0) != '(') return true; else return false;
			}
		}
		else
		if (s.contains("(") && s.contains(")") && s.charAt(len-1) == ';'){
			if (s.contains("()") && !s.contains("=") && !s.contains(".") && list.length >= 2) return true;
			if (s.contains("{") && s.contains("}")){
				if (list.length > 1)
					if (list[0].contains(nameClass) || list[1].contains(nameClass)) return true;
				if (list.length == 1)
					if (list[0].contains(nameClass)) return true;
				for (int i = 0; i < list.length; i++){
					if (list[i].equals("public") || list[i].equals("protected") || list[i].equals("private"))
						return true;
				if (list.length > 1)
				if (!list[0].contains("(") && list[1].charAt(0) != '(') return true; else return false;
				}
			}
			
		}
		
		return false;
	}

	public void analyzeMethod(){
		String nameClass = aClass.getNameClass();
		int index = listLine.size();
		int i = 0;
		while (i < index){  
			String line1 = listLine.get(i).trim();
			if (isMethod(line1)){
				Method mt = new Method();
				if (aClass.getIsInterface()){
					mt.setAccessModifer("public");
					mt.setIsAbstract(true);
					
				}
				String ss = "";
				if (line1.contains("{")){
					ss = ss + line1.substring(0, line1.indexOf("{"));
				}
				else ss = line1;
				String[] list;
				list = ss.split("\\s+");
				int dem1 = 0;	
				if (list[0].equals("public") || list[0].equals("private") || list[0].equals("protected")){
					dem1++;
					mt.setAccessModifer(list[0]);
				}
				else if (!aClass.getIsInterface())
					mt.setAccessModifer("package");
				if (list[dem1].equals("abstract")) {
					dem1++;
					mt.setIsAbstract(true);
				}
				if (list[dem1].equals("static")){
					dem1++;
					mt.setIsStatic(true);
				}
				if (list[dem1].equals("final")){
					dem1++;
					mt.setIsFinal(true);
				}
				if (list[dem1].contains(nameClass) && list[dem1].contains("(")){
					mt.setTypeMethod(" ");
				}
				else if (list[dem1].equals(nameClass)) {
					mt.setTypeMethod(" ");
				}
				else {
					int flag = dem1;
//					String str1 = list[flag];
//					for (int j = dem1; j < list.length; j++){
//						if (list[j].contains(">") || (list[j].contains("]") && j > dem1 + 2))
//						{flag = j; str1 = list[flag];break;}
//					}
//					String str2 = list[dem1];
//					for (int j = dem1 + 1; j < flag; j++) str2 = str2 + list[j] + " ";
//					if (list[flag].contains(">")) str2 = str2 + list[flag].substring(0, list[flag].indexOf(">") + 1);
//					if (list[flag].contains("]")) str2 = str2 + list[flag].substring(0, list[flag].indexOf("]") + 1);
//					mt.setTypeMethod(str2);
//				}
//				for (int j = list.length - 1; j >= dem1; j--){
//					if (list[j].contains("(") && list[j].indexOf("(") == 0)
//						{
//						mt.setNameMethod(list[j - 1]);
//						
//						break;
//						}
//					else if (list[j].contains("(") && list[j].indexOf("(") != 0)
					//String _line1 = line1.substring(0, line1.indexOf(")"));
					int in = line1.indexOf("(");
					String line = line1.substring(0, in);
					String[] word = line.split("\\s+");
					String str1 = list[flag];
					for (int j = dem1; j < word.length; j++){
						if (word[j].contains(">") || (word[j].contains("]") && j > dem1 + 2))
						{flag = j; str1 = word[flag];break;}
					}
					String str2 = list[dem1];
					for (int j = dem1 + 1; j < flag; j++) str2 = str2 + list[j] + " ";
					//if (word[flag].contains(">")) str2 = str2 + word[flag].substring(0, word[flag].indexOf(">") + 1);
					if (word[flag].contains("]")) str2 = str2 + word[flag].substring(0, word[flag].indexOf("]") + 1);
					mt.setTypeMethod(str2);
				}
				//
				for (int j = list.length - 1; j >= dem1; j--){
					if (list[j].contains("(") && list[j].indexOf("(") == 0)
						{
						mt.setNameMethod(list[j - 1]);	
						break;
						}
					else if (list[j].contains("(") && list[j].indexOf("(") != 0)
						{
						mt.setNameMethod(list[j].substring(0, list[j].indexOf("(")));
						break;
						}
				}
				Parameter pr = new Parameter();
				int index1 = line1.indexOf("(");
				int index2 = line1.indexOf(")");
				String str = line1.substring(index1 + 1, index2);
				if (str.equals("")) {
					pr.addName(" ");
					pr.addType(" ");
				}
				else {
					String s = repairLine(str);
					String[] word =  s.split("\\s+");
					for (int count = 0; count < word.length; count++){
						if (count % 3 == 0)
							pr.addType(word[count]);
						
						if (count % 3 == 1)
							pr.addName(word[count]);
					}
				}
				mt.setParameter(pr);
				aClass.addMethod(mt);
			}
			i++;
		}
	
	}
	public void analyze(){
		this.removeComment();
		this.analyzeClassName();
		this.analyzeAttribute();
		this.analyzeMethod();
	}
//	public static void main(String[]Args){
//		File file = new File("C:\\Users\\Dell\\Downloads\\analyzer_nhom_thanh\\analyzer\\ClassMethod.java");
//		Analyze obj = new Analyze(file);
//		obj.removeComment();
//		obj.analyzeClassName();
//		obj.analyzeAttribute();
//		obj.analyzeMethod();
////		ArrayList<Method> al = obj.getAClass().getMethod();
////		for (int i = 0; i < al.size(); i++){
////			System.out.println(al.get(i));
////		}
//		System.out.println(obj.getAClass().toString());
//	}
	
}
