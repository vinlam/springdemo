package com;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CompressUtil {
	
    public CompressUtil() {}
     
    public static void zip(String inputFileName,String zipFileName) throws Exception {
       // String zipFileName = "e:/test/test.zip"; 
        //System.out.println(zipFileName);
        zip(zipFileName, new File(inputFileName));
    }

    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        String filePath = inputFile.getPath();
        String base = filePath.substring(filePath.lastIndexOf(File.separator)+1,filePath.length());
        zip(out, inputFile, "");// zip(out, inputFile,base);
        //System.out.println("zip done");  
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           if(!"".equals(base))
           out.putNextEntry(new  ZipEntry(base + File.separator));
           base = base.length() == 0 ? "" : base + File.separator;
           for (int i = 0; i < fl.length; i++) {
           zip(out, fl[i], base + fl[i].getName());
         }
        }else {
           out.putNextEntry(new ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           int b;
           //System.out.println(base);
           byte data[] = new byte[8192];  
			int count;
			while ((count = in.read(data, 0, 8192)) != -1) {
				out.write(data, 0, count);
			}

           
         in.close();
       }
    }
    public static void unZip(String zipPath,String Path) throws Exception{   
		int index = -1;
		int buffer = 2048;
		int count = -1;
        BufferedOutputStream bos = null;   
        ZipEntry entry = null;   
        FileInputStream fis = new FileInputStream(zipPath);    
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));             
        while((entry = zis.getNextEntry()) != null)    
        {   
            byte data[] = new byte[buffer];
            String temp = entry.getName();
            String [] tempArr = temp.split("/");
            if(tempArr!=null){
            	StringBuffer strBuf = new StringBuffer();
            	strBuf.append(Path);
            	for(int i=0;i<tempArr.length;i++){
            		strBuf.append(File.separator+tempArr[i]);
                	File file = new File(strBuf.toString());
                	if(!file.exists()){
                		file.mkdir();
                	}
                }
            }
            
//            System.out.println(Path+temp);
            //File f = new File((savepath+temp).substring(0, (savepath+temp).lastIndexOf(File.separator)));
            String path = Path+File.separator+temp;
            index = path.lastIndexOf(File.separator);
            String isfileString = path.substring(index+1,path.length());
            File f = new File(path);
            if(isfileString.equals("")||entry.isDirectory()){//文件夹
            	if(!f.exists()){
            		f.mkdirs();
            	}  
            }else {  
            	if(f.exists()){
            		f.delete();
            	}
                //f.createNewFile();   

                FileOutputStream fos = new FileOutputStream(f);   
                bos = new BufferedOutputStream(fos, buffer);                 
                while((count  = zis.read(data, 0, buffer)) != -1)    
                {   
                    bos.write(data, 0, count);   
                }   
                bos.flush();   
                bos.close();   
                fos.close();
            }
            
        }   

        zis.close();   
        fis.close();
//        System.out.println("******************解压完毕********************");
    } 
    
    
}