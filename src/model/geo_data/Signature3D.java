package model.geo_data;

/**
 * Model Klasse für eine dreidimensionale Signatur 
 */
public class Signature3D extends AbstractSignature{
	public static final int SHAPE_PRISM=1000;
	public static final int SHAPE_CYLINDER=1001;
	public static final int SHAPE_CONE=1002;
	public static final int SHAPE_RECTANGULARPRISM=1003;
	public static final int SHAPE_HEXAGONALPRISM=1004;
	public static final int SHAPE_PYRAMID=1005;
	public static final int SHAPE_PRISMLINE=1006;
	public static final int SHAPE_CYLINDERLINE=1007;
	public static final int SHAPE_POLYGONPRISM=1008;
	public static final int SHAPE_SPHERE=1009;
	public static final int SHAPE_CROSS3D=1010;
	public static final int SHAPE_RHOMBIC_PRISM=1011;
	public static final int SHAPE_RECTANGULARLINEPOLYGON=1012;
	public static final int SHAPE_RHOMBLINE=1013;
		
	float height;

	/**
	 * Höhe auslesen
	 * @return Höhe
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Höhe setzen 
	 * @param height Höhe
	 */
	public void setHeight(float height) {
		this.height = height*this.highlightSize;
	}
}
