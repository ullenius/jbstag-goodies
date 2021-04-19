package se.anosh.jbstag.model;

import java.beans.PropertyChangeListener;
import java.util.Objects;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;

public class GbsBean {
	
	private ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(
			this);
	
	private String title = "";
	private String composer = "";
	private String copyright = "";
	private String filename = "";
	
	
	public GbsBean() {
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
		String oldValue = this.composer;
		this.composer = composer;
		changeSupport.firePropertyChange("composer", oldValue, composer);
	}
	
	public String getCopyright() {
		return copyright;
	}
	
	public void setCopyright(String copyright) {
		String oldValue = this.copyright;
		this.copyright = copyright;
		changeSupport.firePropertyChange("copyright", oldValue, copyright);
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		String oldValue = this.filename;
		this.filename = filename;
		changeSupport.firePropertyChange("filename", oldValue, filename);
	}

	public void addPropertyChangeListener(PropertyChangeListener x) {
		changeSupport.addPropertyChangeListener(x);
	}

	public void removePropertyChangeListener(PropertyChangeListener x) {
		changeSupport.removePropertyChangeListener(x);
	}

	@Override
	public String toString() {
		return "Bean [title=" + title + ", composer=" + composer + ", copyright=" + copyright + ", filename=" + filename
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(composer, copyright, filename, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GbsBean other = (GbsBean) obj;
		return Objects.equals(composer, other.composer) && Objects.equals(copyright, other.copyright)
				&& Objects.equals(filename, other.filename) && Objects.equals(title, other.title);
	}
	
	
	
}
