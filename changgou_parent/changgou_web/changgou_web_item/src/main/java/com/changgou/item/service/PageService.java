package com.changgou.item.service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface PageService {
    /**
     * 根据商品的ID 生成静态页
     * @param spuId
     */
    void createPageHtml(Long spuId) throws FileNotFoundException, UnsupportedEncodingException;

}
