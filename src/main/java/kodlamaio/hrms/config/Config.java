package kodlamaio.hrms.config;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import kodlamaio.hrms.core.imageUploaders.CloudinaryService;
import kodlamaio.hrms.core.imageUploaders.ImageService;
@Configuration
public class Config {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	
    public Cloudinary cloudinaryService(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dwkfso0ns",
                "api_key", "262274737437967",
                "api_secret", "0EEuRS4gv5OFdZsig6Xm_k6ynKw"));
    }

    @Bean
    public ImageService imageService(){
        return new CloudinaryService();
    }
}