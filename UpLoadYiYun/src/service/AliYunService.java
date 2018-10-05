package service;

import java.io.File;


public interface AliYunService {
	/**
	 * 阿里 云上传
	 * @param filePath(文件的完整路径)	
	 * @return 
	 */
	//@Transactional
	public String aliYunUpLoad (File file,String key);
	/**
	 * 阿里云下载
	 * @param filename(完整的文件名称+后缀名)
	 * @return
	 */
	//@Transactional
	public boolean aliYunDownLoad (String filename);
}
