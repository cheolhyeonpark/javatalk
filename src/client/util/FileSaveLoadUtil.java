package javatalk.client.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.swing.JOptionPane;

import javatalk.model.FilePath;

/*
 * 전송된 파일의 save, load
 * fileSave() - file을 save한 뒤 저장한 이름을 return
 * fileLoad() - byte 단위로 load 된 file을 return
 */

public class FileSaveLoadUtil {

	public static String fileSave(String extention, String path, byte[] file) {
		String fileName = UUID.randomUUID() + extention;
		try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
				new FileOutputStream(path + fileName))) {
			bufferedOutputStream.write(file, 0, file.length);
			bufferedOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	public static byte[] fileLoad(String path) {
		File file = new File(path);
		byte[] loadFile = new byte[(int)file.length()];
		BufferedInputStream bufferedInputStream;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			bufferedInputStream.read(loadFile, 0, loadFile.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loadFile;
	}
}
