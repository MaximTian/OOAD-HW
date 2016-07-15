package mainActivity;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.example.maximtian.myapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import Database.DBManager;

/**
 * Created by MaximTian on 2016/5/22.
 */
public class Movie_Activity extends ListActivity {

    private ListView movie_ListView;

    private ViewPager mViewPager;  // ͼƬ������
    private List<ImageView> imageViewList; // ������ͼƬ����

    private int[] imageResId; // ͼƬID
    private List<View> dots; // ͼƬ����Բ��
    private int currentItem = 0; // ͼƬ��������

    private ScheduledExecutorService scheduledExecutorService;

    private SimpleAdapter adapter; // ���ݶ�

    private static final String[] m_Cities = {"����", "����", "��ݸ", "��ɽ", "��ɽ", "����"};

    private Spinner m_Spinner;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_layout);
        dbManager = new DBManager(this);

        init_TopView();
        init_movie_info();
        init_Spinner();
    }

    public void init_Spinner() {
        m_Spinner = (Spinner)findViewById(R.id.location_Spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_Cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        m_Spinner.setAdapter(adapter);
        //ѡ����е������˵�
        m_Spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //ѡ��ͬ�ĳ��к��������������
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    @Override
    protected void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // ��Activity��ʾ������ÿ�������л�һ��ͼƬ��ʾ
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
        super.onStart();
    }

    private class ScrollTask implements Runnable {
        public void run() {
            synchronized (mViewPager) {
                currentItem = (currentItem + 1) % imageViewList.size();
                handler.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
            }
        }
    }

    @Override
    protected void onStop() {
        // ��Activity���ɼ���ʱ��ֹͣ�л�
        scheduledExecutorService.shutdown();
        super.onStop();
    }

    private void init_movie_info() {
        movie_ListView = getListView();
        adapter = new SimpleAdapter(this, get_movie_Data(),
                R.layout.movie_item, new String[]{"img", "title", "info", "time"},
                new int[]{R.id.movie_image, R.id.movie_title, R.id.movie_info, R.id.movie_time});
        setListAdapter(adapter);

        movie_ListView.setOnItemClickListener(new MyItemClickListener());
    }

    private List<Map<String, Object>> get_movie_Data() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        try {
/*            File file = new File("/sdcard/test.txt");
            if (file.exists()) {
                Toast.makeText(Movie_Activity.this, "HHHHHHH", Toast.LENGTH_SHORT).show();
            } */
            /*
            InputStream inputStream = getResources().openRawResource("test");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\t");
                map = new HashMap<String, Object>();
                map.put("img", R.mipmap.ic_launcher);
                map.put("title", tokens[1]);
                map.put("time", tokens[2]);
                map.put("info", tokens[5]);
                list.add(map);
            }
            isr.close();
            */
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPager.setCurrentItem(currentItem);// �л���ǰ��ʾ��ͼƬ
        };
    };

    public void init_TopView() {
        get_image_Data();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        LinearLayout.LayoutParams cParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                getScreenHeight(Movie_Activity.this) * 3 / 10);  // ����ͼƬռ��Ļ1/3
//        mViewPager.setLayoutParams(cParams);

        mViewPager.setAdapter(new MyAdapter());// �������ViewPagerҳ���������
        mViewPager.setOnPageChangeListener(new MyPageChangeListener()); // ����һ������������ViewPager�е�ҳ��ı�ʱ����
    }

    private void get_image_Data() {
        imageViewList = new ArrayList<ImageView>();
        imageResId = getImageResIDs();
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViewList.add(imageView);
        }

        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.v_dot0));
        dots.add(findViewById(R.id.v_dot1));
        dots.add(findViewById(R.id.v_dot2));
        dots.add(findViewById(R.id.v_dot3));
        dots.add(findViewById(R.id.v_dot4));
    }

    private int[] getImageResIDs() {
        return new int[]{
                R.drawable.image01,
                R.drawable.image02,
                R.drawable.image03,
                R.drawable.image04,
                R.drawable.image05
        };
    }

    private class MyPageChangeListener implements OnPageChangeListener {
        private int oldPosition = 0;

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focus);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    public static int getScreenHeight(Context context){
        if (null == context) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imageViewList.get(arg1));
            return imageViewList.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    private class MyItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Map<String, String> map = (Map<String, String>) adapter.getItem(position);
            Intent start_main = new Intent(Movie_Activity.this, Movie_Detail.class);
            startActivity(start_main);
        }
    }
}
