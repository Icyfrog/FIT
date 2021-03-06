package FIT.user.Service;

import FIT.user.Entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Rollback(value = true)
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void findByTel() {
        Assert.assertEquals("Test UserDao.findByTel", "队友呢", userService.findByTel("6827").getNickName());
    }

    @Test
    public void login() {
        assertEquals("100", userService.login("6827", "aaaaa"));
        assertEquals("101", userService.login("MakeAmericaGreatAgain", "a"));
        assertEquals("102", userService.login("6827", "a"));
        assertEquals("103", userService.login("13262606827", "test"));
    }

    @Test
    public void registerTest() {
        User user = new User();
        user.setNickName("Never use it");
        user.setPassword("Never use it");
        user.setTel("Never use it");
        user.setIsactive(1);
        assertEquals("1", userService.register(user));
        User user1 = userService.findByTel("6827");
        assertEquals("0", userService.register(user1));
    }

           /**
           需要THROW CATCH
            **/
    /*
    @Test
    public void changePwdTest() {
        User user1 = userService.findByTel("101");
        User user = userService.findByTel("Never use it");
        assertEquals("0", userService.changePwd("Never use it", "Change!"));
        assertEquals("1", userService.changePwd("101", "succeed!"));
    }

     */

    @Test
    public void saveTest() {
        User user = new User();
        user.setNickName("Never use it");
        user.setPassword("Never use it");
        user.setTel("Never use it");
        user.setIsactive(1);
        userService.save(user);
        User ut = userService.findByTel("Never use it");
        assertNotNull(ut);
    }

    @Test
    public void findAllTest() {
        Iterator<User> ss = userService.findAll().iterator();
        ss.next().getNickName();
        assertEquals("复读机天下无敌\n",ss.next().getNickName());
    }

    /**
     * 考虑到涉及到别家的API，所以这方面不进行测试了
     *
     *
    @Test
    public void chanegInfoTest() throws Exception {
        JSONObject data_f = new JSONObject();
        data_f.put("tel", "Never has it!");
        // assertEquals(false,userService.changeUserInfo(data_f));
        assertFalse(userService.changeUserInfo(data_f));
        JSONObject data_t = new JSONObject();
        data_t.put("tel","101");
        data_t.put("avatar","New AVATAR");
        data_t.put("nickName","nn");
        data_t.put("gender",1);
        data_t.put("birthday","1895-7-5");
        data_t.put("weight",55.5);
        data_t.put("height",178);
        assertTrue(userService.changeUserInfo(data_t));
        // assertEquals(true, userService.changeUserInfo(data_t));
        assertEquals("nn",userService.findByTel("101").getNickName());
    }
    */

    /**
     * 考虑到涉及到别家的API，所以这方面不进行测试了
    @Test
    public void sendMessageTest() {

        userService.sendMessage("18621105309");

        //还有 catch语段怎么做测试。这个回头看百度这些东西，查一查教程

        //1. https://cloud.tencent.com/developer/ask/171070

    }
    */
}