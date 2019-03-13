package com.xqlh.beatandshout.Entity;

/**
 * Created by zl on 2019/2/27.
 */

public class EntityBrainWave {

    private int Attention;
    private int Meditation;

    private int delta;
    private int theta;
    private int AlphaHigh;
    private int AlphaBottom;
    private int BetaHigh;
    private int BetaBottom;
    private int GammaHigh;
    private int GammaBottom;

    public int getAttention() {
        return Attention;
    }

    public void setAttention(int attention) {
        Attention = attention;
    }

    public int getMeditation() {
        return Meditation;
    }

    public void setMeditation(int meditation) {
        Meditation = meditation;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public int getTheta() {
        return theta;
    }

    public void setTheta(int theta) {
        this.theta = theta;
    }

    public int getAlphaHigh() {
        return AlphaHigh;
    }

    public void setAlphaHigh(int alphaHigh) {
        AlphaHigh = alphaHigh;
    }

    public int getAlphaBottom() {
        return AlphaBottom;
    }

    public void setAlphaBottom(int alphaBottom) {
        AlphaBottom = alphaBottom;
    }

    public int getBetaHigh() {
        return BetaHigh;
    }

    public void setBetaHigh(int betaHigh) {
        BetaHigh = betaHigh;
    }

    public int getBetaBottom() {
        return BetaBottom;
    }

    public void setBetaBottom(int betaBottom) {
        BetaBottom = betaBottom;
    }

    public int getGammaHigh() {
        return GammaHigh;
    }

    public void setGammaHigh(int gammaHigh) {
        GammaHigh = gammaHigh;
    }

    public int getGammaBottom() {
        return GammaBottom;
    }

    public void setGammaBottom(int gammaBottom) {
        GammaBottom = gammaBottom;
    }
}
