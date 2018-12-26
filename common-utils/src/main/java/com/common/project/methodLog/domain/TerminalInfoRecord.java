//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.project.methodLog.domain;

import com.common.project.methodLog.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class TerminalInfoRecord implements Serializable {
    private static final long serialVersionUID = -8348539664620751994L;
    private long userId;
    private String hsman;
    private String hstype;
    private String osVer;
    private int screenWidth;
    private int screenHeight;
    private int ramSize;
    private String imsi;
    private String imei;
    private String smsCenter;
    private int lac;
    private String ip;
    private int networkType;
    private String channelId;
    private String appId;
    private String appVersion;
    private String provider;
    private String currentDate;

    public TerminalInfoRecord() {
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHsman() {
        return this.hsman;
    }

    public void setHsman(String hsman) {
        this.hsman = hsman;
    }

    public String getHstype() {
        return this.hstype;
    }

    public void setHstype(String hstype) {
        this.hstype = hstype;
    }

    public String getOsVer() {
        return this.osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getRamSize() {
        return this.ramSize;
    }

    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }

    public String getImsi() {
        return this.imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSmsCenter() {
        return this.smsCenter;
    }

    public void setSmsCenter(String smsCenter) {
        this.smsCenter = smsCenter;
    }

    public int getLac() {
        return this.lac;
    }

    public void setLac(int lac) {
        this.lac = lac;
    }

    public String getCurrentDate() {
        if (this.currentDate == null) {
            this.currentDate = DateUtils.formatDate(new Date(), "yyyyMMdd");
        }

        return this.currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getNetworkType() {
        return this.networkType;
    }

    public void setNetworkType(int networkType) {
        this.networkType = networkType;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String toInsertSql() {
        StringBuffer builder = new StringBuffer("INSERT INTO cap_terminal_info_" + this.getCurrentDate() + " (user_id,hsman,hstype,imsi,imei,osVer,screen_width,screen_height,ram_size,sms_center,lac,ip,network_type,channel_id,app_id,app_version, provider, create_date) VALUE (");
        builder.append(this.userId).append(",");
        builder.append(this.hsman).append(",");
        builder.append(this.hstype).append(",");
        builder.append(this.imsi).append(",");
        builder.append(this.imei).append(",");
        builder.append(this.osVer).append(",");
        builder.append(this.screenWidth).append(",");
        builder.append(this.screenHeight).append(",");
        builder.append(this.ramSize).append(",");
        builder.append(this.smsCenter).append(",");
        builder.append(this.lac).append(",");
        builder.append(this.ip).append(",");
        builder.append(this.networkType).append(",");
        builder.append(this.channelId).append(",");
        builder.append(this.appId).append(",");
        builder.append(this.appVersion).append(",");
        builder.append(this.provider).append(",");
        builder.append(new Date()).append(");");
        return builder.toString();
    }
}
