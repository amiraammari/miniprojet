package com.example.amira.rechercheresterant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Amira on 06/08/2017.
 */

public class ExpandaleListeAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> list;
    private HashMap<String,List<ItemsFood>> listHashMap;



    public ExpandaleListeAdapter(Context context, List<String> list, HashMap<String, List<ItemsFood>> listHashMap) {
        this.context = context;
        this.list = list;
        this.listHashMap = listHashMap;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listHashMap.get(this.list.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ItemsFood s= (ItemsFood) getChild(groupPosition, childPosition);

        LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.listitems, null);
        TextView textView = (TextView) convertView.findViewById(R.id.cheld);
        textView.setText(s.nom);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listHashMap.get(this.list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.list.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.listfood, null);

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.food);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
