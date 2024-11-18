package com.shoppingcart.cart.service.image;

import com.shoppingcart.cart.dto.ImageDto;
import com.shoppingcart.cart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IImageService {

    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, long productId);
    void updateImage(MultipartFile file, long imageId);
}
