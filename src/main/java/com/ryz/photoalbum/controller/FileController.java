package com.ryz.photoalbum.controller;

import com.ryz.photoalbum.pojo.PImage;
import com.ryz.photoalbum.pojo.PUser;
import com.ryz.photoalbum.service.ImageService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/file")
@Configuration
public class FileController {

    @Resource
    private ResourceLoader resourceLoader;
    @Autowired
    private ImageService imageService;
    @Value("${basePath}")
    private  String basePath;

    @RequestMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file")MultipartFile file, String userId){

        if(file.isEmpty()){
            return "FAIL";
        }


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String location =df.format(new Date()) + System.getProperty("file.separator");
        // 判断文件夹是否存在，不存在则
        basePath = "all_image"+System.getProperty("file.separator");
        File targetFile = new File(basePath + location);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        String fileName = file.getOriginalFilename();
        fileName = fileName.length()>10?fileName.substring(fileName.length()-10):fileName;
        String url ="";
        try{
            Files.copy(file.getInputStream(), Paths.get(basePath + location, fileName), StandardCopyOption.REPLACE_EXISTING);
            url = location + fileName;
        }catch (Exception e){
            e.printStackTrace();
        }
        PImage image = new PImage();
        image.setId(String.valueOf(System.currentTimeMillis()));
        image.setUserId(userId);

        image.setImageName(fileName);
        image.setImageUrl(url);
        imageService.save(image);

        return url;

    }
    @RequestMapping(value="/{filePath}/{filename}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filePath,@PathVariable String filename)  {
        System.out.println("filePath----------------"+filePath);
        System.out.println("filename--------------"+filename);
        basePath = "all_image"+System.getProperty("file.separator");
        System.out.println("basePath-------------"+ basePath);
        ResponseEntity.ok().contentType(MediaType.IMAGE_PNG);
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(basePath +filePath+"\\"+ filename).toString()));
        }else{
            File file = new File("/usr/photoalbum/all_image/2018-06-04/22.png");
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("/usr/photoalbum/all_image/" +filePath+"/"+ filename).toString()));
        }
    }

    @RequestMapping(value="/getImageByUserId",method = RequestMethod.GET)
    @ResponseBody
    public List<PImage> getImageByUserId(@RequestParam String userId){
        return imageService.getImageByUserId(userId);
    }

    @RequestMapping(value="/getRandomImages",method = RequestMethod.GET)
    @ResponseBody
    public List<PImage> getRandomImages(@RequestParam String userId,@RequestParam String model){
        return imageService.getRandomImages(userId,model);
    }

    @RequestMapping(value="/editDesc",method = RequestMethod.GET)
    @ResponseBody
    public void editDesc(@RequestParam String id,@RequestParam String desc,@RequestParam String name){
        imageService.editDesc(id,desc,name);
    }

    @RequestMapping(value="/deleteImageById",method = RequestMethod.GET)
    @ResponseBody
    public void deleteImageById(@RequestParam String id){
        imageService.deleteImageById(id);
    }


}
