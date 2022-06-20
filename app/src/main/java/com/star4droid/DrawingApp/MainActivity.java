package com.star4droid.DrawingApp;

import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Button;
import android.view.View;
import android.widget.AdapterView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;
import androidx.annotation.*;

public class MainActivity extends Activity {
	
	private String chosenColor = "";
	private String HEX = "";
	private String iniColor = "";
	private  DrawingView dv;
	
	private ArrayList<String> typesL = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear3;
	private LinearLayout linear2;
	private Spinner spinner1;
	private SeekBar seekbar1;
	private Button button5;
	private Button button4;
	private Button button3;
	private Button button2;
	private Button button1;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear3 = findViewById(R.id.linear3);
		linear2 = findViewById(R.id.linear2);
		spinner1 = findViewById(R.id.spinner1);
		seekbar1 = findViewById(R.id.seekbar1);
		button5 = findViewById(R.id.button5);
		button4 = findViewById(R.id.button4);
		button3 = findViewById(R.id.button3);
		button2 = findViewById(R.id.button2);
		button1 = findViewById(R.id.button1);
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 0) {
					dv.setDrawType(DrawingView.TYPES.PEN);
				}
				else {
					if (_position == 1) {
						dv.setDrawType(DrawingView.TYPES.SQUARE);
					}
					else {
						if (_position == 2) {
							dv.setDrawType(DrawingView.TYPES.CIRCLE);
						}
						else {
							if (_position == 3) {
								dv.setDrawType(DrawingView.TYPES.TRIANGLE1);
							}
							else {
								dv.setDrawType(DrawingView.TYPES.TRIANGLE2);
							}
						}
					}
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				dv.setPenSize((int)_progressValue+1);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				HEX = "#FFFFFFFF";
				iniColor = HEX;
				new ColorPickerPopup.Builder(MainActivity.this)
					.initialColor(Color.parseColor(iniColor))
					.enableAlpha(true)
					.okTitle("Set color")
					.cancelTitle("Cancel")
					.showIndicator(true)
					.showValue(true)
					.build()
					.show(new ColorPickerPopup.ColorPickerObserver() {
					
							@Override
							public void onColorPicked(int color) {
						
						
						        int a = Color.alpha(color);
						        int r = Color.red(color);
						        int g = Color.green(color);
						        int b = Color.blue(color);
						chosenColor = 
						String.format(Locale.getDefault(), "#%02X%02X%02X%02X", a, r, g, b);
						  
						
						HEX = chosenColor;
						dv.setColor(color);
						
					}
					
					@Override
							public void onCustomClicked() {
						//_customHex();
					}
							@Override
							public void onColor(int color, boolean fromUser) {
						 
							}
					});
				
			}
		});
		
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dv.redo();
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dv.undo();
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (dv.eraser) {
					button2.setText("Eraser");
				}
				else {
					button2.setText("Pen");
				}
				dv.eraser =(!(dv.eraser)); 
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dv.clear();
			}
		});
	}
	
	private void initializeLogic() {
		dv = new DrawingView(this);
		linear1.addView(dv);
		dv.setLayoutParams(new LinearLayout.LayoutParams((int) (ViewGroup.LayoutParams.MATCH_PARENT),(int) (ViewGroup.LayoutParams.MATCH_PARENT)));
		dv.setBackgroundColor(0xFFCFD8DC);
		typesL.add("PEN");
		typesL.add("SQUARE");
		typesL.add("CIRCLE");
		typesL.add("TRIANGLE1");
		typesL.add("TRIANGLE2");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, typesL));
	}
	
	public void _dd() {
		
	}
	
	
	public void _colorPickerPopup() {
	}
	public static class ColorPickerView extends LinearLayout implements ColorObservable {
		    private ColorWheelView colorWheelView;
		    private BrightnessSliderView brightnessSliderView;
		    private AlphaSliderView alphaSliderView;
		    private ColorObservable observableOnDuty;
		    private int initialColor = Color.WHITE;
		    private int sliderMargin;
		    private int sliderHeight;
		    public ColorPickerView(Context context) {
			        this(context, null);
			    }
		    public ColorPickerView(Context context, AttributeSet attrs) {
			        this(context, attrs, 0);
			    }
		    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
			        super(context, attrs, defStyleAttr);
			        setOrientation(VERTICAL);
			        //TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorPickerView);
			        boolean enableAlpha = true;
			        boolean enableBrightness = true;
			        //typedArray.recycle();
			        colorWheelView = new ColorWheelView(context);
			        float density = getResources().getDisplayMetrics().density;
			        int margin = (int) (8 * density);
			        sliderMargin = 2 * margin;
			        sliderHeight = (int) (24 * density);
			        {
				            LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				                    ViewGroup.LayoutParams.WRAP_CONTENT);
				            addView(colorWheelView, params);
				        }
			
			        {
				            setEnabledBrightness(enableBrightness);
				            setEnabledAlpha(enableAlpha);
				        }
			
			        setPadding(margin, 0, margin, margin);
			    }
		
		    @Override
		    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
			        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
			        /*
		if (BuildConfig.DEBUG) {
            Logger.d("maxWidth: %d, maxHeight: %d", maxWidth, maxHeight);
        }
        */
			
			        int desiredWidth = maxHeight - (getPaddingTop() + getPaddingBottom()) + (getPaddingLeft() + getPaddingRight());
			        if (brightnessSliderView != null) {
				            desiredWidth -= (sliderMargin + sliderHeight);
				        }
			        if (alphaSliderView != null){
				            desiredWidth -= (sliderMargin + sliderHeight);
				        }
					/*
        if (BuildConfig.DEBUG) {
            Logger.d("desiredWidth: %d", desiredWidth);
        }
		*/
			        int width = Math.min(maxWidth, desiredWidth);
			        int height = width - (getPaddingLeft() + getPaddingRight()) + (getPaddingTop() + getPaddingBottom());
			        if (brightnessSliderView != null) {
				            height += (sliderMargin + sliderHeight);
				        }
			        if (alphaSliderView != null) {
				            height += (sliderMargin + sliderHeight);
				        }
					/*
        if (BuildConfig.DEBUG) {
            Logger.d("width: %d, height: %d", width, height);
        }
        */
			        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.getMode(widthMeasureSpec)),
			                MeasureSpec.makeMeasureSpec(height, MeasureSpec.getMode(heightMeasureSpec)));
			    }
		
		    public void setInitialColor(int color) {
			        initialColor = color;
			        colorWheelView.setColor(color);
			    }
		
		    public void setEnabledBrightness(boolean enable) {
			        if (enable) {
				            if (brightnessSliderView == null) {
					                brightnessSliderView = new BrightnessSliderView(getContext());
					                LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, sliderHeight);
					                params.topMargin = sliderMargin;
					                addView(brightnessSliderView, 1, params);
					            }
				            brightnessSliderView.bind(colorWheelView);
				            updateObservableOnDuty();
				        } else {
				            if (brightnessSliderView != null) {
					                brightnessSliderView.unbind();
					                removeView(brightnessSliderView);
					                brightnessSliderView = null;
					            }
				            updateObservableOnDuty();
				        }
			
			        if (alphaSliderView != null) {
				            setEnabledAlpha(true);
				        }
			    }
		
		    public void setEnabledAlpha(boolean enable) {
			        if (enable) {
				            if (alphaSliderView == null) {
					                alphaSliderView = new AlphaSliderView(getContext());
					                LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
					                params.topMargin = sliderMargin;
					                addView(alphaSliderView, params);
					            }
				
				            ColorObservable bindTo = brightnessSliderView;
				            if (bindTo == null) {
					                bindTo = colorWheelView;
					            }
				            alphaSliderView.bind(bindTo);
				            updateObservableOnDuty();
				        } else {
				            if (alphaSliderView != null) {
					                alphaSliderView.unbind();
					                removeView(alphaSliderView);
					                alphaSliderView = null;
					            }
				            updateObservableOnDuty();
				        }
			    }
		
		    private void updateObservableOnDuty() {
			        if (observableOnDuty != null) {
				            for (ColorObserver observer: observers) {
					                observableOnDuty.unsubscribe(observer);
					            }
				        }
			
			        if (brightnessSliderView == null && alphaSliderView == null) {
				            observableOnDuty = colorWheelView;
				        } else {
				            if (alphaSliderView != null) {
					                observableOnDuty = alphaSliderView;
					            } else {
					                observableOnDuty = brightnessSliderView;
					            }
				        }
			
			        if (observers != null) {
				            for (ColorObserver observer : observers) {
					                observableOnDuty.subscribe(observer);
					                observer.onColor(observableOnDuty.getColor(), false);
					            }
				        }
			    }
		
		    public void reset() {
			        colorWheelView.setColor(initialColor);
			    }
		
		    List<ColorObserver> observers = new ArrayList<>();
		
		    @Override
		    public void subscribe(ColorObserver observer) {
			        observableOnDuty.subscribe(observer);
			        observers.add(observer);
			    }
		
		    @Override
		    public void unsubscribe(ColorObserver observer) {
			        observableOnDuty.unsubscribe(observer);
			        observers.remove(observer);
			    }
		
		    @Override
		    public int getColor() {
			        return observableOnDuty.getColor();
			    }
	}
	
	
	
	public static class ThrottledTouchEventHandler {
		    private int minInterval = Constants.EVENT_MIN_INTERVAL;
		    private Updatable updatable;
		    private long lastPassedEventTime = 0;
		    ThrottledTouchEventHandler(Updatable updatable) {
			        this(Constants.EVENT_MIN_INTERVAL, updatable);
			    }
		    ThrottledTouchEventHandler(int minInterval, Updatable updatable) {
			        this.minInterval = minInterval;
			        this.updatable = updatable;
			    }
		    void onTouchEvent(MotionEvent event) {
			        if (updatable == null) return;
			        long current = System.currentTimeMillis();
			        if (current - lastPassedEventTime <= minInterval) {
				            return;
				        }
			        lastPassedEventTime = current;
			        updatable.update(event);
			    }
	}
	public static class ColorWheelSelector extends View {
		    private Paint selectorPaint;
		    private float selectorRadiusPx = Constants.SELECTOR_RADIUS_DP * 3;
		    private PointF currentPoint = new PointF();
		    public ColorWheelSelector(Context context) {
			        this(context, null);
			    }
		    public ColorWheelSelector(Context context, AttributeSet attrs) {
			        this(context, attrs, 0);
			    }
		
		    public ColorWheelSelector(Context context, AttributeSet attrs, int defStyleAttr) {
			        super(context, attrs, defStyleAttr);
			
			        selectorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			        selectorPaint.setColor(Color.BLACK);
			        selectorPaint.setStyle(Paint.Style.STROKE);
			        selectorPaint.setStrokeWidth(2);
			    }
		
		    @Override
		    protected void onDraw(Canvas canvas) {
			        canvas.drawLine(currentPoint.x - selectorRadiusPx, currentPoint.y,
			                currentPoint.x + selectorRadiusPx, currentPoint.y, selectorPaint);
			        canvas.drawLine(currentPoint.x, currentPoint.y - selectorRadiusPx,
			                currentPoint.x, currentPoint.y + selectorRadiusPx, selectorPaint);
			        canvas.drawCircle(currentPoint.x, currentPoint.y, selectorRadiusPx * 0.66f, selectorPaint);
			    }
		
		    public void setSelectorRadiusPx(float selectorRadiusPx) {
			        this.selectorRadiusPx = selectorRadiusPx;
			    }
		
		    public void setCurrentPoint(PointF currentPoint) {
			        this.currentPoint = currentPoint;
			        invalidate();
			    }
	}
	public static class ColorWheelPalette extends View {
		    private float radius;
		    private float centerX;
		    private float centerY;
		    private Paint huePaint;
		    private Paint saturationPaint;
		    public ColorWheelPalette(Context context) {
			        this(context, null);
			    }
		    public ColorWheelPalette(Context context, AttributeSet attrs) {
			        this(context, attrs, 0);
			    }
		    public ColorWheelPalette(Context context, AttributeSet attrs, int defStyleAttr) {
			        super(context, attrs, defStyleAttr);
			        huePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			        saturationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			    }
		    @Override
		    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			        int netWidth = w - getPaddingLeft() - getPaddingRight();
			        int netHeight = h - getPaddingTop() - getPaddingBottom();
			        radius = Math.min(netWidth, netHeight) * 0.5f;
			        if (radius < 0) return;
			        centerX = w * 0.5f;
			        centerY = h * 0.5f;
			        Shader hueShader = new SweepGradient(centerX, centerY,
			                new int[]{Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED},
			                null);
			        huePaint.setShader(hueShader);
			
			        Shader saturationShader = new RadialGradient(centerX, centerY, radius,
			                Color.WHITE, 0x00FFFFFF, Shader.TileMode.CLAMP);
			        saturationPaint.setShader(saturationShader);
			    }
		    @Override
		    protected void onDraw(Canvas canvas) {
			        canvas.drawCircle(centerX, centerY, radius, huePaint);
			        canvas.drawCircle(centerX, centerY, radius, saturationPaint);
			    }
	}
	public static abstract class ColorSliderView extends View implements ColorObservable, Updatable {
		    protected int baseColor = Color.WHITE;
		    private Paint colorPaint;
		    private Paint borderPaint;
		    private Paint selectorPaint;
		    private Path selectorPath;
		    private Path currentSelectorPath = new Path();
		    protected float selectorSize;
		    protected float currentValue = 1f;
		    private ColorObservableEmitter emitter = new ColorObservableEmitter();
		    private ThrottledTouchEventHandler handler = new ThrottledTouchEventHandler(this);
		    public ColorSliderView(Context context) {
			        this(context, null);
			    }
		
		    public ColorSliderView(Context context, AttributeSet attrs) {
			        this(context, attrs, 0);
			    }
		
		    public ColorSliderView(Context context, AttributeSet attrs, int defStyleAttr) {
			        super(context, attrs, defStyleAttr);
			        colorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			        borderPaint.setStyle(Paint.Style.STROKE);
			        borderPaint.setStrokeWidth(0);
			        borderPaint.setColor(Color.BLACK);
			        selectorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			        selectorPaint.setColor(Color.BLACK);
			        selectorPath = new Path();
			        selectorPath.setFillType(Path.FillType.WINDING);
			    }
		
		    @Override
		    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			        configurePaint(colorPaint);
			        selectorPath.reset();
			        selectorSize = h * 0.25f;
			        selectorPath.moveTo(0, 0);
			        selectorPath.lineTo(selectorSize * 2, 0);
			        selectorPath.lineTo(selectorSize, selectorSize);
			        selectorPath.close();
			    }
		
		    @Override
		    protected void onDraw(Canvas canvas) {
			        float width = getWidth();
			        float height = getHeight();
			        canvas.drawRect(selectorSize, selectorSize, width - selectorSize, height, colorPaint);
			        canvas.drawRect(selectorSize, selectorSize, width - selectorSize, height, borderPaint);
			        selectorPath.offset(currentValue * (width - 2 * selectorSize), 0, currentSelectorPath);
			        canvas.drawPath(currentSelectorPath, selectorPaint);
			    }
		    @Override
		    public boolean onTouchEvent(MotionEvent event) {
			        int action = event.getActionMasked();
			        switch ( action ) {
				            case MotionEvent.ACTION_DOWN:
				            case MotionEvent.ACTION_MOVE:
				 
				               handler.onTouchEvent(event);
				                return true;
				            case MotionEvent.ACTION_UP:
				
				                update(event);
				                return true;
				        }
			        return super.onTouchEvent(event);
			    }
		
		    @Override
		    public void update(MotionEvent event) {
			        updateValue(event.getX());
			        emitter.onColor(assembleColor(), true);
			    }
		
		    void setBaseColor(int color, boolean fromUser) {
			        baseColor = color;
			        configurePaint(colorPaint);
			        if (!fromUser) {
				            // if not set by user (means programmatically), resolve currentValue from color value
				            currentValue = resolveValue(color);
				            emitter.onColor(color, false);
				        } else {
				            emitter.onColor(assembleColor(), true);
				        }
			        invalidate();
			    }
		
		    private void updateValue(float eventX) {
			        float left = selectorSize;
			        float right = getWidth() - selectorSize;
			        if (eventX < left) eventX = left;
			        if (eventX > right) eventX = right;
			        currentValue = (eventX - left) / (right - left);
			        invalidate();
			    }
		
		    protected abstract float resolveValue(int color);
		    protected abstract void configurePaint(Paint colorPaint);
		    protected abstract int assembleColor();
		
		    @Override
		    public void subscribe(ColorObserver observer) {
			        emitter.subscribe(observer);
			    }
		
		    @Override
		    public void unsubscribe(ColorObserver observer) {
			        emitter.unsubscribe(observer);
			    }
		
		    @Override
		    public int getColor() {
			        return emitter.getColor();
			    }
		
		    private ColorObserver bindObserver = new ColorObserver() {
			        @Override
			        public void onColor(int color, boolean fromUser) {
				            setBaseColor(color, fromUser);
				        }
			    };
		
		    private ColorObservable boundObservable;
		
		    public void bind(ColorObservable colorObservable) {
			        if (colorObservable != null) {
				            colorObservable.subscribe(bindObserver);
				            setBaseColor(colorObservable.getColor(), true);
				        }
			        boundObservable = colorObservable;
			    }
		
		    public void unbind() {
			        if (boundObservable != null) {
				            boundObservable.unsubscribe(bindObserver);
				            boundObservable = null;
				        }
			    }
	}
	public static class ColorPickerPopup {
		    private Context context;
		    private int initialColor;
		    private boolean enableBrightness;
		    private boolean enableAlpha;
		    private String okTitle;
		    private String cancelTitle;
		    private boolean showIndicator;
		    private boolean showValue;
		
		
		    private ColorPickerPopup(Builder builder) {
			        this.context = builder.context;
			        this.initialColor = builder.initialColor;
			        this.enableBrightness = builder.enableBrightness;
			        this.enableAlpha = builder.enableAlpha;
			        this.okTitle = builder.okTitle;
			        this.cancelTitle = builder.cancelTitle;
			        this.showIndicator = builder.showIndicator;
			        this.showValue = builder.showValue;
			
			    }
		    public void show(final ColorPickerObserver observer) {
			        show(null, observer);
			    }
		    public void show(View parent, final ColorPickerObserver observer) {
			        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
			        if (inflater == null) return;
			
			        final LinearLayout layout = new LinearLayout(context);
					layout.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
			layout.setGravity(Gravity.CENTER_VERTICAL);
			layout.setPadding(50,0,50,0);
			
			        final ColorPickerView colorPickerView = new ColorPickerView(context);
			colorPickerView.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
					
					
					final LinearLayout view_c = new LinearLayout(context);
					view_c.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
			view_c.setOrientation(LinearLayout.HORIZONTAL);
			view_c.setGravity(Gravity.CENTER_HORIZONTAL);
			view_c.setPadding(0,16,0,16);
					final TextView colorHex = new TextView(context);
					colorHex.setLayoutParams(new LinearLayout.LayoutParams(200,60));
			colorHex.setSingleLine(true);
			colorHex.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
					final LinearLayout colorIndicator = new LinearLayout(context);
			colorIndicator.setLayoutParams(new LinearLayout.LayoutParams(200, 60));
			colorIndicator.setOrientation(LinearLayout.HORIZONTAL);
					view_c.addView(colorHex,0);
					view_c.addView(colorIndicator);
					
					
					final LinearLayout view_b = new LinearLayout(context);
					view_b.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
			view_b.setOrientation(LinearLayout.HORIZONTAL);
			view_b.setBackgroundColor(Color.parseColor("#8DD500F9"));
					view_b.setPadding(24,16,24,16);
			
			final TextView custom = new TextView(context);
					custom.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
			custom.setText("Custom");
			custom.setTextColor(Color.WHITE);
			custom.setTextSize(14);
			//custom.setTypeface(null, Typeface.BOLD);
			
							final TextView cancel = new TextView(context);
					cancel.setLayoutParams(new LinearLayout.LayoutParams(140, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
			cancel.setTextColor(Color.WHITE);
			cancel.setTextSize(14);
			//cancel.setTypeface(null, Typeface.BOLD);
			
					final TextView ok = new TextView(context);
					ok.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
			ok.setTextColor(Color.WHITE);
			ok.setTextSize(14);
			ok.setPadding(16,0,0,0);
			//ok.setTypeface(null, Typeface.BOLD);
					view_b.addView(custom);
					view_b.addView(cancel);
			view_b.addView(ok);
			
					final LinearLayout linear1 = new LinearLayout(context);
					linear1.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
			linear1.setBackgroundColor(Color.WHITE);
			linear1.setOrientation(LinearLayout.VERTICAL);
			linear1.setPadding(0,16,0,0);
					linear1.addView(colorPickerView);
					linear1.addView(view_c);
					linear1.addView(view_b);
					layout.addView(linear1);
			
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor("#8F000000"));
			
			        final PopupWindow popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			popupWindow.setBackgroundDrawable(gd);
			popupWindow.setOutsideTouchable(true);
			        colorPickerView.setInitialColor(initialColor);
			colorPickerView.setEnabledBrightness(enableBrightness);
			colorPickerView.setEnabledAlpha(enableAlpha);
			colorPickerView.subscribe(observer);
			        custom.setOnClickListener(new View.OnClickListener() {
				            @Override
				            public void onClick(View v) {
					                popupWindow.dismiss();
					observer.onCustomClicked();
					            }
				        });
			
			        cancel.setText(cancelTitle);
			        cancel.setOnClickListener(new View.OnClickListener() {
				            @Override
				            public void onClick(View v) {
					                popupWindow.dismiss();
					            }
				        });
			        
			        ok.setText(okTitle);
			        ok.setOnClickListener(new View.OnClickListener() {
				            @Override
				            public void onClick(View v) {
					                popupWindow.dismiss();
					                if (observer != null) {
						                    observer.onColorPicked(colorPickerView.getColor());
						                }
					            }
				        });
			
			colorIndicator.setVisibility(showIndicator ? View.VISIBLE : View.GONE);
			        colorHex.setVisibility(showValue ? View.VISIBLE : View.GONE);
			
			        colorPickerView.subscribe(new ColorObserver() {
				            @Override
				            public void onColor(int color, boolean fromUser) {
					                if (showIndicator) {
						                    colorIndicator.setBackgroundColor(color);
						                }
					                if (showValue) {
						                    colorHex.setText(colorHex(color));
						                }
					            }
				        });
			
			        if(Build.VERSION.SDK_INT >= 21){
				            popupWindow.setElevation(10.0f);
				        }
			
			        //popupWindow.setAnimationStyle(R.style.TopDefaultsViewColorPickerPopupAnimation);
			        if (parent == null) parent = layout;
			        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
			    }
		
		    public static class Builder {
			
			        private Context context;
			        private int initialColor = Color.MAGENTA;
			        private boolean enableBrightness = true;
			        private boolean enableAlpha = false;
			        private String okTitle = "OK";
			        private String cancelTitle = "Cancel";
			        private boolean showIndicator = true;
			        private boolean showValue = true;
			
			        public Builder(Context context) {
				            this.context = context;
				        }
			
			        public Builder initialColor(int color) {
				            initialColor = color;
				            return this;
				        }
			
			        public Builder enableBrightness(boolean enable) {
				            enableBrightness = enable;
				            return this;
				        }
			
			
			        public Builder enableAlpha(boolean enable) {
				            enableAlpha = enable;
				            return this;
				        }
			
			        public Builder okTitle(String title) {
				            okTitle = title;
				            return this;
				        }
			
			        public Builder cancelTitle(String title) {
				            cancelTitle = title;
				            return this;
				        }
			
			        public Builder showIndicator(boolean show) {
				            showIndicator = show;
				            return this;
				        }
			
			        public Builder showValue(boolean show) {
				            showValue = show;
				            return this;
				        }
			
			        public ColorPickerPopup build() {
				            return new ColorPickerPopup(this);
				        }
			    }
		
		    private String colorHex(int color) {
			        int a = Color.alpha(color);
			        int r = Color.red(color);
			        int g = Color.green(color);
			        int b = Color.blue(color);
			        return String.format(Locale.getDefault(), "#%02X%02X%02X%02X", a, r, g, b);
			    }
		
		    public interface ColorPickerObserver extends ColorObserver {
			        void onColorPicked(int color);
			void onCustomClicked();
			    }
	}
	public static class ColorObservableEmitter implements ColorObservable {
		    private List<ColorObserver> observers = new ArrayList<>();
		    private int color;
		    @Override
		    public void subscribe(ColorObserver observer) {
			        if (observer == null) return;
			        observers.add(observer);
			    }
		    @Override
		    public void unsubscribe(ColorObserver observer) {
			        if (observer == null) return;
			        observers.remove(observer);
			    }
		    @Override
		    public int getColor() {
			        return color;
			    }
		    void onColor(int color, boolean fromUser) {
			        this.color = color;
			        for (ColorObserver observer : observers) {
				            observer.onColor(color, fromUser);
				        }
			    }
		
	}
	public static interface ColorObserver {
		    void onColor(int color, boolean fromUser);
	}
	public static interface ColorObservable {
		    void subscribe(ColorObserver observer);
		    void unsubscribe(ColorObserver observer);
		    int getColor();
	}
	public static class Constants {
		    static final int EVENT_MIN_INTERVAL = 1000 / 60; // 16ms
		    static final int SELECTOR_RADIUS_DP = 9;
	}
	public static interface Updatable {
		    void update(MotionEvent event);
	}
	public static class BrightnessSliderView extends ColorSliderView {
		    public BrightnessSliderView(Context context) {
			        super(context);
			    }
		    public BrightnessSliderView(Context context, AttributeSet attrs) {
			        super(context, attrs);
			    }
		    public BrightnessSliderView(Context context, AttributeSet attrs, int defStyleAttr) {
			        super(context, attrs, defStyleAttr);
			    }
		    @Override
		    protected float resolveValue(int color) {
			        float[] hsv = new float[3];
			        Color.colorToHSV(color, hsv);
			        return hsv[2];
			    }
		
		    protected void configurePaint(Paint colorPaint) {
			        float[] hsv = new float[3];
			        Color.colorToHSV(baseColor, hsv);
			        hsv[2] = 0;
			        int startColor = Color.HSVToColor(hsv);
			        hsv[2] = 1;
			        int endColor = Color.HSVToColor(hsv);
			        Shader shader = new LinearGradient(0, 0, getWidth(), getHeight(), startColor, endColor, Shader.TileMode.CLAMP);
			        colorPaint.setShader(shader);
			    }
		
		    protected int assembleColor() {
			        float[] hsv = new float[3];
			        Color.colorToHSV(baseColor, hsv);
			        hsv[2] = currentValue;
			        return Color.HSVToColor(hsv);
			    }
	}
	public static class AlphaSliderView extends ColorSliderView {
		    private Bitmap backgroundBitmap;
		    private Canvas backgroundCanvas;
		    public AlphaSliderView(Context context) {
			        super(context);
			    }
		    public AlphaSliderView(Context context, AttributeSet attrs) {
			        super(context, attrs);
			    }
		
		    public AlphaSliderView(Context context, AttributeSet attrs, int defStyleAttr) {
			        super(context, attrs, defStyleAttr);
			    }
		    @Override
		    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			        super.onSizeChanged(w, h, oldw, oldh);
			        backgroundBitmap = Bitmap.createBitmap((int) (w - 2 * selectorSize),
			                (int) (h - selectorSize), Bitmap.Config.ARGB_8888);
			        backgroundCanvas = new Canvas(backgroundBitmap);
			    }
		    @Override
		    protected void onDraw(Canvas canvas) {
			        android.graphics.drawable.Drawable drawable = CheckerboardDrawable.create();
			        drawable.setBounds(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
			        drawable.draw(backgroundCanvas);
			        canvas.drawBitmap(backgroundBitmap, selectorSize, selectorSize, null);
			        super.onDraw(canvas);
			    }
		    @Override
		    protected float resolveValue(int color) {
			        return Color.alpha(color) / 255.f;
			    }
		    protected void configurePaint(Paint colorPaint) {
			        float[] hsv = new float[3];
			        Color.colorToHSV(baseColor, hsv);
			        int startColor = Color.HSVToColor(0, hsv);
			        int endColor = Color.HSVToColor(255, hsv);
			        Shader shader = new LinearGradient(0, 0, getWidth(), getHeight(), startColor, endColor, Shader.TileMode.CLAMP);
			        colorPaint.setShader(shader);
			    }
		    protected int assembleColor() {
			        float[] hsv = new float[3];
			        Color.colorToHSV(baseColor, hsv);
			        int alpha = (int) (currentValue * 255);
			        return Color.HSVToColor(alpha, hsv);
			    }
	}
	public static class ColorWheelView extends FrameLayout implements ColorObservable, Updatable {
		    private float radius;
		    private float centerX;
		    private float centerY;
		    private float selectorRadiusPx = Constants.SELECTOR_RADIUS_DP * 3;
		    private PointF currentPoint = new PointF();
		    private int currentColor = Color.MAGENTA;
		    private ColorWheelSelector selector;
		    private ColorObservableEmitter emitter = new ColorObservableEmitter();
		    private ThrottledTouchEventHandler handler = new ThrottledTouchEventHandler(this);
		    public ColorWheelView(Context context) {
			        this(context, null);
			    }
		    public ColorWheelView(Context context, AttributeSet attrs) {
			        this(context, attrs, 0);
			    }
		
		    public ColorWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
			        super(context, attrs, defStyleAttr);
			        selectorRadiusPx = Constants.SELECTOR_RADIUS_DP * getResources().getDisplayMetrics().density;
			
			        {
				            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
				            ColorWheelPalette palette = new ColorWheelPalette(context);
				            int padding = (int) selectorRadiusPx;
				            palette.setPadding(padding, padding, padding, padding);
				            addView(palette, layoutParams);
				        }
			
			        {
				            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
				            selector = new ColorWheelSelector(context);
				            selector.setSelectorRadiusPx(selectorRadiusPx);
				            addView(selector, layoutParams);
				        }
			    }
		
		    @Override
		    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
			        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
			
			        int width, height;
			        width = height = Math.min(maxWidth, maxHeight);
			        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
			                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
			    }
		
		    @Override
		    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			        int netWidth = w - getPaddingLeft() - getPaddingRight();
			        int netHeight = h - getPaddingTop() - getPaddingBottom();
			        radius = Math.min(netWidth, netHeight) * 0.5f - selectorRadiusPx;
			        if (radius < 0) return;
			        centerX = netWidth * 0.5f;
			        centerY = netHeight * 0.5f;
			        setColor(currentColor);
			    }
		    @Override
		    public boolean onTouchEvent(MotionEvent event) {
			        int action = event.getActionMasked();
			        switch (action) {
				            case MotionEvent.ACTION_DOWN:
				            case MotionEvent.ACTION_MOVE:
				                handler.onTouchEvent(event);
				                return true;
				            case MotionEvent.ACTION_UP:
				                update(event);
				                return true;
				        }
			        return super.onTouchEvent(event);
			    }
		
		    @Override
		    public void update(MotionEvent event) {
			        float x = event.getX();
			        float y = event.getY();
			        emitter.onColor(getColorAtPoint(x, y), true);
			        updateSelector(x, y);
			    }
		
		    private int getColorAtPoint(float eventX, float eventY) {
			        float x = eventX - centerX;
			        float y = eventY - centerY;
			        double r = Math.sqrt(x * x + y * y);
			        float[] hsv = {0, 0, 1};
			        hsv[0] = (float) (Math.atan2(y, -x) / Math.PI * 180f) + 180;
			        hsv[1] = Math.max(0f, Math.min(1f, (float) (r / radius)));
			        return Color.HSVToColor(hsv);
			    }
		
		    public void setColor(int color) {
			        float[] hsv = new float[3];
			        Color.colorToHSV(color, hsv);
			        float r = hsv[1] * radius;
			        float radian = (float) (hsv[0] / 180f * Math.PI);
			        updateSelector((float) (r * Math.cos(radian) + centerX), (float) (-r * Math.sin(radian) + centerY));
			        currentColor = color;
			        emitter.onColor(color, false);
			    }
		
		    private void updateSelector(float eventX, float eventY) {
			        float x = eventX - centerX;
			        float y = eventY - centerY;
			        double r = Math.sqrt(x * x + y * y);
			        if (r > radius) {
				            x *= radius / r;
				            y *= radius / r;
				        }
			        currentPoint.x = x + centerX;
			        currentPoint.y = y + centerY;
			        selector.setCurrentPoint(currentPoint);
			    }
		
		    @Override
		    public void subscribe(ColorObserver observer) {
			        emitter.subscribe(observer);
			    }
		
		    @Override
		    public void unsubscribe(ColorObserver observer) {
			        emitter.unsubscribe(observer);
			    }
		
		    @Override
		    public int getColor() {
			        return emitter.getColor();
			    }
	}
	{
			/*
chosenColor = "";
HEX = "";
iniColor = "";
*/
	}
	public static class CheckerboardDrawable extends android.graphics.drawable.Drawable {
		    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    private int size;
		    private int colorOdd;
		    private int colorEven;
		    public static CheckerboardDrawable create() {
			        return new CheckerboardDrawable(new Builder());
			    }
		    private CheckerboardDrawable(Builder builder) {
			        this.size = builder.size;
			        this.colorOdd = builder.colorOdd;
			        this.colorEven = builder.colorEven;
			        configurePaint();
			    }
		    private void configurePaint() {
			        Bitmap bitmap = Bitmap.createBitmap(size * 2, size * 2, Bitmap.Config.ARGB_8888);
			        Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			        bitmapPaint.setStyle(Paint.Style.FILL);
			        Canvas canvas = new Canvas(bitmap);
			        Rect rect = new Rect(0, 0, size, size);
			        bitmapPaint.setColor(colorOdd);
			        canvas.drawRect(rect, bitmapPaint);
			        rect.offset(size, size);
			        canvas.drawRect(rect, bitmapPaint);
			        bitmapPaint.setColor(colorEven);
			        rect.offset(-size, 0);
			        canvas.drawRect(rect, bitmapPaint);
			        rect.offset(size, -size);
			        canvas.drawRect(rect, bitmapPaint);
			        paint.setShader(new BitmapShader(bitmap, BitmapShader.TileMode.REPEAT, BitmapShader.TileMode.REPEAT));
			    }
		    @Override
		    public void draw(Canvas canvas) {
			        canvas.drawPaint(paint);
			    }
		    @Override
		    public void setAlpha(int alpha) {
			        paint.setAlpha(alpha);
			    }
		    @Override
		    public void setColorFilter(ColorFilter colorFilter) {
			        paint.setColorFilter(colorFilter);
			    }
		    @Override
		    public int getOpacity() {
			        return PixelFormat.OPAQUE;
			    }
		    public static final class Builder {
			        private int size = 40;
			        private int colorOdd = 0xFFC2C2C2;
			        private int colorEven = 0xFFF3F3F3;
			        public Builder size(int size) {
				            this.size = size;
				            return this;
				        }
			        public Builder colorOdd(int color) {
				            colorOdd = color;
				            return this;
				        }
			        public Builder colorEven(int color) {
				            colorEven = color;
				            return this;
				        }
			        public CheckerboardDrawable build() {
				            return new CheckerboardDrawable(this);
				        }
			    }
	}
	{
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}