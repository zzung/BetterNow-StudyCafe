package com.project.betterNow.controller;

import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Api(description = "Summernote 이미지 업로드 REST API")
public class ImageUploadController {


    @PostMapping(value="/image", produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = new JsonObject();

        String fileRoot = "C:\\summernote_image\\";	//저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
            jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        return jsonObject;
    }


}
