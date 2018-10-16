package com.dcode.wanjin.activity.bet;


import android.text.TextUtils;
import com.dcode.corelibrary.activity.BaseFragment;
import com.dcode.wanjin.util.QXConstant;
import java.util.ArrayList;

public class CreateChoseNumber {

    public enum ModeDingPei {
        DING_QU, DING_CHU, PEI_QU, PEI_CHU
    }

    private static final String NUMBER_DAN = "13579";
    private static final String NUMBER_SHUANG = "02468";

    private boolean mNoRes = false;
    private int mAllLen = 0;  //类型所有组合 总数
    private String[] mAll = new String[10000]; //所有组合保存进此数组
    private int[] mAllDel = new int[10000]; //记录所有组合哪些是需要删除的， 为1则删除
    private BaseFragment mFragment;
    public ArrayList<String> mResList = new ArrayList<>();
    public String mPlayClass = "";
    public int mPlayLen = 0;
    public ModeDingPei mModeDingPei;
    public String mDingQian = "";
    public String mDingBai = "";
    public String mDingShi = "";
    public String mDingGe = "";

    public String mPei1 = "";
    public String mPei2 = "";
    public String mPei3 = "";
    public String mPei4 = "";

    public boolean mHefenQu = false;
    public boolean mHefenChu = false;
    public boolean mHefenCb11 = false;
    public boolean mHefenCb12 = false;
    public boolean mHefenCb13 = false;
    public boolean mHefenCb14 = false;
    public String mHefen1Str = "";
    public boolean mHefenCb21 = false;
    public boolean mHefenCb22 = false;
    public boolean mHefenCb23 = false;
    public boolean mHefenCb24 = false;
    public String mHefen2Str = "";
    public boolean mHefenCb31 = false;
    public boolean mHefenCb32 = false;
    public boolean mHefenCb33 = false;
    public boolean mHefenCb34 = false;
    public String mHefen3Str = "";
    public boolean mHefenCb41 = false;
    public boolean mHefenCb42 = false;
    public boolean mHefenCb43 = false;
    public boolean mHefenCb44 = false;
    public String mHefen4Str = "";

    //不定合分
    public boolean mHeshuLiang = false;
    public boolean mHeshuSan = false;
    public String mHeshuStr = "";
    public String mHeshuMinStr = "";
    public String mHeshuMaxStr = "";

    //全转
    public String mQuanzhuanStr = "";
    public String mShangjiangStr = "";
    public String mPaichuStr = "";
    public boolean mXPos1 = false;
    public boolean mXPos2 = false;
    public boolean mXPos3 = false;
    public boolean mXPos4 = false;

    //含、复式
    public String mFushiStr = "";
    public String mHanStr = "";
    public boolean mHanQu = false;
    public boolean mHanChu = false;

    //双重， 双双重， 三重， 四重
    public boolean mChongShuangQu = false;
    public boolean mChongShuangChu = false;
    public boolean mChongShuangShuangQu = false;
    public boolean mChongShuangShuangChu = false;
    public boolean mChongSanQu = false;
    public boolean mChongSanChu = false;
    public boolean mChongSiQu = false;
    public boolean mChongSiChu = false;

    //兄弟
    public boolean mErXiongdiQu = false;
    public boolean mErXiongdiChu = false;
    public boolean mSanXiongdiQu = false;
    public boolean mSanXiongdiChu = false;
    public boolean mSiXiongdiQu = false;
    public boolean mSiXiongdiChu = false;

    //对数
    public boolean mDuishuQu = false;
    public boolean mDuishuChu = false;
    public String mDuishu1 = "";
    public String mDuishu2 = "";
    public String mDuishu3 = "";

    //单双
    public boolean mDanQu = false;
    public boolean mDanChu = false;
    public boolean mDan1 = false;
    public boolean mDan2 = false;
    public boolean mDan3 = false;
    public boolean mDan4 = false;
    public boolean mShuangQu = false;
    public boolean mShuangChu = false;
    public boolean mShuang1 = false;
    public boolean mShuang2 = false;
    public boolean mShuang3 = false;
    public boolean mShuang4 = false;


    public CreateChoseNumber(BaseFragment fragment) {
        mFragment = fragment;
    }

    public void createNumber() {
        //初始化数据， 加载所有数据
        if(QXConstant.CLASS_2P.equals(mPlayClass)) {
            mAllLen = QXConstant.ARRAY_2P_LEN;
            System.arraycopy(QXConstant.ARRAY_2P, 0, mAll, 0, mAllLen);
        }
        if(QXConstant.CLASS_3P.equals(mPlayClass)) {
            mAllLen = QXConstant.ARRAY_3P_LEN;
            System.arraycopy(QXConstant.ARRAY_3P, 0, mAll, 0, mAllLen);
        }
        if(QXConstant.CLASS_4P.equals(mPlayClass)) {
            mAllLen = QXConstant.ARRAY_4P_LEN;
            System.arraycopy(QXConstant.ARRAY_4P, 0, mAll, 0, mAllLen);
        }
        if(QXConstant.CLASS_2PX.equals(mPlayClass)) {
            mAllLen = QXConstant.ARRAY_2PX_LEN;
            System.arraycopy(QXConstant.ARRAY_2PX, 0, mAll, 0, mAllLen);
        }
        if(QXConstant.CLASS_3PX.equals(mPlayClass)) {
            mAllLen = QXConstant.ARRAY_3PX_LEN;
            System.arraycopy(QXConstant.ARRAY_3PX, 0, mAll, 0, mAllLen);
        }
        if(QXConstant.CLASS_4PX.equals(mPlayClass)) {
            mAllLen = QXConstant.ARRAY_4PX_LEN;
            System.arraycopy(QXConstant.ARRAY_4PX, 0, mAll, 0, mAllLen);
        }

        clearData(); //清除数据

        //处理定、配
        if(mPlayClass.contains("x")) {
            createClassXianNumber();
        }else {
            createClassDingNumber();
        }

        //处理合分
        processHefen();

        //处理不定合分
        processBudingHefen();
        processBudingHefenFanwei();

        //处理全转
        processQuanzhuan();

        //处理含、 复式
        processHanFushi();

        //双重， 双双重， 三重， 四重
        processChongChong();

        //兄弟
        processXiongdi();

        //对数
        processDuishu();

        //单双
        processDanShuang();

        //收集结果
        if(!mNoRes){  //没有这样的结果
            for (int j = 0; j < mAllLen; j++) {
                if(mAllDel[j] != 1) {
                    mResList.add(mAll[j]);
                }
            }
        }

    }

    //生成定位的号码
    private void createClassDingNumber() {
        if(!TextUtils.isEmpty(mDingQian) ||
                !TextUtils.isEmpty(mDingBai) ||
                !TextUtils.isEmpty(mDingShi) ||
                !TextUtils.isEmpty(mDingGe)){
            processDingDingQu();
            processDingDingChu();
        }

        if(!TextUtils.isEmpty(mPei1) ||
                !TextUtils.isEmpty(mPei2) ||
                !TextUtils.isEmpty(mPei3) ||
                !TextUtils.isEmpty(mPei4)){
            processDingPeiQu();
            processDingPeiChu();
        }
    }

    private boolean processDingDingQu() {
        if(mModeDingPei != ModeDingPei.DING_QU) {
            return true;
        }
        int i = 0;
        if(mDingQian.length() > 0) { i++; }
        if(mDingBai.length() > 0) { i++; }
        if(mDingShi.length() > 0) { i++; }
        if(mDingGe.length() > 0) { i++; }

        if(i > mPlayLen) {
            mNoRes = true;
            mFragment.showToast("没有这样的号码");
            return false;
        }

        if(i == 0) { return true; }

        mDingQian = removeRepeateChar(mDingQian);
        mDingBai = removeRepeateChar(mDingBai);
        mDingShi = removeRepeateChar(mDingShi);
        mDingGe = removeRepeateChar(mDingGe);

        for (int j = 0; j < mAllLen; j++) {
            if(!TextUtils.isEmpty(mDingQian) && !mDingQian.contains(mAll[j].substring(0,1))) {
                mAllDel[j] = 1;
                continue;
            }
            if(!TextUtils.isEmpty(mDingBai) && !mDingBai.contains(mAll[j].substring(1,2))) {
                mAllDel[j] = 1;
                continue;
            }
            if(!TextUtils.isEmpty(mDingShi) && !mDingShi.contains(mAll[j].substring(2,3))) {
                mAllDel[j] = 1;
                continue;
            }
            if(!TextUtils.isEmpty(mDingGe) && !mDingGe.contains(mAll[j].substring(3,4))) {
                mAllDel[j] = 1;
                continue;
            }
        }
        return true;
    }

    private boolean processDingDingChu() {
        if(mModeDingPei != ModeDingPei.DING_CHU) {
            return true;
        }

        mModeDingPei = ModeDingPei.DING_QU;  //零时设置变量
        processDingDingQu();// 先按定取计算一次
        mModeDingPei = ModeDingPei.DING_CHU;  //还原变量
        mResList.clear();

        //对定取做反转，则为定除
        for (int j = 0; j < mAllLen; j++) {
            if(mAllDel[j] == 1) {
                mAllDel[j] = 0;
            }else {
                mAllDel[j] = 1;
            }
        }

        //如果位置设置有值， 则不能为X
        for (int j = 0; j < mAllLen; j++) {
            if(!TextUtils.isEmpty(mDingQian) && mAll[j].substring(0,1).equals("X")) {
                mAllDel[j] = 1;
                continue;
            }
            if(!TextUtils.isEmpty(mDingBai) && mAll[j].substring(1,2).equals("X")) {
                mAllDel[j] = 1;
                continue;
            }
            if(!TextUtils.isEmpty(mDingShi) && mAll[j].substring(2,3).equals("X")) {
                mAllDel[j] = 1;
                continue;
            }
            if(!TextUtils.isEmpty(mDingGe) && mAll[j].substring(3,4).equals("X")) {
                mAllDel[j] = 1;
                continue;
            }
        }
        return true;
    }

    private boolean processDingPeiQu() {
        if(mModeDingPei != ModeDingPei.PEI_QU) {
            return true;
        }
        mPei1 = removeRepeateChar(mPei1);
        mPei2 = removeRepeateChar(mPei2);
        mPei3 = removeRepeateChar(mPei3);
        mPei4 = removeRepeateChar(mPei4);

        int pLen = 0;
        if(!TextUtils.isEmpty(mPei1)) { pLen++; }
        if(!TextUtils.isEmpty(mPei2)) { pLen++; }
        if(!TextUtils.isEmpty(mPei3)) { pLen++; }
        if(!TextUtils.isEmpty(mPei4)) { pLen++; }

        int p1, p2, p3, p4;
        int t1, t2, t3, t4;
        for (int j = 0; j < mAllLen; j++) {
            p1=0; p2=0; p3=0; p4 = 0;
            t1=0; t2=0; t3=0; t4 = 0;
            if(!TextUtils.isEmpty(mPei1)) {
                if(mPei1.contains(mAll[j].substring(0,1))) {
                    p1 = 1;
                    t1 = 1;
                }
                if(mPei1.contains(mAll[j].substring(1,2))) {
                    p2 = 1;
                    t1 = 1;
                }
                if(mAll[j].length()> 2 && mPei1.contains(mAll[j].substring(2,3))) {
                    p3 = 1;
                    t1 = 1;
                }
                if(mAll[j].length()> 3 && mPei1.contains(mAll[j].substring(3,4))) {
                    p4 = 1;
                    t1 = 1;
                }
            }else {
                t1 = 1;
            }
            if(!TextUtils.isEmpty(mPei2)) {
                if(mPei2.contains(mAll[j].substring(0,1))) {
                    p1 = 1;
                    t2 = 1;
                }
                if(mPei2.contains(mAll[j].substring(1,2))) {
                    p2 = 1;
                    t2 = 1;
                }
                if(mAll[j].length()> 2 && mPei2.contains(mAll[j].substring(2,3))) {
                    p3 = 1;
                    t2 = 1;
                }
                if(mAll[j].length()> 3 && mPei2.contains(mAll[j].substring(3,4))) {
                    p4 = 1;
                    t2 = 1;
                }
            }else {
                t2 = 1;
            }
            if(!TextUtils.isEmpty(mPei3)) {
                if(mPei3.contains(mAll[j].substring(0,1))) {
                    p1 = 1;
                    t3 = 1;
                }
                if(mPei3.contains(mAll[j].substring(1,2))) {
                    p2 = 1;
                    t3 = 1;
                }
                if(mAll[j].length()> 2 && mPei3.contains(mAll[j].substring(2,3))) {
                    p3 = 1;
                    t3 = 1;
                }
                if(mAll[j].length()> 3 && mPei3.contains(mAll[j].substring(3,4))) {
                    p4 = 1;
                    t3 = 1;
                }
            }else {
                t3 = 1;
            }
            if(!TextUtils.isEmpty(mPei4)) {
                if(mPei4.contains(mAll[j].substring(0,1))) {
                    p1 = 1;
                    t4 = 1;
                }
                if(mPei4.contains(mAll[j].substring(1,2))) {
                    p2 = 1;
                    t4 = 1;
                }
                if(mAll[j].length()> 2 && mPei4.contains(mAll[j].substring(2,3))) {
                    p3 = 1;
                    t4 = 1;
                }
                if(mAll[j].length()> 3 && mPei4.contains(mAll[j].substring(3,4))) {
                    p4 = 1;
                    t4 = 1;
                }
            }else {
                t4 = 1;
            }

            if(p1+p2+p3+p4 >= pLen && t1 > 0 && t2 > 0 && t3 > 0 && t4 > 0) {
                mAllDel[j] = 0;
            }else {
                mAllDel[j] = 1;
            }
        }
        return true;
    }

    private boolean processDingPeiChu() {
        if(mModeDingPei != ModeDingPei.PEI_CHU) {
            return true;
        }

        //临时转为取， 再还原变量
        mModeDingPei = ModeDingPei.PEI_QU;
        processDingPeiQu();
        mModeDingPei = ModeDingPei.PEI_CHU;

        //对取做反转，则为除
        for (int j = 0; j < mAllLen; j++) {
            if(mAllDel[j] == 1) {
                mAllDel[j] = 0;
            }else {
                mAllDel[j] = 1;
            }
        }

        return true;
    }

    //生成现的号码
    private void createClassXianNumber() {
        if(!TextUtils.isEmpty(mPei1) ||
                !TextUtils.isEmpty(mPei2) ||
                !TextUtils.isEmpty(mPei3) ||
                !TextUtils.isEmpty(mPei4)){
            processXianPeiQu();
            processXianPeiChu();
        }
    }

    private boolean processXianPeiQu() {
        return processDingPeiQu();
    }

    private boolean processXianPeiChu() {
        return processDingPeiChu();
    }

    private void processHefen() {
        if(mPlayClass.contains("x")) {
            return;
        }
        if(!mHefenQu && !mHefenChu ) {
            return;
        }

        processHefenGroup(mHefenCb11,mHefenCb12,mHefenCb13,mHefenCb14,mHefen1Str);
        processHefenGroup(mHefenCb21,mHefenCb22,mHefenCb23,mHefenCb24,mHefen2Str);
        processHefenGroup(mHefenCb31,mHefenCb32,mHefenCb33,mHefenCb34,mHefen3Str);
        processHefenGroup(mHefenCb41,mHefenCb42,mHefenCb43,mHefenCb44,mHefen4Str);
    }

    private void processHefenGroup(boolean cb1, boolean cb2, boolean cb3, boolean cb4, String inStr) {
        if(!cb1 && !cb2 && !cb3 && !cb4) {
            return;
        }
        if(TextUtils.isEmpty(inStr)) {
            return;
        }

        int k = 0;
        int i1 =0, i2 =0, i3 =0, i4 =0;
        for (int j = 0; j < mAllLen; j++) {
            i1 =-1; i2 =-1; i3 =-1; i4 =-1;
            if(cb1 && "X".equals(mAll[j].substring(0,1))) { mAllDel[j] = 1; continue; }
            if(cb2 && "X".equals(mAll[j].substring(1,2))) { mAllDel[j] = 1; continue; }
            if(cb3 && "X".equals(mAll[j].substring(2,3))) { mAllDel[j] = 1; continue; }
            if(cb4 && "X".equals(mAll[j].substring(3,4))) { mAllDel[j] = 1; continue; }

            if(cb1) { i1 = Integer.parseInt(mAll[j].substring(0,1)); }
            if(cb2) { i2 = Integer.parseInt(mAll[j].substring(1,2)); }
            if(cb3) { i3 = Integer.parseInt(mAll[j].substring(2,3)); }
            if(cb4) { i4 = Integer.parseInt(mAll[j].substring(3,4)); }

            k = 0;
            if(i1 > 0) { k = k + i1; }
            if(i2 > 0) { k = k + i2; }
            if(i3 > 0) { k = k + i3; }
            if(i4 > 0) { k = k + i4; }
            k = k%10;
            if(mHefenQu){
                if(!inStr.contains("" + k)) {
                    mAllDel[j] = 1;
                }
            }
            if(mHefenChu){
                if(inStr.contains("" + k)) {
                    mAllDel[j] = 1;
                }
            }
        }
    }

    private void processBudingHefen() {
        if(!mHeshuLiang && !mHeshuSan) {
            return;
        }
        if(TextUtils.isEmpty(mHeshuStr)) {
            return;
        }

        boolean bTmp = false;
        int i1 =0, i2 =0, i3 =0, i4 =0;
        for (int j = 0; j < mAllLen; j++) {
            bTmp = false;
            i1 =-1; i2 =-1; i3 =-1; i4 =-1;
            if(!"X".equals(mAll[j].substring(0,1))) { i1 = Integer.parseInt(mAll[j].substring(0,1)); }
            if(!"X".equals(mAll[j].substring(1,2))) { i2 = Integer.parseInt(mAll[j].substring(1,2)); }
            if(!"X".equals(mAll[j].substring(2,3))) { i3 = Integer.parseInt(mAll[j].substring(2,3)); }
            if(!"X".equals(mAll[j].substring(3,4))) { i4 = Integer.parseInt(mAll[j].substring(3,4)); }

            if(mHeshuLiang) {
                if(i1 > -1 && i2 > -1 && mHeshuStr.contains("" + (i1+i2)%10)){  bTmp = true; }
                if(i1 > -1 && i3 > -1 && mHeshuStr.contains("" + (i1+i3)%10)){  bTmp = true; }
                if(i1 > -1 && i4 > -1 && mHeshuStr.contains("" + (i1+i4)%10)){  bTmp = true; }
                if(i2 > -1 && i3 > -1 && mHeshuStr.contains("" + (i2+i3)%10)){  bTmp = true; }
                if(i2 > -1 && i4 > -1 && mHeshuStr.contains("" + (i2+i4)%10)){  bTmp = true; }
                if(i3 > -1 && i4 > -1 && mHeshuStr.contains("" + (i3+i4)%10)){  bTmp = true; }
                if(!bTmp) { mAllDel[j] = 1; }  //没有符合的数， 删除
            }

            if(mHeshuSan) {
                if(i1 > -1 && i2 > -1 && i3 > -1 && mHeshuStr.contains("" + (i1+i2+i3)%10)){  bTmp = true; }
                if(i1 > -1 && i2 > -1 && i4 > -1 && mHeshuStr.contains("" + (i1+i2+i4)%10)){  bTmp = true; }
                if(i1 > -1 && i3 > -1 && i4 > -1 && mHeshuStr.contains("" + (i1+i3+i4)%10)){  bTmp = true; }
                if(i2 > -1 && i3 > -1 && i4 > -1 && mHeshuStr.contains("" + (i2+i3+i4)%10)){  bTmp = true; }
                if(!bTmp) { mAllDel[j] = 1; }  //没有符合的数， 删除
            }
        }

    }

    private void processBudingHefenFanwei() {
        if(TextUtils.isEmpty(mHeshuMinStr) || TextUtils.isEmpty(mHeshuMaxStr) ) {
            return;
        }

        if(!mPlayClass.equals(QXConstant.CLASS_4P)) {
            return;
        }

        int iMin = 0; int iMax = 0;
        try {
            iMin = Integer.parseInt(mHeshuMinStr);
            iMax = Integer.parseInt(mHeshuMaxStr);
        }catch (NumberFormatException e) {
            return;
        }

        int i1 =0, i2 =0, i3 =0, i4 =0, k = 0;
        for (int j = 0; j < mAllLen; j++) {
            i1 = Integer.parseInt(mAll[j].substring(0, 1));
            i2 = Integer.parseInt(mAll[j].substring(1, 2));
            i3 = Integer.parseInt(mAll[j].substring(2, 3));
            i4 = Integer.parseInt(mAll[j].substring(3, 4));
            k = i1 + i2 + i3 + i4;
            if(k<iMin || k>iMax ){
                mAllDel[j] = 1;  //没有符合， 删除
            }
        }
    }

    public void processQuanzhuan() {
        if(mPlayClass.contains("x")) {
            return;
        }
        if(mQuanzhuanStr.length() > 0 && mQuanzhuanStr.length() < mPlayLen) {
            mNoRes = true;
            return;
        }

        int k = 0;
        int[] pArr = new int[4];
        for (int j = 0; j < mAllLen; j++) {
            //全转
            if(mQuanzhuanStr.length() > 0) {
                pArr[0] =0; pArr[1] =0; pArr[2] =0; pArr[3] =0;
                //输入的str如果有len个位置匹配上，且不能匹配到同一个位置，则成立。
                for (int m = 0; m < mQuanzhuanStr.length(); m++) {
                    k = 0;
                    for (int n = 0; n < 4; n++) {
                        k = mAll[j].indexOf(mQuanzhuanStr.substring(m, m+1), k);
                        if(k>=0) {
                            if(pArr[k] < 1) {
                                pArr[k] = 1;
                                break;
                            }else {
                                k++;
                            }
                        }
                    }
                }
                if(pArr[0]+pArr[1]+pArr[2]+pArr[3] < mPlayLen) {
                    mAllDel[j] = 1;  //没有符合， 删除
                    continue;
                }
            }

            //上奖
            if(!TextUtils.isEmpty(mShangjiangStr)) {
                pArr[0] =0; pArr[1] =0; pArr[2] =0; pArr[3] =0;
                //输入的str如果有len个位置匹配上，且不能匹配到同一个位置，则成立。
                for (int m = 0; m < mShangjiangStr.length(); m++) {
                    k = 0;
                    for (int n = 0; n < 4; n++) {
                        k = mAll[j].indexOf(mShangjiangStr.substring(m, m+1), k);
                        if(k>=0) {
                            if(pArr[k] < 1) {
                                pArr[k] = 1;
                                break;
                            }else {
                                k++;
                            }
                        }
                    }
                }
                if(mShangjiangStr.length() < mPlayLen){
                    if(pArr[0]+pArr[1]+pArr[2]+pArr[3] < mShangjiangStr.length()) {
                        mAllDel[j] = 1;  //没有符合， 删除
                        continue;
                    }
                }else {
                    if(pArr[0]+pArr[1]+pArr[2]+pArr[3] < mPlayLen) {
                        mAllDel[j] = 1;  //没有符合， 删除
                        continue;
                    }
                }
            }

            //排除
            if(!TextUtils.isEmpty(mPaichuStr)) {
                if(mPaichuStr.contains(mAll[j].substring(0, 1))
                        || mPaichuStr.contains(mAll[j].substring(1, 2))
                        || mPaichuStr.contains(mAll[j].substring(2, 3))
                        || mPaichuStr.contains(mAll[j].substring(3, 4))) {
                    mAllDel[j] = 1;  //符合， 删除
                    continue;
                }
            }

            //乘号位置
            if(mPlayLen < 4) {
                if(mXPos1 && !"X".equals(mAll[j].substring(0, 1))) {
                    mAllDel[j] = 1;  //没有符合， 删除
                }
                if(mXPos2 && !"X".equals(mAll[j].substring(1, 2))) {
                    mAllDel[j] = 1;  //没有符合， 删除
                }
                if(mXPos3 && !"X".equals(mAll[j].substring(2, 3))) {
                    mAllDel[j] = 1;  //没有符合， 删除
                }
                if(mXPos4 && !"X".equals(mAll[j].substring(3, 4))) {
                    mAllDel[j] = 1;  //没有符合， 删除
                }
            }

        }

    }

    private void processHanFushi() {
        processHan();
        processFushi();
    }

    private void processHan() {
        if(!mHanQu && !mHanChu) {
            return;
        }
        if(TextUtils.isEmpty(mHanStr)) {
            return;
        }

        boolean bTmp = false;
        for (int j = 0; j < mAllLen; j++) {
            bTmp = false;
            for (int m = 0; m < mHanStr.length(); m++) {
                if(mAll[j].contains(mHanStr.substring(m, m+1))){
                    bTmp = true;
                    break;
                }
            }
            if(!bTmp) {
                mAllDel[j] = 1;  //没有符合， 删除
            }
        }
    }

    private void processFushi() {
        if(TextUtils.isEmpty(mFushiStr)) {
            return;
        }

        boolean bTmp = false;
        for (int j = 0; j < mAllLen; j++) {
            bTmp = false;
            if(!mFushiStr.contains(mAll[j].substring(0,1))) {
                bTmp = true;
            }
            if(!mFushiStr.contains(mAll[j].substring(1,2))) {
                bTmp = true;
            }
            if(mAll[j].length() > 2 && !mFushiStr.contains(mAll[j].substring(2,3))) {
                bTmp = true;
            }
            if(mAll[j].length() > 3 && !mFushiStr.contains(mAll[j].substring(3,4))) {
                bTmp = true;
            }
            if(bTmp){
                mAllDel[j] = 1;  //没有符合， 删除
            }
        }
    }

    //双重， 双双重， 三重， 四重
    private void processChongChong() {
        int k = 0;
        int b2, b3, b4;
        int[] iArr = new int[10];
        for (int j = 0; j < mAllLen; j++) {
            for (int m=0; m<10; m++) {
                iArr[m] = 0;
            }

            for (int n = 0; n < mAll[j].length(); n++) {
                if(!"X".equals(mAll[j].substring(n, n+1))) {
                    k = Integer.parseInt(mAll[j].substring(n, n+1));
                    iArr[k]++;
                }
            }

            b2 = 0;  b3 = 0;  b4 = 0;
            for (int m=0; m<10; m++) {
                if(iArr[m] > 1){ b2++; }
                if(iArr[m] > 2){ b3++; }
                if(iArr[m] > 3){ b4++; }
            }

            //处理除
            if(mChongShuangChu && b2 > 0)  { mAllDel[j] = 1; continue; }
            if(QXConstant.CLASS_4P.equals(mPlayClass) && mChongShuangShuangChu && (b2>1 || b4>0)) { mAllDel[j] = 1; continue; }
            if(mChongSanChu && b3 > 0)  { mAllDel[j] = 1; continue; }
            if(mChongSiChu && b4 > 0)  { mAllDel[j] = 1; continue; }

            //处理取
            if(mChongShuangQu || mChongShuangShuangQu || mChongSanQu || mChongSiQu){

                if((mChongShuangQu && b2 > 0) ||
                        (mChongShuangShuangQu && (b2>1 || b4>0)) ||
                        (mChongSanQu && b3 > 0) ||
                        (mChongSiQu && b4 > 0) ){

                }else {
                    mAllDel[j] = 1;
                }
            }

        }
    }

    //兄弟
    private void processXiongdi() {
        String tmpStr = "";
        String sanStr = "";
        boolean x2, x3, x4;
        for (int j = 0; j < mAllLen; j++) {
            x2 = false; x3 = false; x4 = false;
            char[] sortStr = sortStr(mAll[j].replace("x", ""));

            //四兄弟
            tmpStr = new String(sortStr);
            if(mPlayLen == 4) {
                if(tmpStr.equals("0123") || tmpStr.equals("1234") || tmpStr.equals("2345") || tmpStr.equals("3456") || tmpStr.equals("4567") ||
                        tmpStr.equals("5678") || tmpStr.equals("6789") || tmpStr.equals("0789") || tmpStr.equals("0189") || tmpStr.equals("0129")) {
                    x2 = true; x3 = true;  x4 = true;
                }
            }

            //三兄弟  //如果已经存在四兄弟， 则肯定已经是三兄弟了
            sanStr = removeRepeateChar(tmpStr);
            if(!x3 && mPlayLen > 2) {
                if(sanStr.contains("012") || sanStr.contains("123") || sanStr.contains("234") || sanStr.contains("345") || sanStr.contains("456") ||
                        sanStr.contains("567") || sanStr.contains("678") || sanStr.contains("789") ||
                        (sanStr.contains("0") && sanStr.contains("89")) ||
                        (sanStr.contains("01") && sanStr.contains("9"))) {
                    x2 = true; x3 = true;
                }
            }

            //二兄弟 //如果已经判断是二兄弟了，则不需要继续了
            if(!x2){
                if(sanStr.contains("01") || sanStr.contains("12") || sanStr.contains("23") || sanStr.contains("34") || sanStr.contains("45") ||
                        sanStr.contains("56") || sanStr.contains("67") || sanStr.contains("78") || sanStr.contains("89") || (sanStr.contains("0") && sanStr.contains("9"))) {
                    x2 = true;
                }

            }
            //处理除
            if(mErXiongdiChu && x2) { mAllDel[j] = 1; continue; }
            if(mSanXiongdiChu && x3) { mAllDel[j] = 1; continue; }
            if(mSiXiongdiChu && x4) { mAllDel[j] = 1; continue; }

            //处理取
            if(mErXiongdiQu || mSanXiongdiQu || mSiXiongdiQu) {
                if((mErXiongdiQu && x2) || (mSanXiongdiQu && x3) || (mSiXiongdiQu && x4)) {

                }else {
                    mAllDel[j] = 1;
                }
            }

        }

    }

    private void processDuishu() {
        if(!mDuishuChu && !mDuishuQu) {
            return;
        }
        if(TextUtils.isEmpty(mDuishu1) && TextUtils.isEmpty(mDuishu2) && TextUtils.isEmpty(mDuishu3)){
            return;
        }

        boolean bTmp = false;
        for (int j = 0; j < mAllLen; j++) {
            bTmp = false;
            if(!TextUtils.isEmpty(mDuishu1)){
                if(mAll[j].contains(mDuishu1.substring(0,1)) && mAll[j].contains(mDuishu1.substring(1,2)) ){
                    bTmp = true;
                }
            }

            if(!TextUtils.isEmpty(mDuishu2)){
                if(mAll[j].contains(mDuishu2.substring(0,1)) && mAll[j].contains(mDuishu2.substring(1,2)) ){
                    bTmp = true;
                }
            }

            if(!TextUtils.isEmpty(mDuishu3)){
                if(mAll[j].contains(mDuishu3.substring(0,1)) && mAll[j].contains(mDuishu3.substring(1,2)) ){
                    bTmp = true;
                }
            }

            if(mDuishuQu) {
                if(!bTmp) {
                    mAllDel[j] = 1;
                }
            }

            if(mDuishuChu){
                if(bTmp) {
                    mAllDel[j] = 1;
                }
            }
        }

    }

    //单双
    private void processDanShuang() {
        processDan();
        processShuang();
    }

    private void processDan() {
        if(!mDanChu && !mDanQu) {
            return;
        }
        if(!mDan1 && !mDan2 && !mDan3 && !mDan4){
            return;
        }
        boolean bTmp = false;
        for (int j = 0; j < mAllLen; j++) {
            bTmp = true;
            if(mDan1 && !NUMBER_DAN.contains(mAll[j].substring(0,1))){
                bTmp = false;
            }
            if(mDan2 && !NUMBER_DAN.contains(mAll[j].substring(1,2))){
                bTmp = false;
            }
            if(mDan3 && !NUMBER_DAN.contains(mAll[j].substring(2,3))){
                bTmp = false;
            }
            if(mDan4 && !NUMBER_DAN.contains(mAll[j].substring(3,4))){
                bTmp = false;
            }

            if(mDanQu) {
                if(!bTmp){
                    mAllDel[j] = 1;
                }
            }
            if(mDanChu) {
                if(bTmp){
                    mAllDel[j] = 1;
                }
            }
        }
    }

    private void processShuang() {
        if(!mShuangChu && !mShuangQu) {
            return;
        }
        if(!mShuang1 && !mShuang2 && !mShuang3 && !mShuang4){
            return;
        }
        boolean bTmp = false;
        for (int j = 0; j < mAllLen; j++) {
            bTmp = true;
            if(mShuang1 && !NUMBER_SHUANG.contains(mAll[j].substring(0,1))){
                bTmp = false;
            }
            if(mShuang2 && !NUMBER_SHUANG.contains(mAll[j].substring(1,2))){
                bTmp = false;
            }
            if(mShuang3 && !NUMBER_SHUANG.contains(mAll[j].substring(2,3))){
                bTmp = false;
            }
            if(mShuang4 && !NUMBER_SHUANG.contains(mAll[j].substring(3,4))){
                bTmp = false;
            }

            if(mShuangQu) {
                if(!bTmp){
                    mAllDel[j] = 1;
                }
            }
            if(mShuangChu) {
                if(bTmp){
                    mAllDel[j] = 1;
                }
            }
        }
    }

    private char[] sortStr(String str){
        //利用toCharArray可将字符串转换为char型的数组
        char[] s1 = str.toCharArray();
        for(int i=0;i<s1.length;i++){
            for(int j=0;j<i;j++){
                if(s1[i]<s1[j]){
                    char temp = s1[i];
                    s1[i] = s1[j];
                    s1[j] = temp;
                }
            }
        }
        return s1;
        //再次将字符数组转换为字符串，也可以直接利用String.valueOf(s1)转换
//        String st = new String(s1);
//        return st;
    }

    public String removeRepeateChar(String s) {
        StringBuffer sb = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (s.indexOf(c) ==s.lastIndexOf(c)) {//此字符第一次位置和最后位置一致 即肯定没有重复的直接添加
                sb.append(c);
            } else {//同理 次字符出现过多次
                int fristposition=s.indexOf(c);//次字符第一次出现的位置
                if(fristposition==i){//第一次出现的位置和当前位置一致 即第一次出现添加
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    //清除多余的数据
    private void clearData() {
        if(mPlayLen < 3) {
            mPei3 = "";
            mPei4 = "";
        }
        if(mPlayLen < 4) {
            mPei4 = "";
        }
    }


    public String getQuanzhuan(String inputStr) {
        if(TextUtils.isEmpty(inputStr) || inputStr.length()<4) {
            return "";
        }

        int i, j, k, m;
        String tmpStr = inputStr;
//        i = 0; j = 1; k = 2; m = 3;
//        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 0; j = 1; k = 3; m = 2;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 0; j = 2; k = 1; m = 3;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 0; j = 2; k = 3; m = 1;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 0; j = 3; k = 1; m = 2;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 0; j = 3; k = 2; m = 1;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 1; j = 0; k = 2; m = 3;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 1; j = 0; k = 3; m = 2;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 1; j = 2; k = 0; m = 3;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 1; j = 2; k = 3; m = 0;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 1; j = 3; k = 0; m = 2;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 1; j = 3; k = 2; m = 0;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 2; j = 0; k = 1; m = 3;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 2; j = 0; k = 3; m = 1;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 2; j = 1; k = 0; m = 3;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 2; j = 1; k = 3; m = 0;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 2; j = 3; k = 0; m = 1;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 2; j = 3; k = 1; m = 0;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 3; j = 0; k = 1; m = 2;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 3; j = 0; k = 2; m = 1;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 3; j = 1; k = 0; m = 2;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 3; j = 1; k = 2; m = 0;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 3; j = 2; k = 0; m = 1;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        i = 3; j = 2; k = 1; m = 0;
        tmpStr = inputStr.substring(i,i+1) + inputStr.substring(j, j+1) + inputStr.substring(k, k+1) + inputStr.substring(m, m+1);
        if(!mResList.contains(tmpStr)){ mResList.add(tmpStr);}

        for (int a = 0; a < mResList.size(); a++) {
            if(a == 0) {
                tmpStr = mResList.get(a);
            }else {
                tmpStr = tmpStr + "," + mResList.get(a);
            }
        }

        return tmpStr;
    }

}
