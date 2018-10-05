package service;

import java.io.File;
import java.io.InputStream;

public interface EaYunService {
	/**
	 * 易云（AWS亚马逊 ）上传
	 * @param filePath（文件完整路径）
	 * @return
	 */
	//@Transactional
	public String eaYunUpLoad(File file,String key);
	/**
	 * 易云（AWS亚马逊 ）下载
	 * @param filename（文件名称）	 
	 * @return
	 */
	//@Transactional
	public InputStream eaYunDownLoad(String filename);
	
	
}
