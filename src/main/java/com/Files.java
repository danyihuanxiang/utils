package com;


import java.io.*;
import java.util.Arrays;

/**
 * 2018/10/03  单翼幻想
 * 判断文件目录里的文件组成，不是符合的文件删除
 *     if(file.isFile())//判断是否是一个标准文件
 *     if(file.isDirectory())//判断是否是文件夹
 *     if(file.isHidden())//判断是否是隐藏文件
 *     在Java中，不管是String.split()，还是正则表达式，有一些特殊字符需要转义，
 * 这些字符是
 * (  [   {  /  ^  -  $  ¦  }  ]  )  ?  *  +  . 
 * 转义方法为字符前面加上"\\"，这样在split、replaceAll时就不会报错了；
 * 不过要注意，String.contains()方法不需要转义。
 */

public class Files {
    /**
     * @param path
     */
    private static void traversal(String path,String suffix) {
        File file=new File(path);
        try {
                //判断是否为文件,根据文件后缀名来判断是否为需要删除的格式
            if (file.isFile()) {
                String pathName = file.getName();
                String[] splitPaths = pathName.split("\\.");
                if (null != splitPaths[1]) {
                    System.out.println(pathName);
                    if (splitPaths[1].equals(suffix)) {
                        deleteFile(file);
                    }
                }
            } else {
                //如果是文件夹，则递归判断，直到完成删除所有选定的格式
                File[] files = file.listFiles();
                for (File fs : files) {
                    traversal(fs.getPath(), suffix);

                }
            }
        }catch (Exception e){
            System.out.println(file.getName());
            e.printStackTrace();
        }
    }

 /*       private static void catalog(File file){
            File[] fs = file.listFiles();
            for(File f:fs){
                if(f.isDirectory())	//若是目录，则递归打印该目录下的文件
                    catalog(f);
                if(f.isFile()){
                    //若是文件，直接打印

                }
                    System.out.println(f);
               // deleteFile(f);
            }
        }*/

    private static void deleteFile(File file) {
        file.delete();
    }


     public static  void Copy(String path,String copyPath){
         File file=new File(path);
         if (file.isFile()){
                inputOut(file,copyPath);
         }else {
             System.out.println("不是文件");
         }
     }

    private static void inputOut(File file,String copyPath) {
        FileInputStream fis=null;
        FileOutputStream fos =null;
        try {
             fis = new FileInputStream(file);
            fos = new FileOutputStream(copyPath);
            byte[] b = new byte[1204];
            int len = 0;
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);//复制
            }
        }catch(FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
      /*  String path="E:\\葬花";
        String suffix="jpg";
        traversal(path,suffix);*/
      //先copy到一个文件夹，在当前java文件夹下生成一个文件放进图片
        Copy("E:\\night\\夜\\360wallpaper.jpg","E:\\temp\\360wallpaper.jpg");
        try {
            //生成一个zip包
            String path="E:\\temp\\360wallpaper.zip";
            ZipInput.toZip("E:\\temp\\360wallpaper.jpg", path,true);
            //解压zip包
          ZipOut.unZipFiles(new File("E:\\temp\\360wallpaper.zip"), "E:\\temp\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
