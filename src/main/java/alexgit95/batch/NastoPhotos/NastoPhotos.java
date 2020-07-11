package alexgit95.batch.NastoPhotos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

import alexgit95.batch.NastoPhotos.dao.DaoServices;
import alexgit95.batch.NastoPhotos.model.Photos;
import alexgit95.batch.NastoPhotos.utils.Utils;

@SpringBootApplication
public class NastoPhotos {

	private File srcDirectory;
	private Logger logger = LoggerFactory.getLogger(NastoPhotos.class);
	@Autowired
	private DaoServices daoServices;
	
	private static Set<Date> existingDates = new TreeSet<Date>();

	public static void main(String[] args) {
		SpringApplication.run(NastoPhotos.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			srcDirectory= new File("Z:\\");
			
			//Recuperation de la liste de tous les fichiers
			Collection<File> listFiles = FileUtils.listFiles(srcDirectory, new String[] {"jpg","JPG","JPEG","jpeg","png","PNG"}, true);
			logger.info("NB Elements:"+listFiles.size());
			
			List<Photos> existingPhotos = daoServices.findAll();
			
			Consumer<Photos> consumerExistingDates = photo -> { existingDates.add(photo.getDatePrise()); };
			existingPhotos.stream().forEach(consumerExistingDates);
			
			
			Predicate<File> filter = file -> {
				// verifier s'il possede des metadeta exif &&
				// verifier si un fichier avec ce nom existe dans la base
				try {
					Metadata metadata = ImageMetadataReader.readMetadata(file);
					ArrayList<GpsDirectory> gpsDirectories = new ArrayList<>(metadata.getDirectoriesOfType(GpsDirectory.class));
					if (gpsDirectories.size() == 0) {
						return false;
					}
					GpsDirectory gpsDirectory = gpsDirectories.get(0);
					GeoLocation exifLocation = gpsDirectory.getGeoLocation();
					Date gpsDate = gpsDirectory.getGpsDate();
					if(gpsDate==null) {
						gpsDate =Utils.getDateFromFilename(file);
					}
					
					logger.debug(file.getAbsolutePath()+" : "+gpsDate);
					if(gpsDate==null||existingDates.contains(gpsDate)) {
						return false;
					}
					if (exifLocation == null) {
						return false;
					}

					return true;
				} catch (IOException ioe) {
					logger.error("Erreur lors de la recuperation des metadata", ioe);
					return false;
				} catch (ImageProcessingException ipe) {
					logger.error("Erreur lors de la recuperation des metadata", ipe);
					return false;
				}
			};
			
			
			
			Consumer<File> extractAndSave = file -> {
				try {
					// ectraire les metadata
					Metadata metadata = ImageMetadataReader.readMetadata(file);
					ArrayList<GpsDirectory> gpsDirectories = new ArrayList<>(
							metadata.getDirectoriesOfType(GpsDirectory.class));
					GpsDirectory gpsDirectory = gpsDirectories.get(0);
					GeoLocation exifLocation = gpsDirectory.getGeoLocation();
					
					Date gpsDate = gpsDirectory.getGpsDate();
					if(gpsDate==null) {
						gpsDate =Utils.getDateFromFilename(file);
					}

					logger.info("Sauvegarde :" +file.getAbsolutePath() );
										
					//sauvegarder en base
					Photos toSave = new Photos(file.getName(), exifLocation.getLatitude(), exifLocation.getLongitude(), gpsDate);
					daoServices.save(toSave);
					
				} catch (Exception e) {
					logger.error("Erreur lors de l'extraction des metadata", e);
				}

			}; 
				
			listFiles.stream().filter(filter).forEach(extractAndSave);
			logger.info("Fin de l'extraction");
			
		};
	}

	



}
