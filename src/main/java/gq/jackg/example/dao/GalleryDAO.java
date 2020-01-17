package gq.jackg.example.dao;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.util.Base64;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.ListItem;
import com.google.appengine.tools.cloudstorage.ListOptions;
import com.google.appengine.tools.cloudstorage.ListResult;
import com.google.appengine.tools.cloudstorage.RetryParams;

@Repository
public class GalleryDAO {
	private final static String NAME = "gallery";
	
	private final String bucketName = AppIdentityServiceFactory
											.getAppIdentityService()
											.getDefaultGcsBucketName();
	
	private GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
														.initialRetryDelayMillis(10)
														.retryMaxAttempts(10)
														.totalRetryPeriodMillis(15000)
														.build());
	
	public String getImg(String gsKey) {
		String gsname = new String(Base64.decodeBase64(gsKey));
		
		ImagesService imgService = ImagesServiceFactory.getImagesService();
		
		return imgService.getServingUrl(ServingUrlOptions.Builder.withGoogleStorageFileName(gsname).secureUrl(true));
	}
	
	public void uploadImg(MultipartFile file) {
		String temp = NAME + "/" + System.currentTimeMillis() + file.getName();
		
		GcsFilename filename = new GcsFilename(bucketName, temp);
		
		try {
			gcsService.createOrReplace(filename
										, GcsFileOptions.getDefaultInstance()
										, ByteBuffer.wrap(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getList() {
		ListResult results = null;
		
		try {
			results = gcsService.list(bucketName, new ListOptions.Builder().setPrefix(NAME).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> list = new ArrayList<String>();
		
		ImagesService imgService = ImagesServiceFactory.getImagesService();
		
		while (results.hasNext()) {
			ListItem listItem = (ListItem) results.next();
			
			String url = imgService.getServingUrl(ServingUrlOptions.Builder
										.withGoogleStorageFileName("/gs/" + bucketName + "/" + listItem.getName())
										);
			list.add(url);
		}
		
		return list;
	}
	
	public boolean deleteImg(String gsKey) {
		String gsname = new String(Base64.decodeBase64(gsKey));
		
		String[] names = gsname.split("/");
		
		GcsFilename filename = new GcsFilename(bucketName, NAME + "/" + names[names.length - 1]);
		
		boolean result = false;
		
		try {
			result = gcsService.delete(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
