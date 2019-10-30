package com.itsol.mockup.services.impl;

import com.itsol.mockup.entity.ImageEntity;
import com.itsol.mockup.repository.ImagesRepository;
import com.itsol.mockup.services.ImagesService;
import com.itsol.mockup.utils.Constants;
import com.itsol.mockup.web.FileStorageException;
import com.itsol.mockup.web.FileStorageProperties;
import com.itsol.mockup.web.MyFileNotFoundException;
import com.itsol.mockup.web.dto.image.ImagesDTO;
import com.itsol.mockup.web.dto.response.ArrayResultDTO;
import com.itsol.mockup.web.dto.response.BaseResultDTO;
import com.itsol.mockup.web.dto.response.SingleResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImagesServiceImpl extends BaseService implements ImagesService {
    private final Path fileStorageLocation; //Đường dẫn lưu file

    private static String UPLOAD_DIR = "Upload";

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    public ImagesServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize(); //get ra đường dẫn lưu file tuyệt đối trong file cấu hình property

        try {
            Files.createDirectories(this.fileStorageLocation); //Tạo đường dẫn thư mục
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public ArrayResultDTO<ImageEntity> findAll(Integer pageSize, Integer page) {
        logger.info("START FILL ALL IMAGE");
        ArrayResultDTO arrayResultDTO = new ArrayResultDTO<>();
        List<ImagesDTO> list = new ArrayList<>();
        try {
            Page<ImageEntity> data = imagesRepository.findAll(PageRequest.of(page-1,pageSize));
            if(data!=null){
                if(data.getContent().size() >0){
                    for (ImageEntity imageEntity: data.getContent()
                    ) {
                        ImagesDTO imagesDTO = modelMapper.map(imageEntity,ImagesDTO.class);
                        list.add(imagesDTO);

                    }
                    arrayResultDTO.setSuccess(list,data.getTotalElements(),data.getTotalPages());
                    logger.info("FIND ALL IMAGES WITH RESPONSE:"+arrayResultDTO.getErrorCode());
                }
            }
        }catch (Exception e){
            arrayResultDTO.setFail("FAIL");
            logger.error(e.getMessage());
        }

        return arrayResultDTO;
    }

    @Override
    public BaseResultDTO addImage(MultipartFile file, HttpServletRequest httpServletRequest) {
        logger.info("ADD IMAGES");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String host = "http://"+ httpServletRequest.getHeader("host") + "/" + UPLOAD_DIR + "/";

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("api/downloadFile/")
//                    .path(fileName)
//                    .toUriString(); //ghép nối các phần để tạo thành link download file
            ImageEntity imageEntity = new ImageEntity(fileName, host + fileName);
            imageEntity = imagesRepository.save(imageEntity);
            singleResultDTO.setSuccess(imageEntity);
        } catch (Exception e) {
            singleResultDTO.setFail(e.getMessage());
            logger.error(e.getMessage());
        }


        return singleResultDTO;
    }

    @Override
    public BaseResultDTO deleteImages(Long id) {
        logger.info("DELETE IMAGES");
        SingleResultDTO singleResultDTO = new SingleResultDTO();
        try {
            if (id != null) {
                imagesRepository.deleteById(id);
                singleResultDTO.setSuccess();
            }
            logger.info("DELETE IMAGES FROM:" + singleResultDTO.getErrorCode());
        } catch (Exception e) {
            singleResultDTO.setFail(e.getMessage());
            logger.error(e.getMessage());
        }

        return singleResultDTO;
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public ImagesDTO findOneById(Long id) {
            ImageEntity imageEntity = imagesRepository.findImageEntityByImageId(id);
            ImagesDTO imagesDTO = modelMapper.map(imageEntity,ImagesDTO.class);
            return imagesDTO;
        }
    }

