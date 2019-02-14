import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import static java.awt.geom.AffineTransform.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;



import java.util.*;

class MyCanvas extends JComponent {
	Rectangle[] rect;
	int[] p;
	int cntRect;
	ArrayList<String> classDetail;
   	MyCanvas(Rectangle[] blocks, int cnt, ArrayList<String> cDetail, int[] parent) {
		rect = new Rectangle[cnt];
		classDetail = new ArrayList<String>();  
		classDetail = cDetail;
		p = new int[cnt];
		for (int i = 0; i < cnt; i++) p[i] = parent[i];
      	for (int i = 0; i < cnt; i++) rect[i] = blocks[i];
        cntRect = cnt;
   	}
	public void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();
		
		
		int ARR_SIZE = 4;

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx*dx + dy*dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
					  new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
	}   
	public void paint(Graphics g) {
		  // g.drawRect (x, y, w, h);  
		g.setFont(new Font("TimesRoman", Font.PLAIN, 9)); 
      	for (int i = 0; i < cntRect; i++) {
			g.drawRect(rect[i].x, rect[i].y, rect[i].height, rect[i].width);
			int curX = rect[i].x;
			int curY = rect[i].y + 9;
			String[] tmp = classDetail.get(i).split("\n");
			for (int j = 0 ; j < tmp.length; j++) {
				g.drawString(tmp[j], curX + 5, curY);
				curY += 10;
			}
		}
		for (int i = 0; i < cntRect; i++) {
			if (p[i] != -1) {
				int cx, cy, px, py;
				int j = p[i];
				cx = rect[i].x + rect[i].height / 2;
				cy = rect[i].y;
				px = rect[j].x + rect[j].height / 2;
				py = rect[j].y + rect[j].width;
				drawArrow(g, cx, cy, px, py);
			}
		}
   }
}

public class Gui {
	public int getHeight(String t) {
		int ans = 0;
		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) == '\n') ans++;
		}
		return ans * 10;
	}
	public int getWidth(String t) {
		int pre = 0;
		int ans = -1;
		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) == '\n') {
				if (ans < i - pre - 1) ans = i - pre - 1;
				pre = i;
			}
		}
		return (int)(ans * 5.3);
	}

	public int getLayerExtend(ArrayList<Class> allClass, int id) {
		int ans = 0;
		
		if (allClass.get(id).getIsExtends()) {
			String nameParent = allClass.get(id).getParent();
			for (int i = 0; i < allClass.size(); i++){
				if (allClass.get(i).getNameClass().equals(nameParent)){
					ans = getLayerExtend(allClass, i) + 1;
				}
			}
		} else 
			ans = 0;
		
		return ans;		
		
	}
	
   	public void draw(ArrayList<Class> allClass, ArrayList<String> classDetail) {
      	JFrame window = new JFrame();
      	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 1300, 700);
		
		Rectangle[] rect = new Rectangle[allClass.size()];
		/* 
		Rectangle[] rect = new Rectangle[2];
		rect[0] = new Rectangle(10, 10, 100, 100);
		rect[1] = new Rectangle(120, 10, 200, 100);
		*/
		
		int[] vLayer = new int[allClass.size()] ;
		for (int i = 0; i < allClass.size(); i++) vLayer[i] = getLayerExtend(allClass, i);

		for (int i = 0; i < allClass.size() - 1; i++) {
			for (int j = i + 1; j < allClass.size(); j++) {
				if (vLayer[i] > vLayer[j]) {
					int tmpL = vLayer[i];
					vLayer[i] = vLayer[j];
					vLayer[j] = tmpL;

					Class tmpC = allClass.get(i);
					allClass.set(i, allClass.get(j));
					allClass.set(j, tmpC);

					String tmpS = classDetail.get(i);
					classDetail.set(i, classDetail.get(j));
					classDetail.set(j, tmpS);

				}
			}
		}

		for (int i = 0; i < allClass.size(); i++) {
			if (allClass.get(i).getIsHasA()){
				
				ArrayList<String> has_a = new ArrayList<String>();
				has_a = allClass.get(i).getHasA();
				for (String s: has_a){
					for (int j = 0; j < allClass.size(); j++) {
						if (allClass.get(j).getNameClass().equals(s)) {
							vLayer[j] = Math.max(vLayer[j], vLayer[i] + 1);
						}	
					}
				}
			
			}
		}
		int curY = 0, maxY = 10, curX = 0;
		int[] parent = new int[classDetail.size()];
		for (int i = 0; i < classDetail.size(); i++) {
			if (i == 0 || vLayer[i] != vLayer[i - 1]) {
				curY = maxY;
				curX = 0;
			}
			int h = getHeight(classDetail.get(i));
			int w = getWidth(classDetail.get(i));
			rect[i] = new Rectangle(curX + 10, curY, h, w);
			curX = curX + 10 + w;
			if (maxY < curY + h) maxY = curY + h + 50;

		} 
		for (int i = 0; i < allClass.size(); i++) {
			parent[i] = -1;	
			if (allClass.get(i).getIsExtends()) {
				String nameParent = allClass.get(i).getParent();
				for (int j = 0; j < allClass.size(); j++){
					if (allClass.get(j).getNameClass().equals(nameParent)){
						parent[i] = j;
					}
				}
			}
		}

		for (int i = 0; i < allClass.size(); i++) {
			if (parent[i] != -1) {
			System.out.println(parent[i]);
			System.out.println(allClass.get(i).getNameClass() + " " + allClass.get(parent[i]).getNameClass());
			}
		}
      	window.getContentPane().add(new MyCanvas(rect, allClass.size(), classDetail, parent));
      	window.setVisible(true);
  	}
}
