package service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;



public interface UpDownService {
   /**
    * 单文件上传
    * @param filePath(完整的文件路径+文件名+后缀名) 
    * @return
    */
	public String upLoad(File file,String key ) ; 	
	/**
	 * 多文件上传
	 * @param map
	 * @return
	 */
	public  List<String> upLoadFiles( Map<String ,File > map ) ; 
	/**
	 * 下载
	 * @param fileName(服务器上的文件名+后缀名)
	 * @return
	 */
	public InputStream downLoad(String filename) ;  
	
}
