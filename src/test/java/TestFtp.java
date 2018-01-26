import com.xmall.util.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xies
 * @date 2018/1/25
 */
public class TestFtp {
    public static void main(String[] args){
        File file1 = new File("D:\\data\\dbozzzzzzzzzzzzzzzzz.sql");
       // File file2 = new File("D:\\data\\1.txt");
        List<File> list = new ArrayList<>(10);
        list.add(file1);
        //list.add(file2);
        try{
           boolean success = FTPUtil.uploadFile(list);
           System.out.println("success: "+success);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
