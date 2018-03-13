package com.bb.file;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.FileUtils ;
import java.io.*;
import java.util.List;


public class FileHandlerTest {


    public static void main(String[] argv){
            doIt();
    }

    private static void doIt(){
        String dirPath = "E:\\mj美剧" ;
        File file = new File(dirPath);
        if(file.isDirectory()){

            File[]  srtFiles = file.listFiles(new SrtFileFilter(".srt") ) ;

            try {
                for (File srtFile :  srtFiles) {
                    StringBuffer sb = new StringBuffer("");
                    List<String> contents = FileUtils.readLines( srtFile );
                    for (int i = 0; i < contents.size() ; i++) {

                        String content = contents.get(i);
                        if( i%4 == 2 ){
                            System.out.println( content );
                            sb.append(content + "\r\n");
//                            break;
                        }
                    }

                    File toFile = new File(dirPath + "\\" + srtFile.getName().substring(0 , srtFile.getName().lastIndexOf(".srt") ) + ".txt" );
                    FileUtils.writeStringToFile( toFile  , sb.toString() );
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println( "111===" );
        }
    }

}