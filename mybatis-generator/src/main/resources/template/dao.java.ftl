package ${rootPackage}.dao;

import ${rootPackage}.bo.${domainName}BO;
import ${rootPackage}.entity.${domainName}Entity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ${domainName}Dao{

    List<${domainName}${r'BO'}> queryList(${domainName}Entity ${variableName}Entity);

    int save(List<${domainName}${r'BO'}> ${variableName}List);

    void update(${domainName}Entity ${variableName}Entity);

    void delete(String ${variableName}Id);
}