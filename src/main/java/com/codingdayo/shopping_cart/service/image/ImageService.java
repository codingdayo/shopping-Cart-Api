package com.codingdayo.shopping_cart.service.image;

import com.codingdayo.shopping_cart.Exceptions.ResourceNotFoundException;
import com.codingdayo.shopping_cart.dto.ImageDto;
import com.codingdayo.shopping_cart.model.Image;
import com.codingdayo.shopping_cart.model.Product;
import com.codingdayo.shopping_cart.repository.ImageRepository;
import com.codingdayo.shopping_cart.service.image.interfac.IImageService;
import com.codingdayo.shopping_cart.service.product.interfac.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));

    }

    @Override
    public void deleteImageById(Long id) {

        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("No image found with id: " + id);
        });

    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        //before this part was written, a dto was created to specify what was returned back to the client.
        //Also a product has multiple images.

        Product product = productService.getProductById(productId);


        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();

                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                //building url path for download
                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
              Image savedImage =  imageRepository.save(image);


              savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
              imageRepository.save(savedImage);


              ImageDto imageDto = new ImageDto();
              imageDto.setId(savedImage.getId());
              imageDto.setFileName(savedImage.getFileName());
              imageDto.setDownloadUrl(savedImage.getDownloadUrl());
              savedImageDto.add(imageDto);


            } catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());

            }

        }

        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {

            Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}