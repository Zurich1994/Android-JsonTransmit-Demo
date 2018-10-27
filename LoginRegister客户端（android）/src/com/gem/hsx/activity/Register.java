package com.gem.hsx.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.gem.hsx.bean.User;
import com.gem.hsx.json.WriteJson;
import com.gem.hsx.operation.Operaton;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;


public class Register extends Activity {
	Button submit;
	Button select;
	EditText etusername;
	EditText etpassword;
	RadioButton ckman;
	RadioButton ckwoman;
	EditText etage;
	ImageView imgphoto;
	String str;
	String filepath=null;
	String jsonString=null;
	ProgressDialog dialog;
	private static final int REQUEST_EX = 1;
	String username=null;
	String password=null;
	String sex=null;
	String age=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		init();
		//etusername.addTextChangedListener(new MyTextWatcher());
		etusername.setOnFocusChangeListener(new EtusernameOnFocusChange());
		select.setOnClickListener(new SelectOnclick());
		submit.setOnClickListener(new SubmitOnclick());
	}
	private void init()
	{
		submit=(Button) findViewById(R.id.submit);
		select=(Button) findViewById(R.id.select);
		etusername=(EditText) findViewById(R.id.etusername);
		etpassword=(EditText) findViewById(R.id.etpassword);
		ckman=(RadioButton) findViewById(R.id.ckman);
		ckwoman=(RadioButton) findViewById(R.id.ckwoman);
		etage=(EditText) findViewById(R.id.etage);
		imgphoto=(ImageView) findViewById(R.id.imgphoto);
		dialog=new ProgressDialog(Register.this);
		dialog.setTitle("上传数据中");
		dialog.setMessage("请稍等...");
	}

	//	private   class MyTextWatcher implements TextWatcher
	//	{
	//
	//		public void afterTextChanged(Editable s) {
	//			 str=etusername.getText().toString().trim();
	//			 if (str==null||str.length()<=0) 
	//			 {
	//				etusername.setError("用户名不能为空");
	//			 }
	//			 else 
	//			 {	
	//				 new Thread(new Runnable() {
	//					
	//					public void run() {
	//						 Operaton operaton=new Operaton();
	//						 String result= operaton.checkusername("Check", str);
	//						 Message message=new Message();
	//						 message.obj=result;
	//						 handler.sendMessage(message);
	//					}
	//				}).start();
	//				 
	//			   
	//			  }
	//		}
	//		public void beforeTextChanged(CharSequence s, int start, int count,
	//				int after) {
	//			
	//		}
	//
	//		public void onTextChanged(CharSequence s, int start, int before,
	//				int count) {
	//		
	//		}
	//		
	//	}
	//	Handler handler=new Handler()
	//	{
	//		@Override
	//		public void handleMessage(Message msg) {
	//			String msgobj=msg.obj.toString();
	//			if (msgobj=="该用户名可用") 
	//			{
	//				etusername.setFocusable(false);
	//			}
	//			else 
	//			{
	//				etusername.requestFocus();
	//				etusername.setError(msgobj);
	//			}	    
	//			super.handleMessage(msg);
	//		}	
	//	};
	//	
	private class EtusernameOnFocusChange implements OnFocusChangeListener
	{
		public void onFocusChange(View v, boolean hasFocus) {
			if (!etusername.hasFocus()) {
				str=etusername.getText().toString().trim();
				if (str==null||str.length()<=0) 
				{
					etusername.setError("用户名不能为空");
				}
				else 
				{	
					new Thread(new Runnable() {
                 //如果用户名不为空，那么将用户名提交到服务器上进行验证，看用户名是否存在，就像JavaEE中利用
				//ajax一样，虽然你看不到但是它却偷偷摸摸做了很多
						public void run() {
							Operaton operaton=new Operaton();
							String result= operaton.checkusername("Check", str);
							System.out.println("result:"+result);
							Message message=new Message();
							message.obj=result;
							handler.sendMessage(message);
						}
					}).start();
				}
			}
		}
	}
	Handler handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			String msgobj=msg.obj.toString();
			System.out.println(msgobj);
			System.out.println(msgobj.length());

			if (msgobj.equals("t")) {  
				etusername.requestFocus();
				etusername.setError("用户名"+str+"已存在");
			}	
			else 
			{						
				etpassword.requestFocus();
			}
			super.handleMessage(msg);
		}	
	};
	private class SelectOnclick implements OnClickListener
	{
		public void onClick(View v) {

			Intent intent = new Intent();
			intent.putExtra("explorer_title",
					getString(R.string.dialog_read_from_dir));
			intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");
			intent.setClass(Register.this, ExDialog.class);
			startActivityForResult(intent, REQUEST_EX);
		}
	}
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode == RESULT_OK) {

			Uri uri = intent.getData();
			filepath=uri.toString().substring(6);
			System.out.println(filepath);
			//用户的头像是不是图片格式
			if(filepath.endsWith("jpg")||filepath.endsWith("png"))
			{
				File file=new File(filepath);
				try {
					InputStream inputStream=new FileInputStream(file);
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					imgphoto.setImageBitmap(bitmap);//如果是就将图片显示出来
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				submit.setClickable(true);
			}
			else
			{
				submit.setClickable(false);
				alert();
			}

		}
	}

	private class SubmitOnclick implements OnClickListener
	{
		public void onClick(View v) {
			username=etusername.getText().toString().trim();
			password=etpassword.getText().toString().trim();

			if (ckman.isChecked()) {
				sex="男";
			}
			else {
				sex="女";
			}
			age=etage.getText().toString().trim();
			if (age==null||age.length()<=0) 
			{  
				etage.requestFocus();
				etage.setError("年龄不能为空");			
				return ;
			}

			dialog.show();
			new Thread(new Runnable() {

				public void run() {

					Operaton operaton=new Operaton();
					File file=new File(filepath);
					String photo=operaton.uploadFile(file, "ImgReciver");
					//先进行图片上传的操作，然后服务器返回图片保存在服务器的路径，
					System.out.println("photo---->"+photo);
					System.out.println("sex:------>"+sex);
					User user=new User(username, password, sex, age,photo);
					//构造一个user对象
					List<User> list=new ArrayList<User>();
					list.add(user);
					WriteJson writeJson=new WriteJson();
					//将user对象写出json形式字符串
					jsonString= writeJson.getJsonData(list);
                    System.out.println(jsonString); 
					String result= operaton.UpData("Register", jsonString);
					Message msg=new Message();
					System.out.println("result---->"+result);
					msg.obj=result;
					handler1.sendMessage(msg);
				}
			}).start();

		}
	}
	private void alert()
	{
		Dialog dialog = new AlertDialog.Builder(this)
		.setTitle("提示")
		.setMessage("您选择的不是有效的图片")
		.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int which) {
				filepath = null;
			}
		})
		.create();
		dialog.show();
	}
	Handler handler1=new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			dialog.dismiss();
			String msgobj=msg.obj.toString();
			if(msgobj.equals("t"))
			{
				Toast.makeText(Register.this, "注册成功", 0).show();
				Intent intent=new Intent();
				intent.setClass(Register.this, LoginRegisterActivity.class);
				startActivity(intent);
			}
			else {
				Toast.makeText(Register.this, "注册失败", 0).show();
			}
			super.handleMessage(msg);
		}	
	};
}
