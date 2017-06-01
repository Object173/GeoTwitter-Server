package com.object173.geotwitter.server.service;

import com.object173.geotwitter.server.contract.ResourcesContract;
import com.object173.geotwitter.server.contract.ServiceContract;
import com.object173.geotwitter.server.entity.Image;
import com.object173.geotwitter.server.repository.ImageRepository;
import com.object173.geotwitter.server.utils.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository repository;

    public final Image createImage(final byte[] image, final String path, final String filename) {
        if(image == null || path == null || filename == null) {
            return null;
        }
        final String imagePath = FileManager.writeFile(image, path, filename);
        if(imagePath == null) {
            return  null;
        }
        return new Image(imagePath);
    }

    public final Image insert(final Image image) {
        if(image == null) {
            return null;
        }
        return repository.saveAndFlush(image);
    }

    public final Image createAndSaveImage(final byte[] imageBytes) {
        if(imageBytes == null || imageBytes.length <= 0) {
            System.out.println("null pointer");
            return null;
        }
        final Image dataImage = repository.saveAndFlush(new Image(ResourcesContract.getImagePath()));
        if(dataImage == null) {
            System.out.println("dataImage == null");
            return null;
        }
        final String imageName = ResourcesContract.createImageName(dataImage.getId());
        final Image newImage = createImage(imageBytes, ResourcesContract.getImagePath(), imageName);
        if(newImage == null ||
                repository.setUrl(dataImage.getId(), newImage.getUrl()) <= ServiceContract.NULL_ID) {
            repository.delete(dataImage.getId());
            return null;
        }
        return repository.findOne(dataImage.getId());
    }

    public final void remove(final Image image) {
        if(image != null) {
            if(image.getUrl() != null) {
                FileManager.deleteFile(image.getUrl());
            }
            repository.delete(image.getId());
        }
    }

    public final byte[] getImage(final long id) {
        final Image image = repository.findOne(id);
        if(image == null) {
            return  null;
        }
        return FileManager.readFile(image.getUrl());
    }
}
