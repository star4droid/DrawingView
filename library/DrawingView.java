package com.star4droid.DrawingApp;
import android.view.View;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.graphics.PointF;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;
import java.util.LinkedList;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;
class DrawingView extends LinearLayout {
public DrawingView(Context ctx)  {
super(ctx);
setLayerType(View.LAYER_TYPE_HARDWARE,null);
 rulerp1 = new LinearLayout(ctx);
 rulerp2 = new LinearLayout(ctx);
this.addView(rulerp1);
this.addView(rulerp2);
 rulerp1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)90, (int)1, 0xFF607D8B, Color.TRANSPARENT));
 rulerp2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)90, (int)1, 0xFF607D8B, Color.TRANSPARENT));
 rulerp1.setLayoutParams(new LinearLayout.LayoutParams(50,50));
 rulerp2.setLayoutParams(new LinearLayout.LayoutParams(50,50));
 rulerp1.setX(100);
 rulerp1.setY(100);
 rulerp2.setY(100);
 rulerp2.setX(100);
 dxx= rulerp1.getX()-rulerp2.getX();
 dyy= rulerp1.getY()-rulerp2.getY();
//onTouch drag1
final DrawingView cc=this;
rulerp1.setOnTouchListener(new OnTouchListener() {
				PointF DownPT = new PointF();
				PointF StartPT = new PointF();
		//float dx= rulerp1.getX()-rulerp2.getX();
		//float dy= rulerp1.getY()-rulerp2.getY();
				@Override public boolean onTouch(View _view, MotionEvent event) {
						switch (event.getAction()) {
								case MotionEvent.ACTION_MOVE:
			cc.invalidate();
			PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
								_view.setX((int)(StartPT.x+mv.x));
								_view.setY((int)(StartPT.y+mv.y));
		if(showH&&(TYPE==TYPES.CIRCLE)){
			rulerp2.setX((int)(StartPT.x+mv.x-dxx));
			rulerp2.setY((int)(StartPT.y+mv.y+dyy));
			}
								StartPT = new PointF(_view.getX(), _view.getY());
								break; //xenon
								case MotionEvent.ACTION_DOWN : 
								DownPT.x = event.getX();
								DownPT.y = event.getY();
								StartPT = new PointF(_view.getX(), _view.getY());
								break;
								case MotionEvent.ACTION_UP : break;
								default : break;
						} return true;
				}});
				//onTouch drag2
rulerp2.setOnTouchListener(new OnTouchListener() {
				PointF DownPT = new PointF();
				PointF StartPT = new PointF();
				@Override public boolean onTouch(View _view, MotionEvent event) {
						switch (event.getAction()) {
								case MotionEvent.ACTION_MOVE:
			cc.invalidate();
			PointF mv = new PointF(event.getX() - DownPT.x, event.getY() - DownPT.y);
								_view.setX((int)(StartPT.x+mv.x));
								_view.setY((int)(StartPT.y+mv.y));
								StartPT = new PointF(_view.getX(), _view.getY());
								break; //xenon
								case MotionEvent.ACTION_DOWN : DownPT.x = event.getX();
								DownPT.y = event.getY();
								StartPT = new PointF(_view.getX(), _view.getY());
								break;
								case MotionEvent.ACTION_UP :
			dxx=rulerp1.getX()-rulerp2.getX();
			dyy=rulerp1.getY()-rulerp2.getY();
								  break;
								default : break;
						} return true;
				}});
		rulerp1.setVisibility(View.INVISIBLE);
		rulerp2.setVisibility(View.INVISIBLE);
		setBackgroundColor(Color.TRANSPARENT);
				//end
}
boolean start=false;
float dxx;
float dyy;
	private float strokeWidth=5;
	private LinearLayout rulerp1;
	private LinearLayout rulerp2;
	private boolean showH=false;
	private int pcolor=Color.BLACK;
	private float xx=0;
	public enum STYLE {
	FILL,
	FILL_AND_STROKE,
	STROKE
	}
	private Paint.Style style=Paint.Style.STROKE;
	public void setStyle(STYLE s){
	if(s==STYLE.STROKE){
	style = Paint.Style.STROKE;
	} else if(s==STYLE.FILL){
	style = Paint.Style.FILL;
	} else if(s==STYLE.FILL_AND_STROKE){
	style = Paint.Style.FILL_AND_STROKE;
	}
	}
	public enum TYPES {
		PEN,
		CIRCLE,
		SQUARE,
		TRIANGLE1,
		TRIANGLE2,
		LINE
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
//ruler
if(showH&&((TYPE==TYPES.CIRCLE)|(TYPE==TYPES.LINE))){
rulerp1.setVisibility(View.VISIBLE);
rulerp2.setVisibility(View.VISIBLE);
rulerp1.setLayoutParams(new LinearLayout.LayoutParams(25,25));
if (TYPE==TYPES.LINE){
rulerp1.setLayoutParams(new LinearLayout.LayoutParams(50,50));
Path pth=new Path();
pth.moveTo(rulerp1.getX()+rulerp1.getMeasuredWidth()/2,rulerp1.getY()+rulerp1.getMeasuredHeight()/2);
pth.lineTo(rulerp2.getX()+rulerp2.getMeasuredWidth()/2,rulerp2.getY()+rulerp2.getMeasuredHeight()/2);
			Paint pi = new Paint();
			pi.setStrokeWidth(4);
			pi.setColor(Color.BLACK);
			pi.setStyle(Paint.Style.STROKE);
			pi.setStrokeCap(Paint.Cap.ROUND);
pi.setStrokeJoin(Paint.Join.ROUND);
pi.setDither(true);
pi.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
cv.drawPath(pth,pi);
} else if((TYPE==TYPES.CIRCLE)&&showH){
Path pth = new Path();
Paint pi = new Paint();
			pi.setStrokeWidth(2);
			pi.setColor(Color.BLACK);
			pi.setStyle(Paint.Style.STROKE);
			pi.setStrokeCap(Paint.Cap.ROUND);
pi.setStrokeJoin(Paint.Join.ROUND);
pi.setDither(true);
pi.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
float xc= rulerp2.getX()+rulerp2.getMeasuredWidth()/2;
float yc=rulerp2.getY()+rulerp2.getMeasuredHeight()/2;
float tx1=(float)((rulerp1.getX()+rulerp1.getMeasuredWidth()/2)-xc);
			float tx2=(float)((rulerp1.getY()+rulerp1.getMeasuredHeight()/2)-yc);
float r =(float)Math.sqrt((tx1*tx1)+(tx2*tx2));
pth.addCircle(rulerp1.getX()+rulerp1.getMeasuredWidth()/2,rulerp1.getY()+rulerp1.getMeasuredHeight()/2,r,Path.Direction.CCW);
cv.drawPath(pth,pi);
}
} else {
rulerp1.setVisibility(View.INVISIBLE);
 rulerp2.setVisibility(View.INVISIBLE);
}
//end
}
public void ShowHelper(boolean b){
showH=b;
invalidate();
//not ready
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
	invalidate();
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
			if(!showH){
			pth.moveTo(event.getX()-40,event.getY());
			pth.lineTo(event.getX()+40,event.getY());
			pth.moveTo(event.getX(),event.getY()-40);
			pth.lineTo(event.getX(),event.getY()+40);
			pth.addCircle(event.getX(),event.getY(),30,Path.Direction.CCW);
			}
			PathS pa=new PathS(pth,pi,2,Color.BLACK);
			pz.add(pa);
			}
			path=new Path();
			rz.clear();
			mPaint = new Paint();
			mPaint.setStrokeWidth(strokeWidth);
			mPaint.setAntiAlias(true);
			mPaint.setStyle(style);
if(eraser){
	mPaint.setColor(Color.parseColor("#f4f4f4"));
	mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
	//mPaint.setAlpha(0x00);
	mPaint.setMaskFilter(null);
} else {
mPaint.setColor(pcolor);
mPaint.setStrokeCap(Paint.Cap.ROUND);
mPaint.setStrokeJoin(Paint.Join.ROUND);
mPaint.setDither(true);
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
			if(showH&&false){
			//its not ready
			float tx1=(float)((rulerp1.getX()+rulerp1.getMeasuredWidth()/2)-cx);
			float tx2=(float)((rulerp1.getY()+rulerp1.getMeasuredHeight()/2)-cy);
			float r =(float)Math.sqrt((tx1*tx1)+(tx2*tx2));
			path.addCircle(rulerp1.getX()+rulerp1.getMeasuredWidth()/2,rulerp1.getY()+rulerp1.getMeasuredHeight()/2,r,Path.Direction.CCW);
			} else {
			double xmx = Math.abs((double)(cx-xx));
	double ymy = Math.abs((double)(cy-yy));
	double r = Math.sqrt((xmx*xmx)+(ymy*ymy));
			path.addCircle(xx,yy,(float)r,Path.Direction.CCW);
			}
			} else {
			if(TYPE==TYPES.TRIANGLE1){
			path.reset();
			float dx=Math.abs(xx-cx);
			if(cx>xx){
			path.moveTo(xx,yy);
			path.lineTo(cx,yy);
			path.lineTo(xx+(dx/2),cy);
			path.lineTo(xx,yy);
			} else {
			path.moveTo(xx,yy);
			path.lineTo(cx,yy);
			path.lineTo(xx-(dx/2),cy);
			path.lineTo(xx,yy);
			}//end sq1
			} else {
			if(TYPE==TYPES.TRIANGLE2){
			path.reset();
			path.moveTo(xx,yy);
			path.lineTo(xx,cy);
			path.lineTo(cx,cy);
			path.lineTo(xx,yy);
			} else {
			if(TYPE==TYPES.LINE){
			path.reset();
			path.moveTo(xx,yy);
			if(showH){
			path.lineTo(cx-(xx-rulerp1.getX()),cy);
			} else {
			path.lineTo(cx,cy);
			}
			}
			}//end last
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