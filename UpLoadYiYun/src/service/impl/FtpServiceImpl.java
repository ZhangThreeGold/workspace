/**
 * 
 */
package service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.struts2.ServletActionContext;

import service.FtpService;
import util.UpLoadUtil;
import constants.UpLoadText;


/**
 * @author 周志豪
 *
 */
public class FtpServiceImpl implements FtpService {
	UpLoadUtil util=new UpLoadUtil();
	@Override
	public String ftpUpLoad(File file, String key) {
		// 上传文件的路径
				
				String success = "";// 返回参数
				boolean result = false; // 上传是否成功		
				InputStream input = null;
				FTPClient ftp = new FTPClient();
				try {
					
					// Map ftpMap = ApplicationUtil.getAppConfig().getAppExtProp();
					// url = ftpMap.get("ftpHostIP").toString();
					// port = Integer.parseInt(ftpMap.get("ftpPort").toString());
					// username = ftpMap.get("ftpUserName").toString();
					// password = ftpMap.get("ftpPassWord").toString();
					// System.out.println("上传FTP模版路径："+path+"url:"+url+",");
					// System.out.println("服务器文件名："+filename);

					// 连接FTP服务器// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
					ftp.connect(UpLoadText.ftp_URL,UpLoadText.ftp_PORT);
					// 登录到FTP服务器使用提供的用户名和密码。
					ftp.login(UpLoadText.ftp_USERNAME, UpLoadText.ftp_PASSWORD);
					// 判断返回码是否合法
					if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
						ftp.disconnect();// 关闭到FTP服务器，连接参数恢复到默认值的连接。
						return null;
					}
					// 切换FTP目录//更改的FTP会话的当前工作目录。//ftp.changeWorkingDirectory(path);
					if (ftp.changeWorkingDirectory(UpLoadText.ftp_PATH) == false) {
						ftp.disconnect();
						return null;
					}
					// 设置文件类型转移。
					ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
					// 存储一个使用给定的名称，并采取从给定的InputStream输入服务器的文件。
					input = new FileInputStream(file);
					result = ftp.storeFile(key, input); // 上传文件
					success = key;
					input.close();// 关闭流
					ftp.logout();// 注销的通过发送QUIT命令FTP服务器。
					
				} catch (IOException e) {
					
				} finally {
					if (ftp.isConnected()) {
						try {
							ftp.disconnect();
						} catch (IOException ioe) {
							
						}
					}
				}
				return result == true ? success : "";
	}

	@Override
	public InputStream ftpDownLoad(String filename) {
		if (filename == null || filename.equals("")) {
			return null;
		}
		InputStream in=null;
		HttpServletResponse response = ServletActionContext.getResponse();
		FTPClient ftp = new FTPClient();
		try {
			// 连接FTP服务器// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(UpLoadText.ftp_URL,UpLoadText.ftp_PORT);
			// 下面三行代码必须要，而且不能改变编码格式
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 登录到FTP服务器使用提供的用户名和密码。
			ftp.login(UpLoadText.ftp_USERNAME, UpLoadText.ftp_PASSWORD);
			// 判断返回码是否合法
			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();// 关闭到FTP服务器，连接参数恢复到默认值的连接。
				return null;
			}
			// 切换FTP目录//更改的FTP会话的当前工作目录。//ftp.changeWorkingDirectory(path);
			if (ftp.changeWorkingDirectory(UpLoadText.ftp_PATH) == false) {
				ftp.disconnect();
				return null;
			}
			// 设置文件类型转移。
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
			for (int i = 0; i < fs.length; i++) {
				FTPFile ff = fs[i];
				String flieN = ff.getName();
				// String flieN = new
				// String(ff.getName().getBytes("GBK"),"ISO-8859-1");
				if (flieN.equals(filename)) {
					// 这个就就是弹出下载对话框的关键代码
				    //File localFile = new File(ff.getName());

					// 设置不同类型的文件值
					String appflag = "";
					String attachment = "attachment";
					// String name = fileName;
					// String extName = fileName.substring(name.indexOf("."));
					String name = filename;
					String extName = filename.substring(name.indexOf("."));
					if (extName.equals(".doc")) {
						appflag = "application/msword";
						attachment = "inline";
					} else if (extName.equals(".docx")) {
						appflag = "application/msword";
						attachment = "inline";
					} else if (extName.equals(".xls")) {
						appflag = "application/vnd.ms-excel";
						attachment = "inline";
					} else if (extName.equals(".xlsx")) {
						appflag = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
						attachment = "inline";
					} else if (extName.equals(".pdf")) {
						appflag = "application/pdf";
						attachment = "attachment";
					} else if (extName.equals(".jpg")
							|| extName.equals(".jpeg")
							|| extName.equals(".jpe")) {
						appflag = "image/jpeg";
						attachment = "inline";
					} else if (extName.equals(".png")) {
						appflag = "image/png";
						attachment = "inline";
					} else if (extName.equals(".bmp")) {
						appflag = "image/bmp";
						attachment = "inline";
					} else if (extName.equals(".gif")) {
						appflag = "image/gif";
						attachment = "inline";
					} else if (extName.equals(".ppt")) {
						appflag = "application/vnd.ms-powerpoint";
						attachment = "inline";
					} else {
						appflag = "application/octet-stream";
						attachment = "attachment";
					}
					response.setContentType(appflag);
					response.setHeader( "Content-disposition", attachment + ";filename=" + URLEncoder.encode(filename, "utf-8"));
					// 将文件保存到输出流outputStream中
					
					// InputStream in = ftp.retrieveFileStream(fileName);
					//out = response.getOutputStream();
					 in = ftp.retrieveFileStream(new String(ff .getName().getBytes("GBK"), "ISO-8859-1"));
					// 判断文件夹是否存在，否增加
					/*String fileNamePath = "E:/ftpdownload";
					if (!util.fileIsExists(fileNamePath)) {
						File file = new File(fileNamePath);
						file.mkdir();
					}*/	
					// 将输入流is写入文件输出流out中
					// out = response.getOutputStream();
					/*
					 * int len = 0; long size = 0; byte[] bt = new byte[10240];
					 * while ((len = in.read(bt)) > 0) { 
					 * out.write(bt, 0, len);
					 * size = size + len; }
					 */
					// 下载后保存到本地的路径
					/*String fileLocalPath = "E:/ftpdownload";
					out = new FileOutputStream(new File(fileLocalPath + "/" + filename));
					// ftp.retrieveFile(ff.getName(), out);
					byte[] b = new byte[10 * 1024];
					while (in.read(b, 0, 10240) != -1) {
						out.write(b, 0, 10240);
					}
					out.flush();
					out.close();
					in.close();// 关闭流
					break;*/
				}
			}

			ftp.logout();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {

				}
			}
		}
	 return in;
	}

	
}
