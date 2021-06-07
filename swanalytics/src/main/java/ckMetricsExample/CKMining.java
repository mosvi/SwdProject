package ckMetricsExample;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;

public class CKMining implements Study {

    public static void main(String args[]) {
        new RepoDriller().start(new CKMining());
    }

    public void execute() {
        new RepositoryMining()
                .in(GitRepository.singleProject("/Users/apple/Desktop/SwD/Project/apps-dataset/ornidroid"))
                .through(Commits.all())
                .process(new MetricVisitor(), new CSVFile("/Users/apple/Desktop/SwD/Project/code-metrics/ornidroid-metrics.csv"))
                .mine();

    }
}