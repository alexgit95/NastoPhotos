package alexgit95.batch.NastoPhotos.dao;



import java.util.List;

import alexgit95.batch.NastoPhotos.model.Photos;

public interface DaoServices {
	
	List<Photos> findAll();
	
	void save(Photos toSave);
	

}
