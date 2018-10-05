package service;

import java.io.File;
import java.io.InputStream;

public interface FtpService {
	/**
	 * FTP上传
	 * @param filePath(文件的完整路径)
	 * @return
	 */
	//@Transactional
	public String ftpUpLoad (File file, String key);
	/**
	 * FTP下载
	 * @param filename(完整的文件名称+后缀名)
	 * @return
	 */
	//@Transactional
	public InputStream ftpDownLoad (String filename);
}
