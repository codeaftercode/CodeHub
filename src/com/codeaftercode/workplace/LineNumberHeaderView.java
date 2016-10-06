package com.codeaftercode.workplace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.codeaftercode.common.Global;
/**
 * ����˵��: scrollPane --JScrollPane
 * ��ʾ�кţ�
	if (lineNumberHeader == null) {
	    lineNumberHeader = new LineNumberHeaderView();
	}
	scrollPane.setRowHeaderView(lineNumberHeader);
 * ����ʾ�кţ�
	scrollPane.setRowHeaderView(null);
 */
public class LineNumberHeaderView extends javax.swing.JComponent {

    /**
	 * �кŸ߶����ı����и߲�һ��,�ᵼ�������޷�����
	 * Ӧ�����кŸ߶�,����ı����и�һ��
	 */
	private static final long serialVersionUID = 1L;
	//private final  Font DEFAULT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 13);
    //public final Color DEFAULT_BACKGROUD = new Color(228, 228, 228);
    //public final Color DEFAULT_FOREGROUD = Color.BLACK;
	private final  Font DEFAULT_FONT = Global.lineNumber;		//������JTextArea����һ��,�����к��޷�����
	public final Color DEFAULT_BACKGROUD = Global.lineNumber_DEFAULT_BACKGROUD;
	public final Color DEFAULT_FOREGROUD = Global.LineNumber_DEFAULT_FOREGROUD;
    public final int nHEIGHT = Integer.MAX_VALUE - 1000000;
    public final int MARGIN = 5;
    private int lineHeight;										//�и�
    private int fontLineHeight;									//�����
    private int currentRowWidth;								//��ǰ�п�
    private FontMetrics fontMetrics;

    public LineNumberHeaderView() {
        setFont(DEFAULT_FONT);
        setForeground(DEFAULT_FOREGROUD);
        setBackground(DEFAULT_BACKGROUD);
        setPreferredSize(9999);
    }
    
    public LineNumberHeaderView(Font font) {
        setFont(font);
        setForeground(DEFAULT_FOREGROUD);
        setBackground(DEFAULT_BACKGROUD);
        setPreferredSize(9999);
    }

    public void setPreferredSize(int row) {
        int width = fontMetrics.stringWidth(String.valueOf(row));
        if (currentRowWidth < width) {
            currentRowWidth = width;
            setPreferredSize(new Dimension(2 * MARGIN + width + 1, nHEIGHT));
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
//        fontMetrics = getFontMetrics(getFont());
        fontMetrics = getFontMetrics(font);
        fontLineHeight = fontMetrics.getHeight();
    }

    public int getLineHeight() {
        if (lineHeight == 0) {
            return fontLineHeight;
        }
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        if (lineHeight > 0) {
            this.lineHeight = lineHeight;
        }
    }

    public int getStartOffset() {
        //���������к��ϱ߾�
    	//JTextArea��JTextPane���ò�ͬ,�˴�ҲӦ����
    	//Ӧ�����Ҳ��ı������ô˲���;
    	return 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int nlineHeight = getLineHeight();
        int startOffset = getStartOffset();
        Rectangle drawHere = g.getClipBounds();
        g.setColor(getBackground());
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
        g.setColor(getForeground());
        int startLineNum = (drawHere.y / nlineHeight) + 1;
        int endLineNum = startLineNum + (drawHere.height / nlineHeight);
        int start = (drawHere.y / nlineHeight) * nlineHeight + nlineHeight - startOffset;
        for (int i = startLineNum; i <= endLineNum; ++i) {
            String lineNum = String.valueOf(i);
            int width = fontMetrics.stringWidth(lineNum);
            g.drawString(lineNum + " ", MARGIN + currentRowWidth - width - 1, start);
            start += nlineHeight;
        }
        setPreferredSize(endLineNum);
    }
}