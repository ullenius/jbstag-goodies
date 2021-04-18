package se.anosh.jbstag.model;

import java.beans.PropertyChangeListener;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;

public class GbsTag {
	
	private ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(
			this);
	
	private String title = "";
	private String composer = "";
	private String copyright = "";
	
	public GbsTag() {
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		String oldValue = this.title;
		this.title = title;
		changeSupport.firePropertyChange("title", oldValue, title);
	}
	
	public String getComposer() {
		return composer;
	}
	
	public void setComposer(String composer) {
		String oldValue = this.title;
		this.composer = composer;
		changeSupport.firePropertyChange("title", oldValue, title);
	}
	
	public String getCopyright() {
		return copyright;
	}
	
	public void setCopyright(String copyright) {
		String oldValue = this.title;
		this.copyright = copyright;
		changeSupport.firePropertyChange("title", oldValue, title);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener x) {
		changeSupport.addPropertyChangeListener(x);
	}

	public void removePropertyChangeListener(PropertyChangeListener x) {
		changeSupport.removePropertyChangeListener(x);
	}
	
	
	@Override
	public String toString() {
		return "GbsTag [title=" + title + ", composer=" + composer + ", copyright=" + copyright + "]";
	}
	

	
	
}
