package TeamSeven.util.zip;

import java.io.*;
import java.util.*;
import java.util.zip.ZipInputStream;

public interface ZipManager {

	void endZip() throws IOException;
	void messageRecord( String content ) throws Exception;

	String getZipFilePath();
	String getWeeklyZipFilePath();
	Date getWeeklyZipTime();
	Date getZipTime();
	void doDailyCompass();
	void doWeeklyCompass( List<File> zipFileList ) throws Exception;

	// 压缩
	boolean compress( String destFileName );
	boolean compress( String destFileName, String tempDir ) throws Exception;

	// 解压
	void decompress( String srcPath ) throws Exception;
	void decompress( File srcFile ) throws Exception;
	void decompress( File srcFile, File destFile ) throws Exception;
	void decompress( File srcFile, String destPath ) throws Exception;
	void decompress( String srcPath, String destPath ) throws Exception;
	void decompress( File destFile, ZipInputStream zis ) throws Exception;
	void fileProber( File dirFile);

}



	