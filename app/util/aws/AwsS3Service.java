package util.aws;

import static com.google.common.base.Joiner.on;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.Date;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.common.net.MediaType;

import dto.FileDTO;
import util.exception.ModelException;

public class AwsS3Service {

	public static final String FILES_FOLDER = "files";
	public static final String GEOM_FOLDER = "geom";
	public static final String IMGS_FOLDER = "imgs";
	private static final AwsS3Service INSTANCE = new AwsS3Service();
	public static final String REPORTS_FOLDER = "reports";
	public static final String FILES_PUBLIC = "public";

	public static AwsS3Service getInstance() {
		return INSTANCE;
	}

	public static String getPathGeom(Integer geomId) {
		return on("/").join(FILES_FOLDER, GEOM_FOLDER, geomId);
	}

	public static String getPathPublic() {
		return FILES_PUBLIC;
	}

	public static String prepareFileName(String fileName) {
		fileName = fileName.toLowerCase();
		fileName = Normalizer.normalize(fileName, Normalizer.Form.NFD);
		fileName = fileName.replaceAll("[^\\p{ASCII}]", "");
		fileName = fileName.replaceAll("\\s+", "_");
		fileName = fileName.replaceAll("\\(|\\)", "_");
		String extensao = "";
		if (fileName.contains(".")) {
			extensao = fileName.substring(fileName.lastIndexOf("."));
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		fileName = fileName + "_" + System.currentTimeMillis() + extensao;
		return fileName;
	}

	private String awsBaseFolder;

	private String awsS3Bucket;

	private AmazonS3 s3Client;

	private AwsS3Service() {
		System.out.println("Iniciando AwsS3Service...");
		String awsAccessKey = "key";
		String awsSecretAccessKey = "key";
		String awsS3Endpoint = "s3-sa-east-1.amazonaws.com";
		awsS3Bucket = "geo-coleta";
		awsBaseFolder = "dbs";

		s3Client = new AmazonS3Client(new BasicAWSCredentials(awsAccessKey, awsSecretAccessKey));
		s3Client.setEndpoint(awsS3Endpoint);
	}

	public File download(String folder, String fileName) throws IOException {
		long millis = System.currentTimeMillis();
		File targetFile = null;
		targetFile = File.createTempFile("tmpS3File", String.valueOf(millis));
		s3Client.getObject(new GetObjectRequest(awsS3Bucket, getCompleteKey(folder, fileName)), targetFile);
		return targetFile;
	}

	private String getCompleteKey(String folder, String fileName) {
		return on("/").join(awsBaseFolder, folder, fileName);
	}

	public URL getObject(String folder, String fileName) {
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(awsS3Bucket, getCompleteKey(folder, fileName));
		return s3Client.generatePresignedUrl(request);
	}

	public URL getObject(String folder, String fileName, Date expiration) {
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(awsS3Bucket, getCompleteKey(folder, fileName));
		request.setExpiration(expiration);
		return s3Client.generatePresignedUrl(request);
	}

	private String getRootCompleteKey(String folder, String fileName) {
		return on("/").join(folder, fileName);
	}

	public String getUrlFromPublicObject(String folder, String fileName) {
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(awsS3Bucket, getRootCompleteKey(folder, fileName));
		URL url = s3Client.generatePresignedUrl(request);
		return "https://" + url.getHost() + url.getPath();
	}

	public void putObject(String path, long length, InputStream inputStream, String contentType, CannedAccessControlList cannedAccessControlList) {
		if (cannedAccessControlList == null) {
			cannedAccessControlList = CannedAccessControlList.Private;
		}

		final ObjectMetadata om = new ObjectMetadata();
		om.setContentLength(length);
		if (contentType != null) {
			om.setContentType(contentType);
		}
		s3Client.putObject(awsS3Bucket, path, inputStream, om);
		s3Client.setObjectAcl(awsS3Bucket, path, cannedAccessControlList);
	}

	public void putObject(String folder, String fileName, File file) {
		try {
			putObject(getCompleteKey(folder, fileName), file.length(), new FileInputStream(file), null, CannedAccessControlList.Private);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Arquivo inválido para upload no S3.");
		}
	}

	public void putObject(String folder, String fileName, File file, String contentType) {
		try {
			putObject(getCompleteKey(folder, fileName), file.length(), new FileInputStream(file), contentType, CannedAccessControlList.Private);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Arquivo inválido para upload no S3.");
		}
	}

	public void putObject(String folder, String fileName, long length, InputStream inputStream, String contentType) {
		try {
			putObject(getCompleteKey(folder, fileName), length, inputStream, contentType, CannedAccessControlList.Private);
		} catch (Exception e) {
			throw new IllegalArgumentException("Arquivo inválido para upload no S3.");
		}
	}

	public void putObjectPublic(String folder, String fileName, File file, String contentType) {
		try {
			putObject(getRootCompleteKey(folder, fileName), file.length(), new FileInputStream(file), contentType, CannedAccessControlList.PublicRead);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Arquivo inválido para upload no S3.");
		}
	}

	public void putObjectPublic(String folder, String fileName, long length, InputStream inputStream, String contentType) {
		try {
			putObject(getRootCompleteKey(folder, fileName), length, inputStream, contentType, CannedAccessControlList.PublicRead);
		} catch (Exception e) {
			throw new IllegalArgumentException("Arquivo inválido para upload no S3.");
		}
	}

	public void removeObject(String folder, String fileName) {
		s3Client.deleteObject(awsS3Bucket, getCompleteKey(folder, fileName));
	}

	public void renameObject(String folder, String fileName, String newFolder, String newFileName) {
		s3Client.copyObject(awsS3Bucket, getCompleteKey(folder, fileName), awsS3Bucket, getCompleteKey(newFolder, newFileName));
		s3Client.setObjectAcl(awsS3Bucket, getCompleteKey(newFolder, newFileName), CannedAccessControlList.Private);
		removeObject(folder, fileName);
	}

	public FileDTO sendBytesToS3(FileDTO fileDTO, String pathImage) throws ModelException {
		String contentType = fileDTO.getContentType();
		MediaType mediaType = MediaType.parse(contentType);

		if (mediaType.is(MediaType.ANY_IMAGE_TYPE) || mediaType.is(MediaType.PDF) || mediaType.is(MediaType.MICROSOFT_WORD) || mediaType.is(MediaType.MICROSOFT_EXCEL)
				|| mediaType.is(MediaType.OOXML_DOCUMENT) || mediaType.is(MediaType.OOXML_SHEET)) {

			String fileName = AwsS3Service.prepareFileName(fileDTO.getFileName());

			getInstance().putObjectPublic(pathImage, fileName, fileDTO.getBytes().length, new ByteArrayInputStream(fileDTO.getBytes()), contentType);
			fileDTO.setPublicURL(getInstance().getUrlFromPublicObject(pathImage, fileName));
		}
		return fileDTO;
	}

	public FileDTO sendFileToS3(FileDTO fileDTO, String pathImage) throws ModelException {
		String contentType = fileDTO.getContentType();
		MediaType mediaType = MediaType.parse(contentType);

		if (mediaType.is(MediaType.ANY_IMAGE_TYPE) || mediaType.is(MediaType.PDF) || mediaType.is(MediaType.MICROSOFT_WORD) || mediaType.is(MediaType.MICROSOFT_EXCEL)
				|| mediaType.is(MediaType.OOXML_DOCUMENT) || mediaType.is(MediaType.OOXML_SHEET)) {

			String fileName = AwsS3Service.prepareFileName(fileDTO.getFileName());

			getInstance().putObjectPublic(pathImage, fileName, fileDTO.getFile(), fileDTO.getContentType());
			fileDTO.setPublicURL(getInstance().getUrlFromPublicObject(pathImage, fileName));
		}
		return fileDTO;
	}
}
