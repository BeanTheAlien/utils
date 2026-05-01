package utils.canvas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Frame {
    public JFrame frame;
    public Canvas canvas;
    public static enum Close {
        Kill,
        Dispose,
        Hide,
        None
    };
    public static enum Size {
        Norm,
        Conf,
        MaxW,
        MaxH,
        Max
    };
    private int operationClose(Close close) {
        return close == Close.Kill ? JFrame.EXIT_ON_CLOSE :
            close == Close.Dispose ? JFrame.DISPOSE_ON_CLOSE :
            close == Close.Hide ? JFrame.HIDE_ON_CLOSE :
            close == Close.None ? JFrame.DO_NOTHING_ON_CLOSE :
            -1;
    }
    private int operationSize(Size size) {
        return size == Size.Norm ? JFrame.NORMAL :
            size == Size.Conf ? JFrame.ICONIFIED :
            size == Size.MaxW ? JFrame.MAXIMIZED_HORIZ :
            size == Size.MaxH ? JFrame.MAXIMIZED_VERT :
            size == Size.Max ? JFrame.MAXIMIZED_BOTH :
            -1;
    }
    public Frame() {
        this("Frame");
    }
    public Frame(String windowName) {
        this(windowName, Close.Kill, Size.Max);
    }
    public Frame(Close closeOperation) {
        this(closeOperation, Size.Max);
    }
    public Frame(Size sizeOperation) {
        this(Close.Kill, sizeOperation);
    }
    public Frame(Close closeOperation, Size sizeOperation) {
        this("Frame", closeOperation, sizeOperation);
    }
    public Frame(String windowName, Close closeOperation, Size sizeOperation) {
        this.frame = new JFrame(windowName);
        this.frame.setDefaultCloseOperation(this.operationClose(closeOperation));
        this.frame.setExtendedState(this.operationSize(sizeOperation));
        this.frame.setVisible(true);
        this.canvas = new Canvas();
        this.frame.add(this.canvas);
        this.canvas.requestFocusInWindow();
        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                canvas.rend.forEach(r -> r.kill());
            }
        });
    }
}