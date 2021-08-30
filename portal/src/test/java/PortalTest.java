import com.yan.mall.portal.service.UmsMemberService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-27
 * Time: 17:21
 */
public class PortalTest {
    @Resource
    private UmsMemberService memberService;

    @Test
    public void dfasd(){
        memberService.loadUserByUsername("lisi");
    }
}
