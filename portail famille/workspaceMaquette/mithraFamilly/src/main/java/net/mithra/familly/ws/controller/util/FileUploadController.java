package net.mithra.familly.ws.controller.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/rest")
//@CrossOrigin
@RestController
public class FileUploadController {

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }
    
    @RequestMapping(value="/upload2", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo2() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    //@RequestParam("name") String name
    public @ResponseBody String handleFileUpload(
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("d:\test.zip")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded c'est bien!";
            } catch (Exception e) {
                return "You failed to upload uploaded c'est bien => " + e.getMessage();
            }
        } else {
            return "You failed to upload uploaded c'est bien because the file was empty.";
        }
    }

}
