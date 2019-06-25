package ${rootPackage}.service;
import ${rootPackage}.bo.${domainName}Bo;
import ${rootPackage}.vo.${domainName}Vo;
import java.util.List;

public interface ${domainName}Service{

    List<${domainName}${r'Bo'}> queryList(${domainName}Vo ${variableName}Vo);

    int save(List<${domainName}${r'Bo'}> ${variableName}List);

    void update(${domainName}Vo ${variableName}Vo);

    void delete(String ${variableName}Id);
}