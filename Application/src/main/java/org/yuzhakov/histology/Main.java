package org.yuzhakov.histology;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.yuzhakov.histology.gui.ModelFrame;
import org.yuzhakov.histology.gui.ResultFrame;
import org.yuzhakov.histology.gui.jreality.JrCell;
import org.yuzhakov.histology.gui.jreality.JrCutPlane;
import org.yuzhakov.histology.gui.jreality.JrModelCut;
import org.yuzhakov.histology.gui.jreality.JrSlice;
import org.yuzhakov.histology.model.Vertex;
import org.yuzhakov.histology.model.cut.CutPlane;
import org.yuzhakov.histology.model.cut.ModelCut;
import org.yuzhakov.histology.model.m3D.Cell;
import org.yuzhakov.histology.model.m3D.CellPrototype;
import org.yuzhakov.histology.model.m3D.Slice;
import org.yuzhakov.histology.model.samples.Gekko;

import de.jreality.scene.SceneGraphComponent;
import de.jreality.util.SceneGraphUtility;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
//            	CellPrototype cellPrototype = Gekko.cellTypeA();
//				for (int i = 0; i < 7; ++i){
//					cellPrototype.getBases().remove(0);
//					cellPrototype.getMappings().remove(0);
//				}
//				Cell cell = new Cell(cellPrototype);
//				cut(Gekko.getGisteon(new Vertex()));
//            	cut(new Cell(Gekko.cellTypeA()));
//            	show(new Cell(Gekko.cellTypeA()));
//            	Cell cell = new Cell(Gekko.cellTypeA());
//            	Slice slice = cell.getSlices().get(1);
//            	show(slice);
//            	show(cell);
            	cut(Gekko.getModel(3, 3));
            }           
		});
	}
	
	
	
	public static void main2(String[] args) throws InterruptedException {
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {        		
        		final double height = 6;
        		
        		final SceneGraphComponent world = SceneGraphUtility.createFullSceneGraphComponent();
        		final SceneGraphComponent world2 = SceneGraphUtility.createFullSceneGraphComponent();
        		     		
//				List<Cell> cells = Gekko.getModel(3, 3);
				
				List<Cell> gistion = Gekko.getGisteon(new Vertex());
				CellPrototype cellPrototype = Gekko.cellTypeA();
				for (int i = 0; i < 6; ++i){
					cellPrototype.getBases().remove(0);
					cellPrototype.getMappings().remove(0);
				}
				Cell cell = new Cell(cellPrototype);
				
        		JrCell jrCell = new JrCell(cell);
//        		world.addChild(jrCell2.getSceneGraphComponent());
				
//				List<Cell> cells = gisteon;
//				cells.add(gisteon.get(2));
				
//				for (Cell cell : gis ion){
//					JrCell jrCell = new JrCell(cell);
//					world.addChild(jrCell.getSceneGraphComponent());
//				}
				
//				final CutPlane cutPlane = new CutPlane();
//				cutPlane.construct();
//				final JrCutPlane JRcutPlane = new JrCutPlane(cutPlane);
//				JRcutPlane.setPlaneSize(40, 40, 20);
//				JRcutPlane.update();
//				
//				final ModelCut modelCut = new ModelCut(gistion, cutPlane);
//				final JrModelCut jrModelCut = new JrModelCut(modelCut);
//				jrModelCut.getSceneGraphComponent();				
//				world.addChild(JRcutPlane.getSceneGraphComponent());

            	ModelFrame modelTestFrame = new ModelFrame(world, "Модель Геккона"){
            		@Override
            		public void sliderStateChanged(double A, double B, double Z) {
//            			cutPlane.setA(A);
//            			cutPlane.setB(B);
//            			cutPlane.setZ(Z*height);
//            			cutPlane.construct();
//            			JRcutPlane.update();
//            			jrModelCut.update();
            		}
            	};
//            	
//            	ResultFrame resultFrame = new ResultFrame(jrModelCut.getSceneGraphComponent(), "Сечение 1");
            	
            	ResultFrame resultFrame = new ResultFrame(world2, "");
            	resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            	ResultFrame cutFrame = new ResultFrame(jrModelCut.getSceneGraphComponent(), "Сечение 1");
//            	cutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				JrCell jrCell = new JrCell(gistion.get(0));
//				CellFrame cellFrame = new CellFrame(Gekko.cellTypeA(), jrCell.getSceneGraphComponent(), "Тип А");
//				cellFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				HistionFrame histionFrame = new HistionFrame(Gekko.getGisteon(new Vertex()), world, "Гистион сетчатки геккона");
//				histionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				ModelFrame modelFrame = new ModelFrame(Gekko.getGisteon(new Vertex()), world, "Cетчатка геккона");
//				modelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
        });
	}
	
	private static void show(Cell cell){
		final SceneGraphComponent world = SceneGraphUtility.createFullSceneGraphComponent();
		JrCell jrCell = new JrCell(cell);
		world.addChild(jrCell.getSceneGraphComponent());
		ResultFrame resultFrame = new ResultFrame(world, "");
    	resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	private static void show(Slice slice){
		final SceneGraphComponent world = SceneGraphUtility.createFullSceneGraphComponent();
		JrSlice jrSlice = new JrSlice(slice);
		world.addChild(jrSlice.getSceneGraphComponent());
		ResultFrame resultFrame = new ResultFrame(world, "");
    	resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	private static void cut(Cell cell){
		final int MODEL_HEIGHT = cell.getNumberOfBases();
		
		final CutPlane cutPlane = new CutPlane();
		cutPlane.construct();		
		ArrayList<Cell> cells = new ArrayList<>();
		cells.add(cell);
		final ModelCut modelCut = new ModelCut(cells, cutPlane);
		
		final JrCell jrCell = new JrCell(cell);
		final JrCutPlane jrCutPlane = new JrCutPlane(cutPlane);
		final JrModelCut jrModelCut = new JrModelCut(modelCut);
		
		final SceneGraphComponent model = SceneGraphUtility.createFullSceneGraphComponent();
		model.addChild(jrCell.getSceneGraphComponent());
		model.addChild(jrCutPlane.getSceneGraphComponent());
		
		final SceneGraphComponent result = SceneGraphUtility.createFullSceneGraphComponent();
		result.addChild(jrModelCut.getSceneGraphComponent());
		
    	ModelFrame modelTestFrame = new ModelFrame(model, "Модель"){
    		@Override
    		public void sliderStateChanged(double A, double B, double Z) {
    			cutPlane.setA(A);
    			cutPlane.setB(B);
    			cutPlane.setZ(Z*MODEL_HEIGHT);
    			cutPlane.construct();
    			jrCutPlane.update();
    			jrModelCut.update();
    		}
    	};
		
		ResultFrame resultFrame = new ResultFrame(result, "");
    	resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	private static void cut(List<Cell> cells){
		final int MODEL_HEIGHT = cells.get(0).getNumberOfBases();
		
		final CutPlane cutPlane = new CutPlane();
		cutPlane.construct();		

		final ModelCut modelCut = new ModelCut(cells, cutPlane);
		
		List<JrCell> jrCells = new ArrayList<>();
		for (Cell cell : cells){
			jrCells.add(new JrCell(cell));
		}
		final JrCutPlane jrCutPlane = new JrCutPlane(cutPlane);
		final JrModelCut jrModelCut = new JrModelCut(modelCut);
		
		final SceneGraphComponent model = SceneGraphUtility.createFullSceneGraphComponent();
		for (JrCell cell : jrCells){
			model.addChild(cell.getSceneGraphComponent());
		}
		model.addChild(jrCutPlane.getSceneGraphComponent());
		
		final SceneGraphComponent result = SceneGraphUtility.createFullSceneGraphComponent();
		result.addChild(jrModelCut.getSceneGraphComponent());
		
    	ModelFrame modelTestFrame = new ModelFrame(model, "Модель"){
    		@Override
    		public void sliderStateChanged(double A, double B, double Z) {
    			cutPlane.setA(A);
    			cutPlane.setB(B);
    			cutPlane.setZ(Z*MODEL_HEIGHT);
    			cutPlane.construct();
    			jrCutPlane.update();
    			jrModelCut.update();
    		}
    	};
		
		ResultFrame resultFrame = new ResultFrame(result, "");
    	resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}
