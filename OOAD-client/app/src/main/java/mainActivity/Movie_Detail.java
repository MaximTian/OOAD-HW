package mainActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.maximtian.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MaximTian on 2016/5/31.
 */
public class Movie_Detail extends ListActivity implements View.OnClickListener {

    private ListView remark_List; // ����

    private ImageView imageView; // ��Ӱ����
    private TextView title; // ��Ӱ����
    private TextView rank; // ��Ӱ����
    private TextView movie_source; // ��Ӱ��Դ
    private TextView movie_time; // ��Ӱ��ӳʱ��

    private TextView movie_brevity; // ��Ӱ���

    private Button button;

    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 4;// Ĭ��չʾ�������3��
    private static final int SHOW_CONTENT_NONE_STATE = 0;// ����
    private static final int SHRINK_UP_STATE = 1;// ����״̬
    private static final int SPREAD_STATE = 2;// չ��״̬
    private static int mState = SHRINK_UP_STATE;//Ĭ������״̬

    private TextView mContentText;// չʾ�ı�����
    private RelativeLayout mShowMore;// չʾ����
    private ImageView mImageSpread;// չ��
    private ImageView mImageShrinkUp;// ����

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        initView();
        init_remarks();
        remark_List.setFocusable(false); // ���ý��㣬��ҳ�涥��
       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.movie_detail_image);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.zootopia));

        title = (TextView) findViewById(R.id.movie_detail_title);
        title.setText("������");

        rank = (TextView) findViewById(R.id.movie_detail_rank);
        rank.setText("9.0");

        movie_source = (TextView) findViewById(R.id.movie_detail_source);
        movie_source.setText("���� | 97����");

        movie_time = (TextView) findViewById(R.id.movie_detail_time);
        movie_time.setText("2016-01-01 ��ӳ");

//        movie_brevity = (TextView) findViewById(R.id.movie_detail_brevity);
//        movie_brevity.setText("һ���ִ����Ķ��ﶼ�У�ÿ�ֶ��������ﶼ���Լ��ľ�������ɳĮ������������㳡�������Ϻ��ı�����ȵȣ�������һ������¯���������������ƽ�������������Ǵ�����С����ֻҪŬ�������ܴ���һ�����á�������ϴ�С�������ܳ�Ϊ������еľ��죬������ߵ������˶��������Ӳ����ܵ��Ͼ��죬��������ͨ���Լ���Ŭ����������ȫ�Ǵ��ͷ����Ǿ���֣���Ϊ�˵�һ�����Ӿ��١�Ϊ��֤���Լ�������������һ׮���ذ�����׷Ѱ�����·�ϣ������ʹ�ڶ�������Կ��ɹ�ƭΪ���ĺ�����˰����Լ���ȴ������׮��������������һ�������߸�����ǵľ޴���ı�����ǲ��ò����ֺ�����ȥ���Խҿ���������޴���ı�������");

        mContentText = (TextView) findViewById(R.id.movie_content);
        mContentText.setText("        һ���ִ����Ķ��ﶼ�У�ÿ�ֶ��������ﶼ���Լ��ľ�������ɳĮ������������㳡�������Ϻ��ı�����ȵȣ�������һ������¯���������������ƽ�������������Ǵ�����С����ֻҪŬ�������ܴ���һ�����á�������ϴ�С�������ܳ�Ϊ������еľ��죬������ߵ������˶��������Ӳ����ܵ��Ͼ��죬��������ͨ���Լ���Ŭ����������ȫ�Ǵ��ͷ����Ǿ���֣���Ϊ�˵�һ�����Ӿ��١�Ϊ��֤���Լ�������������һ׮���ذ�����׷Ѱ�����·�ϣ������ʹ�ڶ�������Կ��ɹ�ƭΪ���ĺ�����˰����Լ���ȴ������׮��������������һ�������߸�����ǵľ޴���ı�����ǲ��ò����ֺ�����ȥ���Խҿ���������޴���ı�������");
        mShowMore = (RelativeLayout) findViewById(R.id.show_more);
        mImageSpread = (ImageView) findViewById(R.id.spread);
        mImageShrinkUp = (ImageView) findViewById(R.id.shrink_up);
        mShowMore.setOnClickListener(Movie_Detail.this);

        button = (Button) findViewById(R.id.ticket_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_more: {
                if (mState == SPREAD_STATE) {
                    mContentText.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    mContentText.requestLayout();
                    mImageShrinkUp.setVisibility(View.GONE);
                    mImageSpread.setVisibility(View.VISIBLE);
                    mState = SHRINK_UP_STATE;
                } else if (mState == SHRINK_UP_STATE) {
                    mContentText.setMaxLines(Integer.MAX_VALUE);
                    mContentText.requestLayout();
                    mImageShrinkUp.setVisibility(View.VISIBLE);
                    mImageSpread.setVisibility(View.GONE);
                    mState = SPREAD_STATE;
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    private void init_remarks() {
        remark_List = getListView();
        SimpleAdapter adapter = new SimpleAdapter(this, get_movie_Data(),
                R.layout.movie_item, new String[]{"img", "title", "info", "time"},
                new int[]{R.id.movie_image, R.id.movie_title, R.id.movie_info, R.id.movie_time});
        setListAdapter(adapter);

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, remark_List);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = remark_List.getLayoutParams();
        params.height = totalHeight + (remark_List.getDividerHeight() * (remark_List.getCount() - 1));
        ((ViewGroup.MarginLayoutParams)params).setMargins(10, 10, 10, 10);
        remark_List.setLayoutParams(params);
    }

    private List<Map<String, Object>> get_movie_Data() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("img", R.mipmap.ic_launcher);
        map.put("title", "������");
        map.put("info", "This is an apple");
        map.put("time", "2016-01-01 ��ӳ");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.mipmap.ic_launcher);
        map.put("title", "��ŭ��С��");
        map.put("info", "This is a banana");
        map.put("time", "2016-02-01 ��ӳ");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.mipmap.ic_launcher);
        map.put("title", "���ԭʼ��");
        map.put("info", "This is a cat");
        map.put("time", "2016-03-01 ��ӳ");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.mipmap.ic_launcher);
        map.put("title", "ֲ���ս��ʬ");
        map.put("info", "This is a dog, This is a dog,This is a dog,This is a dog,This is a dog");
        map.put("time", "2016-04-01 ��ӳ");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("img", R.mipmap.ic_launcher);
        map.put("title", "����ϵ��й�");
        map.put("info", "This is an eagle, This is an eagle");
        map.put("time", "2016-05-01 ��ӳ");
        list.add(map);

        return list;
    }

}
