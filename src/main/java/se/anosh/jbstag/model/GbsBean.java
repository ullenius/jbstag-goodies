package se.anosh.jbstag.model;

import java.beans.PropertyChangeListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;

public class GbsBean {

	public static final String PROPERTY_COMPOSER = "composer";
	public static final String PROPERTY_TITLE = "title";
	public static final String PROPERTY_FILENAME = "filename";
	public static final String PROPERTY_COPYRIGHT= "copyright";
	public static final String PROPERTY_FULL_PATH = "fullpath";

	private ExtendedPropertyChangeSupport changeSupport = new ExtendedPropertyChangeSupport(
			this);
	
	private String title = "";
	private String composer = "";
	private String copyright = "";
	private String filename = "";
	private Path fullpath;

	public GbsBean() {
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		String oldValue = this.title;
		this.title = title;
		changeSupport.firePropertyChange(PROPERTY_TITLE, oldValue, title);
	}
	
	public String getComposer() {
		return composer;
	}
	
	public void setComposer(String composer) {
		String oldValue = this.composer;
		this.composer = composer;
		changeSupport.firePropertyChange(PROPERTY_COMPOSER, oldValue, composer);
	}
	
	public String getCopyright() {
		return copyright;
	}
	
	public void setCopyright(String copyright) {
		String oldValue = this.copyright;
		this.copyright = copyright;
		changeSupport.firePropertyChange(PROPERTY_COPYRIGHT, oldValue, copyright);
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		String oldValue = this.filename;
		this.filename = filename;
		changeSupport.firePropertyChange(PROPERTY_FILENAME, oldValue, filename);
	}

	public void setFullpath(String newPath) {
		String oldValue = (fullpath != null) ? fullpath.toString() : "";
		fullpath = (newPath != null) ? Paths.get(newPath) : null;
		changeSupport.firePropertyChange(PROPERTY_FULL_PATH, oldValue, newPath);
	}

	public String getFullpath() {
		return (fullpath != null) ? fullpath.toString() : "";
	}

	public void addPropertyChangeListener(PropertyChangeListener x) {
		changeSupport.addPropertyChangeListener(x);
	}

	public void removePropertyChangeListener(PropertyChangeListener x) {
		changeSupport.removePropertyChangeListener(x);
	}

	@Override
	public String toString() {
		return "GbsBean{" +
				", title='" + title + '\'' +
				", composer='" + composer + '\'' +
				", copyright='" + copyright + '\'' +
				", filename='" + filename + '\'' +
				", fullpath=" + ((fullpath != null) ? fullpath : "") +
				'}';
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
