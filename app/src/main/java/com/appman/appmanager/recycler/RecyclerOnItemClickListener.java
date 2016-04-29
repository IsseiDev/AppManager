package com.appman.appmanager.recycler;

public interface RecyclerOnItemClickListener
{
    void onItemClick(int position);

    boolean onItemLongClick(int position);
}