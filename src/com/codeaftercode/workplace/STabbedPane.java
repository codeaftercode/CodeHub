package com.codeaftercode.workplace;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;

import com.codeaftercode.common.Window;  
/**
 * 
 * @author http://blog.csdn.net/sunyiz/article/details/6068655
 * 添加组件的方法是：
 * public void addTab(String title, Icon icon, Component component, String tip, boolean closable)
 * 移除组件的方法是：
 * public void removeTabAt(int index)
 * 存在的问题：
 * 1.父类的tabLayoutPolicy参数不能定制，设置为JTabbedPane.SCROLL_TAB_LAYOUT不起作用。
 * public JTabbedPane(int tabPlacement, int tabLayoutPolicy)
 * 2.应禁用父类提供的一些方法,如removeAll()
 */
public class STabbedPane extends JTabbedPane {  
    private static final long serialVersionUID = 1L;  
    private Vector<Boolean> closable;
    private boolean showClose = false;	//该参数为true时，无论是否选中，都显示可关闭按钮;为false时，只有选中时才显示 
    private Color colorNorth = new Color(57, 181, 215);  
    private Color colorSouth = new Color(145, 232, 255);  
    private Color colorBorder = new Color(90, 154, 179);
    private Window window;
    /** 
     * 构造方法 
     */  
    public STabbedPane(Window window) {  
        super();  
        this.window = window;
        initialize();  
    }
  
    private void initialize() {  
        closable = new Vector<Boolean>(0);  
        setUI(new STabbedPaneUI());  
    }
    
    
    /**
     * 新建纯文本文件并打开
     */
    public void newScrollTextPane() {
    	window.setNewFileCounter(window.getNewFileCounter() + 1);
		String title = "新的文档" + window.getNewFileCounter() + ".txt";
		ScrollTextPane scrollTextPane = new ScrollTextPane(null,this);
		// 新建选项卡
		addTab(title, null, scrollTextPane, title, true);
		setSelectedComponent(scrollTextPane);
    }
    
    
    /**
     * 打开文件
     */
    public void openFile() {
    	// 选择文件
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 只能选择文件,不能选文件夹
		jfc.showOpenDialog(window);
		File file = jfc.getSelectedFile();

		// 取消打开操作
		if (file == null || !file.isFile()) {
			return;
		}

		// 选择的文件已经打开,则不要重复打开,只需切换到此选项卡即可
		int count = getComponentCount();
		for (int i = 0; i < count; i++) {
			ScrollTextPane scrollTextPane = (ScrollTextPane)getComponentAt(i);
			if (scrollTextPane.getFile() != null && scrollTextPane.getFile().equals(file)) {
				setSelectedComponent(scrollTextPane);
				return;
			}
		}

		// 创建文本显示容器,读取文件由该容器负责
		ScrollTextPane scrollTextPane = new ScrollTextPane(file, this);
		// 配置选项卡,切换到新选项卡
		addTab(file.getName(), null, scrollTextPane, file.getName(), true);
		setSelectedComponent(scrollTextPane);
    }
  
    /**
     * 获取索引
     * @param scrollTextPane
     * @return 返回值为-1表示传入的组件不在该容器内
     */
    public int getTabIndex(ScrollTextPane scrollTextPane) {
    	int index = this.getTabCount()-1;
    	while(index > -1) {
    		if(this.getComponentAt(index).equals(scrollTextPane)) {
    			break;
    		}
    		index--;
    	}
    	return index;
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
  
    /** 
     * 移除组件
     * @param index 组件序号 
     */  
    public void removeTabAt(int index) {  
    	ScrollTextPane scrollTextPane = (ScrollTextPane)this.getComponentAt(index);
    	// 检查文本内容是否被修改,未修改直接关闭;已修改提示是否保存或取消退出操作
    	if(scrollTextPane.isChanged()) {
    		// 文本内容已修改,提示:保存/不保存/取消
    		String message = "是否保存 " + this.getTitleAt(index) + " 文件的更改?";
    		int result = JOptionPane.showOptionDialog(window, message, null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    		switch(result) {
    		case JOptionPane.YES_OPTION:
    			// 保存
    			if(!scrollTextPane.saveFile()) {
    				// 保存未完成,取消关闭操作,直接返回
    				return;
    			}
    			break;
    		case JOptionPane.NO_OPTION:
    			// 不保存,直接关闭
    			break;
    		case JOptionPane.CANCEL_OPTION:
    			// 取消关闭操作,直接返回
    			return;
    		default:
    			break;
    		}
    	}
    	super.removeTabAt(index);  
        closable.remove(index);  
        // 如果全部关闭,则清空状态栏信息
        if(getTabCount() < 1) {
        	Window.statusBar.getLabel().setText("");
        	Window.statusBar.getCharset().setText("");
        }
    }
    
    public void removeAll() {
    	int count = getTabCount();
		while(count-- > 0) {
			setSelectedIndex(0);
			removeTabAt(0);
		}
    }
    
    public void saveSelectedTab() {
    	// 保存
    	if(getTabCount() < 1) {
    		return;
    	}
    	ScrollTextPane scrollTextPane = (ScrollTextPane)getSelectedComponent();
    	scrollTextPane.saveFile();
    }

	public void saveSelectedTabAs() {
		// 另存为
		if(getTabCount() < 1) {
			return;
		}
		ScrollTextPane scrollTextPane = (ScrollTextPane)getSelectedComponent();
    	scrollTextPane.saveFileAs();
	}
	
	public void saveAllTab() {
		// 全部保存
		int count = getTabCount();
		for(int index = 0;index < count;index++) {
			ScrollTextPane scrollTextPane = (ScrollTextPane)getComponentAt(index);
			scrollTextPane.saveFile();
		}
		
	}  
    
	public void reloadCharset() {
		if(getTabCount() < 1) {
			return;
		}
		ScrollTextPane scrollTextPane = (ScrollTextPane)getSelectedComponent();
		scrollTextPane.reloadCharset();
	}


    
  
    /** 
     * 获得渐变的tab的顶部色彩 
     * @return 顶部色彩 
     */  
    public Color getColorNorth() {  
        return colorNorth;  
    }  
  
    /** 
     * 设置渐变的tab的顶部色彩 
     */  
    public void setColorNorth(Color colorNorth) {  
        this.colorNorth = colorNorth;  
    }  
  
    /** 
     * 获得渐变的tab的底部色彩 
     * @return 底部色彩 
     */  
    public Color getColorSouth() {  
        return colorSouth;  
    }  
  
    /** 
     * 设置渐变的tab的底部色彩 
     */  
    public void setColorSouth(Color colorSouth) {  
        this.colorSouth = colorSouth;  
    }  
  
    /** 
     * 获得tabbedPane的边框色彩 
     * @return 边框色彩 
     */  
    public Color getColorBorder() {  
        return colorBorder;  
    }  
  
    /** 
     * 设置tabbedPane的边框色彩 
     */  
    public void setColorBorder(Color colorBorder) {  
        this.colorBorder = colorBorder;  
    }  
  
    /** 
     * UI实现类 
     * @author Sun 
     */  
    class STabbedPaneUI extends BasicTabbedPaneUI {  
        private Rectangle[] closeRects = new Rectangle[0];  
        private int nowIndex = -1;  
        //private int oldIndex = -1;  
  
        public STabbedPaneUI() {  
            super();  
            initialize();  
        }  
          
        private void initialize() {  
            UIManager.put("TabbedPane.contentAreaColor", colorSouth);  
            
            // 鼠标单击事件
            addMouseListener(new MouseAdapter() {  
                public void mousePressed(MouseEvent e) {  
                    for (int i = 0; i < getTabCount(); i++) {
                    	// 关闭选项卡
                        if (closeRects[i].contains(e.getPoint()) && closable.get(i)) {  
                            removeTabAt(i);  
                        }
                    }
                }
            });
            
            // 切换选项卡事件
            addChangeListener(new ChangeListener() {
            	 public void stateChanged(ChangeEvent e) {
            		 STabbedPane sTabbedPane = (STabbedPane)e.getSource();
            		 ScrollTextPane scrollTextPane = (ScrollTextPane)sTabbedPane.getSelectedComponent();
            		 if(scrollTextPane != null) {
            			 Window.statusBar.getLabel().setText("");
            			 Window.statusBar.getCharset().setText(scrollTextPane.getCharset());
            		 }
            	 }
            });
           /* //注册鼠标移动事件--不知何用
            addMouseMotionListener(new MouseAdapter() {  
                public void mouseMoved(MouseEvent e) {  
                    //获取鼠标所在的选项卡索引
                	nowIndex = -1; 
                	int tabCounter = getTabCount();
                	//如果选项卡数<2,则返回.(不加这句会报错)
                	if(tabCounter < 2) {
                		return;
                	}
                    for (int i = 0; i < tabCounter; i++) {
                    	//如果没有上述判断,在第1次打开文件且工作区为空时,下面的if条件会报错
                    	//现在还是会报错
                    	//"AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: 0
                        if (closeRects[i].contains(e.getPoint()) && closable.get(i)) {
                            nowIndex = i;
                            break;  
                        }
                    }  
                    if (oldIndex != nowIndex) {  
                        if (nowIndex != -1) {  
                            repaint(closeRects[nowIndex]);// 控制重绘区域  
                        } else {  
                            if (oldIndex < tabCounter) {  
                                repaint(closeRects[oldIndex]);// 控制重绘区域  
                            }  
                        }  
                        oldIndex = nowIndex;  
                    }
                }  
            }); */ 
        }  
          
        @Override  
        protected void paintContentBorderTopEdge(Graphics g, int tabPlacement,  
                int selectedIndex, int x, int y, int w, int h) {  
            Rectangle selRect = selectedIndex < 0 ? null : getTabBounds(  
                    selectedIndex, calcRect);  
            g.setColor(colorBorder);  
            if (tabPlacement != TOP || selectedIndex < 0  
                    || (selRect.y + selRect.height + 1 < y)  
                    || (selRect.x < x || selRect.x > x + w)) {  
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
          
        @Override  
        protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement,  
                int selectedIndex, int x, int y, int w, int h) {  
            Rectangle selRect = selectedIndex < 0 ? null : getTabBounds(  
                    selectedIndex, calcRect);  
            g.setColor(colorBorder);  
            if (tabPlacement != LEFT || selectedIndex < 0  
                    || (selRect.x + selRect.width + 1 < x)  
                    || (selRect.y < y || selRect.y > y + h)) {  
                g.drawLine(x, y, x, y + h - 2);  
            } else {  
                g.drawLine(x, y, x, selRect.y - 1);  
                if (selRect.y + selRect.height < y + h - 2) {  
                    g.drawLine(x, selRect.y + selRect.height, x, y + h - 2);  
                }  
            }  
        }  
          
        @Override  
        protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement,  
                int selectedIndex, int x, int y, int w, int h) {  
            Rectangle selRect = selectedIndex < 0 ? null : getTabBounds(  
                    selectedIndex, calcRect);  
            g.setColor(colorBorder);  
            if (tabPlacement != BOTTOM || selectedIndex < 0 || (selRect.y - 1 > h)  
                    || (selRect.x < x || selRect.x > x + w)) {  
                g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);  
            } else {  
                g.drawLine(x, y + h - 1, selRect.x - 1, y + h - 1);  
                if (selRect.x + selRect.width < x + w - 2) {  
                    g.drawLine(selRect.x + selRect.width, y + h - 1, x + w - 1, y  
                            + h - 1);  
                }  
            }  
        }  
  
        @Override  
        protected void paintContentBorderRightEdge(Graphics g, int tabPlacement,  
                int selectedIndex, int x, int y, int w, int h) {  
            Rectangle selRect = selectedIndex < 0 ? null : getTabBounds(  
                    selectedIndex, calcRect);  
            g.setColor(colorBorder);  
            if (tabPlacement != RIGHT || selectedIndex < 0 || (selRect.x - 1 > w)  
                    || (selRect.y < y || selRect.y > y + h)) {  
                g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);  
            } else {  
                g.drawLine(x + w - 1, y, x + w - 1, selRect.y - 1);  
                if (selRect.y + selRect.height < y + h - 2) {  
                    g.drawLine(x + w - 1, selRect.y + selRect.height, x + w - 1, y  
                            + h - 2);  
                }  
            }  
        }  
  
        @Override  
        protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect) {  
            super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
            if (tabIndex > -1 && closable.get(tabIndex) && (showClose || tabIndex == getSelectedIndex())) {  
                paintCloseIcon(g, tabIndex, tabIndex == nowIndex);  
            }  
        }  
          
        @Override  
        protected void paintTabBorder(Graphics g, int tabPlacement,  
                int tabIndex, int x, int y, int w, int h, boolean isSelected ) {  
            g.setColor(Color.GRAY);  
            switch (tabPlacement) {  
              case LEFT:  
                  g.drawLine(x, y, x, y+h-1);  
                  g.drawLine(x, y, x+w-1, y);  
                  g.drawLine(x, y+h-1, x+w-1, y+h-1);  
                  break;  
              case RIGHT:  
                  g.drawLine(x, y, x+w-1, y);  
                  g.drawLine(x, y+h-1, x+w-1, y+h-1);  
                  g.drawLine(x+w-1, y, x+w-1, y+h-1);  
                  break;                
              case BOTTOM:  
                  g.drawLine(x, y, x, y+h-1);  
                  g.drawLine(x+w-1, y, x+w-1, y+h-1);  
                  g.drawLine(x, y+h-1, x+w-1, y+h-1);  
                  break;  
              case TOP:  
              default:             
                  g.drawLine(x, y, x, y+h-1);  
                  g.drawLine(x, y, x+w-1, y);  
                  g.drawLine(x+w-1, y, x+w-1, y+h-1);  
            }  
        }  
  
        @Override  
        protected void paintFocusIndicator(Graphics g, int tabPlacement,  
                Rectangle[] rects, int tabIndex,Rectangle iconRect, Rectangle textRect, boolean isSelected) {
            
        }
  
        @Override  
        protected void paintTabBackground(Graphics g, int tabPlacement,  
                int tabIndex, int x, int y, int w, int h, boolean isSelected) {  
            GradientPaint gradient;  
            Graphics2D g2d = (Graphics2D)g;  
            switch(tabPlacement) {  
            case LEFT:  
                if (isSelected) {  
                    gradient = new GradientPaint(x+1, y, colorNorth,   
                            x+w, y, colorSouth, true);  
                } else {  
                    gradient = new GradientPaint(x+1, y, Color.LIGHT_GRAY,   
                            x+w, y, Color.WHITE, true);  
                }  
                g2d.setPaint(gradient);  
                g.fillRect(x+1, y+1, w-1, h-2);  
                break;  
            case RIGHT:  
                if (isSelected) {  
                    gradient = new GradientPaint(x+w, y, colorNorth,   
                            x+1, y, colorSouth, true);  
                } else {  
                    gradient = new GradientPaint(x+w, y, Color.LIGHT_GRAY,   
                            x+1, y, Color.WHITE, true);  
                }  
                g2d.setPaint(gradient);  
                g.fillRect(x, y+1, w-1, h-2);  
                break;  
            case BOTTOM:  
                if (isSelected) {  
                    gradient = new GradientPaint(x+1, y+h, colorNorth,   
                            x+1, y, colorSouth, true);  
                } else {  
                    gradient = new GradientPaint(x+1, y+h, Color.LIGHT_GRAY,   
                            x+1, y, Color.WHITE, true);  
                }  
                g2d.setPaint(gradient);  
                g.fillRect(x+1, y, w-2, h-1);  
                break;  
            case TOP:  
            default:  
                if (isSelected) {  
                    gradient = new GradientPaint(x+1, y, colorNorth,   
                            x+1, y+h, colorSouth, true);  
                } else {  
                    gradient = new GradientPaint(x+1, y, Color.LIGHT_GRAY,   
                            x+1, y+h, Color.WHITE, true);  
                }  
                g2d.setPaint(gradient);  
                g2d.fillRect(x+1, y+1, w-2, h-1);  
            }  
        }  
  
        private void paintCloseIcon(Graphics g, int tabIndex, boolean entered) {  
            Rectangle rect = closeRects[tabIndex];  
            int x = rect.x;  
            int y = rect.y;  
            int[] xs = { x, x + 2, x + 4, x + 5, x + 7, x + 9, x + 9, x + 7,  
                    x + 7, x + 9, x + 9, x + 7, x + 5, x + 4, x + 2, x, x,  
                    x + 2, x + 2, x };  
            int[] ys = { y, y, y + 2, y + 2, y, y, y + 2, y + 4, y + 5, y + 7,  
                    y + 9, y + 9, y + 7, y + 7, y + 9, y + 9, y + 7, y + 5,  
                    y + 4, y + 2 };  
            if (entered) {  
                g.setColor(new Color(252, 160, 160));  
            } else {  
                g.setColor(Color.WHITE);  
            }  
            g.fillPolygon(xs, ys, 20);  
            g.setColor(Color.DARK_GRAY);  
            g.drawPolygon(xs, ys, 20);  
        }  
  
        @Override  
        protected void layoutLabel(int tabPlacement, FontMetrics metrics,  
                int tabIndex, String title, Icon icon, Rectangle tabRect,  
                Rectangle iconRect, Rectangle textRect, boolean isSelected) {  
            textRect.x = textRect.y = iconRect.x = iconRect.y = 0;  
            View v = getTextViewForTab(tabIndex);  
            if (v != null) {  
                tabPane.putClientProperty("html", v);  
            }  
            SwingUtilities.layoutCompoundLabel(  
                    (JComponent) tabPane,  
                    metrics, title, icon,   
                    SwingUtilities.CENTER,   
                    SwingUtilities.CENTER,  
                    SwingUtilities.CENTER,   
                    SwingUtilities.TRAILING,   
                    tabRect, iconRect, textRect, textIconGap);  
            tabPane.putClientProperty("html", null);  
        }  
  
        @Override  
        protected LayoutManager createLayoutManager() {  
            return new TabbedPaneLayout();  
        }  
  
        @Override  
        protected void assureRectsCreated(int tabCount) {  
            super.assureRectsCreated(tabCount);  
            int rectArrayLen = closeRects.length;  
            if (tabCount != rectArrayLen) {  
                Rectangle[] tempRectArray = new Rectangle[tabCount];  
                System.arraycopy(closeRects, 0, tempRectArray, 0, Math.min(  
                        rectArrayLen, tabCount));  
                closeRects = tempRectArray;  
                for (int rectIndex = rectArrayLen; rectIndex < tabCount; rectIndex++) {  
                    closeRects[rectIndex] = new Rectangle();  
                }  
            }  
        }  
  
        class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout {  
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
                    maxTabWidth = calculateMaxTabWidth(tabPlacement) + 20;  
                    x = insets.left + tabAreaInsets.left;  
                    y = insets.top + tabAreaInsets.top;  
                    returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);  
                    break;  
                case RIGHT:  
                    maxTabWidth = calculateMaxTabWidth(tabPlacement) + 20;  
                    x = size.width - insets.right - tabAreaInsets.right  
                            - maxTabWidth;  
                    y = insets.top + tabAreaInsets.top;  
                    returnAt = size.height - (insets.bottom + tabAreaInsets.bottom);  
                    break;  
                case BOTTOM:  
                    maxTabHeight = calculateMaxTabHeight(tabPlacement);  
                    x = insets.left + tabAreaInsets.left;  
                    y = size.height - insets.bottom - tabAreaInsets.bottom  
                            - maxTabHeight;  
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
                        rect.width = calculateTabWidth(tabPlacement, i, metrics) + 20;  
                        maxTabWidth = Math.max(maxTabWidth, rect.width);  
                        if (rect.x != 2 + insets.left  
                                && rect.x + rect.width > returnAt) {  
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
                        rect.height = calculateTabHeight(tabPlacement, i,  
                                fontHeight);  
                        maxTabHeight = Math.max(maxTabHeight, rect.height);  
                        if (rect.y != 2 + insets.top  
                                && rect.y + rect.height > returnAt) {  
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
                    normalizeTabRuns(tabPlacement, tabCount, verticalTabRuns? y : x, returnAt);  
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
                    int rightMargin = size.width  
                            - (insets.right + tabAreaInsets.right);  
                    for (i = 0; i < tabCount; i++) {  
                        rects[i].x = rightMargin - rects[i].x - rects[i].width;  
                    }  
                }  
                for (i = 0; i < tabCount; i++) {  
                    closeRects[i].x = rects[i].x + rects[i].width - 14;  
                    closeRects[i].y = rects[i].y + 6;  
                    closeRects[i].width = 10;  
                    closeRects[i].height = 10;  
                }  
            }  
        }  
    }

}