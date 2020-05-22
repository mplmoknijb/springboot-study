package cn.leon.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SwaggerController {
    @ApiOperation("test")
    @GetMapping("/test/swagger")
    public String test(){
        return "success";
    }

    @PostMapping(value = "/import",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String importFile(@RequestPart MultipartFile file){
        return "success";
    }
}
