package goblins.emoticon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        byte buff[] = intent.getByteArrayExtra("bitmap");
        Bitmap bm_full=BitmapFactory.decodeByteArray(buff, 0, buff.length);//重新编码出Bitmap对象
        final ImageView img_full=(ImageView)findViewById(R.id.img_full);
        img_full.setImageBitmap(bm_full);

        img_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(ImageActivity.this,MainActivity.class);
                ImageActivity.this.startActivity(intent);*/
                finish();
            }
        });

    }
}
