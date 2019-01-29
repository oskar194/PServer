package com.admin.inz.server.budgetrook.services;

import java.util.Collection;

import com.admin.inz.server.budgetrook.dto.ImageDto;
import com.admin.inz.server.budgetrook.model.Image;
import com.admin.inz.server.budgetrook.model.User;

public interface ImageService {
	public void saveImage(ImageDto image);
	public Image getImageByName(String name);
	public Collection<Image> getImagesByOwner(User owner);
	public void deleteImage(Image image);
	public void saveImage(Image image);
}
