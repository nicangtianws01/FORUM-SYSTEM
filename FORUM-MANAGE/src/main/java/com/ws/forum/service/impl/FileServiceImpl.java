package com.ws.forum.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ws.forum.service.FileService;
import com.ws.forum.vo.ImageVo;

@PropertySource("classpath:/properties/image.properties")
@Service
public class FileServiceImpl implements FileService {

	@Value("${image.localDirPath}")
	private String localDirPath;
	@Value("${image.postUrlDirPath}")
	private String urlDirPath;
	
	@Override
	public ImageVo uploadImage(MultipartFile image) {
		if(image.getSize() > 1000*1000) {
			throw new IllegalArgumentException("图片大小不能超过1M");
		}
		//1.判断是否为图片文件
		String filename = image.getOriginalFilename().toLowerCase();
		String reg = "^.+\\.(jpg|jpeg|png|gif)$";
		if(!filename.matches(reg)) {
			return new ImageVo();
		}
		BufferedImage bImg = null;
		try {
			bImg = ImageIO.read(image.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = bImg.getWidth();
		int height = bImg.getHeight();
		if(width == 0 || height == 0) {
			return new ImageVo();
		}
		//2.以时间为单位划分文件夹
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
		String path = sdf.format(new Date());
		String dirPath = localDirPath + path;
		File dir = new File(dirPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		//3.创建图片名称
		String uuid = UUID.randomUUID().toString();
		String fileType = filename.substring(filename.lastIndexOf("."));
		String realPath = dirPath+uuid+fileType;
		File file = new File(realPath);
		try {
			image.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		String url = urlDirPath + path + uuid + fileType;
		ImageVo imgVo = new ImageVo(url,width,height);
		return imgVo;
	}

}
