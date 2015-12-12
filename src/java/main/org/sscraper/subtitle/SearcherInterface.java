/**  
 *  Copyright (C) 2015-2016 dl@zidoo.tv
 */
package org.sscraper.subtitle;

import java.io.File;
import java.util.List;

public interface SearcherInterface {

	/**
	 * Find SubTitle by movie name and language
	 * @param title The movie name
	 * @param language The subtitle language you wanted. could be null, means find all
	 * @return The SubTitle informations if found, null if not found
	 */
	public List<SubTitleInfo> findSubTitle(String title, String language);
	
	/**
	 * Find SubTitle by movie file and language. </p>
	 * By this, we can use file hash to search on OpenSubTitle </p>
	 * @param movie The movie file
	 * @param language The subtitle language you wanted. could be null, means find all
	 * @return The SubTitle informations if found, null if not found
	 */
	public List<SubTitleInfo> findSubTitle(File movie, String language);
	
	/**
	 * Download the SubTitle to specified location
	 * @param subtitle The SubTitle information object
	 * @param location The location where to store the SubTitle
	 * @return 0 if success, other fail
	 */
	public int downlaodSubtitle(SubTitleInfo subtitle, String location);
	
	/**
	 * Download the SubTitle to specified file
	 * @param subtitle The SubTitle information object
	 * @param file The file to save the SubTitle file
	 * @return 0 if success, other fail
	 */
	public int downloadSubTitle(SubTitleInfo subtitle, File file);
}
