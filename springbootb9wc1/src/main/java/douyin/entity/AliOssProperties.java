package douyin.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Project: wms-project - OSSConfig</p>
 * <p>Powered by fpl1116 On 2024-02-28 23:26:54</p>
 * <p>描述：<p>
 *
 * @author penglei
 * @version 1.0
 * @since 1.8
 */

@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class AliOssProperties {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


}