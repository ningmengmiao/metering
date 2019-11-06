package cn.bptop.metering.until;

import java.io.File;

public class FileTool
{

    public static void deleteFile(String filePath)
    {
        File file = new File(filePath);
        boolean delete = file.delete();
    }
}
