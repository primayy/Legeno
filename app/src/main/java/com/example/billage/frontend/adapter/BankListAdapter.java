package com.example.billage.frontend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.widget.ListView;
import androidx.annotation.NonNull;
import com.example.billage.R;
import com.example.billage.backend.api.data.UserAccount;

import java.util.List;

public class BankListAdapter extends ArrayAdapter<UserAccount> {

    private Context context;
    private List mList;


    class BankListViewHolder {
        public ImageView image;
        public TextView name;
        public TextView account_num;
    }

    public BankListAdapter(Context context, List<UserAccount> list, ListView listview) {
        super(context, 0, list);

        this.context = context;
        this.mList = list;
    }

    // ListView의  한 줄(row)이 렌더링(rendering)될 때 호출되는 메소드로 row를 위한 view를 리턴.
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parentViewGroup) {
        View rowView = convertView;
        BankListViewHolder viewHolder;
        String Status;

        if (rowView == null) {

            // 레이아웃을 정의한 XML 파일(R.layout.list_item)을 읽어서 계층 구조의 뷰 객체(rowView)로 변환.
            // rowView 객체는 3개의 TextView로 구성
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.mypage_current_bank_list, parentViewGroup, false);

            viewHolder = new BankListViewHolder();

            viewHolder.image = (ImageView) rowView.findViewById(R.id.imageView);
            viewHolder.name = (TextView) rowView.findViewById(R.id.bank_icon_name);
            viewHolder.account_num = (TextView) rowView.findViewById(R.id.bank_icon_account_num);
            rowView.setTag(viewHolder);

            Status = "created";
        } else {

            viewHolder = (BankListAdapter.BankListViewHolder) rowView.getTag();
            Status = "reused";
        }

        // 태그 분석을 위한 코드 시작
        String Tag = rowView.getTag().toString();
        int idx = Tag.indexOf("@");
        String tag = Tag.substring(idx + 1);


        UserAccount account = (UserAccount) mList.get(position);
        viewHolder.name.setText(account.getBank_name());
        viewHolder.account_num.setText(account.getAccount_num());

        switch (account.getBank_code()){
            case "002":
                viewHolder.image.setImageResource(R.drawable.kdb_bank);
                break;
            case "003":
                viewHolder.image.setImageResource(R.drawable.ibk_bank);
                break;
            case "004":
                viewHolder.image.setImageResource(R.drawable.kb_bank);
                break;
            case "007"://수협은행
                break;
            case "011":
                viewHolder.image.setImageResource(R.drawable.nh_bank);
                break;
            case "020":
                viewHolder.image.setImageResource(R.drawable.woori_bank);
                break;
            case "023": //SC제일은행
                break;
            case "027":
                viewHolder.image.setImageResource(R.drawable.citi_bank);
                break;
            case "031":
                viewHolder.image.setImageResource(R.drawable.dg_bank);
                break;
            case "032":
                viewHolder.image.setImageResource(R.drawable.dg_bank);
                break;
            case "034":
                viewHolder.image.setImageResource(R.drawable.kj_bank);
                break;
            case "035":
                viewHolder.image.setImageResource(R.drawable.jj_bank);
                break;
            case "081":
                viewHolder.image.setImageResource(R.drawable.keb_bank);
                break;
            case "088":
                viewHolder.image.setImageResource(R.drawable.sh_bank);
                break;
            case "089":
                viewHolder.image.setImageResource(R.drawable.k_bank);
                break;
            case "090":
                viewHolder.image.setImageResource(R.drawable.kakao_bank);
                break;
            case "097"://오픈은행
                viewHolder.image.setImageResource(R.drawable.kakao_bank);
                break;
//                return "오픈은행";
        }

        return rowView;
    }

}