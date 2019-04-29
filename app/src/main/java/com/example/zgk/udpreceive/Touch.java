package com.example.zgk.udpreceive;

/**
 * Created by ZGK on 2019/4/28.
 */

public interface Touch {
    void move(byte[] msg);
    void touch();
}
