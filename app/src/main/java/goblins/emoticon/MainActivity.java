package goblins.emoticon;

import android.content.Intent;
import java.io.ByteArrayOutputStream;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.TextView;
import java.util.HashSet;
import java.util.Random;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static int CAMERA_REQUEST_CODE=1;
    private static int EMOJI_REQUEST_CODE=2;
    Bitmap bm_camera=BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/lena.jpg"));
    Bitmap bm_emoji=BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/psyduck.jpg"));
   /* private Uri convertUri(Uri uri)
    {
        InputStream is = null;
        try{
            is=getContentResolver().openInputStream(uri);
            Bitmap bitmap= BitmapFactory.decodeStream(is);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

   private byte[] Bitmap2Bytes(Bitmap bm){
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
       return baos.toByteArray();
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView img_emoji = (ImageView) findViewById(R.id.img_emoji);
        img_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm_emoji = ((BitmapDrawable)img_emoji.getDrawable()).getBitmap();
                byte buff[];
                buff = Bitmap2Bytes(bm_emoji);
                Intent intent = new Intent(MainActivity.this,ImageActivity.class);
                intent.putExtra("bitmap",buff);
                startActivity(intent);
            }
        });

        final ImageView img_camera = (ImageView) findViewById(R.id.img_camera);
        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm_camera = ((BitmapDrawable)img_camera.getDrawable()).getBitmap();
                byte buff[];
                buff = Bitmap2Bytes(bm_camera);
                Intent intent = new Intent(MainActivity.this,ImageActivity.class);
                intent.putExtra("bitmap",buff);
                startActivity(intent);
            }
        });

        Button btn_score=(Button) findViewById(R.id.btn_score);
        btn_score.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Random random=new Random();
                String tempstring;
                int temprandom;
                temprandom=random.nextInt(100);
                tempstring=String.valueOf(Math.round(temprandom+0.5*(85-temprandom)));
                TextView txt_score=(TextView)findViewById(R.id.txt_score);
                txt_score.setText(tempstring);

            }
        });

        Button btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        Button btn_emoji = (Button) findViewById(R.id.btn_emoji);
        btn_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, EMOJI_REQUEST_CODE);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CAMERA_REQUEST_CODE)
        {
            if(data==null){return;}
            else
            {
                Bundle extras=data.getExtras();
                if(extras !=null)
                {
                    bm_camera = extras.getParcelable("data");
                    ImageView imageView=(ImageView)findViewById(R.id.img_camera);
                    imageView.setImageBitmap(bm_camera);
                }
            }
        }
        if (requestCode==EMOJI_REQUEST_CODE)
        {
            if (data==null)
            {
                return;
            }
            else
            {
                Uri uri;
                uri=data.getData();
                bm_emoji = null;
                if (uri != null) {
                    try {
                        bm_emoji = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ImageView imageView=(ImageView)findViewById(R.id.img_emoji);
                        imageView.setImageBitmap(bm_emoji);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    // Example of a call to a native method


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
