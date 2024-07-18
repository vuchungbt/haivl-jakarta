package blwsmartware.service;

import java.io.File;

public interface ImageService {
    int DEFAULT_WIDTH=200 ;
    int DEFAULT_HEIGHT=200 ;
    String DOT_EXTENDS =".JPG";
    File getFile();
    String getFolderUpload();
    void saveImageToDisk();
    void createThumbnail();
    File resize();
    File scale(double scale);
    File createWatermark();
    void submitSystemWaterMark();
    boolean delete();
}
