package com.appman.appmanager.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appman.appmanager.R;
import com.appman.appmanager.activities.ActivityFileManager;
import com.appman.appmanager.activities.MainActivity;
import com.appman.appmanager.recycler.RecyclerAdapter;
import com.appman.appmanager.recycler.RecyclerOnItemClickListener;
import com.appman.appmanager.recycler.RecyclerOnSelectionListener;
import com.appman.appmanager.utils.FileUtils;
import com.appman.appmanager.utils.PreferenceUtils;
import com.appman.appmanager.views.InputDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rudhraksh.pahade on 29-04-2016.
 */
public class FragmentInternalStorage extends Fragment {


    View mainView;
    RecyclerAdapter recyclerAdapter;
    private File currentDirectory;

    private String name;
    private String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mainView = inflater.inflate(R.layout.layout_fragment_internal_storage, container, false);

        initRecyclerView();
        loadIntoRecyclerView();
        setHasOptionsMenu(true);

        return mainView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                actionDelete();
                return true;

            case R.id.action_rename:
                actionRename();
                return true;

            case R.id.action_search:
                actionSearch();
                return true;

            case R.id.action_copy:
                actionCopy();
                return true;

            case R.id.action_move:
                actionMove();
                return true;

            case R.id.action_send:
                actionSend();
                return true;

            case R.id.action_sort:
                actionSort();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(recyclerAdapter != null) {
            int count = recyclerAdapter.getSelectedItemCount();

            menu.findItem(R.id.action_delete).setVisible(count >= 1);

            menu.findItem(R.id.action_rename).setVisible(count >= 1);

            menu.findItem(R.id.action_search).setVisible(count == 0);

            menu.findItem(R.id.action_copy).setVisible(count >= 1 && name == null && type == null);

            menu.findItem(R.id.action_move).setVisible(count >= 1 && name == null && type == null);

            menu.findItem(R.id.action_send).setVisible(count >= 1);

            menu.findItem(R.id.action_sort).setVisible(count == 0);
        }

    }

    private void actionDelete() {
        actionDelete(recyclerAdapter.getSelectedItems());

        recyclerAdapter.clearSelection();
    }

    private void actionDelete(final List<File> files) {
        final File sourceDirectory = currentDirectory;

        recyclerAdapter.removeAll(files);

        String message = String.format("%s files deleted",files.size());

        View.OnClickListener onClickListener=new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(currentDirectory == null || currentDirectory.equals(sourceDirectory)) {
                    recyclerAdapter.addAll(files);
                }
            }
        };
        new MaterialDialog.Builder(getActivity())
                .title("Are you sure you want to delete this file  ?")
                .positiveText("YES")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        try {
                            for(File file : files) FileUtils.deleteFile(file);
                        }
                        catch(Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).negativeText("Cancel")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        /*Snackbar.Callback callback=new Snackbar.Callback()
        {



            @Override
            public void onDismissed(Snackbar snackbar,int event)
            {
                if(event!=DISMISS_EVENT_ACTION)
                {
                    try
                    {
                        for(File file : files) FileUtils.deleteFile(file);
                    }
                    catch(Exception e)
                    {
                        showMessage(e);
                    }
                }

                super.onDismissed(snackbar,event);
            }
        };

        Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_LONG)
                .setAction("Undo",onClickListener)
                .setCallback(callback)
                .show();*/
    }

    private void actionRename() {
        final List<File> selectedItems = recyclerAdapter.getSelectedItems();

        InputDialog inputDialog = new InputDialog(getActivity(),"Rename","Rename") {
            @Override
            public void onActionClick(String text) {
                recyclerAdapter.clearSelection();

                try {
                    if(selectedItems.size() == 1) {
                        File file = selectedItems.get(0);

                        int index = recyclerAdapter.indexOf(file);

                        recyclerAdapter.updateItemAt(index,FileUtils.renameFile(file,text));
                    }
                    else {
                        int size=String.valueOf(selectedItems.size()).length();

                        String format =" (%0"+size+"d)";

                        for(int i = 0;i < selectedItems.size();i++) {
                            File file = selectedItems.get(i);

                            int index = recyclerAdapter.indexOf(file);

                            File newFile = FileUtils.renameFile(file,text+String.format(format,i+1));

                            recyclerAdapter.updateItemAt(index,newFile);
                        }
                    }
                }
                catch(Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        if(selectedItems.size() == 1) {
            inputDialog.setDefault(FileUtils.removeExtension(selectedItems.get(0).getName()));
        }

        inputDialog.show();
    }

    private void actionSearch() {
        InputDialog inputDialog = new InputDialog(getActivity(),"Search","Search") {
            @Override
            public void onActionClick(String text)
            {
                setName(text);
            }
        };

        inputDialog.show();
    }

    private void actionCopy() {
        List<File> selectedItems = recyclerAdapter.getSelectedItems();

        recyclerAdapter.clearSelection();

        transferFiles(selectedItems,false);
    }

    private void actionMove() {
        List<File> selectedItems = recyclerAdapter.getSelectedItems();

        recyclerAdapter.clearSelection();

        transferFiles(selectedItems,true);
    }

    private void actionSend() {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);

        intent.setType("*/*");

        ArrayList<Uri> uris = new ArrayList<>();

        for(File file : recyclerAdapter.getSelectedItems()) {
            if(file.isFile()) uris.add(Uri.fromFile(file));
        }

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uris);

        startActivity(intent);
    }

    private void actionSort() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        int checkedItem= PreferenceUtils.getInteger(getActivity(),"pref_sort",0);

        String sorting[]={"Name","Last modified","Size (high to low)"};

        final Context context = getActivity();

        builder.setSingleChoiceItems(sorting,checkedItem,new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {
                recyclerAdapter.update(which);

                PreferenceUtils.putInt(context,"pref_sort",which);

                dialog.dismiss();

            }
        });

        builder.setTitle("Sort by");

        builder.show();
    }

    private void transferFiles(final List<File> files,final Boolean delete) {
        String paste = delete ? "moved" : "copied";

        String message = String.format("%d items waiting to be %s",files.size(),paste);

        View.OnClickListener onClickListener=new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    for(File file : files)
                    {
                        recyclerAdapter.addAll(FileUtils.copyFile(file,currentDirectory));

                        if(delete) FileUtils.deleteFile(file);
                    }
                }
                catch(Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        /*Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_INDEFINITE)
                .setAction("Paste",onClickListener)
                .show();*/
    }

    private void initRecyclerView() {
        recyclerAdapter = new RecyclerAdapter(getActivity());
        recyclerAdapter.setOnItemClickListener(new OnItemClickListener(getActivity()));
    }
    private void loadIntoRecyclerView() {
        String permission= Manifest.permission.WRITE_EXTERNAL_STORAGE;

        if(PackageManager.PERMISSION_GRANTED!= ContextCompat.checkSelfPermission(getActivity(),permission)) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{permission},0);

            return;
        }

        final Context context = getActivity();

        if(name!=null) {
            recyclerAdapter.addAll(FileUtils.searchFilesName(context,name));

            return;
        }

        if(type!=null) {
            switch(type)
            {
                case "audio":
                    recyclerAdapter.addAll(FileUtils.getAudioLibrary(context));
                    break;

                case "image":
                    recyclerAdapter.addAll(FileUtils.getImageLibrary(context));
                    break;

                case "video":
                    recyclerAdapter.addAll(FileUtils.getVideoLibrary(context));
                    break;
            }

            return;
        }
        setPath(FileUtils.getInternalStorage());
    }

    private void setPath(File directory) {
        if(!directory.exists()) {
            Toast.makeText(getActivity(),"Directory doesn't exist",Toast.LENGTH_SHORT).show();

            return;
        }

        currentDirectory = directory;

        recyclerAdapter.clear();

//        recyclerAdapter.clearSelection();

        recyclerAdapter.addAll(FileUtils.getChildren(directory));

        invalidateTitle();
    }

    private void setName(String name) {
        Intent intent = new Intent(getActivity(), MainActivity.class);

        intent.putExtra(ActivityFileManager.EXTRA_NAME,name);

        startActivity(intent);
    }

    private void setType(String type) {
        Intent intent = new Intent(getActivity() ,MainActivity.class);

        intent.putExtra(ActivityFileManager.EXTRA_TYPE,type);

        if(Build.VERSION.SDK_INT>=21) {
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        }

        startActivity(intent);
    }

    private void invalidateTitle() {
        if(recyclerAdapter.anySelected()) {
            int selectedItemCount = recyclerAdapter.getSelectedItemCount();

            ActivityFileManager.textViewCurrentDirectory.setText(String.format("%s selected",selectedItemCount));
        }
        else if(name != null) {
            ActivityFileManager.textViewCurrentDirectory.setText(String.format("Search for %s",name));
        }
        else if(type != null) {
            switch(type) {
                case "image":
                    ActivityFileManager.textViewCurrentDirectory.setText("Images");
                    break;

                case "audio":
                    ActivityFileManager.textViewCurrentDirectory.setText("Music");
                    break;

                case "video":
                    ActivityFileManager.textViewCurrentDirectory.setText("Videos");
                    break;
            }
        }
        else if(currentDirectory != null && !currentDirectory.equals(FileUtils.getInternalStorage())) {
            ActivityFileManager.textViewCurrentDirectory.setText(FileUtils.getName(currentDirectory));
        }
        else {
            ActivityFileManager.textViewCurrentDirectory.setText(getResources().getString(R.string.app_name));
        }
    }

    private void invalidateToolbar() {
        if(recyclerAdapter.anySelected()) {
            ActivityFileManager.toolbar.setNavigationIcon(R.drawable.ic_clear);

            ActivityFileManager.toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    recyclerAdapter.clearSelection();
                }
            });
        }
        else if(name==null && type==null) {
            ActivityFileManager.toolbar.setNavigationIcon(R.drawable.ic_menu);

            ActivityFileManager.toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //drawerLayout.openDrawer(navigationView);
                }
            });
        }
        else {
            ActivityFileManager.toolbar.setNavigationIcon(R.drawable.ic_back);

            ActivityFileManager.toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    getActivity().finish();
                }
            });
        }
    }

    private final class OnItemClickListener implements RecyclerOnItemClickListener{

        private final Context context;

        private OnItemClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onItemClick(int position) {
            final File file = recyclerAdapter.get(position);
            if (recyclerAdapter.anySelected()){
                recyclerAdapter.toggle(position);
                return;
            }
            if (file.isDirectory()){
                if (file.canRead()){
                    setPath(file);
                }else {
                    Toast.makeText(getActivity(), "Cannot open directory", Toast.LENGTH_SHORT).show();
                }
            }else {
                if (Intent.ACTION_GET_CONTENT.equals(getActivity().getIntent().getAction())){
                    Intent intent = new Intent();
                    intent.setDataAndType(Uri.fromFile(file), FileUtils.getMimeType(file));

                    getActivity().setResult(Activity.RESULT_OK,intent);

                    getActivity().finish();
                }else if(FileUtils.FileType.getFileType(file )== FileUtils.FileType.ZIP) {
                    final ProgressDialog dialog=ProgressDialog.show(context,"","Unzipping",true);

                    Thread thread=new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try {
                                setPath(FileUtils.unzip(file));

                                getActivity().runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        dialog.dismiss();
                                    }
                                });
                            }
                            catch(Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    thread.run();
                }else {
                    try {
                        Intent intent=new Intent(Intent.ACTION_VIEW);

                        intent.setDataAndType(Uri.fromFile(file), FileUtils.getMimeType(file));

                        startActivity(intent);
                    }
                    catch(Exception e) {
                        Toast.makeText(getActivity(),String.format("Cannot open %s", FileUtils.getName(file)), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }

        @Override
        public boolean onItemLongClick(int position) {
            recyclerAdapter.toggle(position);
            return true;
        }
    }

    private final class OnSelectionListener implements RecyclerOnSelectionListener {
        @Override
        public void onSelectionChanged() {
            getActivity().invalidateOptionsMenu();
            invalidateTitle();

            invalidateToolbar();
        }
    }
}
