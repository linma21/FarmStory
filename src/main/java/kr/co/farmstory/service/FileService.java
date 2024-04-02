package kr.co.farmstory.service;

import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.dto.FileDTO;
import kr.co.farmstory.entity.File;
import kr.co.farmstory.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    // 파일 업로드
    public List<FileDTO> fileUpload(ArticleDTO articleDTO) {

        // Entity 클래스명과 곂치기 때문에 java.io.File로 표시
        String path = new java.io.File(fileUploadPath).getAbsolutePath();

        List<FileDTO> files = new ArrayList<>();
        log.info("fileUpload...1");
        for (MultipartFile mf : articleDTO.getFiles()) {
            log.info("fileUpload...2");
            // 파일 첨부 여부 확인
            if (!mf.isEmpty()) {
                log.info("fileUpload...3");
                String oName = mf.getOriginalFilename();
                log.info("fileUpload...4" + oName);
                
                // 파일 저장명 생성
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;

                log.info("sName  : " + sName);
                try {
                    // 저장
                    mf.transferTo(new java.io.File(path, sName));

                    FileDTO fileDTO = FileDTO.builder()
                            .oName(oName)
                            .sName(sName)
                            .build();
                    files.add(fileDTO);

                } catch (IOException e) {
                    log.error("fileUpload : " + e.getMessage());
                }
            }
        }
        return files;
    }
    // 파일 insert
    public void insertFile(List<FileDTO> files, int ano){
        // 파일 각각 insert
        for(FileDTO fileDTO : files){
            fileDTO.setAno(ano);
            File file = modelMapper.map(fileDTO, File.class);

            fileRepository.save(file);
        }
    }
}
