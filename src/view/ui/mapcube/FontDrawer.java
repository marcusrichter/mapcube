package view.ui.mapcube;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Arrays;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import model.geo_data.AbstractSignature;
import com.sun.opengl.util.GLUT;

public class FontDrawer  {
    public static float scale=0.0006f;
    public static float linewidth=1.0f;
    private static boolean grayout=false;

    /**
     * Zeichnet einen Textstring
     * @param font Schriftart
     * @param string Text
     * @param gl OpenGL Kontext
     * @param center_align soll zentriert dargestellt werden
     * @return
     */
	public static float render(int font, String string,GL gl,boolean center_align) {
    	
    	GLUT glut=new GLUT();
    	gl.glPushMatrix();
    	gl.glLineWidth(linewidth);
    	if(linewidth!=1.0f)
    		linewidth=1.0f;
    			
		//gl.glScalef(0.0006f, 0.0006f, 1.0f);
    	gl.glScalef(scale, scale, 1.0f);
    	float _scale=scale;
    	if(scale!=0.0006f)
    		scale=0.0006f;

    	
        // Center Our Text On The Screen        
        float width = glut.glutStrokeLength(font, string);        
        if(center_align)
        {
        	gl.glTranslatef(-width / 2f, 0, 0);
        }
        
        // Render The Text
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            glut.glutStrokeCharacter(font, c);                       
            
        }
        gl.glPopMatrix();
        return width*_scale;
    }

	/**
	 * Text an einen betimmten Punkt zeichnen
	 * @param text Text
	 * @param surfacePoint kartesische Koordinate auf der Würfelfläche
	 * @param drawable drawable OpenGL
	 * @param color Farbe
	 * @param brightness Helligkeit
	 * @param blending Transparenz
	 */
    public static void drawPointText(String text,Point2D surfacePoint,GLAutoDrawable drawable,Color color,int brightness,float blending)
    {
    	GL gl = drawable.getGL();    	        	
    	gl.glPushMatrix();
    	gl.glTranslatef((float)surfacePoint.getX()+0.1f,(float)surfacePoint.getY()-0.1f, 0.0f);
    	colorToGlColor(gl,color,brightness,blending);
    	render( GLUT.STROKE_ROMAN,text,gl,false);
    	gl.glPopMatrix();
    }

    /**
     * Text mit Schild Grafik zeichnen
     * @param text Text
     * @param surfacePoint kartesische Koordinate auf der Würfelfläche
     * @param drawable drawable OpenGL
     * @param color Farbe
     * @param brightness Helligkeit
     * @param blending Transparenz
     * @param surface ID Würfelfläche
     */
    public static void drawPointTextWithSign(String text,Point2D surfacePoint,GLAutoDrawable drawable,Color color,int brightness,float blending,String surface)
    {
    	GL gl = drawable.getGL();
    	GLUT glut=new GLUT();
    	gl.glPushMatrix();
    	gl.glTranslatef((float)surfacePoint.getX(),(float)surfacePoint.getY(), 0.0f);
    	    
    	float mod_height=0.0f;
    	
    	if(text.equals("Hotel Poll"))
    	{
    		mod_height=0.0f;
    	}
    	else if(text.equals("Museum Poll"))
    	{
    		mod_height=0.4f;
    	}
    	else if(text.equals("Altes Stadttor"))
    	{
    		mod_height=0.2f;
    	}
    	else if(text.equals("Stadtmauern"))
    	{
    		mod_height=0.6f;
    	}
   	
    	
    	gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
    	if(surface.equals(MapCubePanel.SURFACE_LEFT) || surface.equals(MapCubePanel.SURFACE_RIGHT))
    		gl.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
    	
    	//if(surface.equals(MapCubePanel.SURFACE_TOP))    	
    	//	gl.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
    	
    	
    	colorToGlColor(gl,color,brightness,blending);
    	gl.glPushMatrix();
    	float text_width= glut.glutStrokeLength( GLUT.STROKE_ROMAN, text)*scale;
    	float signw=text_width*0.5f+0.05f;
    	float sign_height=0.18f;
    	float side_pole_height=0.25f;
    	float center_pole_height=0.4f+mod_height;
    	if(surface.equals(MapCubePanel.SURFACE_TOP) || surface.equals(MapCubePanel.SURFACE_BOTTOM))
    	{
    		float factory=0.7f;
    		if(surface.equals(MapCubePanel.SURFACE_BOTTOM))
    			factory=0.3f;
    		gl.glTranslatef(0.0f, center_pole_height+sign_height*factory, 0.0f);
    	}
    	if(surface.equals(MapCubePanel.SURFACE_LEFT) || surface.equals(MapCubePanel.SURFACE_RIGHT))
    		gl.glTranslatef(-0.055f, side_pole_height+signw, 0.00f);
    	
    	if(surface.equals(MapCubePanel.SURFACE_TOP))    	
    		gl.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);    	
    	if(surface.equals(MapCubePanel.SURFACE_LEFT))
    	{
    		gl.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
    		gl.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);    		
    	}
    	if(surface.equals(MapCubePanel.SURFACE_RIGHT))
    		gl.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
    	    	
    	if(text.length()==1)
    		FontDrawer.scale=0.0010f;
    	render( GLUT.STROKE_ROMAN,text,gl,true);
    	gl.glPopMatrix();
    	
    	
    	
    	float vertices[]=null;
    	if(surface.equals(MapCubePanel.SURFACE_TOP) || surface.equals(MapCubePanel.SURFACE_BOTTOM))
    	{
    		vertices = new float[]{0.0f, 0.0f, 0.0f,
				0.0f,center_pole_height, 0.0f,
				-1.0f*signw, center_pole_height, 0.0f,
				-1.0f*signw, center_pole_height+sign_height, 0.0f,
				signw,  center_pole_height+sign_height, 0.0f,
				signw, center_pole_height, 0.0f,
				0.0f, center_pole_height, 0.0f
    		};
    	}    
    	else if(surface.equals(MapCubePanel.SURFACE_LEFT) || surface.equals(MapCubePanel.SURFACE_RIGHT))
		{
    		vertices = new float[]{0.0f, 0.0f, 0.0f,
    				0.0f, side_pole_height, 0.0f,
    				-0.5f*sign_height,side_pole_height,0.0f,
    				-0.5f*sign_height,side_pole_height+signw*2.0f,0.0f,
    				0.5f*sign_height,side_pole_height+signw*2.0f,0.0f,
    				0.5f*sign_height,side_pole_height,0.0f,
    				0.0f, side_pole_height, 0.0f
        		};
		}   
    				
    	
    	gl.glLineWidth(0.01f);
		DisplayFunctions.drawVertexArrayObject(gl, vertices, GL.GL_LINE_LOOP, null);
		//gl.glScalef(0.99f, 0.99f, 0.99f);
		if(surface.equals(MapCubePanel.SURFACE_LEFT) || surface.equals(MapCubePanel.SURFACE_TOP))
			gl.glTranslatef(0.0f, 0.0f, 0.01f);
		else
			gl.glTranslatef(0.0f, 0.0f, -0.01f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);	
		float[] vx=Arrays.copyOfRange(vertices, 3, vertices.length-1);
		DisplayFunctions.drawVertexArrayObject(gl, vx, GL.GL_POLYGON, null);
    	
    	gl.glPopMatrix();
    }

    /**
     * Text zeichnen
     * @param text Text
     * @param drawable drawable OpenGL
     * @param color Farbe 
     * @param brightness Helligkeit
     * @param blending Transparenz
     * @param center zentriert
     */
    public static void drawFreeText(String text,GLAutoDrawable drawable,Color color,int brightness,float blending,boolean center)
    {
    	GL gl = drawable.getGL();    	        	
    	colorToGlColor(gl,color,brightness,blending);
    	render( GLUT.STROKE_ROMAN,text,gl,center);
    }
    
    /**
     * Text an einer Linie (z.B. Strasse) zeichnen 
     * @param text Text
     * @param surfacePoints kartesische Punkte, die Linien beschreiben 
     * @param drawable drawable OpenGL
     * @param color Farbe 
     * @param brightness Helligkeit
     * @param blending Tranparenz
     */
    public static void drawLineText(String text,Point2D[] surfacePoints,GLAutoDrawable drawable,Color color,int brightness,float blending)
    {
    	GL gl = drawable.getGL();   
    	Point2D lastTextPoint=null;
    	
    	for(int i=0;i<surfacePoints.length-1;i++)
    	{
    		Point2D startRelPoint=surfacePoints[i];
    		Point2D endRelPoint=surfacePoints[i+1];
    		if(endRelPoint.distance(startRelPoint)>0.4f)
    		{
	    		double textpoint_x=startRelPoint.getX()+(endRelPoint.getX()-startRelPoint.getX())/2.0f;
	    		double textpoint_y=startRelPoint.getY()+(endRelPoint.getY()-startRelPoint.getY())/2.0f;    		
	    		
	    		if(lastTextPoint==null || lastTextPoint.distance(new Point2D.Double(textpoint_x, textpoint_y))>0.7f)
	    		{
	    			lastTextPoint=new Point2D.Double(textpoint_x, textpoint_y);
		    		gl.glPushMatrix();
		    		gl.glTranslatef((float)textpoint_x, (float)textpoint_y, 0.0f);
					float angle = (float) Math.atan2((float) startRelPoint.getY()
							- (float) endRelPoint.getY(), (float) startRelPoint.getX()
							- (float) endRelPoint.getX())
							* 180 / (float) Math.PI;		
					//gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
					if((angle>-45.0f && angle<=45.0f) || (angle>315.0f && angle<=405.0f))
						angle-=180.0f;
					else if((angle<-315.0f && angle>=-405) || (angle<45.0f && angle>=-45.0f))
						angle+=180.0f;
					gl.glRotatef(angle + 180, 0.0f, 0.0f, 1.0f);
					gl.glTranslatef(0.0f,0.04f,0.0f);
			    	colorToGlColor(gl,color,brightness,blending);
			    	text=text.replace("_", "");
			    	render( GLUT.STROKE_ROMAN,text,gl,true);
					gl.glPopMatrix();
	    		}
    		}
    	}
    	
    }
    
    /**
     * Text mit Schild in einem Polygon zeichnen
     * @param text Text
     * @param surfacePoints kartesische Punkte, die Linien beschreiben 
     * @param drawable drawable OpenGL
     * @param color Farbe
     * @param brightness Helligkeit
     * @param blending Transparenz
     * @param surface ID Würfelfläche
     */
    public static void drawPolygonTextWithSign(String text,Point2D[] surfacePoints,GLAutoDrawable drawable,Color color,int brightness,float blending,String surface)
    {
    	double max_x,min_x,max_y,min_y;
    	max_x=surfacePoints[0].getX();
    	min_x=surfacePoints[0].getX();
    	max_y=surfacePoints[0].getY();
    	min_y=surfacePoints[0].getY();
    	
    	for(int i=0;i<surfacePoints.length;i++)
    	{
    		if(surfacePoints [i].getX()<min_x)
    			min_x=surfacePoints [i].getX(); 
    		if(surfacePoints [i].getX()>max_x)
    			max_x=surfacePoints [i].getX(); 
    		if(surfacePoints [i].getY()<min_y)
    			min_y=surfacePoints [i].getY(); 
    		if(surfacePoints [i].getY()>max_y)
    			max_y=surfacePoints [i].getY();  		    		
    	}		            

    	double difx=max_x-min_x;
    	double dify=max_y-min_y;
    	
    	double dif=Math.abs(difx);
    	if(Math.abs(dify)<difx)
    		dif=Math.abs(dify);
    	
    	float scalef=1.0f;
    	if(dif>2.0)
    		scalef=2.0f;
        if(dif>4.0)
    		scalef=3.0f;
    	    		    	    	
    	Point2D ip=new Point2D.Double(min_x+((max_x-min_x)/2),min_y+((max_y-min_y)/2));
    	
    	FontDrawer.drawPointTextWithSign(text, ip, drawable, color, brightness, blending, surface);
    	
    	GL gl = drawable.getGL();    	    
    	colorToGlColor(gl,color.darker().darker(),brightness,blending);
    	gl.glPushMatrix();
    	gl.glTranslatef((float)ip.getX(),(float)ip.getY(), 0.0f);
    	gl.glScalef(scalef, scalef, 1.0f);
    	render( GLUT.STROKE_ROMAN,text,gl,true);
    	gl.glPopMatrix();
//		
    }        

    /**
     * Text in einem Polygon zeichnen
     * @param text Text
     * @param surfacePoints kartesische Punkte, die das Polygon beschreiben 
     * @param drawable 
     * @param color
     * @param brightness
     * @param blending
     */
    public static void drawPolygonText(String text,Point2D[] surfacePoints,GLAutoDrawable drawable,Color color,int brightness,float blending)
    {
    	double max_x,min_x,max_y,min_y;
    	max_x=surfacePoints[0].getX();
    	min_x=surfacePoints[0].getX();
    	max_y=surfacePoints[0].getY();
    	min_y=surfacePoints[0].getY();
    	
    	for(int i=0;i<surfacePoints.length;i++)
    	{
    		if(surfacePoints [i].getX()<min_x)
    			min_x=surfacePoints [i].getX(); 
    		if(surfacePoints [i].getX()>max_x)
    			max_x=surfacePoints [i].getX(); 
    		if(surfacePoints [i].getY()<min_y)
    			min_y=surfacePoints [i].getY(); 
    		if(surfacePoints [i].getY()>max_y)
    			max_y=surfacePoints [i].getY();  		    		
    	}		            
    	
    	float scalef=1.0f;
    	    		    	    	
    	Point2D ip=new Point2D.Double(min_x+((max_x-min_x)/2),min_y+((max_y-min_y)/2));
    	
    	GL gl = drawable.getGL();    	    
    	colorToGlColor(gl,color.darker().darker(),brightness,blending);
    	gl.glPushMatrix();
    	gl.glTranslatef((float)ip.getX(),(float)ip.getY(), 0.0f);
    	gl.glScalef(scalef, scalef, 1.0f);
    	render( GLUT.STROKE_ROMAN,text,gl,true);
    	gl.glPopMatrix();
    }
    
    /**
     * Frabe in OpenGL setzen
     * @param gl OpenGL Kontext
     * @param color Farbe 
     * @param brightness Helligkeit
     * @param blending Transparenz
     */
	private static void colorToGlColor(GL gl, Color color,int brightness,float blending) {
		if(brightness==AbstractSignature.BRIGHTNESS_BRIGHT)
			color=color.brighter();
		if(brightness==AbstractSignature.BRIGHTNESS_DARK)
			color=color.darker();
		
		if(grayout)
		{
			float greyfactor=Math.max(Math.max((float) color.getRed(), (float) color.getGreen()),(float) color.getBlue());
			float red=((float) color.getRed() +greyfactor*3)/ 1023.0f;
			float green=((float) color.getGreen() +greyfactor*3)/ 1023.0f;
			float blue=((float) color.getBlue() +greyfactor*3)/ 1023.0f;
			
			gl.glColor4f(red,
					green,
					blue,blending);			
		}
		else
		{
			gl.glColor4f((float) color.getRed() / 255.0f,
				(float) color.getGreen() / 255.0f,
				(float) color.getBlue() / 255.0f,blending);
		}
	}

	/**
	 * Status( Farbe ausgegraut) abfragen
	 * @return
	 */
    public static boolean isGrayout() {
		return grayout;
	}

    /**
     * Status( Farbe ausgegraut) setzen
     * @param grayout
     */
	public static void setGrayout(boolean grayout) {
		FontDrawer.grayout = grayout;
	}
}
