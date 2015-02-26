package main.java;

import java.util.List;

/**
 * 
 * @author swfraser
 *  The Difference class holds a list of the comparable objects
 *  These lists will contain the segments which were divided up by the difference engine.
 *  The segment Object must be a comparable object.  (Base classes will not work)
 *  If you use a base class object, they will be converted to the Java wrapper class by the difference engine.
 *
 */
public class Difference  {
	enum PLACEMENT {A_ONLY, B_ONLY, BOTH};
	PLACEMENT m_location; // This determines the placement of this segment.
	List<?> m_segment;    // This is a list of the segment for this classes dataType 
	
	Difference(PLACEMENT location, List<Object> segment){
		m_segment = segment;
		m_location = location;
	}
	
	Difference(){
	}
	
	public void setSegment(List<?> m_obj1) {
		m_segment = m_obj1;
	}

	public List<?> getSegment() {
		return m_segment;
	}
	
	public void setLocation(PLACEMENT  location) {
		m_location = location;
	}

	public PLACEMENT getLocation() {
		return m_location;
	}
}