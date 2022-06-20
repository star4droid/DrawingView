package com.star4droid.DrawingApp;
import android.view.View;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;
import java.util.LinkedList;
class DrawingView extends View {
public DrawingView(Context ctx)  {
super(ctx);
 
}
boolean start=false;
	private float strokeWidth=5;
	private int pcolor=Color.BLACK;
	private float xx=0;
	public enum TYPES {
		PEN,
		CIRCLE,
		SQUARE,
		TRIANGLE1,
		TRIANGLE2
	}
	private TYPES TYPE;
	private float yy=0;
	boolean eraser=false;
	private Path path=new Path();
private LinkedList<PathS> pz = new LinkedList<>();
	private Paint mPaint = new Paint();
	private PathS paths=new PathS(path,mPaint,strokeWidth,Color.BLACK);
	//4 redo
private LinkedList<PathS> rz = new LinkedList<>();
protected void onDraw(Canvas cv){
super.onDraw(cv);
for(PathS p:pz){
	if(p.joined) {
		for(PathS pp:p.join){
			if(pp.joined){
				draw(pp.join,cv);
			} else cv.drawPath(pp.path,pp.mPaint);
		}
		} else {
if(p.path != null) cv.drawPath(p.path,p.mPaint);
}
}
}
public void setPenSize(float w){
	strokeWidth=w;
}
public float getPenSize(){
	return strokeWidth;
}
public void setColor(int color){
	pcolor=color;
}
public void clear(){
	if(pz.size()>0){
	LinkedList<PathS> pzz=new LinkedList<>();
	pzz.addAll(pz);
	PathS gz=new PathS(pzz);
	rz.add(gz);
	pz.clear();
	invalidate();
	}
}
public void setEraserMode(boolean b){
	eraser=b;
}
public void draw(LinkedList<PathS> pz,Canvas cv){
	for(PathS p:pz){
		if(p.joined){draw(p.join,cv);} else {
			if(p.path != null) cv.drawPath(p.path,p.mPaint);
		}
	}
}
public void undo() {
	if(pz.size()>0){
	rz.add(pz.getLast());
	pz.removeLast();
	}
	invalidate();
}
public void redo() {
	if(rz.size()>0){
		pz.add(rz.getLast());
		rz.removeLast();
	}
	invalidate();
}
public boolean CanUndo(){
	return (pz.size()>0);
}
public boolean CanRedo(){
	return (rz.size()>0);
}
public void setDrawType(TYPES x){
	TYPE=x;
}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
		int ev = event.getAction();
		switch (ev) {
			case MotionEvent.ACTION_DOWN:
			if(TYPE==TYPES.CIRCLE){
			Path pth=new Path();
			Paint pi = new Paint();
			pi.setStrokeWidth(2);
			pi.setColor(Color.BLACK);
			pi.setStyle(Paint.Style.STROKE);
			pth.moveTo(event.getX()-40,event.getY());
			pth.lineTo(event.getX()+40,event.getY());
			pth.moveTo(event.getX(),event.getY()-40);
			pth.lineTo(event.getX(),event.getY()+40);
			pth.addCircle(event.getX(),event.getY(),30,Path.Direction.CCW);
			PathS pa=new PathS(pth,pi,2,Color.BLACK);
			pz.add(pa);
			}
			path=new Path();
			rz.clear();
			mPaint = new Paint();
			mPaint.setStrokeWidth(strokeWidth);
mPaint.setColor(pcolor);
mPaint.setStrokeCap(Paint.Cap.ROUND);
mPaint.setStrokeJoin(Paint.Join.ROUND);
mPaint.setStyle(Paint.Style.STROKE);
mPaint.setDither(true);
mPaint.setAntiAlias(true);
if(eraser){
	mPaint.setColor(Color.TRANSPARENT); 
	mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR)); 
} else {
	mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
}
path.moveTo(event.getX(),event.getY());
			paths=new PathS(path,mPaint,strokeWidth,pcolor);
			xx=event.getX();
			yy=event.getY();
			start=true;
			pz.add(paths);
			//Toast.makeText(getApplicationContext(),"started",Toast.LENGTH_SHORT).show();
			invalidate();
			break;
			case MotionEvent.ACTION_MOVE:
			//path.lineTo(event.getX(),event.getY());
			float cx=event.getX();
			float cy=event.getY();
			float x=event.getX();
			float y=event.getY();
			if (TYPE==TYPES.PEN){
			boolean b1=Math.abs(x-xx)>3;
			boolean b2=Math.abs(y-yy)>3;
			if(b1 | b2){
				path.quadTo(xx,yy,(xx+x)/2,(yy+y)/2);
				xx=x;
				yy=y;
				paths.path=path;
				paths.mPaint=mPaint;
			}
			
			} else {
			if(TYPE==TYPES.SQUARE){
			path.reset();
			path.moveTo(xx,yy);
			path.lineTo(event.getX(),yy);
			path.lineTo(event.getX(),event.getY());
			path.lineTo(xx,event.getY());
			path.lineTo(xx,yy);
			} else {
			if(TYPE==TYPES.CIRCLE){
			path.reset();
			double xmx = Math.abs((double)(cx-xx));
	double ymy = Math.abs((double)(cy-yy));
	double r = Math.sqrt((xmx*xmx)+(ymy*ymy));
			path.addCircle(xx,yy,(float)r,Path.Direction.CCW);
			} else {
			if(TYPE==TYPES.TRIANGLE1){
			path.reset();
			float dx=Math.abs(xx-cx);
			if(cx>xx){
			path.moveTo(xx,yy);
			path.lineTo(cx,cy);
			path.lineTo(xx-dx,cy);
			path.lineTo(xx,yy);
			} else {
			path.moveTo(xx,yy);
			path.lineTo(cx,cy);
			path.lineTo(xx+dx,cy);
			path.lineTo(xx,yy);
			}
			} else {
			if(TYPE==TYPES.TRIANGLE2){
			path.reset();
			path.moveTo(xx,yy);
			path.lineTo(xx,cy);
			path.lineTo(cx,cy);
			path.lineTo(xx,yy);
			}//triangle2 end
			}
			}
			}//end sq
			}
			invalidate();
			break;
			case MotionEvent.ACTION_UP:
			if(TYPE==TYPES.CIRCLE){
			//lazy way
			PathS temp= pz.getLast();
			pz.removeLast();
			pz.removeLast();
			pz.add(temp);
			}
			invalidate();
			break;
		} return true;
	}
	class PathS {
Paint mPaint;
Path path;
float width;
boolean joined=false;
LinkedList<PathS> join;
int color;
public PathS(Path path,Paint paint,float w,int clr){
this.path=path;
mPaint=paint;
width=w;
color=clr;
}
public PathS(LinkedList<PathS> ps){
	join=ps;
	joined=true;
} 
}
}