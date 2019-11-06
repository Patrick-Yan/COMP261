package renderer;

import java.awt.Color;
import java.util.ArrayList;

import renderer.Scene.Polygon;

/**
 * The Pipeline class has method stubs for all the major components of the
 * rendering pipeline, for you to fill in.
 * 
 * Some of these methods can get quite long, in which case you should strongly
 * consider moving them out into their own file. You'll need to update the
 * imports in the test suite if you do.
 */
public class Pipeline {

	/**
	 * Returns true if the given polygon is facing away from the camera (and so
	 * should be hidden), and false otherwise.
	 */
	public static boolean isHidden(Polygon poly) {
		// TODO fill this in.
		Vector3D[] vertices = poly.getVertices();
		Vector3D normal = (vertices[1].minus(vertices[0])).crossProduct(vertices[2].minus(vertices[1]));//2-1 * 3-2
		if(normal.z > 0) {return true;}
		return false;
	}

	/**
	 * Computes the colour of a polygon on the screen, once the lights, their
	 * angles relative to the polygon's face, and the reflectance of the polygon
	 * have been accounted for.
	 * 
	 * @param lightDirection
	 *            The Vector3D pointing to the directional light read in from
	 *            the file.
	 * @param lightColor
	 *            The color of that directional light.
	 * @param ambientLight
	 *            The ambient light in the scene, i.e. light that doesn't depend
	 *            on the direction.
	 */
	public static Color getShading(Polygon poly, Vector3D lightDirection, Color lightColor, Color ambientLight) {
		// TODO fill this in.
		Vector3D[] vertices = poly.getVertices();
		Vector3D normal = (vertices[1].minus(vertices[0])).crossProduct(vertices[2].minus(vertices[1]));//2-1 * 3-2

		float angle = normal.cosTheta(lightDirection);
		if(angle<0) angle = 0;

		Color Refl= poly.getReflectance();

		int r = (int) ((ambientLight.getRed()) * (Refl.getRed()/255.0f) + ((lightColor.getRed()) * (Refl.getRed()/255.0f) * angle));
		int g = (int) ((ambientLight.getGreen()) * (Refl.getGreen()/255.0f)+ ((lightColor.getGreen()) * (Refl.getGreen()/255.0f) * angle));
		int b = (int) ((ambientLight.getBlue()) * (Refl.getBlue()/255.0f)+ ((lightColor.getBlue())* (Refl.getBlue()/255.0f) * angle));

		if(r > 255) r = 255;
		if(g > 255) g = 255;
		if(b > 255) b = 255;
		//System.out.println(r+"	"+g+"	"+b);

		return new Color(r,g,b);
	}

	/**
	 * This method should rotate the polygons and light such that the viewer is
	 * looking down the Z-axis. The idea is that it returns an entirely new
	 * Scene object, filled with new Polygons, that have been rotated.
	 * 
	 * @param scene
	 *            The original Scene.
	 * @param xRot
	 *            An angle describing the viewer's rotation in the YZ-plane (i.e
	 *            around the X-axis).
	 * @param yRot
	 *            An angle describing the viewer's rotation in the XZ-plane (i.e
	 *            around the Y-axis).
	 * @return A new Scene where all the polygons and the light source have been
	 *         rotated accordingly.
	 */
	public static Scene rotateScene(Scene scene, float xRot, float yRot) {
		Transform rotMat = Transform.newXRotation(xRot).compose(Transform.newYRotation(yRot)); //Create rotation matrix

		//rotate all polygons
		ArrayList<Polygon> polygons = new ArrayList<Polygon>();
		for(Polygon p: scene.getPolygons()) {
			Vector3D[] v = p.getVertices();
			for(int i = 0; i < v.length; i++) {
				v[i] = rotMat.multiply(v[i]);
			}
			polygons.add(new Polygon(v[0], v[1], v[2], p.getReflectance()));
		}
		return translateScene(scaleScene(new Scene(polygons ,scene.getLight())));
	}

	/**
	 * This should translate the scene by the appropriate amount.
	 *
	 * @param scene
	 * @return
	 */
	public static Scene translateScene(Scene scene) {

		//get min/max values
		float xMin = Float.MAX_VALUE;
		float yMin = Float.MAX_VALUE;
		float zMax = Float.MIN_VALUE;
		for(Polygon p: scene.polygons) {
			for(Vector3D v: p.vertices) {
				xMin = Math.min(xMin, v.x);
				yMin = Math.min(yMin, v.y);
				zMax = Math.max(zMax, v.z);
			}
		}


		//Translate all poly's accordingly
		Transform translation = Transform.newTranslation(-xMin, -yMin, -zMax);
		ArrayList<Polygon> polygons = new ArrayList<Polygon>();
		for(Polygon p : scene.polygons) {
			Vector3D[] v = p.getVertices();
			for(int i = 0; i < v.length; i++) {
				v[i] = translation.multiply(v[i]);
			}
			polygons.add(new Polygon(v[0], v[1], v[2], p.getReflectance()));
		}

		//Translate light
		translation.multiply(scene.getLight());

		return new Scene(polygons, scene.getLight());
	}

	/**
	 * This should scale the scene.
	 *
	 * @param scene
	 * @return
	 */
	public static Scene scaleScene(Scene scene) {

		//get bounding box
		float xMin = Float.MAX_VALUE;
		float xMax = Float.MIN_VALUE;
		float yMin = Float.MAX_VALUE;
		float yMax = Float.MIN_VALUE;
		for(Polygon p: scene.polygons) {
			for(Vector3D v: p.vertices) {
				xMin = Math.min(xMin, v.x);
				xMax = Math.max(xMax, v.x);
				yMin = Math.min(yMin, v.y);
				yMax = Math.max(yMax, v.y);
			}
		}
		System.out.println(xMin + " " + xMax+" "+yMin +" " + yMax);

		float boxHeight = yMax - yMin;
		float boxWidth = xMax - xMin;
		float s;
		if(boxHeight > boxWidth) {
			s= GUI.CANVAS_HEIGHT/boxHeight;

		}else{ 
			s = GUI.CANVAS_WIDTH/boxWidth;
		}

		if(s <= 0) s = 1;//when I run it ,it shows that s = 0 then  

		//s = 600 / s;//when I run it first time it showed: out of boundary:600 then I 

		System.out.println(s);
		//scale all poly's accordingly
		Transform scale = Transform.newScale(s, s, s);

		ArrayList<Polygon> polygons = new ArrayList<Polygon>();
		for(Polygon p : scene.polygons) {
			Vector3D[] v = p.getVertices();
			for(int i = 0; i < v.length; i++) {
				v[i] = scale.multiply(v[i]);
			}
			polygons.add(new Polygon(v[0], v[1], v[2], p.getReflectance()));
		}

		return new Scene(polygons, scale.multiply(scene.lightPos));
	}

	/**
	 * Computes the edgelist of a single provided polygon, as per the lecture
	 * slides.
	 */
	public static EdgeList computeEdgeList(Polygon poly) {
		// TODO fill this in.
		int startY = Integer.MAX_VALUE;
		int endY = Integer.MIN_VALUE;

		for(Vector3D v: poly.getVertices()) {//try to find startY and endY
			if(startY > v.y) startY = (int) v.y;
			if(endY < v.y) endY = (int) v.y;
		}
		EdgeList edge = new EdgeList(startY, endY);
		//Scan each edge and update the 2-column EdgeList

		Vector3D b;
		Vector3D a;
		for(int i = 0; i < poly.vertices.length; i++){ //traverse every edge
			int j = i+1;
			if(j >= 3) j = 0;

			a = poly.getVertices()[i];
			b = poly.getVertices()[j];

			float xSlope = (b.x-a.x)/(b.y-a.y);//Get slopes
			float zSlope = (b.z-a.z)/(b.y-a.y);
			//Get initial values
			float x = a.x;
			float z = a.z;
			float y = a.y;
			if(a.y < b.y) {	//if direction is going down
				while(y <= Math.round(b.y)) {
					edge.addLeftRow(Math.round(y), x, z);
					x += xSlope;
					z += zSlope;
					y++;
				}
			}else { //if direction is going up
				while(y >= Math.round(b.y)) {
					edge.addRightRow(Math.round(y), x, z);
					x -= xSlope;
					z -= zSlope;
					y = y - 1;
				}
			}
		}
		return edge;
	}

	/**
	 * Fills a zbuffer with the contents of a single edge list according to the
	 * lecture slides.
	 * 
	 * The idea here is to make zbuffer and zdepth arrays in your main loop, and
	 * pass them into the method to be modified.
	 * 
	 * @param zbuffer
	 *            A double array of colours representing the Color at each pixel
	 *            so far.
	 * @param zdepth
	 *            A double array of floats storing the z-value of each pixel
	 *            that has been coloured in so far.
	 * @param polyEdgeList
	 *            The edgelist of the polygon to add into the zbuffer.
	 * @param polyColor
	 *            The colour of the polygon to add into the zbuffer.
	 */
	public static void computeZBuffer(Color[][] zbuffer, float[][] zdepth, EdgeList polyEdgeList, Color polyColor) {
		// TODO fill this in.
		int startY = polyEdgeList.getStartY();
		int endY = polyEdgeList.getEndY();
		for(int y = startY ; y < endY ; y++) {
			
			float slope = (polyEdgeList.getRightZ(y)-polyEdgeList.getLeftZ(y))/(polyEdgeList.getRightX(y)-polyEdgeList.getLeftX(y));
			int x = Math.round(polyEdgeList.getLeftX(y));
			float z = (polyEdgeList.getLeftZ(y)+slope* (x - polyEdgeList.getLeftX(y)));

			while(x < Math.round(polyEdgeList.getRightX(y))) {
				if (x < 0 || x >= zbuffer.length) {
					z += slope;
					x++;
					continue;
				}               
				//if(z<0)z=1;
				if(z < zdepth[x][y]) {
					zbuffer[x][y]= polyColor;
					zdepth[x][y] =z;

				}
				z += slope;
				x++;

			}
		}
	}

}

// code for comp261 assignments
