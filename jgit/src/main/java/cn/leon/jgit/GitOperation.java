package cn.leon.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class GitOperation {
    @Value("git.url")
    private String REMOTE_URL;
    @Value("git.path")
    private String path;
    @Value("git.username")
    private String username;
    @Value("git.password")
    private String password;

    public void pull() throws IOException, GitAPIException {
        // prepare a new folder for the cloned repository
        File localPath = Paths.get(path).toFile();
        // now open the created repository
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(localPath).readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
        Git git = new Git(repository);
        git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();
        System.out.println("Pulled from remote repository to local repository at " + repository.getDirectory());
        repository.close();
    }
}
