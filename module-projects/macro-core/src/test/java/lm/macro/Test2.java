package lm.macro;

import com.google.common.base.Stopwatch;
import com.sun.jna.Memory;
import com.sun.jna.platform.win32.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Test2 {
    public static BufferedImage capture(WinDef.HWND hwnd) throws Exception {
        Stopwatch w = Stopwatch.createStarted();
        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, rect);


//        int width = rect.right - rect.left;
//        int height = rect.bottom - rect.top;
//        User32.INSTANCE.SetForegroundWindow(hwnd);

        Robot robot = new Robot();
//        robot.keyPress(KeyEvent.VK_ENTER);
//        robot.keyRelease(KeyEvent.VK_ENTER);

        return robot.createScreenCapture(rect.toRectangle());
    }

    public static BufferedImage capture2(WinDef.HWND hWnd, WinDef.HDC hdc) {
        WinDef.HDC hdcMemDC = GDI32.INSTANCE.CreateCompatibleDC(hdc);
        WinDef.RECT bounds = new WinDef.RECT();
        User32.INSTANCE.GetClientRect(hWnd, bounds);
        int width = bounds.right - bounds.left;
        int height = bounds.bottom - bounds.top;

        WinDef.HBITMAP hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdc, width, height);

        WinNT.HANDLE hOld = GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap);
        GDI32.INSTANCE.BitBlt(hdcMemDC, 0, 0, width, height, hdc, 0, 0, GDI32.SRCCOPY);

        GDI32.INSTANCE.SelectObject(hdcMemDC, hOld);
        GDI32.INSTANCE.DeleteDC(hdcMemDC);

        WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();
        bmi.bmiHeader.biWidth = width;
        bmi.bmiHeader.biHeight = -height;
        bmi.bmiHeader.biPlanes = 1;
        bmi.bmiHeader.biBitCount = 32;
        bmi.bmiHeader.biCompression = WinGDI.BI_RGB;

        Memory buffer = new Memory(width * height * 4);
        GDI32.INSTANCE.GetDIBits(hdc, hBitmap, 0, height, buffer, bmi, WinGDI.DIB_RGB_COLORS);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, buffer.getIntArray(0, width * height), 0, width);

        GDI32.INSTANCE.DeleteObject(hBitmap);
        User32.INSTANCE.ReleaseDC(hWnd, hdc);

        return image;
    }

//    public static BufferedImage capture(WinDef.HWND hWnd) {
//        WinDef.HDC hdcWindow = User32.INSTANCE.GetDC(hWnd);
//        WinDef.HDC hdcMemDC = GDI32.INSTANCE.CreateCompatibleDC(hdcWindow);
//
//        WinDef.RECT bounds = new WinDef.RECT();
//        User32.INSTANCE.GetClientRect(hWnd, bounds);
//
//        int width = bounds.right - bounds.left;
//        int height = bounds.bottom - bounds.top;
//
//        WinDef.HBITMAP hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcWindow, width, height);
//        WinNT.HANDLE hOld = GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap);
//        GDI32.INSTANCE.BitBlt(hdcMemDC, 0, 0, width, height, hdcWindow, 0, 0, GDI32.SRCCOPY);
//
//        GDI32.INSTANCE.SelectObject(hdcMemDC, hOld);
//        GDI32.INSTANCE.DeleteDC(hdcMemDC);
//
//        WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();
//        bmi.bmiHeader.biWidth = width;
//        bmi.bmiHeader.biHeight = -height;
//        bmi.bmiHeader.biPlanes = 1;
//        bmi.bmiHeader.biBitCount = 32;
//        bmi.bmiHeader.biCompression = WinGDI.BI_RGB;
//
//        Memory buffer = new Memory(width * height * 4);
//        GDI32.INSTANCE.GetDIBits(hdcWindow, hBitmap, 0, height, buffer, bmi, WinGDI.DIB_RGB_COLORS);
//
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        image.setRGB(0, 0, width, height, buffer.getIntArray(0, width * height), 0, width);
//
//        GDI32.INSTANCE.DeleteObject(hBitmap);
//        User32.INSTANCE.ReleaseDC(hWnd, hdcWindow);
//
//        return image;
//    }

    public static void main(String[] args) throws Exception {
//        WinDef.HWND hwnd1 = User32.INSTANCE.FindWindow(null, "TEST MOMO");
//        WinDef.HWND hwnd2 = User32.INSTANCE.FindWindow(null, "바보");
////        WinDef.HWND hwnd3 = User32.INSTANCE.FindWindow(null, "[MOMO]앱플레이어-2");
////        WinDef.HWND hwnd4 = User32.INSTANCE.FindWindow(null, "바보-3");
//
//        while (true) {
//            ImageIO.write(capture(hwnd1), "png", new java.io.File("test1.png"));
//            ImageIO.write(capture(hwnd2), "png", new java.io.File("test2.png"));
////            ImageIO.write(capture(hwnd3), "png", new java.io.File("test3.png"));
////            ImageIO.write(capture(hwnd4), "png", new java.io.File("test4.png"));
//            Thread.sleep(300);
//        }
    }

    public static BufferedImage testGetStringGLVersion() throws IOException {
        WinDef.HWND hWnd = User32.INSTANCE.FindWindow(null, "TEST MOMO");
        WinDef.HDC hdc = User32.INSTANCE.GetDC(hWnd);
        WinGDI.PIXELFORMATDESCRIPTOR.ByReference pfd = new WinGDI.PIXELFORMATDESCRIPTOR.ByReference();
        pfd.nVersion = 1;
        pfd.dwFlags = WinGDI.PFD_DRAW_TO_WINDOW | WinGDI.PFD_SUPPORT_OPENGL | WinGDI.PFD_DOUBLEBUFFER;
        pfd.iPixelType = WinGDI.PFD_TYPE_RGBA;
        pfd.cColorBits = 24;
        pfd.cDepthBits = 16;
        pfd.iLayerType = WinGDI.PFD_MAIN_PLANE;
        GDI32.INSTANCE.SetPixelFormat(hdc, GDI32.INSTANCE.ChoosePixelFormat(hdc, pfd), pfd);

        WinDef.HGLRC hGLRC = OpenGL32.INSTANCE.wglCreateContext(hdc);
        OpenGL32.INSTANCE.wglMakeCurrent(hdc, hGLRC);

        WinDef.RECT bounds = new WinDef.RECT();
        User32.INSTANCE.GetClientRect(hWnd, bounds);

        int width = bounds.right - bounds.left;
        int height = bounds.bottom - bounds.top;

        WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();
        bmi.bmiHeader.biWidth = width;
        bmi.bmiHeader.biHeight = -height;
        bmi.bmiHeader.biPlanes = 1;
        bmi.bmiHeader.biBitCount = 32;
        bmi.bmiHeader.biCompression = WinGDI.BI_RGB;

        WinDef.HBITMAP hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdc, width, height);

        WinNT.HANDLE hOld = GDI32.INSTANCE.SelectObject(hdc, hBitmap);
        GDI32.INSTANCE.BitBlt(hdc, 0, 0, width, height, hdc, 0, 0, GDI32.SRCCOPY);

        Memory buffer = new Memory(width * height * 4);
        GDI32.INSTANCE.GetDIBits(hdc, hBitmap, 0, height, buffer, bmi, WinGDI.DIB_RGB_COLORS);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, buffer.getIntArray(0, width * height), 0, width);

        return image;
    }
}
