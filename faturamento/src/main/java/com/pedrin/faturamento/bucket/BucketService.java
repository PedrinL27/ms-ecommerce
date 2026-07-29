package com.pedrin.faturamento.bucket;

import com.pedrin.faturamento.config.props.MinioProps;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.Http;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BucketService {

    private final MinioClient client;
    private final MinioProps props;

    public void upload(BucketFile file) {
        try {
            var object = PutObjectArgs
                    .builder()
                    .bucket(props.getBucketName())
                    .object(file.name())
                    .stream(file.is(), file.size(), -1L)
                    .contentType(file.type().toString())
                    .build();

            client.putObject(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl(String filename) {
        try {
            var object = GetPresignedObjectUrlArgs
                    .builder()
                    .method(Http.Method.GET)
                    .bucket(props.getBucketName())
                    .object(filename)
                    .expiry(1, TimeUnit.DAYS)
                    .build();

            return client.getPresignedObjectUrl(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
