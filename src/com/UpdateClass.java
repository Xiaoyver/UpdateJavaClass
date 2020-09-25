package com;

import org.gjt.jclasslib.io.ClassFileWriter;
import org.gjt.jclasslib.structures.ClassFile;
import org.gjt.jclasslib.structures.Constant;
import org.gjt.jclasslib.structures.constants.ConstantUtf8Info;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @ClassName UpdateClasses
 * @Description: 修改class文件内容
 * @Author Lgd
 * @Date 2020/9/25
 * @Version V1.0
 **/
public class UpdateClass {

    public static void main(String[] args) throws Exception {

        //class文件地址
        String filePath = "D:\\LoginSuccessListener.class";
        FileInputStream fis = new FileInputStream(filePath);

        DataInput di = new DataInputStream(fis);
        ClassFile cf = new ClassFile();
        cf.read(di);
        Constant[] infos = cf.getConstantPool();

        int count = infos.length;
        for(int i = 0; i < count; i++) {
            if(infos[i] != null) {
                System.out.print(i);
                System.out.print(" = ");
                System.out.print(infos[i].getVerbose());
                System.out.print(" = ");
                System.out.println(infos[i].getVerbose() != "" ? infos[i].getConstantType() : "");
                //要替换的字符串所在的数组下标，通过 jclasslib 去查看
                //https://github.com/ingokegel/jclasslib/releases
                if(i == 186) {
                    ConstantUtf8Info uInfo = (ConstantUtf8Info) infos[i];
                    //替换后的字符串
                    uInfo.setString("我就是最后的要字符串");
                    infos[i] = uInfo;
                }
            }
        }
        cf.setConstantPool(infos);
        fis.close();
        File f = new File(filePath);
        ClassFileWriter.writeToFile(f, cf);
    }
}
