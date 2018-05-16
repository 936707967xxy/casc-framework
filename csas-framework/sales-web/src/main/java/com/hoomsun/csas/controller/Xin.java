package com.hoomsun.csas.controller;

import java.awt.*;

import javax.swing.JFrame;

public class Xin extends JFrame {

	private static final long serialVersionUID = 5201314;
	// 获取屏幕大小
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static int WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static int WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

	public Xin() {
		// 设置窗口标题，嘿嘿~  
		super("I love you");
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		// 设置窗口位置--
		this.setLocation((WINDOW_WIDTH - WIDTH) / 2, (WINDOW_HEIGHT - HEIGHT) / 2);
		// 设置窗口大小--
		this.setSize(WIDTH, HEIGHT);
		// 设置窗口布局 --
		this.setLayout(getLayout());
		// 设置窗口可见 --
		this.setVisible(true);
		// 设置窗口默认关闭方式 -- 
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	public void paint(Graphics g) {

		// 横纵坐标以及半径 
		double x, y, r;

		Image image = this.createImage(WIDTH, HEIGHT);

		Graphics pic = image.getGraphics();
		// 绘制么么哒
		for (int i = -2; i < 90; i++) {
			for (int j = -1; j < 90; j++) {

				r = Math.PI / 45 + Math.PI / 45 * i * (1 - Math.sin(Math.PI / 45 * j)) * 18;
				x = r * Math.cos(Math.PI / 45 * j) * Math.sin(Math.PI / 45 * i) + WIDTH / 2;
				y = -r * Math.sin(Math.PI / 45 * j) + HEIGHT / 3;
				pic.setColor(Color.MAGENTA);
				pic.fillOval((int) x, (int) y, 2, 2);
				// 生成图片
				g.drawImage(image, 0, 0, this);

			}

		}
		// 署名
		pic.setColor(Color.LIGHT_GRAY);
		pic.drawString("...", 400, 425);
		g.drawImage(image, 0, 0, this);
	}

	public static void main(String[] args) {
		// 启动
		new Xin();
	}

}
