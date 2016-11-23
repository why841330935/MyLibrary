package com.example.admin.myapplication.http;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author ymy
 * 
 * @// TODO: 接口请求方法
 */
public interface IApiService
{
    /**2.16老师列表查询接口**/
    @GET(RequestUrl.GET_FINDTEACHLIST)
    Call<Object> getTeachList();
  }
