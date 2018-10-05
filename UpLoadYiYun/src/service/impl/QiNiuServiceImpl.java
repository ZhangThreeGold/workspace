/**
 * 
 */
package service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import service.QiNiuService;
import util.UpLoadUtil;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import constants.UpLoadText;

/**
 * @author 周志豪
 *
 */
public class QiNiuServiceImpl implements QiNiuService {
	UpLoadUtil util=new UpLoadUtil();
	@Override
	public String qiniuUpLoad(File file,String key) {
		// 上传文件的路径
				
				try {
					// 密钥配置
					Auth auth = Auth.create(UpLoadText.ACCESS_KEY, UpLoadText.SECRET_KEY);
					// String localTempDir =
					// Paths.get(System.getProperty("java.io.tmpdir"),
					// BUCKET_IMAGE).toString();
					// String localTempDir = Paths.get(System.getenv("java.io.tmpdir"),	
					// BUCKET_IMAGE).toString();
					// 设置断点续传文件进度保存目录
					// FileRecorder fileRecorder = new FileRecorder(localTempDir);
					// FileRecorder fileRecorder = new FileRecorder(new
					// File(localTempDir));
					// 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
					Zone zone = Zone.autoZone();
					// 构造一个带指定Zone对象的配置类
					Configuration cfg = new Configuration(zone);
					// 创建上传对象
					// UploadManager uploadManager = new UploadManager(cfg,fileRecorder );//设置断点续传
					UploadManager uploadManager = new UploadManager(cfg);
					// 上传到七牛后保存的文件名称
					
					// 调用put方法上传 ,简单上传，使用默认策略，只需要设置上传的空间名就可以了
					Response res = uploadManager.put(file, key, auth.uploadToken(UpLoadText.BUCKET));

					// 解析上传成功的结果
					DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
					// 删除设置断点续传文件进度保存目录
					// fileRecorder.del(localTempDir);

					// System.err.println(res.bodyString());
					// MyRetImpl ret = res.jsonToObject(MyRetImpl.class);
					// System.out.println(ret.toString());
					// System.out.println(ret.hash.toString());
					// ret.key.toString();
					return putRet.key.toString();

				} catch (QiniuException e) {
					Response r = e.response;
					System.err.println(r.toString());
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
	}

	@Override
	public InputStream qiniuDownLoad(String filename) {
		        // 保存在七牛云存储中
				// String urlPre = ApplicationUtil.getAppConfig().getAppExtProp().get("qiniuPicPrePath").toString();// 得到路径;
		       InputStream is=null;
				try {
					Auth auth = Auth.create(UpLoadText.ACCESS_KEY, UpLoadText.SECRET_KEY);
					// 七牛域名下面的那个文件
					String encodedFileName = URLEncoder.encode(filename, "utf-8");
				    String publicUrl = String.format("%s/%s", UpLoadText.endpoint, encodedFileName);
					long expireInSeconds = 3600;// 1小时，可以自定义链接过期时间
					// 下载文件 参数1.文件名称 2.下载链接url
					String finalUrl  = auth.privateDownloadUrl(publicUrl, expireInSeconds);	// 连接地址	
					
					// 构造URLURL
					URL url = new URL(finalUrl);
					// 打开连接
					URLConnection con = url.openConnection();
					// 输入流
					is = con.getInputStream();	
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				} 
				return is;
	}


}
