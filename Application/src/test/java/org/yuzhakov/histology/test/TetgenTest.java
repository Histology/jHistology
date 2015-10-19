package org.yuzhakov.histology.test;

import de.jreality.geometry.IndexedFaceSetFactory;
import de.jreality.scene.IndexedFaceSet;
import de.jreality.scene.SceneGraphComponent;
import de.jreality.util.SceneGraphUtility;
import org.junit.Test;
import org.yuzhakov.histology.gui.ModelFrame;
import org.yuzhakov.histology.gui.ResultFrame;
import org.yuzhakov.histology.gui.jreality.JrCell;
import org.yuzhakov.histology.gui.jreality.JrCutPlane;
import org.yuzhakov.histology.gui.jreality.JrModelCut;
import org.yuzhakov.histology.model.cut.CutPlane;
import org.yuzhakov.histology.model.cut.ModelCut;
import org.yuzhakov.histology.model.m3D.Cell;
import org.yuzhakov.histology.model.samples.Samples;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class TetgenTest {
	public boolean isExit = false;
	
	@Test
	public void test(){
		Cell cell = new Cell(Samples.Star4(3));
		List<Cell> cells = new ArrayList<>();
		cells.add(cell);
		final CutPlane cutPlane = new CutPlane();
		cutPlane.construct();				
		ModelCut modelCut = new ModelCut(cells, cutPlane);
		
		
		
		
		JrCell jrCell = new JrCell(cell);
		final JrCutPlane jrCutPlane = new JrCutPlane(cutPlane);
		final JrModelCut jrModelCut = new JrModelCut(modelCut);
		
		final SceneGraphComponent model = SceneGraphUtility
				.createFullSceneGraphComponent();
		model.addChild(jrCell.getSceneGraphComponent());
		model.addChild(jrCutPlane.getSceneGraphComponent());
		
		
		final SceneGraphComponent result = SceneGraphUtility
				.createFullSceneGraphComponent();
		result.addChild(jrModelCut.getSceneGraphComponent());
		
		
		ModelFrame modelTestFrame = new ModelFrame(model, "Модель Геккона"){
    		@Override
    		public void sliderStateChanged(double A, double B, double Z) {
    			cutPlane.setA(A);
    			cutPlane.setB(B);
    			cutPlane.setZ(Z);
    			cutPlane.construct();
    			jrCutPlane.update();
    			jrModelCut.update();
    		}
    	};
    	
		ResultFrame resultFrame = new ResultFrame(result, "");
    	resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	resultFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				isExit = true;
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		while (!isExit){
			
		}
	}
	
//	@Test
//	public void outgrothTest() {
//		Cell cell = Gekko.getGisteon(new Vertex()).get(0);
//		CellPrototype cellPrototype = cell.getPrototype();
//		for (int i = 0; i < 4; ++i){
//			cellPrototype.getTopologies().remove(0);
//			cellPrototype.getMappings().remove(0);
//		}
//		
//		for (int i = cellPrototype.getTopologies().size() - 1; i > 1; --i){
//			cellPrototype.getTopologies().remove(i);
//			cellPrototype.getMappings().remove(i-1);
//		}
//		Vertex[] bottom = cellPrototype.getTopologies().get(0).getCoordinates();
//		Vertex[] top = cellPrototype.getTopologies().get(1).getCoordinates();
//		
//		List<Vertex> testBottom = new ArrayList<>();
//		testBottom.add(bottom[4]);//0
//		testBottom.add(bottom[5]);//1
//		testBottom.add(bottom[6]);//2
//		testBottom.add(bottom[7]);//3
//		testBottom.add(bottom[8]);//4
//		testBottom.add(bottom[9]);//5
//		testBottom.add(bottom[16]);//6
//		testBottom.add(bottom[21]);//7
//		
//		List<Vertex> testTop = new ArrayList<>();
//		testTop.add(top[6]);//8
//		testTop.add(top[7]);//9
//		testTop.add(top[10]);//10
//		
//		testTop.add(top[19]);//11
//		testTop.add(top[23]);//12
//		
//		for (Vertex v : testTop){
//			v.Z = 1.0;
//		}
//		
//		Topology bottomTopology = new Topology(testBottom.toArray(new Vertex[0]));
//		Topology topTopology = new Topology(testTop.toArray(new Vertex[0]));
//		
//		int[][] mapping = new int[][]{
//				{0,0},//8
//				{1,1},//9
//				{4,1},//9
//				{5,2},//10
//				{6,3},//11
//				{7,4}//12
//		};
//		
//		CellPrototype testCellPrototype = new CellPrototype();
//		testCellPrototype.getTopologies().add(bottomTopology);
//		testCellPrototype.getTopologies().add(topTopology);
//		testCellPrototype.getMappings().add(mapping);
//		testCellPrototype.setColor(Color.MAGENTA);
//		Cell testCell = new Cell(testCellPrototype);
//		
//		List<Vertex> testVertexes = new ArrayList<>();
//		testVertexes.addAll(testBottom);
//		testVertexes.addAll(testTop);
//		Vertex extra = top[8];
//		extra.Z = 1.0;
//		testVertexes.add(extra);
//		
//		List<int[]> bottomTriangles = Triangulation.triangulate(testBottom.toArray(new Vertex[0]));
//		
//		List<int[]> faces = new ArrayList<>();
//		faces.add(new int[]{8,9,10,11,12});
//		faces.add(new int[]{0,8,9,1});
//		faces.add(new int[]{4,9,10,5});
//		faces.add(new int[]{5,10,11,6});
//		faces.add(new int[]{6,11,12,7});
//		faces.add(new int[]{7,12,8,0});
//		faces.add(new int[]{1,9,13,2});
//		faces.add(new int[]{2,13,3});
//		faces.add(new int[]{3,13,9,4});
//		
//		for (int[] triangle : bottomTriangles){
//			faces.add(triangle);
//		}
//		
//		double[][] coordinates = new double[testVertexes.size()][3];
//		int i = 0;
//		for (Vertex v : testVertexes){
//			coordinates[i++] = v.getCoordinates();
//		}
//		int[][] facesArr = faces.toArray(new int[0][0]);
//		int[][] edges = new int[0][0];
//		
//		Tetgen tetgen = new Tetgen(testVertexes, faces);
//		tetgen.tetrahedralize();
//		
//		final SceneGraphComponent model = SceneGraphUtility
//				.createFullSceneGraphComponent();
//		final SceneGraphComponent cut = SceneGraphUtility.createFullSceneGraphComponent();
//		
//		SceneGraphComponent cellComponent = SceneGraphUtility.createFullSceneGraphComponent();		
//		cellComponent.setGeometry(getIndexedFaceSet(coordinates, facesArr, edges));
//		cellComponent.getAppearance().setAttribute(POLYGON_SHADER+"."+DIFFUSE_COLOR, Color.MAGENTA);
//		
//		final CutPlane cutPlane = new CutPlane();
//		cutPlane.construct();
//		final JrCutPlane JRcutPlane = new JrCutPlane(cutPlane);
//		JRcutPlane.setPlaneSize(40, 40, 20);
//		JRcutPlane.update();
//		model.addChild(cellComponent);
//		model.addChild(JRcutPlane.getSceneGraphComponent());
//		
//		final CellCut cellCut = new CellCut(cutPlane, testCell, tetgen.getTetrahedronList());
//		final JrCellCut jrCellCut = new JrCellCut(cellCut);
//		
//		
//		cut.addChild(jrCellCut.getSceneGraphComponent());
//
//    	ModelFrame modelTestFrame = new ModelFrame(model, "Модель Геккона"){
//    		@Override
//    		public void sliderStateChanged(double A, double B, double Z) {
//    			cutPlane.setA(A);
//    			cutPlane.setB(B);
//    			cutPlane.setZ(Z);
//    			cutPlane.construct();
//    			JRcutPlane.update();
//    			jrCellCut.update();
//    		}
//    	};	
//    	
//    	ResultFrame resultFrame = new ResultFrame(cut, "");
//    	resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//    	modelTestFrame.addWindowListener(new WindowListener() {
//
//			@Override
//			public void windowOpened(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void windowIconified(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void windowDeiconified(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void windowDeactivated(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void windowClosing(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void windowClosed(WindowEvent arg0) {
//				isExit = true;
//			}
//
//			@Override
//			public void windowActivated(WindowEvent arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//		while (!isExit){
//			
//		}
//	}				
	
	private static IndexedFaceSet getIndexedFaceSet(double[][] coordinates, int[][] faceIndices, int[][] edges){
		IndexedFaceSetFactory ifsf = new IndexedFaceSetFactory();
		ifsf.setVertexCount(coordinates.length);
		ifsf.setFaceCount(faceIndices.length);
		ifsf.setEdgeCount(edges.length);
		ifsf.setVertexCoordinates(coordinates);
		ifsf.setFaceIndices(faceIndices);
		ifsf.setEdgeIndices(edges);
		ifsf.setGenerateFaceNormals(true);
		ifsf.update();
		
		return ifsf.getIndexedFaceSet();
	}
				

}
