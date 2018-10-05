/**
 * 
 */
package service.impl;

import java.io.File;
import service.AliYunService;
import util.UpLoadUtil;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;

import constants.UpLoadText;

/**	
 * @author 周志豪
 *
 */
public class AliYunServiceImpl implements AliYunService {
	UpLoadUtil util=new UpLoadUtil();
	@Override
	public String aliYunUpLoad(File file,String key) {
		// 上传文件的路径		
				
				// 创建OSSClient实例
				OSSClient ossClient = new OSSClient(UpLoadText.endpoint, UpLoadText.ACCESS_KEY, UpLoadText.SECRET_KEY);

				try {// 设置新的文件名字
				
					// 设置断点续传请求
					UploadFileRequest uploadFileRequest = new UploadFileRequest( UpLoadText.BUCKET, key);
					// 待上传的本地文件
					uploadFileRequest.setUploadFile(file.getPath());
					// 设置并发下载数，默认1// 指定上传并发线程数
					uploadFileRequest.setTaskNum(5);
					// 设置分片大小，默认100KB// 指定上传的分片大小
					uploadFileRequest.setPartSize(1024 * 1024 * 1);
					// 开启断点续传，默认关闭
					uploadFileRequest.setEnableCheckpoint(true);
					// 断点续传上传
					UploadFileResult uploadResult = ossClient .uploadFile(uploadFileRequest);
					// PutObjectResult result = ossClient.putObject("your bucket", "2.txt", new File("C:\\Aliyun.png"));
					CompleteMultipartUploadResult multipartUploadResult = uploadResult .getMultipartUploadResult();
					System.out.println(multipartUploadResult.getETag());

				} catch (OSSException oe) {
					System.out .println("Caught an OSSException, which means your request made it to OSS, "
									+ "but was rejected with an error response for some reason.");
					System.out.println("Error Message: " + oe.getErrorCode());
					System.out.println("Error Code:       " + oe.getErrorCode());
					System.out.println("Request ID:      " + oe.getRequestId());
					System.out.println("Host ID:           " + oe.getHostId());
				} catch (ClientException ce) {
					System.out .println("Caught an ClientException, which means the client encountered "
									+ "a serious internal problem while trying to communicate with OSS, "
									+ "such as not being able to access the network.");
					System.out.println("Error Message: " + ce.getMessage());
				} catch (Throwable e) {
					e.printStackTrace();
				} finally {
					ossClient.shutdown();// 关闭client
				}
				return key;
	}

	@Override
	public boolean aliYunDownLoad(String filename) {
		boolean flag=false;
		if (filename == null || filename.equals("")) {
			return flag;
		}
		
		// endpoint以杭州为例，其它region请按实际情况填写
		//String endpoint = "<endpoint, http://oss-cn-hangzhou.aliyuncs.com>";
		// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
		// https://ram.console.aliyun.com 创建
		//String accessKeyId = "<accessKeyId>";
		//String accessKeySecret = "<accessKeySecret>";
		//String bucketName = "<bucketName>";
		// String key = "<downloadKey>";
		// String downloadFile = "<downloadFile>";
		String key = filename;
		// InputStream is=null;
		// FileOutputStream fos=null;
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(UpLoadText.endpoint, UpLoadText.ACCESS_KEY, UpLoadText.SECRET_KEY);

		try {
			// 设置断点续传请求
			DownloadFileRequest downloadFileRequest = new DownloadFileRequest( UpLoadText.BUCKET, key);
			// 设置本地文件
			String fileDownPath = UpLoadText.loaclfilePath;// 判断文件夹是否存在，否增加
			if (!util.fileIsExists(fileDownPath)) {
				File file = new File(fileDownPath);
				file.mkdir();
			}
			String downloadFile = fileDownPath + "/" + key;
			// 本地文件，下载到该文件，可选参数，默认是key，通过构造方法或setDownloadFile设置
			downloadFileRequest.setDownloadFile(downloadFile);
			// 设置并发下载数，默认1
			downloadFileRequest.setTaskNum(5);
			// 设置分片大小，默认100KB
			downloadFileRequest.setPartSize(1024 * 1024 * 1);
			// 开启断点续传，默认关闭
			downloadFileRequest.setEnableCheckpoint(true);

			DownloadFileResult downloadResult = ossClient .downloadFile(downloadFileRequest);
			flag=true;
			// 下载object到文件
			//ossClient.getObject(new GetObjectRequest( UpLoadText.ali_BUCKET, key),new File(downloadFile) );
			ObjectMetadata objectMetadata = downloadResult.getObjectMetadata();
			System.out.println(objectMetadata.getETag());
			System.out.println(objectMetadata.getLastModified());
			System.out.println(objectMetadata.getUserMetadata().get("meta"));
			
		} catch (OSSException oe) {
			System.out .println("Caught an OSSException, which means your request made it to OSS, "
							+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message: " + oe.getErrorCode());
			System.out.println("Error Code:       " + oe.getErrorCode());
			System.out.println("Request ID:      " + oe.getRequestId());
			System.out.println("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			System.out .println("Caught an ClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with OSS, "
							+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ce.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}
		return flag;
	}

}
