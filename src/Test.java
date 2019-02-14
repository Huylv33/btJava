import java.io.File;
import java.util.*;

import javax.print.DocFlavor.STRING;
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nameDir = "D:\\Document\\Nguyen_Viet_Thang_16020048_Le_Van_Huy_16020996\\Nguyen_Viet_Thang_16020048_Le_Van_Huy_16020996\\thu_muc_thu_nghiem";
		ReadDir oj = new ReadDir(nameDir);
		oj.ReadADir();
		oj.setClasses();
		oj.printClassInforInADir();
		// System.out.println("----------------------------------------------");
		ArrayList<Class> cl = new ArrayList<Class>();
		ArrayList<String> classDetail = new ArrayList<String>();
		cl = oj.getClasses();
		System.out.println(cl.size());
		int dem = 0;
		for (int i = 0; i < cl.size(); i++){
			dem++;
			System.out.println(dem);
			classDetail.add(cl.get(i).toString());
			if (cl.get(i).getIsExtends()){
				System.out.print("Lop " + cl.get(i).getNameClass() + " la lop con cua lop ");
				String s = cl.get(i).getParent();
				System.out.println(s);
				System.out.println("Thong tin lop cha");
				for (int j = 0; j < cl.size(); j++){
					if (cl.get(j).getNameClass().equals(s)){
						System.out.println(cl.get(j).toString());
					}
				}
				
			}
			else 
			if (cl.get(i).getIsImplements()){
				System.out.println("Lop " + cl.get(i).getNameClass() + " la lop thuc thi cua inteface ");
				ArrayList<String> inter = new ArrayList<String>();
				inter = cl.get(i).getSources();
				System.out.println("Cac interface la");
				for (String s: inter){
					System.out.println(s);
				}

			}
			else 
			if (cl.get(i).getIsHasA()){
				
				System.out.println("Lop " + cl.get(i).getNameClass() + " co chua cac lop ");
				ArrayList<String> has_a = new ArrayList<String>();
				has_a = cl.get(i).getHasA();
				for (String s: has_a){
					System.out.println(s);
				}
			
			}
			else {
				System.out.println(cl.get(i).getNameClass());
			
			}
			System.out.println("------------------------");
		}
		
		Gui gui = new Gui();
		gui.draw(cl, classDetail);
	}
	
}
