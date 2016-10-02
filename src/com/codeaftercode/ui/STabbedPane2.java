package com.codeaftercode.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;

public class STabbedPane2 extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	private Vector<Boolean> closable;
	private boolean showClose;
	private Color colorNorth = new Color(57, 181, 215);
	private Color colorSouth = new Color(145, 232, 255);
	private Color colorBorder = new Color(90, 154, 179);
	private int runPos = 1;

	/** 
	 * 构造方法
	 */
	public STabbedPane2() {
		super();
		initialize();
	}

	/** 
	 * 构造方法  
	 * @param arg0 该参数为true时，无论是否选中，都显示可关闭按钮， 
	 * 为false时，只有选中时才显示 
	 */
	public STabbedPane2(boolean arg0) {
		super();
		showClose = arg0;
		initialize();
	}

	/** * 初始化部分，将UI设置成STabbedPaneUI（自己实现的UI） */
	private void initialize() {
		closable = new Vector<Boolean>(0);
		try {
			ImageIO.read(getClass().getResource("/ico/bw.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setUI(new STabbedPaneUI());
	}

	/**
	 * 加入组件 
	 * @param title 标题
	 * @param icon 图标
	 * @param component 组件
	 * @param tip 提示信息
	 * @param closabel 是否可关闭
	 */
	public void addTab(String title, Icon icon, Component component, String tip, boolean closable) {
		addTab(title, icon, component, tip);
		this.closable.add(closable);
	}

	/** * 移除组件 * @param index 组件序号 */
	public void removeTab(int index) {
		super.removeTabAt(index);
		if (runPos >= getTabCount()) {
			runPos = 1;
		}
		closable.remove(index);
	}

	/** * 重写updateUI方法，防止在updateUI的时候被设置成默认的UI风格，造成异常 */
	@Override
	public void updateUI() {
		setUI((STabbedPaneUI) ui);
	}

	/** * 获得渐变的tab的顶部色彩 * @return 顶部色彩 */
	public Color getColorNorth() {
		return colorNorth;
	}

	/** * 设置渐变的tab的顶部色彩 */
	public void setColorNorth(Color colorNorth) {
		this.colorNorth = colorNorth;
	}

	/** * 获得渐变的tab的底部色彩 * @return 底部色彩 */
	public Color getColorSouth() {
		return colorSouth;
	}

	/** * 设置渐变的tab的底部色彩 */
	public void setColorSouth(Color colorSouth) {
		this.colorSouth = colorSouth;
	}

	/** * 获得tabbedPane的边框色彩 * @return 边框色彩 */
	public Color getColorBorder() {
		return colorBorder;
	}

	/** * 设置tabbedPane的边框色彩 */
	public void setColorBorder(Color colorBorder) {
		this.colorBorder = colorBorder;
	}

	/** * 设置tabbedPane的边框色彩 */
	public void setColorContent(Color color) {
		UIManager.put("TabbedPane.contentAreaColor", color);
	}

	/** * UI实现类 * @author Sun */
	class STabbedPaneUI extends BasicTabbedPaneUI {
		private Rectangle[] closeRects = new Rectangle[0];
		private Rectangle leftRect = new Rectangle();
		private Rectangle rightRect = new Rectangle();
		private int nowIndex = -1;
		private int oldIndex = -1;
		private boolean needBtn = false;

		public STabbedPaneUI() {
			super();
			initialize();
		}

		/** * UI类的初始化部分，为tab的关闭按钮和左右箭头按钮注册事件 */
		private void initialize() {
			UIManager.put("TabbedPane.contentAreaColor", colorSouth);
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					int tabCount = getTabCount();
					for (int i = 0; i < tabCount; i++) {
						Point p = e.getPoint();
						if (rightRect.contains(p)) {
							runPos++;
							if (runPos >= tabCount) {
								runPos = 1;
							}
							updateUI();
						} else if (leftRect.contains(p)) {
							runPos--;
							if (runPos < 1) {
								runPos = tabCount - 1;
							}
							updateUI();
						} else if (closeRects[i].contains(p)&& closable.get(i)) {
							removeTab(i);
						}
					}
				}
			});
			addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					nowIndex = -1;
					for (int i = 0; i < getTabCount(); i++) {
						if (closeRects[i].contains(e.getPoint())&& closable.get(i)) {
							nowIndex = i;break;
						}
					}
					if (oldIndex != nowIndex) {
						if (nowIndex != -1) {
							repaint(closeRects[nowIndex]);
							// 控制重绘区域
						} else {
							if (oldIndex < getTabCount()) {
								repaint(closeRects[oldIndex]);
								// 控制重绘区域
							}
						}
						oldIndex=nowIndex;
					}
				}
			});
		}

	/** * 重写的paintContentBorderTopEdge方法 * 负责绘制整个tabPane的顶部边框 */
	@Override
	protected void paintContentBorderTopEdge(Graphics g, int tabPlacement,int selectedIndex, int x, int y, int w, int h) {
		Rectangle selRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);
		g.setColor(colorBorder);
		if (tabPlacement != TOP || selectedIndex < 0|| (selRect.y + selRect.height + 1 < y)|| (selRect.x < x || selRect.x > x + w)) {
			g.drawLine(x, y, x + w - 2, y);
			} else {
				g.drawLine(x, y, selRect.x - 1, y);
				if (selRect.x + selRect.width < x + w - 2) {
					g.drawLine(selRect.x + selRect.width, y, x + w - 2, y);
				} else {
					g.drawLine(x + w - 2, y, x + w - 2, y);
			}
		}
	}

	/** * 重写的paintContentBorderLeftEdge方法 * 负责绘制整个tabPane的左侧边框 */
	@Override
	protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement,int selectedIndex, int x, int y, int w, int h) {
		Rectangle selRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);g.setColor(colorBorder);if (tabPlacement != LEFT || selectedIndex < 0|| (selRect.x + selRect.width + 1 < x)|| (selRect.y < y || selRect.y > y + h)) {
			g.drawLine(x, y, x, y + h - 2);
			} else {
				g.drawLine(x, y, x, selRect.y - 1);
			if (selRect.y + selRect.height < y + h - 2) {
				g.drawLine(x, selRect.y + selRect.height, x, y + h - 2);
			}
		}
	}

	/** * 重写的paintContentBorderBottomEdge方法 * 负责绘制整个tabPane的底部边框 */
	@Override
	protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement,int selectedIndex, int x, int y, int w, int h) {
		Rectangle selRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);
		g.setColor(colorBorder);
		if (tabPlacement != BOTTOM || selectedIndex < 0 || (selRect.y - 1 > h)|| (selRect.x < x || selRect.x > x + w)) {
			g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
		} else {
			g.drawLine(x, y + h - 1, selRect.x - 1, y + h - 1);
			if (selRect.x + selRect.width < x + w - 2) {
				g.drawLine(selRect.x + selRect.width, y + h - 1, x + w - 1, y+ h - 1);
			}
		}
	}

	/** * 重写的paintContentBorderLeftEdge方法 * 负责绘制整个tabPane的右侧边框 */
	@Override
	protected void paintContentBorderRightEdge(Graphics g, int tabPlacement,int selectedIndex, int x, int y, int w, int h) {
		Rectangle selRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);
		g.setColor(colorBorder);
		if (tabPlacement != RIGHT || selectedIndex < 0 || (selRect.x - 1 > w)|| (selRect.y < y || selRect.y > y + h)) {
			g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
		} else {
			g.drawLine(x + w - 1, y, x + w - 1, selRect.y - 1);
			if (selRect.y + selRect.height < y + h - 2) {
				g.drawLine(x + w - 1, selRect.y + selRect.height, x + w - 1, y+ h - 2);
			}
		}
	}

	/** * 重写的paintTab方法， * 在绘制了原有tab的基础上，绘制出关闭按钮以及左右箭头 */
	@Override
	protected void paintTab(Graphics g, int tabPlacement,Rectangle[] rects, int tabIndex, Rectangle iconRect,Rectangle textRect) {
		super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
		if (closable.get(tabIndex)&& (showClose || tabIndex == getSelectedIndex())) {
			paintCloseIcon(g, tabIndex, tabIndex == nowIndex);
		}
		if (scrollableTabLayoutEnabled() && needBtn) {
			paintLeftBtn(g);
			paintRightBtn(g);
		}
	}

	/** * 绘制右箭头的方法 * @param g */
	private void paintRightBtn(Graphics g) {
		g.setColor(colorSouth);
		g.fillRect(rightRect.x, rightRect.y, rightRect.width, rightRect.height);
		g.setColor(Color.GRAY);
		int[] xs = { rightRect.x + 12, rightRect.x + 5, rightRect.x + 5 };
		int[] ys = { rightRect.y + 8, rightRect.y + 5, rightRect.y + 11 };
		g.fillPolygon(xs, ys, 3);
		g.setColor(Color.DARK_GRAY);
		g.drawPolygon(xs, ys, 3);
		g.drawRect(rightRect.x, rightRect.y, rightRect.width, rightRect.height);
	}

	/** * 绘制左箭头的方法 * @param g */
	private void paintLeftBtn(Graphics g) {
		g.setColor(colorSouth);
		g.fillRect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);
		g.setColor(Color.GRAY);
		int[] xs = { leftRect.x + 5, leftRect.x + 11, leftRect.x + 11 };
		int[] ys = { leftRect.y + 8, leftRect.y + 5, leftRect.y + 11 };
		g.fillPolygon(xs, ys, 3);
		g.setColor(Color.DARK_GRAY);
		g.drawPolygon(xs, ys, 3);
		g.drawRect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);
	}

	/** * 重写的paintTabBorder方法 * 负责绘制tab的边框 */
	@Override
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
			boolean isSelected) {
		g.setColor(Color.GRAY);
		switch (tabPlacement) {
		case LEFT:
			g.drawLine(x, y, x, y + h - 1);
			g.drawLine(x, y, x + w - 1, y);
			g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
			break;
		case RIGHT:
			g.drawLine(x, y, x + w - 1, y);
			g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
			g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
			break;
		case BOTTOM:
			g.drawLine(x, y, x, y + h - 1);
			g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
			g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
			break;
		case TOP:
		default:
			g.drawLine(x, y, x, y + h - 1);
			g.drawLine(x, y, x + w - 1, y);
			g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
		}
	}

	/**
	 * * 重写的paintFocusIndicator * 因为原来的paintFocusIndicator方法会把选中的tab绘制一个虚框 *
	 * 个人不喜欢这个虚框，就用这个方法覆盖掉了
	 */
	@Override
	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
			Rectangle iconRect, Rectangle textRect, boolean isSelected) {
	}

	/**
	 * * 重写的paintTabBackground * 负责绘制tab的背景 * 在本UI中，使用了GradientPaint（渐变模式）来进行绘制
	 */
	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
			boolean isSelected) {
		GradientPaint gradient;
		Graphics2D g2d = (Graphics2D) g;
		switch (tabPlacement) {
		case LEFT:
			if (isSelected) {
				gradient = new GradientPaint(x + 1, y, colorNorth, x + w, y, colorSouth, true);
			} else {
				gradient = new GradientPaint(x + 1, y, Color.LIGHT_GRAY, x + w, y, Color.WHITE, true);
			}
			g2d.setPaint(gradient);
			g.fillRect(x + 1, y + 1, w - 1, h - 2);
			break;
		case RIGHT:
			if (isSelected) {
				gradient = new GradientPaint(x + w, y, colorNorth, x + 1, y, colorSouth, true);
			} else {
				gradient = new GradientPaint(x + w, y, Color.LIGHT_GRAY, x + 1, y, Color.WHITE, true);
			}
			g2d.setPaint(gradient);
			g.fillRect(x, y + 1, w - 1, h - 2);
			break;
		case BOTTOM:
			if (isSelected) {
				gradient = new GradientPaint(x + 1, y + h, colorNorth, x + 1, y, colorSouth, true);
			} else {
				gradient = new GradientPaint(x + 1, y + h, Color.LIGHT_GRAY, x + 1, y, Color.WHITE, true);
			}
			g2d.setPaint(gradient);
			g.fillRect(x + 1, y, w - 2, h - 1);
			break;
		case TOP:
		default:
			if (isSelected) {
				gradient = new GradientPaint(x + 1, y, colorNorth, x + 1, y + h, colorSouth, true);
			} else {
				gradient = new GradientPaint(x + 1, y, Color.LIGHT_GRAY, x + 1, y + h, Color.WHITE, true);
			}
			g2d.setPaint(gradient);
			g.fillRect(x + 1, y + 1, w - 2, h);
		}
	}

	/**
	 * * 绘制关闭按钮，在鼠标移进按钮的时候，内部以红色填充，平时为白色 * @param g Graphics * @param tabIndex
	 * tab的序号 * @param entered 鼠标是否在这个按钮上
	 */
	protected void paintCloseIcon(Graphics g, int tabIndex, boolean entered) {
		Rectangle rect = closeRects[tabIndex];
		int x = rect.x;
		int y = rect.y;
		int[] xs = { x, x + 2, x + 4, x + 5, x + 7, x + 9, x + 9, x + 7, x + 7, x + 9, x + 9, x + 7, x + 5, x + 4,
				x + 2, x, x, x + 2, x + 2, x };
		int[] ys = { y, y, y + 2, y + 2, y, y, y + 2, y + 4, y + 5, y + 7, y + 9, y + 9, y + 7, y + 7, y + 9, y + 9,
				y + 7, y + 5, y + 4, y + 2 };
		if (entered) {
			g.setColor(new Color(252, 160, 160));
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillPolygon(xs, ys, 20);
		g.setColor(Color.DARK_GRAY);
		g.drawPolygon(xs, ys, 20);
	}

	/** * 判断是否为TabbedPaneScrollLayout布局 * @return */
	private boolean scrollableTabLayoutEnabled() {
		return (tabPane.getLayout() instanceof TabbedPaneScrollLayout);
	}

	/**
	 * * 重写的layoutLabel方法 * 负责在绘制tab的文字，icon之前，计算这些文字，icon的相对位置 * 因为Basic类中的这个方法
	 * * 会在选中tab的时候把文字和icon进行一个提升效果的处理 * 我把这个效果给去掉了
	 */
	@Override
	protected void layoutLabel(int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon,
			Rectangle tabRect, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
		textRect.x = textRect.y = iconRect.x = iconRect.y = 0;
		View v = getTextViewForTab(tabIndex);
		if (v != null) {
			tabPane.putClientProperty("html", v);
		}
		SwingUtilities.layoutCompoundLabel((JComponent) tabPane, metrics, title, icon, SwingUtilities.CENTER,
				SwingUtilities.CENTER, SwingUtilities.CENTER, SwingUtilities.TRAILING, tabRect, iconRect, textRect,
				textIconGap);
		tabPane.putClientProperty("html", null);
	}

	/**
	 * * 重写的createLayoutManager方法 *
	 * 负责根据当前的tabLayoutPolicy属性（是否为SCROLL_TAB_LAYOUT） * 对tab设置一个正确的布局管理器
	 */
	@Override
	protected LayoutManager createLayoutManager() {
		if (tabPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT) {
			return new TabbedPaneScrollLayout();
		} else {
			return new TabbedPaneLayout();
		}
	}

	/**
	 * * 重写的assureRectsCreated方法 * 这个方法的目的是根据当前的tab的数量 * 动态更新rects这个数组的大小， *
	 * rects数组，就是记录tab的范围的Rectangle数组 * 现在我在这个的基础上增加了对closeRects数组的大小的动态更新 *
	 * closeRects数组，是我增加的用于记录tab的关闭按钮范围的Rectangle数组
	 */
	@Override
	protected void assureRectsCreated(int tabCount) {
		super.assureRectsCreated(tabCount);
		int rectArrayLen = closeRects.length;
		if (tabCount != rectArrayLen) {
			Rectangle[] tempRectArray = new Rectangle[tabCount];
			System.arraycopy(closeRects, 0, tempRectArray, 0, Math.min(rectArrayLen, tabCount));
			closeRects = tempRectArray;
			for (int rectIndex = rectArrayLen; rectIndex < tabCount; rectIndex++) {
				closeRects[rectIndex] = new Rectangle();
			}
		}
	}

	/** * 计算closeRects数组的范围 * （根据rects数组的范围而计算出来的） */
	private void calcCloseRects() {
		int tabCount = tabPane.getTabCount();
		for (int i = 0; i < tabCount; i++) {
			closeRects[i].x = rects[i].x + rects[i].width - 14;
			closeRects[i].y = rects[i].y + 6;
			closeRects[i].width = 10;
			closeRects[i].height = 10;
		}
	}

	/**
	 * * TabbedPaneLayout布局实现类 * 这个布局是在非SCROLL_TAB_LAYOUT模式下的tab布局 *
	 * 就是当tab的总宽度超出tabPane的宽度时，会把tab跳动到下一行的效果 * @author Sun
	 */
	public class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout {
		/** * 计算每个tab的具体范围 * 这个布局中的核心部分 */
		@Override
		protected void calculateTabRects(int tabPlacement, int tabCount) {
			FontMetrics metrics = getFontMetrics();
			Dimension size = tabPane.getSize();
			Insets insets = tabPane.getInsets();
			Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
			int fontHeight = metrics.getHeight();
			int selectedIndex = tabPane.getSelectedIndex();
			int tabRunOverlay;
			int i, j;
			int x, y;
			int returnAt;
			boolean verticalTabRuns = (tabPlacement == LEFT || tabPlacement == RIGHT);
			boolean leftToRight = true;
			switch (tabPlacement) {
			case LEFT:
				// 在原有的宽度基础上加了20用来放置关闭按钮
				maxTabWidth = calculateMaxTabWidth(tabPlacement) + 20;
				x = insets.left + tabAreaInsets.left;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);
				break;
			case RIGHT:
				// 在原有的宽度基础上加了20用来放置关闭按钮
				maxTabWidth = calculateMaxTabWidth(tabPlacement) + 20;
				x = size.width - insets.right - tabAreaInsets.right - maxTabWidth;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);
				break;
			case BOTTOM:
				maxTabHeight = calculateMaxTabHeight(tabPlacement);
				x = insets.left + tabAreaInsets.left;
				y = size.height - insets.bottom - tabAreaInsets.bottom - maxTabHeight;
				returnAt = size.width - (insets.right + tabAreaInsets.right);
				break;
			case TOP:
			default:
				maxTabHeight = calculateMaxTabHeight(tabPlacement);
				x = insets.left + tabAreaInsets.left;
				y = insets.top + tabAreaInsets.top;
				returnAt = size.width - (insets.right + tabAreaInsets.right);
				break;
			}
			tabRunOverlay = getTabRunOverlay(tabPlacement);
			runCount = 0;
			selectedRun = -1;
			if (tabCount == 0) {
				return;
			}
			selectedRun = 0;
			runCount = 1;
			Rectangle rect;
			for (i = 0; i < tabCount; i++) {
				rect = rects[i];
				if (!verticalTabRuns) {
					// Tabs on TOP or BOTTOM....
					if (i > 0) {
						rect.x = rects[i - 1].x + rects[i - 1].width;
					} else {
						tabRuns[0] = 0;
						runCount = 1;
						maxTabWidth = 0;
						rect.x = x;
					}
					// 在原有的宽度基础上加了20用来放置关闭按钮
					rect.width = calculateTabWidth(tabPlacement, i, metrics) + 20;
					maxTabWidth = Math.max(maxTabWidth, rect.width);
					if (rect.x != 2 + insets.left && rect.x + rect.width > returnAt) {
						if (runCount > tabRuns.length - 1) {
							expandTabRunsArray();
						}
						tabRuns[runCount] = i;
						runCount++;
						rect.x = x;
					}
					rect.y = y;
					rect.height = maxTabHeight/* - 2 */;
				} else {
					// Tabs on LEFT or RIGHT...
					if (i > 0) {
						rect.y = rects[i - 1].y + rects[i - 1].height;
					} else {
						tabRuns[0] = 0;
						runCount = 1;
						maxTabHeight = 0;
						rect.y = y;
					}
					rect.height = calculateTabHeight(tabPlacement, i, fontHeight);
					maxTabHeight = Math.max(maxTabHeight, rect.height);
					if (rect.y != 2 + insets.top && rect.y + rect.height > returnAt) {
						if (runCount > tabRuns.length - 1) {
							expandTabRunsArray();
						}
						tabRuns[runCount] = i;
						runCount++;
						rect.y = y;
					}
					rect.x = x;
					rect.width = maxTabWidth/* - 2 */;
				}
				if (i == selectedIndex) {
					selectedRun = runCount - 1;
				}
			}
			if (runCount > 1) {
				normalizeTabRuns(tabPlacement, tabCount, verticalTabRuns ? y : x, returnAt);
				selectedRun = getRunForTab(tabCount, selectedIndex);
				if (shouldRotateTabRuns(tabPlacement)) {
					rotateTabRuns(tabPlacement, selectedRun);
				}
			}
			for (i = runCount - 1; i >= 0; i--) {
				int start = tabRuns[i];
				int next = tabRuns[i == (runCount - 1) ? 0 : i + 1];
				int end = (next != 0 ? next - 1 : tabCount - 1);
				if (!verticalTabRuns) {
					for (j = start; j <= end; j++) {
						rect = rects[j];
						rect.y = y;
						rect.x += getTabRunIndent(tabPlacement, i);
					}
					if (shouldPadTabRun(tabPlacement, i)) {
						padTabRun(tabPlacement, start, end, returnAt);
					}
					if (tabPlacement == BOTTOM) {
						y -= (maxTabHeight - tabRunOverlay);
					} else {
						y += (maxTabHeight - tabRunOverlay);
					}
				} else {
					for (j = start; j <= end; j++) {
						rect = rects[j];
						rect.x = x;
						rect.y += getTabRunIndent(tabPlacement, i);
					}
					if (shouldPadTabRun(tabPlacement, i)) {
						padTabRun(tabPlacement, start, end, returnAt);
					}
					if (tabPlacement == RIGHT) {
						x -= (maxTabWidth - tabRunOverlay);
					} else {
						x += (maxTabWidth - tabRunOverlay);
					}
				}
			}
			if (!leftToRight && !verticalTabRuns) {
				int rightMargin = size.width - (insets.right + tabAreaInsets.right);
				for (i = 0; i < tabCount; i++) {
					rects[i].x = rightMargin - rects[i].x - rects[i].width;
				}
			}
			// 计算关闭按钮范围calcCloseRects();
		}
	}
	/**
			 * * TabbedPaneScrollLayout布局实现类 * 这个布局是在SCROLL_TAB_LAYOUT模式下的tab布局
			 * * 就是当tab的总宽度超出tabPane的宽度时，会出现左右调整按钮 * 这个类在Basic类中是私有的，说明官方不建议重写 *
			 * 不过我还是大胆的把它重写了，有bug的话请大家指出 * 目前只处理了tab在顶部和底部的模式 * tab在左侧和右侧的模式赞未处理
			 * * @author Sun
	 */
		private class TabbedPaneScrollLayout extends TabbedPaneLayout {
					/** * 计算每个tab的具体范围 * 这个布局中的核心部分 */
			@Override
			protected void calculateTabRects(int tabPlacement, int tabCount) {
				FontMetrics metrics = getFontMetrics();
				Dimension size = tabPane.getSize();
				Insets insets = tabPane.getInsets();
				Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
				int fontHeight = metrics.getHeight();
				int i;
				int x, y;
				boolean verticalTabRuns = (tabPlacement == LEFT || tabPlacement == RIGHT);
				boolean leftToRight = true;
				switch (tabPlacement) {
				case LEFT:
					maxTabWidth = calculateMaxTabWidth(tabPlacement) + 20;
					x = insets.left + tabAreaInsets.left;
					y = insets.top + tabAreaInsets.top;
					break;
				case RIGHT:
					maxTabWidth = calculateMaxTabWidth(tabPlacement) + 20;
					x = size.width - insets.right - tabAreaInsets.right - maxTabWidth;
					y = insets.top + tabAreaInsets.top;
					break;
				case BOTTOM:
					maxTabHeight = calculateMaxTabHeight(tabPlacement);
					x = insets.left + tabAreaInsets.left;
					y = size.height - insets.bottom - tabAreaInsets.bottom - maxTabHeight;
					break;
				case TOP:
				default:
					maxTabHeight = calculateMaxTabHeight(tabPlacement);
					x = insets.left + tabAreaInsets.left;
					y = insets.top + tabAreaInsets.top;
					break;
				}
				tabRunOverlay = getTabRunOverlay(tabPlacement);
				if (tabCount == 0) {
					return;
				}
				Rectangle rect;
				int j = runPos;
				int lastRect = 0;
				for (i = 0; i < tabCount; i++) {
					if (!verticalTabRuns) {
						// Tabs on TOP or BOTTOM....
						if (i == 0) {
							// 对0号tab的特优处理
							rect = rects[i];
							runCount = 1;
							maxTabWidth = 0;
							rect.x = x;
							rect.width = calculateTabWidth(tabPlacement, 0, metrics) + 20;
						} else {
							// 由偏移变量runPos的大小决定剩下的tab的顺序
							rect = rects[j];
							rect.x = rects[lastRect].x + rects[lastRect].width;
							lastRect = j;
							rect.width = calculateTabWidth(tabPlacement, j, metrics) + 20;
							j++;
							if (j >= tabCount) {
								j = 1;
							}
						}
						maxTabWidth = Math.max(maxTabWidth, rect.width);
						rect.y = y;
						rect.height = maxTabHeight/* - 2 */;
					} else {
						rect = rects[i];
						// Tabs on LEFT or RIGHT...
						if (i > 0) {
							rect.y = rects[i - 1].y + rects[i - 1].height;
						} else {
							runCount = 1;
							maxTabHeight = 0;
							rect.y = y;
						}
						rect.height = calculateTabHeight(tabPlacement, i, fontHeight);
						maxTabHeight = Math.max(maxTabHeight, rect.height);
						rect.x = x;
						rect.width = maxTabWidth/* - 2 */;
					}
				}
				if (!leftToRight && !verticalTabRuns) {
					int rightMargin = size.width - (insets.right + tabAreaInsets.right);
					for (i = 0; i < tabCount; i++) {
						rects[i].x = rightMargin - rects[i].x - rects[i].width;
					}
				}
				needBtn = false;
				// 将超出tabPane范围的tab给隐藏
				for (i = 1; i < rects.length; i++) {
					if (rects[i].x + rects[i].width > tabPane.getWidth() - 35) {
						rects[i].x = 0;
						rects[i].y = 0;
						rects[i].width = 0;
						rects[i].height = 0;
						// 是否需要显示左右按钮
						needBtn = true;
					}
				}
				// 计算关闭按钮的范围
				calcCloseRects();
				if (!needBtn) {
					// 不需要左右按钮时将其隐藏
					runPos = 1;
					rightRect.x = 0;
					rightRect.x = 0;
					rightRect.y = 0;
					rightRect.width = 0;
					rightRect.height = 0;
					leftRect.x = 0;
					leftRect.y = 0;
					leftRect.width = 0;
					leftRect.height = 0;
				} else {
					rightRect.x = tabPane.getWidth() - 18;
					rightRect.y = 5;
					rightRect.width = 16;
					rightRect.height = 16;
					leftRect.x = rightRect.x - 17;
					leftRect.y = 5;
					leftRect.width = 16;
					leftRect.height = 16;
				}
			}
		}
	}
}
//}}}}}}}}