package com.kgc.sauw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.Maths;
import com.kgc.sauw.Vector2d;
import com.kgc.sauw.Vector2i;
import com.kgc.sauw.Camera2D;

public class Joystick{



    public int X;
    public int Y;
    private int D1;
    private int D2;
    private Texture a;
    private Texture h;
    private Vector2d handle_pos;
    private SpriteBatch b;
    private boolean joystick_touched;
    private Camera2D CAM=null;
    private boolean isTouched;
    private boolean hided;



    public Joystick(Texture a,Texture h,int x,int y,SpriteBatch b,int d){
        setTextures(a,h);
        setPos(x,y);
        setBatch(b);
        setDiameters(d);
        handle_pos=new Vector2d();
    }

    public Joystick(Texture a,Texture h,int x,int y,SpriteBatch b,int d,Camera2D c){
        setTextures(a,h);
        setPos(x,y);
        setBatch(b);
        setDiameters(d);
        setCamera(c);
        handle_pos=new Vector2d();
    }



    public void hide(boolean h){
        hided=h;
    }

    public boolean isHided(){
        return hided;
    }

    public void setDiameters(int area){
        D1=area;
        double k=a.getWidth()/h.getWidth();
        D2=(int)Math.round(area/k);
    }

    public void setDiameters(int area,int handle){
        D1=area;
        D2=handle;
    }

    public void setPos(int x,int y){
        X=x;
        Y=y;
    }

    public void setPos(Vector2d v){
        X=v.x();
        Y=v.y();
    }

    public void setTextures(Texture area,Texture handle){
        a=area;
        h=handle;
    }

    public void setTextures(FileHandle area,FileHandle handle){
        a=new Texture(area);
        h=new Texture(handle);
    }

    public void setAreaTexture(Texture area){
        a=area;
    }

    public void setHandleTexture(Texture handle){
       h=handle;
    }

    public void setBatch(SpriteBatch b){
        this.b=b;
    }

    public void setCamera(Camera2D c){
        CAM=c;
    }

    public double angleD(){
        return handle_pos.angleD();
    }

    public int angleI(){
        return handle_pos.angleI();
    }

    public Vector2d normD(){
        return handle_pos.getNorm();
    }

    public Vector2d normD(int length){
        return handle_pos.getNorm(length);
    }

    public Vector2d normD(double length){
        return handle_pos.getNorm(length);
    }

    public Vector2i normI(){
        Vector2d v=handle_pos.getNorm();
        return new Vector2i(v.x(),v.y());
    }

    public Vector2i normI(int length){
        Vector2d v=handle_pos.getNorm(length);
        return new Vector2i(v.x(),v.y());
    }

    public int dist(){
        return Maths.distance(new Vector2d(),handle_pos);
    }

    public double normDist(){
        return Maths.distanceD(new Vector2d(),handle_pos)/(D1/2-D2/2);
    }

    public int normDistI(int length){
        return (int)Math.round(Maths.distanceD(new Vector2d(),handle_pos)/(D1/2-D2/2)*length);
    }

    public double normDistD(int length){
        return Maths.distanceD(new Vector2d(),handle_pos)/(D1/2-D2/2)*length;
    }

    public double normDistD(double length){
        return Maths.distanceD(new Vector2d(),handle_pos)/(D1/2-D2/2)*length;
    }

    public boolean isTouched(){
        return joystick_touched;
    }

    public void render(){
        if(!hided)
            render(X,Y);
    }

    public void render(Camera2D c){
        if(!hided)
            render(X+c.X,Y+c.Y);
    }

    private void render(int X,int Y){

        int x=0,y=0;
        if(Gdx.input.isTouched()){
            if(CAM!=null){
                x=CAM.touchX();
                y=CAM.touchY();
            }else{
                x=Gdx.input.getX();
                y=Gdx.graphics.getHeight()-Gdx.input.getY();
            }

            if(!isTouched){
                if(Maths.distance(X+D1/2,Y+D1/2,x,y)<=D1/2)
                    joystick_touched=true;

                isTouched=true;
            }
        }

        if(joystick_touched){
            handle_pos.x=x-X-D1/2;
            handle_pos.y=y-Y-D1/2;

            if(Maths.distance(0,0,handle_pos.x(),handle_pos.y())>D1/2-D2/2){
                handle_pos.norm(D1+D2);
            }

            if(!Gdx.input.isTouched()){
                joystick_touched=false;
                handle_pos.setZero();
            }
        }

        if(!Gdx.input.isTouched())
            isTouched=false;

        b.draw(a,X,Y,D1,D1);
        b.draw(h,X+handle_pos.x()+D1/2-D2/2,Y+handle_pos.y()+D1/2-D2/2,D2,D2);
    }



}
