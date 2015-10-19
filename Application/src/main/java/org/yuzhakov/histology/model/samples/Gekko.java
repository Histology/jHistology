package org.yuzhakov.histology.model.samples;

import java.util.ArrayList;
import java.util.List;

import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.m2D.Base;
import org.yuzhakov.histology.model.m2D.Topology;
import org.yuzhakov.histology.model.m3D.Cell;
import org.yuzhakov.histology.model.m3D.CellPrototype;

import de.jreality.shader.Color;

public class Gekko {
	public static final double L = 2.414;
	public static final double D = L - 1;
	public static final double H = 0.5 + D;
	public static final double GISTEON_WIDTH = H*2;
	public static final double GISTEON_HEIGHT = H*4;
	public static final double EPS = 0.0001;
	
	private static final ArrayList<Double> heights;
	private static final ArrayList<Double> heightsD; 
	
	public static List<Cell> getModel(int sizeX, int sizeY){
		ArrayList<Cell> model = new ArrayList<>();
		int startX = -sizeX/2;
		int startY = -sizeY/2;
		for (int i = 0; i < sizeX; ++i){
			for (int j = 0; j < sizeY; ++j){
				double X = (startX + i)*GISTEON_WIDTH;
				double Y = (startY + j)*GISTEON_HEIGHT;
				model.addAll(getGisteon(new Vertex(X,Y,0)));
			}
		}
		return model;
	}
	
	public static List<Cell> getGisteon(Vertex offset){
		
		CellPrototype typeA = Gekko.cellTypeA();
		typeA.setColor(new Color(204,51,51));
		
		CellPrototype typeB = Gekko.cellTypeB();
		typeB.setColor(new Color(204,102,0));
		
		CellPrototype typeC1 = Gekko.cellTypeC();
		typeC1.setColor(new Color(0,104,255));
		
		CellPrototype typeC2 = Gekko.cellTypeC();
		typeC2.setColor(new Color(0,255,104));
		
		CellPrototype typeD1 = Gekko.cellTypeD();
		typeD1.setColor(new Color(204,0,0));
		
		CellPrototype typeD2 = Gekko.cellTypeD();
		typeD2.setColor(new Color(255,102,204));
		
		
		Cell cellA1 = new Cell(typeA, heights, 0, Vertex.getSumm(offset, new Vertex(0,0,0)));
		Cell cellA2 = new Cell(typeA, heights, 0, Vertex.getSumm(offset, new Vertex(0,Gekko.H*2,0)));
		Cell cellB1 = new Cell(typeB, heights, 0, Vertex.getSumm(offset, new Vertex(Gekko.H,Gekko.H,0)));
		Cell cellB2 = new Cell(typeB, heights, 0, Vertex.getSumm(offset, new Vertex(Gekko.H,-Gekko.H,0)));
		Cell cellC1 = new Cell(typeC1, heights, 0, Vertex.getSumm(offset, new Vertex(0.5,-Gekko.H,0)));
		Cell cellC2 = new Cell(typeC2, heights, 180, Vertex.getSumm(offset, new Vertex(Gekko.H+Gekko.D,-Gekko.H,0)));
		Cell cellC3 = new Cell(typeC2, heights, 0, Vertex.getSumm(offset, new Vertex(0.5,Gekko.H,0)));
		Cell cellC4 = new Cell(typeC1, heights, 180, Vertex.getSumm(offset, new Vertex(Gekko.H+Gekko.D,Gekko.H,0)));
		Cell cellD1 = new Cell(typeD1, heightsD, 0, Vertex.getSumm(offset, new Vertex(Gekko.H,0,0)));
		Cell cellD2 = new Cell(typeD2, heightsD, 0, Vertex.getSumm(offset, new Vertex(Gekko.H,Gekko.H*2,0)));

		
		ArrayList<Cell> cells = new ArrayList<>();
		cells.add(cellA1);
		cells.add(cellA2);
		cells.add(cellB1);
		cells.add(cellB2);
		cells.add(cellC1);
		cells.add(cellC2);
		cells.add(cellC3);
		cells.add(cellC4);
		cells.add(cellD1);
		cells.add(cellD2);
		return cells;
	}
	
	public static CellPrototype cellTypeA(){
		Base base0 = new Base();
		
		base0.addTopologyVertex(new Vertex(H, -0.5, 0.0)); // 0
		base0.addTopologyVertex(new Vertex(H, 0.5, 0.0)); // 1
		base0.addTopologyVertex(new Vertex((0.5 + D / 2), (0.5 + D / 2), 0.0)); // 2
		base0.addTopologyVertex(new Vertex(0.5, H, 0.0)); // 3
		base0.addTopologyVertex(new Vertex(-0.5, H, 0.0)); // 4
		base0.addTopologyVertex(new Vertex(-(0.5 + D / 2), (0.5 + D / 2), 0.0)); // 5
		base0.addTopologyVertex(new Vertex(-H, 0.5, 0.0)); // 6
		base0.addTopologyVertex(new Vertex(-H, -0.5, 0.0)); // 7
		base0.addTopologyVertex(new Vertex(-(0.5 + D / 2), -(0.5 + D / 2), 0.0)); // 8
		base0.addTopologyVertex(new Vertex(-0.5, -H, 0.0)); // 9
		base0.addTopologyVertex(new Vertex(0.5, -H, 0.0)); // 10
		base0.addTopologyVertex(new Vertex((0.5 + D / 2), -(0.5 + D / 2), 0.0)); // 11

		int[][] mapping0 = getDefaultMapping(base0.size());
		
		Base base1 =  base0;
		
		int[][] mapping1 = new int[][]{
				{0,0},
				{1,1},
				{2,2},
				{2,3},
				{3,4},
				{4,5},
				{5,6},
				{5,7},
				{6,8},
				{7,9},
				{8,10},
				{8,11},
				{9,12},
				{10,13},
				{11,14},
				{11,15},
		};
		
		Topology topology2 = new Topology(new Vertex[] {
				new Vertex(H, -0.5, 0.0), 		//0
				new Vertex(H, 0.5, 0.0), 		//1
				
				new Vertex((0.5+D/2), (0.5+D/2), 0.0), 	//2
				new Vertex((0.5+D/3), (0.5+D/3), 0.0), 	//3
				new Vertex(0.25, H, 0.0), 				//4
				
				new Vertex(-0.25, H, 0.0), 					//5
				new Vertex(-(0.5+D/3), (0.5+D/3), 0.0), 	//6
				new Vertex(-(0.5+D/2), (0.5+D/2), 0.0), 	//7
				
				new Vertex(-H, 0.5, 0.0), 		//8
				new Vertex(-H, -0.5, 0.0), 		//9
				
				new Vertex(-(0.5+D/2), -(0.5+D/2), 0.0), 	//10
				new Vertex(-(0.5+D/3), -(0.5+D/3), 0.0), 	//11
				new Vertex(-0.25, -H, 0.0), 				//12
				
				new Vertex(0.25, -H, 0.0), 					//13
				new Vertex((0.5+D/3), -(0.5+D/3), 0.0), 	//14
				new Vertex((0.5+D/2), -(0.5+D/2), 0.0), 	//15
				});
		Base base2 = new Base(topology2);
		
		int[][] mapping2 = new int[][]{
				{0,0},
				{1,1},
				{2,2},
				{3,3},
				{3,4},
				{4,5},
				{4,6},
				{5,7},
				{5,8},
				{6,9},
				{6,10},
				{7,11},
				{8,12},
				{9,13},
				{10,14},
				{11,15},
				{11,16},
				{12,17},
				{12,18},
				{13,19},
				{13,20},
				{14,21},
				{14,22},
				{15,23},
		};
		
		Topology topology3 = new Topology(new Vertex[] {
				new Vertex(H, -0.5, 0.0), 		//0
				new Vertex(H, 0.5, 0.0), 		//1
				
				new Vertex((0.5+D/2), (0.5+D/2), 0.0), 	//2
				new Vertex((0.5+D/3), (0.5+D/3), 0.0), 	//3
				new Vertex(0.5, (0.5+D/3), 0.0), 		//4
				new Vertex(0.25, (0.5+D*7/12), 0.0), 	//5
				new Vertex(0.25, H, 0.0), 				//6
				
				new Vertex(-0.25, H, 0.0), 				//7
				new Vertex(-0.25, (0.5+D*7/12), 0.0), 	//8
				new Vertex(-0.5, (0.5+D/3), 0.0), 		//9
				new Vertex(-(0.5+D/3), (0.5+D/3), 0.0), //10
				new Vertex(-(0.5+D/2), (0.5+D/2), 0.0), //11
				
				new Vertex(-H, 0.5, 0.0), 		//12
				new Vertex(-H, -0.5, 0.0), 		//13
				
				new Vertex(-(0.5+D/2), -(0.5+D/2), 0.0),//14
				new Vertex(-(0.5+D/3), -(0.5+D/3), 0.0),//15
				new Vertex(-0.5, -(0.5+D/3), 0.0), 		//16
				new Vertex(-0.25, -(0.5+D*7/12), 0.0), 	//17
				new Vertex(-0.25, -H, 0.0), 			//18
				
				new Vertex(0.25, -H, 0.0), 				//19
				new Vertex(0.25, -(0.5+D*7/12), 0.0), 	//20
				new Vertex(0.5, -(0.5+D/3), 0.0), 		//21
				new Vertex((0.5+D/3), -(0.5+D/3), 0.0), //22
				new Vertex((0.5+D/2), -(0.5+D/2), 0.0), //23
				});
		Base base3 = new Base(topology3);
		
		int[][] mapping3 = getDefaultMapping(topology3.getSize());
		
		Topology topology4 = new Topology(new Vertex[] {
				new Vertex((0.5+D*2/3), -(0.5+D/3), 0.0), 	//0
				new Vertex((0.5+D*2/3), (0.5+D/3), 0.0), 	//1
				
				new Vertex((0.5+D/2), (0.5+D/2), 0.0), 	//2
				new Vertex((0.5+D/3), (0.5+D/3), 0.0), 	//3
				new Vertex(0.5, (0.5+D/3), 0.0), 		//4
				new Vertex(0.25, (0.5+D*7/12), 0.0), 	//5
				new Vertex(0.25, H, 0.0), 				//6
				
				new Vertex(-0.25, H, 0.0), 				//7
				new Vertex(-0.25, (0.5+D*7/12), 0.0), 	//8
				new Vertex(-0.5, (0.5+D/3), 0.0), 		//9
				new Vertex(-(0.5+D/3), (0.5+D/3), 0.0), //10
				new Vertex(-(0.5+D/2), (0.5+D/2), 0.0), //11
				
				new Vertex(-(0.5+D*2/3), (0.5+D/3), 0.0), 	//12
				new Vertex(-(0.5+D*2/3), -(0.5+D/3), 0.0), 	//13
				
				new Vertex(-(0.5+D/2), -(0.5+D/2), 0.0),//14
				new Vertex(-(0.5+D/3), -(0.5+D/3), 0.0),//15
				new Vertex(-0.5, -(0.5+D/3), 0.0), 		//16
				new Vertex(-0.25, -(0.5+D*7/12), 0.0), 	//17
				new Vertex(-0.25, -H, 0.0), 			//18
				
				new Vertex(0.25, -H, 0.0), 				//19
				new Vertex(0.25, -(0.5+D*7/12), 0.0), 	//20
				new Vertex(0.5, -(0.5+D/3), 0.0), 		//21
				new Vertex((0.5+D/3), -(0.5+D/3), 0.0), //22
				new Vertex((0.5+D/2), -(0.5+D/2), 0.0), //23
				});
		Base base4 = new Base(topology4);
		
		int[][] mapping4 = new int[][]{
				{0,0},
				{0,1},
				{1,2},
				{1,3},
				
				{2,4},
				{3,5},
				{4,6},
				{5,7},
				{6,8},
				{7,8},
				{8,9},
				{9,10},				
				{10,11},
				{11,12},
				
				{12,13},
				{12,14},
				{13,15},
				{13,16},
				
				{14,17},
				{15,18},
				{16,19},
				{17,20},
				{18,21},
				{19,21},
				{20,22},
				{21,23},
				{22,24},
				{23,25},
		};
		
		Topology topology5 = new Topology(new Vertex[] {
				new Vertex((0.5+D*2/3), -(0.5+D/3), 0.0), 	//0
				new Vertex((0.5+D/2), -(0.5+D/6), 0.0), 	//1
				new Vertex((0.5+D/2), (0.5+D/6), 0.0), 		//2				
				new Vertex((0.5+D*2/3), (0.5+D/3), 0.0), 	//3
				
				new Vertex((0.5+D/2), (0.5+D/2), 0.0), 	//4
				new Vertex((0.5+D/3), (0.5+D/3), 0.0), 	//5
				new Vertex(0.5, (0.5+D/3), 0.0), 		//6
				new Vertex(0.00001, (0.5+D*2/3), 0.0), 	//7
				new Vertex(0.0, H, 0.0), 				//8

				new Vertex(-0.00001, (0.5+D*2/3), 0.0), //9
				new Vertex(-0.5, (0.5+D/3), 0.0), 		//10
				new Vertex(-(0.5+D/3), (0.5+D/3), 0.0), //11
				new Vertex(-(0.5+D/2), (0.5+D/2), 0.0), //12	
				
				new Vertex(-(0.5+D*2/3), (0.5+D/3), 0.0), 	//13				
				new Vertex(-(0.5+D/2), (0.5+D/6), 0.0), 	//14
				new Vertex(-(0.5+D/2), -(0.5+D/6), 0.0), 	//15
				new Vertex(-(0.5+D*2/3), -(0.5+D/3), 0.0), 	//16
				
				new Vertex(-(0.5+D/2), -(0.5+D/2), 0.0),	//17				
				new Vertex(-(0.5+D/3), -(0.5+D/3), 0.0),//18
				new Vertex(-0.5, -(0.5+D/3), 0.0), 		//19
				new Vertex(-0.00001, -(0.5+D*2/3), 0.0), //20
				new Vertex(0.0, -H, 0.0), 				//21

				new Vertex(0.00001, -(0.5+D*2/3), 0.0), //22
				new Vertex(0.5, -(0.5+D/3), 0.0), 		//23
				new Vertex((0.5+D/3), -(0.5+D/3), 0.0), //24
				new Vertex((0.5+D/2), -(0.5+D/2), 0.0), //25
		});
		Base base5 = new Base(topology5);
		
		int[][] mapping5 = new int[][]{
				{0,0},
				{1,1},
				{2,2},
				{3,3},
				
				{4,3},
				{5,4},
				{6,5},
				{7,6},
				{8,6},
				{9,6},
				{10,7},
				{11,8},
				{12,9},				
				{13,9},
				
				{14,10},				
				{15,11},
				{16,12},
				{17,12},
				{18,13},
				
				{19,14},
				{20,15},
				{21,15},
				{22,15},
				{23,16},
				{24,17},
				{25,0},
		};
		
		Topology topology6 = new Topology(new Vertex[] {
				new Vertex((0.5+D*2/3), -(0.5+D/3), 0.0), 	//0
				new Vertex((0.5+D/2), -(0.5+D/6), 0.0), 	//1
				new Vertex((0.5+D/2), (0.5+D/6), 0.0), 		//2				
				new Vertex((0.5+D*2/3), (0.5+D/3), 0.0), 	//3
				
				new Vertex((0.5+D/2)-EPS, (0.5+D/6)+EPS, 0.0), 		//4
				new Vertex((0.5-D/6), (0.5+D/6), 0.0), 				//5
				new Vertex(0.0, (0.5+D*1/3), 0.0), 					//6

				new Vertex(-(0.5-D/6), (0.5+D/6), 0.0), 			//7
				new Vertex(-(0.5+D/2)+EPS, (0.5+D/6)+EPS, 0.0), 	//8	
				
				new Vertex(-(0.5+D*2/3), (0.5+D/3), 0.0), 	//9				
				new Vertex(-(0.5+D/2), (0.5+D/6), 0.0), 	//10
				new Vertex(-(0.5+D/2), -(0.5+D/6), 0.0), 	//11
				new Vertex(-(0.5+D*2/3), -(0.5+D/3), 0.0), 	//12
						
				new Vertex(-(0.5+D/2)+EPS, -(0.5+D/6)-EPS, 0.0),	//13
				new Vertex(-(0.5-D/6), -(0.5+D/6), 0.0), 			//14
				new Vertex(0.0, -(0.5+D*1/3), 0.0), 				//15

				new Vertex((0.5-D/6), -(0.5+D/6), 0.0), 			//16
				new Vertex((0.5+D/2)-EPS, -(0.5+D/6)-EPS, 0.0), 	//17	
		});
		Base base6 = new Base(topology6);
		
		int[][] mapping6 = new int[][]{
				{0,0},
				{1,0},
				
				{2,1},
				{3,1},				
				{4,1},
				
				{5,2},
				{6,2},				
				{7,2},
				
				{8,3},
				{9,3},
				{10,3},
				
				{11,4},
				{12,4},				
				{13,4},
				
				{14,5},				
				{15,5},
				{16,5},
				{17,0},
		};
		
//		base6 = new Base();
//
//		base6.addTopologyVertex(new Vertex(D/2, -D/6, 0.0)); 	//0
//		base6.addTopologyVertex(new Vertex(D/2, D/6, 0.0)); 	//1			
//				
//		base6.addTopologyVertex(new Vertex(0, D/6, 0.0)); 		//2
//
//		base6.addTopologyVertex(new Vertex(-D/2, D/6, 0.0)); 	//3
//		base6.addTopologyVertex(new Vertex(-D/2, -D/6, 0.0)); 	//4
//				
//		base6.addTopologyVertex(new Vertex(0, -D/6, 0.0)); 		//5
//		
//		mapping6 = new int[][]{
//			{0,0},
//			{1,1},				
//			{2,2},				
//			{3,3},
//			{4,4},
//			{5,5},
//		};
		
		Base base7 = new Base();

		base7.addTopologyVertex(new Vertex(D/2, -D/6, 0.0)); 	//0
		base7.addTopologyVertex(new Vertex(D/2, D/6, 0.0)); 	//1			
				
		base7.addIntermediateVertex(new Vertex(0, D/6, 0.0)); 		//2

		base7.addTopologyVertex(new Vertex(-D/2, D/6, 0.0)); 	//3
		base7.addTopologyVertex(new Vertex(-D/2, -D/6, 0.0)); 	//4
				
		base7.addIntermediateVertex(new Vertex(0, -D/6, 0.0)); 		//5		

		
		int[][] mapping7 = new int[][]{
				{0,0},
				{1,0},				
				{2,1},				
				{3,2},
				{4,2},
				{5,1},
		};
		
		Base base8 = new Base();
		base8.addTopologyVertex(new Vertex(D/3, 0, 0.0));	//0
		base8.addIntermediateVertex(new Vertex(0, 0.0, 0.0));	//1
		base8.addTopologyVertex(new Vertex(-D/3, 0, 0.0));	//2

		CellPrototype cellA = new CellPrototype();
		cellA.getBases().add(base0);
		cellA.getBases().add(base1);
		cellA.getBases().add(base2);
		cellA.getBases().add(base3);
		cellA.getBases().add(base4);
		cellA.getBases().add(base5);
		cellA.getBases().add(base6);
		cellA.getBases().add(base7);
//		cellA.getBases().add(base8);
		
		cellA.getMappings().add(mapping0);
		cellA.getMappings().add(mapping1);
		cellA.getMappings().add(mapping2);
		cellA.getMappings().add(mapping3);
		cellA.getMappings().add(mapping4);
		cellA.getMappings().add(mapping5);
		cellA.getMappings().add(mapping6);
//		cellA.getMappings().add(mapping7);
		
		return cellA;
	}
	
	public static CellPrototype cellTypeB(){		
		Topology topology0 = new Topology(new Vertex[] {
				new Vertex(D, 0.0, 0.0), //0
				new Vertex(0.0, D, 0.0), //1
				new Vertex(-D, 0.0, 0.0),//2
				new Vertex(0.0, -D, 0.0) //3
				});
		
		int[][] mapping0 = new int[][]{
				{0,0},
				{0,1},
				{1,2},
				{2,3},
				{2,4},
				{3,5}
		};
		
		Topology topology1 = new Topology(new Vertex[] {
				new Vertex(D/2, -D/2, 0.0), //0
				new Vertex(D/2, D/2, 0.0), //1
				new Vertex(0.0, D, 0.0), //2
				new Vertex(-D/2, D/2, 0.0), //3
				new Vertex(-D/2, -D/2, 0.0), //4
				new Vertex(0.0, -D, 0.0) //5
				});
		
		int[][] mapping1 = getDefaultMapping(topology1.getSize());
		
		Topology topology2 = topology1;
		
		int[][] mapping2 = getDefaultMapping(topology2.getSize());
		
		Topology topology3 = topology2;
		
		int[][] mapping3 = new int[][]{
				{0,0},
				{1,1},
				{2,2},
				{2,3},
				{3,4},
				{4,5},
				{5,6},
				{5,7}
		};
		
		Topology topology4 = new Topology(new Vertex[] {
				new Vertex(D/2, -D/2, 0.0),	//0
				new Vertex(D/2, D/2, 0.0),	//1
				new Vertex(D/3, D*2/3, 0.0),	//2
				new Vertex(-D/3, D*2/3, 0.0), //3
				new Vertex(-D/2, D/2, 0.0),	//4
				new Vertex(-D/2, -D/2, 0.0),	//5
				new Vertex(-D/3, -D*2/3, 0.0),//6
				new Vertex(D/3, -D*2/3, 0.0), //7
				});
		
		int[][] mapping4 = new int[][]{
				{0,0},
				{0,1},
				{1,2},
				{1,3},
				{2,4},
				{2,5},
				{3,6},
				{3,7},
				{4,8},
				{4,9},
				{5,10},
				{5,11},
				{6,12},
				{6,13},
				{7,14},
				{7,15},
		};
		
		Topology topology5 = new Topology(new Vertex[] {
				new Vertex(D/2, -D/2, 0.0),	//0
				new Vertex(D/3, -D/3, 0.0), //1
				new Vertex(D/3, D/3, 0.0),	//2
				new Vertex(D/2, D/2, 0.0),	//3
				
				new Vertex(D/3, D*2/3, 0.0),//4
				new Vertex(D/6, D/2, 0.0),	//5
				new Vertex(-D/6, D/2, 0.0),	//6
				new Vertex(-D/3, D*2/3, 0.0),//7
				
				new Vertex(-D/2, D/2, 0.0),	//8
				new Vertex(-D/3, D/3, 0.0),	//9
				new Vertex(-D/3, -D/3, 0.0),//10
				new Vertex(-D/2, -D/2, 0.0),//11
				
				new Vertex(-D/3, -D*2/3, 0.0),//12
				new Vertex(-D/6, -D/2, 0.0),//13
				new Vertex(D/6, -D/2, 0.0),	//14
				new Vertex(D/3, -D*2/3, 0.0),//15
		});
		
		int[][] mapping5 = new int[][]{
				{0,11},
				{1,0},
				{2,1},
				{3,2},
				{4,2},
				{5,3},
				{6,4},
				{7,5},
				{8,5},
				{9,6},
				{10,7},
				{11,8},
				{12,8},
				{13,9},
				{14,10},
				{15,11},
		};
		
		double eps = 0.001;
		
		Topology topology6 = new Topology(new Vertex[] {
				new Vertex(D/6+eps, -D/2+eps, 0.0),	//0
				new Vertex(D/6+eps, D/2-eps, 0.0),	//1
				
				new Vertex(D/3, D*2/3, 0.0),//2
				new Vertex(D/6, D/2, 0.0),	//3
				new Vertex(-D/6, D/2, 0.0),	//4
				new Vertex(-D/3, D*2/3, 0.0),//5
				
				new Vertex(-D/6-eps, D/2-eps, 0.0),	//6
				new Vertex(-D/6-eps, -D/2+eps, 0.0),//7
				
				new Vertex(-D/3, -D*2/3, 0.0),//8
				new Vertex(-D/6, -D/2, 0.0),//9
				new Vertex(D/6, -D/2, 0.0),	//10
				new Vertex(D/3, -D*2/3, 0.0),//11
		});
		
		int[][] mapping6 = new int[][]{
				{0,0},
				{1,1},
				{2,1},
				{3,1},
				{4,2},
				{5,2},
				{6,2},
				{7,3},
				{8,3},
				{9,3},
				{10,0},
				{11,0},
		};
		
		Topology topology7 = new Topology(new Vertex[] {
				new Vertex(D/6, -D/2, 0.0),	//0
				new Vertex(D/6, D/2, 0.0),	//1
				new Vertex(-D/6, D/2, 0.0),	//2				
				new Vertex(-D/6, -D/2, 0.0),//3
		});

		CellPrototype cellB = new CellPrototype();
		cellB.getBases().add(new Base(topology0));
		cellB.getBases().add(new Base(topology1));
		cellB.getBases().add(new Base(topology2));
		cellB.getBases().add(new Base(topology3));
		cellB.getBases().add(new Base(topology4));
		cellB.getBases().add(new Base(topology5));
		cellB.getBases().add(new Base(topology6));
		cellB.getBases().add(new Base(topology7));
		
		cellB.getMappings().add(mapping0);
		cellB.getMappings().add(mapping1);
		cellB.getMappings().add(mapping2);
		cellB.getMappings().add(mapping3);
		cellB.getMappings().add(mapping4);
		cellB.getMappings().add(mapping5);
		cellB.getMappings().add(mapping6);
		
		return cellB;
	}
	
	public static CellPrototype cellTypeC(){		
		Topology topology0 = new Topology(new Vertex[] {
				new Vertex(0.0, 0.0, 0.0), 				//0
				});
		
		int[][] mapping0 = new int[][]{
				{0,0},
				{0,1},
				{0,2},
		};
		
		Topology topology1 = new Topology(new Vertex[] {
				new Vertex(D/2, -D/2, 0.0), 			//0
				new Vertex(D/2, D/2, 0.0), 				//1
				new Vertex(0.0, 0.0, 0.0), 				//2
				});
		
		int[][] mapping1 = new int[][]{
				{0,0},
				{0,1},
				{1,2},
				{1,3},
				{2,4},
		};
		
		Topology topology2 = new Topology(new Vertex[] {
				new Vertex(D/3, -D*2/3, 0.0), 			//0
				new Vertex(D/2, -D/2, 0.0), 			//1
				new Vertex(D/2, D/2, 0.0), 				//2
				new Vertex(D/3, D*2/3, 0.0), 			//3
				new Vertex(-0.25, 0.0, 0.0), 			//4
				});
		
		int[][] mapping2 = new int[][]{
				{0,0},
				{0,1},
				{1,2},
				{2,3},
				{3,4},
				{3,5},
				{4,6},
				{4,7},
		};
		
		Topology topology3 = new Topology(new Vertex[] {
				new Vertex(0.0, -D*2/3, 0.0), 			//0
				new Vertex(D/3, -D*2/3, 0.0), 			//1
				new Vertex(D/2, -D/2, 0.0), 			//2
				new Vertex(D/2, D/2, 0.0), 				//3
				new Vertex(D/3, D*2/3, 0.0), 			//4
				new Vertex(0.0, D*2/3, 0.0), 			//5
				new Vertex(-0.25, D*5/12, 0.0), 		//6
				new Vertex(-0.25, -D*5/12, 0.0), 		//7
				});
		
		int[][] mapping3 = new int[][]{
				{0,0},
				{1,1},
				{2,2},
				{3,3},
				{4,4},
				{5,5},
				{6,6},
				{7,7},
		};
		
		Topology topology4 = new Topology(new Vertex[] {
				new Vertex(0.0, -D*2/3, 0.0), 			//0
				new Vertex(D/3, -D*2/3, 0.0), 			//1
				new Vertex(D/2, -D/2, 0.0), 			//2
				new Vertex(D/2, D/2, 0.0), 				//3
				new Vertex(D/3, D*2/3, 0.0), 			//4
				new Vertex(0.0, D*2/3, 0.0), 			//5
				new Vertex(-0.25, D*5/12, 0.0), 		//6
				new Vertex(-0.25, -D*5/12, 0.0), 		//7
		});
		
		int[][] mapping4 = new int[][]{
				{0,0},
				{1,1},
				{2,2},
				{3,3},
				{4,4},
				{5,5},
				{6,6},
				{7,7},
		};
		
		Topology topology5 = new Topology(new Vertex[] {
				new Vertex(0.0, -D*2/3, 0.0), 			//0
				new Vertex(D/3, -D*2/3, 0.0), 			//1
				new Vertex(D*2/3, -D/3, 0.0), 			//2
				new Vertex(D*2/3, D/3, 0.0), 			//3
				new Vertex(D/3, D*2/3, 0.0), 			//4
				new Vertex(0.0, D*2/3, 0.0), 			//5
				new Vertex(-0.5, D/3, 0.0), 			//6
				new Vertex(-0.5, -D/3, 0.0), 			//7
		});
		
		int[][] mapping5 = new int[][]{
				{0,0},
				{1,1},
				{2,2},
				{3,3},
				{4,4},
				{5,5},
				{6,6},
				{7,7},
		};
		
		Topology topology6 = new Topology(new Vertex[] {
				new Vertex(-D/6, -D*5/6, 0.0), 			//0
				new Vertex(D/2, -D*5/6, 0.0), 			//1
				new Vertex(D*5/6, -D/2, 0.0), 			//2
				new Vertex(D*5/6, D/2, 0.0), 			//3
				new Vertex(D/2, D*5/6, 0.0), 			//4
				new Vertex(-D/6, D*5/6, 0.0), 			//5
				new Vertex(-0.5, D*2/3, 0.0), 			//6
				new Vertex(-0.5, -D*2/3, 0.0), 			//7
		});
		
		int[][] mapping6 = new int[][]{
				{0,5},
				{1,0},
				{2,1},
				{3,2},
				{4,3},
				{5,4},
				{6,4},
				{7,5},
		};
		
		Topology topology7 = new Topology(new Vertex[] {
				new Vertex(D/2-0.5, -(0.5+D*5/6), 0.0),		//0
				new Vertex(D*5/6, -D/2, 0.0), 				//1
				new Vertex(D*5/6, D/2, 0.0), 				//2
				new Vertex(D/2-0.5, (0.5+D*5/6), 0.0),		//3
				new Vertex(-0.5, (0.5+D*5/6), 0.0), 		//4
				new Vertex(-0.5, -(0.5+D*5/6), 0.0), 		//5
		});
		
		CellPrototype cellC = new CellPrototype();
		cellC.getBases().add(new Base(topology0));
		cellC.getBases().add(new Base(topology1));
		cellC.getBases().add(new Base(topology2));
		cellC.getBases().add(new Base(topology3));
		cellC.getBases().add(new Base(topology4));
		cellC.getBases().add(new Base(topology5));
		cellC.getBases().add(new Base(topology6));
		cellC.getBases().add(new Base(topology7));
		
//		cellA.getMappings().add(getDefaultMapping(topology5.getSize()));
		cellC.getMappings().add(mapping0);
		cellC.getMappings().add(mapping1);
		cellC.getMappings().add(mapping2);
		cellC.getMappings().add(mapping3);
		cellC.getMappings().add(mapping4);
		cellC.getMappings().add(mapping5);
		cellC.getMappings().add(mapping6);
		
		return cellC;
	}
	
	public static CellPrototype cellTypeD(){		
		Topology topology0 = new Topology(new Vertex[] {
				new Vertex(0.0, 0.5, 0.0), //0
				new Vertex(0.0, -0.5, 0.0), //1
				});
		
		int[][] mapping0 = new int[][]{
				{0,0},
				{0,1},
				{1,2},
				{1,3},
		};
		
		Topology topology1 = new Topology(new Vertex[] {
				new Vertex(D/3, (0.5 + D/3), 0.0), 	//0
				new Vertex(-D/3, (0.5 + D/3), 0.0),	//1
				new Vertex(-D/3, -(0.5 + D/3), 0.0),//2
				new Vertex(D/3, -(0.5 + D/3), 0.0), //3
				});
		
		int[][] mapping1 = new int[][]{
				{0,0},
				{0,1},
				{1,2},
				{1,3},
				{2,4},
				{2,5},
				{3,6},
				{3,7},
		};
		
		Topology topology2 = new Topology(new Vertex[] {
				new Vertex(D/2, (0.5 + D/6), 0.0), 			//0
				new Vertex(D/6, (0.5 + D/2), 0.0),			//1
				new Vertex(-D/6,(0.5 + D/2), 0.0),			//2
				new Vertex(-D/2,(0.5 + D/6), 0.0), 			//3
				
				new Vertex(-D/2, -(0.5 + D/6), 0.0), 		//4
				new Vertex(-D/6, -(0.5 + D/2), 0.0),		//5
				new Vertex(D/6,  -(0.5 + D/2), 0.0),		//6
				new Vertex(D/2,  -(0.5 + D/6), 0.0), 		//7
		});
		
		int[][] mapping2 = getDefaultMapping(topology2.getSize());
		
		Topology topology3 = new Topology(new Vertex[] {
				new Vertex(D/2, (0.5 + D/6), 0.0), 			//0
				new Vertex(D/6, (0.5 + D/2), 0.0),			//1
				new Vertex(-D/6,(0.5 + D/2), 0.0),			//2
				new Vertex(-D/2,(0.5 + D/6), 0.0), 			//3
				
				new Vertex(-D/2, -(0.5 + D/6), 0.0), 		//4
				new Vertex(-D/6, -(0.5 + D/2), 0.0),		//5
				new Vertex(D/6,  -(0.5 + D/2), 0.0),		//6
				new Vertex(D/2,  -(0.5 + D/6), 0.0), 		//7
		});
		
		int[][] mapping3 = getDefaultMapping(topology3.getSize());
		
		Topology topology4 = new Topology(new Vertex[] {
				new Vertex(0.5 + D/2, D/6, 0.0), 			//0
				new Vertex(D/6, (0.5 + D/2), 0.0),			//1
				new Vertex(-D/6,(0.5 + D/2), 0.0),			//2
				new Vertex(-(0.5 + D/2), D/6, 0.0), 		//3
				
				new Vertex(-(0.5 + D/2), -D/6, 0.0), 		//4
				new Vertex(-D/6, -(0.5 + D/2), 0.0),		//5
				new Vertex(D/6,  -(0.5 + D/2), 0.0),		//6
				new Vertex((0.5 + D/2), -D/6, 0.0), 		//7
		});
		
		CellPrototype cellD = new CellPrototype();
		cellD.getBases().add(new Base(topology0));
		cellD.getBases().add(new Base(topology1));
		cellD.getBases().add(new Base(topology2));
		cellD.getBases().add(new Base(topology3));
		cellD.getBases().add(new Base(topology4));
		
//		cellD.getMappings().add(getDefaultMapping(topology1.getSize()));
		cellD.getMappings().add(mapping0);
		cellD.getMappings().add(mapping1);
		cellD.getMappings().add(mapping2);
		cellD.getMappings().add(mapping3);
		
		return cellD;
	}
	
	static{
		heights = new ArrayList<>();
		for (int i = 0; i < 10; ++i){
			heights.add((double) i);
		}
		heightsD = new ArrayList<>(heights);
		heightsD.remove(0);
		heightsD.remove(0);
		heightsD.remove(0);
	}
	
	private static int[][] getDefaultMapping(int size){
		int[][] mapping = new int[size][2];
		for (int i = 0; i < size; ++i){
			mapping[i][0] = i;
			mapping[i][1] = i;
		}
		return mapping;
	}
}
