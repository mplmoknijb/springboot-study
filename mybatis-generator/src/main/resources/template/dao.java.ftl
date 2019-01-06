package ${rootPackage}.repository;

import ${rootPackage}.bo.${domainName}Bo;
import ${rootPackage}.command.${domainName}Entity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ${domainName}Dao{

    List<${domainName}Bo> queryList(${domainName}Entity entity);

    int insertList (List<${domainName}> ${variableName}s);
}