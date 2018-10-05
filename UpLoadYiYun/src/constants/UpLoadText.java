package constants;

import java.util.ResourceBundle;

public interface  UpLoadText {
	  //uploadContext为属性文件名，放在包com.mmq下，如果是放在src下，直接用test即可  
	  public static ResourceBundle resource = ResourceBundle.getBundle("uploadContext");
	  public static final String DEFAULTTYPE =resource.containsKey("DEFAULTTYPE")==false?"C":resource.getString("DEFAULTTYPE").toString().trim(); //flag(具体的下载方法  A 阿里云 ,B 七牛 ,C 易云, D FTP )
	  public static final String DATEFLAG =resource.containsKey("DATEFLAG")==false?"D":resource.getString("DATEFLAG").toString().trim(); //"D";// 时间字符串(默认八位 ) Y 四位 例如：2018 ,M 六位 例如：201802 ,D 八位 例如：20180208  //存放上传服务器后的新名字由   当前时间+uuid+后缀名
	 
	  public static final String endpoint = resource.containsKey("endpoint")==false?"":resource.getString("endpoint").toString().trim();// endpoint以杭州为例，其它region请按实际情况填写
	  public static final String ACCESS_KEY =resource.containsKey("ACCESS_KEY")==false?"":resource.getString("ACCESS_KEY").toString().trim();	 
	  public static final String SECRET_KEY = resource.containsKey("SECRET_KEY")==false?"":resource.getString("SECRET_KEY").toString().trim();
	  public static final String BUCKET = resource.containsKey("BUCKET")==false?"":resource.getString("BUCKET").toString().trim();
	  public static final String loaclfilePath = resource.containsKey("loaclfilePath")==false?"":resource.getString("loaclfilePath").toString().trim(); //下载文件到本地文件夹的路径	  
	  public static final String CHECK=resource.containsKey("CHECK")==false?"":resource.getString("CHECK").toString().trim();//设置使用V2认证	  
	  //FTP		
	  public static final String ftp_URL= resource.containsKey("URL")==false?"":resource.getString("URL").toString().trim();// FTP地址		
	  public static final int ftp_PORT = Integer.parseInt(resource.containsKey("PORT")==false?"0":resource.getString("PORT").toString().trim());// FTP端口号		
	  public static final String ftp_USERNAME =resource.containsKey("USERNAME")==false?"":resource.getString("USERNAME").toString().trim(); // FTP用户名	
	  public static final String ftp_PASSWORD = resource.containsKey("lyshpwssod")==false?"":resource.getString("lyshpwssod").toString().trim();// FTP密码		
	  public static final String ftp_PATH = resource.containsKey("SERVICEPATH")==false?"":resource.getString("lyshpwssod").toString().trim();// FTP默认保存路径
	  
	  
	 

	
}
