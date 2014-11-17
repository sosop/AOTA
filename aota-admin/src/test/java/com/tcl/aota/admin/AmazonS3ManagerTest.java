package com.tcl.aota.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tcl.aota.admin.dto.PackageDTO;
import com.tcl.aota.common.utils.DateUtil;
import com.tcl.aota.common.utils.JsonParser;

/*import com.tcl.aota.admin.util.FileStoreUtil;
import com.tcl.aota.common.utils.AmazonS3Op;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;*/

/**
 * @author kelong
 * @date 11/6/14
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")*/
public class AmazonS3ManagerTest {
    /*@Resource
    private AmazonS3Op amazons3Op;

    @Test
    public void storeS3Test() {
        Map<File, String> s3Map = new HashMap<File, String>();
        File file = new File("/home/kelong/Desktop/com.kingroot.RootManager_72592300_0.apk");
        System.out.println(FilenameUtils.getExtension(file.getName()));
        System.out.println(FilenameUtils.getBaseName(file.getName()));
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
//        boolean flag = amazons3Op.copy(file, "aota/apk/com.kingroot.RootManager_72592300_0.apk");
    }

    @Test
    public void delS3Test() {
        boolean flag = amazons3Op.deleteFile("aota/apk/com.kingroot.RootManager_72592300_0.apk");
    }

    @Test
    public void uuidTest(){
        int x= RandomUtils.nextInt(10000);
        String fileName="com.kingroot.RootManager_72592300_0.apk";
        String baseName=FilenameUtils.getBaseName(fileName);
        String fileextengs=FilenameUtils.getExtension(fileName);
        String uuid=FileStoreUtil.gerneratedFileUUid(baseName,fileextengs);
//        String recoverName=FileStoreUtil.getFileViaNameUUID(uuid);
    }

*/
	// @Test
	public void test() {
		PackageDTO dto = new PackageDTO();
		dto.setCreateTime(DateUtil.fomartDateToStr("yyyy-MM-dd HH:mm:ss", new Date()));
		dto.setPackageName("p14888999332");
		dto.setQuantity(10);
		dto.setSequence(888);
		System.out.println(JsonParser.toString(dto));
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(5L);
		System.out.println(JsonParser.toString(ids));
	}

}
