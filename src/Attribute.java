public class Attribute {

	private String nameAttribute;
	private String typeAttribute;
	private String value; 
	private String accessModifer; 
	private boolean isStatic;
	private boolean isFinal;
	
	public Attribute() {
		// TODO Auto-generated constructor stub
		isStatic = false;
		isFinal = false;
	}
	public void setNameAttribute(String s){
		nameAttribute = s;
	}
	public String getNameAttrbute(){
		return nameAttribute;
	}
	public void setTypeAttribute(String s){
		typeAttribute = s;
	}
	public String getTypeAttribute(){
		return typeAttribute;
	}
	public void setValue(String s){
		value = s;
	}
	public String getValue(){
		return value;
	}
	public void setAccessModifer(String am){
		accessModifer = am;
	}
	public String getAccessModifer(){
		return accessModifer;
	}
	public void setIsStatic(boolean b){
		isStatic = b;
	}
	public boolean getIsStatic(){
		return isStatic;
	}
	public void setIsFinal(boolean b){
		isFinal = b;
	}
	public boolean getIsFinal(){
		return  isFinal;
	}
	public String toString(){
		String s = " ";
		s += nameAttribute + ": ";
		s += typeAttribute;
		if (!value.equals(""))
			s += " = " + value;
		return s;
	}
	
}
