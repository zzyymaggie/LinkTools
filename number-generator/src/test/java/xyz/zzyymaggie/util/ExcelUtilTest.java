package xyz.zzyymaggie.util;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ExcelUtilTest {
    @Test
    public void testWriteV2007() {
        try {
            ExcelUtil.writeV2007();
//            ExcelUtil.simpleReadListStringV2007();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}