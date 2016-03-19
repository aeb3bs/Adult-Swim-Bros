package edu.virginia.engine.dynamodbmanager;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Sprite;

public class DynamoDBManager {
	public static final String AWS_ACCESS_KEY_ID="";
	public static final String AWS_SECRET_ACCESS_KEY="";
	private static int userId;
	private static int enemyId;
	private static DynamoDBManager instance = new DynamoDBManager();
	private DynamoDB db;
	public DynamoDBManager()
	{
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY);
		db = new DynamoDB(new AmazonDynamoDBClient(
				new ProfileCredentialsProvider()));
	}
	public static DynamoDBManager getInstance()
	{
		return instance;
	}
	public void pushSpriteProperties(Sprite s)
	{
		if(!(s.getPosition().getX()<500 && s.getPosition().getX()>0) || !(s.getPosition().getY()<500 && s.getPosition().getY()>0))
			return;
		
		Table table = db.getTable("Sprites");
		
		Map<String, String> expressionAttributeNames = new HashMap<String, String>();
		expressionAttributeNames.put("#Px", "xPos");
		expressionAttributeNames.put("#Py", "yPos");
		expressionAttributeNames.put("#currentframe", "currentframe");
		
		Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
		expressionAttributeValues.put(":val1",
		    s.getPosition().getX());
		expressionAttributeValues.put(":val2",
			    s.getPosition().getY());
		
		if(s instanceof AnimatedSprite)
		{
			AnimatedSprite temp = (AnimatedSprite)s;
			expressionAttributeValues.put(":val3",
				    temp.getCurrentFrame());
		}
		
		
		UpdateItemOutcome outcome =  table.updateItem(
			    "id",          // key attribute name
			    this.userId,           // key attribute value
			    "set #Px = :val1, #Py = :val2, #currentframe = :val3", // UpdateExpression
			    expressionAttributeNames,
			    expressionAttributeValues);
	}
	
	public void pullSpriteProperties(Sprite s)
	{
		DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
			    new ProfileCredentialsProvider()));

		Table table = dynamoDB.getTable("Sprites");

		Item item = table.getItem("id", this.enemyId);
		
		int x = item.getInt("xPos");
		int y = item.getInt("yPos");
		Point newPosition = new Point(x, y);
		s.setPosition(newPosition);
		if(s instanceof AnimatedSprite)
		{

			int currentframe = item.getInt("currentframe");
			AnimatedSprite temp = (AnimatedSprite)s;
			if(temp.getCurrentFrame()!=currentframe)
			{
				temp.setCurrentFrame(currentframe);
				BufferedImage image = temp.getImage();
				temp.setImage(image);
			}
			
			
		}
	}
	public void setUserId(int userId)
	{
		DynamoDBManager.userId = userId;
	}
	public void setEnemyId(int enemyId)
	{
		this.enemyId = enemyId;
	}
	
	
}
