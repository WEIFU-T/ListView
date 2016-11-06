package cn.edu.gdmec.s07150836.listview;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv= (ListView) findViewById(R.id.ListView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0, "ArrayAdapter");
        menu.add(0,2,0, "SimpleCursorAdapter");
        menu.add(0,3,0, "SimpleAdapter");
        menu.add(0,4,0, "BaseAdapter");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                arrayAdapter();
                break;
            case 2:
                simpleCursorAdapter();
                break;
            case 3:
                simpleAdapter();
                break;
            case 4:
                baseAdapter();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void arrayAdapter(){
        final String[] weeks={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,weeks);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,weeks[i],Toast.LENGTH_LONG).show();
            }
        });
    }

    private void simpleCursorAdapter(){
        final Cursor cursor=getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        startManagingCursor(cursor);
        SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_1,cursor,new String[]{ContactsContract.Contacts.DISPLAY_NAME},new int[]{android.R.id.text1},0);
        lv.setAdapter(simpleCursorAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void simpleAdapter(){
        final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("title","G1");
        map.put("info","google 1");
        map.put("img",R.drawable.icon1);
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("title","G2");
        map.put("info","google 2");
        map.put("img",R.drawable.icon2);
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("title","G3");
        map.put("info","google 3");
        map.put("img",R.drawable.icon3);
        list.add(map);

        SimpleAdapter sim=new SimpleAdapter(this,list,R.layout.simpleadapter_demo,new String[]{"img","title","info"},new int[]{R.id.imageView,R.id.titleView,R.id.infoView});
        lv.setAdapter(sim);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,list.get(i).get("title").toString(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    static class ViewHolder{
        public ImageView img;
        public TextView title;
        public TextView info;
        public Button but;
        public LinearLayout layout;
    }

    private void baseAdapter(){
        class MyBaseAdapter extends BaseAdapter{
            private List<Map<String,Object>> data;
            private Context context;
            private LayoutInflater myLayoutInflater;

            public MyBaseAdapter( Context context,List<Map<String, Object>> data) {
                super();
                this.data = data;
                this.context = context;
                this.myLayoutInflater=LayoutInflater.from(context);
            }


            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int i) {
                return data.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(final int i, View view, ViewGroup viewGroup) {
                ViewHolder holder=null;
                if(view==null){
                    holder=new ViewHolder();
                    view=myLayoutInflater.inflate(R.layout.l1,viewGroup,false);
                    holder.img= (ImageView) view.findViewById(R.id.img);
                    holder.title= (TextView) view.findViewById(R.id.title);
                    holder.info= (TextView) view.findViewById(R.id.info);
                    holder.but= (Button) view.findViewById(R.id.but);
                    holder.layout= (LinearLayout) view.findViewById(R.id.l1);
                    view.setTag(holder);
                }else{
                    holder= (ViewHolder) view.getTag();
                }

                holder.img.setImageResource(Integer.parseInt(data.get(i).get("img").toString()));
                holder.title.setText(data.get(i).get("title").toString());
                holder.info.setText(data.get(i).get("info").toString());

                if(i%2==1){
                    holder.layout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
                }else{
                    holder.layout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
                }

                holder.but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"按钮点击"+i,Toast.LENGTH_SHORT).show();
                    }
                });
                return view;
            }
        }

        final List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("title","G1");
        map.put("info","google 1");
        map.put("img",R.drawable.icon1);
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("title","G2");
        map.put("info","google 2");
        map.put("img",R.drawable.icon2);
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("title","g3");
        map.put("info","google 3");
        map.put("img",R.drawable.icon3);
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("title","g4");
        map.put("info","google 4");
        map.put("img",R.drawable.icon4);
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("title","g5");
        map.put("info","google 5");
        map.put("img",R.drawable.icon5);
        list.add(map);

        MyBaseAdapter myb=new MyBaseAdapter(this,list);
        lv.setAdapter(myb);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,list.get(i).get("title").toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
