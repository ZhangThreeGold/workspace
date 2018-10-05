package service;

import java.io.File;
import java.io.InputStream;

public interface QiNiuService {
	/**
	 * 七牛上传
	 * @param filePath(文件的完整路径)
	 * @return fileName
	 */
	//@Transactional
	public String qiniuUpLoad (File file, String key);	
	/**
	 * 七牛上传  
	 * @param data 例如： byte[] data="hello qiniu cloud".getBytes("utf-8");
	 * @param bucket(空间)
	 * @param fileNameAll(文件的完整路径)
	 * @return fileName
	 */
	//@Transactional
	//public String qiniuUpLoad (byte[] data,String fileNameAll) ;
	/**
	 * 七牛下载
	 * @param filename(文件名称+后缀名)
	 * @return filePathUrl
	 */
	//@Transactional
	public InputStream qiniuDownLoad (String filename);		

}
