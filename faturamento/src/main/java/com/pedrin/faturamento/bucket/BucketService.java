package com.pedrin.faturamento.bucket;

import com.pedrin.faturamento.config.props.MinioProps;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BucketService {

    private final MinioClient client;
    private final MinioProps props;

    public void upload(BucketFile file) {

    }

    public String getUrl(String filename) {
        
    }
}
