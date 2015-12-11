/**  
 *  Copyright (C) 2015-2016 dl@zidoo.tv
 */
package org.sscraper.subtitle;

import java.util.List;

import org.sscraper.subtitle.qqmedia.QqMediaSearcher;
import org.sscraper.subtitle.shooter.ShooterSearcher;

public class SubTitleProcess {

	public static List<SubTitleInfo> findSubTitle(String title, String language) {		
		//return new ShooterSearcher().findSubTitle(title, language);
		return new QqMediaSearcher().findSubTitle(title, language);
	}
}
