package controller.controlsystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.geo_data.GeoObject;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.vividsolutions.jts.geom.Coordinate;

/***
 * Klasse lädt XML Datein und erzeugt Geoobjekten
 *
 */
public class GeoObjectLoader {

	private static GeoObjectLoader instance=null;
	
	private ArrayList<GeoObject> geoObjects=new ArrayList<GeoObject>();
	private String file="C:\\temp\\testdata2.xml";
	
	private URL codebase=null;	
	
	/**
	 * Codebase für den Pfad zur XML Datei auslesen
	 * @return Codebase für den Pfad zur XML Datei
	 */
    public URL getCodebase() {
		return codebase;
	}
    
    /**
     * Codebase für den Pfad zur XML Datei setzen
     * @param codebase Codebase für den Pfad zur XML Datei
     */
	public void setCodebase(URL codebase) {
		this.codebase = codebase;
	}

	/**
	 * Singleton für GeoObjectLoader abrufen
	 * @return
	 */
	public static GeoObjectLoader getInstance()
	{
		if(instance==null)
			instance=new GeoObjectLoader();
		return instance;
	}
	
	/**
	 * geladene Geoobjekte auslesen
	 * @return Geoobjekte
	 */
	public ArrayList<GeoObject> getGeoObjects() {
		return geoObjects;
	}
	
	/**
	 * Geoobjekte setzen 
	 * @param geoObjects
	 */
	public void setGeoObjects(ArrayList<GeoObject> geoObjects) {
		this.geoObjects = geoObjects;
	}
	
	/**
	 * Filename für die XML Datei auslesen
	 * @return Filename
	 */
	public String getFile() {
		return file;
	}
	
	/**
	 * Filename für die XML Datei setzen
	 * @param file
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	/**
	 * alle Geoobjekte aus der XML Datei laden und erzeugen
	 * @param use Verwendungszweck(Karte, Anwendungsfall,....)
	 * @return Liste mit Geoobjekten
	 */
	public ArrayList<GeoObject> loadAndCreateObjects(String use)
	{
		try
		{
			Document fileGeoObjects=null;
			if(this.codebase==null)
				fileGeoObjects=this.loadFileFromLocal();
			else
				fileGeoObjects=this.loadFileFromUrl();
			this.geoObjects=this.create(fileGeoObjects,use);
			return this.geoObjects;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.printf(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Liste von Geoobjekten in einer XML Datei spiechern
	 * @param geoObjects
	 * @return Operation erfolgreich?
	 */
	public boolean saveObjects(ArrayList<GeoObject> geoObjects)
	{
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		xml+="	<objects>\n";
		for(GeoObject geoObject:geoObjects)
			xml+=geoObject.toXml();
		xml+="</objects>";
		
		//File outFile = new File("C:\\Users\\x\\Documents\\dev\\diplom\\trunk\\marcusrichter\\Implementierung\\MapCube\\src\\testdata2.xml");
		File outFile = new File(this.file);
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
			String[] lines=xml.split("\n");
			for(int i=0;i<lines.length;i++)
			{
				bw.write(lines[i]);
				bw.newLine();
			}					
			bw.close();			
		}
		catch(Exception ex)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * anhand einer XML Dokumentenstrukur die Geoobjekte erzeugen
	 * @param fileGeoObjects
	 * @param use Verwendungszweck(Karte, Anwendungsfall,....)
	 * @return Liste mit Geoobjekten
	 */
	private ArrayList<GeoObject> create(Document fileGeoObjects,String use) {
		  ArrayList<GeoObject> geoObjects=new ArrayList<GeoObject>(); 
		  try {			  		  
			  fileGeoObjects.getDocumentElement().normalize();		  
			  NodeList nodeLst = fileGeoObjects.getElementsByTagName("object");
			 // System.out.println("Information of all employees");		  
			  		  
			  for (int s = 0; s < nodeLst.getLength(); s++) {
	
			    Node objNode = nodeLst.item(s);
			    
			    if (objNode.getNodeType() == Node.ELEMENT_NODE) {
			      GeoObject geoObject=new GeoObject();		      
			  
			      NodeList propLst=objNode.getChildNodes();
			      for(int i=0;i<propLst.getLength();i++)
			      {
			    	  if(propLst.item(i).hasChildNodes())
			    	  {
			    		  String name=propLst.item(i).getNodeName();		    	  
			    		  String val=propLst.item(i).getFirstChild().getNodeValue();
			    		  geoObject.setAttribute(name, val);
	
			    	  }
			      }
	    		  if(use=="" || geoObject.getUse().equals(use))
	    			  geoObjects.add(geoObject);
	    		  
	    		  if(geoObject.getName().equals("Koeln"))
	    		  {    			  
	    			  Coordinate[] coordinates=geoObject.getGeometry().getCoordinates();
	    			  double minx=coordinates[0].x;
	    			  double maxx=coordinates[0].x;
	    			  double miny=coordinates[0].y;
	    			  double maxy=coordinates[0].y;
	
	    			  for(Coordinate coordinate:coordinates)
	    			  {
	    				if(coordinate.x<minx)
	    					minx=coordinate.x;
	    				if(coordinate.y<miny)
	    					miny=coordinate.y;
	    				if(coordinate.x>maxx)
	    					maxx=coordinate.x;
	    				if(coordinate.y>maxy)
	    					maxy=coordinate.y;
	    			  }
	    		  }
			    }
			  }
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		  
		  //Signaturen zuordnen
		  for(GeoObject geoObject:geoObjects)
			  VisualMapper.getInstance().assignSignatures(geoObject);
		  
		  return geoObjects;
	}
	
	/**
	 * XML Datei anhand einer URL laden
	 * @return XML Dokumentenstruktur
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private Document loadFileFromUrl() throws IOException, SAXException, ParserConfigurationException
	{
		URL myFile = new URL(this.getCodebase(), "testdata2.xml");
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new InputStreamReader(myFile.openStream())));
		return doc;
	}
	
	/**
	 * XML Datei aus dem Dateisytem laden
	 * @return XML Dokumentenstruktur
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private Document loadFileFromLocal() throws IOException, SAXException, ParserConfigurationException
	{
		  File file = new File(this.file);		  
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = dbf.newDocumentBuilder();
		  Document doc = db.parse(file);
		return doc;
	}
}
