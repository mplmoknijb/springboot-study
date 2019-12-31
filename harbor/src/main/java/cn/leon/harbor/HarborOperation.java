package cn.leon.harbor;

import cn.leon.harbor.model.HarborResponse;
import cn.leon.harbor.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName HarborOperation
 * @Description
 * @Author Jevon
 * @Date2019/12/30 10:15
 **/
@Service
public class HarborOperation implements CommandLineRunner {
    @Autowired
    private HarborConfig harborConfig;

    public void harborRepo() throws IOException, HarborClientException {
        HarborClient harborClient = harborConfig.initHarbor();
        List<Project> projects = harborClient.getProjects("chart-repo", "1");
        projects.forEach(project -> {
            System.out.println(project.getName());
        });
        HarborResponse harborResponse = harborClient.checkProject("chart-repo");
        harborResponse.getMessage();
    }

    @Override
    public void run(String... args) throws Exception {
        harborRepo();
    }
}
