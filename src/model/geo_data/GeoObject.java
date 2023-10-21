package model.geo_data;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * Model Klasse für ein Geoobjekt
 */
public class GeoObject {
	
	public final static String OBJTYPE_STREET="OBJTYPE_STREET";
	public final static String OBJTYPE_FREEWAY="OBJTYPE_FREEWAY";
	public final static String OBJTYPE_VILLAGE="OBJTYPE_VILLAGE";
	public final static String OBJTYPE_PARK="OBJTYPE_PARK";
	public final static String OBJTYPE_SEA="OBJTYPE_SEA";
	public final static String OBJTYPE_RIVER="OBJTYPE_RIVER";
	public final static String OBJTYPE_SIGHTSEEING="OBJTYPE_SIGHTSEEING";
	public final static String OBJTYPE_TRAINSTATION="OBJTYPE_TRAINSTATION";
	public final static String OBJTYPE_TOURISTREGION="OBJTYPE_TOURISTREGION";
	public final static String OBJTYPE_HOTEL="OBJTYPE_HOTEL";
	public final static String OBJTYPE_FUELSTATION="OBJTYPE_FUELSTATION";
	public final static String OBJTYPE_RESTINGPLACE="OBJTYPE_RESTINGPLACE";
	public final static String OBJTYPE_PERSON="OBJTYPE_PERSON";
	public final static String OBJTYPE_MARKER="OBJTYPE_MARKER";
	public final static String OBJTYPE_ROUTE="OBJTYPE_ROUTE";
	public final static String OBJTYPE_ROUTESTART="OBJTYPE_ROUTESTART";
	public final static String OBJTYPE_ROUTEEND="OBJTYPE_ROUTEEND";
	public final static String OBJTYPE_RESTAURANT="OBJTYPE_RESTAURANT";
	public final static String OBJTYPE_RELATION_LINE="OBJTYPE_RELATION_LINE";	
	
	public final static String ATTR_NAME="name";
	public final static String ATTR_DESCRIPTION="description";
	public final static String ATTR_OBJTYPE="objclass";
	public final static String ATTR_GEOMETRY="geometry";
	public final static String ATTR_USE="use";
	public final static String ATTR_ID="id";
	
	public final static String USE_MAP="USE_MAP";
	public final static String USE_USERTEST_LOCALIZATION="USE_USERTEST_LOCALIZATION";
	public final static String USE_USERTEST_LOCALIZATION2="USE_USERTEST_LOCALIZATION2";
	public final static String USE_USERTEST_NAVIGATION="USE_USERTEST_NAVIGATION";
	public final static String USE_USERTEST_SEARCH="USE_USERTEST_SEARCH_FUELSTATION";
	public final static String USE_USERTEST_SEARCH_RESTAURANT="USE_USERTEST_SEARCH_RESTAURANT";
	public final static String USE_USERTEST_SEARCH_PERSON="USE_USERTEST_SEARCH_PERSON";
	public final static String USE_USERTEST_SEARCH_FUELSTATION="USE_USERTEST_SEARCH_FUELSTATION";
	public final static String USE_USERTEST_IDENTIFICATION="USE_USERTEST_IDENTIFICATION";
	public final static String USE_USERTEST_IDENTIFICATION_PERSON1="USE_USERTEST_IDENTIFICATION_PERSON1";
	public final static String USE_USERTEST_IDENTIFICATION_PERSON2="USE_USERTEST_IDENTIFICATION_PERSON2";
	public final static String USE_USERTEST_IDENTIFICATION_PERSON3="USE_USERTEST_IDENTIFICATION_PERSON3";
	public final static String USE_USERTEST_SEARCH_PROACTIVE="USE_USERTEST_SEARCH_PROACTIVE";
	public final static String USE_USERTEST_NAVIGATION_PROACTIVE="USE_USERTEST_NAVIGATION_PROACTIVE";
	public final static String USE_USERTEST_NAVIGATION_PROACTIVE_ROUTE="USE_USERTEST_NAVIGATION_PROACTIVE_ROUTE";
	
	private Geometry geometry;
	private String objtype="";
	private String name="";
	private String description="";
	private String use="";
	private int id=-1;
	
	private AbstractSignature focusSignature=null;
	private AbstractSignature contextSignature=null;
	private boolean highlighed;
	private boolean hiddenFocus=false;
	private boolean hiddenContext=false;
	private boolean selectable=false;

	/**
	 * Beschreibung Geometrie (JTS) auslesen 
	 * @return Beschreibung Geometrie (JTS)
	 */
	public Geometry getGeometry() {
		return geometry;
	}
	
	/**
	 * Beschreibung Geometrie (JTS) setzen
	 * @param geometry Beschreibung Geometrie (JTS)
	 */
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
	/**
	 * Objekttyp auslesen
	 * @return Objekttyp
	 */
	public String getObjtype() {
		return objtype;
	}
	
	/**
	 * Objekttyp setzen
	 * @param objtype Objekttyp
	 */
	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	/**
	 * Name auslesen
	 * @return Name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Name setzen
	 * @param name Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Beschreibung auslesen
	 * @return Beschreibung
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Beschreibung setzen 
	 * @param description Beschreibung
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Signatur für den Fokus auslesen
	 * @return Signatur
	 */
	public AbstractSignature getFocusSignature() {
		return focusSignature;
	}
	
	/**
	 * Signatur für den Fokus setzen
	 * @param focusSignature Signatur
	 */
	public void setFocusSignature(AbstractSignature focusSignature) {
		this.focusSignature = focusSignature;
	}
	
	/**
	 * Signatur für den Kontext auslesen
	 * @return Signatur
	 */
	public AbstractSignature getContextSignature() {
		return contextSignature;
	}
	
	/**
	 * Signatur für den Kontext setzen
	 * @param contextSignature Signatur
	 */
	public void setContextSignature(AbstractSignature contextSignature) {
		this.contextSignature = contextSignature;
	}
	
	/**
	 * Status (Objekt highlightet) abrufen 
	 * @return Status (Objekt highlightet)
	 */
	public boolean isHighlighed() {
		return highlighed;
	}
	
	/**
	 * Status (Objekt highlightet) setzen
	 * @param highlighed Status (Objekt highlightet)
	 */
	public void setHighlighed(boolean highlighed) {
		this.highlighed = highlighed;
	}
	
	/**
	 * Status (Objekt versteckt in Fokus) abrufen
	 * @return Status (Objekt versteckt in Fokus)
	 */
	public boolean isHiddenFocus() {
		return hiddenFocus;
	}
	
	/**
	 * Status (Objekt versteckt in Fokus) setzen
	 * @param hiddenFocus Status (Objekt versteckt in Fokus)
	 */
	public void setHiddenFocus(boolean hiddenFocus) {
		this.hiddenFocus = hiddenFocus;
	}
	
	/**
	 * Status (Objekt versteckt in Kontext) abrufen
	 * @return Status (Objekt versteckt in Kontext)
	 */
	public boolean isHiddenContext() {
		return hiddenContext;
	}
	
	/**
	 * Status (Objekt versteckt in Kontext) setzen
	 * @param hiddenFocus Status (Objekt versteckt in Kontext)
	 */
	public void setHiddenContext(boolean hiddenContext) {
		this.hiddenContext = hiddenContext;
	}
	
	/**
	 * bestimmtes Attribut anhand eines Namens setzen
	 * @param attrname Attribut Name
	 * @param value Wert
	 * @throws ParseException
	 */
	public void setAttribute(String attrname,String value) throws ParseException
	{
		if(attrname.equals(ATTR_GEOMETRY))
		    this.setGeometry(new WKTReader().read(value));	
		else if(attrname.equals(ATTR_NAME))
			this.setName(value);
		else if(attrname.equals(ATTR_DESCRIPTION))
			this.setDescription(value);
		else if(attrname.equals(ATTR_OBJTYPE))
			this.setObjtype(value);
		else if(attrname.equals(ATTR_USE))
			this.setUse(value);
		else if(attrname.equals(ATTR_ID))
			this.setId(Integer.parseInt(value));		
	}
	
	/**
	 * Attribute zu einer XML Beschreibung umwandeln
	 * @return XML Beschreibung
	 */
	public String toXml()
	{
		String xml="<object>\n";
		xml+="	<geometry>"+this.geometry.toText()+"</geometry>\n";
		xml+="	<objclass>"+this.objtype+"</objclass>\n";
		xml+="	<name>"+this.name+"</name>\n";
		xml+="	<description>"+this.description+"</description>\n";
		xml+="	<use>"+this.getUse()+"</use>\n";
		xml+="	<id>"+this.getId()+"</id>\n";
		xml+="</object>\n";
		return xml;
	}
	
	/**
	 * Verwendungszweck auslesen
	 * @return Verwendungszweck
	 */
	public String getUse() {
		return use;
	}
	
	/**
	 * Verwendungszweck setzen
	 * @param use
	 */
	public void setUse(String use) {
		this.use = use;
	}	
	
	/**
	 * Status (kann das Objekt ausgewählt werden) auslesen
	 * @return Status (kann das Objekt ausgewählt werden)
	 */
	public boolean isSelectable() {
		return selectable;
	}
	
	/**
	 * Status (kann das Objekt ausgewählt werden) setzen
	 * @param selectable Status (kann das Objekt ausgewählt werden)
	 */
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	
	/**
	 * ID auslesen
	 * @return ID 
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * ID setzen
	 * @param id ID
	 */
	public void setId(int id) {
		this.id = id;
	}

}
