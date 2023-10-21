package view.ui.mapcube;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUtessellatorCallback;

/**
 * Hilfsklasse zum Zeichnen von konkaven Polygonen
 */
class tessellCallBack implements GLUtessellatorCallback {
	private GL gl;
	/**
	 * 
	 * @param gl
	 * @param glu
	 */
	public tessellCallBack(GL gl, GLU glu) {
		this.gl = gl;
	}

	/**
	 * 
	 */
	public void begin(int type) {
		gl.glBegin(type);
	}

	/**
	 * 
	 */
	public void end() {
		gl.glEnd();
	}

	/**
	 * Vertex zeichnen 
	 */
	public void vertex(Object vertexData) {
		double[] pointer;
		if (vertexData instanceof double[]) {
			pointer = (double[]) vertexData;
			if (pointer.length == 6)
				gl.glColor3dv(pointer, 3);
			gl.glVertex3dv(pointer, 0);
		}

	}

	/**
	 * 
	 */
	public void vertexData(Object vertexData, Object polygonData) {
	}


	/**
	 * Combine Callback   
	 */
	public void combine(double[] coords, Object[] data, float[] weight, Object[] outData) 
	{
		double[] vertex = new double[6];
		int i;

		vertex[0] = coords[0];
		vertex[1] = coords[1];
		vertex[2] = coords[2];
		for (i = 3; i < 6/* 7OutOfBounds from C! */; i++)
			vertex[i] = weight[0] //
					* ((double[]) data[0])[i]
					+ weight[1]
					* ((double[]) data[1])[i]
					+ weight[2]
					* ((double[]) data[2])[i]
					+ weight[3]
					* ((double[]) data[3])[i];
		outData[0] = vertex;
	}

	/**
	 * 
	 */
	public void combineData(double[] coords, Object[] data, float[] weight, Object[] outData, Object polygonData) {
	}

	/**
	 * 
	 */
	public void error(int errnum) {
		String estring;

		// estring = glu.gluErrorString(errnum);
		// System.err.println("Tessellation Error: " + estring);
		// System.exit(0);
	}

	/**
	 * 
	 */
	public void beginData(int type, Object polygonData) 
	{
	}

	/**
	 * 
	 */
	public void endData(Object polygonData) 
	{
		
	}

	/**
	 * 
	 */
	public void edgeFlag(boolean boundaryEdge) 
	{
	}

	/**
	 * 
	 */
	public void edgeFlagData(boolean boundaryEdge, Object polygonData) 
	{
	}

	/**
	 * 
	 */
	public void errorData(int errnum, Object polygonData) 
	{
	}
}
