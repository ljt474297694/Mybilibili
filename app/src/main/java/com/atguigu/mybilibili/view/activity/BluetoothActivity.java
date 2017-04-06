package com.atguigu.mybilibili.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.view.base.BaseActivity;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.Bind;
import butterknife.OnClick;

public class BluetoothActivity extends BaseActivity {

    private static final int REQUEST_BLUETOOTH_PERMISSION = 10;
    @Bind(R.id.listview)
    ListView listview;
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> bluetoothDevicesList;
    private ArrayAdapter<String> adapter;

    private final UUID MY_UUID = UUID
            .fromString("abcd1234-ab12-ab12-ab12-abcdef123456");//类似端口号的作用 发送方 接收方必须匹配
    private BluetoothSocket clientSocket;
    private BluetoothDevice device;
    private OutputStream os;//输出流

    /**
     * 定义广播接收器
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    bluetoothDevicesList.add(device.getName() + ":" + device.getAddress());
                    adapter.notifyDataSetChanged();//更新适配器
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //已搜素完成
                Toast.makeText(BluetoothActivity.this, "搜索完成", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public String setUrl() {
        return null;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(String json, String error) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //获取已经配对的蓝牙设备
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        bluetoothDevicesList = new ArrayList<>();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                bluetoothDevicesList.add(device.getName() + ":" + device.getAddress());
            }
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, bluetoothDevicesList);

        listview.setAdapter(adapter);


        //每搜索到一个设备就会发送一个该广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(receiver, filter);
        //当全部搜索完后发送该广播
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(receiver, filter);

        //设置item监听 点击对应的设备 进行关联 关联成功后 发送文本
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = adapter.getItem(position);
                String address = s.substring(s.indexOf(":") + 1).trim();//把地址解析出来
                //主动连接蓝牙服务端
                try {
                    //判断当前是否正在搜索
                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }
                    try {
                        if (device == null) {
                            //获得远程设备
                            device = mBluetoothAdapter.getRemoteDevice(address);
                        }
                        if (clientSocket == null) {
                            //创建客户端蓝牙Socket
                            clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                            //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                            clientSocket.connect();
                            //获得输出流（客户端指向服务端输出文本）
                            os = clientSocket.getOutputStream();
                        }
                    } catch (Exception e) {
                    }
                    if (os != null) {
                        //往服务端写信息
                        os.write("蓝牙信息来了".getBytes("utf-8"));
                        Toast.makeText(BluetoothActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bluetooth;
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    private void startBluetooth() {
        mBluetoothAdapter.enable();
        Toast.makeText(BluetoothActivity.this, "开启成功", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.bt_start, R.id.bt_stop, R.id.bt_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                //如果当前在搜索，就先取消搜索
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }
                //开启搜索
                mBluetoothAdapter.startDiscovery();
                break;
            case R.id.bt_start:
                startBluetooth();
                break;
            case R.id.bt_stop:
                mBluetoothAdapter.disable();
                Toast.makeText(BluetoothActivity.this, "关闭成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver!=null) {
            unregisterReceiver(receiver);
        }
    }
}
