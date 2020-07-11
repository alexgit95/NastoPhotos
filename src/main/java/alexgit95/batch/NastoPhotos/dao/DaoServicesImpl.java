package alexgit95.batch.NastoPhotos.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import alexgit95.batch.NastoPhotos.model.Photos;

@Component
public class DaoServicesImpl implements DaoServices {
	
	private AmazonDynamoDB client;
	private DynamoDBMapper mapper;
	
	
	public DaoServicesImpl() {
		super();
		client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_3).build();
		mapper = new DynamoDBMapper(client);
	}

	@Override
	public List<Photos> findAll() {
		return mapper.scan(Photos.class, new DynamoDBScanExpression());
	}

	@Override
	public void save(Photos toSave) {
		mapper.save(toSave);

	}

}
