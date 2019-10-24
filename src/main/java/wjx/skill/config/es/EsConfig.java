package wjx.skill.config.es;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * es配置文件
 * Created by WangJX on 2018/9/29.
 */
@Configuration
public class EsConfig {

    @Bean
    public TransportClient client() {
        TransportClient client = null;
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", "elasticsearch")
                    .build();

            client = new PreBuiltTransportClient(settings);
            TransportAddress address = new TransportAddress(InetAddress.getByName("127.0.0.1"),9300);
            client.addTransportAddress(address);
        } catch (Exception e) {

        }
        return client;
    }
}
