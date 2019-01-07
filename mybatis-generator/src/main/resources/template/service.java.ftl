package ${rootPackage}.service;
import ${rootPackage}.bo.${domainName}BO;
import ${rootPackage}.vo.${domainName}VO;
import java.util.List;

public interface ${domainName}Service{

    List<${domainName}${r'BO'}> queryList(${domainName}VO ${variableName}VO);

    int save(List<${domainName}${r'BO'}> ${variableName}List);

    void update(${domainName}VO ${variableName}VO);

    void delete(String ${variableName}Id);
}