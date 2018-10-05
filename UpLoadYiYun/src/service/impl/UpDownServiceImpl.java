/**
 * 
 */
package service.impl;	
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import service.AliYunService;
import service.EaYunService;
import service.FtpService;
import service.QiNiuService;
import service.UpDownService;
import constants.UpLoadText;


/**
 * @author 周志豪
 *
 */
public class UpDownServiceImpl implements UpDownService {   
	/**
	 * 单文件上传
	 */
	@Override
	public String upLoad(File file,String key) {			
		String flag=UpLoadText.DEFAULTTYPE.toString().trim();
		key=this.uuidName(key);
		if(flag.equals("A")){//阿里云
			System.out.println("阿里云上传......");
			AliYunService aliyun=new AliYunServiceImpl();
			return aliyun.aliYunUpLoad(file,key);
		}else if (flag.equals("B")) {//七牛
			System.out.println("七牛上传......");
			QiNiuService qiniu =new QiNiuServiceImpl();	
			return qiniu.qiniuUpLoad(file,key);
		}else if (flag.equals("C")) {//易云
			System.out.println("易云上传......");
			EaYunService eayun = new EaYunServiceImpl();
			return eayun.eaYunUpLoad(file,key);
		}else if (flag.equals("D")) {//FTP
			System.out.println("FTP上传......");
			FtpService  ftp= new FtpServiceImpl();
			return ftp.ftpUpLoad(file,key);
		}else {
			return null;	
		}		
	}
	/**
	 * 多文件上传
	 */
	@Override
	public List<String> upLoadFiles(Map<String, File> map) {
    String flag=UpLoadText.DEFAULTTYPE.toString().trim();
    List<String> list = new ArrayList<String>();
		if(flag.equals("A")){//阿里云
			System.out.println("阿里云上传......");
			AliYunService aliyun=new AliYunServiceImpl();
			for (Map.Entry<String, File> entry : map.entrySet()) {
			      System.out.println(entry.getKey() + ":" + entry.getValue());
			     list.add(aliyun.aliYunUpLoad(entry.getValue(),this.uuidName(entry.getKey())));
			 
			    }
			return list;
		}else if (flag.equals("B")) {//七牛
			System.out.println("七牛上传......");
			QiNiuService qiniu =new QiNiuServiceImpl();	
			for (Map.Entry<String, File> entry : map.entrySet()) {
			      System.out.println(entry.getKey() + ":" + entry.getValue());
			     list.add( qiniu.qiniuUpLoad(entry.getValue(),this.uuidName(entry.getKey())));
			    }
			return list;
		}else if (flag.equals("C")) {//易云
			System.out.println("易云上传......");
			EaYunService eayun = new EaYunServiceImpl();
			for (Map.Entry<String, File> entry : map.entrySet()) {
			      System.out.println(entry.getKey() + ":" + entry.getValue());
			     list.add( eayun.eaYunUpLoad(entry.getValue(),this.uuidName(entry.getKey())));
			    }
			return list;
		}else if (flag.equals("D")) {//FTP
			System.out.println("FTP上传......");
			FtpService  ftp= new FtpServiceImpl();
			for (Map.Entry<String, File> entry : map.entrySet()) {
			      System.out.println(entry.getKey() + ":" + entry.getValue());
			     list.add( ftp.ftpUpLoad(entry.getValue(),this.uuidName(entry.getKey())));
			    }
			return list;
		}else {
			return list;	
		}		
	}
	/**
	 * 下载
	 */
	@Override
	public InputStream downLoad(String filename) {	
		String flag=UpLoadText.DEFAULTTYPE.toString().trim();
		if (!StringUtils.isNotBlank(filename)) {
			return null;		
		}	
		if(flag.equals("A")){//阿里云
			System.out.println("阿里云下载......");
			this.alidown( filename);			
			return null;	
		}else if (flag.equals("B")) {//七牛	
			System.out.println("七牛云下载......");
			QiNiuService qiniu =new QiNiuServiceImpl();	
			return qiniu.qiniuDownLoad(filename);
		}else if (flag.equals("C")) {//易云	
			System.out.println("易云下载......");
			EaYunService eayun = new EaYunServiceImpl();
			return eayun.eaYunDownLoad(filename);
		}else if (flag.equals("D")) {//FTP
			System.out.println("FTP下载......");
			FtpService  ftp= new FtpServiceImpl();
			return ftp.ftpDownLoad(filename);
		}else {
			return null;	
		}		
	}


    public void alidown(String fileName ) {
    	  AliYunService aliyun=new AliYunServiceImpl();
    	 if(aliyun.aliYunDownLoad(fileName)){    		 
    		 System.out.println("下载成功");
    	 }else {
    		 System.out.println("下载失败");
		}
   }
    private String uuidName(String str) {	
    	String newName="";    	
    	String suffixName = str.substring(str.lastIndexOf("."));    	
    	String oldname = str.substring(0,str.lastIndexOf("."));    	
    	String UUIDName =  UUID.randomUUID().toString().replace("-", "");	    	
    	newName=oldname+UUIDName+suffixName;
        return newName;
	}
    
}
