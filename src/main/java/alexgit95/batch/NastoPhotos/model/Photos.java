package alexgit95.batch.NastoPhotos.model;



import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
@DynamoDBTable(tableName="Photos")
public class Photos {
	
	private String name;
	private double lattitude;
	private double longitude;
	private Date datePrise;
	
	@DynamoDBHashKey(attributeName="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@DynamoDBAttribute(attributeName="lattitude")
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	@DynamoDBAttribute(attributeName="longitude")
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	@DynamoDBAttribute(attributeName="dateprise")
	public Date getDatePrise() {
		return datePrise;
	}
	public void setDatePrise(Date datePrise) {
		this.datePrise = datePrise;
	}
	public Photos(String name, double lattitude, double longitude, Date datePrise) {
		super();
		this.name = name;
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.datePrise = datePrise;
	}
	public Photos() {
		super();
	}
	
	

}
