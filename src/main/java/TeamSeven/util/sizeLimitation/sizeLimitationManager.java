package TeamSeven.util.sizeLimitation;

import java.io.File;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by tina on 16/5/9.
 */
public interface SizeLimitationManager {

    long folderSize( File directory );
    
    boolean checkSingleFileSize( String filePath, String content ) throws Exception;
    boolean checkFolderSize( String folderPath ) throws Exception;
    boolean checkSingleFileSize( String filePath, String content, int singleFileSize ) throws Exception;
    boolean checkFolderSize( String folderPath, int totalFileSize ) throws Exception;

    boolean delete( String fileName );
    boolean deleteFile( String fileName );
    boolean deleteDirectory( String dir );

    boolean cleanFolder( String folderName, Date date ) throws ParseException;

}
