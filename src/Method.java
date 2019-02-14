
public class Method {
	private String nameMethod;
	private String accessModifer;
	private String typeMethod;
	private boolean isConstructor;
	private boolean isAbstract;
	private boolean isStatic;
	private boolean isFinal;
	private Parameter parameter;

	public Method(){
		isConstructor = false;
		isAbstract = false;
		isStatic = false;
		isFinal = false;
		parameter = new Parameter();
	}
	public void setNameMethod(String name){
		nameMethod = name;
	}
	public String getNameMethod(){
		return nameMethod;
	}
	public void setTypeMethod(String type){
		typeMethod = type;
	}
	public String getTypeMethod(){
		return typeMethod;
	}
	public void setAccessModifer(String am){
		accessModifer = am;
	}
	public String getAccessModifer(){
		return accessModifer;
	}
	public void setIsConstructor(boolean a){
		isConstructor = a;
	}
	public boolean getIsConstructor(){
		return isConstructor;
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
	/*public void addPName(String name){
		this.pname.add(name);
	}
	public void addType(String type){
		this.pname.add(type);
	}*/
	public void setParameter(Parameter p){
		parameter = p;
	}
	public Parameter getParameter(){
		return parameter;
	}
	public String toString(){
		String method = "";
		String access = "";
		if (accessModifer.equals("public")) access = "+";
		else if (accessModifer.equals("private")) access = "-";
		else if (accessModifer.equals("protected")) access = "#";
		else access = "~";
		
		method += access + " ";
//		if (isAbstract) method += "abstract ";
//		if (isStatic) method += "static ";
//		if (isFinal) method += "final ";
		method += nameMethod;
		method += "(" + parameter.toString() + ")";
		if (!typeMethod.equals(" "))
		method += ": " + typeMethod;
		return method;
	}
}
