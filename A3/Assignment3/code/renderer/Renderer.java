package renderer;

import java.awt.Color;
//import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import renderer.Scene.Polygon;

public class Renderer extends GUI {
	private Vector3D lightsourse;
	//private Color color;
	//private  List<Polygon> poly;
	private Scene scene;
	@Override
	protected void onLoad(File file) {
		// TODO fill this in.

		/*
		 * This method should parse the given file into a Scene object, which
		 * you store and use to render an image.
		 */
		List<Polygon> poly = new ArrayList<Polygon>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line ;
			while((line = reader.readLine())!=null) {

				String[]tokens = line.split(",");

				if(tokens.length>3) {

					int r = Integer.parseInt(tokens[0]);
					int g = Integer.parseInt(tokens[1]);
					int b = Integer.parseInt(tokens[2]);
					Color c = new Color(r,g,b);

					float x1 = Float.parseFloat(tokens[3]);
					float y1 = Float.parseFloat(tokens[4]);
					float z1 = Float.parseFloat(tokens[5]);
					Vector3D node1 = new Vector3D(x1,y1,z1);

					float x2 = Float.parseFloat(tokens[6]);
					float y2 = Float.parseFloat(tokens[7]);
					float z2 = Float.parseFloat(tokens[8]);
					Vector3D node2 = new Vector3D(x2,y2,z2);

					float x3 = Float.parseFloat(tokens[9]);
					float y3 = Float.parseFloat(tokens[10]);
					float z3 = Float.parseFloat(tokens[11]);
					Vector3D node3 = new Vector3D(x3,y3,z3);

					poly.add(new Polygon(node1,node2,node3,c));
					//System.out.println(new Polygon(node1,node2,node3,c));

				}else if(tokens.length==3){
					float lightX = Float.valueOf(tokens[0]);
					float lightY = Float.valueOf(tokens[1]);
					float lightZ = Float.valueOf(tokens[2]);
					lightsourse = new Vector3D(lightX,lightY,lightZ);
					//System.out.println("lightsourse:  "+lightsourse);
					//				}else if(tokens.length==1) {
					//					//reader.readLine();
					//				  System.out.println(reader.readLine());
					//				  }
					

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		scene = new Scene(poly,lightsourse);
		scene = Pipeline.scaleScene(scene);
		scene = Pipeline.translateScene(scene);
		//reader.close;
	}

	@Override
	protected void onKeyPress(KeyEvent ev) {
		// TODO fill this in.

		/*
		 * This method should be used to rotate the user's viewpoint.
		 */
		if(scene == null) return;

		if(ev.getKeyCode()==KeyEvent.VK_LEFT) {scene = Pipeline.rotateScene(scene, 0, -0.5f);}
		redraw();
		if(ev.getKeyCode()==KeyEvent.VK_RIGHT) {scene = Pipeline.rotateScene(scene, 0, 0.5f);}
		redraw();
		if(ev.getKeyCode()==KeyEvent.VK_UP) {scene = Pipeline.rotateScene(scene,  -0.5f,0);}
		redraw();
		if(ev.getKeyCode()==KeyEvent.VK_DOWN) {scene = Pipeline.rotateScene(scene,  0.5f,0);}
		redraw();
	}

	@Override
	protected BufferedImage render() {
		// TODO fill this in.

		/*
		 * This method should put together the pieces of your renderer, as
		 * described in the lecture. This will involve calling each of the
		 * static method stubs in the Pipeline class, which you also need to
		 * fill in.
		 */
		Color[][] zbuffer = new Color[CANVAS_WIDTH][CANVAS_HEIGHT];
		float[][] zdepth = new float[CANVAS_WIDTH][CANVAS_HEIGHT];
		for(int i = 0; i < zbuffer.length; i++) {
			for(int j = 0; j < zbuffer[i].length; j++) {
				zdepth[i][j] = Float.POSITIVE_INFINITY;
				zbuffer[i][j] = Color.GRAY;
			}
		}

		if(scene == null) return convertBitmapToImage(zbuffer); //If there's no scene render blank image

		for(Polygon poly: scene.getPolygons()) {
			if(!Pipeline.isHidden(poly)) {
				Color polyColor = Pipeline.getShading(poly, scene.getLight(), new Color(100,100,100), new Color(getAmbientLight()[0], getAmbientLight()[1], getAmbientLight()[2]));
				EdgeList EL = Pipeline.computeEdgeList(poly);
				Pipeline.computeZBuffer(zbuffer, zdepth, EL, polyColor);
			}
		}
		return convertBitmapToImage(zbuffer);
	}

	/**
	 * Converts a 2D array of Colors to a BufferedImage. Assumes that bitmap is
	 * indexed by column then row and has imageHeight rows and imageWidth
	 * columns. Note that image.setRGB requires x (col) and y (row) are given in
	 * that order.
	 */
	private BufferedImage convertBitmapToImage(Color[][] bitmap) {
		BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < CANVAS_WIDTH; x++) {
			for (int y = 0; y < CANVAS_HEIGHT; y++) {
				image.setRGB(x, y, bitmap[x][y].getRGB());
			}
		}
		return image;
	}

	public static void main(String[] args) {
		new Renderer();
	}
}

// code for comp261 assignments
