import java.util.ArrayList;

public class Class {
	private String nameClass;
	private ArrayList<Attribute> attribute = new ArrayList<Attribute>();
	private ArrayList<Method> method = new ArrayList<Method>();
	private String access = "";
	private boolean isAbstract = false;
	private boolean isStatic = false;
	private boolean isFinal = false;
	public boolean isExtends = false;
	private boolean isImplements = false;
	private boolean isInterface = false;
	private boolean isHasA = false; 
	private ArrayList<String> HasA = new ArrayList<String>(); 
	private String parent = ""; 
	private ArrayList<String> sources = new ArrayList<String>(); 
	public Class() {
		// TODO Auto-generated constructor stub
	}
	public void setNameClass(String name){
		nameClass = name;
	}
	public String getNameClass(){
		return nameClass;
	}
	public void setIsAbstract(boolean a){
		isAbstract = a;
	}
	public boolean getIsAbstract(){
		return isAbstract;
	}
	public void setIsStatic(boolean a){
		isStatic = a;
	}
	public boolean getIsStatic(){
		return isStatic;
	}
	public void setIsFinal(boolean a){
		isFinal = a;
	}
	public boolean getIsFinal(){
		return isFinal;
	}
	public void setIsInterface(boolean s){
		isInterface = s;
	}
	public boolean getIsInterface(){
		return isInterface;
	}
	public void setIsExtends(boolean s){
		isExtends = s;
	}
	public boolean getIsExtends(){
		return isExtends;
	}
	public void setIsImplements(boolean s){
		isImplements = s;
	}
	public boolean getIsImplements(){
		return isImplements;
	}
	public void setIsHasA(boolean s){
		isHasA = s;
	}
	public boolean getIsHasA(){
		return isHasA;
	}
	public void addHasA(String s){
		HasA.add(s);
	}
	public ArrayList<String> getHasA(){
		ArrayList<String> has_a = new ArrayList<String>();// chua cac ten khong trung nhau
		int size = HasA.size();
		for (int i = 0; i < size; i++){
			int j = i - 1;
			for (; j >=0; j--){
				if (HasA.get(i).equals(HasA.get(j))){
					break;
				}
			}
			if (j == -1) has_a.add(HasA.get(i));
		}
		return has_a;
	}
	public void setAccess(String s){
		access = s;
	}
	public String getAccess(){
		return access;
	}
	public void addAttribute(Attribute a){
		attribute.add(a);
	}
	public ArrayList<Attribute> getAttribute(){
		return attribute;
	}
	public void addMethod(Method a){
		method.add(a);
	}
	public ArrayList<Method> getMethod(){
		return method;
	}
	public void addSource(String a){
		sources.add(a);
	}
	public ArrayList<String> getSources(){
		return sources;
	}
	public void setParent(String e){
		parent = e;
	}
	public String getParent(){
		return parent;
	}
	public String toString(){// In thong tin day du cua 1 lop
		String s = " "+ showName() + "\n";
//		if (!access.equals(""))
//			s += access + " ";
//		if (isInterface) s += "interface ";
//		if (isAbstract){
//			s += "abstract ";
//		}
//		if (isStatic) s += "static ";
//		if (isFinal) s += "final ";
//		s += nameClass + " ";
//		if (isExtends) {
//			s += "extends ";
//			s += parent + " ";
//		}
//		if (isImplements){
//			s += "implements ";
//			for (int i = 0; i < sources.size() - 1; i++)
//				s += sources.get(i) + ", ";
//			s += sources.get(sources.size() - 1);
//		}
		s += showAttributes() + "\n" + showMethods() + "\n";		
		return s;
	}
	public String showAttributes(){	// In thong in cac thuoc tinh
		String s = "";
		for (int i = 0; i < attribute.size(); i++)
			s = s + attribute.get(i).toString() + "\n";
		return s;
		
	}
	public String showMethods(){ // In thong tin cac phuong thuc
		String s = "";
		for (int i = 0; i < method.size(); i++)
			s += method.get(i).toString() + "\n";
		return s;
	}
	public String showName(){// In ten Class
		return nameClass;
	}
}

