package ${rootPackage}.service.Impl;
import ${rootPackage}.bo.${domainName}BO;
import ${rootPackage}.dao.${domainName}Dao;
import ${rootPackage}.service.${domainName}Service;
import ${rootPackage}.vo.${domainName}VO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ${domainName}ServiceImpl implements ${domainName}Service{

    @Resource
    private ${domainName}Dao ${domainName ? lower_case}Dao;

    @Override
    public List<${domainName}${r'BO'}> queryList(${domainName}VO ${domainName ? lower_case}VO) {
    return null;
    }
    @Override
    public int save(List<${domainName}${r'BO'}> ${domainName ? lower_case}List) {
        return 0;
    }
    @Override
    public void update(${domainName}VO ${domainName ? lower_case}VO) {
    }
    @Override
    public void delete(String ${domainName ? lower_case}Id) {
    }
}