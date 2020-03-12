package com.fanshang.noveltoutiao.controller;

import com.fanshang.noveltoutiao.entity.VisitData;
import com.fanshang.noveltoutiao.utils.HttpGetClient;
import com.fanshang.noveltoutiao.utils.JsonUtils;
import com.fanshang.noveltoutiao.utils.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/datahandler")
public class DataHandlerController {

    @Autowired
    private StringRedisTemplate redisTpl;

    @Autowired
    private RedisClient redis;

    @Autowired
    private HttpGetClient httpGetClient;

    @RequestMapping(value = "/visitdata", method = RequestMethod.POST)
    public void saveDataToRedis(String ip, String url, String ua){
        //eg:https://xs.lianrengu.cn/zyq002.html?adid=__AID__&creativeid=__CID__&creativetype=__CTYPE__&clickid=__CLICKID__
        VisitData visitData = new VisitData();
        visitData.setVisitIp(ip);
        visitData.setVisitUA(ua);
        visitData.setVisitUrl(url);
        String vdStr = JsonUtils.obj2String(visitData);
        boolean flag = redis.set(ip,vdStr);
        //System.out.println("访问记录的IP为:"+ip+">>>访问状态："+flag);
    }

    @RequestMapping(value="/receivedata", method = RequestMethod.POST)
    public int receiveData(int platform, String time, String open_id, String ua, String ip, String appflag) throws IOException {
        int code = 0;
        boolean flag = false;
        String vdStr = redis.get(ip);
        System.out.println(vdStr);
        if(vdStr != null){
            VisitData visitData = JsonUtils.string2Obj(vdStr, VisitData.class);
            //给头条回传信息https://ad.toutiao.com/track/activate/?link=__LINK__&source=__SOURCE__&conv_time=__CONVTIME__&event_type=__EVENT_TYPE__
            //__SOURCE_  为数据来源：deposit   __conv_time__ 为UTC时间戳，单位秒   __EVENT_TYPE__  为付费 值2   __LINK__落地页链接，必须对url编码
            String link = URLEncoder.encode(visitData.getVisitUrl(),"UTF-8");
            int conv_time = (int) (System.currentTimeMillis()/1000);
            String toutiaoUrl = "https://ad.toutiao.com/track/activate/?link="+link+"&source=deposit&conv_time="+conv_time+"&event_type=2";
            //String toutiaoUrl = "http://127.0.0.1:8000/datahandler/testdata/?link="+link+"&source=deposit&conv_time="+conv_time+"&event_type=2";
            System.out.println("NO1:"+toutiaoUrl);
            String result = httpGetClient.sendGetData(toutiaoUrl);
            if(result != null){
                flag = redis.delete(visitData.getVisitIp());
            }
            code = flag ? code: 1;
        }
        //System.out.println(visitData.getVisitUrl());
        return code;
    }

    @RequestMapping(value="/testdata", method = RequestMethod.GET)
    public String testdata(String link, String source, int conv_time, int event_type){
        System.out.println("NO2: link:"+link+"   source:"+source+"     conv_time:"+conv_time+"    event_type:"+event_type);
        return "success";
    }

}
