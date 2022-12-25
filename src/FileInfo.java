public class FileInfo {

    private String fileName;
    private Long fileLength;

    public FileInfo(String fileName, Long fileLength){
        this.fileName = fileName;
        this.fileLength = fileLength;
    }

    public String getFileName(){
        return fileName;
    }

    public Long getFileLength(){
        return fileLength;
    }
}
