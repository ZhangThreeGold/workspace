package test;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;

import service.UpDownService;
import service.impl.UpDownServiceImpl;
public class Test {
	public static void main(String[] args) {

		//String way=(String)args[0];
		//File file = (File)args[1];
		//String key =(String) args[2];
		String way="up";
		File file =new File("C:/Users/zx/Desktop/项目资料/视频图片/图片/12345.jpg");
		String key ="file/c8dd4b166b1b4b4ebe21b98c7b87ac0d.xlsx";
		
		if (way.equals("up")){
		  new Test().upLoad(file,key);
		  System.out.println("测试成功");
		}else if(way.equals("down")){
		  new Test().upDown(key);	
		}
	
	}
	
	public void upLoad(File file ,String key){		
		UpDownService aa=new UpDownServiceImpl(); 
		System.out.println(aa.upLoad(file,key));
	}	
	public void upDown(String fileName){
	
		FileOutputStream fos=null; 
		InputStream input =null; 
		try { 
			UpDownService aa=new UpDownServiceImpl();
			System.out.println(fileName);
			input=aa.downLoad(fileName); 
			System.out.println(aa);
			String  encodedFileName = URLEncoder.encode(fileName, "utf-8"); 
			fileName =FileUtils.getTempDirectory() +"/"+ encodedFileName;
			File file3 = new File(fileName);
			fos= new FileOutputStream(file3);
			OutputStream writer = new BufferedOutputStream(fos); 
			int read = -1; 
			while ((read = input.read())!= -1){ writer.write(read); } 
			writer.flush();
		    writer.close(); 
		    
		  } catch (IOException e) {
			  e.printStackTrace();
		  }finally{
			  try { //关闭输入流等（略） 
				  fos.close(); 
				  input.close(); 
				 
			  } catch  (IOException e) { 
				  e.printStackTrace();
		      }
	      }
		
	}
}





