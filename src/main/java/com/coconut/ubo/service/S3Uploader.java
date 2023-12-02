package com.coconut.ubo.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;
import java.nio.file.Paths;


@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    // 1. MultipartFile -> S3에 전달 가능한 형태(임시 파일)로 변환 (로컬 시스템에서)
    // 2. 임시 파일을 S3에 public 읽기 권한으로 업로드, S3에 저장된 파일의 URL 반환
    // 3. 로컬 시스템의 임시 파일 삭제

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket; // application.yml 파일에 정의된 S3 버킷 주입

    /**
     * S3에 파일 업로드하고, 업로드된 파일 URL 반환
     * @param multipartFile 업로드할 MultipartFile 객체
     * @param filePath S3에서의 저장 경로
     * @return 업로드된 파일의 URL
     * @throws IOException 파일 처리 중 발생하는 예외
     */
    public String upload(MultipartFile multipartFile, String filePath) throws IOException {

        // MultipartFile -> 로컬 File 변환
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("[errer] : MultipartFile -> File 변환 실패"));

        // S3에 저장될 파일명 생성 (고유ID 포함)
        String fileName = filePath + "/" + UUID.randomUUID();

        // S3로 업로드 후 로컬에 저장된 파일 삭제
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        return uploadImageUrl;
    }


    /**
     * MultipartFile -> 로컬 파일로 변환
     * @param multipartFile 변환할 MultipartFile 객체
     * @return 변환된 File 객체를 포함하는 Optional
     * @throws IOException 파일 처리 중 발생하는 예외
     */
    public Optional<File> convert(MultipartFile multipartFile) throws IOException {

        // UUID를 이용한 고유 파일명 생성
        String uniqueFilename = UUID.randomUUID().toString(); // 랜덤 UUID 생성
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));


        // 로컬에 저장할 파일 경로 : user.dir (현재 디렉토리 기준)
//        String directPath = System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename();
        String directPath = Paths.get(System.getProperty("user.dir"), uniqueFilename + fileExtension).toString();
        File convertFile = new File(directPath);

        log.info("변환할 파일 경로: {}", directPath);

        // 로컬 파일 시스템에 파일 생성
        if (convertFile.createNewFile()) {
            // FileOutputStream을 사용해 데이터를 파일에 바이트 스트림으로 저장
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }else {
            log.warn("파일 생성 실패. 파일이 이미 존재하는지 또는 디렉토리에 쓰기 권한이 있는지 확인 필요.");
            return Optional.empty();
        }
    }

    /**
     * 파일을 S3에 업로드
     * @param uploadFile 업로드할 파일
     * @param fileName S3에 저장될 파일 이름
     * @return 업로드된 파일의 URL
     */
    public String putS3(File uploadFile, String fileName) {

        // S3에 파일 업로드, PublicRead 권한 부여
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        //업로드된 파일 URL 반환
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /**
     * 로컬에 저장된 파일 제거
     * @param uploadFile 제거할 파일
     */
    public void removeNewFile(File uploadFile) {

        if (uploadFile.delete()) {
            log.info("[파일 업로드] : 파일 삭제 성공");
        } else {
            log.info("[파일 업로드] : 파일 삭제 실패");
        }
    }

    /**
     * S3에 저장된 파일 삭제
     * @param fileUrl
     */
    public void deleteFileFromS3Bucket(String fileUrl) {

        try {
            URI uri = new URI(fileUrl);
            String key = uri.getPath().substring(1); // S3 파일 경로(첫 '/' 제거)

            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
            log.info("S3 Bucket에서 파일이 성공적으로 삭제되었습니다. : {}", fileUrl);
        } catch (URISyntaxException e) {
            log.error("file URL 파싱에 에러가 발생하였습니다. : {}", fileUrl, e);
            throw new IllegalArgumentException("Invalid file URL");
        }
    }
}
