package com.admin.inz.server.budgetrook.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admin.inz.server.budgetrook.dto.ImageDto;
import com.admin.inz.server.budgetrook.model.Image;
import com.admin.inz.server.budgetrook.model.User;
import com.admin.inz.server.budgetrook.repositories.ImageRepository;
import com.admin.inz.server.budgetrook.repositories.UserRepository;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ImageRepository imageRepository;
	@Autowired
	ConversionService conversionService;

	@Override
	public void saveImage(ImageDto imageDto) {
		Image image = conversionService.imageDtoToModel(imageDto);
		imageRepository.save(image);
	}

	@Override
	public Image getImageByName(String name) {
		return imageRepository.findByName(name);
	}

	@Override
	public Collection<Image> getImagesByOwner(User owner) {
		return owner.getImages();
	}

	@Override
	public void deleteImage(Image image) {
		imageRepository.delete(image);
	}

	@Override
	public void saveImage(Image image) {
		imageRepository.save(image);
	}

}
