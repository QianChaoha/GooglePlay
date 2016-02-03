/**
 * 
 */
package com.example.googleplay.view;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Description: Company: guanghua
 * 
 * @author qianchao
 */
public class HuXingView extends View {
	private Paint mTextPaint;
	/** 中心圆上的文本 */
	private String content = "下载";
	private int textSize = 3;
	private Paint mArcPaint;
	private int mCircleXY;
	private float mCircleRadius;
	/** 圆弧与圆之间的间隔 */
	private int offset = 0;
	/** 圆弧的宽度 */
	private int mArcWidth = 8;
	private int textOffset;
	private float sweepAngle = 360;
	/** 获取文字长度 */
	private int textLength;
	private DecimalFormat decimalFormat;
	private boolean init = false;

	private int width, height;
	private int max = 360;

	/**
	 * @param context
	 */
	public HuXingView(Context context) {
		super(context);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public HuXingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public HuXingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void init() {
		decimalFormat = new DecimalFormat("0.0");// 保留一位小数
		mTextPaint = new Paint();
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		// 初始化圆弧paint
		mArcPaint = new Paint();
		mArcPaint.setAntiAlias(true);
		mArcPaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
		// 初始化圆弧paint
		mArcPaint.setStrokeWidth(mArcWidth);
		mArcPaint.setStyle(Style.STROKE);
		mTextPaint.setTextSize(20);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		// 获取圆心
		mCircleXY = ((width > height) ? height : width) / 2;
		// 半径
		mCircleRadius = (float) (mCircleXY - mArcWidth - offset);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mTextPaint.setColor(Color.GREEN);
		canvas.drawCircle(mCircleXY, mCircleXY, mCircleRadius, mTextPaint);
		// 文字的坐标以文字左上角为基准
		mTextPaint.setColor(Color.BLACK);
		textLength = getTextWidth(mTextPaint, content);
		textOffset = textLength / 2;
		canvas.drawText(content, 0, content.length(), mCircleXY - textOffset, mCircleXY + textLength / content.length() / 2, mTextPaint);
		// 绘制圆弧
		float leftTop = mCircleXY - mCircleRadius - offset - mArcWidth / 2;
		float rightBottom = mCircleXY + mCircleRadius + offset + mArcWidth / 2;
		RectF oval = new RectF(leftTop, leftTop, rightBottom, rightBottom);
		canvas.drawArc(oval, 0, sweepAngle, false, mArcPaint);
		// sweepAngle += 5;
		// if (sweepAngle <= 360) {
		// postInvalidateDelayed(50);
		// }
	}

	public void setProgress(int progress) {
		if (!init) {
			sweepAngle = 0;
			init = true;
			content = "0%";
		} else {
			sweepAngle = 360 * progress / max;
			content = decimalFormat.format(sweepAngle / max * 100) + "%";
			postInvalidate();
		}
	}

	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * 获取文字宽度
	 * 
	 * @param paint
	 * @param str
	 * @return
	 */
	public int getTextWidth(Paint paint, String str) {
		int iRet = 0;
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len];
			paint.getTextWidths(str, widths);
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);
			}
		}
		return iRet;
	}
}
