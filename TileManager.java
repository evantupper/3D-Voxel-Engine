import java.util.ArrayList;

public class TileManager {
	public static void run() {
		ArrayList<ArrayList<Integer>> tilemap = new ArrayList<>();
		
		for (int i = 0; i < 20; i++)
			tilemap.add(new ArrayList());

		
		for (int i = 0; i < 20; i++) 
			for (int j = 1; j < 21; j++) 
				tilemap.get(i).add(0);
		
		
		tilemap.get(7).set(7,1);
		
		tilemap.get(0).set(1, 1);
		tilemap.get(0).set(2, 1);
		tilemap.get(0).set(3, 1);
		tilemap.get(0).set(4, 1);
		tilemap.get(0).set(5, 1);
		tilemap.get(0).set(6, 1);
		tilemap.get(0).set(7, 1);
		tilemap.get(0).set(8, 1);
		tilemap.get(0).set(9, 1);
		tilemap.get(0).set(10, 1);
		tilemap.get(0).set(11, 1);
		tilemap.get(0).set(12, 1);
		tilemap.get(0).set(13, 1);
		tilemap.get(0).set(14, 1);
		tilemap.get(0).set(15, 1);
		tilemap.get(0).set(16, 1);
		tilemap.get(0).set(17, 1);
		tilemap.get(0).set(18, 1);
		tilemap.get(0).set(19, 1);
		
		tilemap.get(18).set(1, 1);
		tilemap.get(18).set(2, 1);
		tilemap.get(18).set(3, 1);
		tilemap.get(18).set(4, 1);
		tilemap.get(18).set(5, 1);
		tilemap.get(18).set(6, 1);
		tilemap.get(18).set(7, 1);
		tilemap.get(18).set(8, 1);
		tilemap.get(18).set(9, 1);
		tilemap.get(18).set(10, 1);
		tilemap.get(18).set(11, 1);
		tilemap.get(18).set(12, 1);
		tilemap.get(18).set(13, 1);
		tilemap.get(18).set(14, 1);
		tilemap.get(18).set(15, 1);
		tilemap.get(18).set(16, 1);
		tilemap.get(18).set(17, 1);
		tilemap.get(18).set(18, 1);
		tilemap.get(18).set(19, 1);
		
		tilemap.get(7).set(9, 1);
		tilemap.get(8).set(9, 1);
		tilemap.get(9).set(9, 1);
		tilemap.get(10).set(9, 1);
		tilemap.get(11).set(9, 1);
		
		tilemap.get(1).set(5, 1);
		tilemap.get(2).set(5, 1);
		tilemap.get(3).set(5, 1);
		tilemap.get(4).set(5, 1);
		tilemap.get(5).set(5, 1);
		tilemap.get(6).set(5, 1);
		tilemap.get(7).set(5, 1);
		tilemap.get(8).set(5, 1);
		tilemap.get(9).set(5, 1);
		tilemap.get(11).set(5, 1);
		tilemap.get(12).set(5, 1);
		tilemap.get(13).set(5, 1);
		tilemap.get(14).set(5, 1);
		tilemap.get(15).set(5, 1);
		tilemap.get(16).set(5, 1);
		tilemap.get(17).set(5, 1);
		tilemap.get(18).set(5, 1);
		tilemap.get(19).set(5, 1);
		
		tilemap.get(2).set(6, 2);
		tilemap.get(1).set(6, 2);
		tilemap.get(1).set(7, 2);
		tilemap.get(2).set(7, 2);
		tilemap.get(1).set(8, 2);
		tilemap.get(2).set(8, 2);
		tilemap.get(1).set(9, 2);
		tilemap.get(2).set(9, 2);
		
		tilemap.get(4).set(6, 2);
		tilemap.get(3).set(6, 2);
		tilemap.get(4).set(7, 2);
		tilemap.get(3).set(7, 2);
		tilemap.get(4).set(8, 2);
		tilemap.get(3).set(8, 2);
		tilemap.get(4).set(9, 2);
		tilemap.get(3).set(9, 2);
		
		tilemap.get(5).set(6, 2);
		tilemap.get(6).set(6, 2);
		tilemap.get(5).set(7, 2);
		tilemap.get(6).set(7, 2);
		tilemap.get(5).set(8, 2);
		tilemap.get(6).set(8, 2);
		tilemap.get(5).set(9, 2);
		tilemap.get(6).set(9, 2);
		
		tilemap.get(5).set(10, 2);
		tilemap.get(6).set(10, 2);
		tilemap.get(5).set(11, 2);
		tilemap.get(6).set(11, 2);
		tilemap.get(5).set(12, 2);
		tilemap.get(6).set(12, 2);
		tilemap.get(5).set(13, 2);
		tilemap.get(6).set(13, 2);
		
		tilemap.get(1).set(10, 2);
		tilemap.get(2).set(10, 2);
		tilemap.get(1).set(11, 2);
		tilemap.get(2).set(11, 2);
		tilemap.get(1).set(12, 2);
		tilemap.get(2).set(12, 2);
		tilemap.get(1).set(13, 2);
		tilemap.get(2).set(13, 2);
		
		tilemap.get(3).set(10, 2);
		tilemap.get(4).set(10, 2);
		tilemap.get(3).set(11, 2);
		tilemap.get(4).set(11, 2);
		tilemap.get(3).set(12, 2);
		tilemap.get(4).set(12, 2);
		tilemap.get(3).set(13, 2);
		tilemap.get(4).set(13, 2);
		
		tilemap.get(5).set(7, 1);
		
		
		
		
		
		for (int i = 0; i < tilemap.size(); i++) {
			for (int j = 0; j < tilemap.get(i).size(); j++)
				if (tilemap.get(i).get(j)==1) {
					Main.Shapes.add(new Shape(50,50,i*100-900,j*100-900,1));
				}
				else if (tilemap.get(i).get(j)==2)
					Main.Shapes.add(new Shape(50,50,i*100-900,j*100-900,2));
		}
		
		
	}
}
