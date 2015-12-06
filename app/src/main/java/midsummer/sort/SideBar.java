package midsummer.sort;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 项目名称：Sort
 * 类描述：
 * 创建人：77.
 * 创建时间：2015/12/6 18:02
 * 修改人：77.
 * 修改时间：2015/12/6 18:02
 * 修改备注：
 * QQ：951203598
 */
public class SideBar extends View
{
	public static String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
	// 被选中字母的下标
	private int chooseIndex;
	private Paint paint = new Paint();
	private TextView mTextView;
	private OnLetterSelectedListener onLetterSelectedListener;
	
	public SideBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
	}
	
	public static String[] getChars()
	{
		return chars;
	}
	
	public static void setChars(String[] chars)
	{
		SideBar.chars = chars;
	}
	
	public int getChooseIndex()
	{
		return chooseIndex;
	}
	
	public void setChooseIndex(int chooseIndex)
	{
		this.chooseIndex = chooseIndex;
	}
	
	public OnLetterSelectedListener getOnLetterSelectedListener()
	{
		return onLetterSelectedListener;
	}
	
	public void setOnLetterSelectedListener(OnLetterSelectedListener onLetterSelectedListener)
	{
		this.onLetterSelectedListener = onLetterSelectedListener;
	}
	
	public Paint getPaint()
	{
		return paint;
	}
	
	public void setPaint(Paint paint)
	{
		this.paint = paint;
	}
	
	public TextView getTextView()
	{
		return mTextView;
	}
	
	public void setTextView(TextView pTextView)
	{
		mTextView = pTextView;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// 控件的宽
		int width = this.getWidth();
		// 控件的高
		int height = this.getHeight();
		// 每个字母的高度
		int singleHeight = height / chars.length;
		for (int i = 0; i < chars.length; i++)
		{
			paint.setColor(Color.rgb(33, 66, 99));
			// 粗体
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			// 抗锯齿
			paint.setAntiAlias(true);
			paint.setTextSize(50);
			if (i == chooseIndex)
			{
				paint.setColor(Color.parseColor("#3399ff"));
			}
			// 绘制文本
			float xPos = (width - paint.measureText(chars[i])) / 2;
			// 每个字母的Y坐标
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(chars[i], xPos, yPos, paint);
			paint.reset();
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		// 手指动作
		int action = event.getAction();
		// 获取手指Y坐标
		float y = event.getY();
		int oldChoose = chooseIndex;
		int c = (int) (y / this.getHeight() * chars.length);
		switch (action)
		{
			case MotionEvent.ACTION_UP:
				// 设置背景透明
				setBackground(new ColorDrawable(0x00000000));
				chooseIndex = -1;
				// 重绘
				invalidate();
				if (mTextView != null)
				{
					mTextView.setVisibility(View.INVISIBLE);
				}
				break;
			default:
				// 设置红色背景
				setBackgroundResource(R.drawable.sidebarr_background);
				// 根据y坐标获取点到的字母
				if (oldChoose != c)
				{
					if (c >= 0 && c < chars.length)
					{
						if (onLetterSelectedListener != null)
						{
							onLetterSelectedListener.onLetterSelected(chars[c]);
						}
						if (mTextView != null)
						{
							mTextView.setVisibility(View.VISIBLE);
							// 设置选中的字母
							mTextView.setText(chars[c]);
						}
					}
					chooseIndex = c;
					// 重绘
					invalidate();
				}
				break;
		}
		return true;
	}
	
	public interface OnLetterSelectedListener
	{
		void onLetterSelected(String s);
	}
}

