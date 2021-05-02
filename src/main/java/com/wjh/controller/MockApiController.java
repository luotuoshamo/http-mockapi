package com.wjh.controller;

import com.wjh.util.RequestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mock")
public class MockApiController {
    @GetMapping(value = "/get")
    public Object get(HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        Map<String, String> headers = RequestUtil.headers(request);
        Map<String, String> params = RequestUtil.params(request);

        map.put("requestHeader", headers);
        map.put("params", params);
        map.put("responseBody", "get-res");
        return map;
    }

    @PostMapping(value = "/post_formUrlEncoded")
    public Object post_urlencoded(HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        Map<String, String> headers = RequestUtil.headers(request);
        Map<String, String> params = RequestUtil.params(request);

        map.put("requestHeader", headers);
        map.put("params", params);
        map.put("responseBody", "post_formUrlEncoded-res");
        return map;
    }

    @PostMapping(value = "/post_formData")
    public Map<String, Object> post_formData(MultipartFile file1, MultipartFile file2, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap();
        Map<String, String> headers = RequestUtil.headers(request);
        Map<String, String> params = RequestUtil.params(request);

        map.put("requestHeader", headers);
        map.put("params", params);
        map.put("file1", file1.getName() + "," + file1.getSize());
        map.put("file2", file2.getName() + "," + file2.getSize());
        map.put("responseBody", "post_formData-res");
        return map;
    }

    @PostMapping(value = "/post_row")
    public Map<String, Object> post_row(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap();
        Map<String, String> headers = RequestUtil.headers(request);

        // 这样不行，is.available()=1
        // ServletInputStream is = request.getInputStream();
        // byte[] bytes = new byte[is.available()];
        // is.read(bytes);
        // is.close();
        // String json = new String(bytes);

        ServletInputStream sis = request.getInputStream();
        byte[] bytes = new byte[1024];
        String json = "";
        while (true) {
            int i = sis.readLine(bytes, 0, bytes.length);
            if (i == -1) break;
            json += (new String(bytes, 0, i));
        }

        map.put("requestHeader", headers);
        map.put("param", json);
        map.put("responseBody", "post_row-res");
        return map;
    }
}
