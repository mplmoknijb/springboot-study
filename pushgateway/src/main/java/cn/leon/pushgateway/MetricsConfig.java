package cn.leon.pushgateway;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class MetricsConfig implements CommandLineRunner {

  //  public static final Counter register =
  //      Counter.build()
  //          .help("异常统计")
  //          .register();

  public static final Gauge guage =
      Gauge.build("lock_count", "count").labelNames("hospital", "xids", "date")
              .create();

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i  < 100; i++) {
      PushGateway prometheusPush = new PushGateway("172.16.143.102:8080");
      String date = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date());
      guage.labels("铜仁松桃中医院", "self_demo", date).set(0);
      CollectorRegistry collectorRegistry = new CollectorRegistry();
      guage.register(collectorRegistry);
      try {
        prometheusPush.push(guage, "lock");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
