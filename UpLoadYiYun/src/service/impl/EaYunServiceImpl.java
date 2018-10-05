/**
 * 
 */
package service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import service.EaYunService;
import util.UpLoadUtil;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import constants.UpLoadText;

/**
 * @author 周志豪
 *
 */
public class EaYunServiceImpl implements EaYunService {
	UpLoadUtil util=new UpLoadUtil();
	@Override
	public String eaYunUpLoad(File file,String key) {		
					
		AWSCredentials credentials = new BasicAWSCredentials(UpLoadText.ACCESS_KEY, UpLoadText.SECRET_KEY);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		clientConfig.setSignerOverride(UpLoadText.CHECK);// 设置使用V2认证
		AmazonS3 client = new AmazonS3Client(credentials, clientConfig);
		client.setEndpoint(UpLoadText.endpoint);
		try {
						
			PutObjectRequest obj = new PutObjectRequest(UpLoadText.BUCKET, key, file);			
			// 默认添加public权限
			client.putObject(obj);			
			return obj.getKey();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	@Override
	public InputStream eaYunDownLoad(String filename) {		
		InputStream in=null;
		AWSCredentials credentials = new BasicAWSCredentials(UpLoadText.ACCESS_KEY, UpLoadText.SECRET_KEY);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		clientConfig.setSignerOverride(UpLoadText.CHECK);// 设置使用V2认证
		AmazonS3 client3 = new AmazonS3Client(credentials, clientConfig);
		client3.setEndpoint(UpLoadText.endpoint);
		
		try {			
			S3Object object = client3.getObject(new GetObjectRequest( UpLoadText.BUCKET, filename));
			System.out.println(object.getObjectMetadata().toString());
			in = new BufferedInputStream(object.getObjectContent());
			return in;
		} catch (Exception e) {
			e.printStackTrace();			
			return null;
		}

	}


}
