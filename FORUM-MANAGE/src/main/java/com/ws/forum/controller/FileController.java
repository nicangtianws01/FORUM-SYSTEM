package com.ws.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ws.forum.service.FileService;
import com.ws.forum.vo.JsonResult;

@RequestMapping("/file")
@RestController
public class FileController {
	@Autowired
	private FileService fileService;
	@RequestMapping("/doUploadImage")
	public JsonResult doUploadImage(MultipartFile uploadImage) throws Throwable {
		return JsonResult.successData(fileService.uploadImage(uploadImage));
	}
}
