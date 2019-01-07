package ${rootPackage}.controller;

import ${rootPackage}.service.${domainName}Service;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("${domainName}Controller")
@RestController
@RequestMapping("${variableName}")
public class ${domainName}Controller {

    @Autowired
    private ${domainName}Service ${variableName}Service;

}