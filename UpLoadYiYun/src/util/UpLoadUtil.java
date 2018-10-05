package util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import constants.UpLoadText;

public class UpLoadUtil {
	/**
	 * 当前时间字符串(默认八位 ) Y四位 例如：2018 ,M六位 例如：201802 ,D八位 例如：20180208 
	 * @param dateFlag
	 * @return String
	 */
	public String dateUitl() {
		String datePath = "";
		String dateFlag=UpLoadText.DATEFLAG.toString().trim();
		SimpleDateFormat sdf = null;
		if (dateFlag.equals("Y")) {
			sdf = new SimpleDateFormat("yyyy");
			datePath = sdf.format(new Date());
		} else if (dateFlag.equals("M")) {
			sdf = new SimpleDateFormat("yyyyMM");
			datePath = sdf.format(new Date());
		} else if (dateFlag.equals("D")) {
			sdf = new SimpleDateFormat("yyyyMMdd");
			datePath = sdf.format(new Date());
		} else {
			sdf = new SimpleDateFormat("yyyyMMdd");
			datePath = sdf.format(new Date());
		}
		return datePath;
	}
	/**
	 * 判断文件是否存在 
	 * @param filePath
	 * @return boolean
	 */
	public boolean fileIsExists(String filePath) {
		boolean isexist = true;
		if (filePath == null || filePath.equals("")) {
			isexist = false;
		}
		if (!StringUtils.isNotBlank(filePath)) {
			isexist = false;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			isexist = false;
		}
		// DiskFileItemFactory dfif = new DiskFileItemFactory(1024*20, file);
		// ServletFileUpload fileUpload=new ServletFileUpload(dfif);
		// fileUpload.setSizeMax(1024*10);//控制上传大小
		return isexist;
	}

}
