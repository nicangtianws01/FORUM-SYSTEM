package com.ws.forum.service;

import org.springframework.web.multipart.MultipartFile;

import com.ws.forum.vo.ImageVo;

public interface FileService {
	ImageVo uploadImage(MultipartFile image);
}
