package net.mithra.familly.ws.controller.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.service.FileService;
import net.mithra.familly.ws.controller.model.UserInfoModel;

@Service("ServiceMapper")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ServiceMapper {
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	protected FileService fileService;
	

	
	public UserInfoModel getUserInfo(FaUser user){
		if(user!=null){
			UserInfoModel userInfo=new UserInfoModel();
			userInfo.setName(user.getName());
			userInfo.setLogin(user.getLogin());
			userInfo.setAvatar(user.getAvatar());
			userInfo.setEmail(user.getEmail());
			return userInfo;
		}
		return null;
	}
	

	
	public HttpHeaders getResponseHeaderFileDownload(File file) throws MalformedURLException, IOException{
	    String type=file.toURL().openConnection().guessContentTypeFromName(file.getName());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + file.getName());
        responseHeaders.add("Content-Type",type);
	    return responseHeaders;
	    
	}
	
	public InputStreamResource getInputStreamResourceFileDownload(File file) throws FileNotFoundException{
		return new InputStreamResource(new FileInputStream(file));
	}
	
	public File zipDirectory(File inDir, String zipFilePath) throws IOException{
		 byte[] buffer;
		 FileInputStream in = null;
		 int bytes_read;
		File tempFile=new File(zipFilePath);
    	if(!tempFile.exists()){
    		tempFile.createNewFile();
    	}
    	
    	ZipOutputStream z = new ZipOutputStream(new FileOutputStream(tempFile));
    	
    	for(File currFile:inDir.listFiles()){
    		ZipEntry zipEntry=new ZipEntry(currFile.getName());
    		z.putNextEntry(zipEntry);
    		buffer = new byte[Math.toIntExact(currFile.length())];
    		in = new FileInputStream(currFile);
    		 while ((bytes_read = in.read(buffer)) != -1) {
    	            z.write(buffer, 0, bytes_read);
    	        }
    		z.closeEntry();
    	}
    	z.close();
    	return tempFile;
	}
	
	
	
	
	public File[] multipartFilesToFiles(MultipartFile[] multipartFiles) throws IllegalStateException, IOException{
		List<File> files=new ArrayList<File>();
		String fileName = null;
		File file=null;
		if (multipartFiles != null && multipartFiles.length >0) {
			for(int i =0 ;i< multipartFiles.length; i++){
		    fileName = multipartFiles[i].getOriginalFilename();
		    file=new File(fileService.getFolderTemp()+fileName);
		    //transfert downloaded file to temp directory
		    multipartFiles[i].transferTo(file);	
		    files.add(file);
			}
		}
		return files.toArray(new File[0]);
	}
	
	
	
	
	
	
}
