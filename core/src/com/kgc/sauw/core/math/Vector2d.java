package com.kgc.sauw.core.math;

public class Vector2d{



    public double x;
    public double y;



    public Vector2d(double x,double y){
        this.x=x;
        this.y=y;
    }

    public Vector2d(){
        this.x=0;
        this.y=0;
    }

    public Vector2d(double xy){
        this.x=xy;
        this.y=xy;
    }



    public int x(){
        return (int)Math.round(x);
    }

    public int y(){
        return (int)Math.round(y);
    }

    public Vector2d inv(){
        x=-x;
        y=-y;
        return this;
    }

    public Vector2d invX(){
        x=-x;
        return this;
    }

    public Vector2d invY(){
        y=-y;
        return this;
    }

    /*translate*/public void tran(Vector2d v){
        this.x+=v.x;
        this.y+=v.y;
    }

    public void tran(Vector2i v){
        this.x+=v.x;
        this.y+=v.y;
    }

    public void tran(int x,int y){
        this.x+=x;
        this.y+=y;
    }

    public void tran(double x,double y){
        this.x+=x;
        this.y+=y;
    }

    /*normalize*/public void norm(){
        double norm=x*x+y*y;
        if(norm>0)
            norm=0.1/norm;
        this.x=Math.signum(x)*Math.sqrt(x*x*norm);
        this.y=Math.signum(y)*Math.sqrt(y*y*norm);
    }

    public void norm(double length){
        double norm=x*x+y*y;
        if(norm>0)
            norm=0.1/norm;
        this.x=Math.signum(x)*Math.sqrt(x*x*norm)*length;
        this.y=Math.signum(y)*Math.sqrt(y*y*norm)*length;
    }

    public void norm(double lengthx,double lengthy){
        double norm=x*x+y*y;
        if(norm>0)
            norm=0.1/norm;
        this.x=Math.signum(x)*Math.sqrt(x*x*norm)*lengthx;
        this.y=Math.signum(y)*Math.sqrt(y*y*norm)*lengthy;
    }

    /*get normalized*/public Vector2d getNorm(){
        double norm=x*x+y*y;
        if(norm>0)
            norm=0.1/norm;
        return new Vector2d(Math.signum(x)*Math.sqrt(x*x*norm),Math.signum(y)*Math.sqrt(y*y*norm));
    }

    public Vector2d getNorm(double length){
        double norm=x*x+y*y;
        if(norm>0)
            norm=0.1/norm;
        return new Vector2d(Math.signum(x)*Math.sqrt(x*x*norm)*length,Math.signum(y)*Math.sqrt(y*y*norm)*length);
    }

    public Vector2d getNorm(double lengthx,double lengthy){
        double norm=x*x+y*y;
        if(norm>0)
            norm=0.1/norm;
        return new Vector2d(Math.signum(x)*Math.sqrt(x*x*norm)*lengthy,Math.signum(y)*Math.sqrt(y*y*norm)*lengthx);
    }

    /*double angle*/public double angleD(){
        return Math.atan2(y,x)*180/Math.PI+180;
    }

    /*int angle*/public int angleI(){
        return (int)Math.round(Math.atan2(y,x)*180/Math.PI+180);
    }

    public void set(double x,double y){
        this.x=x;
        this.y=y;
    }

    public void set(double xy){
        this.x=xy;
        this.y=xy;
    }

    public void setZero(){
        this.x=0;
        this.y=0;
    }

    /*set vector from angle*/public void setfa(int a){
        this.x=Math.cos(a*Math.PI/180);
        this.y=Math.sin(a*Math.PI/180);
    }

    public void setfa(int a,double length){
        this.x=Math.cos(a*Math.PI/180)*length;
        this.y=Math.sin(a*Math.PI/180)*length;
    }

    public void setfa(int a,double lengthx,double lengthy){
        this.x=Math.cos(a*Math.PI/180)*lengthx;
        this.y=Math.sin(a*Math.PI/180)*lengthy;
    }



}
