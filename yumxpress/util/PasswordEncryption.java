/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yumxpress.util;

import java.util.Base64;

/**
 *
 * @author ABC
 */
public class PasswordEncryption {
    public static String getEncryptedPassword(String pwd){
        Base64.Encoder en = Base64.getEncoder();
        String encrPwd = en.encodeToString(pwd.getBytes());
        return encrPwd;
    }
    
    public static String getDecryptedPassword(String pwd){
        Base64.Decoder dec = Base64.getDecoder();
        byte[] arr = dec.decode(pwd.getBytes());
        String decrPwd = new String(arr);
        return decrPwd;
    }
//    public static void main(String [] args){
//        String ans = PasswordEncryption.getDecryptedPassword("U2FudG9zaDEyMw==");
//        System.out.println(ans);
//    }
}
