package com.bid90.app.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bid90.app.model.Image;
import com.bid90.app.service.ImageService;



@RestController
@RequestMapping("api/file")
public class FileController {

	@Autowired
	ImageService imageService;
	
	
	@RequestMapping(value = "/get/image",method = RequestMethod.GET ,produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
	public  byte[] getFile(@RequestParam(name = "name") String name) throws IOException {
	
			Image image = imageService.findByName(name);
			if(image == null) {
				int width = 350;
		        int height = 350;
				BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bufferedImage.createGraphics();
				for(int x = 0; x <= width; x += 35) {
					for(int y = 0; y <= height; y += 35) {
						if(x % 2 == 0 && y % 2 != 0 || x % 2 != 0 && y % 2 == 0 ){
							g2d.setColor(Color.decode("#C2C2C2"));
						}
						else {
							g2d.setColor(Color.decode("#D8D8D8"));
						}
						
						g2d.fillRect(x, y, 35, 35);
					}
				}
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "png", os);
				InputStream in = new ByteArrayInputStream(os.toByteArray());
			    return IOUtils.toByteArray(in);
			}
		    InputStream in = new FileInputStream(image.getPath());
		    return IOUtils.toByteArray(in);
	}
	
}
