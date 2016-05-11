package TeamSeven.util.sizeLimitation;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tina on 16/5/9.
 */
public class SizeLimitationManagerImpl implements SizeLimitationManager {

    private int singleFileSize;
    private int totalFileSize;

    public SizeLimitationManagerImpl() {}
    public SizeLimitationManagerImpl( int singleFileSize, int totalFileSize ) {
        this.singleFileSize = singleFileSize;
        this.totalFileSize = totalFileSize;
    }

    public long folderSize( File directory ) {
        long length = 0;
        if ( directory.exists() ) {
            File [] files = directory.listFiles();
            if ( files != null ) {
                for ( File file : files ) {
                    if ( file.isFile() )
                        length += file.length();
                    else if( file.isDirectory() )
                        length += folderSize( file );
                }
            }
        }
        return length;
    }

    public boolean checkSingleFileSize( String filePath, String content ) throws Exception {
        return checkSingleFileSize( filePath, content, this.singleFileSize );
    }

    public boolean checkSingleFileSize( String filePath, String content, int singleFileSize ) throws Exception {
        File tar = new File( filePath );
        int addSize = content.getBytes().length;

        if ( ( tar.length() + addSize ) > singleFileSize ) {
            System.out.println( "写入的目标文件'" + filePath + "'大小超过限制" );
            return false;
        } else {
            return true;
        }
    }

    public boolean checkFolderSize( String folderPath ) throws Exception {
        return checkFolderSize( folderPath, this.totalFileSize );
    }

    public boolean checkFolderSize( String folderPath, int totalFileSize ) throws Exception {
        File tar = new File( folderPath );

        if ( folderSize( tar ) > totalFileSize ) {
            System.out.println( "文件夹'" + folderPath + "'大小超过限制" );
            return false;
        } else {
            return true;
        }
    }

    // 删除文件
    public boolean delete( String fileName ) {

        File file = new File( fileName );
        if ( !file.exists() ) {
            System.out.println("删除文件失败："+fileName+"文件不存在");
            return false;
        } else {
            if ( file.isFile() ) {

                return deleteFile( fileName );
            } else {
                return deleteDirectory( fileName );
            }
        }
    }

    public boolean deleteFile( String fileName ) {

        File file = new File( fileName );
        if( file.isFile() && file.exists() ) {

            file.delete();
            System.out.println( "删除单个文件"+fileName+"成功！" );
            return true;
        } else {

            System.out.println( "删除单个文件"+fileName+"失败！" );
            return false;
        }
    }

    public boolean deleteDirectory( String dir ) {

        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if( !dir.endsWith( File.separator ) ) {
            dir = dir + File.separator;
        }
        File dirFile = new File( dir );
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if( !dirFile.exists() || !dirFile.isDirectory() ) {

            System.out.println( "删除目录失败: " + dir + "目录不存在！" );
            return false;
        }

        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for( int i = 0; i < files.length; i ++ ) {

            //删除子文件
            if( files[i].isFile() ) {

                flag = deleteFile( files[i].getAbsolutePath() );
                if( !flag ) {
                    break;
                }
            }
            //删除子目录
            else {
                flag = deleteDirectory( files[i].getAbsolutePath() );
                if( !flag ) {
                    break;
                }
            }
        }

        if( !flag ) {
            System.out.println( "删除目录失败" );
            return false;
        }

        //删除当前目录
        if( dirFile.delete() ) {
            System.out.println( "删除目录"+dir+"成功！" );
            return true;
        } else {
            System.out.println( "删除目录"+dir+"失败！" );
            return false;
        }
    }

    // 清理在 date 之前生成的 message log file
    public boolean cleanFolder( String folderName, Date date ) throws ParseException {

        if( !folderName.endsWith( File.separator ) ) {
            folderName = folderName + File.separator;
        }
        File dirFile = new File( folderName );
        if( !dirFile.exists() || !dirFile.isDirectory() ) {

            System.out.println( "清理目录失败: " + folderName + "目录不存在！" );
            return false;
        }

        File[] files = dirFile.listFiles();
        for ( File file : files ) {

            if ( file.isFile() && file.getName().substring( file.getName().lastIndexOf( "." ) ).equals( ".txt" ) ) {

                String name = file.getName().substring( 0, file.getName().lastIndexOf( "." ) );
                if ( !name.equals( "" ) ) {

                    Date tarDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(name);
                    if ( tarDate.before( date ) ) {
                        deleteFile( file.getAbsolutePath() );
                    }
                }
            }
        }
        return true;
    }

}
