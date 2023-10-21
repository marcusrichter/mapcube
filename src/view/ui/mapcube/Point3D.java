package view.ui.mapcube;


/**
 * Punkt im 3D Raum
 *
 */
public class Point3D {
	private float x;
	private float y;
	private float z;

	/**
	 * Punkt anlegen
	 * @param x X Koordinate
	 * @param y Y Koordinate
	 * @param z Z Koordinate
	 */
	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * X Koordinate setzen
	 * @param x X Koordinate
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * X Koordinate abrufen
	 * @return X Koordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * Y Koordinate setzen
	 * @param y Y Koordinate
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Y Koordinate abrufen
	 * @return Y Koordinate
	 */
	public float getY() {
		return y;
	}

	/**
	 * Z Koordinate setzen
	 * @param z Z Koordinate
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Z Koordinate abrufen
	 * @return Z Koordinate
	 */
	public float getZ() {
		return z;
	}
}
