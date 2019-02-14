import java.util.ArrayList;
public class Parameter {
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> type = new ArrayList<String>();
	public Parameter() {
		// TODO Auto-generated constructor stub
	}
	public void addName(String s){
		name.add(s);
	}
	public ArrayList<String> getName(){
		return name;
	}
	public void addType(String s){
		type.add(s);
	}
	public ArrayList<String> getType(){
		return type;
	}
	public String toString(){
		String parameter = "";
		for (int i = 0; i < name.size(); i++){
			if (!name.get(i).equals(" ")){
				parameter += name.get(i) + ": " + type.get(i);
				if (i != name.size() - 1) parameter += ", ";
			}
		}
		return parameter;
	}
}
