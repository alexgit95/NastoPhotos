package alexgit95.batch.NastoPhotos.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static Date getDateFromFilename(File fichier) {

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd'_'HHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("'WP_'yyyyMMdd");
		SimpleDateFormat sdf3= new SimpleDateFormat("'WP_'yyyyMMdd'_'HH'_'mm'_'ss'_Pro'");
		SimpleDateFormat sdf4= new SimpleDateFormat("'IMG_'yyyyMMdd'_'HHmmss");
		try{
			if(fichier.getName().startsWith("WP_")){
				
				if(fichier.getName().contains("_Pro")){
					Date parse = sdf3.parse(fichier.getName());
					return parse;
					
				}else{
					Date parse = sdf2.parse(fichier.getName().substring(0,11));
					return parse;
				}
				
			}else{
				if(fichier.getName().startsWith("IMG_")) {
					return sdf4.parse(fichier.getName());
				}else {
					return sdf1.parse(fichier.getName());
				}
			
				
			}
		} catch (ParseException e) {
			return null;
		}

	}

}
