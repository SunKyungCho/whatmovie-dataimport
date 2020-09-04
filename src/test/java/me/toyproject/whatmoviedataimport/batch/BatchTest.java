package me.toyproject.whatmoviedataimport.batch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@SpringBootTest(classes = {MovieDetailJobConfiguration.class})
public class BatchTest {

//    @Autowired
//    JobLauncherTestUtils jobLauncherTestUtils;
//
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Test
    public void batch() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        JobExecution run = jobLauncher.run(job, jobParameters);
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
//        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    public void ImageUrlTest() throws IOException {
        Document doc = Jsoup.connect("http://www.kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do?code=" + "20071002").get();
        String imageUri = doc.select(".info1 a").first().attr("href");
        String url = "http://www.kobis.or.kr" + imageUri;
        assertThat(url).isEqualTo("http://www.kobis.or.kr/common/mast/movie/2019/11/5658d961585b46dd8b05225665af2c5a.jpg");
    }

    @Test
    public void descriptionTest() throws IOException {
        Document doc = Jsoup.connect("http://www.kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do?code=" + "20197803").get();
        Element first = doc.select(".desc_info").first();
        System.out.println();
    }
}
