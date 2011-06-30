/**
 * 
 */
package biz.c24.io.spring.source;

import java.io.InputStream;
import java.io.Reader;

import biz.c24.io.api.presentation.BinarySource;
import biz.c24.io.api.presentation.Source;

/**
 * @author askogman
 * 
 */
public class BinarySourceFactory implements SourceFactory {

	private String encoding;

	private Boolean endOfDataRequired = null;

	private Integer lookAhead = null;

	private Integer lookBehind = null;

	/**
	 * 
	 */
	public BinarySourceFactory() {
	}

	public Boolean getEndOfDataRequired() {
		return endOfDataRequired;
	}

	public Integer getLookAhead() {
		return lookAhead;
	}

	public Integer getLookBehind() {
		return lookBehind;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.c24.io.spring.source.SourceFactory#getSource(java.io.InputStream)
	 */
	public Source getSource(InputStream stream) {

		BinarySource binarySource = new BinarySource();

		binarySource.setInputStream(stream);

		configure(binarySource);

		return binarySource;

	}

	final protected void configure(BinarySource binarySource) {

		// Only set the property if a value has been provided to one of the
		// setters. If not, let the underlying Source retain the default.

		if (encoding != null)
			binarySource.setEncoding(encoding);
		if (endOfDataRequired != null)
			binarySource.setEndOfDataRequired(endOfDataRequired);
		if (lookAhead != null)
			binarySource.setLookAhead(lookAhead);
		if (lookBehind != null)
			binarySource.setLookBehind(lookBehind);

		doConfigure(binarySource);

	}

	/**
	 * Override this to provide configuration to the source
	 * 
	 * @param binarySource
	 */
	protected void doConfigure(BinarySource binarySource) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see biz.c24.io.spring.source.SourceFactory#getSource(java.io.Reader)
	 */
	public Source getSource(Reader reader) {
		BinarySource binarySource = new BinarySource();

		binarySource.setReader(reader);

		configure(binarySource);

		return binarySource;
	}

	public void setEndOfDataRequired(Boolean endOfDataRequired) {
		this.endOfDataRequired = endOfDataRequired;
	}

	public void setLookAhead(Integer lookAhead) {
		this.lookAhead = lookAhead;
	}

	public void setLookBehind(Integer lookBehind) {
		this.lookBehind = lookBehind;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
