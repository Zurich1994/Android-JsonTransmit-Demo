package com.gem.hsx.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.gem.hsx.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class JsonUtil {
	public List<User> StringFromJson (String jsondata)
	{     
		Type listType = new TypeToken<List<User>>(){}.getType();
		Gson gson=new Gson();
		ArrayList<User> list=gson.fromJson(jsondata, listType);
		return list;

	}
}
