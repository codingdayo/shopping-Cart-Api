package com.codingdayo.shopping_cart.service.image.interfac;

import com.codingdayo.shopping_cart.dto.ImageDto;
import com.codingdayo.shopping_cart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);

    void deleteImageById(Long id);

    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);

    void updateImage(MultipartFile file, Long imageId);

}
